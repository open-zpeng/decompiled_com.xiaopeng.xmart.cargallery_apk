package com.xiaopeng.xui.graphics;

import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
/* loaded from: classes.dex */
public class XLightPaint {
    public static final int LIGHT_TYPE_BLUR_MASK_FILTER = 0;
    public static final int LIGHT_TYPE_SHADOW_LAYER = 1;
    private static float MAX_LIGHT_RADIUS = 200.0f;
    private BlurMaskFilter mBlurMaskFilter;
    private LightingColorFilter mLightingColorFilter;
    private Paint mPaint;
    private int mLightColor = -1;
    private int mLightAlpha = 255;
    private int mBrightness = 0;
    private float mLightStrength = 1.0f;
    private int mLightType = 0;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface LightType {
    }

    public XLightPaint(Paint paint) {
        this.mPaint = paint;
    }

    public void setLightType(int lightType) {
        this.mLightType = lightType;
    }

    public void setLightRadius(float maxLightRadius) {
        MAX_LIGHT_RADIUS = maxLightRadius;
    }

    public void setBrightness(int add) {
        this.mBrightness = add;
    }

    public void setLightColor(int lightColor) {
        this.mLightColor = lightColor;
    }

    public void setLightStrength(float lightStrength) {
        this.mLightStrength = lightStrength;
    }

    public void apply() {
        float f = MAX_LIGHT_RADIUS;
        float f2 = this.mLightStrength;
        float radius = f * f2;
        int i = this.mBrightness;
        if (i > 0) {
            int color = (int) (f2 * i);
            LightingColorFilter lightingColorFilter = new LightingColorFilter(-1, Color.argb(this.mLightAlpha, color, color, color));
            this.mLightingColorFilter = lightingColorFilter;
            this.mPaint.setColorFilter(lightingColorFilter);
        }
        int color2 = this.mLightType;
        if (color2 == 0) {
            if (radius > 0.0f) {
                BlurMaskFilter blurMaskFilter = new BlurMaskFilter(radius, BlurMaskFilter.Blur.SOLID);
                this.mBlurMaskFilter = blurMaskFilter;
                this.mPaint.setMaskFilter(blurMaskFilter);
                return;
            }
            this.mPaint.setMaskFilter(null);
        } else if (color2 == 1) {
            int i2 = this.mLightColor;
            if (i2 == -1) {
                throw new IllegalArgumentException("Please set light color.");
            }
            this.mPaint.setShadowLayer(radius, 0.0f, 0.0f, i2);
        }
    }

    public Paint getPaint() {
        return this.mPaint;
    }
}
