package com.xiaopeng.speech.protocol.query.speech;

import com.xiaopeng.speech.IQueryCaller;
/* loaded from: classes.dex */
public interface ISpeechQueryCaller extends IQueryCaller {
    String getCarPlatForm();

    int getCurrentMode();

    int getSoundLocation();

    boolean isAccountLogin();

    boolean isAppForeground(String str);

    boolean isEnableGlobalWakeup();

    default int getVuiSceneSwitchStatus() {
        return -1;
    }

    default int getFirstSpeechState() {
        return -1;
    }

    default int getCurrentTtsEngine() {
        return 1;
    }

    default boolean appIsInstalled(String packageName) {
        return false;
    }

    default boolean isUserExpressionOpened() {
        return false;
    }
}
