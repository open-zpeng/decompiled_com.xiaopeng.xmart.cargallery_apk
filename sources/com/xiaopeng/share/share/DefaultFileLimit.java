package com.xiaopeng.share.share;

import com.xiaopeng.share.callback.FileLimitStrategy;
/* loaded from: classes.dex */
class DefaultFileLimit implements FileLimitStrategy {
    @Override // com.xiaopeng.share.callback.FileLimitStrategy
    public int imageMaxAmount() {
        return 9;
    }

    @Override // com.xiaopeng.share.callback.FileLimitStrategy
    public int videoMaxSize() {
        return 31457280;
    }

    @Override // com.xiaopeng.share.callback.FileLimitStrategy
    public int videoMaxDuration() {
        return 30000;
    }
}
