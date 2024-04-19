package com.xiaopeng.xmart.cargallery.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
/* loaded from: classes19.dex */
public class SpeechInputView extends View {
    private static final String TAG = "SpeechInputView";
    private final int mBarGap;
    private final int mInnerFixedHeight;
    private final int mMidFixedHeight;
    private ValueAnimator mObjectAnimator;
    private final int mOuterFixedHeight;
    private Paint mPaint;
    private int microColor;
    private float step;

    public SpeechInputView(Context context) {
        super(context);
        this.mInnerFixedHeight = 30;
        this.mOuterFixedHeight = 16;
        this.mMidFixedHeight = 20;
        this.mBarGap = 12;
        this.mPaint = new Paint(1);
        this.microColor = -9322979;
        initView(context);
    }

    public SpeechInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mInnerFixedHeight = 30;
        this.mOuterFixedHeight = 16;
        this.mMidFixedHeight = 20;
        this.mBarGap = 12;
        this.mPaint = new Paint(1);
        this.microColor = -9322979;
        initView(context);
    }

    public SpeechInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mInnerFixedHeight = 30;
        this.mOuterFixedHeight = 16;
        this.mMidFixedHeight = 20;
        this.mBarGap = 12;
        this.mPaint = new Paint(1);
        this.microColor = -9322979;
        initView(context);
    }

    public SpeechInputView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mInnerFixedHeight = 30;
        this.mOuterFixedHeight = 16;
        this.mMidFixedHeight = 20;
        this.mBarGap = 12;
        this.mPaint = new Paint(1);
        this.microColor = -9322979;
        initView(context);
    }

    private void initView(Context context) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, "step", 0.0f, 6.2831855f);
        this.mObjectAnimator = ofFloat;
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.xiaopeng.xmart.cargallery.widget.-$$Lambda$SpeechInputView$Z3Q8tErSCnciXtsj25zzOxS5wPE
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                SpeechInputView.this.lambda$initView$0$SpeechInputView(valueAnimator);
            }
        });
        this.mObjectAnimator.setRepeatCount(-1);
        this.mObjectAnimator.setDuration(1000L);
        this.mObjectAnimator.start();
        this.mPaint.setColor(this.microColor);
        this.mPaint.setStrokeCap(Paint.Cap.ROUND);
        this.mPaint.setStrokeWidth(6.0f);
    }

    public /* synthetic */ void lambda$initView$0$SpeechInputView(ValueAnimator animation) {
        invalidate();
    }

    public float getStep() {
        return this.step;
    }

    public void setStep(float step) {
        this.step = step;
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawDetect(canvas);
    }

    private void drawDetect(Canvas canvas) {
        int centX = getWidth() / 2;
        int centY = getHeight() / 2;
        this.mPaint.setAlpha(255);
        float innerStart = (float) ((centY - (Math.sin(this.step) * 20.0d)) - 15.0d);
        float innerEnd = (float) (centY + (Math.sin(this.step) * 20.0d) + 15.0d);
        canvas.drawLine(centX, innerStart, centX, innerEnd, this.mPaint);
        this.mPaint.setAlpha(92);
        float midStart = (float) ((centY - (Math.sin(this.step) * 10.0d)) - 10.0d);
        float midEnd = (float) (centY + (Math.sin(this.step) * 10.0d) + 10.0d);
        canvas.drawLine(centX + 12, midStart, centX + 12, midEnd, this.mPaint);
        canvas.drawLine(centX - 12, midStart, centX - 12, midEnd, this.mPaint);
        float outerStart = (float) ((centY - (Math.sin(this.step * 2.0f) * 10.0d)) - 8.0d);
        float outerEnd = (float) (centY + (Math.sin(this.step * 2.0f) * 10.0d) + 8.0d);
        canvas.drawLine(centX + 24, outerStart, centX + 24, outerEnd, this.mPaint);
        canvas.drawLine(centX - 24, outerStart, centX - 24, outerEnd, this.mPaint);
    }

    @Override // android.view.View
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        ValueAnimator valueAnimator = this.mObjectAnimator;
        if (valueAnimator != null) {
            if (visibility == 8) {
                valueAnimator.cancel();
            } else {
                valueAnimator.start();
            }
        }
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ValueAnimator valueAnimator = this.mObjectAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
    }
}
