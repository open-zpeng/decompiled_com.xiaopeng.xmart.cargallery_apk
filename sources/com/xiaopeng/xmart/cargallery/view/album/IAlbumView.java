package com.xiaopeng.xmart.cargallery.view.album;

import com.xiaopeng.xmart.cargallery.bean.BaseItem;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
/* loaded from: classes2.dex */
public interface IAlbumView {

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface AlbumType {
        public static final int ALBUM_TYPE_360 = 0;
        public static final int ALBUM_TYPE_DVR = 3;
        public static final int ALBUM_TYPE_FRONT = 1;
        public static final int ALBUM_TYPE_TOP = 2;
    }

    void onMediaDataDeleted(List<BaseItem> deletedList);

    void onMediaDataLoaded(List<BaseItem> dataList, int selectedIndex);

    default void onGearChanged(int gearLevel) {
    }

    default void onDvrSdCardRemoved() {
    }
}
