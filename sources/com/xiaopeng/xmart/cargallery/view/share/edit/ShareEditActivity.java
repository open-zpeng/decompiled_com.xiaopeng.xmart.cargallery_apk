package com.xiaopeng.xmart.cargallery.view.share.edit;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.FragmentActivity;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.xiaopeng.xmart.cargallery.CameraLog;
import com.xiaopeng.xmart.cargallery.GlideApp;
import com.xiaopeng.xmart.cargallery.R;
import com.xiaopeng.xmart.cargallery.bean.BaseItem;
import com.xiaopeng.xmart.cargallery.manager.LoginManager;
import com.xiaopeng.xmart.cargallery.presenter.share.ShareEditPresenter;
import com.xiaopeng.xmart.cargallery.presenter.transfer.service.TransferManagerService;
import com.xiaopeng.xmart.cargallery.utils.ToastUtils;
import com.xiaopeng.xmart.cargallery.view.GalleryBaseActivity;
import com.xiaopeng.xmart.cargallery.view.share.publish.SharePublishActivity;
import com.xiaopeng.xmart.cargallery.view.transfer.FileTransferActivity;
import com.xiaopeng.xmart.cargallery.widget.NumberIndicatorView;
import com.xiaopeng.xmart.cargallery.widget.SpeechInputLayout;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import com.xiaopeng.xui.theme.XThemeManager;
import java.util.ArrayList;
/* loaded from: classes19.dex */
public class ShareEditActivity extends GalleryBaseActivity implements View.OnClickListener, IShareEditView, SpeechInputLayout.SpeechListener {
    private static final String TAG = "ShareEditActivity";
    private static final int TEXT_LIMIT = 1000;
    private EditText mEtInput;
    private XDialog mExitDialog;
    private boolean mIsMul;
    private ImageView mIvMediaPic;
    private ImageView mIvVideoIcon;
    private NumberIndicatorView mNumberIndicatorView;
    private ShareEditPresenter mPresenter;
    private SpeechInputLayout mSpeechInputLayout;
    private TextView mTvGoPublish;
    private TextView tvInputCounter;
    private final RequestOptions mPhotoOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE).transform(new MultiTransformation(new CenterCrop(), new RoundedCorners(20))).placeholder(R.drawable.ic_mid_picture).priority(Priority.LOW);
    private final RequestOptions mVideoOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE).transform(new MultiTransformation(new CenterCrop(), new RoundedCorners(20))).error(R.drawable.ic_mid_picture).priority(Priority.LOW);
    private ArrayList<BaseItem> mFiles = new ArrayList<>();

    public static void startActivityList(Context context, ArrayList<BaseItem> paths) {
        Intent intent = new Intent(context, ShareEditActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(TransferManagerService.EXTRA_TRANSFER_FILE_PATH, paths);
        bundle.putBoolean(FileTransferActivity.EXTRA_TRANSFER_FILE_MUL, true);
        intent.putExtra(FileTransferActivity.EXTRA_TRANSFER_BUNDLE, bundle);
        context.startActivity(intent);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        XThemeManager.onConfigurationChanged(newConfig, this, getWindow().getDecorView(), "activity_transfer.xml", null);
        XThemeManager.setWindowBackgroundResource(newConfig, getWindow(), R.drawable.x_bg_app);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xmart.cargallery.view.GalleryBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_edit);
        initView();
        initData();
        showMedia();
    }

    private void initView() {
        TextView textView = (TextView) findViewById(R.id.btn_go_publish);
        this.mTvGoPublish = textView;
        textView.setOnClickListener(this);
        SpeechInputLayout speechInputLayout = (SpeechInputLayout) findViewById(R.id.siv_input_layout);
        this.mSpeechInputLayout = speechInputLayout;
        speechInputLayout.setListener(this);
        EditText editText = (EditText) findViewById(R.id.et_share_edit_input);
        this.mEtInput = editText;
        editText.requestFocus();
        this.mIvMediaPic = (ImageView) findViewById(R.id.iv_share_media_content);
        this.mIvVideoIcon = (ImageView) findViewById(R.id.iv_video_cover);
        this.mNumberIndicatorView = (NumberIndicatorView) findViewById(R.id.miv_number);
        this.tvInputCounter = (TextView) findViewById(R.id.tv_input_counter);
        this.mEtInput.addTextChangedListener(new TextWatcher() { // from class: com.xiaopeng.xmart.cargallery.view.share.edit.ShareEditActivity.1
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ShareEditActivity.this.tvInputCounter.setText(s.length() + "/1000");
                ShareEditActivity.this.mSpeechInputLayout.notifyTextCount(s.length() >= 1000);
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
            }
        });
        if (this.mExitDialog == null) {
            this.mExitDialog = new XDialog(this);
        }
        this.mExitDialog.setTitle(getString(R.string.dialog_cancel_share_dialog_title)).setMessage(getString(R.string.dialog_cancel_share_dialog_content)).setPositiveButton(getString(R.string.dialog_cancel_share_dialog_ok), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.xmart.cargallery.view.share.edit.-$$Lambda$ShareEditActivity$dfQ_3qv2vcBrE5ktgUrXkztmON8
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public final void onClick(XDialog xDialog, int i) {
                ShareEditActivity.this.lambda$initView$0$ShareEditActivity(xDialog, i);
            }
        }).setNegativeButton(getString(R.string.dialog_cancel_share_dialog_cancel), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.xmart.cargallery.view.share.edit.-$$Lambda$ShareEditActivity$5h7FPGtGx-7tLMEjI8s8wO9qh1Q
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public final void onClick(XDialog xDialog, int i) {
                ShareEditActivity.this.lambda$initView$1$ShareEditActivity(xDialog, i);
            }
        });
        this.mExitDialog.getDialog().setCanceledOnTouchOutside(false);
        findViewById(R.id.ib_close).setOnClickListener(this);
    }

    public /* synthetic */ void lambda$initView$0$ShareEditActivity(XDialog xDialog, int i) {
        finish();
    }

    public /* synthetic */ void lambda$initView$1$ShareEditActivity(XDialog xDialog, int i) {
        this.mExitDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        initData();
        showMedia();
        EditText editText = this.mEtInput;
        if (editText != null) {
            editText.setText("");
        }
    }

    private void initData() {
        Bundle bundle = (Bundle) getIntent().getParcelableExtra(FileTransferActivity.EXTRA_TRANSFER_BUNDLE);
        boolean z = bundle.getBoolean(FileTransferActivity.EXTRA_TRANSFER_FILE_MUL, false);
        this.mIsMul = z;
        if (z) {
            this.mFiles = bundle.getParcelableArrayList(TransferManagerService.EXTRA_TRANSFER_FILE_PATH);
        } else {
            BaseItem localFileItem = (BaseItem) bundle.getParcelable(FileTransferActivity.EXTRA_TRANSFER_FILE_ITEM);
            this.mFiles.add(localFileItem);
        }
        ShareEditPresenter shareEditPresenter = new ShareEditPresenter(this);
        this.mPresenter = shareEditPresenter;
        shareEditPresenter.initRecord();
    }

    private void showMedia() {
        if (this.mFiles.size() == 0) {
            CameraLog.d(TAG, "show media ,the choose file size == 0!");
            return;
        }
        BaseItem item = this.mFiles.get(0);
        if (item.getType() == 1) {
            GlideApp.with((FragmentActivity) this).load(item.getPath()).apply((BaseRequestOptions<?>) this.mPhotoOptions).into(this.mIvMediaPic);
            this.mNumberIndicatorView.setNumber(this.mFiles.size());
            this.mNumberIndicatorView.setVisibility(0);
            return;
        }
        GlideApp.with((FragmentActivity) this).load(item.getPath()).apply((BaseRequestOptions<?>) this.mVideoOptions).into(this.mIvMediaPic);
        this.mIvVideoIcon.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xmart.cargallery.view.GalleryBaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        this.mPresenter.releaseRecord();
        CameraLog.d(TAG, "onDestory()", false);
        GlideApp.get(this).clearMemory();
        super.onDestroy();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        CameraLog.d(TAG, "onStop()", false);
        this.mPresenter.releaseRecord();
        XDialog xDialog = this.mExitDialog;
        if (xDialog != null) {
            xDialog.dismiss();
        }
        finish();
        super.onStop();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        CameraLog.d(TAG, "onPause()", false);
        this.mSpeechInputLayout.setPrepareMode();
        super.onPause();
    }

    @Override // com.xiaopeng.xmart.cargallery.widget.SpeechInputLayout.SpeechListener
    public void startRecord() {
        this.mPresenter.startRecord();
        runOnUiThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.view.share.edit.-$$Lambda$ShareEditActivity$18D8oaiieOzh8kYYRe30wyY57Cc
            @Override // java.lang.Runnable
            public final void run() {
                ShareEditActivity.this.lambda$startRecord$2$ShareEditActivity();
            }
        });
    }

    public /* synthetic */ void lambda$startRecord$2$ShareEditActivity() {
        this.mSpeechInputLayout.setRecordMode();
    }

    @Override // com.xiaopeng.xmart.cargallery.widget.SpeechInputLayout.SpeechListener
    public void stopRecord(boolean byUser) {
        this.mPresenter.stopRecordByUser();
    }

    @Override // com.xiaopeng.xmart.cargallery.view.share.edit.IShareEditView
    public void parseTextResult(final String text) {
        runOnUiThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.view.share.edit.-$$Lambda$ShareEditActivity$IW1QQbQTONPJ1JIbXWNoMTbbek4
            @Override // java.lang.Runnable
            public final void run() {
                ShareEditActivity.this.lambda$parseTextResult$3$ShareEditActivity(text);
            }
        });
    }

    public /* synthetic */ void lambda$parseTextResult$3$ShareEditActivity(final String text) {
        int index = this.mEtInput.getSelectionStart();
        Editable editable = this.mEtInput.getText();
        editable.insert(index, text);
    }

    @Override // com.xiaopeng.xmart.cargallery.view.share.edit.IShareEditView
    public void onRecordEnd(final boolean error) {
        runOnUiThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.view.share.edit.-$$Lambda$ShareEditActivity$1JSHjm_NcF1MKtdopJdL07VV5Tc
            @Override // java.lang.Runnable
            public final void run() {
                ShareEditActivity.this.lambda$onRecordEnd$4$ShareEditActivity(error);
            }
        });
    }

    public /* synthetic */ void lambda$onRecordEnd$4$ShareEditActivity(final boolean error) {
        if (error) {
            this.mSpeechInputLayout.setPrepareMode();
        } else {
            this.mSpeechInputLayout.setTransferMode();
        }
    }

    @Override // com.xiaopeng.xmart.cargallery.view.share.edit.IShareEditView
    public void onSpeakEnd() {
        runOnUiThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.view.share.edit.-$$Lambda$ShareEditActivity$ersAgFOvFzgHNbnzXKIWFe922BE
            @Override // java.lang.Runnable
            public final void run() {
                ShareEditActivity.this.lambda$onSpeakEnd$5$ShareEditActivity();
            }
        });
    }

    public /* synthetic */ void lambda$onSpeakEnd$5$ShareEditActivity() {
        this.mSpeechInputLayout.setPrepareMode();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_go_publish /* 2131296389 */:
                if (!LoginManager.getInstance().isLogin()) {
                    CameraLog.d(TAG, "publish interrupt,please login first...");
                    return;
                }
                String content = this.mEtInput.getText().toString();
                if (content != null) {
                    content = content.trim();
                }
                if (TextUtils.isEmpty(content)) {
                    ToastUtils.showToast(this, getString(R.string.publish_content_not_null));
                    return;
                }
                SharePublishActivity.startActivityList(this, content, this.mFiles);
                finish();
                return;
            case R.id.ib_close /* 2131296477 */:
                this.mExitDialog.show();
                return;
            default:
                return;
        }
    }
}
