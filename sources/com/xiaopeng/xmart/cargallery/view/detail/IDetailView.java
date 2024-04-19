package com.xiaopeng.xmart.cargallery.view.detail;

import com.xiaopeng.xmart.cargallery.bean.BaseItem;
/* loaded from: classes18.dex */
public interface IDetailView {
    void cleanup();

    void onDestroyItem();

    void onPause();

    void reset();

    void setData(BaseItem item);
}
