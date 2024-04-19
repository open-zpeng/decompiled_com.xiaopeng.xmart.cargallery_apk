package com.xiaopeng.xmart.cargallery.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import com.xiaopeng.xmart.cargallery.R;
/* loaded from: classes17.dex */
public class ProgressView extends View {
    private static final int COLOR_EMPTY_DEFAULT = Color.parseColor("#280F1520");
    private static final int COLOR_LOADED_DEFAULT = Color.parseColor("#FF3B99FF");
    private long mCurrentProgress;
    private int mHeight;
    private long mMaxProgress;
    private Paint mPaintProgressEmpty;
    private Paint mPaintProgressLoaded;
    private int mWidth;

    public ProgressView(Context context) {
        this(context, null);
    }

    public ProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mMaxProgress = 0L;
        this.mCurrentProgress = 0L;
        init(context, attrs);
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.mWidth = View.MeasureSpec.getSize(widthMeasureSpec);
        this.mHeight = View.MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.drawRect(0.0f, 0.0f, this.mWidth, this.mHeight, this.mPaintProgressEmpty);
        canvas.drawRect(0.0f, 0.0f, calculatePositionIndex(this.mCurrentProgress), this.mHeight, this.mPaintProgressLoaded);
        canvas.restore();
    }

    public void setProgress(long[] progressInfo) {
        if (progressInfo[0] <= 0) {
            throw new IllegalArgumentException("Maximum progress value should great than0");
        }
        if (progressInfo[1] < 0) {
            throw new IllegalArgumentException("Current progress should great or equal to 0");
        }
        this.mMaxProgress = progressInfo[0];
        this.mCurrentProgress = progressInfo[1];
        postInvalidate();
    }

    private int calculatePositionIndex(long currentProgress) {
        long j = this.mMaxProgress;
        if (j <= 0) {
            return 0;
        }
        return (int) (((((float) currentProgress) * 1.0f) * this.mWidth) / ((float) j));
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ProgressView);
        this.mMaxProgress = a.getInt(2, 0);
        int loadedColor = a.getColor(1, COLOR_LOADED_DEFAULT);
        int emptyColor = a.getColor(0, COLOR_EMPTY_DEFAULT);
        a.recycle();
        Paint paint = new Paint();
        this.mPaintProgressEmpty = paint;
        paint.setColor(emptyColor);
        this.mPaintProgressEmpty.setStyle(Paint.Style.FILL);
        this.mPaintProgressEmpty.setAntiAlias(true);
        Paint paint2 = new Paint();
        this.mPaintProgressLoaded = paint2;
        paint2.setColor(loadedColor);
        this.mPaintProgressLoaded.setStyle(Paint.Style.FILL);
        this.mPaintProgressLoaded.setAntiAlias(true);
    }
}
