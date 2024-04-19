package com.xiaopeng.speech.protocol.node.dialog;

import com.xiaopeng.speech.INodeListener;
import com.xiaopeng.speech.jarvisproto.DMWait;
import com.xiaopeng.speech.protocol.node.dialog.bean.DialogEndReason;
import com.xiaopeng.speech.protocol.node.dialog.bean.WakeupReason;
/* loaded from: classes.dex */
public interface DialogListener extends INodeListener {
    void onDialogContinue();

    void onDialogEnd(DialogEndReason dialogEndReason);

    void onDialogError();

    void onDialogStart(WakeupReason wakeupReason);

    void onDialogWait(DMWait dMWait);

    void onVadBegin();

    void onVadEnd();

    void onWakeupResult();

    default void onDialogStatusChanged(String status) {
    }
}
