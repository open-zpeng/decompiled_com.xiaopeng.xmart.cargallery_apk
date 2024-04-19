package com.xiaopeng.speech.protocol.query.navi;

import com.xiaopeng.speech.IQueryCaller;
import com.xiaopeng.speech.protocol.node.navi.bean.AddressBean;
/* loaded from: classes.dex */
public interface IMapQueryCaller extends IQueryCaller {
    String getCommonAddress(AddressBean addressBean);

    int getFavoriteOpenStatus();

    String getNavigationInfo();

    int getOpenControlStatus();

    int getSwitchMainRoadStatus();

    int getSwitchSideRoadStatus();

    boolean isCalculatePath();

    boolean isCruise();

    boolean isExplorePath();

    boolean isNavigation();

    boolean isZoominMax();

    boolean isZoomoutMin();

    default int getCurrentScaleLevel() {
        return -1;
    }

    default int getPoiDetailsFavoriteStatus() {
        return -1;
    }

    default boolean isSRMap() {
        return false;
    }
}
