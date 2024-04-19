package com.xiaopeng.lib.bughunter.anr;

import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.os.SystemClock;
import android.util.Log;
import android.util.Printer;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
/* loaded from: classes.dex */
public class UILooperObserver implements Printer {
    public static final long ANR_TRIGGER_TIME = 5000;
    private static final String FIELD_MEMBER_QUEUE = "mQueue";
    private static final String LOG_BEGIN = ">>>>> Dispatching to";
    private static final String LOG_END = "<<<<< Finished to";
    private static final String METHOD_NEXT = "next";
    private static final String METHOD_RECYCLE_UNCHECKED = "recycleUnchecked";
    private static final String TAG = "UILooperObserver";
    private BlockHandler mBlockHandler;
    private long mPreMessageTime = 0;
    private long mPreThreadTime = 0;

    public UILooperObserver(BlockHandler blockHandler) {
        this.mBlockHandler = blockHandler;
        hookMsgDispatchOfLooper();
    }

    private void hookMsgDispatchOfLooper() {
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.xiaopeng.lib.bughunter.anr.UILooperObserver.1
            @Override // java.lang.Runnable
            public void run() {
                Looper mainLooper = Looper.getMainLooper();
                MessageQueue queue = null;
                Method methodNext = null;
                try {
                    Field fieldQueue = mainLooper.getClass().getDeclaredField(UILooperObserver.FIELD_MEMBER_QUEUE);
                    fieldQueue.setAccessible(true);
                    queue = (MessageQueue) fieldQueue.get(mainLooper);
                    methodNext = queue.getClass().getDeclaredMethod(UILooperObserver.METHOD_NEXT, new Class[0]);
                    methodNext.setAccessible(true);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e2) {
                    e2.printStackTrace();
                } catch (NoSuchMethodException e3) {
                    e3.printStackTrace();
                }
                Binder.clearCallingIdentity();
                boolean callReflectRecycledMethod = false;
                Method methodRecycleUnchecked = null;
                if (Build.VERSION.SDK_INT >= 21) {
                    callReflectRecycledMethod = true;
                    Message tmpMsg = Message.obtain();
                    try {
                        methodRecycleUnchecked = tmpMsg.getClass().getDeclaredMethod(UILooperObserver.METHOD_RECYCLE_UNCHECKED, new Class[0]);
                    } catch (NoSuchMethodException e4) {
                        e4.printStackTrace();
                    }
                    methodRecycleUnchecked.setAccessible(true);
                    tmpMsg.recycle();
                }
                while (true) {
                    try {
                        Message msg = (Message) methodNext.invoke(queue, new Object[0]);
                        if (msg != null) {
                            UILooperObserver.this.beginDispatchMsg();
                            msg.getTarget().dispatchMessage(msg);
                            if (callReflectRecycledMethod) {
                                methodRecycleUnchecked.invoke(msg, new Object[0]);
                            } else {
                                msg.recycle();
                            }
                            UILooperObserver.this.endDispatchMsg();
                        } else {
                            return;
                        }
                    } catch (IllegalAccessException e5) {
                        e5.printStackTrace();
                        Log.e(UILooperObserver.TAG, "lib_Bughunter IllegalAccessException");
                        return;
                    } catch (InvocationTargetException e6) {
                        e6.printStackTrace();
                        Log.e(UILooperObserver.TAG, "lib_Bughunter InvocationTargetException");
                        return;
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void beginDispatchMsg() {
        this.mPreMessageTime = SystemClock.uptimeMillis();
        this.mPreThreadTime = SystemClock.currentThreadTimeMillis();
        this.mBlockHandler.startMonitor();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void endDispatchMsg() {
        if (this.mPreMessageTime != 0) {
            long messageElapseTime = SystemClock.uptimeMillis() - this.mPreMessageTime;
            long threadElapseTime = SystemClock.currentThreadTimeMillis() - this.mPreThreadTime;
            this.mBlockHandler.stopMonitor();
            if (messageElapseTime > Config.THRESHOLD_TIME) {
                this.mBlockHandler.notifyBlockOccurs(messageElapseTime >= ANR_TRIGGER_TIME, messageElapseTime, threadElapseTime);
            }
        }
    }

    @Override // android.util.Printer
    public void println(String x) {
        if (x.startsWith(LOG_BEGIN)) {
            beginDispatchMsg();
        } else if (x.startsWith(LOG_END)) {
            endDispatchMsg();
        }
    }
}
