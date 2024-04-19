package com.xiaopeng.xmart.cargallery.view.transfer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.wifi.WifiClient;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.lzy.okgo.OkGo;
import com.xiaopeng.xmart.cargallery.App;
import com.xiaopeng.xmart.cargallery.CameraLog;
import com.xiaopeng.xmart.cargallery.GlideApp;
import com.xiaopeng.xmart.cargallery.R;
import com.xiaopeng.xmart.cargallery.bean.BIConfig;
import com.xiaopeng.xmart.cargallery.bean.BaseItem;
import com.xiaopeng.xmart.cargallery.bean.ConnectionBean;
import com.xiaopeng.xmart.cargallery.bean.TransferProgressEvent;
import com.xiaopeng.xmart.cargallery.helper.BIHelper;
import com.xiaopeng.xmart.cargallery.helper.ClickTooQuickHelper;
import com.xiaopeng.xmart.cargallery.helper.ThreadPoolHelper;
import com.xiaopeng.xmart.cargallery.presenter.transfer.FileTransferPresenter;
import com.xiaopeng.xmart.cargallery.presenter.transfer.service.TransferManagerService;
import com.xiaopeng.xmart.cargallery.utils.FileUtils;
import com.xiaopeng.xmart.cargallery.utils.ToastUtils;
import com.xiaopeng.xmart.cargallery.utils.ViewUtils;
import com.xiaopeng.xmart.cargallery.view.GalleryBaseActivity;
import com.xiaopeng.xmart.cargallery.view.transfer.ConnectionAdapter;
import com.xiaopeng.xmart.cargallery.widget.SpacesItemDecoration;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import com.xiaopeng.xui.theme.XThemeManager;
import com.xiaopeng.xui.widget.XImageButton;
import com.xiaopeng.xui.widget.XListTwo;
import com.xiaopeng.xui.widget.XTextView;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/* loaded from: classes21.dex */
public class FileTransferActivity extends GalleryBaseActivity implements IFileTransferView {
    public static final String CLICK_EVENT_AP_OPERATION = "click_event_ap_operation";
    private static final int CONNECT_STAGE = 1;
    private static final int END_STAGE = 4;
    private static final int ERROR_STAGE = 3;
    public static final String EXTRA_TRANSFER_BUNDLE = "extra_transfer_bundle";
    public static final String EXTRA_TRANSFER_FILE_ITEM = "extra_transfer_file_item";
    public static final String EXTRA_TRANSFER_FILE_MUL = "extra_transfer_file_mul";
    private static final String EXTRA_TRANSFER_FILE_PATH = "extra_transfer_file_path";
    private static final String INTERNAL_TRANSFER_STAGE = "INTERNAL_TRANSFER_STAGE";
    private static final String TAG = "FileTransferActivity";
    private static final int TRANSFER_STAGE = 2;
    private static final String UNDER_WAY_TOAST_EVENT = "under_way_toast_event";
    private static final int WAIT_STAGE = 0;
    private View bgDescrption;
    private Button mBtTransferBack;
    private ConnectionAdapter mConnectionAdapter;
    private XTextView mConnectionTitle;
    private View mContentView;
    private Context mContext;
    private ConnectionBean mCurrentConnection;
    private int mCurrentStage;
    private XDialog mErrorDialog;
    private long mFileTotalLength;
    private XImageButton mIbClose;
    private boolean mIsMul;
    private ImageView mIvApShadow;
    private ImageView mIvTransferWaitting;
    private ImageView mIvTransfering;
    private ImageView mIvTransferingBgD21;
    private LinearLayout mLLApInfo;
    private boolean mNeedShowExitDialog;
    private FileTransferPresenter mPresenter;
    private XDialog mRejectDialog;
    private RecyclerView mRvConnection;
    private RecyclerView mRvTransferInfo;
    private TransferInfoAdapter mTransferInfoAdapter;
    private TextView mTvApInfoPwd;
    private TextView mTvApInfoSdid;
    private XTextView mTvDescription;
    private XTextView mTvSummaryTitle;
    private TextView mTvTipsContent;
    private XTextView mTvTipsTitle;
    private TextView mTvTransfering;
    private TextView mTvTransferingD21;
    private XListTwo mXltSingle;
    private String transferAddress;
    private SafeHandler mSafeHandler = new SafeHandler(this);
    private ArrayList<BaseItem> mPaths = new ArrayList<>();
    private List<ConnectionBean> mConnectionData = new ArrayList();
    private List<TransferProgressEvent> mInfoData = new ArrayList();

    public static void startActivityList(Context context, ArrayList<BaseItem> paths) {
        if (TransferManagerService.TRANSFER_UNDER_WAY) {
            ToastUtils.showToast(context, (int) R.string.toast_file_transfer_progress);
            return;
        }
        Intent intent = new Intent(context, FileTransferActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("extra_transfer_file_path", paths);
        bundle.putBoolean(EXTRA_TRANSFER_FILE_MUL, true);
        intent.putExtra(EXTRA_TRANSFER_BUNDLE, bundle);
        context.startActivity(intent);
    }

    public static void startActivitySingle(Context context, BaseItem item) {
        if (TransferManagerService.TRANSFER_UNDER_WAY) {
            ToastUtils.showToast(context, (int) R.string.toast_file_transfer_progress);
            return;
        }
        Intent intent = new Intent(context, FileTransferActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean(EXTRA_TRANSFER_FILE_MUL, false);
        bundle.putParcelable(EXTRA_TRANSFER_FILE_ITEM, item);
        intent.putExtra(EXTRA_TRANSFER_BUNDLE, bundle);
        context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xmart.cargallery.view.GalleryBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        this.mContext = this;
        setContentView(R.layout.activity_file_transfer);
        initData();
        findView();
        initConnectErrorDialog();
        setView();
        initRejectDialog();
        super.onCreate(savedInstanceState);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        initData();
        updateByStage(0);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        XThemeManager.onConfigurationChanged(newConfig, this, getWindow().getDecorView(), "activity_transfer.xml", null);
        Log.e(TAG, "onConfigurationChanged isThemeChanged=" + XThemeManager.isThemeChanged(newConfig) + " uiMode=" + newConfig.uiMode);
        XThemeManager.setWindowBackgroundResource(newConfig, getWindow(), R.drawable.x_bg_app);
        this.bgDescrption.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_transfer_description));
        this.mConnectionAdapter.notifyDataSetChanged();
        this.mTransferInfoAdapter.notifyDataSetChanged();
    }

    private void initData() {
        Bundle bundle = (Bundle) getIntent().getParcelableExtra(EXTRA_TRANSFER_BUNDLE);
        boolean z = bundle.getBoolean(EXTRA_TRANSFER_FILE_MUL, false);
        this.mIsMul = z;
        if (z) {
            this.mPaths = bundle.getParcelableArrayList("extra_transfer_file_path");
        } else {
            BaseItem localFileItem = (BaseItem) bundle.getParcelable(EXTRA_TRANSFER_FILE_ITEM);
            this.mPaths.add(localFileItem);
        }
        FileTransferPresenter fileTransferPresenter = new FileTransferPresenter(this, this.mPaths);
        this.mPresenter = fileTransferPresenter;
        this.mFileTotalLength = fileTransferPresenter.getTotalFileLength();
        this.mInfoData = this.mPresenter.getTransferInfoData();
    }

    public void findView() {
        this.mContentView = findViewById(R.id.cl_content);
        this.bgDescrption = findViewById(R.id.bg_transfer_description);
        this.mIbClose = (XImageButton) findViewById(R.id.ib_close);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_connection);
        this.mRvConnection = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.mRvConnection.addItemDecoration(new SpacesItemDecoration(16));
        ConnectionAdapter connectionAdapter = new ConnectionAdapter(this.mConnectionData);
        this.mConnectionAdapter = connectionAdapter;
        this.mRvConnection.setAdapter(connectionAdapter);
        this.mConnectionAdapter.setmListener(new ConnectionAdapter.OnItemClickListener() { // from class: com.xiaopeng.xmart.cargallery.view.transfer.-$$Lambda$FileTransferActivity$gfhM5tJid0Tn4ZdUtYnh1H1mqjw
            @Override // com.xiaopeng.xmart.cargallery.view.transfer.ConnectionAdapter.OnItemClickListener
            public final void onItemClick(ConnectionBean connectionBean) {
                FileTransferActivity.this.lambda$findView$0$FileTransferActivity(connectionBean);
            }
        });
        this.mRvTransferInfo = (RecyclerView) findViewById(R.id.rv_transfer_info);
        this.mTransferInfoAdapter = new TransferInfoAdapter(this.mInfoData);
        this.mRvTransferInfo.setLayoutManager(new LinearLayoutManager(this));
        this.mRvTransferInfo.addItemDecoration(new SpacesItemDecoration(16));
        this.mRvTransferInfo.setAdapter(this.mTransferInfoAdapter);
        this.mTvSummaryTitle = (XTextView) findViewById(R.id.tv_send_summary_title);
        this.mTvDescription = (XTextView) findViewById(R.id.tv_connect_description);
        this.mTvTipsTitle = (XTextView) findViewById(R.id.tv_ap_usage);
        this.mTvTipsContent = (TextView) findViewById(R.id.tv_ap_usage_content);
        this.mLLApInfo = (LinearLayout) findViewById(R.id.ll_ap_info);
        this.mTvApInfoSdid = (TextView) findViewById(R.id.tv_ap_info_ssid);
        this.mTvApInfoPwd = (TextView) findViewById(R.id.tv_ap_info_pwd);
        this.mXltSingle = (XListTwo) findViewById(R.id.xlt_single_connect);
        this.mConnectionTitle = (XTextView) findViewById(R.id.tv_connection_list_title);
        this.mBtTransferBack = (Button) findViewById(R.id.bt_transfer_background);
        this.mIvTransfering = (ImageView) findViewById(R.id.iv_transfer_ing);
        this.mTvTransfering = (TextView) findViewById(R.id.tv_transfer_ing);
        this.mIvTransferWaitting = (ImageView) findViewById(R.id.iv_connect_loading);
        this.mIvTransferingBgD21 = (ImageView) findViewById(R.id.iv_transfer_ing_bg_d21);
        this.mTvTransferingD21 = (TextView) findViewById(R.id.tv_transfer_ing_d21);
        this.mIvApShadow = (ImageView) findViewById(R.id.iv_connect_loading_shadow);
    }

    public /* synthetic */ void lambda$findView$0$FileTransferActivity(ConnectionBean connection) {
        this.mCurrentConnection = connection;
        this.mPresenter.replyToClient(connection);
    }

    public void setView() {
        this.mIbClose.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.xmart.cargallery.view.transfer.-$$Lambda$FileTransferActivity$YXsNNBxdWt3qeFt7w9bQXImFvtw
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                FileTransferActivity.this.lambda$setView$1$FileTransferActivity(view);
            }
        });
        Button button = this.mBtTransferBack;
        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.xmart.cargallery.view.transfer.-$$Lambda$FileTransferActivity$6b4I_Af2MyMPNSPaDTmMdb3qLlQ
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    FileTransferActivity.this.lambda$setView$2$FileTransferActivity(view);
                }
            });
        }
        this.mTvSummaryTitle.setText(getResources().getQuantityString(R.plurals.transfer_file_title, this.mPaths.size(), Integer.valueOf(this.mPaths.size()), FileUtils.convertFileSize(this.mFileTotalLength)));
        this.mTvApInfoSdid.setText(this.mPresenter.generateWifiSSID());
        this.mTvApInfoPwd.setText(this.mPresenter.generateRandomPw());
        this.mXltSingle.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.xmart.cargallery.view.transfer.-$$Lambda$FileTransferActivity$YZhkFNVmrS-u9WS1M2B4bZfmoSk
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                FileTransferActivity.this.lambda$setView$3$FileTransferActivity(view);
            }
        });
    }

    public /* synthetic */ void lambda$setView$1$FileTransferActivity(View v) {
        if (ClickTooQuickHelper.getInstance().isClickTooQuick(CLICK_EVENT_AP_OPERATION, 3000L)) {
            CameraLog.i(TAG, "operation ap to quick,click close icon.", false);
            ToastUtils.showToast(this, (int) R.string.ap_operation_quick);
            return;
        }
        if (TransferManagerService.TRANSFER_UNDER_WAY) {
            showUnderWayToast();
        }
        finish();
    }

    public /* synthetic */ void lambda$setView$2$FileTransferActivity(View v) {
        if (TransferManagerService.TRANSFER_UNDER_WAY) {
            showUnderWayToast();
        } else {
            ToastUtils.showToast(this, getString(R.string.toast_transfer_over));
        }
        finish();
    }

    public /* synthetic */ void lambda$setView$3$FileTransferActivity(View v) {
        this.mPresenter.replyToClient(this.mCurrentConnection);
    }

    private void showUnderWayToast() {
        if (ClickTooQuickHelper.getInstance().isClickTooQuick(UNDER_WAY_TOAST_EVENT)) {
            CameraLog.d(TAG, "show under way toast too quickly...", false);
        } else {
            ToastUtils.showToast(this, getString(R.string.toast_transfer_under_way));
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        CameraLog.d(TAG, "onPause() ...", false);
        SafeHandler safeHandler = this.mSafeHandler;
        if (safeHandler != null) {
            safeHandler.removeCallbacksAndMessages(null);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        CameraLog.d(TAG, "onStop() ...", false);
        if (TransferManagerService.TRANSFER_UNDER_WAY) {
            showUnderWayToast();
        }
        XDialog xDialog = this.mRejectDialog;
        if (xDialog != null && xDialog.isShowing()) {
            this.mRejectDialog.dismiss();
        }
        XDialog xDialog2 = this.mErrorDialog;
        if (xDialog2 != null && xDialog2.isShowing()) {
            this.mErrorDialog.dismiss();
        }
        finish();
        if (isFinishing()) {
            this.mPresenter.onStop();
        }
        super.onStop();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xmart.cargallery.view.GalleryBaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        CameraLog.d(TAG, "onDestroy() ...", false);
        super.onDestroy();
        GlideApp.get(this).clearMemory();
    }

    @Override // com.xiaopeng.xmart.cargallery.view.transfer.IFileTransferView
    public void addConnect(final ConnectionBean bean) {
        CameraLog.d(TAG, "Add connection bean:" + (bean == null ? "null" : bean.toString()), false);
        this.mRvConnection.post(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.view.transfer.-$$Lambda$FileTransferActivity$Cmm_34KlTuK8LxVcLibrbzKQ25M
            @Override // java.lang.Runnable
            public final void run() {
                FileTransferActivity.this.lambda$addConnect$4$FileTransferActivity(bean);
            }
        });
    }

    public /* synthetic */ void lambda$addConnect$4$FileTransferActivity(final ConnectionBean bean) {
        if (this.mConnectionData.size() == 0) {
            updateByStage(1);
        }
        boolean hasAdd = false;
        int i = 0;
        while (true) {
            if (i >= this.mConnectionData.size()) {
                break;
            } else if (!this.mConnectionData.get(i).getIp().equals(bean.getIp())) {
                i++;
            } else {
                hasAdd = true;
                break;
            }
        }
        CameraLog.d(TAG, "Add connection do post runnable,size: " + this.mConnectionData.size() + "has added? " + hasAdd, false);
        if (!hasAdd) {
            this.mConnectionData.add(bean);
            this.mTvDescription.setText(getResources().getQuantityString(R.plurals.transfer_connect_client_number, this.mConnectionData.size(), Integer.valueOf(this.mConnectionData.size())));
        }
        this.mConnectionAdapter.notifyDataSetChanged();
    }

    @Override // com.xiaopeng.xmart.cargallery.view.transfer.IFileTransferView
    public void clientReject(final ConnectionBean connection) {
        this.mCurrentConnection = connection;
        runOnUiThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.view.transfer.-$$Lambda$FileTransferActivity$eEzZgzFP3kG69ivF02ICWxt5ak4
            @Override // java.lang.Runnable
            public final void run() {
                FileTransferActivity.this.lambda$clientReject$5$FileTransferActivity();
            }
        });
    }

    public /* synthetic */ void lambda$clientReject$5$FileTransferActivity() {
        if (!this.mRejectDialog.getDialog().isShowing() && this.mCurrentStage < 2) {
            this.mRejectDialog.show();
        }
    }

    private void updateByStage(int stage) {
        if (this.mCurrentStage == stage) {
            return;
        }
        switch (stage) {
            case 0:
                ViewUtils.setViewsGone(this.mRvConnection, this.mConnectionTitle);
                ViewUtils.setViewsVisible(this.mTvTipsTitle, this.mTvTipsContent);
                this.mTvTipsTitle.setText(getString(R.string.transfer_file_ap_usage));
                this.mTvDescription.setText(getString(R.string.transfer_file_do_connect));
                break;
            case 1:
                ViewUtils.setViewsVisible(this.mConnectionTitle, this.mRvConnection);
                ViewUtils.setViewsGone(this.bgDescrption, this.mTvTipsContent, this.mTvTipsTitle);
                this.mTvDescription.setText(getResources().getQuantityString(R.plurals.transfer_connect_client_number, this.mConnectionData.size(), Integer.valueOf(this.mConnectionData.size())));
                break;
            case 2:
                ViewUtils.setViewsVisible(this.mRvTransferInfo, this.mTvTransfering, this.mIvTransfering, this.mTvTransferingD21, this.mIvTransferingBgD21);
                ViewUtils.setViewsGone(this.mXltSingle, this.mConnectionTitle, this.mRvConnection, this.mTvDescription, this.mLLApInfo, this.mIvTransferWaitting, this.mIvApShadow);
                this.mSafeHandler.removeCallbacksAndMessages(null);
                this.mNeedShowExitDialog = true;
                Button button = this.mBtTransferBack;
                if (button != null) {
                    button.setVisibility(0);
                }
                this.mTvSummaryTitle.setText(getString(R.string.transfer_connect_under_way_d21));
                TextView textView = this.mTvTransfering;
                if (textView != null) {
                    textView.setText(getString(R.string.transfer_connect_under_way));
                    break;
                }
                break;
            case 3:
            case 4:
                if (TransferManagerService.TRANSFER_UNDER_WAY) {
                    clearBackGroundTransfer();
                }
                Button button2 = this.mBtTransferBack;
                if (button2 != null) {
                    button2.setVisibility(8);
                }
                this.mTvSummaryTitle.setText(getString(R.string.transfer_complete));
                TextView textView2 = this.mTvTransfering;
                if (textView2 != null) {
                    textView2.setText(getString(R.string.transfer_complete));
                }
                updateResult();
                break;
        }
        this.mCurrentStage = stage;
    }

    private void clearBackGroundTransfer() {
        TransferManagerService.TRANSFER_UNDER_WAY = false;
        this.mPresenter.connectError();
        CameraLog.d(TAG, "set TRANSFER_UNDER_WAY  when error" + TransferManagerService.TRANSFER_UNDER_WAY, false);
    }

    private void updateResult() {
        boolean hasFailure = false;
        for (int i = 0; i < this.mInfoData.size(); i++) {
            TransferProgressEvent event = this.mInfoData.get(i);
            CameraLog.d(TAG, "update result ,file" + event.getFileName() + " progress" + event.getProgress(), false);
            if (event.getProgress() != event.getFileSize()) {
                hasFailure = true;
            }
        }
        uploadTransferBI(!hasFailure);
    }

    private void initConnectErrorDialog() {
        ThreadPoolHelper.getInstance().execute(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.view.transfer.-$$Lambda$FileTransferActivity$RHJ2YufbCNFopOesdTG6AUjG0aM
            @Override // java.lang.Runnable
            public final void run() {
                FileTransferActivity.this.lambda$initConnectErrorDialog$8$FileTransferActivity();
            }
        });
    }

    public /* synthetic */ void lambda$initConnectErrorDialog$8$FileTransferActivity() {
        runOnUiThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.view.transfer.-$$Lambda$FileTransferActivity$TNcYJTwYtWTj34h_4bXWZYXYqks
            @Override // java.lang.Runnable
            public final void run() {
                FileTransferActivity.this.lambda$null$7$FileTransferActivity();
            }
        });
    }

    public /* synthetic */ void lambda$null$7$FileTransferActivity() {
        if (this.mErrorDialog == null) {
            this.mErrorDialog = new XDialog(this.mContext);
        }
        this.mErrorDialog.setPositiveButton(getString(R.string.dlg_btn_ok), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.xmart.cargallery.view.transfer.-$$Lambda$FileTransferActivity$oUk16K4DNVkqqfCKASG-Ln8kE40
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public final void onClick(XDialog xDialog, int i) {
                FileTransferActivity.this.lambda$null$6$FileTransferActivity(xDialog, i);
            }
        });
        this.mErrorDialog.setTitle(R.string.transfer_file_connect_error_title);
        this.mErrorDialog.setMessage(R.string.transfer_file_connect_error_content);
        this.mErrorDialog.getDialog().setCanceledOnTouchOutside(false);
    }

    public /* synthetic */ void lambda$null$6$FileTransferActivity(XDialog xDialog, int i) {
        this.mPresenter.connectError();
        this.mErrorDialog.dismiss();
        finish();
    }

    private void initRejectDialog() {
        ThreadPoolHelper.getInstance().execute(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.view.transfer.-$$Lambda$FileTransferActivity$pqIL-5CK7-xeuiWXg3CoEvUbsdU
            @Override // java.lang.Runnable
            public final void run() {
                FileTransferActivity.this.lambda$initRejectDialog$12$FileTransferActivity();
            }
        });
    }

    public /* synthetic */ void lambda$initRejectDialog$12$FileTransferActivity() {
        runOnUiThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.view.transfer.-$$Lambda$FileTransferActivity$6igUuUaODgWQFZqzRe_f9Uls1-8
            @Override // java.lang.Runnable
            public final void run() {
                FileTransferActivity.this.lambda$null$11$FileTransferActivity();
            }
        });
    }

    public /* synthetic */ void lambda$null$11$FileTransferActivity() {
        if (this.mRejectDialog == null) {
            this.mRejectDialog = new XDialog(this.mContext);
        }
        this.mRejectDialog.setNegativeButton(getString(R.string.transfer_file_reject_again), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.xmart.cargallery.view.transfer.-$$Lambda$FileTransferActivity$Ch_C6SnDmeHvPUs7Lat0yDnMXcc
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public final void onClick(XDialog xDialog, int i) {
                FileTransferActivity.this.lambda$null$9$FileTransferActivity(xDialog, i);
            }
        });
        this.mRejectDialog.setPositiveButton(getString(R.string.dlg_btn_cancel), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.xmart.cargallery.view.transfer.-$$Lambda$FileTransferActivity$Vt-TMZrjIXO15Grf21NLHHx9iD8
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public final void onClick(XDialog xDialog, int i) {
                FileTransferActivity.this.lambda$null$10$FileTransferActivity(xDialog, i);
            }
        });
        this.mRejectDialog.setTitle(R.string.transfer_file_reject_title);
        this.mRejectDialog.setMessage(R.string.transfer_file_reject_content);
        this.mRejectDialog.getDialog().setCanceledOnTouchOutside(false);
    }

    public /* synthetic */ void lambda$null$9$FileTransferActivity(XDialog xDialog, int i) {
        this.mPresenter.replyToClient(this.mCurrentConnection);
        this.mRejectDialog.dismiss();
    }

    public /* synthetic */ void lambda$null$10$FileTransferActivity(XDialog xDialog, int i) {
        showAllConnection();
        this.mRejectDialog.dismiss();
    }

    private void showAllConnection() {
        this.mRvConnection.setVisibility(0);
        this.mXltSingle.setVisibility(8);
        this.mConnectionTitle.setText(getString(R.string.transfer_file_please_chose_device));
        if (this.mConnectionData.size() == 0) {
            updateByStage(0);
        }
        this.mCurrentConnection = null;
    }

    @Override // com.xiaopeng.xmart.cargallery.view.transfer.IFileTransferView
    public void showSingleConnection() {
        runOnUiThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.view.transfer.-$$Lambda$FileTransferActivity$c2ZR37WqMMmtIm2n21jWY1dl7ug
            @Override // java.lang.Runnable
            public final void run() {
                FileTransferActivity.this.lambda$showSingleConnection$13$FileTransferActivity();
            }
        });
    }

    public /* synthetic */ void lambda$showSingleConnection$13$FileTransferActivity() {
        this.mRvConnection.setVisibility(8);
        this.mXltSingle.setVisibility(0);
        this.mConnectionTitle.setText(getString(R.string.transfer_file_please_confirm));
        this.mXltSingle.setText(this.mCurrentConnection.getDeviceName());
    }

    @Override // com.xiaopeng.xmart.cargallery.view.transfer.IFileTransferView
    public void replyError(final ConnectionBean connection) {
        runOnUiThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.view.transfer.-$$Lambda$FileTransferActivity$ocPIMCOF21Mk4GroIPoc3i3endY
            @Override // java.lang.Runnable
            public final void run() {
                FileTransferActivity.this.lambda$replyError$14$FileTransferActivity();
            }
        });
    }

    public /* synthetic */ void lambda$replyError$14$FileTransferActivity() {
        CameraLog.d(TAG, getString(R.string.transfer_file_toast_connect_error));
        showAllConnection();
    }

    @Override // com.xiaopeng.xmart.cargallery.view.transfer.IFileTransferView
    public void replySuc(ConnectionBean connection) {
        showSingleConnection();
        this.mSafeHandler.removeCallbacksAndMessages(null);
        this.mSafeHandler.sendEmptyMessageDelayed(1, OkGo.DEFAULT_MILLISECONDS);
    }

    @Override // com.xiaopeng.xmart.cargallery.view.transfer.IFileTransferView
    public void clientReceiveOver() {
        CameraLog.d(TAG, "receive the over action ,stop service", false);
        this.mPresenter.clientReceiveOver();
        this.mNeedShowExitDialog = false;
        runOnUiThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.view.transfer.-$$Lambda$FileTransferActivity$Z0NsHqt8lzScStna8I_8MaQBWPw
            @Override // java.lang.Runnable
            public final void run() {
                FileTransferActivity.this.lambda$clientReceiveOver$15$FileTransferActivity();
            }
        });
    }

    public /* synthetic */ void lambda$clientReceiveOver$15$FileTransferActivity() {
        if (this.mCurrentStage < 2) {
            updateByStage(4);
            CameraLog.d(TAG, "client connect error!", false);
            for (int i = 0; i < this.mInfoData.size(); i++) {
                this.mInfoData.get(i).setProgress(-100L);
            }
            this.mTransferInfoAdapter.notifyDataSetChanged();
            return;
        }
        updateByStage(4);
    }

    @Override // com.xiaopeng.xmart.cargallery.view.transfer.IFileTransferView
    public void updateProgress(long progress, long total, String filePath) {
        CameraLog.d(TAG, "on progress ==" + progress, false);
        for (int i = 0; i < this.mInfoData.size(); i++) {
            TransferProgressEvent info = this.mInfoData.get(i);
            if (filePath.equals(info.getFileName())) {
                info.setProgress(progress);
                info.setFileSize(total);
                this.mTransferInfoAdapter.notifyItemChanged(i, 1);
                return;
            }
        }
        CameraLog.d(TAG, "on progress == not find file :" + filePath + "in the file list", false);
    }

    @Override // com.xiaopeng.xmart.cargallery.view.transfer.IFileTransferView
    public void updateTransferFailure(String filePath) {
        for (int i = 0; i < this.mInfoData.size(); i++) {
            TransferProgressEvent info = this.mInfoData.get(i);
            if (info.getProgress() != -1 && info.getProgress() != info.getFileSize()) {
                info.setProgress(-1L);
                this.mTransferInfoAdapter.notifyItemChanged(i, 1);
                if (3 != this.mCurrentStage) {
                    runOnUiThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.view.transfer.-$$Lambda$FileTransferActivity$VFT6vC9mmTqJOAwpbIZeoU_ArCA
                        @Override // java.lang.Runnable
                        public final void run() {
                            FileTransferActivity.this.lambda$updateTransferFailure$16$FileTransferActivity();
                        }
                    });
                }
            }
        }
    }

    public /* synthetic */ void lambda$updateTransferFailure$16$FileTransferActivity() {
        updateByStage(3);
    }

    @Override // com.xiaopeng.xmart.cargallery.view.transfer.IFileTransferView
    public void transferStart(String address) {
        this.transferAddress = address;
        updateByStage(2);
    }

    @Override // com.xiaopeng.xmart.cargallery.view.transfer.IFileTransferView
    public void updateConnectList(List<WifiClient> clientList) {
        int i = this.mCurrentStage;
        if (i == 1 && this.mCurrentConnection != null) {
            int i2 = 0;
            while (true) {
                if (i2 < clientList.size()) {
                    if (!clientList.get(i2).mIpAddr.equals(this.mCurrentConnection.getIp())) {
                        i2++;
                    } else {
                        this.mNeedShowExitDialog = false;
                        break;
                    }
                } else {
                    break;
                }
            }
            if (1 != 0) {
                this.mErrorDialog.show();
            }
        } else if (i == 1) {
            ArrayList<ConnectionBean> needRemoveItems = new ArrayList<>();
            for (ConnectionBean bean : this.mConnectionData) {
                boolean needRemove = true;
                int i3 = 0;
                while (true) {
                    if (i3 < clientList.size()) {
                        if (!clientList.get(i3).mIpAddr.equals(bean.getIp())) {
                            i3++;
                        } else {
                            needRemove = false;
                            break;
                        }
                    } else {
                        break;
                    }
                }
                if (needRemove) {
                    needRemoveItems.add(bean);
                }
            }
            for (int i4 = 0; i4 < needRemoveItems.size(); i4++) {
                ConnectionBean bean2 = needRemoveItems.get(i4);
                this.mConnectionData.remove(bean2);
                CameraLog.d(TAG, "updateConnectList: " + bean2.toString(), false);
            }
            this.mTvDescription.setText(getResources().getQuantityString(R.plurals.transfer_connect_client_number, this.mConnectionData.size(), Integer.valueOf(this.mConnectionData.size())));
            this.mConnectionAdapter.notifyDataSetChanged();
            if (this.mConnectionData.size() == 0) {
                updateByStage(0);
            }
        } else if (i >= 2 && this.transferAddress != null) {
            boolean needShowError = true;
            int i5 = 0;
            while (true) {
                if (i5 < clientList.size()) {
                    if (!this.transferAddress.equals(clientList.get(i5).mIpAddr)) {
                        i5++;
                    } else {
                        needShowError = false;
                        break;
                    }
                } else {
                    break;
                }
            }
            if (needShowError) {
                updateByStage(3);
                if (TransferManagerService.TRANSFER_UNDER_WAY) {
                    clearBackGroundTransfer();
                } else {
                    this.mErrorDialog.show();
                }
            }
        }
    }

    @Override // com.xiaopeng.xmart.cargallery.view.transfer.IFileTransferView
    public void apDisable() {
        XDialog xDialog;
        int i = this.mCurrentStage;
        if ((i == 0 || i == 1) && (xDialog = this.mErrorDialog) != null) {
            xDialog.setTitle(R.string.ap_disable);
            this.mErrorDialog.show();
        }
    }

    @Override // com.xiaopeng.xmart.cargallery.view.transfer.IFileTransferView
    public void onToastInfo(final int status) {
        ThreadPoolHelper.getInstance().postOnMainThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.view.transfer.-$$Lambda$FileTransferActivity$GkcJDJtQf2HIRRXuifCM9lIpuBM
            @Override // java.lang.Runnable
            public final void run() {
                FileTransferActivity.this.lambda$onToastInfo$17$FileTransferActivity(status);
            }
        });
    }

    public /* synthetic */ void lambda$onToastInfo$17$FileTransferActivity(final int status) {
        switch (status) {
            case 1:
                ToastUtils.showToast(App.getInstance(), (int) R.string.toast_hotspot_opened_success);
                return;
            case 2:
                ToastUtils.showToast(App.getInstance(), (int) R.string.toast_hotspot_turn_on_fail_tips);
                return;
            case 32:
                ToastUtils.showToast(getApplicationContext(), getString(R.string.transfer_file_all_success));
                return;
            case 34:
                ToastUtils.showToast(getApplicationContext(), getString(R.string.transfer_file_all_failure));
                return;
            default:
                return;
        }
    }

    @Override // com.xiaopeng.xmart.cargallery.view.transfer.IFileTransferView
    public void onToastTransferInfo(int status, final int sucNum, final int failureNum) {
        ThreadPoolHelper.getInstance().postOnMainThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.view.transfer.-$$Lambda$FileTransferActivity$6173zxCixbUgNT8EOO5I2KkAKcU
            @Override // java.lang.Runnable
            public final void run() {
                FileTransferActivity.this.lambda$onToastTransferInfo$18$FileTransferActivity(sucNum, failureNum);
            }
        });
    }

    public /* synthetic */ void lambda$onToastTransferInfo$18$FileTransferActivity(final int sucNum, final int failureNum) {
        ToastUtils.showToast(getApplicationContext(), getString(R.string.transfer_file_part_success, new Object[]{Integer.valueOf(sucNum), Integer.valueOf(failureNum)}));
    }

    @Override // android.app.Activity
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes21.dex */
    public class SafeHandler extends Handler {
        static final int MESSAGE_SHOE_DIALOG = 1;
        WeakReference<Activity> mActivityReference;

        SafeHandler(Activity activity) {
            this.mActivityReference = new WeakReference<>(activity);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            Activity activity = this.mActivityReference.get();
            if (activity != null) {
                if (!FileTransferActivity.this.mRejectDialog.getDialog().isShowing() && FileTransferActivity.this.mCurrentStage < 2) {
                    FileTransferActivity.this.mRejectDialog.show();
                }
                removeCallbacksAndMessages(null);
            }
        }
    }

    private void uploadTransferBI(boolean success) {
        ArrayList<BaseItem> arrayList = this.mPaths;
        if (arrayList == null || arrayList.size() == 0 || this.mPaths.get(0) == null) {
            return;
        }
        BaseItem item = this.mPaths.get(0);
        int eventType = 21;
        if ("camera_top".equals(item.getKey())) {
            eventType = 23;
        } else if ("camera_dvr".equals(item.getKey())) {
            eventType = 22;
        }
        Map<String, Number> params = new HashMap<>();
        params.put(BIConfig.PROPERTY.DATA_TYPE, 0);
        params.put("result", Integer.valueOf(success ? 1 : 0));
        BIHelper.getInstance().uploadGalleryBI(8, eventType, params);
        if (FileUtils.isExitShockFile(this.mPaths)) {
            params.put(BIConfig.PROPERTY.DATA_TYPE, 0);
            params.put("result", Integer.valueOf(success ? 1 : 0));
            BIHelper.getInstance().uploadGalleryBI(8, 24, params);
        }
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        if (ClickTooQuickHelper.getInstance().isClickTooQuick(CLICK_EVENT_AP_OPERATION, 3000L)) {
            ToastUtils.showToast(this, (int) R.string.ap_operation_quick);
            CameraLog.i(TAG, "operation ap to quick,back pressed", false);
            return;
        }
        super.onBackPressed();
    }
}
