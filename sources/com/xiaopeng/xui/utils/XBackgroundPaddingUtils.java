package com.xiaopeng.xui.utils;

import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.util.AttributeSet;
import android.view.View;
import com.xiaopeng.xpui.R;
/* loaded from: classes.dex */
public class XBackgroundPaddingUtils {
    public static Rect backgroundPadding(View view, AttributeSet attrs) {
        TypedArray a = view.getContext().obtainStyledAttributes(attrs, R.styleable.XBackgroundPadding);
        boolean enable = a.getBoolean(R.styleable.XBackgroundPadding_x_bg_padding_enable, false);
        if (enable) {
            int insetLeft = -1;
            int insetRight = -1;
            int insetTop = -1;
            int insetBottom = -1;
            if (a.hasValue(R.styleable.XBackgroundPadding_x_bg_padding)) {
                int inset = a.getDimensionPixelSize(R.styleable.XBackgroundPadding_x_bg_padding, -1);
                insetBottom = inset;
                insetTop = inset;
                insetRight = inset;
                insetLeft = inset;
            }
            if (a.hasValue(R.styleable.XBackgroundPadding_x_bg_padding_left)) {
                insetLeft = a.getDimensionPixelSize(R.styleable.XBackgroundPadding_x_bg_padding_left, -1);
            }
            if (a.hasValue(R.styleable.XBackgroundPadding_x_bg_padding_right)) {
                insetRight = a.getDimensionPixelSize(R.styleable.XBackgroundPadding_x_bg_padding_right, -1);
            }
            if (a.hasValue(R.styleable.XBackgroundPadding_x_bg_padding_top)) {
                insetTop = a.getDimensionPixelSize(R.styleable.XBackgroundPadding_x_bg_padding_top, -1);
            }
            if (a.hasValue(R.styleable.XBackgroundPadding_x_bg_padding_bottom)) {
                insetBottom = a.getDimensionPixelSize(R.styleable.XBackgroundPadding_x_bg_padding_bottom, -1);
            }
            a.recycle();
            return backgroundPadding(view, insetLeft, insetTop, insetRight, insetBottom);
        }
        a.recycle();
        return null;
    }

    public static Rect backgroundPadding(View view, int insetLeft, int insetTop, int insetRight, int insetBottom) {
        int insetLeft2;
        int insetTop2;
        int insetRight2;
        int insetBottom2;
        Drawable child;
        int paddingLeftOffset;
        log(String.format("insetLeft %s, insetTop %s, insetRight %s, insetBottom %s, ", Integer.valueOf(insetLeft), Integer.valueOf(insetTop), Integer.valueOf(insetRight), Integer.valueOf(insetBottom)));
        Drawable drawable = view.getBackground();
        int paddingLeft = view.getPaddingLeft();
        int paddingRight = view.getPaddingRight();
        int paddingTop = view.getPaddingTop();
        int paddingBottom = view.getPaddingBottom();
        log(String.format("paddingLeft %s, paddingRight %s, paddingTop %s, paddingBottom %s, ", Integer.valueOf(paddingLeft), Integer.valueOf(paddingRight), Integer.valueOf(paddingTop), Integer.valueOf(paddingBottom)));
        int paddingRightOffset = 0;
        int paddingTopOffset = 0;
        int paddingBottomOffset = 0;
        if (drawable != null) {
            if (drawable instanceof InsetDrawable) {
                InsetDrawable insetDrawable = (InsetDrawable) drawable;
                Drawable child2 = insetDrawable.getDrawable();
                Rect rect = new Rect();
                insetDrawable.getPadding(rect);
                int insetLeft3 = insetLeft;
                if (insetLeft3 == -1) {
                    insetLeft3 = rect.left;
                }
                insetTop2 = insetTop;
                if (insetTop2 == -1) {
                    insetTop2 = rect.top;
                }
                insetRight2 = insetRight;
                if (insetRight2 == -1) {
                    insetRight2 = rect.right;
                }
                insetBottom2 = insetBottom;
                if (insetBottom2 == -1) {
                    insetBottom2 = rect.bottom;
                }
                paddingLeftOffset = insetLeft3 - rect.left;
                paddingTopOffset = insetTop2 - rect.top;
                paddingRightOffset = insetRight2 - rect.right;
                paddingBottomOffset = insetBottom2 - rect.bottom;
                insetLeft2 = insetLeft3;
                child = child2;
            } else {
                insetLeft2 = insetLeft;
                insetTop2 = insetTop;
                insetRight2 = insetRight;
                insetBottom2 = insetBottom;
                child = drawable;
                paddingLeftOffset = 0;
            }
            log(String.format("paddingLeftOffset %s, paddingTopOffset %s, paddingRightOffset %s, paddingBottomOffset %s, ", Integer.valueOf(paddingLeftOffset), Integer.valueOf(paddingTopOffset), Integer.valueOf(paddingRightOffset), Integer.valueOf(paddingBottomOffset)));
            InsetDrawable newDrawable = new InsetDrawable(child, insetLeft2, insetTop2, insetRight2, insetBottom2);
            view.setBackground(newDrawable);
            newDrawable.setDrawable(child);
            int paddingLeftOffset2 = paddingBottom + paddingBottomOffset;
            view.setPadding(paddingLeft + paddingLeftOffset, paddingTop + paddingTopOffset, paddingRight + paddingRightOffset, paddingLeftOffset2);
            log(String.format("paddingLeft %s, paddingRight %s, paddingTop %s, paddingBottom %s, ", Integer.valueOf(view.getPaddingLeft()), Integer.valueOf(view.getPaddingRight()), Integer.valueOf(view.getPaddingTop()), Integer.valueOf(view.getPaddingBottom())));
            return new Rect(insetLeft2, insetTop2, insetRight2, insetBottom2);
        }
        return null;
    }

    private static void log(String msg) {
        XLogUtils.d("xpui-bgPadding", msg);
    }
}
