package com.xiaopeng.speech.protocol.query.speech;

import android.text.TextUtils;
import com.xiaopeng.speech.SpeechQuery;
import com.xiaopeng.speech.common.util.LogUtils;
import org.json.JSONException;
import org.json.JSONObject;
/* loaded from: classes.dex */
public class SpeechSensorQuery extends SpeechQuery<ISpeechQueryCaller> {
    /* JADX INFO: Access modifiers changed from: protected */
    public int getSoundLocation(String event, String data) {
        return ((ISpeechQueryCaller) this.mQueryCaller).getSoundLocation();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isAppForeground(String event, String data) {
        String packageName = "";
        try {
            JSONObject jsonObject = new JSONObject(data);
            packageName = jsonObject.optString("package");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ((ISpeechQueryCaller) this.mQueryCaller).isAppForeground(packageName);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isAccountLogin(String event, String data) {
        return ((ISpeechQueryCaller) this.mQueryCaller).isAccountLogin();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isEnableGlobalWakeup(String event, String data) {
        return ((ISpeechQueryCaller) this.mQueryCaller).isEnableGlobalWakeup();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getCurrentMode(String event, String data) {
        return ((ISpeechQueryCaller) this.mQueryCaller).getCurrentMode();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String getCarPlatform(String event, String data) {
        return ((ISpeechQueryCaller) this.mQueryCaller).getCarPlatForm();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getVuiSceneSwitchStatus(String event, String data) {
        return ((ISpeechQueryCaller) this.mQueryCaller).getVuiSceneSwitchStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getFirstSpeechState(String event, String data) {
        return ((ISpeechQueryCaller) this.mQueryCaller).getFirstSpeechState();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getCurrentTtsEngine(String event, String data) {
        return ((ISpeechQueryCaller) this.mQueryCaller).getCurrentTtsEngine();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean appIsInstalled(String event, String data) {
        LogUtils.d("SpeechUiQuery", "enter appIsInstalled , event = " + event + ", data = " + data);
        try {
            if (!TextUtils.isEmpty(data)) {
                JSONObject json = new JSONObject(data);
                String packageName = json.optString("packageName", "");
                return ((ISpeechQueryCaller) this.mQueryCaller).appIsInstalled(packageName);
            }
            return false;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isUserExpressionOpened(String event, String data) {
        return ((ISpeechQueryCaller) this.mQueryCaller).isUserExpressionOpened();
    }
}
