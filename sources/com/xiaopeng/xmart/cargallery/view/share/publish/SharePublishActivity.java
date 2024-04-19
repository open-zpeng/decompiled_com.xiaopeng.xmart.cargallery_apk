package com.xiaopeng.xmart.cargallery.view.share.publish;

import android.accounts.Account;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.xiaopeng.lib.utils.config.CommonConfig;
import com.xiaopeng.share.callback.OssProgressCallback;
import com.xiaopeng.share.callback.ShareCallback;
import com.xiaopeng.share.share.ShareBuilder;
import com.xiaopeng.xmart.cargallery.App;
import com.xiaopeng.xmart.cargallery.CameraLog;
import com.xiaopeng.xmart.cargallery.GlideApp;
import com.xiaopeng.xmart.cargallery.R;
import com.xiaopeng.xmart.cargallery.bean.BIConfig;
import com.xiaopeng.xmart.cargallery.bean.BaseItem;
import com.xiaopeng.xmart.cargallery.bean.ShareResultBean;
import com.xiaopeng.xmart.cargallery.helper.BIHelper;
import com.xiaopeng.xmart.cargallery.helper.ThreadPoolHelper;
import com.xiaopeng.xmart.cargallery.manager.LoginManager;
import com.xiaopeng.xmart.cargallery.presenter.transfer.service.TransferManagerService;
import com.xiaopeng.xmart.cargallery.utils.FileUtils;
import com.xiaopeng.xmart.cargallery.utils.GsonUtils;
import com.xiaopeng.xmart.cargallery.utils.ToastUtils;
import com.xiaopeng.xmart.cargallery.utils.ViewUtils;
import com.xiaopeng.xmart.cargallery.view.GalleryBaseActivity;
import com.xiaopeng.xmart.cargallery.view.share.publish.SharePublishActivity;
import com.xiaopeng.xmart.cargallery.view.transfer.FileTransferActivity;
import com.xiaopeng.xmart.cargallery.widget.PublishProgressView;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import com.xiaopeng.xui.theme.XThemeManager;
import com.xiaopeng.xui.widget.XImageView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
/* loaded from: classes15.dex */
public class SharePublishActivity extends GalleryBaseActivity {
    private static final String EXTRA_SHARE_CONTENT = "extra_share_content";
    private static final String EXTRA_SHARE_FILE_TEMP = "extra_share_file_temp";
    private static final int PUBLISH_RETRY_TIME = 3;
    private static final String QR_CODE_URL_PREFIX = "https://bbs.xiaopeng.com/v2/landing/bbsDetail.html?tid=";
    private static final String QR_CODE_URL_PREFIX_ENG = "http://bbs.xiaopeng.local:8701/bbsDetail.html?tid=";
    private static final int QR_IMG_SIZE = 240;
    private static final String TAG = "PublishActivity";
    private View mBgTips;
    private XDialog mErrorDialog;
    private List<BaseItem> mFileList;
    private String mId;
    private boolean mIsPublished;
    private boolean mIsTemplate;
    private XImageView mIvProgress;
    private String mOtp;
    private PublishProgressView mPublishProgressLayout;
    private ImageView mPublishQuitBtn;
    private XImageView mQrCode;
    private XImageView mQrCodeShadow;
    private int mRepeatCount;
    private XDialog mRepeatDialog;
    private TextView mScanTip;
    private String mShareContent;
    private View mTvLeaveTips;
    private TextView mTvPublishSuccess;
    private TextView mTvTipsContent;
    private TextView mTvTipsTitle;
    private TextView mTvUploadProgress;
    private TextView mTvUploading;
    private OssProgressCallback mProgressCallback = new OssProgressCallback() { // from class: com.xiaopeng.xmart.cargallery.view.share.publish.-$$Lambda$SharePublishActivity$Pt4rkpqI4kfVfGfVVi7iqvNuoFE
        @Override // com.xiaopeng.share.callback.OssProgressCallback
        public final void ossProgress(int i, int i2, String str) {
            SharePublishActivity.this.lambda$new$1$SharePublishActivity(i, i2, str);
        }
    };
    private View.OnClickListener mOnClickListener = new View.OnClickListener() { // from class: com.xiaopeng.xmart.cargallery.view.share.publish.-$$Lambda$SharePublishActivity$Yp5HMcH85qBkuCI-wJTdZSuPt2k
        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            SharePublishActivity.this.lambda$new$2$SharePublishActivity(view);
        }
    };
    private ShareCallback mCallback = new AnonymousClass1();

    static /* synthetic */ int access$1508(SharePublishActivity x0) {
        int i = x0.mRepeatCount;
        x0.mRepeatCount = i + 1;
        return i;
    }

    public /* synthetic */ void lambda$new$1$SharePublishActivity(final int progress, final int total, String s) {
        runOnUiThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.view.share.publish.-$$Lambda$SharePublishActivity$G2RH7vJrMUXDRpgCHPRcZd4T9hA
            @Override // java.lang.Runnable
            public final void run() {
                SharePublishActivity.this.lambda$null$0$SharePublishActivity(progress, total);
            }
        });
    }

    public /* synthetic */ void lambda$null$0$SharePublishActivity(final int progress, final int total) {
        this.mTvUploadProgress.setText(getString(R.string.publish_upload_progress, new Object[]{Integer.valueOf(progress), Integer.valueOf(total)}));
    }

    public /* synthetic */ void lambda$new$2$SharePublishActivity(View v) {
        switch (v.getId()) {
            case R.id.ib_close /* 2131296477 */:
                finish();
                return;
            default:
                return;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.xmart.cargallery.view.share.publish.SharePublishActivity$1  reason: invalid class name */
    /* loaded from: classes15.dex */
    public class AnonymousClass1 implements ShareCallback {
        AnonymousClass1() {
        }

        @Override // com.xiaopeng.share.callback.ShareCallback
        public void onError(final int code, final String reason) {
            CameraLog.d(SharePublishActivity.TAG, "publish onError!!! Code:" + code + "Reason: " + reason, false);
            ThreadPoolHelper.getInstance().postOnMainThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.view.share.publish.-$$Lambda$SharePublishActivity$1$zqd8wOUrZWbs4zSiXJ17HrdE1MQ
                @Override // java.lang.Runnable
                public final void run() {
                    SharePublishActivity.AnonymousClass1.this.lambda$onError$0$SharePublishActivity$1(code, reason);
                }
            });
            SharePublishActivity.this.uploadPublishBI(false);
        }

        public /* synthetic */ void lambda$onError$0$SharePublishActivity$1(final int code, final String reason) {
            if (code == 66) {
                ToastUtils.showToast(SharePublishActivity.this, reason);
            }
            SharePublishActivity.this.mPublishProgressLayout.pause();
            if (SharePublishActivity.access$1508(SharePublishActivity.this) < 3) {
                SharePublishActivity.this.showRepeatDialog();
            } else {
                SharePublishActivity.this.lambda$fetchOtp$9$SharePublishActivity();
            }
        }

        @Override // com.xiaopeng.share.callback.ShareCallback
        public void onException(Throwable throwable) {
            CameraLog.d(SharePublishActivity.TAG, "publish occurs an exception!!!" + throwable.getMessage());
            ThreadPoolHelper.getInstance().postOnMainThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.view.share.publish.-$$Lambda$SharePublishActivity$1$LFfhm2YhwyMyNt8L3A_zixfeozI
                @Override // java.lang.Runnable
                public final void run() {
                    SharePublishActivity.AnonymousClass1.this.lambda$onException$1$SharePublishActivity$1();
                }
            });
            SharePublishActivity.this.uploadPublishBI(false);
        }

        public /* synthetic */ void lambda$onException$1$SharePublishActivity$1() {
            SharePublishActivity.this.mPublishProgressLayout.pause();
            if (SharePublishActivity.access$1508(SharePublishActivity.this) < 3) {
                SharePublishActivity.this.showRepeatDialog();
            } else {
                SharePublishActivity.this.lambda$fetchOtp$9$SharePublishActivity();
            }
        }

        @Override // com.xiaopeng.share.callback.ShareCallback
        public void onSuccess(final String result) {
            CameraLog.d(SharePublishActivity.TAG, "publish success!!! result: " + result);
            final ShareResultBean bean = (ShareResultBean) GsonUtils.convertString2Object(result, ShareResultBean.class);
            if (bean == null) {
                CameraLog.d(SharePublishActivity.TAG, "Convert the result error!", false);
                return;
            }
            SharePublishActivity.this.runOnUiThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.view.share.publish.-$$Lambda$SharePublishActivity$1$7dSDvra2qS522TSaDa8PLRAGMdE
                @Override // java.lang.Runnable
                public final void run() {
                    SharePublishActivity.AnonymousClass1.this.lambda$onSuccess$2$SharePublishActivity$1(bean);
                }
            });
            SharePublishActivity.this.uploadPublishBI(true);
        }

        public /* synthetic */ void lambda$onSuccess$2$SharePublishActivity$1(final ShareResultBean bean) {
            ViewUtils.setViewsGone(SharePublishActivity.this.mPublishProgressLayout, SharePublishActivity.this.mIvProgress, SharePublishActivity.this.mBgTips, SharePublishActivity.this.mTvLeaveTips, SharePublishActivity.this.mTvTipsTitle, SharePublishActivity.this.mTvTipsContent, SharePublishActivity.this.mTvUploading, SharePublishActivity.this.mTvUploadProgress);
            Bitmap qrBitmap = SharePublishActivity.this.createQRImage(SharePublishActivity.this.getQrCodeUrlPrefix() + bean.getThreadId(), SharePublishActivity.QR_IMG_SIZE, SharePublishActivity.QR_IMG_SIZE);
            SharePublishActivity.this.mQrCode.setImageBitmap(qrBitmap);
            ViewUtils.setViewsVisible(SharePublishActivity.this.mQrCode, SharePublishActivity.this.mTvPublishSuccess, SharePublishActivity.this.mScanTip, SharePublishActivity.this.mQrCodeShadow);
        }
    }

    public static void startActivityList(Context context, String content, ArrayList<BaseItem> fileList) {
        Intent intent = new Intent(context, SharePublishActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(TransferManagerService.EXTRA_TRANSFER_FILE_PATH, fileList);
        bundle.putBoolean(FileTransferActivity.EXTRA_TRANSFER_FILE_MUL, true);
        intent.putExtra(FileTransferActivity.EXTRA_TRANSFER_BUNDLE, bundle);
        intent.putExtra(EXTRA_SHARE_CONTENT, content);
        intent.putExtra(EXTRA_SHARE_FILE_TEMP, false);
        context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xmart.cargallery.view.GalleryBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_publish);
        initDate();
        initView();
        ThreadPoolHelper.getInstance().execute(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.view.share.publish.-$$Lambda$SharePublishActivity$5Vgjha46Xb4QcFogIbIhwIc5sU8
            @Override // java.lang.Runnable
            public final void run() {
                SharePublishActivity.this.lambda$onCreate$3$SharePublishActivity();
            }
        });
    }

    private void initView() {
        ImageView imageView = (ImageView) findViewById(R.id.ib_close);
        this.mPublishQuitBtn = imageView;
        imageView.setOnClickListener(this.mOnClickListener);
        this.mBgTips = findViewById(R.id.view_bg_publish_tips);
        this.mPublishProgressLayout = (PublishProgressView) findViewById(R.id.pbv_publish_progress);
        this.mTvTipsTitle = (TextView) findViewById(R.id.publish_progress_content_tv);
        this.mTvTipsContent = (TextView) findViewById(R.id.publish_notice_content_tv);
        this.mTvLeaveTips = findViewById(R.id.tv_leave_tips);
        this.mQrCode = (XImageView) findViewById(R.id.iv_url_qr_code);
        this.mQrCodeShadow = (XImageView) findViewById(R.id.iv_qr_code_shadow);
        this.mTvPublishSuccess = (TextView) findViewById(R.id.publish_progress_success_tv);
        this.mIvProgress = (XImageView) findViewById(R.id.iv_progress_shadow);
        this.mScanTip = (TextView) findViewById(R.id.tv_scan_tips);
        this.mTvUploading = (TextView) findViewById(R.id.tv_publishing);
        TextView textView = (TextView) findViewById(R.id.tv_upload_progress);
        this.mTvUploadProgress = textView;
        textView.setText(getString(R.string.publish_upload_progress, new Object[]{0, Integer.valueOf(this.mFileList.size())}));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        super.onStop();
        if (!isFinishing()) {
            finish();
        }
        this.mPublishProgressLayout.destroy();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xmart.cargallery.view.GalleryBaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        CameraLog.d(TAG, "onDestroy");
        hideDialog(this.mRepeatDialog);
        hideDialog(this.mErrorDialog);
        this.mPublishProgressLayout.destroy();
        GlideApp.get(this).clearMemory();
    }

    public void initDate() {
        this.mIsTemplate = getIntent().getBooleanExtra(EXTRA_SHARE_FILE_TEMP, false);
        Bundle bundle = (Bundle) getIntent().getParcelableExtra(FileTransferActivity.EXTRA_TRANSFER_BUNDLE);
        this.mFileList = bundle.getParcelableArrayList(TransferManagerService.EXTRA_TRANSFER_FILE_PATH);
        StringBuilder append = new StringBuilder().append("mIsTemplate is: ").append(this.mIsTemplate).append("mFileList size ");
        List<BaseItem> list = this.mFileList;
        CameraLog.d(TAG, append.append(list != null ? list.size() : 0).toString(), false);
        this.mShareContent = getIntent().getStringExtra(EXTRA_SHARE_CONTENT);
        this.mRepeatCount = 0;
    }

    private void publish(String otp, String uid) {
        CameraLog.d(TAG, "publish, repeat is: " + this.mRepeatCount, false);
        List<BaseItem> list = this.mFileList;
        if (list == null || list.size() == 0 || TextUtils.isEmpty(this.mShareContent)) {
            finish();
        }
        if (this.mFileList.get(0).getType() == 2) {
            ShareBuilder.create(2, this.mCallback, otp, uid).buildContent(this.mShareContent).buildVideoPath(this.mFileList.get(0).getPath()).share();
            runOnUiThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.view.share.publish.-$$Lambda$SharePublishActivity$YGD6e5mOB_6qLxKBvb6E-KigdEE
                @Override // java.lang.Runnable
                public final void run() {
                    SharePublishActivity.this.lambda$publish$4$SharePublishActivity();
                }
            });
            return;
        }
        List<String> imgs = new ArrayList<>();
        for (int i = 0; i < this.mFileList.size(); i++) {
            imgs.add(this.mFileList.get(i).getPath());
        }
        ShareBuilder.create(1, this.mCallback, otp, uid).buildContent(this.mShareContent).buildImagesPath(imgs).needProgress(this.mProgressCallback).share();
        CameraLog.d(TAG, "publish end", false);
    }

    public /* synthetic */ void lambda$publish$4$SharePublishActivity() {
        this.mTvUploadProgress.setText(getString(R.string.publish_upload_progress, new Object[]{0, 1}));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Bitmap createQRImage(String url, final int width, final int height) {
        if (url != null) {
            try {
                if (!"".equals(url) && url.length() >= 1) {
                    Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
                    hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
                    hints.put(EncodeHintType.MARGIN, 0);
                    BitMatrix bitMatrix = new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, width, height, hints);
                    int[] pixels = new int[width * height];
                    for (int y = 0; y < height; y++) {
                        for (int x = 0; x < width; x++) {
                            if (bitMatrix.get(x, y)) {
                                pixels[(y * width) + x] = -16777216;
                            } else {
                                pixels[(y * width) + x] = -1;
                            }
                        }
                    }
                    Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                    bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
                    return bitmap;
                }
            } catch (WriterException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showRepeatDialog() {
        XDialog xDialog = this.mRepeatDialog;
        if (xDialog == null) {
            if (xDialog == null) {
                this.mRepeatDialog = new XDialog(this);
            }
            this.mRepeatDialog.setTitle(App.getInstance().getString(R.string.publish_to_server_failure_title));
            this.mRepeatDialog.setMessage(R.string.publish_to_server_failure_repeat_content);
            this.mRepeatDialog.setNegativeButton(getString(R.string.publish_to_server_failure_cancel), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.xmart.cargallery.view.share.publish.-$$Lambda$SharePublishActivity$DM73njzTI9w9C93tdKuEYwwj4To
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog2, int i) {
                    SharePublishActivity.this.lambda$showRepeatDialog$5$SharePublishActivity(xDialog2, i);
                }
            });
            this.mRepeatDialog.setPositiveButton(getString(R.string.publish_to_server_failure_retry), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.xmart.cargallery.view.share.publish.-$$Lambda$SharePublishActivity$GNjJQhGtNIRuOrh2XBf7C4R1wWY
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog2, int i) {
                    SharePublishActivity.this.lambda$showRepeatDialog$6$SharePublishActivity(xDialog2, i);
                }
            });
        }
        this.mRepeatDialog.getDialog().setCanceledOnTouchOutside(false);
        if (!isFinishing()) {
            this.mRepeatDialog.show();
        }
    }

    public /* synthetic */ void lambda$showRepeatDialog$5$SharePublishActivity(XDialog xDialog, int i) {
        xDialog.dismiss();
        finish();
    }

    public /* synthetic */ void lambda$showRepeatDialog$6$SharePublishActivity(XDialog xDialog, int i) {
        this.mRepeatDialog.dismiss();
        this.mPublishProgressLayout.resume();
        publish(this.mOtp, this.mId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: showErrorDialog */
    public void lambda$fetchOtp$9$SharePublishActivity() {
        if (this.mErrorDialog == null) {
            this.mErrorDialog = new XDialog(this);
        }
        this.mErrorDialog.setTitle(getString(R.string.publish_to_server_failure_title));
        this.mErrorDialog.setMessage(R.string.publish_to_server_failure_content);
        this.mErrorDialog.setPositiveButton(getString(R.string.text_ok), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.xmart.cargallery.view.share.publish.-$$Lambda$SharePublishActivity$xjj3cE-Ytoq-KchD_S7PJU8bIf0
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public final void onClick(XDialog xDialog, int i) {
                SharePublishActivity.this.lambda$showErrorDialog$7$SharePublishActivity(xDialog, i);
            }
        });
        this.mErrorDialog.getDialog().setCanceledOnTouchOutside(false);
        if (!isFinishing()) {
            this.mErrorDialog.show();
        }
    }

    public /* synthetic */ void lambda$showErrorDialog$7$SharePublishActivity(XDialog xDialog, int i) {
        this.mErrorDialog.dismiss();
        finish();
    }

    private void hideDialog(XDialog dialog) {
        if (dialog != null && dialog.getDialog().isShowing()) {
            dialog.dismiss();
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        XThemeManager.onConfigurationChanged(newConfig, this, getWindow().getDecorView(), "activity_transfer.xml", null);
        XThemeManager.setWindowBackgroundResource(newConfig, getWindow(), R.drawable.x_bg_app);
        View view = this.mBgTips;
        if (view != null) {
            view.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_transfer_description));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: fetchOtp */
    public void lambda$onCreate$3$SharePublishActivity() {
        Runnable runnable;
        Account[] accounts = LoginManager.getInstance().getmAccountManager().getAccountsByType(LoginManager.ACCOUNT_TYPE_XP_VEHICLE);
        if (accounts.length > 0) {
            String uid = LoginManager.getInstance().getmAccountManager().getUserData(accounts[0], "uid");
            Bundle bundle = new Bundle();
            bundle.putString(LoginManager.AUTH_INFO_EXTRA_APP_ID, LoginManager.APP_ID);
            AccountManagerFuture<Bundle> future = LoginManager.getInstance().getmAccountManager().getAuthToken(accounts[0], LoginManager.AUTH_TYPE_AUTH_OTP, bundle, false, (AccountManagerCallback<Bundle>) null, (Handler) null);
            try {
                try {
                    String token = future.getResult().getString("authtoken");
                    this.mOtp = token;
                    this.mId = uid;
                    CameraLog.d(TAG, "fetch the otp and uid otp = " + this.mOtp + " uid = " + this.mId, false);
                    publish(token, uid);
                } catch (Throwable th) {
                    if (TextUtils.isEmpty(this.mOtp) || TextUtils.isEmpty(this.mId)) {
                        CameraLog.d(TAG, "otp or uid is null", false);
                        runOnUiThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.view.share.publish.-$$Lambda$SharePublishActivity$lCEiIlTlFLxEgvh2X4lJEoe0HKk
                            @Override // java.lang.Runnable
                            public final void run() {
                                SharePublishActivity.this.lambda$fetchOtp$9$SharePublishActivity();
                            }
                        });
                    }
                    throw th;
                }
            } catch (AuthenticatorException | OperationCanceledException | IOException e) {
                e.printStackTrace();
                CameraLog.d(TAG, "fetch otp and uid failure.", false);
                runOnUiThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.view.share.publish.-$$Lambda$SharePublishActivity$ZBRU6j0WgVnTiQ21LZUwmY6Vfto
                    @Override // java.lang.Runnable
                    public final void run() {
                        SharePublishActivity.this.lambda$fetchOtp$8$SharePublishActivity();
                    }
                });
                if (!TextUtils.isEmpty(this.mOtp) && !TextUtils.isEmpty(this.mId)) {
                    return;
                }
                CameraLog.d(TAG, "otp or uid is null", false);
                runnable = new Runnable() { // from class: com.xiaopeng.xmart.cargallery.view.share.publish.-$$Lambda$SharePublishActivity$lCEiIlTlFLxEgvh2X4lJEoe0HKk
                    @Override // java.lang.Runnable
                    public final void run() {
                        SharePublishActivity.this.lambda$fetchOtp$9$SharePublishActivity();
                    }
                };
            }
            if (TextUtils.isEmpty(this.mOtp) || TextUtils.isEmpty(this.mId)) {
                CameraLog.d(TAG, "otp or uid is null", false);
                runnable = new Runnable() { // from class: com.xiaopeng.xmart.cargallery.view.share.publish.-$$Lambda$SharePublishActivity$lCEiIlTlFLxEgvh2X4lJEoe0HKk
                    @Override // java.lang.Runnable
                    public final void run() {
                        SharePublishActivity.this.lambda$fetchOtp$9$SharePublishActivity();
                    }
                };
                runOnUiThread(runnable);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getQrCodeUrlPrefix() {
        if (CommonConfig.HTTP_HOST.startsWith("https://xmart.xiaopeng.com")) {
            return QR_CODE_URL_PREFIX;
        }
        return QR_CODE_URL_PREFIX_ENG;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void uploadPublishBI(boolean success) {
        List<BaseItem> list = this.mFileList;
        if (list == null || list.size() == 0 || this.mFileList.get(0) == null) {
            return;
        }
        BaseItem item = this.mFileList.get(0);
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
        if (FileUtils.isExitShockFile(this.mFileList)) {
            BIHelper.getInstance().uploadGalleryBI(8, 24, params);
        }
    }
}
