package com.xiaopeng.speech.protocol.node.xpu;

import android.text.TextUtils;
import com.xiaopeng.speech.SpeechNode;
import org.json.JSONException;
import org.json.JSONObject;
/* loaded from: classes.dex */
public class XpuNode extends SpeechNode<IXpuListener> {
    /* JADX INFO: Access modifiers changed from: protected */
    public void laneChange(String event, String data) {
        try {
            JSONObject object = new JSONObject(data);
            if (object.has("direction")) {
                String direction = object.optString("direction");
                if (TextUtils.isEmpty(direction)) {
                    return;
                }
                int dir = Integer.parseInt(direction);
                Object[] listenerList = this.mListenerList.collectCallbacks();
                if (listenerList != null) {
                    for (Object obj : listenerList) {
                        ((IXpuListener) obj).laneChange(dir);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
