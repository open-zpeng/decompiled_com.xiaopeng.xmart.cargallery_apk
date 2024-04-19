package com.xiaopeng.xui.drawable.shimmer;

import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import com.xiaopeng.xpui.R;
import com.xiaopeng.xui.drawable.shimmer.XShimmer;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
@Deprecated
/* loaded from: classes.dex */
public class XShimmerDrawable extends Drawable {
    private XShimmer.Builder mBuilder;
    private final Rect mDrawRect;
    private final Matrix mShaderMatrix;
    private XShimmer mShimmer;
    private final Paint mShimmerPaint;
    private final ValueAnimator.AnimatorUpdateListener mUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.xiaopeng.xui.drawable.shimmer.XShimmerDrawable.1
        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator animation) {
            XShimmerDrawable.this.invalidateSelf();
        }
    };
    private ValueAnimator mValueAnimator;

    public XShimmerDrawable() {
        Paint paint = new Paint();
        this.mShimmerPaint = paint;
        this.mDrawRect = new Rect();
        this.mShaderMatrix = new Matrix();
        paint.setAntiAlias(true);
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(Resources r, XmlPullParser parser, AttributeSet attrs) throws XmlPullParserException, IOException {
        inflate(r, parser, attrs, null);
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(Resources r, XmlPullParser parser, AttributeSet attrs, Resources.Theme theme) throws XmlPullParserException, IOException {
        setShimmer(parseShimmer(r, attrs, theme));
        super.inflate(r, parser, attrs, theme);
    }

    public XShimmer parseShimmer(Resources resources, AttributeSet attrs, Resources.Theme theme) {
        TypedArray typedArray;
        if (attrs == null) {
            return new XShimmer.AlphaHighlightBuilder().build();
        }
        if (resources != null && attrs != null) {
            if (theme != null) {
                typedArray = theme.obtainStyledAttributes(attrs, R.styleable.XShimmerDrawable, 0, 0);
            } else {
                typedArray = resources.obtainAttributes(attrs, R.styleable.XShimmerDrawable);
            }
            try {
                XShimmer.Builder shimmerBuilder = (typedArray.hasValue(R.styleable.XShimmerDrawable_shimmer_colored) && typedArray.getBoolean(R.styleable.XShimmerDrawable_shimmer_colored, false)) ? new XShimmer.ColorHighlightBuilder() : new XShimmer.AlphaHighlightBuilder();
                saveShimmerBuilder(shimmerBuilder);
                return shimmerBuilder.consumeAttributes(typedArray).build();
            } finally {
                typedArray.recycle();
            }
        }
        return null;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        float dx;
        float dy;
        if (this.mShimmer == null || this.mShimmerPaint.getShader() == null) {
            return;
        }
        float tiltTan = (float) Math.tan(Math.toRadians(this.mShimmer.tilt));
        float translateHeight = this.mDrawRect.height() + (this.mDrawRect.width() * tiltTan);
        float translateWidth = this.mDrawRect.width() + (this.mDrawRect.height() * tiltTan);
        ValueAnimator valueAnimator = this.mValueAnimator;
        float animatedValue = valueAnimator != null ? valueAnimator.getAnimatedFraction() : 0.0f;
        switch (this.mShimmer.direction) {
            case 1:
                dx = 0.0f;
                dy = offset(-translateHeight, translateHeight, animatedValue);
                break;
            case 2:
                float dx2 = -translateWidth;
                dx = offset(translateWidth, dx2, animatedValue);
                dy = 0.0f;
                break;
            case 3:
                dx = 0.0f;
                dy = offset(translateHeight, -translateHeight, animatedValue);
                break;
            default:
                dx = offset(-translateWidth, translateWidth, animatedValue);
                dy = 0.0f;
                break;
        }
        this.mShaderMatrix.reset();
        this.mShaderMatrix.setRotate(this.mShimmer.tilt, this.mDrawRect.width() / 2.0f, this.mDrawRect.height() / 2.0f);
        this.mShaderMatrix.postTranslate(dx, dy);
        this.mShimmerPaint.getShader().setLocalMatrix(this.mShaderMatrix);
        canvas.drawRect(this.mDrawRect, this.mShimmerPaint);
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int alpha) {
        float alphaPercent = alpha / 255.0f;
        this.mBuilder.setHighlightAlpha(alphaPercent).build();
        setShimmer(this.mBuilder.build());
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        XShimmer xShimmer = this.mShimmer;
        return (xShimmer == null || !(xShimmer.clipToChildren || this.mShimmer.alphaShimmer)) ? -1 : -3;
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        this.mDrawRect.set(bounds);
        updateShader();
        maybeStartShimmer();
    }

    public void setColorHighlight(int colorHighlight, int colorBase) {
        this.mBuilder.setHighlightColor(colorHighlight).setBaseColor(colorBase).build();
        setShimmer(this.mBuilder.build());
    }

    @Override // android.graphics.drawable.Drawable
    public boolean setVisible(boolean visible, boolean restart) {
        if (!visible) {
            stopShimmer();
        } else {
            maybeStartShimmer();
        }
        return super.setVisible(visible, restart);
    }

    public void setShimmer(XShimmer shimmer) {
        this.mShimmer = shimmer;
        if (shimmer != null) {
            updateShader();
            updateValueAnimator();
            invalidateSelf();
        }
    }

    private void saveShimmerBuilder(XShimmer.Builder builder) {
        this.mBuilder = builder;
    }

    public void maybeStartShimmer() {
        XShimmer xShimmer;
        ValueAnimator valueAnimator = this.mValueAnimator;
        if (valueAnimator != null && !valueAnimator.isStarted() && (xShimmer = this.mShimmer) != null && xShimmer.autoStart && getCallback() != null) {
            this.mValueAnimator.start();
        }
    }

    public void startShimmer() {
        if (this.mValueAnimator != null && !isShimmerStarted() && getCallback() != null) {
            this.mValueAnimator.start();
        }
    }

    public void stopShimmer() {
        if (this.mValueAnimator != null && isShimmerStarted()) {
            this.mValueAnimator.cancel();
        }
    }

    public boolean isShimmerStarted() {
        ValueAnimator valueAnimator = this.mValueAnimator;
        return valueAnimator != null && valueAnimator.isStarted();
    }

    private void updateValueAnimator() {
        boolean started;
        if (this.mShimmer == null) {
            return;
        }
        ValueAnimator valueAnimator = this.mValueAnimator;
        if (valueAnimator != null) {
            started = valueAnimator.isStarted();
            this.mValueAnimator.cancel();
            this.mValueAnimator.removeAllUpdateListeners();
        } else {
            started = false;
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, ((float) (this.mShimmer.repeatDelay / this.mShimmer.animationDuration)) + 1.0f);
        this.mValueAnimator = ofFloat;
        ofFloat.setRepeatMode(this.mShimmer.repeatMode);
        this.mValueAnimator.setRepeatCount(this.mShimmer.repeatCount);
        this.mValueAnimator.setDuration(this.mShimmer.animationDuration + this.mShimmer.repeatDelay);
        this.mValueAnimator.addUpdateListener(this.mUpdateListener);
        if (started) {
            this.mValueAnimator.start();
        }
    }

    private void updateShader() {
        XShimmer xShimmer;
        Shader shader;
        Rect bounds = getBounds();
        int boundsWidth = bounds.width();
        int boundsHeight = bounds.height();
        if (boundsWidth == 0 || boundsHeight == 0 || (xShimmer = this.mShimmer) == null) {
            return;
        }
        int width = xShimmer.width(boundsWidth);
        int height = this.mShimmer.height(boundsHeight);
        switch (this.mShimmer.shape) {
            case 1:
                shader = new RadialGradient(width / 2.0f, height / 2.0f, (float) (Math.max(width, height) / Math.sqrt(2.0d)), this.mShimmer.colors, this.mShimmer.positions, Shader.TileMode.CLAMP);
                break;
            default:
                boolean z = true;
                if (this.mShimmer.direction != 1 && this.mShimmer.direction != 3) {
                    z = false;
                }
                boolean vertical = z;
                int endX = vertical ? 0 : width;
                int endY = vertical ? height : 0;
                Shader shader2 = new LinearGradient(0.0f, 0.0f, endX, endY, this.mShimmer.colors, this.mShimmer.positions, Shader.TileMode.CLAMP);
                shader = shader2;
                break;
        }
        this.mShimmerPaint.setShader(shader);
    }

    private float offset(float start, float end, float percent) {
        return ((end - start) * percent) + start;
    }
}
