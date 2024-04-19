package com.xiaopeng.speech.protocol.node.navi;

import android.text.TextUtils;
import com.xiaopeng.speech.SpeechClient;
import com.xiaopeng.speech.SpeechNode;
import com.xiaopeng.speech.actorapi.ResultActor;
import com.xiaopeng.speech.common.util.LogUtils;
import com.xiaopeng.speech.jarvisproto.FeedUIEvent;
import com.xiaopeng.speech.jarvisproto.WakeupResult;
import com.xiaopeng.speech.protocol.bean.FeedListUIValue;
import com.xiaopeng.speech.protocol.event.ContextEvent;
import com.xiaopeng.speech.protocol.event.NaviEvent;
import com.xiaopeng.speech.protocol.event.OOBEEvent;
import com.xiaopeng.speech.protocol.node.navi.bean.AddressBean;
import com.xiaopeng.speech.protocol.node.navi.bean.NaviPreferenceBean;
import com.xiaopeng.speech.protocol.node.navi.bean.NearbySearchBean;
import com.xiaopeng.speech.protocol.node.navi.bean.PathBean;
import com.xiaopeng.speech.protocol.node.navi.bean.PoiBean;
import com.xiaopeng.speech.protocol.node.navi.bean.PoiSearchBean;
import com.xiaopeng.speech.protocol.node.navi.bean.RouteSelectBean;
import com.xiaopeng.speech.protocol.node.navi.bean.SelectParkingBean;
import com.xiaopeng.speech.protocol.node.navi.bean.SelectRouteBean;
import com.xiaopeng.speech.protocol.node.navi.bean.StartNaviBean;
import com.xiaopeng.speech.protocol.node.navi.bean.WaypointSearchBean;
import com.xiaopeng.speech.speechwidget.ContentWidget;
import com.xiaopeng.speech.speechwidget.ListWidget;
import com.xiaopeng.speech.speechwidget.SpeechWidget;
import com.xiaopeng.speech.speechwidget.TextWidget;
import com.xiaopeng.xmart.cargallery.bean.BIConfig;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/* loaded from: classes.dex */
public class NaviNode extends SpeechNode<NaviListener> {
    private static final String ACTIVE_VOICE_TASK = "主动语音";
    public static final String ALL_ROUTE_WIDGET_ID = "-Route-2";
    public static final String BASE_ROUTE_WIDGET_ID = "-Route-1";
    private static final String COMMAND_SPLIT = "#";
    private static final String KEY_MODE = "mode";
    private static final String KEY_PULLUP_NAVI = "pullUpNavi";
    private static final String OFFLINE_SKILL = "离线命令词";
    private static final String SELECT_PARKING_INTENT = "停车场主动语音";
    private static final String SELECT_ROUTE_INTENT = "路线主动语音";
    private static final int STOP_DIALOG_OPT_FORCE = 0;
    private static final int STOP_DIALOG_OPT_OPTIONAL = 1;
    private boolean mAddressPendingRoute = false;

    public void onControlClose(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onControlClose();
            }
        }
    }

    public void onMapZoomIn(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onMapZoomIn();
            }
        }
    }

    public void onMapZoomOut(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onMapZoomOut();
            }
        }
    }

    public void onOpenTraffic(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onOpenTraffic();
            }
        }
    }

    public void onCloseTraffic(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onCloseTraffic();
            }
        }
    }

    public void onControlOverviewOpen(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onControlOverviewOpen();
            }
        }
    }

    public void onControlOverviewClose(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onControlOverviewClose();
            }
        }
    }

    public void onMapOverview(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onMapOverview();
            }
        }
    }

    public void onControlFavoriteOpen(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onControlFavoriteOpen();
            }
        }
    }

    public void onControlSettingsOpen(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onControlSettingsOpen();
            }
        }
    }

    public void onControlChargeOpen(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onControlChargeOpen();
            }
        }
    }

    public void onControlChargeClose(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onControlChargeClose();
            }
        }
    }

    public void onDriveAvoidCongestion(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onDriveAvoidCongestion();
            }
        }
    }

    public void onDriveAvoidCongestionOff(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onDriveAvoidCongestionOff();
            }
        }
    }

    public void onDriveAvoidCharge(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onDriveAvoidCharge();
            }
        }
    }

    public void onDriveAvoidChargeOff(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onDriveAvoidChargeOff();
            }
        }
    }

    public void onDriveHighwayFirstOff(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onDriveHighwayFirstOff();
            }
        }
    }

    public void onDriveAvoidControls(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onDriveAvoidControls();
            }
        }
    }

    public void onDriveAvoidControlsOff(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onDriveAvoidControlsOff();
            }
        }
    }

    public void onDriveRadarRoute(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onDriveRadarRoute();
            }
        }
    }

    public void onDriveRadarRouteOff(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onDriveRadarRouteOff();
            }
        }
    }

    public void onControlSpeechSuperSimple(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onControlSpeechSuperSimple();
            }
        }
    }

    public void onControlSpeechGeneral(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onControlSpeechGeneral();
            }
        }
    }

    public void onControlSpeechEye(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onControlSpeechEye();
            }
        }
    }

    public void onControlSpeechEyeOff(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onControlSpeechEyeOff();
            }
        }
    }

    public void onControlSmartScale(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onControlSmartScale();
            }
        }
    }

    public void onControlSmartScaleOff(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onControlSmartScaleOff();
            }
        }
    }

    public void onControlSecurityRemind(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onControlSecurityRemind();
            }
        }
    }

    public void onControlRoadAhead(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onControlRoadAhead();
            }
        }
    }

    public void onDriveHighwayNo(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onDriveHighwayNo();
            }
        }
    }

    public void onDriveHighwayNoOff(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onDriveHighwayNoOff();
            }
        }
    }

    public void onDriveHighwayFirst(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onDriveHighwayFirst();
            }
        }
    }

    public void onNavigatingGet(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onNavigatingGet();
            }
        }
    }

    public void onPoiSearch(String event, String data) {
        PoiSearchBean poiSearchBean = PoiSearchBean.fromJson(data);
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onPoiSearch(poiSearchBean);
            }
        }
    }

    public void onControlSecurityRemindOff(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onControlSecurityRemindOff();
            }
        }
    }

    public void onMapEnterFindPath(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onMapEnterFindPath();
            }
        }
    }

    public void onMapExitFindPath(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onMapExitFindPath();
            }
        }
    }

    public void onSearchClose(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onSearchClose();
            }
        }
    }

    public void onMainRoad(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onMainRoad();
            }
        }
    }

    public void onSideRoad(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onSideRoad();
            }
        }
    }

    public void onControlFavoriteClose(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onControlFavoriteClose();
            }
        }
    }

    public void onControlRoadAheadOff(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onControlRoadAheadOff();
            }
        }
    }

    public void onMapZoominMax(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onMapZoominMax();
            }
        }
    }

    public void onMapZoomoutMin(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onMapZoomoutMin();
            }
        }
    }

    public void onNearbySearch(String event, String data) {
        NearbySearchBean searchBean = NearbySearchBean.fromJson(data);
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onNearbySearch(searchBean);
            }
        }
    }

    public void postPoiResult(String searchKey, List<PoiBean> poiBeanList) {
        postPoiResult(NaviEvent.POI_SEARCH, searchKey, poiBeanList);
    }

    public void postNearbyResult(String searchKey, List<PoiBean> poiBeanList) {
        postPoiResult(NaviEvent.NEARBY_SEARCH, searchKey, poiBeanList);
    }

    public void postSearchPoiResult(String event, String searchKey, List<PoiBean> poiBeanList) {
        postPoiResult(event, searchKey, poiBeanList);
    }

    public void postAddressPosResult(String searchKey, List<PoiBean> poiBeanList) {
        postPoiResult(NaviEvent.POI_SEARCH, searchKey, poiBeanList);
    }

    public void postWaypointsFull(String searchKey) {
        postWaypointSearchResult(searchKey, null, true, true);
    }

    public void postWaypointsNotExitRoute(String searchKey) {
        postWaypointSearchResult(searchKey, null, false, false);
    }

    public void postWaypointSearchResult(String searchKey, List<PoiBean> poiBeanList) {
        postWaypointSearchResult(searchKey, poiBeanList, true, false);
    }

    private void postWaypointSearchResult(String searchKey, List<PoiBean> poiBeanList, boolean isExistRoute, boolean isWaypointListFull) {
        ListWidget listWidget = new ListWidget();
        listWidget.setTitle(searchKey);
        listWidget.setExist(isExistRoute);
        listWidget.setExtraType("navi");
        listWidget.addContent("isWaypointListFull", String.valueOf(isWaypointListFull));
        if (poiBeanList != null) {
            for (PoiBean poiBean : poiBeanList) {
                ContentWidget contentWidget = new ContentWidget();
                contentWidget.setTitle(poiBean.getName());
                contentWidget.setSubTitle(poiBean.getAddress());
                try {
                    contentWidget.addExtra("navi", poiBean.toJson().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listWidget.addContentWidget(contentWidget);
            }
        }
        SpeechClient.instance().getActorBridge().send(new ResultActor(NaviEvent.WAYPOINT_SEARCH).setResult(listWidget));
    }

    private void postPoiResult(String event, String searchKey, List<PoiBean> poiBeanList) {
        ListWidget listWidget = new ListWidget();
        listWidget.setTitle(searchKey);
        listWidget.setExtraType("navi");
        if (poiBeanList != null) {
            for (PoiBean poiBean : poiBeanList) {
                ContentWidget contentWidget = new ContentWidget();
                contentWidget.setTitle(poiBean.getName());
                contentWidget.setSubTitle(poiBean.getAddress());
                try {
                    contentWidget.addExtra("navi", poiBean.toJson().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listWidget.addContentWidget(contentWidget);
            }
        }
        SpeechClient.instance().getActorBridge().send(new ResultActor(event).setResult(listWidget));
    }

    public void onAddressGet(String event, String data) {
        AddressBean addressBean = AddressBean.fromJson(data);
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onAddressGet(addressBean);
            }
        }
        this.mAddressPendingRoute = false;
    }

    public void postAddressGetResult(boolean isExist, boolean hasBigData, PoiBean poiBean) {
        TextWidget textWidget = new TextWidget();
        textWidget.setText(isExist ? "success" : "fail");
        textWidget.addContent("hasBigData", hasBigData ? OOBEEvent.STRING_TRUE : OOBEEvent.STRING_FALSE);
        if (poiBean != null) {
            try {
                textWidget.addContent("address", poiBean.getAddress());
                textWidget.addExtra("navi", poiBean.toJson().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        SpeechClient.instance().getActorBridge().send(new ResultActor(NaviEvent.ADDRESS_GET).setResult(textWidget));
    }

    public void onAddressPendingRoute(String event, String data) {
        LogUtils.i(this, "pending route");
        this.mAddressPendingRoute = true;
    }

    public void onAddressSet(String event, String data) {
        AddressBean addressBean = new AddressBean();
        PoiBean poiBean = null;
        String pathPref = null;
        String type = null;
        int naviType = 0;
        int routeSelectRef = 0;
        try {
            JSONObject jsonObject = new JSONObject(data);
            addressBean.setAddressType(jsonObject.optString("addressType"));
            String posJson = jsonObject.optString("poi");
            poiBean = PoiBean.fromJson(posJson);
            pathPref = jsonObject.optString("pref");
            type = jsonObject.optString(BIConfig.PROPERTY.DATA_TYPE);
            naviType = jsonObject.optInt("naviType");
            routeSelectRef = jsonObject.optInt("routeSelectRef");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onAddressSet(addressBean, poiBean);
            }
        }
        if (this.mAddressPendingRoute) {
            StartNaviBean startNaviBean = new StartNaviBean(poiBean, pathPref, type);
            startNaviBean.setNaviType(naviType);
            startNaviBean.setRouteSelectRef(routeSelectRef);
            doControlStart(startNaviBean);
        }
        this.mAddressPendingRoute = false;
    }

    public void onControlStart(String event, String data) {
        PoiBean poiBean = null;
        String pathPref = null;
        String type = null;
        int naviType = 0;
        int routeSelectRef = 0;
        try {
            JSONObject jsonObject = new JSONObject(data);
            String posJson = jsonObject.optString("poi");
            poiBean = PoiBean.fromJson(posJson);
            pathPref = jsonObject.optString("pref");
            type = jsonObject.optString(BIConfig.PROPERTY.DATA_TYPE);
            naviType = jsonObject.optInt("naviType");
            routeSelectRef = jsonObject.optInt("routeSelectRef");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        StartNaviBean startNaviBean = new StartNaviBean(poiBean, pathPref, type);
        startNaviBean.setNaviType(naviType);
        startNaviBean.setRouteSelectRef(routeSelectRef);
        LogUtils.d("NaviNode", "StartNaviBean = %s", startNaviBean.toString());
        doControlStart(startNaviBean);
    }

    private void doControlStart(StartNaviBean startNaviBean) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onControlStart(startNaviBean);
            }
        }
    }

    public void onControlSpeechSimple(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onControlSpeechSimple();
            }
        }
    }

    public void onControlSpeechDetail(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onControlSpeechDetail();
            }
        }
    }

    public void onControlDisPlayNorth(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onControlDisPlayNorth();
            }
        }
    }

    public void onControlDisPlayCar(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onControlDisPlayCar();
            }
        }
    }

    public void onControlDisplay3D(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onControlDisplay3D();
            }
        }
    }

    public void onControlVolOn(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        boolean pullUpNavi = true;
        int mode = 0;
        try {
            JSONObject jsonObject = new JSONObject(data);
            pullUpNavi = jsonObject.optBoolean(KEY_PULLUP_NAVI, false);
            if (jsonObject.has(KEY_MODE)) {
                mode = jsonObject.optInt(KEY_MODE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onControlVolOn(pullUpNavi, mode);
            }
        }
    }

    public void onControlVolOff(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        boolean pullUpNavi = true;
        int mode = 0;
        try {
            JSONObject jsonObject = new JSONObject(data);
            pullUpNavi = jsonObject.optBoolean(KEY_PULLUP_NAVI, false);
            if (jsonObject.has(KEY_MODE)) {
                mode = jsonObject.optInt(KEY_MODE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onControlVolOff(pullUpNavi, mode);
            }
        }
    }

    public void onRouteNearbySearch(String event, String data) {
        NearbySearchBean searchBean = NearbySearchBean.fromJson(data);
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onRouteNearbySearch(searchBean);
            }
        }
    }

    public void onParkingSelect(String event, String data) {
        SelectParkingBean selectParkingBean = SelectParkingBean.fromJson(data);
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onParkingSelect(selectParkingBean);
            }
        }
    }

    public void onConfirmOk(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onConfirmOk();
            }
        }
    }

    public void onConfirmCancel(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onConfirmCancel();
            }
        }
    }

    public void onRouteSelect(String event, String data) {
        SelectRouteBean selectRouteBean = SelectRouteBean.fromJson(data);
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onRouteSelect(selectRouteBean);
            }
        }
    }

    public void onSelectParkingCount(String event, String data) {
        SelectParkingBean selectParkingBean = SelectParkingBean.fromJson(data);
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onSelectParkingCount(selectParkingBean);
            }
        }
    }

    public void onSelectRouteCount(String event, String data) {
        SelectRouteBean selectRouteBean = SelectRouteBean.fromJson(data);
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onSelectRouteCount(selectRouteBean);
            }
        }
    }

    public void onDataControlDisplay3dTts(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onDataControlDisplay3dTts();
            }
        }
    }

    public void onDataControlDisplayCarTts(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onDataControlDisplayCarTts();
            }
        }
    }

    public void onDataControlDisplayNorthTts(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onDataControlDisplayNorthTts();
            }
        }
    }

    public void onDriveAdvoidTrafficControl(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onDriveAdvoidTrafficControl();
            }
        }
    }

    public void onWaypointSearch(String event, String data) {
        WaypointSearchBean waypointSearch = WaypointSearchBean.fromJson(data);
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onWaypointSearch(waypointSearch);
            }
        }
    }

    public void onControlWaypointStart(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        PathBean pathBean = PathBean.fromJson(data);
        LogUtils.d("NaviNode, pathBean =%s", pathBean == null ? "data is null" : pathBean.toString());
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onControlWaypointStart(pathBean);
            }
        }
    }

    public void onControlOpenSmallMap(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onControlOpenSmallMap();
            }
        }
    }

    public void onControlCloseSmallMap(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onControlCloseSmallMap();
            }
        }
    }

    public void onControlOpenRibbonMap(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onControlOpenRibbonMap();
            }
        }
    }

    public void onControlCloseRibbonMap(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onControlCloseRibbonMap();
            }
        }
    }

    public void selectParking(String tts) {
        SpeechClient.instance().getWakeupEngine().stopDialog();
        String slots = "";
        try {
            slots = new JSONObject().put("tts", tts).put("isLocalSkill", true).put("intent", SELECT_PARKING_INTENT).put("isAsrModeOffline", false).put(WakeupResult.REASON_COMMAND, "native://navi.select.parking.count#command://navi.parking.select#command://navi.confirm.cancel").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        SpeechClient.instance().getAgent().triggerIntent(OFFLINE_SKILL, ACTIVE_VOICE_TASK, SELECT_PARKING_INTENT, slots);
    }

    @Deprecated
    public void selectRoute(String tts) {
        SpeechClient.instance().getWakeupEngine().stopDialog();
        String slots = "";
        try {
            slots = new JSONObject().put("tts", tts).put("isLocalSkill", true).put("intent", SELECT_ROUTE_INTENT).put("isAsrModeOffline", false).put(WakeupResult.REASON_COMMAND, "native://navi.select.route.count#command://navi.route.select#command://navi.confirm.cancel").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        SpeechClient.instance().getAgent().triggerIntent(OFFLINE_SKILL, ACTIVE_VOICE_TASK, SELECT_ROUTE_INTENT, slots);
    }

    public void selectRoute(List<RouteSelectBean> list) {
        JSONObject data = new JSONObject();
        try {
            JSONArray jsArray = new JSONArray();
            if (list != null && list.size() > 0) {
                for (RouteSelectBean routeSelectBean : list) {
                    jsArray.put(routeSelectBean.toJson());
                }
            }
            data.put("route_list", jsArray.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        SpeechClient.instance().getAgent().triggerEvent(FeedUIEvent.LIST_ROUT_UPLOAD, data.toString());
    }

    public void updateRouteSelect(List<RouteSelectBean> list) {
        ListWidget listWidget = new ListWidget();
        listWidget.setTitle(FeedListUIValue.TYPE_ROUTE);
        listWidget.setExtraType(ListWidget.EXTRA_TYPE_NAVI_ROUTE);
        if (list != null && list.size() > 0) {
            for (RouteSelectBean routeSelectBean : list) {
                ContentWidget contentWidget = new ContentWidget();
                contentWidget.setTitle(routeSelectBean.totalTimeLine1);
                contentWidget.setSubTitle(routeSelectBean.routeTypeName);
                try {
                    contentWidget.addExtra(ListWidget.EXTRA_TYPE_NAVI_ROUTE, routeSelectBean.toJson().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listWidget.addContentWidget(contentWidget);
            }
        }
        LogUtils.i("updateRouteSelect", "updateRouteSelect:" + listWidget.toString());
        SpeechClient.instance().getAgent().sendEvent(ContextEvent.WIDGET_LIST, listWidget.toString());
    }

    private SpeechWidget getRouteSelectWidget(List<RouteSelectBean> list) {
        ListWidget listWidget = new ListWidget();
        listWidget.setTitle(FeedListUIValue.TYPE_ROUTE);
        listWidget.setExtraType(ListWidget.EXTRA_TYPE_NAVI_ROUTE);
        if (list != null && list.size() > 0) {
            for (RouteSelectBean routeSelectBean : list) {
                ContentWidget contentWidget = new ContentWidget();
                contentWidget.setTitle(routeSelectBean.totalTimeLine1);
                contentWidget.setSubTitle(routeSelectBean.routeTypeName);
                try {
                    contentWidget.addExtra(ListWidget.EXTRA_TYPE_NAVI_ROUTE, routeSelectBean.toJson().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listWidget.addContentWidget(contentWidget);
            }
        }
        return listWidget;
    }

    public void stopSpeechDialog() {
        SpeechClient.instance().getWakeupEngine().stopDialog();
    }

    public void stopSpeechDialog(int stopOption) {
        LogUtils.i(this, "stopDialog option: " + stopOption);
        if (stopOption == 0) {
            SpeechClient.instance().getWakeupEngine().stopDialog();
        } else {
            SpeechClient.instance().getAgent().sendUIEvent(FeedUIEvent.SCRIPT_QUIT, null);
        }
    }

    public void startSpeechDialog() {
        SpeechClient.instance().getAgent().triggerEvent(FeedUIEvent.SCRIPT_QUIT, null);
    }

    public void directNavigation() {
        SpeechClient.instance().getAgent().triggerEvent(FeedUIEvent.SCRIPT_QUIT, null);
    }

    public void onControlHistory(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onControlHistory();
            }
        }
    }

    public void syncRoute(List<RouteSelectBean> list, String id, boolean isBaseInfo) {
        JSONObject data = new JSONObject();
        try {
            JSONArray jsArray = new JSONArray();
            if (list != null && list.size() > 0) {
                for (RouteSelectBean routeSelectBean : list) {
                    jsArray.put(routeSelectBean.toJson());
                }
            }
            data.put("route_list", jsArray.toString());
            data.put("localStory", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (isBaseInfo) {
            SpeechClient.instance().getAgent().triggerEvent(FeedUIEvent.LIST_ROUT_UPLOAD, data.toString());
        }
        syncRouteToInfoFlow(list, id, isBaseInfo);
    }

    public void syncRouteToInfoFlow(List<RouteSelectBean> list, String id, boolean isBaseInfo) {
        ListWidget listWidget = new ListWidget();
        listWidget.setTitle(FeedListUIValue.TYPE_ROUTE);
        listWidget.setExtraType(ListWidget.EXTRA_TYPE_NAVI_ROUTE);
        if (isBaseInfo) {
            listWidget.setWidgetId(id + BASE_ROUTE_WIDGET_ID);
        } else {
            listWidget.setWidgetId(id + ALL_ROUTE_WIDGET_ID);
        }
        if (list != null && list.size() > 0) {
            for (RouteSelectBean routeSelectBean : list) {
                ContentWidget contentWidget = new ContentWidget();
                contentWidget.setTitle(routeSelectBean.totalTimeLine1);
                contentWidget.setSubTitle(routeSelectBean.routeTypeName);
                try {
                    contentWidget.addExtra(ListWidget.EXTRA_TYPE_NAVI_ROUTE, routeSelectBean.toJson().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listWidget.addContentWidget(contentWidget);
            }
        }
        LogUtils.i("NaviNode", "syncRouteToInfoFlow:" + listWidget.toString());
        SpeechClient.instance().getAgent().sendEvent(ContextEvent.WIDGET_LIST, listWidget.toString());
    }

    public void onGetSettingsInfo(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onGetSettingsInfo();
            }
        }
    }

    public void postSettingsInfo(String info) {
        SpeechClient.instance().getActorBridge().send(new ResultActor(NaviEvent.SETTINGS_INFO_GET).setResult(info));
    }

    public void onControlParkRecommendOn(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onControlParkRecommendOn();
            }
        }
    }

    public void onControlParkRecommendOff(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onControlParkRecommendOff();
            }
        }
    }

    public void onSetScaleLevel(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null && !TextUtils.isEmpty(data)) {
            try {
                JSONObject object = new JSONObject(data);
                if (object.has("level")) {
                    int level = object.getInt("level");
                    for (Object obj : listenerList) {
                        ((NaviListener) obj).onSetScaleLevel(level);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void onAlertsPreferenceSet(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null && !TextUtils.isEmpty(data)) {
            NaviPreferenceBean pref = NaviPreferenceBean.fromJson(data);
            for (Object obj : listenerList) {
                ((NaviListener) obj).onAlertsPreferenceSet(pref);
            }
        }
    }

    public void onAvoidRouteSet(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null && !TextUtils.isEmpty(data)) {
            NaviPreferenceBean pref = NaviPreferenceBean.fromJson(data);
            for (Object obj : listenerList) {
                ((NaviListener) obj).onAvoidRouteSet(pref);
            }
        }
    }

    public void onAutoRerouteBetterRoute(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onAutoRerouteBetterRoute();
            }
        }
    }

    public void onAutoRerouteAskFirst(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onAutoRerouteAskFirst();
            }
        }
    }

    public void onAutoRerouteNever(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onAutoRerouteNever();
            }
        }
    }

    public void onMapShowSet(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null && !TextUtils.isEmpty(data)) {
            NaviPreferenceBean pref = NaviPreferenceBean.fromJson(data);
            for (Object obj : listenerList) {
                ((NaviListener) obj).onMapShowSet(pref);
            }
        }
    }

    public void onPoiDetailsFavoriteAdd(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onPoiDetailsFavoriteAdd();
            }
        }
    }

    public void onPoiDetailsFavoriteDel(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onPoiDetailsFavoriteDel();
            }
        }
    }

    public void onControlSettingsCLose(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onControlSettingsClose();
            }
        }
    }

    public void onControlHistoryCLose(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((NaviListener) obj).onControlHistoryClose();
            }
        }
    }
}
