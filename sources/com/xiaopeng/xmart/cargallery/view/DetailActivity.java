package com.xiaopeng.xmart.cargallery.view;

import android.accounts.Account;
import android.accounts.OnAccountsUpdateListener;
import android.content.ComponentName;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.xiaopeng.xmart.cargallery.CameraLog;
import com.xiaopeng.xmart.cargallery.CarGalleryConfig;
import com.xiaopeng.xmart.cargallery.GlideApp;
import com.xiaopeng.xmart.cargallery.R;
import com.xiaopeng.xmart.cargallery.bean.BaseItem;
import com.xiaopeng.xmart.cargallery.carmanager.CarClientWrapper;
import com.xiaopeng.xmart.cargallery.carmanager.listener.IVcuController;
import com.xiaopeng.xmart.cargallery.define.DVRDefine;
import com.xiaopeng.xmart.cargallery.fastsp.FastSharedPreferences;
import com.xiaopeng.xmart.cargallery.helper.BIHelper;
import com.xiaopeng.xmart.cargallery.helper.CarTypeHelper;
import com.xiaopeng.xmart.cargallery.helper.ClickTooQuickHelper;
import com.xiaopeng.xmart.cargallery.helper.ThreadPoolHelper;
import com.xiaopeng.xmart.cargallery.manager.DVRStorageManager;
import com.xiaopeng.xmart.cargallery.manager.LoginManager;
import com.xiaopeng.xmart.cargallery.model.GalleryDataLoader;
import com.xiaopeng.xmart.cargallery.presenter.DetailPresenter;
import com.xiaopeng.xmart.cargallery.utils.DateUtils;
import com.xiaopeng.xmart.cargallery.utils.FileUtils;
import com.xiaopeng.xmart.cargallery.utils.ToastUtils;
import com.xiaopeng.xmart.cargallery.view.album.AlbumPageFragment;
import com.xiaopeng.xmart.cargallery.view.album.IAlbumView;
import com.xiaopeng.xmart.cargallery.view.detail.DetailAdapter;
import com.xiaopeng.xmart.cargallery.view.detail.DetailListAdapter;
import com.xiaopeng.xmart.cargallery.view.detail.IDetailView;
import com.xiaopeng.xmart.cargallery.view.player.SuperVideoView;
import com.xiaopeng.xmart.cargallery.view.share.edit.ShareEditActivity;
import com.xiaopeng.xmart.cargallery.view.transfer.FileTransferActivity;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import com.xiaopeng.xui.theme.XThemeManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.paho.client.mqttv3.MqttTopic;
/* loaded from: classes17.dex */
public class DetailActivity extends GalleryBaseActivity implements IAlbumView, OnAccountsUpdateListener {
    private static final String ACTION_VIEW = "com.xiaopeng.camera.action.VIEW";
    private static final String TAG = "DetailActivity";
    private DetailAdapter mAdapter;
    private int mCurrentIndex;
    private DetailListAdapter mDetailListAdapter;
    private RecyclerView mDetailListView;
    private XDialog mDialog;
    private String mDvrDir;
    private GalleryDetailTopView mGalleryTopView;
    private IVcuController mIVcuController;
    private boolean mIsPrePublish;
    private DetailPresenter mPresent;
    private View mRootView;
    private XDialog mSecurityDialog;
    private String mSelectItemPath;
    private int mSelectTab;
    private TextView mTvDuration;
    private TextView mTvFileSize;
    private TextView mTvTakenData;
    private GalleryViewPager mViewPager;
    private List<BaseItem> mDataList = new ArrayList();
    private boolean mIsFromExternal = false;
    private boolean isNoSendBiFirstJumperFile = true;
    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() { // from class: com.xiaopeng.xmart.cargallery.view.DetailActivity.1
        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageSelected(int position) {
            CameraLog.d(DetailActivity.TAG, "onPageSelected position-->" + position, false);
            DetailActivity.this.mAdapter.resetLastView(DetailActivity.this.mCurrentIndex);
            DetailActivity.this.mCurrentIndex = position;
            if (DetailActivity.this.mDataList != null && DetailActivity.this.mDataList.size() > position && DetailActivity.this.mDataList.get(position) != null) {
                DetailActivity detailActivity = DetailActivity.this;
                detailActivity.uploadDeleteBI((BaseItem) detailActivity.mDataList.get(position));
            }
            DetailActivity detailActivity2 = DetailActivity.this;
            detailActivity2.doOnPageSelected(detailActivity2.mCurrentIndex);
            if (DetailActivity.this.mDetailListView != null && DetailActivity.this.mDetailListAdapter != null) {
                DetailActivity.this.mDetailListView.scrollToPosition(DetailActivity.this.mCurrentIndex);
                DetailActivity.this.mDetailListAdapter.selected(position);
            }
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrollStateChanged(int state) {
        }
    };

    /* loaded from: classes17.dex */
    public interface OnTopViewChangeListener {
        void onTopChange(boolean force, int visible);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xmart.cargallery.view.GalleryBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CameraLog.d(TAG, "onCreate...", false);
        setContentView(R.layout.activity_detail);
        initDetailActivity();
        initController();
        initSecurityDialog();
        LoginManager.getInstance().register(this);
    }

    private void initDetailList() {
        this.mRootView = findViewById(R.id.rl_container);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_detail_media_list);
        this.mDetailListView = recyclerView;
        if (recyclerView == null) {
            return;
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(0);
        this.mDetailListView.setLayoutManager(layoutManager);
        DetailListAdapter detailListAdapter = new DetailListAdapter(this.mDataList, this, new DetailListAdapter.OnItemListener() { // from class: com.xiaopeng.xmart.cargallery.view.-$$Lambda$DetailActivity$jgpeP0zrk2f4XHmyiH1NwLgE0YE
            @Override // com.xiaopeng.xmart.cargallery.view.detail.DetailListAdapter.OnItemListener
            public final void onItemClick(int i) {
                DetailActivity.this.lambda$initDetailList$0$DetailActivity(i);
            }
        });
        this.mDetailListAdapter = detailListAdapter;
        detailListAdapter.selected(this.mCurrentIndex);
        this.mDetailListView.setAdapter(this.mDetailListAdapter);
        this.mDetailListView.scrollToPosition(this.mCurrentIndex);
    }

    public /* synthetic */ void lambda$initDetailList$0$DetailActivity(int position) {
        this.mViewPager.setCurrentItem(position, false);
        doOnPageSelected(position);
    }

    private void initController() {
        CameraLog.i(TAG, "  initController ", false);
        ThreadPoolHelper.getInstance().execute(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.view.-$$Lambda$DetailActivity$ziU1BIveorCR5r6RKH6MyFGs_uU
            @Override // java.lang.Runnable
            public final void run() {
                DetailActivity.this.lambda$initController$1$DetailActivity();
            }
        });
    }

    public /* synthetic */ void lambda$initController$1$DetailActivity() {
        CarClientWrapper carClientWrapper = CarClientWrapper.getInstance();
        this.mIVcuController = (IVcuController) carClientWrapper.getController(CarClientWrapper.XP_VCU_SERVICE);
    }

    private void initSecurityDialog() {
        ThreadPoolHelper.getInstance().execute(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.view.-$$Lambda$DetailActivity$3BNs5Vnrecd16YGdKNsy46jdh_M
            @Override // java.lang.Runnable
            public final void run() {
                DetailActivity.this.lambda$initSecurityDialog$4$DetailActivity();
            }
        });
    }

    public /* synthetic */ void lambda$initSecurityDialog$4$DetailActivity() {
        runOnUiThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.view.-$$Lambda$DetailActivity$7-l6ZtKN5xeA5WUhcambEeZGRik
            @Override // java.lang.Runnable
            public final void run() {
                DetailActivity.this.lambda$null$3$DetailActivity();
            }
        });
    }

    public /* synthetic */ void lambda$null$3$DetailActivity() {
        if (this.mSecurityDialog == null) {
            this.mSecurityDialog = new XDialog(this);
        }
        this.mSecurityDialog.setPositiveButton(getString(R.string.dlg_btn_ok), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.xmart.cargallery.view.-$$Lambda$DetailActivity$ZTmjD3z-QYeypYbuBJTOx5VEJrA
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public final void onClick(XDialog xDialog, int i) {
                DetailActivity.this.lambda$null$2$DetailActivity(xDialog, i);
            }
        });
        this.mSecurityDialog.setTitle(R.string.play_video_tips_title);
        this.mSecurityDialog.setMessage(R.string.play_video_tips_message);
    }

    public /* synthetic */ void lambda$null$2$DetailActivity(XDialog xDialog, int i) {
        this.mSecurityDialog.dismiss();
        IDetailView view = this.mAdapter.getView(this.mCurrentIndex);
        if (view instanceof SuperVideoView) {
            CameraLog.d(TAG, "Now is not P Gear,play video by dialog confirm! ", false);
            ((SuperVideoView) view).executePlayVideo();
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        View view = this.mRootView;
        if (view != null) {
            view.setBackground(ContextCompat.getDrawable(this, R.drawable.x_bg_app));
        }
        XThemeManager.setWindowBackgroundResource(newConfig, getWindow(), R.drawable.x_bg_app);
        DetailListAdapter detailListAdapter = this.mDetailListAdapter;
        if (detailListAdapter != null) {
            detailListAdapter.notifyItemChanged(this.mCurrentIndex, true);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStart() {
        super.onStart();
        CameraLog.d(TAG, "onStart");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onNewIntent(Intent intent) {
        CameraLog.d(TAG, "onNewIntent");
        super.onNewIntent(intent);
        setIntent(intent);
        initDetailActivity();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        CameraLog.d(TAG, "onPause", false);
        super.onPause();
        GlideApp.get(this).clearMemory();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        super.onStop();
        CameraLog.d(TAG, "onStop", false);
        XDialog xDialog = this.mDialog;
        if (xDialog != null && xDialog.isShowing()) {
            this.mDialog.dismiss();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xmart.cargallery.view.GalleryBaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        CameraLog.d(TAG, "onDestroy...", false);
        super.onDestroy();
        this.mAdapter.cleanup();
        LoginManager.getInstance().unregister(this);
    }

    @Override // com.xiaopeng.xmart.cargallery.view.album.IAlbumView
    public void onMediaDataLoaded(List<BaseItem> dataList, int selectedIndex) {
        CameraLog.d(TAG, "onMediaDataLoaded size: " + dataList.size() + ", selectedIndex: " + selectedIndex, false);
        this.mDataList.clear();
        if (dataList != null) {
            this.mDataList.addAll(dataList);
        }
        this.mAdapter.setData(this.mDataList);
        this.mViewPager.setCurrentItem(selectedIndex, false);
        this.mCurrentIndex = selectedIndex;
        initDetailList();
        doOnPageSelected(selectedIndex);
    }

    @Override // com.xiaopeng.xmart.cargallery.view.album.IAlbumView
    public void onMediaDataDeleted(List<BaseItem> deletedList) {
        CameraLog.d(TAG, "onMediaDataDeleted size: " + deletedList.size(), false);
        if (deletedList.size() > 0) {
            ToastUtils.showToast(this, getResources().getQuantityString(R.plurals.toast_item_deleted, deletedList.size()));
            BaseItem item = deletedList.get(0);
            int deletedIndex = this.mDataList.indexOf(item);
            this.mDataList.remove(deletedIndex);
            DetailListAdapter detailListAdapter = this.mDetailListAdapter;
            if (detailListAdapter != null) {
                detailListAdapter.notifyItemRemoved(deletedIndex);
                if (deletedIndex != this.mDataList.size()) {
                    this.mDetailListAdapter.notifyItemRangeChanged(deletedIndex, this.mDataList.size() - deletedIndex);
                }
            }
            if (this.mDataList.isEmpty()) {
                CameraLog.d(TAG, "All file deleted, exit detail page directly");
                exitDetail();
                return;
            }
            this.mCurrentIndex = deletedIndex;
            if (deletedIndex >= this.mDataList.size()) {
                this.mCurrentIndex--;
            }
            this.mViewPager.setCurrentItem(this.mCurrentIndex, false);
            this.mAdapter.delete(item);
            DetailListAdapter detailListAdapter2 = this.mDetailListAdapter;
            if (detailListAdapter2 != null) {
                detailListAdapter2.selected(this.mCurrentIndex);
            }
            doOnPageSelected(this.mCurrentIndex);
        }
    }

    private void initDetailActivity() {
        if (getIntent() == null) {
            exitDetail();
        }
        this.mIsFromExternal = ACTION_VIEW.equals(getIntent().getAction());
        this.mSelectItemPath = getIntent().getStringExtra(CarGalleryConfig.INTENT_EXTRA_ITEM_PATH);
        this.mSelectTab = getIntent().getIntExtra(CarGalleryConfig.INTENT_EXTRA_SELECTED_TAB, 0);
        String enterType = getIntent().getStringExtra(CarGalleryConfig.INTENT_EXTRA_TAB);
        if (getIntent().getExtras() != null && !getIntent().getExtras().containsKey(CarGalleryConfig.INTENT_EXTRA_SELECTED_TAB)) {
            if (TextUtils.equals(CarGalleryConfig.TAB_CAR_TOP, enterType)) {
                this.mSelectTab = 2;
            } else if (TextUtils.equals(CarGalleryConfig.TAB_CAR_FRONT, enterType)) {
                this.mSelectTab = 1;
            }
        }
        CameraLog.d(TAG, "initDetailActivity mIsFromExternal=" + this.mIsFromExternal + " path:" + this.mSelectItemPath + " tab:" + this.mSelectTab);
        GalleryDetailTopView galleryDetailTopView = (GalleryDetailTopView) findViewById(R.id.detail_top_view);
        this.mGalleryTopView = galleryDetailTopView;
        galleryDetailTopView.setExitClickListener(new View.OnClickListener() { // from class: com.xiaopeng.xmart.cargallery.view.-$$Lambda$DetailActivity$Z97ksD6WIAoqC88DCucbbk6xE2U
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DetailActivity.this.lambda$initDetailActivity$5$DetailActivity(view);
            }
        });
        this.mGalleryTopView.setDeleteClickListener(new View.OnClickListener() { // from class: com.xiaopeng.xmart.cargallery.view.-$$Lambda$DetailActivity$dWA5R43nXGDJu6Q-IL_n0gQ1uv0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DetailActivity.this.lambda$initDetailActivity$6$DetailActivity(view);
            }
        });
        if (this.mIsFromExternal) {
            this.mGalleryTopView.setTopLeftAnotherClickListener(new View.OnClickListener() { // from class: com.xiaopeng.xmart.cargallery.view.-$$Lambda$DetailActivity$V8FQIk-6S2IkMqc-SceClyEAXac
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    DetailActivity.this.lambda$initDetailActivity$7$DetailActivity(view);
                }
            });
        } else {
            this.mGalleryTopView.setTopLeftAnotherClickListener(null);
        }
        this.mTvDuration = (TextView) findViewById(R.id.tv_detail_duration);
        this.mTvFileSize = (TextView) findViewById(R.id.tv_detail_file_size);
        this.mTvTakenData = (TextView) findViewById(R.id.tv_detail_taken_date);
        this.mGalleryTopView.setSendClickListener(new View.OnClickListener() { // from class: com.xiaopeng.xmart.cargallery.view.-$$Lambda$DetailActivity$lI_3AVjyXAggFKyTxSDQvwzqzT4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DetailActivity.this.lambda$initDetailActivity$8$DetailActivity(view);
            }
        });
        this.mGalleryTopView.setPublishClickListener(new View.OnClickListener() { // from class: com.xiaopeng.xmart.cargallery.view.-$$Lambda$DetailActivity$boKq7K_-B14MhvcWixL6IhvPNvU
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DetailActivity.this.lambda$initDetailActivity$9$DetailActivity(view);
            }
        });
        GalleryViewPager galleryViewPager = (GalleryViewPager) findViewById(R.id.view_pager_detail);
        this.mViewPager = galleryViewPager;
        galleryViewPager.addOnPageChangeListener(this.mOnPageChangeListener);
        DetailAdapter detailAdapter = new DetailAdapter(this, OnTopViewChangeListenerFactory.createOnTopViewChangeListener(CarTypeHelper.getXpCduType(), this.mGalleryTopView));
        this.mAdapter = detailAdapter;
        this.mViewPager.setAdapter(detailAdapter);
        DetailPresenter detailPresenter = new DetailPresenter(this, this.mSelectTab);
        this.mPresent = detailPresenter;
        if (this.mSelectTab == 3) {
            this.mDvrDir = getIntent().getStringExtra(CarGalleryConfig.INTENT_EXTRA_DVR_DIR);
            CameraLog.d(TAG, "mDvrDir:" + this.mDvrDir);
            if (!TextUtils.isEmpty(this.mDvrDir)) {
                String mountStatus = DVRStorageManager.getInstance().getVolumeState(this.mDvrDir);
                CameraLog.d(TAG, "mDvrDir:" + this.mDvrDir + ",mount status:" + mountStatus);
                if (mountStatus.equals("mounted")) {
                    this.mPresent.requestDVRMediaData(this.mDvrDir, this.mSelectItemPath);
                    return;
                }
                return;
            }
            return;
        }
        detailPresenter.requestMediaData(this.mSelectItemPath, this.mIsFromExternal);
    }

    public /* synthetic */ void lambda$initDetailActivity$5$DetailActivity(View v) {
        exitDetail();
    }

    public /* synthetic */ void lambda$initDetailActivity$6$DetailActivity(View v) {
        deleteFile();
    }

    public /* synthetic */ void lambda$initDetailActivity$7$DetailActivity(View v) {
        backToGallery();
    }

    public /* synthetic */ void lambda$initDetailActivity$8$DetailActivity(View v) {
        goTransfer();
    }

    public /* synthetic */ void lambda$initDetailActivity$9$DetailActivity(View v) {
        goShareEdit();
    }

    private void exitDetail() {
        GalleryDataLoader.getInstance().cancelLoadData(true);
        if (this.mIsFromExternal) {
            backToCameraApp();
        } else {
            finish();
        }
    }

    private void backToGallery() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(this, MainActivity.class));
        if (getIntent() != null) {
            intent.putExtras(getIntent());
        }
        intent.putExtra(CarGalleryConfig.INTENT_EXTRA_LIST_INDEX, 0);
        startActivity(intent);
        finish();
    }

    private void backToCameraApp() {
        try {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.xiaopeng.xmart.camera", "com.xiaopeng.xmart.camera.MainActivity"));
            startActivity(intent);
            finish();
        } catch (Exception e) {
            CameraLog.d(TAG, e.getMessage(), false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doOnPageSelected(int position) {
        List<BaseItem> list = this.mDataList;
        if (list == null || list.size() <= 0) {
            return;
        }
        CameraLog.d(TAG, "doOnPageSelected, position: " + position + "  dataSize: " + this.mDataList.size());
        if (position < 0 || position >= this.mDataList.size()) {
            return;
        }
        BaseItem fileItem = this.mDataList.get(position);
        updateFileItemInfoTime(fileItem);
        if (fileItem.getType() == 2) {
            this.mGalleryTopView.setVisibility(0);
        }
    }

    private void updateFileItemInfoTime(final BaseItem fileItem) {
        if (fileItem != null) {
            GalleryDataLoader.getInstance().setShockUnReadState(fileItem);
            long timeLong = fileItem.getDateTaken();
            if (timeLong <= 0) {
                try {
                    timeLong = Long.parseLong(fileItem.getName());
                } catch (NumberFormatException e) {
                    CameraLog.e(TAG, "File " + fileItem.getName() + " can not be parsed to number", false);
                }
            }
            this.mGalleryTopView.setDateTakenInfo(getString(R.string.detail_file_title, new Object[]{DateUtils.formatDate9(timeLong)}));
            if (this.mTvDuration != null && this.mTvFileSize != null && this.mTvTakenData != null) {
                if (fileItem.getType() == 2) {
                    if (fileItem.isDvrItem()) {
                        final FastSharedPreferences fastSp = FastSharedPreferences.get(DVRDefine.DVR_VIDEO_DURATION_FILE_NAME);
                        final String videoName = fileItem.getPath().substring(fileItem.getPath().lastIndexOf(MqttTopic.TOPIC_LEVEL_SEPARATOR) + 1);
                        if (fastSp.contains(videoName)) {
                            long videoDuration = fastSp.getLong(videoName, -1L);
                            CameraLog.d(TAG, "FastSharedPreferences get " + videoName + ",videoDuration:" + videoDuration, false);
                            if (videoDuration > 0) {
                                this.mTvDuration.setVisibility(0);
                                this.mTvDuration.setText(getString(R.string.detail_video_duration, new Object[]{DateUtils.timeParse(videoDuration)}));
                            }
                        } else {
                            ThreadPoolHelper.getInstance().execute(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.view.-$$Lambda$DetailActivity$cDL1hbNMdoXtWYY6mq4lhMNH-xU
                                @Override // java.lang.Runnable
                                public final void run() {
                                    DetailActivity.this.lambda$updateFileItemInfoTime$11$DetailActivity(fileItem, fastSp, videoName);
                                }
                            });
                        }
                    } else {
                        this.mTvDuration.setVisibility(0);
                        this.mTvDuration.setText(getString(R.string.detail_video_duration, new Object[]{DateUtils.timeParse(fileItem.getDuration())}));
                    }
                } else {
                    this.mTvDuration.setVisibility(8);
                }
                this.mTvFileSize.setText(getString(R.string.detail_file_size, new Object[]{FileUtils.convertFileSize(fileItem.getSize())}));
                this.mTvTakenData.setText(getString(R.string.detail_date_taken_d21, new Object[]{DateUtils.formatDate8(timeLong)}));
            }
        }
    }

    public /* synthetic */ void lambda$updateFileItemInfoTime$11$DetailActivity(final BaseItem fileItem, final FastSharedPreferences fastSp, final String videoName) {
        final long duration = GalleryDataLoader.parseDurationFromMMRetriever(fileItem.getPath());
        if (duration > 0) {
            fastSp.edit().putLong(videoName, duration).apply();
            CameraLog.d(TAG, "FastSharedPreferences save " + videoName + ",videoDuration:" + duration, false);
            ThreadPoolHelper.getInstance().postOnMainThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.view.-$$Lambda$DetailActivity$q_5SlUTpiqLpVjLnLAt7cDpojb0
                @Override // java.lang.Runnable
                public final void run() {
                    DetailActivity.this.lambda$null$10$DetailActivity(duration);
                }
            });
        }
    }

    public /* synthetic */ void lambda$null$10$DetailActivity(final long duration) {
        this.mTvDuration.setVisibility(0);
        this.mTvDuration.setText(getString(R.string.detail_video_duration, new Object[]{DateUtils.timeParse(duration)}));
    }

    private void goTransfer() {
        if (ClickTooQuickHelper.getInstance().isClickTooQuick(FileTransferActivity.CLICK_EVENT_AP_OPERATION, 1000L)) {
            CameraLog.i(TAG, "operation ap to quick,enter transfer", false);
            ToastUtils.showToast(this, (int) R.string.ap_operation_quick);
            return;
        }
        int currentItem = this.mViewPager.getCurrentItem();
        CameraLog.d(TAG, "transfer file : " + currentItem + "  mDataList.size : " + this.mDataList.size(), false);
        List<BaseItem> list = this.mDataList;
        if (list != null && currentItem < list.size()) {
            if (ClickTooQuickHelper.getInstance().isClickTooQuick(AlbumPageFragment.OPEN_TRANSFER)) {
                CameraLog.d(TAG, "Click too quick,open transfer");
            } else {
                FileTransferActivity.startActivitySingle(this, this.mDataList.get(currentItem));
            }
        }
    }

    private void goShareEdit() {
        this.mIsPrePublish = false;
        int currentItem = this.mViewPager.getCurrentItem();
        if (currentItem >= this.mDataList.size()) {
            CameraLog.d(TAG, "Share file not exist!");
        } else if (this.mDataList.get(currentItem).getSize() > AlbumPageFragment.MAX_SHARE_SIZE) {
            ToastUtils.showToast(this, getString(R.string.share_edit_exceed_max_size));
        } else if (this.mDataList.get(currentItem).getDuration() > 30000) {
            ToastUtils.showToast(this, getString(R.string.share_edit_exceed_max_duration));
        } else if (LoginManager.getInstance().isLogin()) {
            List<BaseItem> shareList = new ArrayList<>();
            shareList.add(this.mDataList.get(currentItem));
            ShareEditActivity.startActivityList(this, (ArrayList) shareList);
        } else {
            this.mIsPrePublish = true;
        }
    }

    private void deleteFile() {
        IDetailView view = this.mAdapter.getView(this.mCurrentIndex);
        if (view != null) {
            view.onPause();
        }
        XDialog xDialog = this.mDialog;
        if (xDialog != null && xDialog.isShowing()) {
            this.mDialog.dismiss();
        }
        if (this.mDialog == null) {
            this.mDialog = new XDialog(this);
        }
        this.mDialog.setNegativeButton(getString(R.string.dlg_btn_cancel), (XDialogInterface.OnClickListener) null);
        this.mDialog.setPositiveButton(getString(R.string.dlg_btn_delete), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.xmart.cargallery.view.-$$Lambda$DetailActivity$1BxSva9F8IBjIK_xIgfOFrY3-YY
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public final void onClick(XDialog xDialog2, int i) {
                DetailActivity.this.lambda$deleteFile$12$DetailActivity(xDialog2, i);
            }
        });
        this.mDialog.setTitle(getResources().getQuantityString(R.plurals.dlg_delete_file_title, 1));
        this.mDialog.setMessage(getString(R.string.dlg_msg_delete_file, new Object[]{""}));
        this.mDialog.show();
    }

    public /* synthetic */ void lambda$deleteFile$12$DetailActivity(XDialog xDialog, int which) {
        this.mDialog.dismiss();
        int currentItem = this.mViewPager.getCurrentItem();
        CameraLog.d(TAG, "deletePos : " + currentItem + "  mDataList.size : " + this.mDataList.size(), false);
        List<BaseItem> list = this.mDataList;
        if (list != null && currentItem < list.size()) {
            List<BaseItem> deleteList = new ArrayList<>();
            deleteList.add(this.mDataList.get(currentItem));
            this.mPresent.deleteFiles(deleteList);
        }
    }

    @Override // android.accounts.OnAccountsUpdateListener
    public void onAccountsUpdated(Account[] accounts) {
        if (LoginManager.getInstance().getCurrentAccountInfo() != null && this.mIsPrePublish) {
            List<BaseItem> shareList = new ArrayList<>();
            int currentItem = this.mViewPager.getCurrentItem();
            shareList.add(this.mDataList.get(currentItem));
            ShareEditActivity.startActivityList(this, (ArrayList) shareList);
        }
    }

    public boolean checkPGear() {
        if (isPGear()) {
            return true;
        }
        XDialog xDialog = this.mSecurityDialog;
        if (xDialog != null) {
            xDialog.show();
            return false;
        }
        return false;
    }

    private boolean isPGear() {
        IVcuController iVcuController = this.mIVcuController;
        return iVcuController != null && iVcuController.getGearLevel() == 4;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void uploadDeleteBI(BaseItem fileItem) {
        if (this.isNoSendBiFirstJumperFile) {
            this.isNoSendBiFirstJumperFile = false;
        } else if (!fileItem.getPath().contains(CarGalleryConfig.SENTRY_MODE_COLLISION_SUFFIX)) {
        } else {
            Map<String, Number> params = new HashMap<>();
            BIHelper.getInstance().uploadGalleryBI(9, 25, params);
        }
    }

    @Override // com.xiaopeng.xmart.cargallery.view.GalleryBaseActivity
    public void onIgStatusChange(int igStatus) {
        IDetailView view = this.mAdapter.getView(this.mCurrentIndex);
        if ((view instanceof SuperVideoView) && igStatus == 0) {
            CameraLog.d(TAG, "video view need reset when igoff avoid screen white.", false);
            view.reset();
        }
        super.onIgStatusChange(igStatus);
    }
}
