package com.xiaopeng.lib.bughunter.anr;

import android.content.Context;
import android.os.Build;
import android.view.Choreographer;
import com.xiaopeng.lib.bughunter.StackTraceCollector;
/* loaded from: classes.dex */
public class Caton {
    static final long DEFAULT_COLLECT_INTERVAL = 1000;
    static MonitorMode DEFAULT_MODE = MonitorMode.LOOPER;
    static final long DEFAULT_THRESHOLD_TIME = 3000;
    static final long MAX_COLLECT_INTERVAL = 400;
    static final long MAX_THRESHOLD_TIME = 400;
    static final long MIN_COLLECT_INTERVAL = 200;
    static final long MIN_THRESHOLD_TIME = 200;
    private static volatile Caton sCaton;

    /* loaded from: classes.dex */
    public interface Callback {
        void onBlockOccurs(String[] strArr, boolean z, long... jArr);
    }

    private Caton(Context context, long thresholdTimeMillis, long collectIntervalMillis, MonitorMode mode, boolean loggingEnabled, Callback callback) {
        long thresholdTime = Math.min(Math.max(thresholdTimeMillis, 200L), 400L);
        long collectInterval = Math.min(Math.max(collectIntervalMillis, 200L), 400L);
        Config.LOG_ENABLED = loggingEnabled;
        Config.THRESHOLD_TIME = thresholdTime;
        Collector mTraceCollector = new StackTraceCollector(collectInterval);
        BlockHandler mBlockHandler = new BlockHandler(context, mTraceCollector, callback);
        if (mode == MonitorMode.LOOPER) {
            new UILooperObserver(mBlockHandler);
        } else if (mode == MonitorMode.FRAME) {
            if (Build.VERSION.SDK_INT >= 16) {
                FPSFrameCallBack fpsFrameCallBack = new FPSFrameCallBack(context, mBlockHandler);
                Choreographer.getInstance().postFrameCallback(fpsFrameCallBack);
                return;
            }
            new UILooperObserver(mBlockHandler);
        }
    }

    public static void initialize(Context context) {
        initialize(new Builder(context));
    }

    public static void initialize(Builder builder) {
        if (sCaton == null) {
            synchronized (Caton.class) {
                if (sCaton == null) {
                    sCaton = builder.build();
                }
            }
        }
    }

    public static void setLoggingEnabled(boolean enabled) {
        Config.LOG_ENABLED = enabled;
    }

    /* loaded from: classes.dex */
    public static class Builder {
        private Callback mCallback;
        private Context mContext;
        private long mThresholdTime = Caton.DEFAULT_THRESHOLD_TIME;
        private long mCollectInterval = Caton.DEFAULT_COLLECT_INTERVAL;
        private MonitorMode mMonitorMode = Caton.DEFAULT_MODE;
        private boolean loggingEnabled = true;

        public Builder(Context context) {
            this.mContext = context;
        }

        public Builder thresholdTime(long thresholdTimeMillis) {
            this.mThresholdTime = thresholdTimeMillis;
            return this;
        }

        public Builder collectInterval(long collectIntervalMillis) {
            this.mCollectInterval = collectIntervalMillis;
            return this;
        }

        public Builder monitorMode(MonitorMode mode) {
            this.mMonitorMode = mode;
            return this;
        }

        public Builder loggingEnabled(boolean enable) {
            this.loggingEnabled = enable;
            return this;
        }

        public Builder callback(Callback callback) {
            this.mCallback = callback;
            return this;
        }

        Caton build() {
            return new Caton(this.mContext, this.mThresholdTime, this.mCollectInterval, this.mMonitorMode, this.loggingEnabled, this.mCallback);
        }
    }

    /* loaded from: classes.dex */
    public enum MonitorMode {
        LOOPER(0),
        FRAME(1);
        
        int value;

        MonitorMode(int mode) {
            this.value = mode;
        }
    }
}
