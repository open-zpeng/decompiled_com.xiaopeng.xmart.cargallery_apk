package com.xiaopeng.lib.bughunter;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import com.xiaopeng.lib.bughunter.anr.Collector;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
/* loaded from: classes.dex */
public class StackTraceCollector implements Collector {
    private static final String CATON_STACK_INFO = "caton_stack_info";
    private static final int COLLECT_SPACE_TIME = 3000;
    private static final int MIN_COLLECT_COUNT = 3;
    private static final int MSG_BEGIN_WATCH = 54;
    private static final int MSG_COLLECT_CONTINUE = 55;
    private static final String TAG = "StackTraceCollector";
    private static final String THREAD_TAG = "-----";
    private long mCollectInterval;
    private volatile CollectorHandler mCollectorHandler;
    private volatile boolean mIsWatching;
    private StackTraceElement[] mLastStackTrace;
    private int mLimitLength;
    private Thread mMainThread;
    private int[] mRepeatTimes;
    private ArrayList<StackTraceElement[]> mStackQueue;

    public StackTraceCollector(long collectInterval) {
        this.mCollectInterval = collectInterval;
        HandlerThread thread = new HandlerThread(TAG);
        thread.setPriority(10);
        thread.start();
        this.mCollectorHandler = new CollectorHandler(thread.getLooper());
        this.mLimitLength = 3;
        this.mStackQueue = new ArrayList<>(this.mLimitLength);
        this.mRepeatTimes = new int[this.mLimitLength];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reset() {
        synchronized (this.mStackQueue) {
            if (!this.mStackQueue.isEmpty()) {
                this.mLastStackTrace = null;
                this.mStackQueue.clear();
                Arrays.fill(this.mRepeatTimes, 0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void increaseRepeatTimes() {
        synchronized (this.mStackQueue) {
            int currentIndex = this.mStackQueue.size() - 1;
            int[] iArr = this.mRepeatTimes;
            iArr[currentIndex] = iArr[currentIndex] + 1;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void trigger(int what) {
        Message message = this.mCollectorHandler.obtainMessage();
        message.obj = this;
        message.what = what;
        this.mCollectorHandler.sendMessageDelayed(message, this.mCollectInterval);
    }

    public boolean isWatching() {
        return this.mIsWatching;
    }

    @Override // com.xiaopeng.lib.bughunter.anr.Collector
    public void start() {
        this.mIsWatching = true;
        trigger(54);
    }

    @Override // com.xiaopeng.lib.bughunter.anr.Collector
    public void stop() {
        this.mIsWatching = false;
        this.mCollectorHandler.removeMessages(54);
        this.mCollectorHandler.removeMessages(55);
    }

    @Override // com.xiaopeng.lib.bughunter.anr.Collector
    public int[] getStackTraceRepeats() {
        int[] repeats;
        synchronized (this.mStackQueue) {
            int[] iArr = this.mRepeatTimes;
            repeats = Arrays.copyOf(iArr, iArr.length);
        }
        return repeats;
    }

    @Override // com.xiaopeng.lib.bughunter.anr.Collector
    public StackTraceElement[][] getStackTraceInfo() {
        StackTraceElement[][] stackTraceElementArr;
        synchronized (this.mStackQueue) {
            stackTraceElementArr = (StackTraceElement[][]) this.mStackQueue.toArray((StackTraceElement[][]) Array.newInstance(StackTraceElement.class, 0, 0));
        }
        return stackTraceElementArr;
    }

    @Override // com.xiaopeng.lib.bughunter.anr.Collector
    public void add(StackTraceElement[] stackTrace) {
        synchronized (this.mStackQueue) {
            this.mLastStackTrace = stackTrace;
            int size = this.mStackQueue.size();
            int i = this.mLimitLength;
            if (size >= i) {
                int maxIndex = i - 1;
                int removeIndex = maxIndex;
                int minRepeat = this.mRepeatTimes[removeIndex];
                for (int i2 = maxIndex - 1; i2 >= 1; i2--) {
                    int[] iArr = this.mRepeatTimes;
                    if (minRepeat > iArr[i2]) {
                        removeIndex = i2;
                        minRepeat = iArr[i2];
                    }
                }
                this.mStackQueue.remove(removeIndex);
                for (int i3 = removeIndex; i3 < maxIndex; i3++) {
                    int[] iArr2 = this.mRepeatTimes;
                    iArr2[i3] = iArr2[i3 + 1];
                }
                this.mRepeatTimes[maxIndex] = 0;
            }
            this.mStackQueue.add(stackTrace);
            this.mRepeatTimes[this.mStackQueue.size() - 1] = 1;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class CollectorHandler extends Handler {
        public CollectorHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg.what != 54 && msg.what != 55) {
                return;
            }
            if (msg.what == 54) {
                StackTraceCollector.this.reset();
            }
            StackTraceElement[] stackTraceElements = StackTraceCollector.this.getMainThreadStackInfo();
            if (StackTraceCollector.isEqualsAndNotNull(stackTraceElements, StackTraceCollector.this.mLastStackTrace)) {
                StackTraceCollector.this.increaseRepeatTimes();
            } else {
                StackTraceCollector.this.add(stackTraceElements);
            }
            if (StackTraceCollector.this.isWatching()) {
                StackTraceCollector.this.trigger(55);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public StackTraceElement[] getMainThreadStackInfo() {
        if (this.mMainThread == null) {
            this.mMainThread = Looper.getMainLooper().getThread();
        }
        return this.mMainThread.getStackTrace();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isEqualsAndNotNull(StackTraceElement[] currentStackTraceElements, StackTraceElement[] stackTraceElements) {
        if (currentStackTraceElements == null || stackTraceElements == null) {
            return false;
        }
        int currentStackTraceElementsSize = currentStackTraceElements.length;
        int stackTraceElementsSize = stackTraceElements.length;
        if (currentStackTraceElementsSize != stackTraceElementsSize) {
            return false;
        }
        for (int i = 0; i < currentStackTraceElementsSize; i++) {
            if (!currentStackTraceElements[i].equals(stackTraceElements[i])) {
                return false;
            }
        }
        return true;
    }
}
