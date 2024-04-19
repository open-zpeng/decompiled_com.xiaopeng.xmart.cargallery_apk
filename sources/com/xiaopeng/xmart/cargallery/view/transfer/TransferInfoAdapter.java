package com.xiaopeng.xmart.cargallery.view.transfer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.xiaopeng.xmart.cargallery.R;
import com.xiaopeng.xmart.cargallery.bean.TransferProgressEvent;
import com.xiaopeng.xmart.cargallery.utils.FileUtils;
import java.util.List;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes21.dex */
public class TransferInfoAdapter extends RecyclerView.Adapter<TransferVH> {
    private Context mContext;
    private List<TransferProgressEvent> mInfoData;
    private RequestOptions mPhotoOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE).priority(Priority.LOW);
    private RequestOptions mVideoOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE).priority(Priority.HIGH);

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public /* bridge */ /* synthetic */ void onBindViewHolder(TransferVH holder, int position, List payloads) {
        onBindViewHolder2(holder, position, (List<Object>) payloads);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public TransferInfoAdapter(List<TransferProgressEvent> infoDatas) {
        this.mInfoData = infoDatas;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public TransferVH onCreateViewHolder(ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transfer_info, parent, false);
        return new TransferVH(view);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(TransferVH holder, int position) {
        holder.rootView.setBackground(ContextCompat.getDrawable(this.mContext, R.drawable.bg_item_transfer_info));
        holder.mIvPlay.setImageDrawable(ContextCompat.getDrawable(this.mContext, R.drawable.ic_large_play));
        holder.mTvFileName.setTextColor(ContextCompat.getColor(this.mContext, R.color.x_theme_text_01));
        holder.mTvFileSize.setTextColor(ContextCompat.getColor(this.mContext, R.color.file_transfer_size));
        TransferProgressEvent ev = this.mInfoData.get(position);
        holder.mTvFileName.setText(FileUtils.getFileName(ev.getFileName()));
        holder.mTvFileSize.setText(FileUtils.convertFileSize(ev.getFileSize()));
        updateProgress(holder, ev);
        boolean isVideo = FileUtils.isMp4File(ev.getFileName());
        if (isVideo) {
            holder.mTvDuration.setText(ev.getDuration());
            holder.mIvPlay.setVisibility(0);
            holder.mTvDuration.setVisibility(0);
            this.mPhotoOptions.placeholder(R.drawable.ic_large_play);
            Glide.with(this.mContext).load(ev.getFileName()).apply((BaseRequestOptions<?>) this.mVideoOptions).into(holder.mImageView);
            return;
        }
        this.mPhotoOptions.placeholder(R.drawable.ic_mid_picture);
        Glide.with(this.mContext).load(ev.getFileName()).apply((BaseRequestOptions<?>) this.mPhotoOptions).into(holder.mImageView);
        holder.mIvPlay.setVisibility(8);
        holder.mTvDuration.setVisibility(8);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mInfoData.size();
    }

    /* renamed from: onBindViewHolder  reason: avoid collision after fix types in other method */
    public void onBindViewHolder2(TransferVH holder, int position, List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
            return;
        }
        TransferProgressEvent ev = this.mInfoData.get(position);
        if (ev != null) {
            updateProgress(holder, ev);
        }
    }

    private void updateProgress(TransferVH holder, TransferProgressEvent ev) {
        if (ev.getProgress() < 0) {
            holder.mProgressBar.setVisibility(8);
            holder.mTvProgress.setVisibility(8);
            holder.mTvProgressStatue.setVisibility(0);
            holder.mTvProgressStatue.setTextColor(ContextCompat.getColor(this.mContext, R.color.file_transfer_transfer_fail));
            holder.mTvProgressStatue.setText(R.string.transfer_file_failure);
            holder.mIvTransferStatue.setImageResource(R.drawable.ic_transfer_error);
            holder.mIvTransferStatue.setVisibility(0);
            return;
        }
        int progress = (int) ((((float) (ev.getProgress() * 100)) / ((float) ev.getFileSize())) + 0.5d);
        if (progress >= 100) {
            holder.mTvProgressStatue.setText(R.string.transfer_file_finish);
            holder.mTvProgressStatue.setTextColor(this.mContext.getResources().getColor(R.color.file_transfer_transfer_complete));
            holder.mTvProgressStatue.setVisibility(0);
            holder.mProgressBar.setVisibility(8);
            holder.mTvProgress.setVisibility(8);
            holder.mIvTransferStatue.setImageResource(R.drawable.ic_transfer_success);
            holder.mIvTransferStatue.setVisibility(0);
            return;
        }
        holder.mTvProgressStatue.setVisibility(8);
        holder.mProgressBar.setVisibility(0);
        holder.mTvProgress.setVisibility(0);
        holder.mIvTransferStatue.setVisibility(8);
        holder.mTvProgress.setText(progress + "%");
        holder.mProgressBar.setProgress(progress);
    }

    /* loaded from: classes21.dex */
    public static class TransferVH extends RecyclerView.ViewHolder {
        ImageView mImageView;
        ImageView mIvPlay;
        ImageView mIvTransferStatue;
        ProgressBar mProgressBar;
        TextView mTvDuration;
        TextView mTvFileName;
        TextView mTvFileSize;
        TextView mTvProgress;
        TextView mTvProgressStatue;
        View rootView;

        TransferVH(View itemView) {
            super(itemView);
            this.rootView = itemView.findViewById(R.id.rl_root);
            this.mTvFileName = (TextView) itemView.findViewById(R.id.tv_file_name);
            this.mTvFileSize = (TextView) itemView.findViewById(R.id.tv_file_size);
            this.mTvProgress = (TextView) itemView.findViewById(R.id.tv_file_progress);
            this.mTvProgressStatue = (TextView) itemView.findViewById(R.id.tv_file_progress_statue);
            this.mProgressBar = (ProgressBar) itemView.findViewById(R.id.pb_transfer_progress);
            this.mImageView = (ImageView) itemView.findViewById(R.id.iv_file_thumb_nail);
            this.mIvPlay = (ImageView) itemView.findViewById(R.id.iv_play);
            this.mIvTransferStatue = (ImageView) itemView.findViewById(R.id.iv_transfer_statue);
            this.mTvDuration = (TextView) itemView.findViewById(R.id.tv_duration);
        }
    }
}
