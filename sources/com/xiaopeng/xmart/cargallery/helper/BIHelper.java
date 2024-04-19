package com.xiaopeng.xmart.cargallery.helper;

import android.content.Context;
import android.text.TextUtils;
import com.xiaopeng.datalog.DataLogModuleEntry;
import com.xiaopeng.lib.bughunter.BugHunter;
import com.xiaopeng.lib.framework.module.Module;
import com.xiaopeng.lib.framework.moduleinterface.datalogmodule.IDataLog;
import com.xiaopeng.lib.framework.moduleinterface.datalogmodule.IMoleEventBuilder;
import com.xiaopeng.xmart.cargallery.CameraLog;
import com.xiaopeng.xmart.cargallery.bean.BIConfig;
import com.xiaopeng.xmart.cargallery.utils.DeviceUtils;
import java.util.Map;
/* loaded from: classes12.dex */
public class BIHelper {
    private static final String TAG = "BIHelper";
    private IDataLog mDataLogService;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes12.dex */
    public static class SingletonHolder {
        static BIHelper sInstance = new BIHelper();

        private SingletonHolder() {
        }
    }

    public static BIHelper getInstance() {
        return SingletonHolder.sInstance;
    }

    public void init(Context context) {
        BugHunter.init(context);
        this.mDataLogService = (IDataLog) Module.get(DataLogModuleEntry.class).get(IDataLog.class);
    }

    public void uploadGalleryBI(int scene, int eventId, Map<String, Number> params) {
        CameraLog.d(TAG, "uploadGalleryBI, scene : " + scene + ", eventId: " + eventId, false);
        String pageId = "";
        String buttonId = "";
        switch (scene) {
            case 1:
                pageId = "P00005";
                buttonId = BIConfig.ButtionID.GALLERY_360_VIIEW_ENTER_BUTTON_ID;
                break;
            case 2:
                pageId = "P00005";
                buttonId = BIConfig.ButtionID.GALLERY_DVR_VIIEW_ENTER_BUTTON_ID;
                break;
            case 3:
                pageId = BIConfig.PageId.GALLERY_TOP_VIIEW_ENTER_PAGE_ID;
                buttonId = BIConfig.ButtionID.GALLERY_TOP_VIIEW_ENTER_BUTTON_ID;
                break;
            case 8:
                pageId = BIConfig.PageId.GALLERY_OPERATION_PAGE_ID;
                switch (eventId) {
                    case 21:
                        buttonId = BIConfig.ButtionID.GALLERY_OPERATION_PANORAMA;
                        break;
                    case 22:
                        buttonId = BIConfig.ButtionID.GALLERY_OPERATION_DVR;
                        break;
                    case 23:
                        buttonId = "B006";
                        break;
                    case 24:
                        buttonId = BIConfig.ButtionID.GALLERY_SHOCK_SHARE_TOP;
                        break;
                    case 26:
                        buttonId = BIConfig.ButtionID.GALLERY_SHOCK_VIDEO_PLAY;
                        break;
                }
            case 9:
                pageId = "P00005";
                buttonId = "B007";
                break;
        }
        if (TextUtils.isEmpty(pageId) || TextUtils.isEmpty(buttonId)) {
            CameraLog.d(TAG, "uploadTopCameraBI,  pageId is null ");
        } else {
            sendMqttDataLog(pageId, buttonId, params);
        }
    }

    private void sendMqttDataLog(final String pageId, final String buttonId, final Map<String, Number> params) {
        if ((DeviceUtils.isInter() && (!"Q1".equals(CarTypeHelper.getXpCduType()) || !"Q8".equals(CarTypeHelper.getXpCduType()))) || this.mDataLogService == null) {
            return;
        }
        ThreadPoolHelper.getInstance().execute(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.helper.BIHelper.1
            @Override // java.lang.Runnable
            public void run() {
                IDataLog dataLog = BIHelper.this.mDataLogService;
                IMoleEventBuilder eventBuilder = dataLog.buildMoleEvent().setEvent(BIConfig.EVENT_CAMERA).setModule(BIConfig.MODULE_CAMERA).setPageId(pageId).setButtonId(buttonId);
                String paramStr = "";
                Map map = params;
                if (map != null) {
                    for (Map.Entry<String, Number> entry : map.entrySet()) {
                        eventBuilder.setProperty(entry.getKey(), entry.getValue());
                        paramStr = paramStr + entry.getKey() + " : " + entry.getValue() + ";";
                    }
                }
                dataLog.sendStatData(eventBuilder.build());
                CameraLog.d(BIHelper.TAG, "sendMqttDataLog, pageId : " + pageId + ", buttonId: " + buttonId + ", params: " + paramStr, false);
            }
        });
    }
}
