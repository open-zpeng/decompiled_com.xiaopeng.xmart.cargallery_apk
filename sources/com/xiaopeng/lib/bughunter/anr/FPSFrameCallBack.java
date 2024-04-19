package com.xiaopeng.lib.bughunter.anr;

import android.content.Context;
import android.view.Choreographer;
import android.view.Display;
import android.view.WindowManager;
/* loaded from: classes.dex */
public class FPSFrameCallBack implements Choreographer.FrameCallback {
    private static long SKIPPED_FRAME_ANR_TRIGGER = 0;
    private static long SKIPPED_FRAME_WARNING_LIMIT = 0;
    private static final String TAG = "FPSFrameCallBack";
    private BlockHandler mBlockHandler;
    private long mFrameIntervalNanos;
    private long mLastFrameTimeNanos;

    public FPSFrameCallBack(Context context, BlockHandler blockHandler) {
        float mRefreshRate = getRefreshRate(context);
        this.mFrameIntervalNanos = 1.0E9f / mRefreshRate;
        long j = this.mFrameIntervalNanos;
        SKIPPED_FRAME_WARNING_LIMIT = ((Config.THRESHOLD_TIME * 1000) * 1000) / j;
        SKIPPED_FRAME_ANR_TRIGGER = 5000000000L / j;
        Config.log(TAG, "SKIPPED_FRAME_WARNING_LIMIT : " + SKIPPED_FRAME_WARNING_LIMIT + " ,SKIPPED_FRAME_ANR_TRIGGER : " + SKIPPED_FRAME_ANR_TRIGGER);
        this.mBlockHandler = blockHandler;
    }

    private float getRefreshRate(Context context) {
        Display display = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        return display.getRefreshRate();
    }

    @Override // android.view.Choreographer.FrameCallback
    public void doFrame(long frameTimeNanos) {
        long j = this.mLastFrameTimeNanos;
        if (j == 0) {
            this.mLastFrameTimeNanos = frameTimeNanos;
            Choreographer.getInstance().postFrameCallback(this);
            return;
        }
        long jitterNanos = frameTimeNanos - j;
        long j2 = this.mFrameIntervalNanos;
        if (jitterNanos >= j2) {
            long skippedFrames = jitterNanos / j2;
            if (skippedFrames >= SKIPPED_FRAME_WARNING_LIMIT) {
                Config.log(TAG, "Skipped " + skippedFrames + " frames!  The application may be doing too much work on its main thread.");
                this.mBlockHandler.notifyBlockOccurs(skippedFrames >= SKIPPED_FRAME_ANR_TRIGGER, skippedFrames);
            }
        }
        this.mLastFrameTimeNanos = frameTimeNanos;
        Choreographer.getInstance().postFrameCallback(this);
    }
}
