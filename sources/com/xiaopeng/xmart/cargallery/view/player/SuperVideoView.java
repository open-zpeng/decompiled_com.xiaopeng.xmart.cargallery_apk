package com.xiaopeng.xmart.cargallery.view.player;

import android.content.Context;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.VideoDecoder;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.xiaopeng.speech.speechwidget.ListWidget;
import com.xiaopeng.xmart.cargallery.CameraLog;
import com.xiaopeng.xmart.cargallery.GlideApp;
import com.xiaopeng.xmart.cargallery.R;
import com.xiaopeng.xmart.cargallery.bean.BaseItem;
import com.xiaopeng.xmart.cargallery.define.DVRDefine;
import com.xiaopeng.xmart.cargallery.fastsp.FastSharedPreferences;
import com.xiaopeng.xmart.cargallery.helper.ThreadPoolHelper;
import com.xiaopeng.xmart.cargallery.model.GalleryDataLoader;
import com.xiaopeng.xmart.cargallery.view.DetailActivity;
import com.xiaopeng.xmart.cargallery.view.GalleryViewPager;
import com.xiaopeng.xmart.cargallery.view.detail.IDetailView;
import com.xiaopeng.xmart.cargallery.view.player.MediaPlayerController;
import com.xiaopeng.xmart.cargallery.view.player.TextureVideoView;
import java.io.File;
import java.util.Locale;
import org.eclipse.paho.client.mqttv3.MqttTopic;
/* loaded from: classes20.dex */
public class SuperVideoView extends RelativeLayout implements IDetailView {
    private static final long MEDIA_CONTROLLER_DISMISS_DURATION = 3000;
    private static final String TAG = "SuperVideoView";
    private AudioManager mAudioManager;
    private View mBgControl;
    private ImageView mBtnController;
    private Runnable mControlViewRunnable;
    private ImageView mCoverImage;
    private MediaPlayer.OnErrorListener mErrorListener;
    private volatile boolean mHasHideCoverExeced;
    private boolean mHasPlayed;
    private boolean mIsPlaying;
    private long mLastPreparingTime;
    private DetailActivity.OnTopViewChangeListener mListener;
    private MediaControlContainer mLlyController;
    private MediaPlayerController mMediaController;
    private View.OnClickListener mOnClickListener;
    private MediaPlayerController.OnPlayListener mOnPlayListener;
    private MediaPlayer.OnPreparedListener mPrepareListener;
    private SeekBar.OnSeekBarChangeListener mSeekBarChangeListener;
    private SeekBar mSeekBarProgress;
    private volatile boolean mSeekBeforeHideCoverExeced;
    private TextView mTvCurrentProgress;
    private TextView mTvTotalProgress;
    private boolean mVideoDataSourceSeted;
    private RequestOptions mVideoOptions;
    private String mVideoPath;
    private CustomVideoView mVideoView;
    private boolean skipHide;

    public SuperVideoView(Context context, DetailActivity.OnTopViewChangeListener listener) {
        this(context, null, 0);
        this.mListener = listener;
    }

    public SuperVideoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$new$0(MediaPlayer mp) {
        CameraLog.d(TAG, "MediaPlayer is prepared. ", false);
        mp.setLooping(false);
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        refreshProgressBar();
    }

    public void refreshProgressBar() {
        SeekBar seekBar = this.mSeekBarProgress;
        if (seekBar != null) {
            seekBar.setProgressDrawable(ContextCompat.getDrawable(getContext(), R.drawable.bg_video_seek_bar));
            this.mSeekBarProgress.setThumb(ContextCompat.getDrawable(getContext(), R.drawable.ic_camera_video_drag));
        }
    }

    public SuperVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mVideoOptions = new RequestOptions();
        this.mPrepareListener = new MediaPlayer.OnPreparedListener() { // from class: com.xiaopeng.xmart.cargallery.view.player.-$$Lambda$SuperVideoView$0WrXG98N1uLcyAWXGYszpZsAGuE
            @Override // android.media.MediaPlayer.OnPreparedListener
            public final void onPrepared(MediaPlayer mediaPlayer) {
                SuperVideoView.lambda$new$0(mediaPlayer);
            }
        };
        this.mControlViewRunnable = new Runnable() { // from class: com.xiaopeng.xmart.cargallery.view.player.SuperVideoView.1
            @Override // java.lang.Runnable
            public void run() {
                SuperVideoView.this.mLlyController.setVisibility(4);
                SuperVideoView.this.mBtnController.setVisibility(4);
                SuperVideoView.this.mBgControl.setVisibility(4);
                if (SuperVideoView.this.mListener != null) {
                    SuperVideoView.this.mListener.onTopChange(true, 8);
                }
            }
        };
        this.mSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() { // from class: com.xiaopeng.xmart.cargallery.view.player.SuperVideoView.2
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                CameraLog.d(SuperVideoView.TAG, "progress : " + progress + " fromUser: " + fromUser);
                if (fromUser) {
                    SuperVideoView superVideoView = SuperVideoView.this;
                    superVideoView.updateTextViewFormat(superVideoView.mTvCurrentProgress, progress);
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
                CameraLog.d(SuperVideoView.TAG, "onStartTrackingTouch...", false);
                ThreadPoolHelper.getInstance().removeCallbacksOnMainThread(SuperVideoView.this.mControlViewRunnable);
                SuperVideoView.this.setVideoDataSource();
                SuperVideoView superVideoView = SuperVideoView.this;
                superVideoView.mIsPlaying = superVideoView.mMediaController.isPlaying();
                if (SuperVideoView.this.mIsPlaying) {
                    SuperVideoView.this.mVideoView.pause();
                    SuperVideoView.this.mMediaController.pause();
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                CameraLog.d(SuperVideoView.TAG, "onStopTrackingTouch, isPlaying:" + SuperVideoView.this.mVideoView.isPlaying(), false);
                SuperVideoView superVideoView = SuperVideoView.this;
                superVideoView.mSeekBeforeHideCoverExeced = !superVideoView.mHasHideCoverExeced;
                SuperVideoView.this.hideCover();
                SuperVideoView.this.setVideoDataSource();
                SuperVideoView.this.mVideoView.seekTo(seekBar.getProgress());
                CameraLog.d(SuperVideoView.TAG, "SeekTo: " + seekBar.getProgress() + "  Max:" + seekBar.getMax(), false);
                CameraLog.d(SuperVideoView.TAG, "mIsPlaying: " + SuperVideoView.this.mIsPlaying + " progress:" + seekBar.getProgress() + " max:" + seekBar.getMax(), false);
                if (SuperVideoView.this.mIsPlaying) {
                    if (seekBar.getProgress() != seekBar.getMax()) {
                        SuperVideoView.this.mVideoView.start();
                        SuperVideoView.this.mMediaController.start();
                    } else {
                        SuperVideoView.this.reset();
                    }
                }
                if (SuperVideoView.this.mVideoView.isPlaying()) {
                    ThreadPoolHelper.getInstance().postDelayedOnMainThread(SuperVideoView.this.mControlViewRunnable, SuperVideoView.MEDIA_CONTROLLER_DISMISS_DURATION);
                }
            }
        };
        this.mErrorListener = new MediaPlayer.OnErrorListener() { // from class: com.xiaopeng.xmart.cargallery.view.player.-$$Lambda$SuperVideoView$PPwJfnwrHte5YwfncZkMTb4DHo8
            @Override // android.media.MediaPlayer.OnErrorListener
            public final boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                return SuperVideoView.this.lambda$new$1$SuperVideoView(mediaPlayer, i, i2);
            }
        };
        this.mOnClickListener = new View.OnClickListener() { // from class: com.xiaopeng.xmart.cargallery.view.player.SuperVideoView.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                CameraLog.d(SuperVideoView.TAG, "MediaPlayer is playing: " + SuperVideoView.this.mVideoView.isPlaying(), false);
                ThreadPoolHelper.getInstance().removeCallbacksOnMainThread(SuperVideoView.this.mControlViewRunnable);
                if (SuperVideoView.this.mVideoView.isPlaying()) {
                    SuperVideoView.this.mBtnController.setImageResource(R.drawable.ic_camera_video_play);
                    SuperVideoView.this.mBtnController.setVisibility(0);
                    SuperVideoView.this.mBgControl.setVisibility(0);
                    SuperVideoView.this.mLlyController.setVisibility(0);
                    SuperVideoView.this.mVideoView.pause();
                    SuperVideoView.this.mMediaController.pause();
                } else if (((DetailActivity) SuperVideoView.this.getContext()).checkPGear()) {
                    CameraLog.d(SuperVideoView.TAG, "Now is P Gear,play video directly! ", false);
                    SuperVideoView.this.executePlayVideo();
                }
            }
        };
        this.mOnPlayListener = new MediaPlayerController.OnPlayListener() { // from class: com.xiaopeng.xmart.cargallery.view.player.SuperVideoView.4
            @Override // com.xiaopeng.xmart.cargallery.view.player.MediaPlayerController.OnPlayListener
            public void onPlaying(int progress, int duration) {
                SuperVideoView superVideoView = SuperVideoView.this;
                superVideoView.updateTextViewFormat(superVideoView.mTvCurrentProgress, progress);
                SuperVideoView superVideoView2 = SuperVideoView.this;
                superVideoView2.updateTextViewFormat(superVideoView2.mTvTotalProgress, duration);
            }

            @Override // com.xiaopeng.xmart.cargallery.view.player.MediaPlayerController.OnPlayListener
            public void onPlayCompletion() {
                CameraLog.d(SuperVideoView.TAG, "MediaPlayerController, onPlayCompletion...", false);
                SuperVideoView.this.reset();
                if (SuperVideoView.this.mListener != null) {
                    SuperVideoView.this.mListener.onTopChange(true, 0);
                }
            }
        };
        init();
        initView();
        initData();
        initListener();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setVideoDataSource() {
        if (this.mVideoDataSourceSeted) {
            return;
        }
        this.mVideoDataSourceSeted = true;
        String path = this.mVideoPath;
        if (TextUtils.isEmpty(path)) {
            return;
        }
        this.mVideoView.setVideoURI(path);
    }

    private void warmUp() {
        setVideoDataSource();
    }

    public /* synthetic */ boolean lambda$new$1$SuperVideoView(MediaPlayer mediaPlayer, int i, int i1) {
        reset();
        return true;
    }

    public void executePlayVideo() {
        this.mBtnController.setVisibility(0);
        this.mBgControl.setVisibility(0);
        CameraLog.d(TAG, "mVideoView.isInPlaybackState(): " + this.mVideoView.isInPlaybackState(), false);
        if (this.mVideoView.isInPlaybackState()) {
            playVideo();
        } else if (this.mVideoView.isPareparing()) {
            CameraLog.e(TAG, "MediaPlayer is preparing", false);
            long curPreparingTime = System.currentTimeMillis();
            long j = this.mLastPreparingTime;
            if (j > 0 && curPreparingTime - j > 1000) {
                this.mVideoView.setVideoURI(this.mVideoPath);
            }
            this.mLastPreparingTime = curPreparingTime;
        } else {
            startPlayVideo();
        }
    }

    public void setPhotoPath(String path) {
        GlideApp.with(getContext()).load(path).apply((BaseRequestOptions<?>) this.mVideoOptions).into(this.mCoverImage);
    }

    @Override // android.widget.RelativeLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            ViewParent parent = getParent();
            if (parent instanceof GalleryViewPager) {
                this.mLlyController.setViewPager((GalleryViewPager) parent);
            }
        }
    }

    private void initData() {
        this.mAudioManager.getStreamVolume(3);
    }

    @Override // com.xiaopeng.xmart.cargallery.view.detail.IDetailView
    public void setData(BaseItem item) {
        if (item != null) {
            this.mVideoPath = item.getPath();
            if (item.isDvrItem() && (item.getDuration() <= 0 || item.getDuration() == DVRDefine.DVR_VIDEO_DURATION_NOT_EXTRACTED)) {
                FastSharedPreferences fastSp = FastSharedPreferences.get(DVRDefine.DVR_VIDEO_DURATION_FILE_NAME);
                String str = this.mVideoPath;
                String videoName = str.substring(str.lastIndexOf(MqttTopic.TOPIC_LEVEL_SEPARATOR) + 1);
                if (fastSp.contains(videoName)) {
                    long videoDuration = fastSp.getLong(videoName, -1L);
                    CameraLog.d(TAG, "FastSharedPreferences get " + videoName + ",videoDuration:" + videoDuration, false);
                    if (videoDuration > 0) {
                        lambda$null$2$SuperVideoView(videoDuration);
                    } else {
                        asyncParseDVRVideoDuration();
                    }
                } else {
                    asyncParseDVRVideoDuration();
                }
            } else {
                lambda$null$2$SuperVideoView(item.getDuration());
            }
            GlideApp.with(getContext()).load(this.mVideoPath).apply((BaseRequestOptions<?>) this.mVideoOptions).into(this.mCoverImage);
        }
    }

    private void asyncParseDVRVideoDuration() {
        ThreadPoolHelper.getInstance().execute(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.view.player.-$$Lambda$SuperVideoView$8c-5H_Wl75QDA5mR1Vpi7-u_B7Q
            @Override // java.lang.Runnable
            public final void run() {
                SuperVideoView.this.lambda$asyncParseDVRVideoDuration$3$SuperVideoView();
            }
        });
    }

    public /* synthetic */ void lambda$asyncParseDVRVideoDuration$3$SuperVideoView() {
        if (new File(this.mVideoPath).exists()) {
            FastSharedPreferences fastSp = FastSharedPreferences.get(DVRDefine.DVR_VIDEO_DURATION_FILE_NAME);
            final long duration = GalleryDataLoader.parseDurationFromMMRetriever(this.mVideoPath);
            if (duration > 0) {
                String str = this.mVideoPath;
                String videoName = str.substring(str.lastIndexOf(MqttTopic.TOPIC_LEVEL_SEPARATOR) + 1);
                fastSp.edit().putLong(videoName, duration).apply();
                CameraLog.d(TAG, "FastSharedPreferences save " + videoName + ",videoDuration:" + duration, false);
                ThreadPoolHelper.getInstance().postOnMainThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.view.player.-$$Lambda$SuperVideoView$y9cm3qWpH5dP9FX2pPQYxwS67sY
                    @Override // java.lang.Runnable
                    public final void run() {
                        SuperVideoView.this.lambda$null$2$SuperVideoView(duration);
                    }
                });
                return;
            }
            return;
        }
        CameraLog.d(TAG, "video file:" + this.mVideoPath + " not exist.", false);
    }

    private void init() {
        this.mAudioManager = (AudioManager) getContext().getSystemService(ListWidget.EXTRA_TYPE_AUDIO);
        this.mVideoOptions = this.mVideoOptions.frame(0L).diskCacheStrategy(DiskCacheStrategy.RESOURCE).set(VideoDecoder.FRAME_OPTION, 3);
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_detail_video_item, this);
        CustomVideoView customVideoView = (CustomVideoView) findViewById(R.id.videoView);
        this.mVideoView = customVideoView;
        customVideoView.setShouldRequestAudioFocus(true);
        this.mSeekBarProgress = (SeekBar) findViewById(R.id.seekbar_progress);
        this.mBtnController = (ImageView) findViewById(R.id.btn_controller);
        this.mTvCurrentProgress = (TextView) findViewById(R.id.tv_currentProgress);
        this.mTvTotalProgress = (TextView) findViewById(R.id.tv_totalProgress);
        this.mLlyController = (MediaControlContainer) findViewById(R.id.lly_controller);
        this.mBgControl = findViewById(R.id.view_bg_control);
        this.mCoverImage = (ImageView) findViewById(R.id.iv_video_cover);
    }

    @Override // com.xiaopeng.xmart.cargallery.view.detail.IDetailView
    public void onDestroyItem() {
        this.mVideoView.cleanup();
    }

    private void otherDistrictClick() {
        if (this.mLlyController.getVisibility() == 0) {
            this.mLlyController.setVisibility(4);
            this.mBtnController.setVisibility(4);
            this.mBgControl.setVisibility(4);
            DetailActivity.OnTopViewChangeListener onTopViewChangeListener = this.mListener;
            if (onTopViewChangeListener != null) {
                onTopViewChangeListener.onTopChange(true, 8);
                return;
            }
            return;
        }
        this.mLlyController.setVisibility(0);
        this.mBtnController.setVisibility(0);
        this.mBgControl.setVisibility(0);
        DetailActivity.OnTopViewChangeListener onTopViewChangeListener2 = this.mListener;
        if (onTopViewChangeListener2 != null) {
            onTopViewChangeListener2.onTopChange(true, 0);
        }
        if (this.mVideoView.isPlaying()) {
            ThreadPoolHelper.getInstance().postDelayedOnMainThread(this.mControlViewRunnable, MEDIA_CONTROLLER_DISMISS_DURATION);
        }
    }

    @Override // com.xiaopeng.xmart.cargallery.view.detail.IDetailView
    public void cleanup() {
        ThreadPoolHelper.getInstance().removeCallbacksOnMainThread(this.mControlViewRunnable);
        this.mControlViewRunnable = null;
        this.mVideoView.setOnErrorListener(null);
        this.mErrorListener = null;
        this.mVideoView.setOnCompletionListener(null);
        this.mSeekBarProgress.setOnSeekBarChangeListener(null);
        this.mSeekBarChangeListener = null;
        this.mBtnController.setOnClickListener(null);
        this.mOnClickListener = null;
        this.mVideoDataSourceSeted = false;
        this.mVideoView.cleanup();
        this.mLlyController.cleanup();
        this.mLastPreparingTime = 0L;
        this.mHasHideCoverExeced = false;
        this.mSeekBeforeHideCoverExeced = false;
    }

    private void startPlayVideo() {
        this.mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: com.xiaopeng.xmart.cargallery.view.player.-$$Lambda$SuperVideoView$bwDoNqjCr5lIwWhH23XgtdYIWqA
            @Override // android.media.MediaPlayer.OnPreparedListener
            public final void onPrepared(MediaPlayer mediaPlayer) {
                SuperVideoView.this.lambda$startPlayVideo$4$SuperVideoView(mediaPlayer);
            }
        });
        CameraLog.d(TAG, "Start setVideoDataSource. ", false);
        setVideoDataSource();
    }

    public /* synthetic */ void lambda$startPlayVideo$4$SuperVideoView(MediaPlayer mp) {
        this.mVideoView.setOnPreparedListener(null);
        CameraLog.d(TAG, "Start setVideoDataSource, prepared.", false);
        playVideo();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideCover() {
        this.skipHide = false;
        ThreadPoolHelper.getInstance().execute(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.view.player.-$$Lambda$SuperVideoView$ts4oYbIHqXqgX-zWUSbBMcFIBF0
            @Override // java.lang.Runnable
            public final void run() {
                SuperVideoView.this.lambda$hideCover$6$SuperVideoView();
            }
        });
    }

    public /* synthetic */ void lambda$hideCover$6$SuperVideoView() {
        long time = System.currentTimeMillis();
        while (true) {
            if (this.mSeekBeforeHideCoverExeced) {
                SystemClock.sleep(250L);
            }
            this.mHasHideCoverExeced = true;
            long position = this.mVideoView.getCurrentPosition();
            if (position > 0) {
                ThreadPoolHelper.getInstance().postDelayedOnMainThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.view.player.-$$Lambda$SuperVideoView$PFZzLzaW7Vf7cfsvNs8Pll8j3MI
                    @Override // java.lang.Runnable
                    public final void run() {
                        SuperVideoView.this.lambda$null$5$SuperVideoView();
                    }
                }, 250L);
                return;
            } else if (System.currentTimeMillis() - time <= MEDIA_CONTROLLER_DISMISS_DURATION) {
                SystemClock.sleep(20L);
            } else {
                return;
            }
        }
    }

    public /* synthetic */ void lambda$null$5$SuperVideoView() {
        if (this.mCoverImage.getVisibility() == 8) {
            this.mVideoView.seekTo(0);
        }
        if (!this.skipHide) {
            this.mCoverImage.setVisibility(4);
        } else {
            CameraLog.d(TAG, "Already reset skip hide");
        }
    }

    @Override // com.xiaopeng.xmart.cargallery.view.detail.IDetailView
    public void onPause() {
        if (this.mVideoView.isPlaying()) {
            this.mBtnController.setImageResource(R.drawable.ic_camera_video_play);
            this.mBtnController.setVisibility(0);
            this.mVideoView.pause();
            this.mMediaController.pause();
            this.mLlyController.setVisibility(0);
            this.mBgControl.setVisibility(0);
            ThreadPoolHelper.getInstance().removeCallbacksOnMainThread(this.mControlViewRunnable);
        }
    }

    private void playVideo() {
        CameraLog.d(TAG, "Start playVideo().", false);
        if (this.mSeekBarProgress.getMax() == this.mSeekBarProgress.getProgress()) {
            reset();
            return;
        }
        this.mBtnController.setImageResource(R.drawable.ic_camera_video_pause);
        CameraLog.d(TAG, "MediaPlayer start play. hasPlayed:" + this.mHasPlayed, false);
        CameraLog.d(TAG, "MediaPlayer start play over.", false);
        this.mMediaController.start();
        this.mVideoView.start();
        hideCover();
        this.mLlyController.setVisibility(0);
        this.mBgControl.setVisibility(0);
        ThreadPoolHelper.getInstance().postDelayedOnMainThread(this.mControlViewRunnable, MEDIA_CONTROLLER_DISMISS_DURATION);
    }

    private void initListener() {
        this.mVideoView.setVideoInterruptListener(new TextureVideoView.VideoInterrupterListener() { // from class: com.xiaopeng.xmart.cargallery.view.player.-$$Lambda$SuperVideoView$KSqDSjv7cDJSEeeQFLx8CjHwV0Y
            @Override // com.xiaopeng.xmart.cargallery.view.player.TextureVideoView.VideoInterrupterListener
            public final void interruptPlay() {
                SuperVideoView.this.lambda$initListener$7$SuperVideoView();
            }
        });
        this.mBtnController.setOnClickListener(this.mOnClickListener);
        this.mVideoView.setOnErrorListener(this.mErrorListener);
        this.mVideoView.setOnPreparedListener(this.mPrepareListener);
        findViewById(R.id.controller_rl).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.xmart.cargallery.view.player.-$$Lambda$SuperVideoView$NDrbUwTL2VAQyT79jCTwbBZY7Cs
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SuperVideoView.this.lambda$initListener$8$SuperVideoView(view);
            }
        });
        this.mSeekBarProgress.setOnSeekBarChangeListener(this.mSeekBarChangeListener);
    }

    public /* synthetic */ void lambda$initListener$7$SuperVideoView() {
        this.mBtnController.setImageResource(R.drawable.ic_camera_video_play);
        this.mBtnController.setVisibility(0);
        this.mVideoView.pause();
        this.mMediaController.pause();
        this.mLlyController.setVisibility(0);
        this.mBgControl.setVisibility(0);
    }

    public /* synthetic */ void lambda$initListener$8$SuperVideoView(View view) {
        otherDistrictClick();
    }

    /* renamed from: setMaxDuration */
    public void lambda$null$2$SuperVideoView(long duration) {
        CameraLog.d(TAG, "Video duration: " + duration, false);
        updateTextViewFormat(this.mTvTotalProgress, (int) duration);
        this.mSeekBarProgress.setMax((int) duration);
        MediaPlayerController mediaPlayerController = this.mMediaController;
        if (mediaPlayerController != null) {
            mediaPlayerController.cancel();
        }
        MediaPlayerController mediaPlayerController2 = new MediaPlayerController(this.mSeekBarProgress);
        this.mMediaController = mediaPlayerController2;
        mediaPlayerController2.setOnPlayListener(this.mOnPlayListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateTextViewFormat(final TextView tv, final int m) {
        String result;
        int second = m / 1000;
        int hour = second / 3600;
        int minute = (second % 3600) / 60;
        int ss = second % 60;
        CameraLog.d("TIME", "m: " + m + "minute:" + minute + "  ss:" + ss);
        if (hour != 0) {
            result = String.format(Locale.getDefault(), "%02d:%02d:%02d", Integer.valueOf(hour), Integer.valueOf(minute), Integer.valueOf(ss));
        } else {
            result = String.format(Locale.getDefault(), "%02d:%02d", Integer.valueOf(minute), Integer.valueOf(ss));
        }
        tv.setText(result);
    }

    @Override // com.xiaopeng.xmart.cargallery.view.detail.IDetailView
    public void reset() {
        this.skipHide = true;
        CameraLog.d(TAG, "MediaPlayerController, reset...", false);
        ThreadPoolHelper.getInstance().removeCallbacksOnMainThread(this.mControlViewRunnable);
        this.mBtnController.setImageResource(R.drawable.ic_camera_video_play);
        this.mBtnController.setVisibility(0);
        this.mLlyController.setVisibility(0);
        this.mBgControl.setVisibility(0);
        this.mVideoView.pause();
        this.mVideoView.seekTo(0);
        this.mSeekBarProgress.setProgress(0);
        updateTextViewFormat(this.mTvCurrentProgress, 0);
        MediaPlayerController mediaPlayerController = this.mMediaController;
        if (mediaPlayerController != null) {
            mediaPlayerController.cancel();
        }
        this.mCoverImage.setVisibility(0);
        this.mLastPreparingTime = 0L;
    }

    @Override // android.view.View
    public void onWindowFocusChanged(boolean b) {
        super.onWindowFocusChanged(b);
        if (!b && this.mVideoView.isPlaying()) {
            this.mOnClickListener.onClick(this.mBtnController);
        }
    }
}
