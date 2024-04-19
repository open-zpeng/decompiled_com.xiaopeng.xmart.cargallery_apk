package com.xiaopeng.xmart.cargallery.view.detail;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.xmart.cargallery.R;
/* compiled from: DetailListAdapter.java */
/* loaded from: classes18.dex */
class DetailListViewHolder extends RecyclerView.ViewHolder {
    ImageView mIvPlay;
    ImageView mIvThumb;
    ImageView mIvThumbCover;
    FrameLayout mRootView;

    public DetailListViewHolder(View itemView) {
        super(itemView);
        this.mRootView = (FrameLayout) itemView.findViewById(R.id.fl_root_view);
        this.mIvPlay = (ImageView) itemView.findViewById(R.id.item_detail_play);
        this.mIvThumb = (ImageView) itemView.findViewById(R.id.item_detail_thumbnail);
        this.mIvThumbCover = (ImageView) itemView.findViewById(R.id.item_detail_thumbnail_cover);
    }
}
