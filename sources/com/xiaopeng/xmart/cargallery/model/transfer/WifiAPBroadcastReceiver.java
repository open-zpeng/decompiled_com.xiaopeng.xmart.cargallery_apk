package com.xiaopeng.xmart.cargallery.model.transfer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.xiaopeng.xmart.cargallery.CameraLog;
/* loaded from: classes8.dex */
public abstract class WifiAPBroadcastReceiver extends BroadcastReceiver {
    public static final String ACTION_WIFI_AP_STATE_CHANGED = "android.net.wifi.WIFI_AP_STATE_CHANGED";
    public static final String TAG = WifiAPBroadcastReceiver.class.getSimpleName();

    public abstract void onWifiApEnabled();

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(ACTION_WIFI_AP_STATE_CHANGED)) {
            int state = intent.getIntExtra("wifi_state", 0);
            String str = TAG;
            CameraLog.d(str, "Wifi Ap state--->>>" + state, false);
            if (13 == state) {
                CameraLog.d(str, "wifi ap is enabled", false);
                onWifiApEnabled();
            } else if (2 == state) {
                CameraLog.d(str, "wifi ap is enabling", false);
            } else if (11 == state) {
                CameraLog.d(str, "wifi ap is disabled", false);
            } else if (10 == state) {
                CameraLog.d(str, "wifi ap is disabling", false);
            }
        }
    }
}
