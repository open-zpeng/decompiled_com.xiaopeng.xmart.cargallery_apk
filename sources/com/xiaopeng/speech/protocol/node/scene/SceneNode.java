package com.xiaopeng.speech.protocol.node.scene;

import android.text.TextUtils;
import com.xiaopeng.speech.SpeechNode;
import com.xiaopeng.speech.common.SpeechConstant;
import com.xiaopeng.speech.jarvisproto.DMEnd;
import com.xiaopeng.speech.jarvisproto.DMStart;
import com.xiaopeng.speech.jarvisproto.VuiDisable;
import com.xiaopeng.speech.jarvisproto.VuiEnable;
import java.util.Arrays;
import java.util.List;
import org.json.JSONObject;
/* loaded from: classes.dex */
public class SceneNode extends SpeechNode<SceneListener> {
    List<String> supportAppNameList = Arrays.asList("com.android.systemui", "com.xiaopeng.chargecontrol", "com.xiaopeng.speech.scenedemo", SpeechConstant.SPEECH_SERVICE_PACKAGE_NAME, "com.xiaopeng.caraccount");

    public void onSceneEvent(String event, String data) {
        if (TextUtils.isEmpty(data)) {
            return;
        }
        try {
            String sceneId = new JSONObject(data).optString("sceneId");
            if (TextUtils.isEmpty(sceneId)) {
                return;
            }
            String packageName = null;
            if (sceneId.contains("-")) {
                packageName = sceneId.split("-")[0];
            } else if (sceneId.contains("_")) {
                packageName = sceneId.split("_")[0];
            }
            if (!TextUtils.isEmpty(packageName) && this.supportAppNameList.contains(packageName)) {
                callSceneEvent(event, data);
            }
        } catch (Exception e) {
        }
    }

    public void onDMStart(String event, String data) {
        callSceneEvent(DMStart.EVENT, data);
    }

    public void onDMEnd(String event, String data) {
        callSceneEvent(DMEnd.EVENT, data);
    }

    public void onVuiEnable(String event, String data) {
        callSceneEvent(VuiEnable.EVENT, data);
    }

    public void onVuiDisable(String event, String data) {
        callSceneEvent(VuiDisable.EVENT, data);
    }

    public void onRebuild(String event, String data) {
        callSceneEvent(event, data);
    }

    private void callSceneEvent(String event, String data) {
        try {
            Object[] listenerList = this.mListenerList.collectCallbacks();
            if (listenerList != null) {
                for (Object obj : listenerList) {
                    ((SceneListener) obj).onSceneEvent(event, data);
                }
            }
        } catch (Exception e) {
        }
    }
}
