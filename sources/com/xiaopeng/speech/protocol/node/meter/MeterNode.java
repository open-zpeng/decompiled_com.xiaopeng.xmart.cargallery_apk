package com.xiaopeng.speech.protocol.node.meter;

import com.xiaopeng.speech.SpeechNode;
import org.json.JSONException;
import org.json.JSONObject;
/* loaded from: classes.dex */
public class MeterNode extends SpeechNode<MeterListener> {
    /* JADX INFO: Access modifiers changed from: protected */
    public void setLeftCard(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        int index = -1;
        try {
            JSONObject jsonObject = new JSONObject(data);
            index = jsonObject.optInt("index");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((MeterListener) obj).setLeftCard(index);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setRightCard(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        int index = -1;
        try {
            JSONObject jsonObject = new JSONObject(data);
            index = jsonObject.optInt("index");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((MeterListener) obj).setRightCard(index);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onDashboardLightsStatus(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((MeterListener) obj).onDashboardLightsStatus();
            }
        }
    }
}
