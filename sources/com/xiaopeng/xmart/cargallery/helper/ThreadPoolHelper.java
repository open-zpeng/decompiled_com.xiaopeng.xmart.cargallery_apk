package com.xiaopeng.xmart.cargallery.helper;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
/* loaded from: classes12.dex */
public class ThreadPoolHelper {
    private static final String TAG = "ThreadPoolHelper";
    private ExecutorService mFixedThreadPool;
    private Handler mMainHandler;
    private Executor mSingleDiscardOldestPool;

    private ThreadPoolHelper() {
        this.mFixedThreadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        if (Looper.myLooper() != null) {
            this.mMainHandler = new Handler(Looper.myLooper());
        } else {
            this.mMainHandler = new Handler(Looper.getMainLooper());
        }
        this.mSingleDiscardOldestPool = new ThreadPoolExecutor(1, 1, 60L, TimeUnit.SECONDS, new ArrayBlockingQueue(1), new ThreadPoolExecutor.DiscardOldestPolicy());
    }

    /* loaded from: classes12.dex */
    private static class SingletonHolder {
        private static ThreadPoolHelper INSTANCE = new ThreadPoolHelper();

        private SingletonHolder() {
        }
    }

    public static ThreadPoolHelper getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void execute(Runnable runnable) {
        this.mFixedThreadPool.execute(runnable);
    }

    public Future<?> submit(Callable<?> callable) {
        return this.mFixedThreadPool.submit(callable);
    }

    public void postOnMainThread(Runnable runnable) {
        this.mMainHandler.post(runnable);
    }

    public void postDelayedOnMainThread(Runnable runnable, long duration) {
        this.mMainHandler.postDelayed(runnable, duration);
    }

    public void removeCallbacksOnMainThread(Runnable runnable) {
        this.mMainHandler.removeCallbacks(runnable);
    }

    public void executeWhileRemoveOldest(Runnable runnable) {
        this.mSingleDiscardOldestPool.execute(runnable);
    }
}
