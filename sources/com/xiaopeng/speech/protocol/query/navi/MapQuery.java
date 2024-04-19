package com.xiaopeng.speech.protocol.query.navi;

import com.xiaopeng.speech.SpeechQuery;
import com.xiaopeng.speech.protocol.node.navi.bean.AddressBean;
/* loaded from: classes.dex */
public class MapQuery extends SpeechQuery<IMapQueryCaller> {
    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isCruise(String event, String data) {
        return ((IMapQueryCaller) this.mQueryCaller).isCruise();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isExplorePath(String event, String data) {
        return ((IMapQueryCaller) this.mQueryCaller).isExplorePath();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isNavigation(String event, String data) {
        return ((IMapQueryCaller) this.mQueryCaller).isNavigation();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String getAddress(String event, String data) {
        AddressBean addressBean = AddressBean.fromJson(data);
        return ((IMapQueryCaller) this.mQueryCaller).getCommonAddress(addressBean);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String getNavigationInfo(String event, String data) {
        return ((IMapQueryCaller) this.mQueryCaller).getNavigationInfo();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isZoominMax(String event, String data) {
        return ((IMapQueryCaller) this.mQueryCaller).isZoominMax();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isZoomoutMax(String event, String data) {
        return ((IMapQueryCaller) this.mQueryCaller).isZoomoutMin();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isCalculatePath(String event, String data) {
        return ((IMapQueryCaller) this.mQueryCaller).isCalculatePath();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getFavoriteOpenStatus(String event, String data) {
        return ((IMapQueryCaller) this.mQueryCaller).getFavoriteOpenStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getOpenControlStatus(String event, String data) {
        return ((IMapQueryCaller) this.mQueryCaller).getOpenControlStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getSwitchMainRoadStatus(String event, String data) {
        return ((IMapQueryCaller) this.mQueryCaller).getSwitchMainRoadStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getSwitchSideRoadStatus(String event, String data) {
        return ((IMapQueryCaller) this.mQueryCaller).getSwitchSideRoadStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getCurrentScaleLevel(String event, String data) {
        return ((IMapQueryCaller) this.mQueryCaller).getCurrentScaleLevel();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getPoiDetailsFavoriteStatus(String event, String data) {
        return ((IMapQueryCaller) this.mQueryCaller).getPoiDetailsFavoriteStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isSRMap(String event, String data) {
        return ((IMapQueryCaller) this.mQueryCaller).isSRMap();
    }
}
