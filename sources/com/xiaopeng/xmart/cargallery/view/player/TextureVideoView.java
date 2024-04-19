package com.xiaopeng.xmart.cargallery.view.player;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.MediaController;
import com.xiaopeng.speech.speechwidget.ListWidget;
import com.xiaopeng.xmart.cargallery.CameraLog;
import com.xiaopeng.xmart.cargallery.helper.ThreadPoolHelper;
/* loaded from: classes20.dex */
public class TextureVideoView extends TextureView implements MediaController.MediaPlayerControl {
    private static final int STATE_ERROR = -1;
    private static final int STATE_IDLE = 0;
    private static final int STATE_PAUSED = 4;
    private static final int STATE_PLAYBACK_COMPLETED = 5;
    private static final int STATE_PLAYING = 3;
    private static final int STATE_PREPARED = 2;
    private static final int STATE_PREPARING = 1;
    private static final String TAG = "TextureVideoView";
    AudioManager.OnAudioFocusChangeListener audioFocusChangeListener;
    private VideoInterrupterListener interruptListener;
    private int mAudioSession;
    private MediaPlayer.OnBufferingUpdateListener mBufferingUpdateListener;
    private boolean mCanPause;
    private boolean mCanSeekBack;
    private boolean mCanSeekForward;
    private MediaPlayer.OnCompletionListener mCompletionListener;
    private int mCurrentBufferPercentage;
    private int mCurrentState;
    private MediaPlayer.OnErrorListener mErrorListener;
    private MediaPlayer.OnInfoListener mInfoListener;
    private MediaController mMediaController;
    private MediaPlayer mMediaPlayer;
    private MediaPlayer.OnCompletionListener mOnCompletionListener;
    private MediaPlayer.OnErrorListener mOnErrorListener;
    private MediaPlayer.OnInfoListener mOnInfoListener;
    private MediaPlayer.OnPreparedListener mOnPreparedListener;
    private String mPath;
    MediaPlayer.OnPreparedListener mPreparedListener;
    private int mSeekWhenPrepared;
    private boolean mShouldRequestAudioFocus;
    MediaPlayer.OnVideoSizeChangedListener mSizeChangedListener;
    protected Surface mSurface;
    TextureView.SurfaceTextureListener mSurfaceTextureListener;
    private int mTargetState;
    private Uri mUri;
    private int mVideoHeight;
    private int mVideoWidth;

    /* loaded from: classes20.dex */
    public interface VideoInterrupterListener {
        void interruptPlay();
    }

    public /* synthetic */ void lambda$new$0$TextureVideoView(int focusChange) {
        CameraLog.d(TAG, "audio focus change...  focus change type: " + focusChange, false);
        switch (focusChange) {
            case -2:
            case -1:
                VideoInterrupterListener videoInterrupterListener = this.interruptListener;
                if (videoInterrupterListener != null) {
                    videoInterrupterListener.interruptPlay();
                    return;
                }
                return;
            default:
                return;
        }
    }

    public TextureVideoView(Context context) {
        this(context, null);
    }

    public TextureVideoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize(this.mVideoWidth, widthMeasureSpec);
        int height = getDefaultSize(this.mVideoHeight, heightMeasureSpec);
        if (this.mVideoWidth > 0 && this.mVideoHeight > 0) {
            int widthSpecMode = View.MeasureSpec.getMode(widthMeasureSpec);
            int widthSpecSize = View.MeasureSpec.getSize(widthMeasureSpec);
            int heightSpecMode = View.MeasureSpec.getMode(heightMeasureSpec);
            int heightSpecSize = View.MeasureSpec.getSize(heightMeasureSpec);
            if (widthSpecMode != 1073741824 || heightSpecMode != 1073741824) {
                if (widthSpecMode == 1073741824) {
                    width = widthSpecSize;
                    height = (this.mVideoHeight * width) / this.mVideoWidth;
                    if (heightSpecMode == Integer.MIN_VALUE && height > heightSpecSize) {
                        height = heightSpecSize;
                    }
                } else if (heightSpecMode == 1073741824) {
                    height = heightSpecSize;
                    width = (this.mVideoWidth * height) / this.mVideoHeight;
                    if (widthSpecMode == Integer.MIN_VALUE && width > widthSpecSize) {
                        width = widthSpecSize;
                    }
                } else {
                    width = this.mVideoWidth;
                    height = this.mVideoHeight;
                    if (heightSpecMode == Integer.MIN_VALUE && height > heightSpecSize) {
                        height = heightSpecSize;
                        width = (this.mVideoWidth * height) / this.mVideoHeight;
                    }
                    if (widthSpecMode == Integer.MIN_VALUE && width > widthSpecSize) {
                        width = widthSpecSize;
                        height = (this.mVideoHeight * width) / this.mVideoWidth;
                    }
                }
            } else {
                width = widthSpecSize;
                height = heightSpecSize;
                int i = this.mVideoWidth;
                int i2 = i * height;
                int i3 = this.mVideoHeight;
                if (i2 < width * i3) {
                    width = (i * height) / i3;
                } else if (i * height > width * i3) {
                    height = (i3 * width) / i;
                }
            }
        }
        setMeasuredDimension(width, height);
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        event.setClassName(TextureVideoView.class.getName());
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(TextureVideoView.class.getName());
    }

    public int resolveAdjustedSize(int desiredSize, int measureSpec) {
        return getDefaultSize(desiredSize, measureSpec);
    }

    public void setVideoURI(String path) {
        this.mPath = path;
        this.mSeekWhenPrepared = 0;
        openVideo();
        requestLayout();
        invalidate();
    }

    public TextureVideoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mCurrentState = 0;
        this.mTargetState = 0;
        this.mSurface = null;
        this.mMediaPlayer = null;
        this.mShouldRequestAudioFocus = false;
        this.audioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() { // from class: com.xiaopeng.xmart.cargallery.view.player.-$$Lambda$TextureVideoView$aUUSslfmJp8A9o7QhKVOio3zOIA
            @Override // android.media.AudioManager.OnAudioFocusChangeListener
            public final void onAudioFocusChange(int i) {
                TextureVideoView.this.lambda$new$0$TextureVideoView(i);
            }
        };
        this.mSurfaceTextureListener = new TextureView.SurfaceTextureListener() { // from class: com.xiaopeng.xmart.cargallery.view.player.TextureVideoView.1
            @Override // android.view.TextureView.SurfaceTextureListener
            public void onSurfaceTextureSizeChanged(final SurfaceTexture surface, final int width, final int height) {
                boolean hasValidSize = true;
                boolean isValidState = TextureVideoView.this.mTargetState == 3;
                if (width <= 0 || height <= 0) {
                    hasValidSize = false;
                }
                if (TextureVideoView.this.mMediaPlayer != null && isValidState && hasValidSize) {
                    if (TextureVideoView.this.mSeekWhenPrepared != 0) {
                        TextureVideoView textureVideoView = TextureVideoView.this;
                        textureVideoView.seekTo(textureVideoView.mSeekWhenPrepared);
                    }
                    TextureVideoView.this.start();
                }
            }

            @Override // android.view.TextureView.SurfaceTextureListener
            public void onSurfaceTextureAvailable(final SurfaceTexture surface, final int width, final int height) {
                TextureVideoView.this.mSurface = new Surface(surface);
                TextureVideoView.this.openVideo();
            }

            @Override // android.view.TextureView.SurfaceTextureListener
            public boolean onSurfaceTextureDestroyed(final SurfaceTexture surface) {
                if (TextureVideoView.this.mSurface != null) {
                    TextureVideoView.this.mSurface.release();
                    TextureVideoView.this.mSurface = null;
                }
                if (TextureVideoView.this.mMediaController != null) {
                    TextureVideoView.this.mMediaController.hide();
                }
                TextureVideoView.this.release(true);
                return true;
            }

            @Override // android.view.TextureView.SurfaceTextureListener
            public void onSurfaceTextureUpdated(final SurfaceTexture surface) {
            }
        };
        this.mSizeChangedListener = new MediaPlayer.OnVideoSizeChangedListener() { // from class: com.xiaopeng.xmart.cargallery.view.player.TextureVideoView.2
            @Override // android.media.MediaPlayer.OnVideoSizeChangedListener
            public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                TextureVideoView.this.mVideoWidth = mp.getVideoWidth();
                TextureVideoView.this.mVideoHeight = mp.getVideoHeight();
                if (TextureVideoView.this.mVideoWidth != 0 && TextureVideoView.this.mVideoHeight != 0) {
                    TextureVideoView.this.getSurfaceTexture().setDefaultBufferSize(TextureVideoView.this.mVideoWidth, TextureVideoView.this.mVideoHeight);
                    TextureVideoView.this.requestLayout();
                }
            }
        };
        this.mPreparedListener = new MediaPlayer.OnPreparedListener() { // from class: com.xiaopeng.xmart.cargallery.view.player.TextureVideoView.3
            @Override // android.media.MediaPlayer.OnPreparedListener
            public void onPrepared(MediaPlayer mp) {
                TextureVideoView.this.mCurrentState = 2;
                TextureVideoView textureVideoView = TextureVideoView.this;
                textureVideoView.mCanPause = textureVideoView.mCanSeekBack = textureVideoView.mCanSeekForward = true;
                if (TextureVideoView.this.mOnPreparedListener != null) {
                    TextureVideoView.this.mOnPreparedListener.onPrepared(TextureVideoView.this.mMediaPlayer);
                }
                if (TextureVideoView.this.mMediaController != null) {
                    TextureVideoView.this.mMediaController.setEnabled(true);
                }
                TextureVideoView.this.mVideoWidth = mp.getVideoWidth();
                TextureVideoView.this.mVideoHeight = mp.getVideoHeight();
                int seekToPosition = TextureVideoView.this.mSeekWhenPrepared;
                if (seekToPosition != 0) {
                    TextureVideoView.this.seekTo(seekToPosition);
                }
                if (TextureVideoView.this.mVideoWidth == 0 || TextureVideoView.this.mVideoHeight == 0) {
                    if (TextureVideoView.this.mTargetState == 3) {
                        TextureVideoView.this.start();
                        return;
                    }
                    return;
                }
                TextureVideoView.this.getSurfaceTexture().setDefaultBufferSize(TextureVideoView.this.mVideoWidth, TextureVideoView.this.mVideoHeight);
                if (TextureVideoView.this.mTargetState == 3) {
                    TextureVideoView.this.start();
                    if (TextureVideoView.this.mMediaController != null) {
                        TextureVideoView.this.mMediaController.show();
                    }
                } else if (!TextureVideoView.this.isPlaying()) {
                    if ((seekToPosition != 0 || TextureVideoView.this.getCurrentPosition() > 0) && TextureVideoView.this.mMediaController != null) {
                        TextureVideoView.this.mMediaController.show(0);
                    }
                }
            }
        };
        this.mCompletionListener = new MediaPlayer.OnCompletionListener() { // from class: com.xiaopeng.xmart.cargallery.view.player.TextureVideoView.4
            @Override // android.media.MediaPlayer.OnCompletionListener
            public void onCompletion(MediaPlayer mp) {
                TextureVideoView.this.mCurrentState = 5;
                TextureVideoView.this.mTargetState = 5;
                if (TextureVideoView.this.mMediaController != null) {
                    TextureVideoView.this.mMediaController.hide();
                }
                if (TextureVideoView.this.mOnCompletionListener != null) {
                    TextureVideoView.this.mOnCompletionListener.onCompletion(TextureVideoView.this.mMediaPlayer);
                }
            }
        };
        this.mInfoListener = new MediaPlayer.OnInfoListener() { // from class: com.xiaopeng.xmart.cargallery.view.player.TextureVideoView.5
            @Override // android.media.MediaPlayer.OnInfoListener
            public boolean onInfo(MediaPlayer mp, int arg1, int arg2) {
                if (TextureVideoView.this.mOnInfoListener != null) {
                    TextureVideoView.this.mOnInfoListener.onInfo(mp, arg1, arg2);
                    return true;
                }
                return true;
            }
        };
        this.mErrorListener = new MediaPlayer.OnErrorListener() { // from class: com.xiaopeng.xmart.cargallery.view.player.TextureVideoView.6
            @Override // android.media.MediaPlayer.OnErrorListener
            public boolean onError(MediaPlayer mp, int frameworkErr, int implErr) {
                int messageId;
                Log.d(TextureVideoView.TAG, "Error: " + frameworkErr + "," + implErr);
                TextureVideoView.this.mCurrentState = -1;
                TextureVideoView.this.mTargetState = -1;
                if (TextureVideoView.this.mMediaController != null) {
                    TextureVideoView.this.mMediaController.hide();
                }
                if ((TextureVideoView.this.mOnErrorListener == null || !TextureVideoView.this.mOnErrorListener.onError(TextureVideoView.this.mMediaPlayer, frameworkErr, implErr)) && TextureVideoView.this.getWindowToken() != null) {
                    TextureVideoView.this.getContext().getResources();
                    if (frameworkErr == 200) {
                        messageId = 17039381;
                    } else {
                        messageId = 17039377;
                    }
                    new AlertDialog.Builder(TextureVideoView.this.getContext()).setMessage(messageId).setPositiveButton(17039376, new DialogInterface.OnClickListener() { // from class: com.xiaopeng.xmart.cargallery.view.player.TextureVideoView.6.1
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialog, int whichButton) {
                            if (TextureVideoView.this.mOnCompletionListener != null) {
                                TextureVideoView.this.mOnCompletionListener.onCompletion(TextureVideoView.this.mMediaPlayer);
                            }
                        }
                    }).setCancelable(false).show();
                }
                return true;
            }
        };
        this.mBufferingUpdateListener = new MediaPlayer.OnBufferingUpdateListener() { // from class: com.xiaopeng.xmart.cargallery.view.player.TextureVideoView.7
            @Override // android.media.MediaPlayer.OnBufferingUpdateListener
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                TextureVideoView.this.mCurrentBufferPercentage = percent;
            }
        };
        this.mVideoWidth = 0;
        this.mVideoHeight = 0;
        setSurfaceTextureListener(this.mSurfaceTextureListener);
        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus();
        this.mCurrentState = 0;
        this.mTargetState = 0;
    }

    public void setMediaController(MediaController controller) {
        MediaController mediaController = this.mMediaController;
        if (mediaController != null) {
            mediaController.hide();
        }
        this.mMediaController = controller;
        attachMediaController();
    }

    private void attachMediaController() {
        MediaController mediaController;
        if (this.mMediaPlayer != null && (mediaController = this.mMediaController) != null) {
            mediaController.setMediaPlayer(this);
            View anchorView = getParent() instanceof View ? (View) getParent() : this;
            this.mMediaController.setAnchorView(anchorView);
            this.mMediaController.setEnabled(isInPlaybackState());
        }
    }

    public void setOnPreparedListener(MediaPlayer.OnPreparedListener l) {
        this.mOnPreparedListener = l;
    }

    public void setOnCompletionListener(MediaPlayer.OnCompletionListener l) {
        this.mOnCompletionListener = l;
    }

    public void setOnErrorListener(MediaPlayer.OnErrorListener l) {
        this.mOnErrorListener = l;
    }

    public void setOnInfoListener(MediaPlayer.OnInfoListener l) {
        this.mOnInfoListener = l;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void openVideo() {
        if (this.mPath == null || this.mSurface == null) {
            return;
        }
        release(false);
        try {
            MediaPlayer mediaPlayer = new MediaPlayer();
            this.mMediaPlayer = mediaPlayer;
            int i = this.mAudioSession;
            if (i != 0) {
                mediaPlayer.setAudioSessionId(i);
            } else {
                this.mAudioSession = mediaPlayer.getAudioSessionId();
            }
            this.mMediaPlayer.setOnPreparedListener(this.mPreparedListener);
            this.mMediaPlayer.setOnVideoSizeChangedListener(this.mSizeChangedListener);
            this.mMediaPlayer.setOnCompletionListener(this.mCompletionListener);
            this.mMediaPlayer.setOnErrorListener(this.mErrorListener);
            this.mMediaPlayer.setOnInfoListener(this.mInfoListener);
            this.mMediaPlayer.setOnBufferingUpdateListener(this.mBufferingUpdateListener);
            this.mCurrentBufferPercentage = 0;
            this.mMediaPlayer.setSurface(this.mSurface);
            this.mMediaPlayer.setAudioStreamType(3);
            this.mMediaPlayer.setScreenOnWhilePlaying(true);
            ThreadPoolHelper.getInstance().execute(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.view.player.TextureVideoView.8
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        TextureVideoView.this.mMediaPlayer.setDataSource(TextureVideoView.this.mPath);
                        TextureVideoView.this.mMediaPlayer.prepareAsync();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            });
            this.mCurrentState = 1;
            attachMediaController();
        } catch (Exception e) {
            Log.w(TAG, "Unable to open content: " + this.mUri, e);
            this.mCurrentState = -1;
            this.mTargetState = -1;
            this.mErrorListener.onError(this.mMediaPlayer, 1, 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void release(boolean cleartargetstate) {
        CameraLog.d(TAG, "player release cleartargetstate:" + cleartargetstate + " mMediaPlayer:" + this.mMediaPlayer);
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            this.mMediaPlayer.release();
            this.mMediaPlayer = null;
            this.mCurrentState = 0;
            if (cleartargetstate) {
                this.mTargetState = 0;
            }
            abandonAudioFocus();
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent ev) {
        if (isInPlaybackState() && this.mMediaController != null) {
            toggleMediaControlsVisiblity();
            return false;
        }
        return false;
    }

    @Override // android.view.View
    public boolean onTrackballEvent(MotionEvent ev) {
        if (isInPlaybackState() && this.mMediaController != null) {
            toggleMediaControlsVisiblity();
            return false;
        }
        return false;
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean isKeyCodeSupported = (keyCode == 4 || keyCode == 24 || keyCode == 25 || keyCode == 164 || keyCode == 82 || keyCode == 5 || keyCode == 6) ? false : true;
        if (isInPlaybackState() && isKeyCodeSupported && this.mMediaController != null) {
            if (keyCode == 79 || keyCode == 85) {
                if (this.mMediaPlayer.isPlaying()) {
                    pause();
                    this.mMediaController.show();
                } else {
                    start();
                    this.mMediaController.hide();
                }
                return true;
            } else if (keyCode == 126) {
                if (!this.mMediaPlayer.isPlaying()) {
                    start();
                    this.mMediaController.hide();
                }
                return true;
            } else if (keyCode == 86 || keyCode == 127) {
                if (this.mMediaPlayer.isPlaying()) {
                    pause();
                    this.mMediaController.show();
                }
                return true;
            } else {
                toggleMediaControlsVisiblity();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void toggleMediaControlsVisiblity() {
        if (this.mMediaController.isShowing()) {
            this.mMediaController.hide();
        } else {
            this.mMediaController.show();
        }
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public void start() {
        CameraLog.d(TAG, "start play, isInPlaybackState(): " + isInPlaybackState() + " mCurrentState:" + this.mCurrentState, false);
        if (isInPlaybackState()) {
            this.mMediaPlayer.start();
            this.mCurrentState = 3;
            requestAudioFocus();
        }
        this.mTargetState = 3;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public void pause() {
        CameraLog.d(TAG, "start pause, isInPlaybackState(): " + isInPlaybackState() + " mCurrentState:" + this.mCurrentState, false);
        if (isInPlaybackState()) {
            if (this.mMediaPlayer.isPlaying()) {
                this.mMediaPlayer.pause();
                this.mCurrentState = 4;
            }
            abandonAudioFocus();
        }
        this.mTargetState = 4;
    }

    public void suspend() {
        release(false);
    }

    public void resume() {
        openVideo();
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public int getDuration() {
        if (isInPlaybackState()) {
            return this.mMediaPlayer.getDuration();
        }
        return -1;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public int getCurrentPosition() {
        if (isInPlaybackState()) {
            return this.mMediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public void seekTo(int msec) {
        CameraLog.d(TAG, "start seekTo, isInPlaybackState(): " + isInPlaybackState() + " mCurrentState:" + this.mCurrentState, false);
        if (isInPlaybackState()) {
            this.mMediaPlayer.seekTo(msec);
            this.mSeekWhenPrepared = 0;
            return;
        }
        this.mSeekWhenPrepared = msec;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public boolean isPlaying() {
        return isInPlaybackState() && this.mMediaPlayer.isPlaying();
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public int getBufferPercentage() {
        if (this.mMediaPlayer != null) {
            return this.mCurrentBufferPercentage;
        }
        return 0;
    }

    public boolean isInPlaybackState() {
        int i;
        return (this.mMediaPlayer == null || (i = this.mCurrentState) == -1 || i == 0 || i == 1) ? false : true;
    }

    public boolean isPareparing() {
        return this.mMediaPlayer != null && this.mCurrentState == 1;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public boolean canPause() {
        return this.mCanPause;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public boolean canSeekBackward() {
        return this.mCanSeekBack;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public boolean canSeekForward() {
        return this.mCanSeekForward;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public int getAudioSessionId() {
        if (this.mAudioSession == 0) {
            MediaPlayer foo = new MediaPlayer();
            this.mAudioSession = foo.getAudioSessionId();
            foo.release();
        }
        return this.mAudioSession;
    }

    public void setShouldRequestAudioFocus(boolean shouldRequestAudioFocus) {
        this.mShouldRequestAudioFocus = shouldRequestAudioFocus;
    }

    public boolean shouldRequestAudioFocus() {
        return this.mShouldRequestAudioFocus;
    }

    private void requestAudioFocus() {
        VideoInterrupterListener videoInterrupterListener;
        CameraLog.d(TAG, "requestAudioFocus");
        if (this.mShouldRequestAudioFocus) {
            AudioManager am = (AudioManager) getContext().getApplicationContext().getSystemService(ListWidget.EXTRA_TYPE_AUDIO);
            int result = am.requestAudioFocus(this.audioFocusChangeListener, 3, 2);
            if (result == 0 && (videoInterrupterListener = this.interruptListener) != null) {
                videoInterrupterListener.interruptPlay();
            }
        }
    }

    private void abandonAudioFocus() {
        CameraLog.d(TAG, "abandonAudioFocus" + this.mShouldRequestAudioFocus);
        if (this.mShouldRequestAudioFocus) {
            AudioManager am = (AudioManager) getContext().getApplicationContext().getSystemService(ListWidget.EXTRA_TYPE_AUDIO);
            am.abandonAudioFocus(this.audioFocusChangeListener);
        }
    }

    public void setVideoInterruptListener(VideoInterrupterListener listener) {
        this.interruptListener = listener;
    }
}
