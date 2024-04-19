package com.xiaopeng.lib.framework.netchannelmodule.http;

import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IBizHelper;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IRequest;
import com.xiaopeng.lib.framework.netchannelmodule.http.okgoadapter.GetRequestAdapter;
import com.xiaopeng.lib.framework.netchannelmodule.http.okgoadapter.PostRequestAdapter;
import com.xiaopeng.lib.framework.netchannelmodule.http.xmart.bizapi.BizConstants;
import com.xiaopeng.lib.framework.netchannelmodule.http.xmart.bizapi.BizRequestBuilder;
import java.util.Map;
/* loaded from: classes.dex */
public final class XmartBizHelper implements IBizHelper {
    private BizRequestBuilder mBizRequestBuilder = null;

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IBizHelper
    public /* bridge */ /* synthetic */ IBizHelper needAuthorizationInfo(Map map) {
        return needAuthorizationInfo((Map<String, String>) map);
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IBizHelper
    public XmartBizHelper get(String url) {
        GetRequestAdapter request = new GetRequestAdapter(url);
        this.mBizRequestBuilder = new BizRequestBuilder(request, BizConstants.METHOD.GET);
        return this;
    }

    public XmartBizHelper get(String url, Map<String, String> para) {
        GetRequestAdapter request = new GetRequestAdapter(url);
        this.mBizRequestBuilder = new BizRequestBuilder(request, BizConstants.METHOD.GET);
        request.params(para, true);
        return this;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IBizHelper
    public XmartBizHelper post(String url, String jsonBody) {
        PostRequestAdapter request = new PostRequestAdapter(url);
        BizRequestBuilder bizRequestBuilder = new BizRequestBuilder(request, BizConstants.METHOD.POST);
        this.mBizRequestBuilder = bizRequestBuilder;
        bizRequestBuilder.body(jsonBody);
        return this;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IBizHelper
    public XmartBizHelper enableIrdetoEncoding() {
        this.mBizRequestBuilder.enableIrdetoEncoding();
        return this;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IBizHelper
    public IBizHelper enableSecurityEncoding() {
        this.mBizRequestBuilder.enableSecurityEncoding();
        return this;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IBizHelper
    public XmartBizHelper needAuthorizationInfo() {
        this.mBizRequestBuilder.needAuthorizationInfo(null);
        return this;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IBizHelper
    public XmartBizHelper needAuthorizationInfo(Map<String, String> extParams) {
        this.mBizRequestBuilder.needAuthorizationInfo(extParams);
        return this;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IBizHelper
    public XmartBizHelper appId(String value) {
        this.mBizRequestBuilder.appId(value);
        return this;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IBizHelper
    public XmartBizHelper uid(String value) {
        this.mBizRequestBuilder.uid(value);
        return this;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IBizHelper
    public XmartBizHelper extendBizHeader(String header, String value) {
        this.mBizRequestBuilder.setExtHeader(header, value);
        return this;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IBizHelper
    public XmartBizHelper customTokensForAuth(String[] tokens) {
        this.mBizRequestBuilder.customTokensForAuth(tokens);
        return this;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IBizHelper
    public IRequest build() {
        return this.mBizRequestBuilder.build(null);
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IBizHelper
    public IRequest buildWithSecretKey(String secretKey) {
        return this.mBizRequestBuilder.build(secretKey);
    }
}
