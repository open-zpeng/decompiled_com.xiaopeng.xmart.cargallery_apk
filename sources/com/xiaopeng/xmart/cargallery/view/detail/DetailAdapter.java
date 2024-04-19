package com.xiaopeng.xmart.cargallery.view.detail;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.viewpager.widget.PagerAdapter;
import com.xiaopeng.xmart.cargallery.CameraLog;
import com.xiaopeng.xmart.cargallery.bean.BaseItem;
import com.xiaopeng.xmart.cargallery.view.DetailActivity;
import com.xiaopeng.xmart.cargallery.view.player.SuperPhotoView;
import com.xiaopeng.xmart.cargallery.view.player.SuperVideoView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/* loaded from: classes18.dex */
public class DetailAdapter extends PagerAdapter {
    private static final String TAG = "DetailAdapter";
    private Context mContext;
    private DetailActivity.OnTopViewChangeListener mTopViewChangeListener;
    private List<BaseItem> mDataList = new ArrayList();
    private Map<BaseItem, IDetailView> mViewMap = new HashMap();

    public DetailAdapter(Context context, DetailActivity.OnTopViewChangeListener listener) {
        this.mTopViewChangeListener = listener;
        this.mContext = context;
    }

    public void setData(List<BaseItem> dataList) {
        this.mDataList.clear();
        this.mDataList.addAll(dataList);
        notifyDataSetChanged();
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getCount() {
        List<BaseItem> list = this.mDataList;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public Object instantiateItem(ViewGroup container, int position) {
        BaseItem item = this.mDataList.get(position);
        IDetailView detailView = this.mViewMap.get(item);
        if (detailView == null) {
            detailView = createDetailView(item);
            this.mViewMap.put(item, detailView);
        }
        detailView.setData(item);
        View view = (View) detailView;
        ViewParent vp = view.getParent();
        if (vp != null) {
            ViewGroup parent = (ViewGroup) vp;
            parent.removeView(view);
        }
        if (detailView instanceof SuperVideoView) {
            ((SuperVideoView) detailView).refreshProgressBar();
        }
        container.addView(view);
        return view;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void destroyItem(ViewGroup container, int position, Object object) {
        CameraLog.d(TAG, "onDestroy item position: " + position, false);
        container.removeView((View) object);
        if (object instanceof IDetailView) {
            ((IDetailView) object).onDestroyItem();
        }
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getItemPosition(Object object) {
        return -2;
    }

    private IDetailView createDetailView(BaseItem item) {
        CameraLog.d(TAG, "createDetailView item: " + item.getName() + " type: " + item.getType(), false);
        if (1 == item.getType()) {
            IDetailView itemView = new SuperPhotoView(this.mContext);
            ((SuperPhotoView) itemView).setOnTopViewChangeListener(this.mTopViewChangeListener);
            return itemView;
        } else if (2 != item.getType()) {
            return null;
        } else {
            SuperVideoView videoView = new SuperVideoView(this.mContext, this.mTopViewChangeListener);
            videoView.setPhotoPath(item.getPath());
            return videoView;
        }
    }

    public void resetLastView(int lastIndex) {
        CameraLog.d(TAG, "lastIndex: " + lastIndex + " mDataList.size:" + this.mDataList.size(), false);
        if (lastIndex < 0 || lastIndex >= this.mDataList.size()) {
            return;
        }
        BaseItem lastItem = this.mDataList.get(lastIndex);
        IDetailView lastView = this.mViewMap.get(lastItem);
        if (lastView == null) {
            CameraLog.d(TAG, "resetLastView, childAt is null, return.", false);
            return;
        }
        try {
            lastView.reset();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public IDetailView getView(int position) {
        if (position >= 0 && position < this.mDataList.size()) {
            BaseItem item = this.mDataList.get(position);
            return this.mViewMap.get(item);
        }
        return null;
    }

    public void delete(BaseItem item) {
        CameraLog.d(TAG, "delete data:" + item, false);
        if (this.mViewMap.containsKey(item)) {
            this.mViewMap.remove(item);
            this.mDataList.remove(item);
            notifyDataSetChanged();
            return;
        }
        CameraLog.d(TAG, "delete error", false);
    }

    public void cleanup() {
        try {
            for (BaseItem item : this.mViewMap.keySet()) {
                IDetailView view = this.mViewMap.get(item);
                view.cleanup();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.mViewMap.clear();
    }
}
