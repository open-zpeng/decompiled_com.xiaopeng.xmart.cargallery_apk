package com.xiaopeng.xmart.cargallery.widget;

import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
/* loaded from: classes19.dex */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public SpacesItemDecoration(int space) {
        this.space = space;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = this.space;
        outRect.right = this.space;
        outRect.bottom = this.space;
        if (parent.getChildPosition(view) == 0) {
            outRect.top = this.space;
        }
    }
}
