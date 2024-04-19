package com.xiaopeng.xmart.cargallery.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import com.xiaopeng.xmart.cargallery.CameraLog;
/* loaded from: classes19.dex */
public class NumberIndicatorView extends View {
    private static final String TAG = "NumberIndicatorView";
    private int mCircleColor;
    private final int mDesireHeight;
    private final int mDesireWidth;
    private int mNumber;
    private Paint mPaint;
    private final int mRingWidth;
    private int mTextColor;
    private Paint mTextPaint;
    private final int mTextSize;

    public NumberIndicatorView(Context context) {
        super(context);
        this.mDesireWidth = 42;
        this.mDesireHeight = 42;
        this.mRingWidth = 4;
        this.mTextSize = 26;
        this.mCircleColor = -13777667;
        this.mTextColor = -1;
        this.mPaint = new Paint(1);
        this.mTextPaint = new Paint(1);
        initView();
    }

    public NumberIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mDesireWidth = 42;
        this.mDesireHeight = 42;
        this.mRingWidth = 4;
        this.mTextSize = 26;
        this.mCircleColor = -13777667;
        this.mTextColor = -1;
        this.mPaint = new Paint(1);
        this.mTextPaint = new Paint(1);
        initView();
    }

    public NumberIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mDesireWidth = 42;
        this.mDesireHeight = 42;
        this.mRingWidth = 4;
        this.mTextSize = 26;
        this.mCircleColor = -13777667;
        this.mTextColor = -1;
        this.mPaint = new Paint(1);
        this.mTextPaint = new Paint(1);
        initView();
    }

    private void initView() {
        this.mTextPaint.setTextSize(26.0f);
        this.mTextPaint.setColor(this.mTextColor);
        this.mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        postDelayed(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.widget.-$$Lambda$NumberIndicatorView$TZoJdXB_NxTJzrfb21koGwJMaEk
            @Override // java.lang.Runnable
            public final void run() {
                NumberIndicatorView.this.lambda$onConfigurationChanged$0$NumberIndicatorView();
            }
        }, 1000L);
    }

    public /* synthetic */ void lambda$onConfigurationChanged$0$NumberIndicatorView() {
        invalidate();
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width;
        int height;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (View.MeasureSpec.getMode(widthMeasureSpec) == Integer.MIN_VALUE) {
            width = 42;
        } else {
            width = getMeasuredWidth();
        }
        if (View.MeasureSpec.getMode(heightMeasureSpec) == Integer.MIN_VALUE) {
            height = 42;
        } else {
            height = getMeasuredHeight();
        }
        setMeasuredDimension(width, height);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        RectF rectF = new RectF(0.0f, 0.0f, width, height);
        canvas.drawArc(rectF, 0.0f, 360.0f, false, this.mPaint);
        this.mPaint.setColor(this.mCircleColor);
        canvas.drawCircle(width / 2.0f, height / 2.0f, height / 2.0f, this.mPaint);
        Paint.FontMetrics fontMetrics = this.mTextPaint.getFontMetrics();
        float top = fontMetrics.top;
        float bottom = fontMetrics.bottom;
        int baseLineY = (int) ((rectF.centerY() - (top / 2.0f)) - (bottom / 2.0f));
        canvas.drawText(this.mNumber + "", rectF.centerX(), baseLineY, this.mTextPaint);
    }

    public void setNumber(int number) {
        this.mNumber = number;
        if (number < 0 || number > 9) {
            CameraLog.d(TAG, "set error mNumber:" + number);
        } else {
            invalidate();
        }
    }
}
