package com.xiaopeng.share.share;

import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.share.task.FilePreCheckProcess;
/* loaded from: classes.dex */
public class ShareTask implements Runnable {
    private static final String TAG = "ShareTask";
    private final ShareBuilder shareBuilder;

    public ShareTask(ShareBuilder shareBuilder) {
        this.shareBuilder = shareBuilder;
    }

    @Override // java.lang.Runnable
    public void run() {
        LogUtils.d(TAG, "share task begin!");
        FilePreCheckProcess filePreCheckProcess = new FilePreCheckProcess();
        filePreCheckProcess.process(this.shareBuilder);
        LogUtils.d(TAG, "share task end!");
    }
}
