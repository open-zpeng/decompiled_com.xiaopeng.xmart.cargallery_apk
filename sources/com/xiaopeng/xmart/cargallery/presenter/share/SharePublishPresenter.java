package com.xiaopeng.xmart.cargallery.presenter.share;

import android.accounts.Account;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import com.xiaopeng.lib.utils.config.CommonConfig;
import com.xiaopeng.share.callback.OssProgressCallback;
import com.xiaopeng.share.callback.ShareCallback;
import com.xiaopeng.share.share.ShareBuilder;
import com.xiaopeng.xmart.cargallery.CameraLog;
import com.xiaopeng.xmart.cargallery.bean.BaseItem;
import com.xiaopeng.xmart.cargallery.bean.ShareResultBean;
import com.xiaopeng.xmart.cargallery.helper.ThreadPoolHelper;
import com.xiaopeng.xmart.cargallery.manager.LoginManager;
import com.xiaopeng.xmart.cargallery.presenter.share.SharePublishPresenter;
import com.xiaopeng.xmart.cargallery.utils.GsonUtils;
import com.xiaopeng.xmart.cargallery.view.share.ISharePublishView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes8.dex */
public class SharePublishPresenter {
    private static final String QR_CODE_URL_PREFIX = "https://bbs.xiaopeng.com/v2/landing/bbsDetail.html?tid=";
    private static final String QR_CODE_URL_PREFIX_ENG = "http://bbs.xiaopeng.local:8701/bbsDetail.html?tid=";
    private static final String TAG = SharePublishPresenter.class.getSimpleName();
    private final ShareCallback mShareCallback;
    private final OssProgressCallback mShareProgress;
    private final ISharePublishView mView;

    public SharePublishPresenter(final ISharePublishView mView) {
        this.mView = mView;
        this.mShareProgress = new OssProgressCallback() { // from class: com.xiaopeng.xmart.cargallery.presenter.share.-$$Lambda$SharePublishPresenter$WYfJ6KFrG91dWb5qb5jBkCFo55U
            @Override // com.xiaopeng.share.callback.OssProgressCallback
            public final void ossProgress(int i, int i2, String str) {
                ISharePublishView.this.uploadProgress(i, i2, str);
            }
        };
        this.mShareCallback = new AnonymousClass1(mView);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.xmart.cargallery.presenter.share.SharePublishPresenter$1  reason: invalid class name */
    /* loaded from: classes8.dex */
    public class AnonymousClass1 implements ShareCallback {
        final /* synthetic */ ISharePublishView val$mView;

        AnonymousClass1(final ISharePublishView val$mView) {
            this.val$mView = val$mView;
        }

        @Override // com.xiaopeng.share.callback.ShareCallback
        public void onError(final int code, final String reason) {
            ThreadPoolHelper threadPoolHelper = ThreadPoolHelper.getInstance();
            final ISharePublishView iSharePublishView = this.val$mView;
            threadPoolHelper.postOnMainThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.presenter.share.-$$Lambda$SharePublishPresenter$1$KuQO9xCfF-f7rBdXlpQw5jok60o
                @Override // java.lang.Runnable
                public final void run() {
                    ISharePublishView.this.shareOnError(code, reason);
                }
            });
        }

        @Override // com.xiaopeng.share.callback.ShareCallback
        public void onException(final Throwable throwable) {
            ThreadPoolHelper threadPoolHelper = ThreadPoolHelper.getInstance();
            final ISharePublishView iSharePublishView = this.val$mView;
            threadPoolHelper.postOnMainThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.presenter.share.-$$Lambda$SharePublishPresenter$1$x_taH23BM3hooow0XWdhANwcUsQ
                @Override // java.lang.Runnable
                public final void run() {
                    ISharePublishView.this.shareOnException(throwable.getMessage());
                }
            });
        }

        @Override // com.xiaopeng.share.callback.ShareCallback
        public void onSuccess(String result) {
            final ShareResultBean bean = (ShareResultBean) GsonUtils.convertString2Object(result, ShareResultBean.class);
            if (bean == null) {
                CameraLog.d(SharePublishPresenter.TAG, "Convert the result error!" + result, false);
                ThreadPoolHelper threadPoolHelper = ThreadPoolHelper.getInstance();
                final ISharePublishView iSharePublishView = this.val$mView;
                threadPoolHelper.postOnMainThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.presenter.share.-$$Lambda$SharePublishPresenter$1$mnxtTc1f6U9_SppipGxb5A1s_pE
                    @Override // java.lang.Runnable
                    public final void run() {
                        ISharePublishView.this.shareOnError(-1, "the result format is illegal.");
                    }
                });
                return;
            }
            ThreadPoolHelper threadPoolHelper2 = ThreadPoolHelper.getInstance();
            final ISharePublishView iSharePublishView2 = this.val$mView;
            threadPoolHelper2.postOnMainThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.presenter.share.-$$Lambda$SharePublishPresenter$1$JnVvXqergdOfn5XxspINLuhqvHs
                @Override // java.lang.Runnable
                public final void run() {
                    SharePublishPresenter.AnonymousClass1.this.lambda$onSuccess$3$SharePublishPresenter$1(iSharePublishView2, bean);
                }
            });
        }

        public /* synthetic */ void lambda$onSuccess$3$SharePublishPresenter$1(final ISharePublishView mView, final ShareResultBean bean) {
            mView.shareOnSuccess(SharePublishPresenter.this.getQrCodeUrlPrefix() + bean.getThreadId());
        }
    }

    public boolean isLogin() {
        return LoginManager.getInstance().isLogin();
    }

    @Deprecated
    public void getAuthority() {
        String str;
        Account[] accounts = LoginManager.getInstance().getmAccountManager().getAccountsByType(LoginManager.ACCOUNT_TYPE_XP_VEHICLE);
        if (accounts.length > 0) {
            String token = null;
            String uid = LoginManager.getInstance().getmAccountManager().getUserData(accounts[0], "uid");
            Bundle bundle = new Bundle();
            bundle.putString(LoginManager.AUTH_INFO_EXTRA_APP_ID, LoginManager.APP_ID);
            AccountManagerFuture<Bundle> future = LoginManager.getInstance().getmAccountManager().getAuthToken(accounts[0], LoginManager.AUTH_TYPE_AUTH_OTP, bundle, false, (AccountManagerCallback<Bundle>) null, (Handler) null);
            try {
                try {
                    token = future.getResult().getString("authtoken");
                    str = TAG;
                    CameraLog.d(str, "fetch the otp and uid otp = " + token + " uid = " + uid, false);
                    this.mView.fetchOtp(token, uid);
                } catch (Throwable th) {
                    if (TextUtils.isEmpty(null) || TextUtils.isEmpty(uid)) {
                        CameraLog.d(TAG, "otp or uid is null", false);
                        this.mView.fetchOtpError();
                    }
                    throw th;
                }
            } catch (AuthenticatorException | OperationCanceledException | IOException e) {
                e.printStackTrace();
                String str2 = TAG;
                CameraLog.d(str2, "fetch otp and uid failure.", false);
                this.mView.fetchOtpException(e.getMessage());
                if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(uid)) {
                    return;
                }
                CameraLog.d(str2, "otp or uid is null", false);
            }
            if (TextUtils.isEmpty(token) || TextUtils.isEmpty(uid)) {
                CameraLog.d(str, "otp or uid is null", false);
                this.mView.fetchOtpError();
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x00ee  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00fb  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x011e  */
    /* JADX WARN: Removed duplicated region for block: B:59:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:60:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void publishAndGetAuthority(java.util.List<com.xiaopeng.xmart.cargallery.bean.BaseItem> r17, java.lang.String r18) {
        /*
            Method dump skipped, instructions count: 304
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.xmart.cargallery.presenter.share.SharePublishPresenter.publishAndGetAuthority(java.util.List, java.lang.String):void");
    }

    public /* synthetic */ void lambda$publishAndGetAuthority$1$SharePublishPresenter(final Exception e) {
        this.mView.fetchOtpException(e.getMessage());
    }

    public /* synthetic */ void lambda$publishAndGetAuthority$2$SharePublishPresenter() {
        this.mView.fetchOtpError();
    }

    public void publish(String otp, String uid, List<BaseItem> shareFileList, String shareContent) {
        if (shareFileList == null || shareFileList.size() == 0 || TextUtils.isEmpty(shareContent)) {
            ThreadPoolHelper.getInstance().postOnMainThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.presenter.share.-$$Lambda$SharePublishPresenter$F2K_orlrbk5qhqdgtqGQwzoxOn4
                @Override // java.lang.Runnable
                public final void run() {
                    SharePublishPresenter.this.lambda$publish$3$SharePublishPresenter();
                }
            });
        } else if (shareFileList.get(0).getType() == 2) {
            ShareBuilder.create(2, this.mShareCallback, otp, uid).buildContent(shareContent).buildVideoPath(shareFileList.get(0).getPath()).share();
        } else {
            List<String> imgs = new ArrayList<>();
            for (int i = 0; i < shareFileList.size(); i++) {
                imgs.add(shareFileList.get(i).getPath());
            }
            ShareBuilder.create(1, this.mShareCallback, otp, uid).buildContent(shareContent).buildImagesPath(imgs).needProgress(this.mShareProgress).share();
            CameraLog.d(TAG, "publish end", false);
        }
    }

    public /* synthetic */ void lambda$publish$3$SharePublishPresenter() {
        this.mView.shareFileIllegal();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getQrCodeUrlPrefix() {
        if (CommonConfig.HTTP_HOST.startsWith("https://xmart.xiaopeng.com")) {
            return QR_CODE_URL_PREFIX;
        }
        return QR_CODE_URL_PREFIX_ENG;
    }
}
