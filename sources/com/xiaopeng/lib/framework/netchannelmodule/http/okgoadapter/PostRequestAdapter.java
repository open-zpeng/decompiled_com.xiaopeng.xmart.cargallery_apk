package com.xiaopeng.lib.framework.netchannelmodule.http.okgoadapter;

import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.Callback;
/* loaded from: classes.dex */
public class PostRequestAdapter extends BasePostRequestAdapter<String> {
    public PostRequestAdapter(String url) {
        super(url);
    }

    @Override // com.xiaopeng.lib.framework.netchannelmodule.http.okgoadapter.BasePostRequestAdapter, com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IRequest
    public void execute(Callback callback) {
        super.execute(new StringCallbackAdapter(callback));
    }
}
