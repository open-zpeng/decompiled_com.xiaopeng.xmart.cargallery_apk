package com.xiaopeng.xmart.cargallery.view.player;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import com.xiaopeng.xmart.cargallery.view.GalleryViewPager;
/* loaded from: classes20.dex */
public class MediaControlContainer extends LinearLayout {
    private GalleryViewPager mPager;
    private Rect mRect;

    public MediaControlContainer(Context context) {
        this(context, null);
    }

    public MediaControlContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mRect = new Rect();
    }

    public void setViewPager(GalleryViewPager pager) {
        this.mPager = pager;
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        this.mRect.set(0, 0, getWidth(), getHeight());
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        GalleryViewPager galleryViewPager = this.mPager;
        if (galleryViewPager != null) {
            galleryViewPager.setScrollEnabled(!this.mRect.contains(x, y));
        }
        if (3 == ev.getAction() || 1 == ev.getAction()) {
            this.mPager.setScrollEnabled(true);
        }
        super.dispatchTouchEvent(ev);
        return true;
    }

    public void cleanup() {
    }
}
