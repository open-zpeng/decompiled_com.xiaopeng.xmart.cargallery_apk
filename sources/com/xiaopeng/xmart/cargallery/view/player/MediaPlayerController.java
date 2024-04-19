package com.xiaopeng.xmart.cargallery.view.player;

import android.os.CountDownTimer;
import android.widget.SeekBar;
import com.xiaopeng.xmart.cargallery.CameraLog;
/* loaded from: classes20.dex */
public class MediaPlayerController {
    private static final String TAG = "MediaPlayerController";
    private CountDownTimer mCountDownTimer;
    private boolean mIsPlaying;
    private OnPlayListener mOnPlayListener;
    private SeekBar mSeekBar;

    /* loaded from: classes20.dex */
    public interface OnPlayListener {
        void onPlayCompletion();

        void onPlaying(int progress, int duration);
    }

    public MediaPlayerController(SeekBar seekBar) {
        this.mSeekBar = seekBar;
        createTimer();
    }

    public void setOnPlayListener(OnPlayListener listener) {
        this.mOnPlayListener = listener;
    }

    private void createTimer() {
        final int lastProgress = this.mSeekBar.getProgress();
        final long millisInFuture = this.mSeekBar.getMax() - this.mSeekBar.getProgress();
        this.mCountDownTimer = new CountDownTimer(millisInFuture, 1L) { // from class: com.xiaopeng.xmart.cargallery.view.player.MediaPlayerController.1
            @Override // android.os.CountDownTimer
            public void onFinish() {
                MediaPlayerController.this.mIsPlaying = false;
                if (MediaPlayerController.this.mOnPlayListener != null) {
                    MediaPlayerController.this.mOnPlayListener.onPlayCompletion();
                }
            }

            @Override // android.os.CountDownTimer
            public void onTick(final long millisUntilFinished) {
                int progress = ((int) (millisInFuture - millisUntilFinished)) + lastProgress;
                MediaPlayerController.this.mSeekBar.setProgress(progress);
                MediaPlayerController.this.mIsPlaying = true;
                if (MediaPlayerController.this.mOnPlayListener != null) {
                    MediaPlayerController.this.mOnPlayListener.onPlaying(progress, MediaPlayerController.this.mSeekBar.getMax());
                }
            }
        };
    }

    public boolean isPlaying() {
        return this.mIsPlaying;
    }

    public void start() {
        CameraLog.d(TAG, "MediaPlayerController, start().", false);
        createTimer();
        this.mCountDownTimer.start();
    }

    public void pause() {
        CameraLog.d(TAG, "MediaPlayerController, pause().", false);
        cancel();
    }

    public void cancel() {
        this.mIsPlaying = false;
        this.mCountDownTimer.cancel();
    }
}
