package com.xiaopeng.xmart.cargallery.view.detail;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
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
import com.xiaopeng.xmart.cargallery.helper.ClickTooQuickHelper;
import java.util.List;
/* loaded from: classes18.dex */
public class DetailListAdapter extends RecyclerView.Adapter<DetailListViewHolder> {
    private static final String CLICK_ITEM = "click_item";
    private static final long DETAIL_ITEM_TIME_INTERVAL = 100;
    private static final String TAG = "DetailListAdapter";
    private Context mContext;
    private List<BaseItem> mDatas;
    private OnItemListener mListener;
    private final RequestOptions mPhotoOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE).transform(new MultiTransformation(new CenterCrop(), new RoundedCorners(12))).placeholder(R.drawable.ic_mid_picture).priority(Priority.LOW);
    private final RequestOptions mVideoOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE).transform(new MultiTransformation(new CenterCrop(), new RoundedCorners(12))).placeholder(R.drawable.ic_mid_picture).error(R.drawable.ic_mid_picture).priority(Priority.LOW);
    private int mPreClickIndex = -1;

    /* loaded from: classes18.dex */
    public interface OnItemListener {
        void onItemClick(int position);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public /* bridge */ /* synthetic */ void onBindViewHolder(DetailListViewHolder holder, int position, List payloads) {
        onBindViewHolder2(holder, position, (List<Object>) payloads);
    }

    public DetailListAdapter(List<BaseItem> datas, Context context, OnItemListener listener) {
        this.mListener = listener;
        this.mDatas = datas;
        this.mContext = context;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public DetailListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_file, parent, false);
        return new DetailListViewHolder(view);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(DetailListViewHolder holder, final int position) {
        BaseItem item = this.mDatas.get(position);
        if (item.getType() == 2) {
            holder.mIvPlay.setVisibility(0);
            GlideApp.with(this.mContext).load(item.getPath()).apply((BaseRequestOptions<?>) this.mVideoOptions).into(holder.mIvThumb);
        } else {
            holder.mIvPlay.setVisibility(8);
            GlideApp.with(this.mContext).load(item.getPath()).apply((BaseRequestOptions<?>) this.mPhotoOptions).into(holder.mIvThumb);
        }
        holder.mRootView.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.xmart.cargallery.view.detail.-$$Lambda$DetailListAdapter$WCL-G9GbERpHlsa2eU0PrN6B9hg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DetailListAdapter.this.lambda$onBindViewHolder$0$DetailListAdapter(position, view);
            }
        });
        if (position == this.mPreClickIndex) {
            holder.mIvThumbCover.setImageDrawable(ContextCompat.getDrawable(this.mContext, R.drawable.bg_item_detail_list));
        } else {
            holder.mIvThumbCover.setImageDrawable(new ColorDrawable(0));
        }
    }

    public /* synthetic */ void lambda$onBindViewHolder$0$DetailListAdapter(final int position, View v) {
        if (ClickTooQuickHelper.getInstance().isClickTooQuick(CLICK_ITEM, DETAIL_ITEM_TIME_INTERVAL)) {
            CameraLog.d(TAG, "Click too quick,click item", false);
            return;
        }
        selected(position);
        OnItemListener onItemListener = this.mListener;
        if (onItemListener != null) {
            onItemListener.onItemClick(position);
        }
    }

    /* renamed from: onBindViewHolder  reason: avoid collision after fix types in other method */
    public void onBindViewHolder2(DetailListViewHolder holder, final int position, List<Object> payloads) {
        super.onBindViewHolder((DetailListAdapter) holder, position, payloads);
        if (!payloads.isEmpty()) {
            if (!((Boolean) payloads.get(0)).booleanValue()) {
                holder.mIvThumbCover.setImageDrawable(new ColorDrawable(0));
            } else {
                holder.mIvThumbCover.setImageDrawable(ContextCompat.getDrawable(this.mContext, R.drawable.bg_item_detail_list));
            }
        }
        holder.mRootView.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.xmart.cargallery.view.detail.-$$Lambda$DetailListAdapter$1AEC7dbjz2zxZ00X00V6X2ssVYk
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DetailListAdapter.this.lambda$onBindViewHolder$1$DetailListAdapter(position, view);
            }
        });
    }

    public /* synthetic */ void lambda$onBindViewHolder$1$DetailListAdapter(final int position, View v) {
        if (ClickTooQuickHelper.getInstance().isClickTooQuick(CLICK_ITEM, DETAIL_ITEM_TIME_INTERVAL)) {
            CameraLog.d(TAG, "Click too quick,click item", false);
            return;
        }
        OnItemListener onItemListener = this.mListener;
        if (onItemListener != null) {
            onItemListener.onItemClick(position);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mDatas.size();
    }

    public void selected(int position) {
        int i = this.mPreClickIndex;
        if (position == i) {
            notifyItemChanged(position, true);
            return;
        }
        if (i != -1) {
            notifyItemChanged(i, false);
        }
        notifyItemChanged(position, true);
        this.mPreClickIndex = position;
    }
}
