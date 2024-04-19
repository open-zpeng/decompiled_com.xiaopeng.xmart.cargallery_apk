package com.xiaopeng.share.task;

import com.xiaopeng.share.share.ShareBuilder;
/* loaded from: classes.dex */
public abstract class ShareProcess {
    protected ShareProcess nextProcess;

    abstract boolean process(ShareBuilder shareBuilder);

    public void onNext(ShareProcess nextProcess) {
        this.nextProcess = nextProcess;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onError(int code, String reason, ShareBuilder shareBuilder) {
        if (shareBuilder != null && shareBuilder.getCallback() != null) {
            shareBuilder.getCallback().onError(code, reason);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onThrowable(Throwable throwable, ShareBuilder shareBuilder) {
        if (shareBuilder != null && shareBuilder.getCallback() != null) {
            shareBuilder.getCallback().onException(throwable);
        }
    }
}
