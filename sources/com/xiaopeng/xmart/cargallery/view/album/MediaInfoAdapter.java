package com.xiaopeng.xmart.cargallery.view.album;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.xiaopeng.xmart.cargallery.GlideApp;
import com.xiaopeng.xmart.cargallery.R;
import com.xiaopeng.xmart.cargallery.bean.BaseItem;
import com.xiaopeng.xmart.cargallery.helper.CarTypeHelper;
import com.xiaopeng.xmart.cargallery.utils.DateUtils;
import com.xiaopeng.xui.widget.XImageView;
import java.util.List;
/* loaded from: classes16.dex */
public class MediaInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<BaseItem> mDataList;
    private Fragment mFragment;
    private LayoutInflater mLayoutInflater;
    private OnItemClickListener mOnItemClickListener;
    private final RequestOptions mPhotoOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE).transform(new MultiTransformation(new CenterCrop(), new RoundedCorners(6))).placeholder(R.drawable.ic_mid_picture).priority(Priority.LOW);
    private final RequestOptions mVideoOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE).transform(new MultiTransformation(new CenterCrop(), new RoundedCorners(6))).error(R.drawable.ic_mid_picture).priority(Priority.LOW);

    /* loaded from: classes16.dex */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public MediaInfoAdapter(Fragment fragment, List<BaseItem> mediaList) {
        this.mFragment = fragment;
        this.mLayoutInflater = LayoutInflater.from(fragment.getContext());
        this.mDataList = mediaList;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = (GridLayoutManager) manager;
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: com.xiaopeng.xmart.cargallery.view.album.MediaInfoAdapter.1
                @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
                public int getSpanSize(int position) {
                    if (MediaInfoAdapter.this.getItemViewType(position) == 3 || MediaInfoAdapter.this.getItemViewType(position) == 4) {
                        return gridManager.getSpanCount();
                    }
                    return 1;
                }
            });
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 3) {
            View itemView = this.mLayoutInflater.inflate(R.layout.layout_item_category, parent, false);
            return new CategoryVH(itemView);
        } else if (viewType == 4) {
            View itemView2 = this.mLayoutInflater.inflate(R.layout.layout_item_footer, parent, false);
            return new FooterVH(itemView2);
        } else {
            View itemView3 = this.mLayoutInflater.inflate(R.layout.layout_item_album, parent, false);
            return new MediaInfoVH(itemView3);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int position) {
        List<BaseItem> list = this.mDataList;
        if (list == null || list.isEmpty()) {
            return super.getItemViewType(position);
        }
        return this.mDataList.get(position).getType();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final BaseItem item = this.mDataList.get(position);
        if (3 == item.getType()) {
            CategoryVH categoryVh = (CategoryVH) viewHolder;
            categoryVh.mDateTaken.setText(item.getDateTakenKey());
        } else if (1 == item.getType() || 2 == item.getType()) {
            final MediaInfoVH mediaInfoVH = (MediaInfoVH) viewHolder;
            if (1 == item.getType()) {
                mediaInfoVH.mPlay.setVisibility(8);
                mediaInfoVH.mDuration.setText((CharSequence) null);
                mediaInfoVH.mDuration.setVisibility(8);
                GlideApp.with(this.mFragment).load(item.getPath()).apply((BaseRequestOptions<?>) this.mPhotoOptions).into(mediaInfoVH.mThumbView);
            } else {
                mediaInfoVH.mPlay.setVisibility(0);
                if (item.isCollision() || item.isDvrItem()) {
                    mediaInfoVH.mDuration.setText(DateUtils.formatDate7(item.getDateTaken()));
                } else {
                    mediaInfoVH.mDuration.setText(DateUtils.timeParse(item.getDuration()));
                }
                mediaInfoVH.mDuration.setVisibility(0);
                GlideApp.with(this.mFragment).load(item.getPath()).apply((BaseRequestOptions<?>) this.mVideoOptions).into(mediaInfoVH.mThumbView);
            }
            if (this.mOnItemClickListener != null) {
                mediaInfoVH.mThumbView.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.xmart.cargallery.view.album.-$$Lambda$MediaInfoAdapter$3h338dyNaVRP2FHxDni9egFHfEw
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        MediaInfoAdapter.this.lambda$onBindViewHolder$0$MediaInfoAdapter(mediaInfoVH, position, item, view);
                    }
                });
            }
            mediaInfoVH.mSelect.setVisibility(item.isSelected() ? 0 : 8);
            if (item.isCollision()) {
                mediaInfoVH.mCollisonBg.setVisibility(0);
                mediaInfoVH.mReadFlag.setVisibility(0);
                mediaInfoVH.mReadFlag.setImageResource(item.isRead() ? R.drawable.ic_collision_upload : R.drawable.ic_collision_not_upload);
            } else {
                mediaInfoVH.mCollisonBg.setVisibility(8);
                mediaInfoVH.mReadFlag.setVisibility(8);
            }
            if (CarTypeHelper.isXMartQ5()) {
                if (item.isDvrItem() && item.getPath().contains("PROTECT")) {
                    mediaInfoVH.mDVRLockFlag.setVisibility(0);
                } else {
                    mediaInfoVH.mDVRLockFlag.setVisibility(8);
                }
            }
            mediaInfoVH.mSelectMask.setVisibility(item.isSelected() ? 0 : 8);
        }
    }

    public /* synthetic */ void lambda$onBindViewHolder$0$MediaInfoAdapter(final MediaInfoVH mediaInfoVH, final int position, final BaseItem item, View v) {
        this.mOnItemClickListener.onItemClick(mediaInfoVH.mThumbView, position);
        if (item.isCollision()) {
            mediaInfoVH.mReadFlag.setImageResource(R.drawable.ic_collision_upload);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
        if (!payloads.isEmpty()) {
            BaseItem fileItem = this.mDataList.get(position);
            if (fileItem.isValid()) {
                MediaInfoVH baseItem = (MediaInfoVH) holder;
                baseItem.mSelect.setVisibility(fileItem.isSelected() ? 0 : 8);
                if (fileItem.isCollision()) {
                    baseItem.mCollisonBg.setVisibility(0);
                    baseItem.mReadFlag.setVisibility(0);
                    baseItem.mReadFlag.setImageResource(fileItem.isRead() ? R.drawable.ic_collision_upload : R.drawable.ic_collision_not_upload);
                } else {
                    baseItem.mCollisonBg.setVisibility(8);
                    baseItem.mReadFlag.setVisibility(8);
                }
                if (CarTypeHelper.isXMartQ5()) {
                    if (fileItem.isDvrItem() && fileItem.getPath().contains("PROTECT")) {
                        baseItem.mDVRLockFlag.setVisibility(0);
                    } else {
                        baseItem.mDVRLockFlag.setVisibility(8);
                    }
                }
                baseItem.mSelectMask.setVisibility(fileItem.isSelected() ? 0 : 8);
                return;
            }
            return;
        }
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<BaseItem> list = this.mDataList;
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void notifyItem(BaseItem item) {
        int position = this.mDataList.indexOf(item);
        notifyItemChanged(position, Integer.valueOf(position));
    }

    public void resumeGlideRequests() {
        GlideApp.with(this.mFragment).resumeRequests();
    }

    public void pauseGlideRequests() {
        GlideApp.with(this.mFragment).pauseRequests();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void deleteItems(List<BaseItem> deletedList) {
        this.mDataList.removeAll(deletedList);
        notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes16.dex */
    public static class MediaInfoVH extends RecyclerView.ViewHolder {
        View mCollisonBg;
        XImageView mDVRLockFlag;
        TextView mDuration;
        XImageView mPlay;
        XImageView mReadFlag;
        ImageView mSelect;
        View mSelectMask;
        ImageView mThumbView;

        MediaInfoVH(View itemView) {
            super(itemView);
            this.mThumbView = (ImageView) itemView.findViewById(R.id.item_thumbnail);
            this.mDuration = (TextView) itemView.findViewById(R.id.item_duration);
            this.mPlay = (XImageView) itemView.findViewById(R.id.item_play);
            this.mSelect = (ImageView) itemView.findViewById(R.id.item_select);
            this.mReadFlag = (XImageView) itemView.findViewById(R.id.item_read_flag);
            this.mCollisonBg = itemView.findViewById(R.id.item_lock_bg);
            this.mSelectMask = itemView.findViewById(R.id.item_select_mask);
            this.mDVRLockFlag = (XImageView) itemView.findViewById(R.id.item_dvr_lock);
        }
    }

    /* loaded from: classes16.dex */
    static class CategoryVH extends RecyclerView.ViewHolder {
        TextView mDateTaken;
        ImageView mLocationImage;
        TextView mLocationTag;

        CategoryVH(View itemView) {
            super(itemView);
            this.mDateTaken = (TextView) itemView.findViewById(R.id.tv_date_taken);
            this.mLocationImage = (ImageView) itemView.findViewById(R.id.iv_location);
            this.mLocationTag = (TextView) itemView.findViewById(R.id.tv_location);
        }
    }

    /* loaded from: classes16.dex */
    static class FooterVH extends RecyclerView.ViewHolder {
        FooterVH(View itemView) {
            super(itemView);
        }
    }
}
