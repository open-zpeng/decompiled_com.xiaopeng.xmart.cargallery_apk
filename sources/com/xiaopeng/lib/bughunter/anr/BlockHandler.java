package com.xiaopeng.lib.bughunter.anr;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Debug;
import android.text.TextUtils;
import com.xiaopeng.lib.bughunter.anr.Caton;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/* loaded from: classes.dex */
public class BlockHandler {
    private static final String MAIN_THREAD = "main";
    private static final String TAG = "BlockHandler";
    private static final String THREAD_TAG = "-----";
    private static ExecutorService mExecutorService = Executors.newFixedThreadPool(1);
    private Caton.Callback mCallback;
    private Collector mCollector;
    private Context mContext;
    private StringBuilder mStackBuilder = new StringBuilder(4096);
    private List<String> mStackInfoList = new ArrayList();

    public BlockHandler(Context context, Collector collector, Caton.Callback callback) {
        this.mContext = context;
        this.mCollector = collector;
        this.mCallback = callback;
    }

    public void notifyBlockOccurs(boolean needCheckAnr, long... blockArgs) {
        if (this.mCallback != null && !Debug.isDebuggerConnected()) {
            StackTraceElement[][] stackTraces = this.mCollector.getStackTraceInfo();
            int[] stacktraceRepeats = this.mCollector.getStackTraceRepeats();
            mExecutorService.execute(getRunnable(stackTraces, stacktraceRepeats, needCheckAnr, blockArgs));
        }
    }

    private Runnable getRunnable(final StackTraceElement[][] stackTraces, final int[] stacktraceRepeats, final boolean needCheckAnr, final long... blockArgs) {
        Runnable r = new Runnable() { // from class: com.xiaopeng.lib.bughunter.anr.BlockHandler.1
            @Override // java.lang.Runnable
            public void run() {
                String anr = "";
                if (needCheckAnr) {
                    anr = BlockHandler.this.checkAnr();
                }
                boolean isAnr = !TextUtils.isEmpty(anr);
                BlockHandler.this.mStackInfoList.clear();
                StackTraceElement[][] stackTraceElementArr = stackTraces;
                if (stackTraceElementArr != null && stackTraceElementArr.length > 0) {
                    int i = 0;
                    for (StackTraceElement[] elementArray : stackTraceElementArr) {
                        if (elementArray != null && elementArray.length > 0) {
                            if (BlockHandler.this.mStackBuilder.length() > 0) {
                                BlockHandler.this.mStackBuilder.delete(0, BlockHandler.this.mStackBuilder.length());
                            }
                            BlockHandler.this.mStackBuilder.append(BlockHandler.THREAD_TAG).append(BlockHandler.MAIN_THREAD).append(" repeat ").append(stacktraceRepeats[i]).append("\n");
                            for (StackTraceElement element : elementArray) {
                                BlockHandler.this.mStackBuilder.append("\tat ").append(element.toString()).append("\n");
                            }
                        }
                        BlockHandler.this.mStackInfoList.add(BlockHandler.this.mStackBuilder.toString());
                        i++;
                    }
                }
                String[] stackTraces2 = (String[]) BlockHandler.this.mStackInfoList.toArray(new String[0]);
                if (stackTraces2.length != 0) {
                    BlockHandler.this.mCallback.onBlockOccurs(stackTraces2, isAnr, blockArgs);
                }
            }
        };
        return r;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String checkAnr() {
        ActivityManager activityManager = (ActivityManager) this.mContext.getSystemService("activity");
        List<ActivityManager.ProcessErrorStateInfo> errorStateInfos = activityManager.getProcessesInErrorState();
        if (errorStateInfos != null) {
            for (ActivityManager.ProcessErrorStateInfo info : errorStateInfos) {
                if (info.condition == 2) {
                    StringBuilder anrInfo = new StringBuilder();
                    anrInfo.append(info.processName).append("\n").append(info.shortMsg).append("\n").append(info.longMsg);
                    Config.log(TAG, anrInfo.toString());
                    return anrInfo.toString();
                }
            }
            return "";
        }
        return "";
    }

    public void startMonitor() {
        this.mCollector.start();
    }

    public void stopMonitor() {
        this.mCollector.stop();
    }
}
