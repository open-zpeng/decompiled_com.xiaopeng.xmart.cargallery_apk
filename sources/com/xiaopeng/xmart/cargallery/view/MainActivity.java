package com.xiaopeng.xmart.cargallery.view;

import android.accounts.Account;
import android.accounts.OnAccountsUpdateListener;
import android.content.ComponentName;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.xmart.cargallery.CameraLog;
import com.xiaopeng.xmart.cargallery.CarGalleryConfig;
import com.xiaopeng.xmart.cargallery.GlideApp;
import com.xiaopeng.xmart.cargallery.R;
import com.xiaopeng.xmart.cargallery.bean.TabConfig;
import com.xiaopeng.xmart.cargallery.helper.BIHelper;
import com.xiaopeng.xmart.cargallery.helper.ClickTooQuickHelper;
import com.xiaopeng.xmart.cargallery.helper.ThreadPoolHelper;
import com.xiaopeng.xmart.cargallery.manager.LoginManager;
import com.xiaopeng.xmart.cargallery.model.GalleryDataLoader;
import com.xiaopeng.xmart.cargallery.utils.DeviceUtils;
import com.xiaopeng.xmart.cargallery.utils.FileUtils;
import com.xiaopeng.xmart.cargallery.utils.ViewUtils;
import com.xiaopeng.xmart.cargallery.view.album.AlbumPageFragment;
import com.xiaopeng.xui.theme.XThemeManager;
import com.xiaopeng.xui.widget.XButton;
import com.xiaopeng.xui.widget.XTabLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
/* loaded from: classes17.dex */
public class MainActivity extends GalleryBaseActivity implements View.OnClickListener, AlbumPageFragment.OnDataListChangedListener, OnAccountsUpdateListener {
    private static final String CHOOSE_EVENT = "choose_event";
    private static final String TAG = "GalleryMainActivity";
    private GalleryViewPager mAlbumPage;
    private View mBottomView;
    private XButton mBtAllChose;
    private XButton mBtCancelChose;
    private XButton mBtChose;
    private XButton mBtPublish;
    private XButton mBtTransfer;
    private ProgressBar mFreeSpaceProgress;
    private ImageButton mIbtClose;
    private ImageButton mIbtDelete;
    private boolean mIsPrePublish;
    private boolean mIsSelectState;
    private List<TabConfig> mTabList;
    private XTabLayout mTitleTabLayout;
    private TextView mTvAlbumDescription;
    private TextView mTvFreeSpace;
    private List<AlbumPageFragment> mFragments = new ArrayList();
    private int mScrollState = 0;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xmart.cargallery.view.GalleryBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CameraLog.d(TAG, "onCreate", false);
        initGalleryTab();
        setContentView(R.layout.activity_main);
        this.mTitleTabLayout = (XTabLayout) findViewById(R.id.xtl_album_title);
        this.mAlbumPage = (GalleryViewPager) findViewById(R.id.vp_album_page);
        initFragment();
        initCurrentTab();
        initListener();
        ClickTooQuickHelper.getInstance().resetClickMap();
        LoginManager.getInstance().register(this);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        ProgressBar progressBar;
        super.onConfigurationChanged(newConfig);
        XThemeManager.onConfigurationChanged(newConfig, this, getWindow().getDecorView(), "activity_main.xml", null);
        XThemeManager.setWindowBackgroundResource(newConfig, getWindow(), R.drawable.x_bg_app);
        if (ThemeManager.isThemeChanged(newConfig) && (progressBar = this.mFreeSpaceProgress) != null) {
            progressBar.setProgressDrawable(getDrawable(R.drawable.bg_free_space));
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        ArrayList<String> typeList = getIntent().getStringArrayListExtra(CarGalleryConfig.INTENT_EXTRA_TAB_LIST);
        String enterType = getIntent().getStringExtra(CarGalleryConfig.INTENT_EXTRA_TAB);
        if (typeList != null) {
            int currentIndex = typeList.indexOf(enterType);
            if (currentIndex == this.mAlbumPage.getCurrentItem()) {
                upLoadShowTabBI(currentIndex);
            }
            CameraLog.d(TAG, "onNewIntent,index:" + currentIndex + " cur:" + this.mAlbumPage.getCurrentItem(), false);
            if (currentIndex > -1 && currentIndex < this.mAlbumPage.getChildCount() && currentIndex < this.mTitleTabLayout.getTabCount() && this.mFragments.size() > currentIndex) {
                this.mTitleTabLayout.selectTab(currentIndex);
                int selectAlbumPageType = this.mFragments.get(currentIndex).getAlbumPageType();
                for (int i = 0; i < this.mFragments.size(); i++) {
                    AlbumPageFragment fragment = this.mFragments.get(i);
                    fragment.setCurrentAlbumPageSelected(selectAlbumPageType == fragment.getAlbumPageType());
                }
            }
        }
        String action = null;
        int scrollPosition = -1;
        if (intent != null) {
            action = intent.getAction();
            scrollPosition = intent.getIntExtra(CarGalleryConfig.INTENT_EXTRA_LIST_INDEX, -1);
            if (scrollPosition != -1) {
                intent.putExtra(CarGalleryConfig.INTENT_EXTRA_LIST_INDEX, -1);
            }
        }
        for (int i2 = 0; i2 < this.mFragments.size(); i2++) {
            AlbumPageFragment fragment2 = this.mFragments.get(i2);
            if (i2 == this.mTitleTabLayout.getSelectedTabIndex() && scrollPosition >= 0) {
                fragment2.setScrollPosition(scrollPosition);
            }
            fragment2.setReloadFlag(action != null);
        }
        ClickTooQuickHelper.getInstance().resetClickMap();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        CameraLog.d(TAG, "onResume...", false);
        updateSelectState(false, true);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        CameraLog.d(TAG, "onPause...", false);
        if (!this.mFragments.isEmpty() && this.mFragments.size() > this.mAlbumPage.getCurrentItem()) {
            this.mFragments.get(this.mAlbumPage.getCurrentItem()).hideDeleteConfirmDialog();
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        super.onStop();
        CameraLog.d(TAG, "onStop...", false);
        GlideApp.get(this).clearMemory();
        GalleryDataLoader.getInstance().cancelLoadData(true);
        if (isFinishing()) {
            CameraLog.d(TAG, "onStop & finishing...", false);
            GalleryDataLoader.getInstance().clear();
        }
    }

    @Override // com.xiaopeng.xmart.cargallery.view.GalleryBaseActivity
    public void onGearLevelChanged(int gearLevel) {
    }

    private void initGalleryTab() {
        ArrayList<String> typeList = getIntent().getStringArrayListExtra(CarGalleryConfig.INTENT_EXTRA_TAB_LIST);
        this.mTabList = CarGalleryConfig.getTabConfig(typeList);
    }

    private void initCurrentTab() {
        String enterType = getIntent().getStringExtra(CarGalleryConfig.INTENT_EXTRA_TAB);
        int size = this.mTabList.size();
        int currentIndex = -1;
        for (int i = 0; i < size; i++) {
            TabConfig tabConfig = this.mTabList.get(i);
            this.mTitleTabLayout.addTab(getString(tabConfig.tabTitleResId));
            if (currentIndex == -1 && tabConfig.tabType.equals(enterType)) {
                currentIndex = i;
                upLoadShowTabBI(currentIndex);
                getIntent().putExtra(CarGalleryConfig.INTENT_EXTRA_TAB, "");
            }
        }
        ViewGroup.LayoutParams lp = this.mTitleTabLayout.getLayoutParams();
        lp.width = getResources().getDimensionPixelSize(R.dimen.tab_title_width) * size;
        this.mTitleTabLayout.setLayoutParams(lp);
        if (currentIndex >= 0) {
            if (this.mAlbumPage.getChildCount() > 0) {
                this.mAlbumPage.setCurrentItem(currentIndex);
            }
            this.mTitleTabLayout.selectTab(currentIndex);
        }
    }

    private void initFragment() {
        this.mAlbumPage.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(), 1) { // from class: com.xiaopeng.xmart.cargallery.view.MainActivity.1
            TreeMap<Integer, AlbumPageFragment> cacheMap = new TreeMap<>();

            @Override // androidx.fragment.app.FragmentPagerAdapter
            public Fragment getItem(int position) {
                CameraLog.d(MainActivity.TAG, "createFragment,position:" + position, false);
                return MainActivity.this.createFragment(position);
            }

            @Override // androidx.fragment.app.FragmentPagerAdapter, androidx.viewpager.widget.PagerAdapter
            public Object instantiateItem(ViewGroup container, int position) {
                CameraLog.d(MainActivity.TAG, "instantiateItem,position:" + position, false);
                Object item = super.instantiateItem(container, position);
                if (item instanceof AlbumPageFragment) {
                    this.cacheMap.put(Integer.valueOf(position), (AlbumPageFragment) item);
                    MainActivity.this.mFragments.clear();
                    for (AlbumPageFragment albumPageFragment : this.cacheMap.values()) {
                        MainActivity.this.mFragments.add(albumPageFragment);
                    }
                    CameraLog.d(MainActivity.TAG, "instantiateItem,fragments:\n" + MainActivity.this.mFragments, false);
                }
                return item;
            }

            @Override // androidx.viewpager.widget.PagerAdapter
            public int getCount() {
                return MainActivity.this.mTabList.size();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Fragment createFragment(int position) {
        Fragment fragment;
        TabConfig tabConfig = this.mTabList.get(position);
        int currentIdx = this.mTitleTabLayout.getSelectedTabIndex();
        if (CarGalleryConfig.TAB_CAMERA_360.equals(tabConfig.tabType) || CarGalleryConfig.TAB_CAMERA_BACK.equals(tabConfig.tabType)) {
            fragment = AlbumPageFragment.newInstance(0, currentIdx == position);
        } else if (CarGalleryConfig.TAB_CAR_FRONT.equals(tabConfig.tabType)) {
            fragment = AlbumPageFragment.newInstance(1, currentIdx == position);
        } else if (CarGalleryConfig.TAB_CAR_TOP.equals(tabConfig.tabType)) {
            fragment = AlbumPageFragment.newInstance(2, currentIdx == position);
        } else if (CarGalleryConfig.TAB_CAR_DVR.equals(tabConfig.tabType)) {
            fragment = AlbumPageFragment.newInstance(3, currentIdx == position);
        } else {
            throw new IllegalArgumentException("Unknown tab: " + tabConfig.tabType);
        }
        CameraLog.d(TAG, tabConfig.tabType + " created: " + fragment, false);
        return fragment;
    }

    private void initListener() {
        this.mAlbumPage.setOffscreenPageLimit(3);
        this.mAlbumPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.xiaopeng.xmart.cargallery.view.MainActivity.2
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int position) {
                MainActivity.this.upLoadShowTabBI(position);
                MainActivity.this.mTitleTabLayout.selectTab(position);
                if (MainActivity.this.mFragments.size() > position) {
                    AlbumPageFragment fragment = (AlbumPageFragment) MainActivity.this.mFragments.get(position);
                    int selectedPageType = fragment.getAlbumPageType();
                    Iterator it = MainActivity.this.mFragments.iterator();
                    while (true) {
                        boolean z = false;
                        if (!it.hasNext()) {
                            break;
                        }
                        AlbumPageFragment frag = (AlbumPageFragment) it.next();
                        if (selectedPageType == frag.getAlbumPageType()) {
                            z = true;
                        }
                        frag.setCurrentAlbumPageSelected(z);
                    }
                    MainActivity.this.mBtChose.setVisibility(fragment.isEmpty() ? 8 : 0);
                }
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int state) {
                MainActivity.this.mScrollState = state;
            }
        });
        this.mTitleTabLayout.setOnTabChangeListener(new XTabLayout.OnTabChangeListener() { // from class: com.xiaopeng.xmart.cargallery.view.MainActivity.3
            @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
            public boolean onInterceptTabChange(XTabLayout xTabLayout, int index, boolean tabChange, boolean fromUser) {
                return false;
            }

            @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
            public void onTabChangeStart(XTabLayout xTabLayout, int index, boolean tabChange, boolean fromUser) {
            }

            @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
            public void onTabChangeEnd(XTabLayout xTabLayout, int index, boolean tabChange, boolean fromUser) {
                CameraLog.d(MainActivity.TAG, "onTabChangeEnd,index:" + index + ",pageCount:" + MainActivity.this.mAlbumPage.getChildCount() + ",fromUser:" + fromUser, false);
                if ((fromUser || MainActivity.this.mAlbumPage.getCurrentItem() != MainActivity.this.mTitleTabLayout.getSelectedTabIndex()) && index < MainActivity.this.mAlbumPage.getChildCount()) {
                    AlbumPageFragment fragment = (AlbumPageFragment) MainActivity.this.mFragments.get(index);
                    for (AlbumPageFragment frag : MainActivity.this.mFragments) {
                        frag.setCurrentAlbumPageSelected(fragment.getAlbumPageType() == frag.getAlbumPageType());
                    }
                    MainActivity.this.mAlbumPage.setCurrentItem(index);
                }
            }
        });
        ImageButton imageButton = (ImageButton) findViewById(R.id.ib_close);
        this.mIbtClose = imageButton;
        imageButton.setOnClickListener(this);
        XButton xButton = (XButton) findViewById(R.id.xbt_chose);
        this.mBtChose = xButton;
        xButton.setOnClickListener(this);
        XButton xButton2 = (XButton) findViewById(R.id.xbt_all_chose);
        this.mBtAllChose = xButton2;
        xButton2.setOnClickListener(this);
        XButton xButton3 = (XButton) findViewById(R.id.xbt_cancel_chose);
        this.mBtCancelChose = xButton3;
        if (xButton3 != null) {
            xButton3.setOnClickListener(this);
        }
        ImageButton imageButton2 = (ImageButton) findViewById(R.id.ib_delete);
        this.mIbtDelete = imageButton2;
        imageButton2.setOnClickListener(this);
        XButton xButton4 = (XButton) findViewById(R.id.xbt_transfer);
        this.mBtTransfer = xButton4;
        xButton4.setOnClickListener(this);
        this.mBottomView = findViewById(R.id.xbt_bottom);
        XButton xButton5 = (XButton) findViewById(R.id.xbt_share);
        this.mBtPublish = xButton5;
        xButton5.setOnClickListener(this);
        this.mTvAlbumDescription = (TextView) findViewById(R.id.tv_album_description);
        this.mFreeSpaceProgress = (ProgressBar) findViewById(R.id.view_free_space);
        this.mTvFreeSpace = (TextView) findViewById(R.id.xtv_free_space);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        AlbumPageFragment albumPageFragment = this.mFragments.get(this.mAlbumPage.getCurrentItem());
        this.mIsPrePublish = false;
        switch (v.getId()) {
            case R.id.ib_close /* 2131296477 */:
                if (this.mIsSelectState) {
                    updateSelectState(false, false);
                    return;
                } else {
                    backToCameraApp();
                    return;
                }
            case R.id.ib_delete /* 2131296478 */:
                if (ClickTooQuickHelper.getInstance().isClickTooQuick(CHOOSE_EVENT)) {
                    CameraLog.d(TAG, "click delete file to fast!", false);
                    return;
                } else {
                    albumPageFragment.deleteSelect();
                    return;
                }
            case R.id.xbt_all_chose /* 2131296829 */:
                albumPageFragment.doAllSelectClick();
                return;
            case R.id.xbt_cancel_chose /* 2131296831 */:
                if (this.mIsSelectState) {
                    updateSelectState(false, false);
                    return;
                }
                return;
            case R.id.xbt_chose /* 2131296832 */:
                if (this.mScrollState != 0) {
                    CameraLog.d(TAG, "no response , now mScrollState:" + this.mScrollState, false);
                    return;
                } else {
                    updateSelectState(true, false);
                    return;
                }
            case R.id.xbt_share /* 2131296833 */:
                if (ClickTooQuickHelper.getInstance().isClickTooQuick(CHOOSE_EVENT)) {
                    CameraLog.d(TAG, "click share file to fast!", false);
                    return;
                } else if (albumPageFragment.checkPublishFile()) {
                    if (LoginManager.getInstance().isLogin()) {
                        albumPageFragment.publishSelect();
                        updateSelectState(false, false);
                        return;
                    }
                    this.mIsPrePublish = true;
                    return;
                } else {
                    return;
                }
            case R.id.xbt_transfer /* 2131296834 */:
                if (ClickTooQuickHelper.getInstance().isClickTooQuick(CHOOSE_EVENT)) {
                    CameraLog.d(TAG, "click transfer file to fast!", false);
                    return;
                } else {
                    albumPageFragment.transferSelect();
                    return;
                }
            default:
                return;
        }
    }

    private void updateSelectState(boolean isSelect, boolean forceUpdateStorage) {
        CameraLog.d(TAG, "updateSelectState for fragment: " + this.mAlbumPage.getCurrentItem() + " isSelect:" + isSelect, false);
        this.mIsSelectState = isSelect;
        this.mBtAllChose.setVisibility(isSelect ? 0 : 8);
        if (!this.mFragments.isEmpty() && this.mFragments.size() > this.mAlbumPage.getCurrentItem()) {
            this.mFragments.get(this.mAlbumPage.getCurrentItem()).setSelectState(isSelect);
        }
        this.mAlbumPage.setScrollEnabled(!isSelect);
        this.mIbtClose.setImageResource(isSelect ? R.drawable.x_ic_small_close : R.drawable.x_ic_small_back);
        if (!ViewUtils.isE28ViewByTag(this.mTitleTabLayout)) {
            this.mTitleTabLayout.setVisibility(isSelect ? 8 : 0);
        }
        boolean isVisibilityChoseView = (isSelect || this.mAlbumPage.getCurrentItem() >= this.mFragments.size() || this.mFragments.get(this.mAlbumPage.getCurrentItem()).isEmpty()) ? false : true;
        this.mBtChose.setVisibility(isVisibilityChoseView ? 0 : 8);
        this.mIbtDelete.setVisibility(isSelect ? 0 : 8);
        if (DeviceUtils.isInter()) {
            if (DeviceUtils.isBtTransferEnable()) {
                this.mBtTransfer.setVisibility(isSelect ? 0 : 8);
            } else {
                this.mBtTransfer.setVisibility(4);
            }
        } else {
            this.mBtTransfer.setVisibility(isSelect ? 0 : 8);
            this.mBtPublish.setVisibility(isSelect ? 0 : 8);
        }
        this.mTvAlbumDescription.setVisibility(isSelect ? 0 : 8);
        this.mTvAlbumDescription.setText(isSelect ? getString(R.string.album_file_select) : null);
        View view = this.mBottomView;
        if (view != null) {
            view.setVisibility(isSelect ? 0 : 8);
        }
        XButton xButton = this.mBtCancelChose;
        if (xButton != null) {
            xButton.setVisibility(isSelect ? 0 : 8);
            this.mIbtClose.setVisibility(this.mBtCancelChose.getVisibility() == 0 ? 4 : 0);
        }
    }

    @Override // com.xiaopeng.xmart.cargallery.view.album.AlbumPageFragment.OnDataListChangedListener
    public void onSelectedCountChanged(int size, String strMemory) {
        if (this.mIsSelectState) {
            String content = getResources().getQuantityString(R.plurals.album_text_select_size, Math.max(size, 1), String.valueOf(size), strMemory);
            this.mTvAlbumDescription.setText(content);
        }
    }

    @Override // com.xiaopeng.xmart.cargallery.view.album.AlbumPageFragment.OnDataListChangedListener
    public void onItemDeleted() {
        updateSelectState(false, true);
    }

    @Override // com.xiaopeng.xmart.cargallery.view.album.AlbumPageFragment.OnDataListChangedListener
    public void onUpdateMediaDataList() {
        updateSelectState(false, true);
    }

    private void showStorageUsageInfo(boolean show, boolean forceUpdateStorage) {
        if (this.mFreeSpaceProgress == null || this.mTvFreeSpace == null) {
            return;
        }
        if (show && forceUpdateStorage) {
            ThreadPoolHelper.getInstance().execute(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.view.-$$Lambda$MainActivity$Mr6N-i5fN41qzRxp8Jzd_6gT5_E
                @Override // java.lang.Runnable
                public final void run() {
                    MainActivity.this.lambda$showStorageUsageInfo$1$MainActivity();
                }
            });
        }
        this.mFreeSpaceProgress.setVisibility(show ? 0 : 8);
        this.mTvFreeSpace.setVisibility(show ? 0 : 8);
    }

    public /* synthetic */ void lambda$showStorageUsageInfo$1$MainActivity() {
        final long[] storageUsageInfo = FileUtils.getStorageUsageInfo(getApplicationContext());
        ThreadPoolHelper.getInstance().postOnMainThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.view.-$$Lambda$MainActivity$DTsCHsuIdqXj_iD1nLvQHwYnLF4
            @Override // java.lang.Runnable
            public final void run() {
                MainActivity.this.lambda$null$0$MainActivity(storageUsageInfo);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: updateStorageUsedInfo */
    public void lambda$null$0$MainActivity(long[] storageUsageInfo) {
        int progress = Math.round((((float) storageUsageInfo[1]) * 100.0f) / ((float) storageUsageInfo[0]));
        this.mFreeSpaceProgress.setProgress(progress);
        this.mTvFreeSpace.setText(getString(R.string.album_text_storage_info, new Object[]{progress + "%"}));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xmart.cargallery.view.GalleryBaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        CameraLog.d(TAG, "onDestroy...", false);
        super.onDestroy();
        LoginManager.getInstance().unregister(this);
    }

    @Override // android.accounts.OnAccountsUpdateListener
    public void onAccountsUpdated(Account[] accounts) {
        CameraLog.d(TAG, "account update :" + accounts);
        if (LoginManager.getInstance().getCurrentAccountInfo() != null && this.mIsPrePublish) {
            if (this.mFragments.size() > this.mAlbumPage.getCurrentItem()) {
                this.mFragments.get(this.mAlbumPage.getCurrentItem()).publishSelect();
            }
            updateSelectState(false, false);
        }
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
    public void upLoadShowTabBI(int current) {
        int sceneId = 0;
        if (this.mTabList.size() <= current) {
            return;
        }
        CameraLog.d(TAG, "upLoadShowTabBI,current:" + current + " tab:" + this.mTabList.get(current).tabType, false);
        String str = this.mTabList.get(current).tabType;
        char c = 65535;
        switch (str.hashCode()) {
            case -264335337:
                if (str.equals(CarGalleryConfig.TAB_CAMERA_BACK)) {
                    c = 0;
                    break;
                }
                break;
            case 1931089149:
                if (str.equals(CarGalleryConfig.TAB_CAMERA_360)) {
                    c = 1;
                    break;
                }
                break;
            case 1931138288:
                if (str.equals(CarGalleryConfig.TAB_CAR_DVR)) {
                    c = 2;
                    break;
                }
                break;
            case 1931153445:
                if (str.equals(CarGalleryConfig.TAB_CAR_TOP)) {
                    c = 3;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
            case 1:
                sceneId = 1;
                break;
            case 2:
                sceneId = 2;
                break;
            case 3:
                sceneId = 3;
                break;
        }
        if (sceneId != 0) {
            BIHelper.getInstance().uploadGalleryBI(sceneId, 0, null);
        }
    }
}
