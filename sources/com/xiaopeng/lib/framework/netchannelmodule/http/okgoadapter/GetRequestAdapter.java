package com.xiaopeng.lib.framework.netchannelmodule.http.okgoadapter;

import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.Callback;
/* loaded from: classes.dex */
public class GetRequestAdapter extends BaseGetRequestAdapter<String> {
    public GetRequestAdapter(String url) {
        super(url);
    }

    @Override // com.xiaopeng.lib.framework.netchannelmodule.http.okgoadapter.BaseGetRequestAdapter, com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IRequest
    public void execute(Callback callback) {
        super.execute(new StringCallbackAdapter(callback));
    }
}
