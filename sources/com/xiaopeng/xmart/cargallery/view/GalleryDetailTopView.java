package com.xiaopeng.xmart.cargallery.view;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.xiaopeng.xmart.cargallery.R;
import com.xiaopeng.xui.widget.XButton;
/* loaded from: classes17.dex */
public class GalleryDetailTopView extends FrameLayout {
    private View mBackToGalleryBtn;
    private TextView mDateTakenTv;
    private View mDeleteBtn;
    private View mExitBtn;
    private XButton mPublish;
    private View mRootView;
    private XButton mSendOut;

    public GalleryDetailTopView(Context context) {
        super(context);
        initChildView();
    }

    public GalleryDetailTopView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initChildView();
    }

    public GalleryDetailTopView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initChildView();
    }

    private void initChildView() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_detail_top_view, (ViewGroup) this, true);
        this.mRootView = findViewById(R.id.rl_root);
        this.mExitBtn = findViewById(R.id.top_view_exit);
        this.mBackToGalleryBtn = findViewById(R.id.top_view_back_to_list);
        this.mDeleteBtn = findViewById(R.id.top_view_delete);
        this.mDateTakenTv = (TextView) findViewById(R.id.top_view_date_taken);
        this.mSendOut = (XButton) findViewById(R.id.top_view_send_out);
        this.mPublish = (XButton) findViewById(R.id.top_view_publish);
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        View view = this.mRootView;
        if (view != null) {
            view.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_detail_top));
        }
    }

    public void setExitClickListener(View.OnClickListener listener) {
        this.mExitBtn.setOnClickListener(listener);
    }

    public void setTopLeftAnotherClickListener(View.OnClickListener listener) {
        this.mBackToGalleryBtn.setOnClickListener(listener);
        this.mBackToGalleryBtn.setVisibility(listener == null ? 8 : 0);
    }

    public void setDeleteClickListener(View.OnClickListener listener) {
        this.mDeleteBtn.setOnClickListener(listener);
    }

    public void setDateTakenInfo(String dateTaken) {
        this.mDateTakenTv.setText(dateTaken);
    }

    public void setSendClickListener(View.OnClickListener listener) {
        this.mSendOut.setOnClickListener(listener);
    }

    public void setPublishClickListener(View.OnClickListener listener) {
        this.mPublish.setOnClickListener(listener);
    }
}
