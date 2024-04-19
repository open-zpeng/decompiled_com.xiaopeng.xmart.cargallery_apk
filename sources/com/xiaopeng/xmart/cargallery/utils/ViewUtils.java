package com.xiaopeng.xmart.cargallery.utils;

import android.view.View;
import com.xiaopeng.xmart.cargallery.R;
/* loaded from: classes3.dex */
public class ViewUtils {
    private static final String TAG = "ViewUtils";

    private static void setViewsVisibility(int visibility, View... views) {
        for (int i = 0; i < views.length; i++) {
            if (views[i] != null) {
                views[i].setVisibility(visibility);
            }
        }
    }

    public static void setViewsGone(View... views) {
        setViewsVisibility(8, views);
    }

    public static void setViewsVisible(View... views) {
        setViewsVisibility(0, views);
    }

    public static boolean isE28ViewByTag(View view) {
        if (view == null) {
            return false;
        }
        return view.getContext().getString(R.string.tag_e28).equals(view.getTag());
    }
}
