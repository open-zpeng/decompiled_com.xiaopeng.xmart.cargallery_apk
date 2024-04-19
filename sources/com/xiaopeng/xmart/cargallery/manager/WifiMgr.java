package com.xiaopeng.xmart.cargallery.manager;

import android.content.Context;
import android.net.wifi.WifiManager;
import com.xiaopeng.xmart.cargallery.CameraLog;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
/* loaded from: classes9.dex */
public final class WifiMgr {
    private static final String TAG = WifiMgr.class.getSimpleName();
    private static WifiMgr sWifiMgr;
    private WifiManager mWifiManager;

    private WifiMgr(Context context) {
        this.mWifiManager = (WifiManager) context.getSystemService("wifi");
    }

    public static WifiMgr getInstance(Context context) {
        if (sWifiMgr == null) {
            synchronized (WifiMgr.class) {
                if (sWifiMgr == null) {
                    sWifiMgr = new WifiMgr(context);
                }
            }
        }
        return sWifiMgr;
    }

    public void disableWifi() {
        WifiManager wifiManager = this.mWifiManager;
        if (wifiManager != null) {
            wifiManager.setWifiEnabled(false);
        }
    }

    public boolean isWifiOn() {
        WifiManager wifiManager = this.mWifiManager;
        if (wifiManager != null) {
            boolean isWifiOn = wifiManager.getWifiState() == 3;
            CameraLog.d(TAG, "fetch wifi statue isWifiOn:" + isWifiOn, false);
            return isWifiOn;
        }
        return false;
    }

    public static String getIpAddress() {
        try {
            Enumeration<NetworkInterface> enNetI = NetworkInterface.getNetworkInterfaces();
            while (enNetI.hasMoreElements()) {
                NetworkInterface netI = enNetI.nextElement();
                Enumeration<InetAddress> enumIpAddr = netI.getInetAddresses();
                while (enumIpAddr.hasMoreElements()) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if ((inetAddress instanceof Inet4Address) && !inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
            return "";
        } catch (SocketException e) {
            e.printStackTrace();
            return "";
        }
    }
}
