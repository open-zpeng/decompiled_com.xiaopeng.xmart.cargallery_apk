package com.xiaopeng.xmart.cargallery.view.player;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.VideoDecoder;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.xiaopeng.xmart.cargallery.CameraLog;
import com.xiaopeng.xmart.cargallery.GlideApp;
import com.xiaopeng.xmart.cargallery.R;
import com.xiaopeng.xmart.cargallery.bean.BaseItem;
import com.xiaopeng.xmart.cargallery.view.DetailActivity;
import com.xiaopeng.xmart.cargallery.view.PinchImageView;
import com.xiaopeng.xmart.cargallery.view.detail.IDetailView;
/* loaded from: classes20.dex */
public class SuperPhotoView extends FrameLayout implements IDetailView {
    private static final String TAG = "SuperPhotoView";
    private PinchImageView mImageView;
    private String mPath;
    private RequestOptions mPhotoOptions;
    private RequestOptions mVideoOptions;

    public SuperPhotoView(Context context) {
        this(context, null);
    }

    public SuperPhotoView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SuperPhotoView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    public void init() {
        View root = LayoutInflater.from(getContext()).inflate(R.layout.layout_detail_picture_item, (ViewGroup) this, true);
        this.mImageView = (PinchImageView) root.findViewById(R.id.image_view_product);
        this.mPhotoOptions = new RequestOptions().fitCenter().diskCacheStrategy(DiskCacheStrategy.NONE);
        this.mVideoOptions = new RequestOptions().fitCenter();
    }

    public void setPhotoPath(String path) {
        CameraLog.d(TAG, "setPhotoPath: " + path, false);
        this.mPath = path;
        if (TextUtils.isEmpty(path)) {
            return;
        }
        if (path.endsWith(".jpg")) {
            GlideApp.with(getContext()).load(path).apply((BaseRequestOptions<?>) this.mPhotoOptions).into(this.mImageView);
            return;
        }
        this.mVideoOptions = this.mVideoOptions.frame(0L).diskCacheStrategy(DiskCacheStrategy.RESOURCE).set(VideoDecoder.FRAME_OPTION, 3);
        GlideApp.with(getContext()).load(this.mPath).apply((BaseRequestOptions<?>) this.mVideoOptions).into(this.mImageView);
    }

    public void setOnTopViewChangeListener(DetailActivity.OnTopViewChangeListener listener) {
        this.mImageView.setOnTopViewChangeListener(listener);
    }

    @Override // com.xiaopeng.xmart.cargallery.view.detail.IDetailView
    public void cleanup() {
        this.mImageView.setImageBitmap(null);
    }

    @Override // com.xiaopeng.xmart.cargallery.view.detail.IDetailView
    public void reset() {
        this.mImageView.reset();
    }

    @Override // com.xiaopeng.xmart.cargallery.view.detail.IDetailView
    public void onDestroyItem() {
        cleanup();
    }

    @Override // com.xiaopeng.xmart.cargallery.view.detail.IDetailView
    public void onPause() {
    }

    @Override // com.xiaopeng.xmart.cargallery.view.detail.IDetailView
    public void setData(BaseItem item) {
        if (item != null) {
            setPhotoPath(item.getPath());
        }
    }
}
