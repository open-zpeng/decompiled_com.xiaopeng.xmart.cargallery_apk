package com.xiaopeng.xui.drawable;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.DecelerateInterpolator;
import com.xiaopeng.xpui.R;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
/* loaded from: classes.dex */
public class XSwitchThumbDrawable extends Drawable {
    private static final int DEFAULT_HEIGHT = 44;
    private static final int DEFAULT_INDICATOR_HEIGHT = 18;
    private static final int DEFAULT_INDICATOR_RADIUS = 3;
    private static final int DEFAULT_INDICATOR_WIDTH = 6;
    private static final int DEFAULT_PADDING = 4;
    private static final int DEFAULT_THUMB_RADIUS = 7;
    private static final int DEFAULT_WIDTH = 44;
    private static final int INDICATOR_COLOR = -14176402;
    private static final long INDICATOR_DURATION = 200;
    private static final String TAG = "XSwitchThumbDrawable";
    private static final int THUMB_COLOR = -1;
    private boolean mChecked;
    private boolean mEnableIndicator;
    private boolean mEnabled;
    private int mHeight;
    private ValueAnimator mIndicatorAnimator;
    private ColorStateList mIndicatorColors;
    private float mIndicatorPaddingStart;
    private float mIndicatorPaddingTop;
    private final Paint mIndicatorPaint;
    private int mIndicatorRoundRadius;
    private float mIndicatorStartOffset;
    private float mIndicatorTopOffset;
    private float mPadding;
    private ColorStateList mThumbColors;
    private final Paint mThumbPaint;
    private int mThumbRoundRadius;
    private int mWidth;

    public XSwitchThumbDrawable() {
        Paint paint = new Paint(1);
        this.mThumbPaint = paint;
        Paint paint2 = new Paint(1);
        this.mIndicatorPaint = paint2;
        this.mChecked = false;
        this.mEnabled = true;
        this.mEnableIndicator = false;
        this.mIndicatorTopOffset = 0.0f;
        this.mIndicatorStartOffset = 0.0f;
        paint.setColor(-1);
        paint.setStyle(Paint.Style.FILL);
        paint2.setColor(INDICATOR_COLOR);
        paint2.setStyle(Paint.Style.FILL);
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(Resources r, XmlPullParser parser, AttributeSet attrs, Resources.Theme theme) throws IOException, XmlPullParserException {
        TypedArray ta;
        super.inflate(r, parser, attrs, theme);
        if (theme != null) {
            ta = theme.obtainStyledAttributes(attrs, R.styleable.XSwitchThumbDrawable, 0, 0);
        } else {
            ta = r.obtainAttributes(attrs, R.styleable.XSwitchThumbDrawable);
        }
        this.mWidth = ta.getDimensionPixelSize(R.styleable.XSwitchThumbDrawable_switch_thumb_width, 44);
        this.mHeight = ta.getDimensionPixelSize(R.styleable.XSwitchThumbDrawable_switch_thumb_height, 44);
        this.mPadding = ta.getDimension(R.styleable.XSwitchThumbDrawable_switch_thumb_padding, 4.0f);
        this.mThumbRoundRadius = ta.getDimensionPixelSize(R.styleable.XSwitchThumbDrawable_switch_thumb_round_radius, 7);
        this.mIndicatorRoundRadius = ta.getDimensionPixelSize(R.styleable.XSwitchThumbDrawable_switch_thumb_indicator_round_radius, 3);
        float indicatorWidth = ta.getDimension(R.styleable.XSwitchThumbDrawable_switch_thumb_indicator_width, 6.0f);
        float indicatorHeight = ta.getDimension(R.styleable.XSwitchThumbDrawable_switch_thumb_indicator_height, 18.0f);
        this.mIndicatorPaddingStart = Math.round((this.mWidth - indicatorWidth) / 2.0f);
        this.mIndicatorPaddingTop = Math.round((this.mHeight - indicatorHeight) / 2.0f);
        ColorStateList colorStateList = ta.getColorStateList(R.styleable.XSwitchThumbDrawable_switch_thumb_indicator_color);
        this.mIndicatorColors = colorStateList;
        if (colorStateList == null) {
            this.mIndicatorColors = ColorStateList.valueOf(INDICATOR_COLOR);
        }
        this.mIndicatorPaint.setColor(this.mIndicatorColors.getDefaultColor());
        ColorStateList colorStateList2 = ta.getColorStateList(R.styleable.XSwitchThumbDrawable_switch_thumb_color);
        this.mThumbColors = colorStateList2;
        if (colorStateList2 == null) {
            this.mThumbColors = ColorStateList.valueOf(-1);
        }
        this.mThumbPaint.setColor(this.mThumbColors.getDefaultColor());
        ta.recycle();
        initAnimator();
    }

    private void initAnimator() {
        final float offsetStart = (this.mHeight / 2.0f) - this.mIndicatorPaddingTop;
        ValueAnimator ofInt = ValueAnimator.ofInt(0, 100);
        this.mIndicatorAnimator = ofInt;
        ofInt.setDuration(INDICATOR_DURATION);
        this.mIndicatorAnimator.setInterpolator(new DecelerateInterpolator());
        this.mIndicatorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.xiaopeng.xui.drawable.-$$Lambda$XSwitchThumbDrawable$-gy1wv4AgqewfiGYH7_raGMJxtk
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                XSwitchThumbDrawable.this.lambda$initAnimator$0$XSwitchThumbDrawable(offsetStart, valueAnimator);
            }
        });
        this.mIndicatorAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.xiaopeng.xui.drawable.XSwitchThumbDrawable.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                Log.d(XSwitchThumbDrawable.TAG, "onAnimationCancel");
                XSwitchThumbDrawable xSwitchThumbDrawable = XSwitchThumbDrawable.this;
                xSwitchThumbDrawable.mEnableIndicator = xSwitchThumbDrawable.mChecked;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Log.d(XSwitchThumbDrawable.TAG, "onAnimationCancel");
                XSwitchThumbDrawable xSwitchThumbDrawable = XSwitchThumbDrawable.this;
                xSwitchThumbDrawable.mEnableIndicator = xSwitchThumbDrawable.mChecked;
            }
        });
    }

    public /* synthetic */ void lambda$initAnimator$0$XSwitchThumbDrawable(float offsetStart, ValueAnimator animation) {
        int current = ((Integer) animation.getAnimatedValue()).intValue();
        if (this.mChecked) {
            this.mIndicatorTopOffset = offsetStart - Math.round((current / 100.0f) * offsetStart);
        } else {
            this.mIndicatorTopOffset = Math.round((current / 100.0f) * offsetStart);
        }
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        int count = canvas.save();
        Rect bounds = getBounds();
        float f = bounds.left + this.mPadding;
        float f2 = bounds.top + this.mPadding;
        float f3 = bounds.right - this.mPadding;
        float f4 = bounds.bottom - this.mPadding;
        int i = this.mThumbRoundRadius;
        canvas.drawRoundRect(f, f2, f3, f4, i, i, this.mThumbPaint);
        if (this.mEnableIndicator) {
            float f5 = bounds.left + this.mIndicatorPaddingStart + this.mIndicatorStartOffset;
            float f6 = bounds.top + this.mIndicatorPaddingTop + this.mIndicatorTopOffset;
            float f7 = (bounds.right - this.mIndicatorPaddingStart) - this.mIndicatorStartOffset;
            float f8 = (bounds.bottom - this.mIndicatorPaddingTop) - this.mIndicatorTopOffset;
            int i2 = this.mIndicatorRoundRadius;
            canvas.drawRoundRect(f5, f6, f7, f8, i2, i2, this.mIndicatorPaint);
        }
        canvas.restoreToCount(count);
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int alpha) {
        this.mThumbPaint.setAlpha(alpha);
        this.mIndicatorPaint.setAlpha(alpha);
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.mWidth;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.mHeight;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onStateChange(int[] state) {
        this.mIndicatorPaint.setColor(this.mIndicatorColors.getColorForState(state, INDICATOR_COLOR));
        this.mThumbPaint.setColor(this.mThumbColors.getColorForState(state, -1));
        boolean checked = false;
        boolean enabled = false;
        for (int s : state) {
            if (s == 16842912) {
                checked = true;
            } else if (s == 16842910) {
                enabled = true;
            }
        }
        boolean checkChanged = this.mChecked != checked;
        if (checkChanged) {
            this.mChecked = checked;
            this.mEnableIndicator = true;
            if (getBounds().isEmpty()) {
                Log.d(TAG, "onStateChange: checked:" + checked + ", ignore animation, bounds:" + getBounds());
            } else {
                Log.d(TAG, "onStateChange: checked:" + checked + ", with animation, bounds:" + getBounds());
                ValueAnimator valueAnimator = this.mIndicatorAnimator;
                if (valueAnimator != null) {
                    valueAnimator.cancel();
                    this.mIndicatorAnimator.start();
                }
            }
        }
        boolean enableChanged = this.mEnabled != enabled;
        if (enableChanged) {
            Log.d(TAG, "onStateChange: enabled:" + enabled);
            this.mEnabled = enabled;
        }
        return checkChanged;
    }
}
