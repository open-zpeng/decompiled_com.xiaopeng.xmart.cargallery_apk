package com.xiaopeng.lib.framework.netchannelmodule.http.okgoadapter;

import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.request.HeadRequest;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.Callback;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IRequest;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IResponse;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.xmart.IServerCallback;
import com.xiaopeng.lib.framework.netchannelmodule.common.GlobalConfig;
import com.xiaopeng.lib.framework.netchannelmodule.common.TrafficStatsEntry;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import okhttp3.Response;
/* loaded from: classes.dex */
public class HeadRequestAdapter implements IRequest {
    private HeadRequest<String> mInternalRequest;

    public HeadRequestAdapter(String url) {
        this.mInternalRequest = new HeadRequest<>(url);
        if (GlobalConfig.getApplicationContext() != null) {
            headers(GlobalConfig.PACKAGE_IDENTIFIER, GlobalConfig.getApplicationName());
        }
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IRequest
    public void execute(Callback callback) {
        this.mInternalRequest.execute(new StringCallbackAdapter(callback));
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IRequest
    public IRequest uploadJson(String json) {
        throw new RuntimeException("HEAD请求不支持POST操作!!");
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IRequest
    public IRequest uploadFile(String file) {
        throw new RuntimeException("HEAD请求不支持POST操作!!");
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IRequest
    public IResponse execute() throws IOException {
        TrafficStatsEntry.setTraficInfo();
        Response response = this.mInternalRequest.execute();
        return new ResponseAdapter(response);
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IRequest
    public void execute(IServerCallback callback) {
        throw new RuntimeException("Not supported!!!");
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IRequest
    public String getUrl() {
        return this.mInternalRequest.getUrl();
    }

    public HttpHeaders getHeaders() {
        return this.mInternalRequest.getHeaders();
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IRequest
    public IRequest headers(String key, String value) {
        this.mInternalRequest.headers(key, value);
        return this;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IRequest
    public IRequest isMultipart(boolean isMultipart) {
        throw new RuntimeException("GET请求不支持Multipart设置!!");
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IRequest
    public IRequest removeHeader(String key) {
        this.mInternalRequest.removeHeader(key);
        return this;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IRequest
    public IRequest removeAllHeaders() {
        this.mInternalRequest.removeAllHeaders();
        return this;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IRequest
    public IRequest tag(Object tag) {
        this.mInternalRequest.tag(tag);
        return this;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IRequest
    public IRequest params(Map<String, String> params, boolean... isReplace) {
        this.mInternalRequest.params(params, isReplace);
        return this;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IRequest
    public IRequest params(String key, String value) {
        this.mInternalRequest.params(key, value, new boolean[0]);
        return this;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IRequest
    public IRequest params(String key, int value) {
        this.mInternalRequest.params(key, value, new boolean[0]);
        return this;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IRequest
    public IRequest params(String key, float value) {
        this.mInternalRequest.params(key, value, new boolean[0]);
        return this;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IRequest
    public IRequest params(String key, boolean value) {
        this.mInternalRequest.params(key, value, new boolean[0]);
        return this;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IRequest
    public IRequest params(String key, File file) {
        throw new RuntimeException("GET请求不支持File参数!!");
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IRequest
    public IRequest removeParam(String key) {
        this.mInternalRequest.removeParam(key);
        return this;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IRequest
    public IRequest removeAllParams() {
        this.mInternalRequest.removeAllParams();
        return this;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IRequest
    public String header(String key) {
        return this.mInternalRequest.getHeaders().get(key);
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IRequest
    public Map<String, List<String>> headers() {
        if (this.mInternalRequest.getRequest() == null || this.mInternalRequest.getRequest().headers() == null) {
            return null;
        }
        return this.mInternalRequest.getRequest().headers().toMultimap();
    }
}
