package com.xiaopeng.xmart.cargallery.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import com.xiaopeng.xui.theme.XThemeManager;
/* loaded from: classes19.dex */
public class PublishProgressView extends View {
    private boolean isNight;
    private final int mBallColorDay;
    private final int mBallColorNight;
    private final int mBallCount;
    private final int mBallMaxSize;
    private final int mBallMinSize;
    private Paint mBallPaint;
    private final float mBallRadius;
    private final float mBallRadiusGap;
    private ValueAnimator mValueAnimator;
    private int step;

    public PublishProgressView(Context context) {
        super(context);
        this.mBallCount = 12;
        this.mBallMaxSize = 16;
        this.mBallMinSize = 5;
        this.mBallRadius = 16.0f;
        this.mBallRadiusGap = 11.0f;
        this.mBallColorDay = 3906047;
        this.mBallColorNight = 9090559;
        this.mBallPaint = new Paint(1);
        initView();
    }

    public PublishProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mBallCount = 12;
        this.mBallMaxSize = 16;
        this.mBallMinSize = 5;
        this.mBallRadius = 16.0f;
        this.mBallRadiusGap = 11.0f;
        this.mBallColorDay = 3906047;
        this.mBallColorNight = 9090559;
        this.mBallPaint = new Paint(1);
        initView();
    }

    public PublishProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mBallCount = 12;
        this.mBallMaxSize = 16;
        this.mBallMinSize = 5;
        this.mBallRadius = 16.0f;
        this.mBallRadiusGap = 11.0f;
        this.mBallColorDay = 3906047;
        this.mBallColorNight = 9090559;
        this.mBallPaint = new Paint(1);
        initView();
    }

    public PublishProgressView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mBallCount = 12;
        this.mBallMaxSize = 16;
        this.mBallMinSize = 5;
        this.mBallRadius = 16.0f;
        this.mBallRadiusGap = 11.0f;
        this.mBallColorDay = 3906047;
        this.mBallColorNight = 9090559;
        this.mBallPaint = new Paint(1);
        initView();
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        this.isNight = XThemeManager.isNight(getContext());
        postDelayed(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.widget.-$$Lambda$PublishProgressView$3ftAAlHUm1MVVsGmWCpy3IhJ--Y
            @Override // java.lang.Runnable
            public final void run() {
                PublishProgressView.this.lambda$onConfigurationChanged$0$PublishProgressView();
            }
        }, 1000L);
    }

    public /* synthetic */ void lambda$onConfigurationChanged$0$PublishProgressView() {
        invalidate();
    }

    private void initView() {
        this.isNight = XThemeManager.isNight(getContext());
        initAnimator();
        initPaint();
    }

    private void initPaint() {
        this.mBallPaint.setColor(3906047);
    }

    private void initAnimator() {
        ValueAnimator ofInt = ValueAnimator.ofInt(12, 0);
        this.mValueAnimator = ofInt;
        ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.xiaopeng.xmart.cargallery.widget.-$$Lambda$PublishProgressView$zmRllydr1h7Opu-Qt-oPAkBifgk
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                PublishProgressView.this.lambda$initAnimator$1$PublishProgressView(valueAnimator);
            }
        });
        this.mValueAnimator.setInterpolator(new LinearInterpolator());
        this.mValueAnimator.setRepeatCount(-1);
        this.mValueAnimator.setDuration(2000L);
        this.mValueAnimator.start();
    }

    public /* synthetic */ void lambda$initAnimator$1$PublishProgressView(ValueAnimator animation) {
        this.step = ((Integer) animation.getAnimatedValue()).intValue();
        invalidate();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int centX = getWidth() / 2;
        int centY = getHeight() / 2;
        this.mBallPaint.setColor(this.isNight ? 9090559 : 3906047);
        float innerRadius = Math.min(centX, centY);
        for (int i = 0; i < 12; i++) {
            this.mBallPaint.setAlpha((int) ((((this.step + i) % 12) * 255) / 12.0f));
            double ballCentX = innerRadius + ((innerRadius - 16.0f) * Math.cos((i * 6.283185307179586d) / 12.0d));
            double ballCentY = innerRadius + ((innerRadius - 16.0f) * Math.sin((i * 6.283185307179586d) / 12.0d));
            canvas.drawCircle((float) ballCentX, (float) ballCentY, ((((this.step + i) % 12) * 11.0f) / 12.0f) + 5.0f, this.mBallPaint);
        }
    }

    public void pause() {
        ValueAnimator valueAnimator = this.mValueAnimator;
        if (valueAnimator != null) {
            valueAnimator.pause();
        }
    }

    public void resume() {
        ValueAnimator valueAnimator = this.mValueAnimator;
        if (valueAnimator != null) {
            valueAnimator.resume();
        }
    }

    public void destroy() {
        ValueAnimator valueAnimator = this.mValueAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
            this.mValueAnimator = null;
        }
    }
}
