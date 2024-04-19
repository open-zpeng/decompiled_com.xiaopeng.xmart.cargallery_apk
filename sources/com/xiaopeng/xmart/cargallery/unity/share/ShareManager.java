package com.xiaopeng.xmart.cargallery.unity.share;

import com.xiaopeng.xmart.cargallery.CameraLog;
import com.xiaopeng.xmart.cargallery.bean.BIConfig;
import com.xiaopeng.xmart.cargallery.bean.BaseItem;
import com.xiaopeng.xmart.cargallery.helper.BIHelper;
import com.xiaopeng.xmart.cargallery.helper.ThreadPoolHelper;
import com.xiaopeng.xmart.cargallery.presenter.share.ShareEditPresenter;
import com.xiaopeng.xmart.cargallery.presenter.share.SharePublishPresenter;
import com.xiaopeng.xmart.cargallery.unity.app.GalleryAppManager;
import com.xiaopeng.xmart.cargallery.utils.FileUtils;
import com.xiaopeng.xmart.cargallery.view.share.ISharePublishView;
import com.xiaopeng.xmart.cargallery.view.share.edit.IShareEditView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/* loaded from: classes2.dex */
public class ShareManager extends GalleryAppManager implements ISharePublishView, IShareEditView {
    private static final String TAG = ShareManager.class.getSimpleName();
    private final ShareEditPresenter mEditPresenter;
    private IShareListener mListener;
    private final SharePublishPresenter mPresenter;
    private List<BaseItem> mShareFileList;

    /* loaded from: classes2.dex */
    public static class SingleHolder {
        public static ShareManager instance = new ShareManager();
    }

    private ShareManager() {
        this.mPresenter = new SharePublishPresenter(this);
        ShareEditPresenter shareEditPresenter = new ShareEditPresenter(this);
        this.mEditPresenter = shareEditPresenter;
        shareEditPresenter.initRecord();
    }

    public static ShareManager get_instance() {
        return SingleHolder.instance;
    }

    public void set_listener(IShareListener listener) {
        this.mListener = listener;
    }

    @Override // com.xiaopeng.xmart.cargallery.unity.app.GalleryAppManager, com.xiaopeng.xmart.cargallery.unity.UnityBaseManager
    public void enterScene() {
        this.mEditPresenter.initRecord();
        CameraLog.i(TAG, "enterScene: ", false);
    }

    @Override // com.xiaopeng.xmart.cargallery.unity.app.GalleryAppManager, com.xiaopeng.xmart.cargallery.unity.UnityBaseManager
    public void exitScene() {
        this.mEditPresenter.releaseRecord();
        CameraLog.i(TAG, "exitScene: ", false);
    }

    public void startRecord() {
        CameraLog.i(TAG, "startRecord...", false);
        this.mEditPresenter.startRecord();
    }

    public void stopRecord() {
        CameraLog.i(TAG, "stopRecord...", false);
        this.mEditPresenter.stopRecordByUser();
    }

    public boolean isUserLogin() {
        boolean login = this.mPresenter.isLogin();
        CameraLog.i(TAG, "isUserLogin: " + login, false);
        return login;
    }

    @Deprecated
    public void getAuthority() {
        CameraLog.i(TAG, "getAuthority: ", false);
        ThreadPoolHelper.getInstance().execute(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.unity.share.-$$Lambda$ShareManager$d8lCCNAnaUillonb_Cqpy50lQdU
            @Override // java.lang.Runnable
            public final void run() {
                ShareManager.this.lambda$getAuthority$0$ShareManager();
            }
        });
    }

    public /* synthetic */ void lambda$getAuthority$0$ShareManager() {
        this.mPresenter.getAuthority();
    }

    public void shareFile(final List<BaseItem> shareFileList, final String shareContent) {
        CameraLog.i(TAG, shareFileList + "shareContent:" + shareContent, false);
        if (this.mShareFileList == null) {
            this.mShareFileList = new ArrayList();
        }
        this.mShareFileList.clear();
        this.mShareFileList.addAll(shareFileList);
        ThreadPoolHelper.getInstance().execute(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.unity.share.-$$Lambda$ShareManager$OpS5NFZJZtbbZA4d7mMRGx9ilF8
            @Override // java.lang.Runnable
            public final void run() {
                ShareManager.this.lambda$shareFile$1$ShareManager(shareFileList, shareContent);
            }
        });
    }

    public /* synthetic */ void lambda$shareFile$1$ShareManager(final List shareFileList, final String shareContent) {
        this.mPresenter.publishAndGetAuthority(shareFileList, shareContent);
    }

    @Override // com.xiaopeng.xmart.cargallery.view.share.ISharePublishView
    public void shareFileIllegal() {
        CameraLog.i(TAG, "shareFileIllegal: ", false);
        ThreadPoolHelper.getInstance().postOnMainThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.unity.share.-$$Lambda$ShareManager$f5kVaiqOpDYQm5R8zl4QB1WbY4g
            @Override // java.lang.Runnable
            public final void run() {
                ShareManager.this.lambda$shareFileIllegal$2$ShareManager();
            }
        });
    }

    public /* synthetic */ void lambda$shareFileIllegal$2$ShareManager() {
        IShareListener iShareListener = this.mListener;
        if (iShareListener != null) {
            iShareListener.shareFileIllegal();
        }
    }

    @Override // com.xiaopeng.xmart.cargallery.view.share.ISharePublishView
    public void uploadProgress(final int progress, final int total, final String fileName) {
        CameraLog.i(TAG, "uploadProgress progress: " + progress + " total: " + total + " fileName: " + fileName, false);
        ThreadPoolHelper.getInstance().postOnMainThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.unity.share.-$$Lambda$ShareManager$zYAhzC7DOEudi6DTGOlTguaH2Gk
            @Override // java.lang.Runnable
            public final void run() {
                ShareManager.this.lambda$uploadProgress$3$ShareManager(progress, total, fileName);
            }
        });
    }

    public /* synthetic */ void lambda$uploadProgress$3$ShareManager(final int progress, final int total, final String fileName) {
        IShareListener iShareListener = this.mListener;
        if (iShareListener != null) {
            iShareListener.uploadProgress(progress, total, fileName);
        }
    }

    @Override // com.xiaopeng.xmart.cargallery.view.share.ISharePublishView
    public void shareOnSuccess(final String result) {
        CameraLog.i(TAG, "shareOnSuccess: " + result, false);
        ThreadPoolHelper.getInstance().postOnMainThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.unity.share.-$$Lambda$ShareManager$seT6nL9J1xofKdZUOR-V_azFceg
            @Override // java.lang.Runnable
            public final void run() {
                ShareManager.this.lambda$shareOnSuccess$4$ShareManager(result);
            }
        });
        uploadPublishBI(true);
    }

    public /* synthetic */ void lambda$shareOnSuccess$4$ShareManager(final String result) {
        IShareListener iShareListener = this.mListener;
        if (iShareListener != null) {
            iShareListener.shareOnSuccess(result);
        }
    }

    @Override // com.xiaopeng.xmart.cargallery.view.share.ISharePublishView
    public void shareOnException(final String message) {
        CameraLog.i(TAG, "shareOnException: " + message, false);
        ThreadPoolHelper.getInstance().postOnMainThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.unity.share.-$$Lambda$ShareManager$_BK-SfckTZa0DtPElTvUgjm4Ges
            @Override // java.lang.Runnable
            public final void run() {
                ShareManager.this.lambda$shareOnException$5$ShareManager(message);
            }
        });
        uploadPublishBI(false);
    }

    public /* synthetic */ void lambda$shareOnException$5$ShareManager(final String message) {
        IShareListener iShareListener = this.mListener;
        if (iShareListener != null) {
            iShareListener.shareOnException(message);
        }
    }

    @Override // com.xiaopeng.xmart.cargallery.view.share.ISharePublishView
    public void shareOnError(final int code, final String reason) {
        CameraLog.i(TAG, "shareOnError code: " + code + " reason: " + reason, false);
        ThreadPoolHelper.getInstance().postOnMainThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.unity.share.-$$Lambda$ShareManager$pHFimTokfa0HLrrT-MMuFc-1KgM
            @Override // java.lang.Runnable
            public final void run() {
                ShareManager.this.lambda$shareOnError$6$ShareManager(code, reason);
            }
        });
        uploadPublishBI(false);
    }

    public /* synthetic */ void lambda$shareOnError$6$ShareManager(final int code, final String reason) {
        IShareListener iShareListener = this.mListener;
        if (iShareListener != null) {
            iShareListener.shareOnError(code, reason);
        }
    }

    @Override // com.xiaopeng.xmart.cargallery.view.share.ISharePublishView
    public void fetchOtp(final String token, final String uid) {
        CameraLog.i(TAG, "fetchOtp token: " + token + " uid: " + uid, false);
        ThreadPoolHelper.getInstance().postOnMainThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.unity.share.-$$Lambda$ShareManager$p0H0cAdA1IP2-2-JHL9u4es-qJ4
            @Override // java.lang.Runnable
            public final void run() {
                ShareManager.this.lambda$fetchOtp$7$ShareManager(token, uid);
            }
        });
    }

    public /* synthetic */ void lambda$fetchOtp$7$ShareManager(final String token, final String uid) {
        IShareListener iShareListener = this.mListener;
        if (iShareListener != null) {
            iShareListener.fetchOtp(token, uid);
        }
    }

    @Override // com.xiaopeng.xmart.cargallery.view.share.ISharePublishView
    public void fetchOtpError() {
        CameraLog.i(TAG, "fetchOtpError: ", false);
        ThreadPoolHelper.getInstance().postOnMainThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.unity.share.-$$Lambda$ShareManager$h69z1sFMM5vN_n5mKY6e2ap7d0E
            @Override // java.lang.Runnable
            public final void run() {
                ShareManager.this.lambda$fetchOtpError$8$ShareManager();
            }
        });
    }

    public /* synthetic */ void lambda$fetchOtpError$8$ShareManager() {
        IShareListener iShareListener = this.mListener;
        if (iShareListener != null) {
            iShareListener.fetchOtpError();
        }
    }

    @Override // com.xiaopeng.xmart.cargallery.view.share.ISharePublishView
    public void fetchOtpException(final String message) {
        CameraLog.i(TAG, "fetchOtpException: " + message, false);
        ThreadPoolHelper.getInstance().postOnMainThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.unity.share.-$$Lambda$ShareManager$7CplQfhLED0DFmyf2AOp3APR00g
            @Override // java.lang.Runnable
            public final void run() {
                ShareManager.this.lambda$fetchOtpException$9$ShareManager(message);
            }
        });
    }

    public /* synthetic */ void lambda$fetchOtpException$9$ShareManager(final String message) {
        IShareListener iShareListener = this.mListener;
        if (iShareListener != null) {
            iShareListener.fetchOtpException(message);
        }
    }

    @Override // com.xiaopeng.xmart.cargallery.view.share.edit.IShareEditView
    public void parseTextResult(final String text) {
        CameraLog.i(TAG, "parseTextResult,text: " + text, false);
        ThreadPoolHelper.getInstance().postOnMainThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.unity.share.-$$Lambda$ShareManager$VDKcLAygNEY4I1IowyWMJXHQuds
            @Override // java.lang.Runnable
            public final void run() {
                ShareManager.this.lambda$parseTextResult$10$ShareManager(text);
            }
        });
    }

    public /* synthetic */ void lambda$parseTextResult$10$ShareManager(final String text) {
        IShareListener iShareListener = this.mListener;
        if (iShareListener != null) {
            iShareListener.parseTextResult(text);
        }
    }

    @Override // com.xiaopeng.xmart.cargallery.view.share.edit.IShareEditView
    public void onRecordEnd(final boolean error) {
        CameraLog.i(TAG, "onRecordEnd,error : " + error, false);
        ThreadPoolHelper.getInstance().postOnMainThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.unity.share.-$$Lambda$ShareManager$ArfhKqdd9NMHm-3Mu7pkvMCioa4
            @Override // java.lang.Runnable
            public final void run() {
                ShareManager.this.lambda$onRecordEnd$11$ShareManager(error);
            }
        });
    }

    public /* synthetic */ void lambda$onRecordEnd$11$ShareManager(final boolean error) {
        IShareListener iShareListener = this.mListener;
        if (iShareListener != null) {
            iShareListener.onRecordEnd(error);
        }
    }

    @Override // com.xiaopeng.xmart.cargallery.view.share.edit.IShareEditView
    public void onSpeakEnd() {
        CameraLog.i(TAG, "onSpeakEnd... ", false);
        ThreadPoolHelper.getInstance().postOnMainThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.unity.share.-$$Lambda$ShareManager$i6L4WxlGNAKakWdOPBTH81Gu7Sc
            @Override // java.lang.Runnable
            public final void run() {
                ShareManager.this.lambda$onSpeakEnd$12$ShareManager();
            }
        });
    }

    public /* synthetic */ void lambda$onSpeakEnd$12$ShareManager() {
        IShareListener iShareListener = this.mListener;
        if (iShareListener != null) {
            iShareListener.onSpeakEnd();
        }
    }

    private void uploadPublishBI(boolean success) {
        List<BaseItem> list = this.mShareFileList;
        if (list == null || list.size() == 0 || this.mShareFileList.get(0) == null) {
            return;
        }
        BaseItem item = this.mShareFileList.get(0);
        int eventType = 21;
        if ("camera_top".equals(item.getKey())) {
            eventType = 23;
        } else if ("camera_dvr".equals(item.getKey())) {
            eventType = 22;
        }
        Map<String, Number> params = new HashMap<>();
        params.put(BIConfig.PROPERTY.DATA_TYPE, 1);
        params.put("result", Integer.valueOf(success ? 1 : 0));
        BIHelper.getInstance().uploadGalleryBI(8, eventType, params);
        if (FileUtils.isExitShockFile(this.mShareFileList)) {
            BIHelper.getInstance().uploadGalleryBI(8, 24, params);
        }
    }
}
