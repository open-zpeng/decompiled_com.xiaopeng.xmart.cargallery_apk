package com.xiaopeng.share.task;

import com.xiaopeng.lib.framework.moduleinterface.accountmodule.AbsException;
import com.xiaopeng.share.ComponentManager;
import com.xiaopeng.share.share.ShareBuilder;
/* loaded from: classes.dex */
public class LoginProcess extends ShareProcess {
    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.xiaopeng.share.task.ShareProcess
    public boolean process(ShareBuilder shareBuilder) {
        if (ComponentManager.getInstance().getAccount() == null) {
            try {
                ComponentManager.getInstance().getAccount().login();
                return false;
            } catch (AbsException e) {
                onThrowable(e, shareBuilder);
                e.printStackTrace();
                return false;
            }
        }
        return new OssUploadProcess().process(shareBuilder);
    }
}
