package com.xiaopeng.speech.protocol.node.internal;

import android.text.TextUtils;
import com.xiaopeng.speech.SpeechNode;
import com.xiaopeng.speech.common.SpeechEvent;
import com.xiaopeng.speech.jarvisproto.WakeupResult;
import com.xiaopeng.speech.speechwidget.SpeechWidget;
import com.xiaopeng.xmart.cargallery.bean.BIConfig;
import org.json.JSONException;
import org.json.JSONObject;
/* loaded from: classes.dex */
public class InternalNode extends SpeechNode<InternalListener> {
    /* JADX INFO: Access modifiers changed from: protected */
    public void onDmOutput(String event, String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONObject dm = jsonObject.optJSONObject("dm");
            String dataFrom = dm.optString("dataFrom", "");
            if (dataFrom.equals("native")) {
                String api = dm.optString(SpeechWidget.DATA_SOURCE_API);
                if (TextUtils.isEmpty(api)) {
                    return;
                }
                String str = SpeechEvent.NATIVE_API_SCHEME + api;
            }
            JSONObject commandObj = dm.optJSONObject(WakeupResult.REASON_COMMAND);
            if (commandObj != null) {
                String api2 = commandObj.optString(SpeechWidget.DATA_SOURCE_API);
                if (!TextUtils.isEmpty(api2)) {
                    String str2 = SpeechEvent.COMMAND_SCHEME + api2;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((InternalListener) obj).onDmOutput(data);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onInputDmData(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((InternalListener) obj).onInputDmData(event, data);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLocalWakeupResult(String event, String data) {
        String type = "";
        String word = "";
        String channel = "";
        try {
            JSONObject jsonObject = new JSONObject(data);
            type = jsonObject.optString(BIConfig.PROPERTY.DATA_TYPE);
            word = jsonObject.optString("word");
            channel = jsonObject.optString("channel");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (int i = 0; i < listenerList.length; i++) {
                ((InternalListener) listenerList[i]).onLocalWakeupResult(type, word);
                ((InternalListener) listenerList[i]).onLocalWakeupResultWithChannel(type, word, channel);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onResourceUpdateFinish(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((InternalListener) obj).onResourceUpdateFinish(event, data);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onRebootComplete(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((InternalListener) obj).onRebootComplete(event, data);
            }
        }
    }
}
