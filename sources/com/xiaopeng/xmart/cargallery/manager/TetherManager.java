package com.xiaopeng.xmart.cargallery.manager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiClient;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.xiaopeng.xmart.cargallery.App;
import com.xiaopeng.xmart.cargallery.CameraLog;
import com.xiaopeng.xmart.cargallery.utils.SpUtils;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.List;
import java.util.concurrent.TimeUnit;
/* loaded from: classes9.dex */
public class TetherManager {
    private static final long CHECK_AP_STATE_COUNT = 3;
    private static final long CHECK_AP_STATE_TIME_GAP = 1;
    private static TetherManager INSTANCE = null;
    private static final int RECOVER_WIFI_STATUE_TIME_GAP = 2000;
    private static final int RECOVER_WIFI_STATUE_TIME_OUT = 3;
    private static final String TAG = "TetherManager";
    private static final String WIFI_DEFAULT_PASSWORD = "xp123456";
    private static final String WIFI_PREFIX = "Xiaopeng_";
    private WifiTetherSoftApCallback mCallback;
    private Disposable mCheckAPStateDisposable;
    private Context mContext;
    private WifiManager mWifiManager;
    private WifiManager.SoftApCallback mSoftApCallback = new WifiManager.SoftApCallback() { // from class: com.xiaopeng.xmart.cargallery.manager.TetherManager.1
        public void onStateChanged(int state, int failureReason) {
            CameraLog.d(TetherManager.TAG, "onStateChange...  state: " + state + "failureReason" + failureReason);
            if (TetherManager.this.mCallback != null) {
                TetherManager.this.mCallback.onStateChanged(state, failureReason);
            }
        }

        public void onNumClientsChanged(int numClients) {
            CameraLog.d(TetherManager.TAG, "onNumClientsChanged... numClients: " + numClients);
            if (TetherManager.this.mCallback != null) {
                TetherManager.this.mCallback.onNumClientsChanged(numClients);
            }
        }

        public void onClientsUpdated(List<WifiClient> clients) {
            CameraLog.d(TetherManager.TAG, "onClientsUpdated... clients: " + clients.toString(), false);
            if (TetherManager.this.mCallback != null) {
                TetherManager.this.mCallback.onClientsUpdated(clients);
            }
        }
    };
    final ConnectivityManager.OnStartTetheringCallback mOnStartTetheringCallback = new ConnectivityManager.OnStartTetheringCallback() { // from class: com.xiaopeng.xmart.cargallery.manager.TetherManager.2
        public void onTetheringFailed() {
            CameraLog.d(TetherManager.TAG, "热点开启失败，请手动开启！", false);
            TetherManager.this.unRegisterSoftApCallback(true);
            super.onTetheringFailed();
        }

        public void onTetheringStarted() {
            CameraLog.d(TetherManager.TAG, "热点开启成功！", false);
            TetherManager.this.registerSoftApCallback();
            super.onTetheringFailed();
        }
    };
    private ConnectivityManager mConnectivityManager = (ConnectivityManager) App.getInstance().getSystemService("connectivity");

    /* loaded from: classes9.dex */
    public interface WifiTetherSoftApCallback {
        void onClientsUpdated(List<WifiClient> clients);

        void onNumClientsChanged(int numClients);

        void onStateChanged(int state, int failureReason);

        void onTetherStartFailure();

        void onTetherStartSuccess();
    }

    private TetherManager(Context context, WifiTetherSoftApCallback callback) {
        this.mContext = context;
        this.mCallback = callback;
        this.mWifiManager = (WifiManager) context.getSystemService("wifi");
    }

    public static TetherManager getInstance(Context context, WifiTetherSoftApCallback callback) {
        return new TetherManager(context, callback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void registerSoftApCallback() {
        this.mWifiManager.registerSoftApCallback(this.mSoftApCallback, new Handler(this.mContext.getMainLooper()));
        WifiTetherSoftApCallback wifiTetherSoftApCallback = this.mCallback;
        if (wifiTetherSoftApCallback != null) {
            wifiTetherSoftApCallback.onTetherStartSuccess();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unRegisterSoftApCallback(boolean notify) {
        WifiTetherSoftApCallback wifiTetherSoftApCallback;
        this.mWifiManager.unregisterSoftApCallback(this.mSoftApCallback);
        if (notify && (wifiTetherSoftApCallback = this.mCallback) != null) {
            wifiTetherSoftApCallback.onTetherStartFailure();
        }
    }

    public void startTether(final String ssid, final String pwd) {
        clearTetherForStart();
        this.mCheckAPStateDisposable = Observable.interval(0L, 1L, TimeUnit.SECONDS).subscribe(new Consumer() { // from class: com.xiaopeng.xmart.cargallery.manager.-$$Lambda$TetherManager$QjtRM01HX7dUs6siJDElMEIk4Lg
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                TetherManager.this.lambda$startTether$0$TetherManager(ssid, pwd, (Long) obj);
            }
        });
    }

    public /* synthetic */ void lambda$startTether$0$TetherManager(final String ssid, final String pwd, Long aLong) throws Exception {
        boolean timeOut = aLong.longValue() / 1 >= 3;
        boolean apDisabled = this.mWifiManager.getWifiApState() == 11;
        CameraLog.d(TAG, "check ap state,time : " + aLong + " apDisabled: " + apDisabled + " time out:" + timeOut, false);
        if (timeOut || apDisabled) {
            Disposable disposable = this.mCheckAPStateDisposable;
            if (disposable != null) {
                disposable.dispose();
            }
            CameraLog.d(TAG, "startTether...", false);
            WifiConfiguration wificonfiguration = this.mWifiManager.getWifiApConfiguration();
            wificonfiguration.SSID = generateWifiSSID(ssid);
            wificonfiguration.preSharedKey = generateRandomPw(pwd);
            boolean result = this.mWifiManager.setWifiApConfiguration(wificonfiguration);
            CameraLog.e(TAG, "startTether()..." + (result ? "success" : "failure"), false);
            ConnectivityManager mConnectivityManager = (ConnectivityManager) App.getInstance().getSystemService("connectivity");
            mConnectivityManager.startTethering(0, true, this.mOnStartTetheringCallback, new Handler(Looper.getMainLooper()));
        }
    }

    private void clearTetherForStart() {
        CameraLog.d(TAG, "clear tether...", false);
        unRegisterSoftApCallback(false);
        this.mConnectivityManager.stopTethering(0);
    }

    public void stopTether() {
        Disposable disposable = this.mCheckAPStateDisposable;
        if (disposable != null) {
            disposable.dispose();
        }
        CameraLog.d(TAG, "stop tether...", false);
        unRegisterSoftApCallback(false);
        this.mConnectivityManager.stopTethering(0);
    }

    public String generateWifiSSID(String defaultSSID) {
        if (!TextUtils.isEmpty(defaultSSID)) {
            return defaultSSID;
        }
        CameraLog.d(TAG, "default ssid is null,please check the code!", false);
        String ssid = (String) SpUtils.get(this.mContext, SpUtils.SP_KEY_WIFI_SSID, "");
        if (TextUtils.isEmpty(ssid)) {
            String ssid2 = WIFI_PREFIX + String.format("%04d", Integer.valueOf((int) (Math.random() * 10000.0d)));
            SpUtils.put(this.mContext, SpUtils.SP_KEY_WIFI_SSID, ssid2);
            return ssid2;
        }
        return ssid;
    }

    public String generateRandomPw(String defaultPwd) {
        if (!TextUtils.isEmpty(defaultPwd)) {
            return defaultPwd;
        }
        CameraLog.d(TAG, "default pwd is null,please check the code!", false);
        return WIFI_DEFAULT_PASSWORD;
    }
}
