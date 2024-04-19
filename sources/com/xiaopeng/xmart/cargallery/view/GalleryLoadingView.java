package com.xiaopeng.xmart.cargallery.view;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.lzy.okgo.OkGo;
import com.xiaopeng.xmart.cargallery.R;
import com.xiaopeng.xui.widget.XLoading;
/* loaded from: classes17.dex */
public class GalleryLoadingView extends FrameLayout {
    public static final int STATE_DVR_EMPTY = 7;
    public static final int STATE_DVR_RETRY = 5;
    public static final int STATE_DVR_WARNING = 6;
    public static final int STATE_LOAD_EMPTY = 2;
    public static final int STATE_LOAD_FAIL = 3;
    public static final int STATE_LOAD_ING = 1;
    public static final int STATE_LOAD_SUCCESS = 4;
    private Button mButton;
    private ViewStub mButtonStub;
    private Runnable mEnableDVRRetryButton;
    private ImageView mImageView;
    private OnLoadFailedListener mOnLoadFailedListener;
    private OnRetryListener mOnRetryListener;
    private TextView mTextView;
    private CountDownTimer mTimer;
    private TextView mTvDvrRecordTips;
    private XLoading mXLoadingView;

    /* loaded from: classes17.dex */
    public interface OnLoadFailedListener {
        void onLoadOverTime();
    }

    /* loaded from: classes17.dex */
    public interface OnRetryListener {
        void onRetry();
    }

    public GalleryLoadingView(Context context) {
        this(context, null);
    }

    public GalleryLoadingView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public GalleryLoadingView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mTimer = new CountDownTimer(OkGo.DEFAULT_MILLISECONDS, 1000L) { // from class: com.xiaopeng.xmart.cargallery.view.GalleryLoadingView.1
            @Override // android.os.CountDownTimer
            public void onFinish() {
                GalleryLoadingView.this.updateState(3);
                if (GalleryLoadingView.this.mTimer != null) {
                    GalleryLoadingView.this.mTimer.cancel();
                }
            }

            @Override // android.os.CountDownTimer
            public void onTick(long l) {
            }
        };
        this.mEnableDVRRetryButton = new Runnable() { // from class: com.xiaopeng.xmart.cargallery.view.GalleryLoadingView.2
            @Override // java.lang.Runnable
            public void run() {
                if (GalleryLoadingView.this.mButton != null) {
                    GalleryLoadingView.this.mButton.setEnabled(true);
                }
            }
        };
        init();
    }

    private void init() {
        View root = LayoutInflater.from(getContext()).inflate(R.layout.layout_loading_view, this);
        this.mImageView = (ImageView) root.findViewById(R.id.iv_load_result);
        this.mTextView = (TextView) root.findViewById(R.id.tv_load_result);
        this.mTvDvrRecordTips = (TextView) root.findViewById(R.id.tv_load_result_tips);
        this.mXLoadingView = (XLoading) root.findViewById(R.id.iv_loading_bar);
        this.mButtonStub = (ViewStub) root.findViewById(R.id.btn_stub);
        this.mButton = null;
    }

    public void showLoading() {
        updateState(1);
    }

    public void hideLoading() {
        updateState(4);
    }

    public void setText(int resId) {
        this.mTextView.setText(resId);
    }

    public void updateState(int state) {
        switch (state) {
            case 1:
                this.mImageView.clearAnimation();
                this.mXLoadingView.setVisibility(0);
                this.mImageView.setVisibility(8);
                this.mTextView.setText((CharSequence) null);
                this.mTextView.setVisibility(8);
                this.mTvDvrRecordTips.setVisibility(8);
                Button button = this.mButton;
                if (button != null) {
                    button.setVisibility(8);
                }
                setVisibility(0);
                CountDownTimer countDownTimer = this.mTimer;
                if (countDownTimer != null) {
                    countDownTimer.start();
                    return;
                }
                return;
            case 2:
                this.mXLoadingView.setVisibility(8);
                this.mImageView.clearAnimation();
                this.mImageView.setImageResource(R.drawable.ic_xxlarge_nopic);
                this.mImageView.setVisibility(0);
                this.mTextView.setText(R.string.album_list_empty);
                this.mTextView.setVisibility(0);
                this.mTvDvrRecordTips.setVisibility(8);
                Button button2 = this.mButton;
                if (button2 != null) {
                    button2.setVisibility(8);
                }
                setVisibility(0);
                CountDownTimer countDownTimer2 = this.mTimer;
                if (countDownTimer2 != null) {
                    countDownTimer2.cancel();
                    return;
                }
                return;
            case 3:
                this.mXLoadingView.setVisibility(8);
                this.mImageView.clearAnimation();
                this.mImageView.setImageResource(R.drawable.ic_xxlarge_nopic);
                this.mImageView.setVisibility(0);
                this.mTextView.setText(R.string.album_list_failed);
                this.mTextView.setVisibility(0);
                this.mTvDvrRecordTips.setVisibility(8);
                if (this.mButton == null) {
                    inflateRetryButton();
                }
                this.mButton.setVisibility(0);
                this.mButton.setText(R.string.btn_retry);
                setVisibility(0);
                CountDownTimer countDownTimer3 = this.mTimer;
                if (countDownTimer3 != null) {
                    countDownTimer3.cancel();
                }
                OnLoadFailedListener onLoadFailedListener = this.mOnLoadFailedListener;
                if (onLoadFailedListener != null) {
                    onLoadFailedListener.onLoadOverTime();
                    return;
                }
                return;
            case 4:
                this.mXLoadingView.setVisibility(8);
                this.mImageView.clearAnimation();
                this.mImageView.setImageResource(R.drawable.ic_xxlarge_nopic);
                this.mImageView.setVisibility(0);
                setVisibility(8);
                CountDownTimer countDownTimer4 = this.mTimer;
                if (countDownTimer4 != null) {
                    countDownTimer4.cancel();
                    return;
                }
                return;
            case 5:
                this.mXLoadingView.setVisibility(8);
                if (this.mButton == null) {
                    inflateRetryButton();
                }
                this.mButton.setEnabled(false);
                postDelayed(this.mEnableDVRRetryButton, 1000L);
                this.mButton.setVisibility(0);
                this.mButton.setText(R.string.btn_retry);
                this.mImageView.clearAnimation();
                this.mImageView.setImageResource(R.drawable.ic_xxlarge_nopic);
                this.mImageView.setVisibility(0);
                this.mTvDvrRecordTips.setVisibility(0);
                setVisibility(0);
                this.mTextView.setText(R.string.album_list_dvr_p);
                this.mTextView.setVisibility(0);
                CountDownTimer countDownTimer5 = this.mTimer;
                if (countDownTimer5 != null) {
                    countDownTimer5.cancel();
                    return;
                }
                return;
            case 6:
                this.mXLoadingView.setVisibility(8);
                if (this.mButton == null) {
                    inflateRetryButton();
                }
                this.mButton.setVisibility(8);
                this.mImageView.clearAnimation();
                this.mImageView.setImageResource(R.drawable.ic_xxlarge_warning);
                this.mImageView.setVisibility(0);
                this.mTvDvrRecordTips.setVisibility(0);
                setVisibility(0);
                this.mTextView.setText(R.string.album_list_dvr_p);
                this.mTextView.setVisibility(0);
                CountDownTimer countDownTimer6 = this.mTimer;
                if (countDownTimer6 != null) {
                    countDownTimer6.cancel();
                    return;
                }
                return;
            case 7:
                this.mXLoadingView.setVisibility(8);
                if (this.mButton == null) {
                    inflateRetryButton();
                }
                this.mButton.setText(R.string.album_list_dvr_button_empty);
                this.mButton.setEnabled(false);
                postDelayed(this.mEnableDVRRetryButton, 1000L);
                this.mButton.setVisibility(0);
                this.mImageView.clearAnimation();
                this.mImageView.setImageResource(R.drawable.ic_xxlarge_nopic);
                this.mImageView.setVisibility(0);
                this.mTvDvrRecordTips.setVisibility(0);
                setVisibility(0);
                this.mTextView.setText(R.string.album_list_dvr_continue);
                this.mTextView.setVisibility(0);
                CountDownTimer countDownTimer7 = this.mTimer;
                if (countDownTimer7 != null) {
                    countDownTimer7.cancel();
                    return;
                }
                return;
            default:
                return;
        }
    }

    public void setOnRetryListener(OnRetryListener listener) {
        this.mOnRetryListener = listener;
    }

    public void setOnLoadFailedListener(OnLoadFailedListener onLoadFailedListener) {
        this.mOnLoadFailedListener = onLoadFailedListener;
    }

    public void onStop() {
        CountDownTimer countDownTimer = this.mTimer;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    public void onDestroy() {
        CountDownTimer countDownTimer = this.mTimer;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        this.mTimer = null;
        removeCallbacks(this.mEnableDVRRetryButton);
    }

    private void inflateRetryButton() {
        Button button = (Button) this.mButtonStub.inflate();
        this.mButton = button;
        button.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.xmart.cargallery.view.-$$Lambda$GalleryLoadingView$z3YWhkGMfSIUzHEKRwdBRxh1xc4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                GalleryLoadingView.this.lambda$inflateRetryButton$0$GalleryLoadingView(view);
            }
        });
    }

    public /* synthetic */ void lambda$inflateRetryButton$0$GalleryLoadingView(View view) {
        OnRetryListener onRetryListener = this.mOnRetryListener;
        if (onRetryListener != null) {
            onRetryListener.onRetry();
        }
    }
}
