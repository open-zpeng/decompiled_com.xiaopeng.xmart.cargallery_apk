package com.xiaopeng.xmart.cargallery.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.viewpager.widget.ViewPager;
import com.xiaopeng.xmart.cargallery.CameraLog;
/* loaded from: classes17.dex */
public class GalleryViewPager extends ViewPager {
    private static final String TAG = "GalleryViewPager";
    private boolean mIsScrollEnabled;

    public GalleryViewPager(Context context) {
        super(context.getApplicationContext());
        this.mIsScrollEnabled = true;
    }

    public GalleryViewPager(Context context, AttributeSet attrs) {
        super(context.getApplicationContext(), attrs);
        this.mIsScrollEnabled = true;
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            if (this.mIsScrollEnabled) {
                return super.onInterceptTouchEvent(ev);
            }
            return false;
        } catch (ArrayIndexOutOfBoundsException e) {
            CameraLog.e(TAG, "viewpager error2", false);
            return false;
        } catch (IllegalArgumentException e2) {
            CameraLog.e(TAG, "viewpager error1", false);
            return false;
        }
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.View
    public boolean onTouchEvent(MotionEvent ev) {
        return this.mIsScrollEnabled && super.onTouchEvent(ev);
    }

    public void setScrollEnabled(boolean enabled) {
        this.mIsScrollEnabled = enabled;
    }
}
