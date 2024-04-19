package com.xiaopeng.xmart.cargallery.view.player;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.xiaopeng.xmart.cargallery.CameraLog;
/* loaded from: classes20.dex */
public class CustomVideoView extends TextureVideoView implements View.OnTouchListener {
    private static final String TAG = "CustomVideoView";
    private float lastX;
    private float lastY;
    private StateListener mStateListener;
    private int thresold;

    /* loaded from: classes20.dex */
    public interface StateListener {
        void changeBrightness(float detlaX);

        void changeVolumn(float detlaY);

        void hideHint();
    }

    public CustomVideoView(Context context) {
        this(context, null);
    }

    public CustomVideoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context.getApplicationContext(), attrs, defStyleAttr);
        this.thresold = 30;
    }

    public void setStateListener(StateListener stateListener) {
        this.mStateListener = stateListener;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xmart.cargallery.view.player.TextureVideoView, android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getDefaultSize(1920, widthMeasureSpec);
        int height = getDefaultSize(1080, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case 0:
                this.lastX = event.getX();
                this.lastY = event.getY();
                return true;
            case 1:
                this.mStateListener.hideHint();
                return true;
            case 2:
                float detlaX = event.getX() - this.lastX;
                float detlaY = event.getY() - this.lastY;
                if (Math.abs(detlaX) > this.thresold && Math.abs(detlaY) < this.thresold) {
                    this.mStateListener.changeBrightness(detlaX);
                }
                if (Math.abs(detlaX) < this.thresold && Math.abs(detlaY) > this.thresold) {
                    this.mStateListener.changeVolumn(detlaY);
                }
                this.lastX = event.getX();
                this.lastY = event.getY();
                return true;
            default:
                return true;
        }
    }

    public void cleanup() {
        CameraLog.d(TAG, "video resource clean up...", false);
        suspend();
        if (this.mSurface != null) {
            this.mSurface.release();
            this.mSurface = null;
        }
    }
}
