package com.xiaopeng.xmart.cargallery.unity.album;

import com.xiaopeng.xmart.cargallery.bean.BaseItem;
import java.util.List;
/* loaded from: classes12.dex */
interface AlbumListener {
    void onMediaDataDeleted(List<BaseItem> deletedList);

    void onMediaDataLoaded(List<BaseItem> dataList);

    void onUsagePercent(long total, long remind);
}
