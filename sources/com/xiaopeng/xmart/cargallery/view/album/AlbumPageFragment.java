package com.xiaopeng.xmart.cargallery.view.album;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.xmart.cargallery.App;
import com.xiaopeng.xmart.cargallery.CameraLog;
import com.xiaopeng.xmart.cargallery.CarGalleryConfig;
import com.xiaopeng.xmart.cargallery.GlideApp;
import com.xiaopeng.xmart.cargallery.R;
import com.xiaopeng.xmart.cargallery.bean.BaseItem;
import com.xiaopeng.xmart.cargallery.helper.BIHelper;
import com.xiaopeng.xmart.cargallery.helper.ClickTooQuickHelper;
import com.xiaopeng.xmart.cargallery.helper.ThreadPoolHelper;
import com.xiaopeng.xmart.cargallery.manager.DVRStorageManager;
import com.xiaopeng.xmart.cargallery.model.GalleryDataLoader;
import com.xiaopeng.xmart.cargallery.presenter.AlbumPresenter;
import com.xiaopeng.xmart.cargallery.presenter.DVRListPresenter;
import com.xiaopeng.xmart.cargallery.presenter.ListPresenter;
import com.xiaopeng.xmart.cargallery.utils.ToastUtils;
import com.xiaopeng.xmart.cargallery.view.DetailActivity;
import com.xiaopeng.xmart.cargallery.view.GalleryLoadingView;
import com.xiaopeng.xmart.cargallery.view.album.MediaInfoAdapter;
import com.xiaopeng.xmart.cargallery.view.share.edit.ShareEditActivity;
import com.xiaopeng.xmart.cargallery.view.transfer.FileTransferActivity;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.paho.client.mqttv3.internal.ClientDefaults;
/* loaded from: classes16.dex */
public class AlbumPageFragment extends Fragment implements IAlbumView, GalleryLoadingView.OnLoadFailedListener {
    private static final String ARGS_ALBUM_SELECTED = "ARGS_ALBUM_SELECTED";
    private static final String ARGS_ALBUM_TYPE = "ARGS_ALBUM_TYPE";
    public static final int MAX_SHARE_DURATION = 30000;
    private static final int MAX_SHARE_PIC_COUNT = 9;
    public static final long MAX_SHARE_SIZE = 31457280;
    private static final int MAX_SHARE_VIDEO_COUNT = 1;
    private static final long MAX_TRANSFER_FILE_SIZE = 1500000000;
    private static final int MEDIA_SPAN_COUNT = 4;
    private static final long MIN_LOADING_TIME = 1000;
    private static final String OPEN_DETAIL = "openDetail";
    public static final String OPEN_TRANSFER = "openTransfer";
    private static final String SAVED_ALBUM_SELECTED = "saved_album_selected";
    private static final String TAG = "AlbumPageFragment";
    private static final int UPDATE_MESSAGE = 1;
    private List<BaseItem> dataList;
    private MediaInfoAdapter mAdapter;
    private int mAlbumPageType;
    private XDialog mDeleteConfirmDialog;
    private XDialog mDeleteWaitDialog;
    private boolean mIsSelectState;
    private GalleryLoadingView mLoadingView;
    private AlbumPresenter mPresent;
    private RecyclerView mRvMediaList;
    private long mStartLoadingTime;
    private boolean mNeedReload = false;
    private boolean mCurrentAlbumPageSelected = true;
    private boolean mIsFirstLoading = true;
    private boolean mIsFirstEnterDVRTab = true;
    private boolean mIsLoading = false;
    private List<BaseItem> mMediaList = new ArrayList();
    private OnDataListChangedListener mOnDataListListener = null;
    private List<BaseItem> mSelectedItemList = new ArrayList();
    private MediaInfoAdapter.OnItemClickListener mOnItemClickListener = new MediaInfoAdapter.OnItemClickListener() { // from class: com.xiaopeng.xmart.cargallery.view.album.-$$Lambda$AlbumPageFragment$dA7hqBlPbq4UETa43OKdDEjDgBc
        @Override // com.xiaopeng.xmart.cargallery.view.album.MediaInfoAdapter.OnItemClickListener
        public final void onItemClick(View view, int i) {
            AlbumPageFragment.this.lambda$new$0$AlbumPageFragment(view, i);
        }
    };
    private SafeHandler mHandler = new SafeHandler(this);

    /* loaded from: classes16.dex */
    public interface OnDataListChangedListener {
        void onItemDeleted();

        void onSelectedCountChanged(int size, String strMemory);

        void onUpdateMediaDataList();
    }

    @Override // androidx.fragment.app.Fragment
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(SAVED_ALBUM_SELECTED, this.mCurrentAlbumPageSelected);
        CameraLog.d(TAG, "just save selected:" + this.mCurrentAlbumPageSelected + "\n\nFrom Fragment:" + this, false);
        super.onSaveInstanceState(outState);
    }

    public /* synthetic */ void lambda$new$0$AlbumPageFragment(View view, int position) {
        CameraLog.d(TAG, "onItemClick: " + position, false);
        if (position > this.mMediaList.size() - 1) {
            return;
        }
        BaseItem clickedItemItem = this.mMediaList.get(position);
        CameraLog.d(TAG, "onItemClick file: " + clickedItemItem.toString(), false);
        if (!clickedItemItem.isValid()) {
            return;
        }
        CameraLog.d(TAG, "onItemClick is select state: " + this.mIsSelectState);
        if (this.mIsSelectState) {
            clickedItemItem.setSelected(!clickedItemItem.isSelected());
            this.mAdapter.notifyItemChanged(position, Integer.valueOf(position));
            if (this.mSelectedItemList.contains(clickedItemItem)) {
                this.mSelectedItemList.remove(clickedItemItem);
            } else {
                this.mSelectedItemList.add(clickedItemItem);
            }
            OnDataListChangedListener onDataListChangedListener = this.mOnDataListListener;
            if (onDataListChangedListener != null) {
                onDataListChangedListener.onSelectedCountChanged(this.mSelectedItemList.size(), sizeParse(getSelectedFileSize()));
                return;
            }
            return;
        }
        openFile(clickedItemItem);
    }

    public void doAllSelectClick() {
        this.mSelectedItemList.clear();
        CameraLog.d(TAG, "doAllSelectClick file.size:" + this.mMediaList.size(), false);
        for (int i = 0; i < this.mMediaList.size(); i++) {
            BaseItem clickedItemItem = this.mMediaList.get(i);
            if (clickedItemItem.isValid()) {
                if (!clickedItemItem.isSelected()) {
                    clickedItemItem.setSelected(true);
                    this.mAdapter.notifyItem(clickedItemItem);
                }
                this.mSelectedItemList.add(clickedItemItem);
            }
        }
        OnDataListChangedListener onDataListChangedListener = this.mOnDataListListener;
        if (onDataListChangedListener != null) {
            onDataListChangedListener.onSelectedCountChanged(this.mSelectedItemList.size(), sizeParse(getSelectedFileSize()));
        }
    }

    private static String sizeParse(long size) {
        float kBytes = ((float) size) / 1024.0f;
        if (kBytes >= 1024.0f) {
            float mBytes = kBytes / 1024.0f;
            if (mBytes < 1024.0f) {
                String sizeStr = String.format("%.1fMB", Float.valueOf(mBytes));
                return sizeStr;
            }
            String sizeStr2 = String.format("%.1fG", Float.valueOf(mBytes / 1024.0f));
            return sizeStr2;
        }
        String sizeStr3 = String.format("%dKB", Integer.valueOf((int) kBytes));
        return sizeStr3;
    }

    private long getSelectedFileSize() {
        long totalSize = 0;
        for (BaseItem item : this.mSelectedItemList) {
            totalSize += item.getSize();
        }
        return totalSize;
    }

    public static AlbumPageFragment newInstance(int albumType, boolean isSelected) {
        Bundle args = new Bundle();
        args.putInt(ARGS_ALBUM_TYPE, albumType);
        args.putBoolean(ARGS_ALBUM_SELECTED, isSelected);
        AlbumPageFragment fragment = new AlbumPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDataListChangedListener) {
            this.mOnDataListListener = (OnDataListChangedListener) context;
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        boolean z = false;
        CameraLog.d(TAG, "onActivityCreated...", false);
        CameraLog.d(TAG, "savedInstanceState:" + savedInstanceState + "\n\n  args:" + getArguments(), false);
        boolean restoreSaveAlbumSelect = false;
        if (savedInstanceState != null) {
            restoreSaveAlbumSelect = savedInstanceState.getBoolean(SAVED_ALBUM_SELECTED);
            CameraLog.d(TAG, "recover select:" + restoreSaveAlbumSelect, true);
            setCurrentAlbumPageSelected(restoreSaveAlbumSelect);
        }
        if (getArguments() != null) {
            this.mAlbumPageType = getArguments().getInt(ARGS_ALBUM_TYPE);
            boolean argStateAlbumSelect = getArguments().getBoolean(ARGS_ALBUM_SELECTED);
            if (argStateAlbumSelect || restoreSaveAlbumSelect) {
                z = true;
            }
            setCurrentAlbumPageSelected(z);
            CameraLog.d(TAG, "args select:" + argStateAlbumSelect, true);
        } else {
            this.mAlbumPageType = 0;
        }
        initAlbumRecyclerView();
        if (this.mAlbumPageType != 3) {
            this.mPresent = new ListPresenter(this, this.mAlbumPageType);
            return;
        }
        DVRListPresenter dVRListPresenter = new DVRListPresenter(this, this.mAlbumPageType);
        this.mPresent = dVRListPresenter;
        dVRListPresenter.registerStorageEventListener();
        ((DVRListPresenter) this.mPresent).registerGearEventBus();
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        CameraLog.d(TAG, "onCreateView...", false);
        return inflater.inflate(R.layout.fragment_albumpage, container, false);
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        CameraLog.d(TAG, "onViewCreated...", false);
        super.onViewCreated(view, savedInstanceState);
        this.mRvMediaList = (RecyclerView) view.findViewById(R.id.rv_media_list);
        GalleryLoadingView galleryLoadingView = (GalleryLoadingView) view.findViewById(R.id.loading_view);
        this.mLoadingView = galleryLoadingView;
        galleryLoadingView.setOnLoadFailedListener(this);
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        CameraLog.d(TAG, "onDestroyView...", false);
        super.onDestroyView();
        this.mLoadingView.setOnLoadFailedListener(null);
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        CameraLog.d(TAG, "onResume mAlbumPageType:" + this.mAlbumPageType + ",mCurrentAlbumPageSelected:" + this.mCurrentAlbumPageSelected, false);
        boolean z = this.mCurrentAlbumPageSelected;
        if (z && (this.mPresent instanceof DVRListPresenter)) {
            handleResumeDVRUI();
        } else if (z && this.mIsFirstLoading) {
            CameraLog.d(TAG, "onResume mIsFirstLoading:" + this.mIsFirstLoading + " startLoadingData()", false);
            startLoadingData();
        } else if (z && this.mNeedReload) {
            CameraLog.d(TAG, "onResume mNeedReload:" + this.mNeedReload + " startLoadingData()", false);
            this.mNeedReload = false;
            startLoadingData();
        }
    }

    private void handleResumeDVRUI() {
        if (((DVRListPresenter) this.mPresent).isCarControllerInited()) {
            if (((DVRListPresenter) this.mPresent).isPGear()) {
                if (((DVRListPresenter) this.mPresent).getDvrMode() != 1) {
                    showDvrEmptyView();
                } else if (this.mNeedReload) {
                    String dvrDir = ((DVRListPresenter) this.mPresent).getDVRDir();
                    if (!TextUtils.isEmpty(dvrDir)) {
                        String mountStatus = DVRStorageManager.getInstance().getVolumeState(dvrDir);
                        CameraLog.d(TAG, "dvrDir:" + dvrDir + ",mount status:" + mountStatus);
                        if (mountStatus.equals("mounted")) {
                            this.mPresent.requestDVRMediaData(dvrDir);
                        } else {
                            showDvrRetryView();
                        }
                    } else {
                        showDvrRetryView();
                    }
                } else {
                    CameraLog.d(TAG, "mIsFirstEnterDVRTab:" + this.mIsFirstEnterDVRTab, false);
                    if (this.mIsFirstEnterDVRTab) {
                        showDvrRetryView();
                    }
                }
            } else {
                showDvrEmptyView();
            }
            this.mIsFirstEnterDVRTab = false;
            return;
        }
        showDvrRetryView();
    }

    @Override // com.xiaopeng.xmart.cargallery.view.album.IAlbumView
    public void onGearChanged(int gearLevel) {
        if (this.mAlbumPageType != 3) {
            return;
        }
        showDvrEmptyView();
    }

    @Override // com.xiaopeng.xmart.cargallery.view.album.IAlbumView
    public void onDvrSdCardRemoved() {
        if (this.mAlbumPageType == 3 && !isUserLeaveGallery(getContext())) {
            ToastUtils.showToast(App.getInstance(), App.getInstance().getString(R.string.dvr_sdcard_removed));
            CameraLog.d(TAG, "dvr sdcard removed", false);
            showDvrEmptyView();
        }
    }

    private boolean isUserLeaveGallery(Context context) {
        try {
            ActivityManager manager = (ActivityManager) context.getSystemService("activity");
            List<ActivityManager.RunningTaskInfo> runningTaskInfoList = manager.getRunningTasks(1);
            if (runningTaskInfoList == null || runningTaskInfoList.size() <= 0) {
                return true;
            }
            ComponentName cn2 = runningTaskInfoList.get(0).topActivity;
            if (!cn2.getPackageName().equals(context.getPackageName())) {
                return true;
            }
            CameraLog.d(TAG, "user leave false", false);
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onStop() {
        CameraLog.d(TAG, "onStop mAlbumPageType:" + this.mAlbumPageType, false);
        this.mLoadingView.onStop();
        this.mNeedReload = true;
        dismissDeleteWaitDialog();
        super.onStop();
    }

    @Override // com.xiaopeng.xmart.cargallery.view.album.IAlbumView
    public void onMediaDataLoaded(List<BaseItem> dataList, int selectedIndex) {
        CameraLog.d(TAG, "onMediaDataLoaded mNeedReload:" + this.mNeedReload, false);
        long now = System.currentTimeMillis();
        CameraLog.d(TAG, "onMediaDataLoaded", false);
        this.dataList = dataList;
        if (this.mIsFirstLoading) {
            long j = this.mStartLoadingTime;
            if (now - j < MIN_LOADING_TIME) {
                this.mHandler.sendEmptyMessageDelayed(1, MIN_LOADING_TIME - (now - j));
                return;
            }
        }
        updateMediaDataList(dataList);
    }

    @Override // com.xiaopeng.xmart.cargallery.view.album.IAlbumView
    public void onMediaDataDeleted(final List<BaseItem> deletedList) {
        this.mIsSelectState = false;
        this.mSelectedItemList.clear();
        if (deletedList.size() > 0) {
            ToastUtils.showToast(getContext(), getResources().getQuantityString(R.plurals.toast_item_deleted, deletedList.size()));
            ThreadPoolHelper.getInstance().postOnMainThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.view.album.-$$Lambda$AlbumPageFragment$k8teyURzyNKH98ii2LpdZFFWt1s
                @Override // java.lang.Runnable
                public final void run() {
                    AlbumPageFragment.this.lambda$onMediaDataDeleted$1$AlbumPageFragment(deletedList);
                }
            });
        }
        OnDataListChangedListener onDataListChangedListener = this.mOnDataListListener;
        if (onDataListChangedListener != null) {
            onDataListChangedListener.onItemDeleted();
        }
    }

    public /* synthetic */ void lambda$onMediaDataDeleted$1$AlbumPageFragment(final List deletedList) {
        this.mAdapter.deleteItems(deletedList);
    }

    @Override // com.xiaopeng.xmart.cargallery.view.GalleryLoadingView.OnLoadFailedListener
    public void onLoadOverTime() {
        this.mIsLoading = false;
        CameraLog.d(TAG, "onLoadOverTime", false);
        if (this.mPresent instanceof DVRListPresenter) {
            ensureDvrInRecordMode();
        }
    }

    public int getAlbumPageType() {
        return this.mAlbumPageType;
    }

    public void setCurrentAlbumPageSelected(boolean currentAlbumPageSelected) {
        this.mCurrentAlbumPageSelected = currentAlbumPageSelected;
    }

    public void setReloadFlag(boolean forceUpdate) {
        this.mIsFirstLoading = forceUpdate;
    }

    private void startLoadingData() {
        CameraLog.d(TAG, "startLoadingData isIsLoading:" + this.mIsLoading, false);
        if (this.mIsLoading) {
            return;
        }
        CameraLog.d(TAG, "startLoadingData mIsFirstLoading:" + this.mIsFirstLoading, false);
        this.mStartLoadingTime = System.currentTimeMillis();
        this.mIsLoading = true;
        this.mLoadingView.showLoading();
        this.mPresent.requestMediaData(this.mIsFirstLoading);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateMediaDataList(List<BaseItem> dataList) {
        int index;
        dismissDeleteWaitDialog();
        this.mLoadingView.hideLoading();
        this.mIsLoading = false;
        this.mIsFirstLoading = false;
        this.mMediaList.clear();
        if (dataList == null || dataList.isEmpty()) {
            showEmptyView();
        } else {
            this.mMediaList.addAll(dataList);
        }
        OnDataListChangedListener onDataListChangedListener = this.mOnDataListListener;
        if (onDataListChangedListener != null) {
            onDataListChangedListener.onUpdateMediaDataList();
        }
        if (getArguments() != null && (index = getArguments().getInt(CarGalleryConfig.INTENT_EXTRA_LIST_INDEX, -1)) != -1) {
            this.mRvMediaList.scrollToPosition(index);
        }
        if (this.mPresent instanceof DVRListPresenter) {
            try {
                GlideApp.with(this).onStart();
            } catch (Exception e) {
            }
        }
        this.mAdapter.notifyDataSetChanged();
    }

    private void initAlbumRecyclerView() {
        MediaInfoAdapter mediaInfoAdapter = new MediaInfoAdapter(this, this.mMediaList);
        this.mAdapter = mediaInfoAdapter;
        mediaInfoAdapter.setOnItemClickListener(this.mOnItemClickListener);
        this.mRvMediaList.setLayoutManager(new GridLayoutManager(getContext(), 4));
        this.mRvMediaList.setAdapter(this.mAdapter);
    }

    private void showEmptyView() {
        this.mLoadingView.updateState(2);
    }

    private void showDvrEmptyView() {
        boolean isPGear = ((DVRListPresenter) this.mPresent).isPGear();
        if (isPGear) {
            this.mLoadingView.updateState(7);
            ensureDvrInRecordMode();
            this.mLoadingView.setOnRetryListener(new GalleryLoadingView.OnRetryListener() { // from class: com.xiaopeng.xmart.cargallery.view.album.-$$Lambda$AlbumPageFragment$CFw72a3WF5K66iosVKJIJLteBHY
                @Override // com.xiaopeng.xmart.cargallery.view.GalleryLoadingView.OnRetryListener
                public final void onRetry() {
                    AlbumPageFragment.this.lambda$showDvrEmptyView$2$AlbumPageFragment();
                }
            });
        } else {
            this.mLoadingView.updateState(6);
        }
        try {
            GlideApp.with(this).onStop();
            CameraLog.d(TAG, "GlideApp.with(this).onStop()");
        } catch (Exception e) {
            CameraLog.d(TAG, "GlideApp onStop() failed:" + e.getMessage());
        }
        this.mMediaList.clear();
        this.mAdapter.notifyDataSetChanged();
    }

    public /* synthetic */ void lambda$showDvrEmptyView$2$AlbumPageFragment() {
        this.mLoadingView.showLoading();
        ensureDvrInReplayMode();
    }

    private void showDvrRetryView() {
        ensureDvrInRecordMode();
        this.mLoadingView.setOnRetryListener(new GalleryLoadingView.OnRetryListener() { // from class: com.xiaopeng.xmart.cargallery.view.album.-$$Lambda$AlbumPageFragment$BwwqMTQgzKKr4ZYbYaG3S8NAlCI
            @Override // com.xiaopeng.xmart.cargallery.view.GalleryLoadingView.OnRetryListener
            public final void onRetry() {
                AlbumPageFragment.this.lambda$showDvrRetryView$3$AlbumPageFragment();
            }
        });
        this.mLoadingView.updateState(5);
        this.mMediaList.clear();
        this.mAdapter.notifyDataSetChanged();
    }

    public /* synthetic */ void lambda$showDvrRetryView$3$AlbumPageFragment() {
        this.mLoadingView.showLoading();
        ensureDvrInReplayMode();
    }

    private void openFile(BaseItem fileItem) {
        if (ClickTooQuickHelper.getInstance().isClickTooQuick(OPEN_DETAIL)) {
            CameraLog.d(TAG, "Click too quick, can not open file: " + fileItem.getName());
            return;
        }
        GalleryDataLoader.getInstance().setShockUnReadState(fileItem);
        uploadDeleteBI(fileItem);
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.addFlags(ClientDefaults.MAX_MSG_SIZE);
        intent.putExtra(CarGalleryConfig.INTENT_EXTRA_SELECTED_TAB, this.mAlbumPageType);
        intent.putExtra(CarGalleryConfig.INTENT_EXTRA_ITEM_PATH, fileItem.getPath());
        AlbumPresenter albumPresenter = this.mPresent;
        if (albumPresenter instanceof DVRListPresenter) {
            String dvrDir = ((DVRListPresenter) albumPresenter).getDVRDir();
            intent.putExtra(CarGalleryConfig.INTENT_EXTRA_DVR_DIR, dvrDir);
        }
        try {
            startActivity(intent);
        } catch (Exception e) {
            CameraLog.d(TAG, "openFile failed: " + e.getMessage(), false);
        }
    }

    private void uploadDeleteBI(BaseItem fileItem) {
        if (!fileItem.getPath().contains(CarGalleryConfig.SENTRY_MODE_COLLISION_SUFFIX)) {
            return;
        }
        Map<String, Number> params = new HashMap<>();
        BIHelper.getInstance().uploadGalleryBI(9, 25, params);
    }

    public void setSelectState(boolean isSelect) {
        this.mIsSelectState = isSelect;
        if (!isSelect) {
            for (BaseItem item : this.mSelectedItemList) {
                item.setSelected(false);
                this.mAdapter.notifyItem(item);
            }
            this.mSelectedItemList.clear();
        }
    }

    public void hideDeleteConfirmDialog() {
        XDialog xDialog = this.mDeleteConfirmDialog;
        if (xDialog != null && xDialog.getDialog().isShowing()) {
            this.mDeleteConfirmDialog.dismiss();
        }
    }

    public boolean isEmpty() {
        List<BaseItem> list = this.mMediaList;
        if (list != null) {
            return list.isEmpty();
        }
        return true;
    }

    public boolean transferSelect() {
        if (checkTransferSize(this.mSelectedItemList.size())) {
            if (ClickTooQuickHelper.getInstance().isClickTooQuick(OPEN_TRANSFER)) {
                CameraLog.d(TAG, "Click too quick,open transfer");
                return false;
            }
            FileTransferActivity.startActivityList(getContext(), (ArrayList) this.mSelectedItemList);
            return true;
        }
        return false;
    }

    public void publishSelect() {
        if (checkPublishFile(this.mSelectedItemList)) {
            if (ClickTooQuickHelper.getInstance().isClickTooQuick(OPEN_TRANSFER)) {
                CameraLog.d(TAG, "Click too quick,open share");
            } else {
                ShareEditActivity.startActivityList(getContext(), (ArrayList) this.mSelectedItemList);
            }
        }
    }

    public boolean checkPublishFile() {
        return checkPublishFile(this.mSelectedItemList);
    }

    private boolean checkPublishFile(List<BaseItem> selectedItemList) {
        LogUtils.d(TAG, " check the publish files selectedItemList:" + selectedItemList.toString(), false);
        int selectCount = selectedItemList.size();
        int selectVideoCount = 0;
        long selectVideoSize = 0;
        long selectDuration = 0;
        for (int i = 0; i < selectCount; i++) {
            if (selectedItemList.get(i).getType() == 2) {
                selectVideoCount++;
                selectVideoSize += selectedItemList.get(i).getSize();
                selectDuration += selectedItemList.get(i).getDuration();
            }
        }
        LogUtils.d(TAG, "check the publish file selectCount = " + selectCount + ", selectVideoSize = " + selectVideoCount + ", selectVideoSize = " + selectVideoSize + ", selectDuration = " + selectDuration);
        if (selectCount <= 0) {
            ToastUtils.showToast(getContext(), getString(R.string.share_edit_no_choice));
            return false;
        }
        if (selectVideoCount > 0) {
            if (selectCount != selectVideoCount) {
                ToastUtils.showToast(getContext(), getString(R.string.share_edit_contain_pic_and_video));
                return false;
            } else if (selectCount > 1) {
                ToastUtils.showToast(getContext(), getString(R.string.share_edit_exceed_max_video_count));
                return false;
            } else if (selectVideoSize > MAX_SHARE_SIZE) {
                ToastUtils.showToast(getContext(), getString(R.string.share_edit_exceed_max_size));
                return false;
            } else if (selectDuration > 30000) {
                ToastUtils.showToast(getContext(), getString(R.string.share_edit_exceed_max_duration));
                return false;
            }
        } else if (selectCount > 9) {
            ToastUtils.showToast(getContext(), getString(R.string.share_edit_exceed_max_pic_count));
            return false;
        }
        return true;
    }

    private boolean checkTransferSize(int size) {
        if (size == 0) {
            ToastUtils.showToast(getContext(), getString(R.string.toast_check_transfer_size_zero));
            return false;
        } else if (size > 9) {
            ToastUtils.showToast(getContext(), getString(R.string.toast_check_transfer_size_more_than_nine));
            return false;
        } else {
            long totalSize = 0;
            for (int i = 0; i < this.mSelectedItemList.size(); i++) {
                totalSize += this.mSelectedItemList.get(i).getSize();
            }
            if (totalSize > MAX_TRANSFER_FILE_SIZE) {
                ToastUtils.showToast(getContext(), getString(R.string.transfer_file_exceed_max_size));
                return false;
            }
            return true;
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        CameraLog.d(TAG, "AlbumPage onDestroy..." + this.mPresent.getClass().getCanonicalName(), false);
        this.mLoadingView.onDestroy();
        AlbumPresenter albumPresenter = this.mPresent;
        if (albumPresenter instanceof DVRListPresenter) {
            ((DVRListPresenter) albumPresenter).unRegisterStorageEventListener();
            ((DVRListPresenter) this.mPresent).unRegisterGearEventBus();
        }
        super.onDestroy();
    }

    public void deleteSelect() {
        if (this.mSelectedItemList.size() == 0) {
            ToastUtils.showToast(getContext(), getString(R.string.toast_check_delete_size));
        } else {
            showDeleteConfirmDialog();
        }
    }

    private void showDeleteConfirmDialog() {
        String string;
        String string2;
        if (getActivity() == null) {
            return;
        }
        XDialog xDialog = this.mDeleteConfirmDialog;
        if (xDialog != null && !xDialog.getDialog().isShowing()) {
            XDialog xDialog2 = this.mDeleteConfirmDialog;
            if (this.mSelectedItemList.size() > 1) {
                string2 = getString(R.string.dlg_msg_delete_files, String.valueOf(this.mSelectedItemList.size()));
            } else {
                string2 = getString(R.string.dlg_msg_delete_file);
            }
            xDialog2.setMessage(string2);
            this.mDeleteConfirmDialog.show();
            return;
        }
        if (this.mDeleteConfirmDialog == null) {
            this.mDeleteConfirmDialog = new XDialog(getActivity());
        }
        this.mDeleteConfirmDialog.setNegativeButton(getString(R.string.dlg_btn_cancel), (XDialogInterface.OnClickListener) null);
        this.mDeleteConfirmDialog.setPositiveButton(getString(R.string.dlg_btn_delete), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.xmart.cargallery.view.album.-$$Lambda$AlbumPageFragment$0i8j2qFwqnr_abBQzpQ_ZHM1q3k
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public final void onClick(XDialog xDialog3, int i) {
                AlbumPageFragment.this.lambda$showDeleteConfirmDialog$4$AlbumPageFragment(xDialog3, i);
            }
        });
        this.mDeleteConfirmDialog.setTitle(getResources().getQuantityString(R.plurals.dlg_delete_file_title, this.mSelectedItemList.size()));
        XDialog xDialog3 = this.mDeleteConfirmDialog;
        if (this.mSelectedItemList.size() > 1) {
            string = getString(R.string.dlg_msg_delete_files, String.valueOf(this.mSelectedItemList.size()));
        } else {
            string = getString(R.string.dlg_msg_delete_file);
        }
        xDialog3.setMessage(string);
        this.mDeleteConfirmDialog.show();
    }

    public /* synthetic */ void lambda$showDeleteConfirmDialog$4$AlbumPageFragment(XDialog xDialog, int which) {
        this.mPresent.deleteFiles(this.mSelectedItemList);
        showDeleteWaitDialog();
        this.mDeleteConfirmDialog.dismiss();
    }

    private void showDeleteWaitDialog() {
        if (this.mAlbumPageType != 3 || getActivity() == null) {
            return;
        }
        if (this.mDeleteWaitDialog == null) {
            this.mDeleteWaitDialog = new XDialog(getContext());
            View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_file_delete_wait, (ViewGroup) null);
            this.mDeleteWaitDialog.setCustomView(view).setTitleVisibility(false).show();
        }
        this.mDeleteWaitDialog.getDialog().setCancelable(false);
        this.mDeleteWaitDialog.getDialog().setCanceledOnTouchOutside(false);
        if (this.mDeleteWaitDialog.getDialog().isShowing()) {
            return;
        }
        this.mDeleteWaitDialog.show();
    }

    private void dismissDeleteWaitDialog() {
        XDialog xDialog;
        if (this.mAlbumPageType == 3 && (xDialog = this.mDeleteWaitDialog) != null && xDialog.getDialog().isShowing()) {
            this.mDeleteWaitDialog.dismiss();
            CameraLog.d(TAG, "dismiss delete wait dialog...", false);
        }
    }

    public void setScrollPosition(int scrollPosition) {
        if (this.mRvMediaList != null) {
            CameraLog.d(TAG, "setScrollPosition to " + scrollPosition, false);
            this.mRvMediaList.scrollToPosition(scrollPosition);
        }
    }

    private void ensureDvrInRecordMode() {
        int dvrMode = ((DVRListPresenter) this.mPresent).getDvrMode();
        CameraLog.d(TAG, "ensureDvrInRecordMode() dvrMode:" + dvrMode, false);
        if (dvrMode != 0) {
            ((DVRListPresenter) this.mPresent).switchDvrRecordMode();
        }
    }

    private void ensureDvrInReplayMode() {
        int dvrMode = ((DVRListPresenter) this.mPresent).getDvrMode();
        CameraLog.d(TAG, "ensureDvrInReplayMode() dvrMode:" + dvrMode, false);
        ((DVRListPresenter) this.mPresent).switchDvrReplayMode();
    }

    /* loaded from: classes16.dex */
    private static class SafeHandler extends Handler {
        private final WeakReference<AlbumPageFragment> mFragment;

        SafeHandler(AlbumPageFragment activity) {
            this.mFragment = new WeakReference<>(activity);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            AlbumPageFragment fragment = this.mFragment.get();
            if (fragment != null) {
                fragment.updateMediaDataList(fragment.dataList);
            }
        }
    }
}
