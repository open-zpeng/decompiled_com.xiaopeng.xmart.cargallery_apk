package com.xiaopeng.xui.app.delegate;

import android.app.Activity;
import android.os.Handler;
import com.xiaopeng.xui.app.delegate.XActivityDismiss;
import com.xiaopeng.xui.app.delegate.XActivityDismissExtend;
import com.xiaopeng.xui.utils.XActivityUtils;
import com.xiaopeng.xui.utils.XLogUtils;
/* loaded from: classes.dex */
abstract class XActivityDismissExtend implements XActivityDismiss, XActivityLifecycle {

    /* loaded from: classes.dex */
    interface OnDismissListenerEx {
        void afterDismiss(int i);

        void beforeDismiss(int i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void setOnDismissListenerEx(OnDismissListenerEx onDismissListenerEx);

    XActivityDismissExtend() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static XActivityDismissExtend create(Activity activity) {
        return new XActivityDismissExtendImpl(activity);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class XActivityDismissExtendImpl extends XActivityDismissExtend {
        private static final String TAG = "XActivityDismiss";
        private Activity mActivity;
        private Runnable mDelayRunnable;
        private XActivityDismiss.OnDismissListener mOnDismissListener;
        private OnDismissListenerEx mOnDismissListenerEx;
        private int mDismissType = 0;
        private long mDismissDelay = 0;
        private final Handler mHandler = new Handler();

        XActivityDismissExtendImpl(Activity activity) {
            this.mActivity = activity;
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityDismiss
        public void setDismissDelay(long delay) {
            this.mDismissDelay = delay;
            XLogUtils.i(TAG, "setDismissDelay: " + delay);
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityDismiss
        public void setOnDismissListener(XActivityDismiss.OnDismissListener listener) {
            this.mOnDismissListener = listener;
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityDismissExtend
        void setOnDismissListenerEx(OnDismissListenerEx listenerEx) {
            this.mOnDismissListenerEx = listenerEx;
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityDismiss
        public void setDismissType(int type) {
            this.mDismissType = type;
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityDismiss
        public void dismiss(final int cause) {
            XActivityDismiss.OnDismissListener onDismissListener = this.mOnDismissListener;
            if (onDismissListener != null && onDismissListener.onDismiss(cause, this.mDismissType)) {
                XLogUtils.i(TAG, "dismiss: intercept for " + convertCauseToString(cause));
                return;
            }
            OnDismissListenerEx onDismissListenerEx = this.mOnDismissListenerEx;
            if (onDismissListenerEx != null) {
                onDismissListenerEx.beforeDismiss(cause);
            }
            if (this.mDismissDelay == 0) {
                _dismiss(cause);
                return;
            }
            XLogUtils.i(TAG, "dismiss: type : " + convertTypeToString(this.mDismissType) + " , cause : " + convertCauseToString(cause) + " " + this.mDelayRunnable);
            if (this.mDelayRunnable == null) {
                Runnable runnable = new Runnable() { // from class: com.xiaopeng.xui.app.delegate.-$$Lambda$XActivityDismissExtend$XActivityDismissExtendImpl$88suYoVCKDXANih9pewktN1TrnQ
                    @Override // java.lang.Runnable
                    public final void run() {
                        XActivityDismissExtend.XActivityDismissExtendImpl.this.lambda$dismiss$0$XActivityDismissExtend$XActivityDismissExtendImpl(cause);
                    }
                };
                this.mDelayRunnable = runnable;
                this.mHandler.postDelayed(runnable, this.mDismissDelay);
            }
        }

        public /* synthetic */ void lambda$dismiss$0$XActivityDismissExtend$XActivityDismissExtendImpl(int cause) {
            _dismiss(cause);
            this.mDelayRunnable = null;
            OnDismissListenerEx onDismissListenerEx = this.mOnDismissListenerEx;
            if (onDismissListenerEx != null) {
                onDismissListenerEx.afterDismiss(cause);
            }
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityLifecycle
        public void onDestroy() {
            Runnable runnable = this.mDelayRunnable;
            if (runnable != null) {
                this.mHandler.removeCallbacks(runnable);
                this.mDelayRunnable = null;
            }
        }

        private void _dismiss(int cause) {
            XLogUtils.i(TAG, "_dismiss: type : " + convertTypeToString(this.mDismissType) + " , cause : " + convertCauseToString(cause));
            int type = this.mDismissType;
            switch (type) {
                case 0:
                    XActivityUtils.finish(this.mActivity);
                    return;
                case 1:
                    XActivityUtils.moveTaskToBack(this.mActivity, true);
                    return;
                default:
                    return;
            }
        }

        private String convertCauseToString(int cause) {
            switch (cause) {
                case 1:
                    return "back";
                case 2:
                    return "pause";
                case 3:
                    return "outside";
                case 4:
                    return "timeout";
                default:
                    return "user";
            }
        }

        private String convertTypeToString(int type) {
            switch (type) {
                case 1:
                    return "moveToBack";
                default:
                    return "finish";
            }
        }

        public String toString() {
            return "{mDismissType=" + this.mDismissType + ", mDismissDelay=" + this.mDismissDelay + '}';
        }
    }
}
