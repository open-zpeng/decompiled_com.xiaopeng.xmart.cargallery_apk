package com.xiaopeng.share.task;

import android.text.TextUtils;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import com.google.gson.Gson;
import com.lzy.okgo.cache.CacheEntity;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.Callback;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IHttp;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IRequest;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IResponse;
import com.xiaopeng.lib.framework.netchannelmodule.common.TrafficeStaFlagInterceptor;
import com.xiaopeng.lib.framework.netchannelmodule.http.xmart.bizapi.BizConstants;
import com.xiaopeng.lib.http.server.ServerBean;
import com.xiaopeng.lib.utils.config.CommonConfig;
import com.xiaopeng.share.ComponentManager;
import com.xiaopeng.share.bean.ShareContentBean;
import com.xiaopeng.share.callback.ShareCallback;
import com.xiaopeng.share.encode.NoneSecurityUtils;
import com.xiaopeng.share.share.ShareBuilder;
import com.xiaopeng.share.share.ShareProcedure;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONObject;
/* loaded from: classes.dex */
public class PublishProcess extends ShareProcess {
    private static final String SHARE_URL_DEBUG = CommonConfig.HTTP_HOST + "/bbs/vehicle/v1/thread/post";
    private static final String TAG = "PublishProcess";

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.xiaopeng.share.task.ShareProcess
    public boolean process(ShareBuilder shareBuilder) {
        long uid = Long.parseLong(shareBuilder.getUid());
        if (TextUtils.isEmpty(shareBuilder.getOtp()) || TextUtils.isEmpty(shareBuilder.getUid())) {
            onError(0, ShareCallback.BUILD_USER_ERROR_MESSAGE, shareBuilder);
            return false;
        }
        publish(uid, shareBuilder.getOtp(), shareBuilder);
        return true;
    }

    private void publish(long uid, String otp, final ShareBuilder shareBuilder) {
        ShareContentBean shareBean = generateShareContent(uid, shareBuilder);
        String shareString = new Gson().toJson(shareBean);
        Log.d(TAG, shareString);
        HashMap<String, String> param = new HashMap<>();
        param.put("otp", otp);
        IHttp http = ComponentManager.getInstance().getHttp();
        http.config().addInterceptor(new TrafficeStaFlagInterceptor()).apply();
        IRequest request = http.bizHelper().post(SHARE_URL_DEBUG, shareString).appId(ShareProcedure.APP_ID).uid(uid + "").needAuthorizationInfo(param).buildWithSecretKey(ShareProcedure.APP_SECRET).headers(BizConstants.HEADER_ENCRYPTION_TYPE, "3");
        request.headers(BizConstants.HEADER_AUTHORIZATION, NoneSecurityUtils.getAuthorization(request, "POST", shareString, ShareProcedure.APP_SECRET, otp));
        request.headers(BizConstants.HEADER_SIGNATURE, NoneSecurityUtils.getSignature(request, "POST", shareString, ShareProcedure.APP_SECRET));
        request.execute(new Callback() { // from class: com.xiaopeng.share.task.PublishProcess.1
            @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.Callback
            public void onSuccess(IResponse iResponse) {
                ServerBean bean = PublishProcess.this.getServerBean(iResponse);
                if (bean == null) {
                    PublishProcess.this.onError(66, ShareCallback.PUBLISH_RESPONSE_ERROR_MESSAGE, shareBuilder);
                } else if (TextUtils.isEmpty(bean.getData()) || "null".equals(bean.getData())) {
                    PublishProcess.this.onError(66, bean.getMsg(), shareBuilder);
                } else if (iResponse.code() == 200) {
                    Log.d(PublishProcess.TAG, "onSuccess: " + iResponse.body());
                    ShareBuilder shareBuilder2 = shareBuilder;
                    if (shareBuilder2 != null && shareBuilder2.getCallback() != null) {
                        shareBuilder.getCallback().onSuccess(bean.getData());
                    }
                }
            }

            @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.Callback
            public void onFailure(IResponse iResponse) {
                PublishProcess.this.onError(65, ShareCallback.PUBLISH_FAILURE_MESSAGE, shareBuilder);
                Log.d(PublishProcess.TAG, "iResponse: " + iResponse);
            }
        });
    }

    private ShareContentBean generateShareContent(long uid, ShareBuilder shareBuilder) {
        int type = shareBuilder.getShareType();
        switch (type) {
            case 0:
                return ShareContentBean.buildTextShare(uid, shareBuilder.getContent());
            case 1:
                List<String> imgList = new ArrayList<>();
                HashMap<String, String> map = shareBuilder.getOssMap();
                for (String key : map.keySet()) {
                    imgList.add(map.get(key));
                }
                return ShareContentBean.buildImageShare(uid, shareBuilder.getContent(), imgList);
            case 2:
                String content = shareBuilder.getContent();
                String videoCoverUrl = shareBuilder.getVideoCoverUrl();
                String videoPath = shareBuilder.getVideoUrl();
                int height = shareBuilder.getVideoHeight();
                int width = shareBuilder.getVideoWidth();
                return ShareContentBean.buildVideoShare(uid, content, videoCoverUrl, videoPath, width, height);
            default:
                return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ServerBean getServerBean(IResponse response) {
        try {
            JSONObject jsonObject = new JSONObject(response.body());
            ServerBean bean = new ServerBean();
            bean.setCode(jsonObject.getInt("code"));
            bean.setData(jsonObject.getString(CacheEntity.DATA));
            try {
                bean.setMsg(jsonObject.getString(NotificationCompat.CATEGORY_MESSAGE));
                return bean;
            } catch (Exception ex) {
                ex.printStackTrace();
                return bean;
            }
        } catch (Exception ex2) {
            ex2.printStackTrace();
            return null;
        }
    }
}
