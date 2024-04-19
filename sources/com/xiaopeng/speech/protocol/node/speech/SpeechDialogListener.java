package com.xiaopeng.speech.protocol.node.speech;

import com.xiaopeng.speech.INodeListener;
/* loaded from: classes.dex */
public interface SpeechDialogListener extends INodeListener {
    void onCloseSceneGuideWindow(String str);

    void onCloseSpeechSceneSetting();

    void onCloseWindow(String str);

    void onOpenSceneGuideWindow(String str);

    void onOpenSpeechSceneSetting();

    void onOpenWindow(String str);

    default void onOpenSuperDialogue() {
    }

    default void onCloseSuperDialogue() {
    }

    default void onRefreshUi(int type, boolean state) {
    }
}
