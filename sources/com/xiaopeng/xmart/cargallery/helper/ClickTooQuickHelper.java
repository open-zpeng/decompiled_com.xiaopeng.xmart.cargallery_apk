package com.xiaopeng.xmart.cargallery.helper;

import android.os.SystemClock;
import java.util.HashMap;
/* loaded from: classes12.dex */
public class ClickTooQuickHelper {
    private static final long CLICK_INTERVAL = 1000;
    private final HashMap<String, Long> mClickRecMap;

    /* loaded from: classes12.dex */
    private static class SingletonHolder {
        private static ClickTooQuickHelper INSTANCE = new ClickTooQuickHelper();

        private SingletonHolder() {
        }
    }

    public static ClickTooQuickHelper getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private ClickTooQuickHelper() {
        this.mClickRecMap = new HashMap<>();
    }

    public boolean isClickTooQuick(String clickEv) {
        long clickTime = SystemClock.elapsedRealtime();
        Long lastClick = this.mClickRecMap.get(clickEv);
        if (lastClick != null && clickTime - lastClick.longValue() < CLICK_INTERVAL) {
            return true;
        }
        this.mClickRecMap.put(clickEv, Long.valueOf(clickTime));
        return false;
    }

    public boolean isClickTooQuick(String clickEv, long timeInterval) {
        long clickTime = SystemClock.elapsedRealtime();
        Long lastClick = this.mClickRecMap.get(clickEv);
        if (lastClick != null && clickTime - lastClick.longValue() < timeInterval) {
            return true;
        }
        this.mClickRecMap.put(clickEv, Long.valueOf(clickTime));
        return false;
    }

    public void resetClickMap() {
        HashMap<String, Long> hashMap = this.mClickRecMap;
        if (hashMap != null) {
            hashMap.clear();
        }
    }
}
