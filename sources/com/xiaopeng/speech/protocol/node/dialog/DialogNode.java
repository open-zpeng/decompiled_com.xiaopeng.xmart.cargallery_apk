package com.xiaopeng.speech.protocol.node.dialog;

import android.text.TextUtils;
import com.xiaopeng.speech.SpeechNode;
import com.xiaopeng.speech.jarvisproto.DMWait;
import com.xiaopeng.speech.protocol.node.dialog.bean.DialogEndReason;
import com.xiaopeng.speech.protocol.node.dialog.bean.WakeupReason;
/* loaded from: classes.dex */
public class DialogNode extends SpeechNode<DialogListener> {
    /* JADX INFO: Access modifiers changed from: protected */
    public void onDialogStart(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        WakeupReason wakeupReason = WakeupReason.fromJson(data);
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((DialogListener) obj).onDialogStart(wakeupReason);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onDialogError(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((DialogListener) obj).onDialogError();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onDialogEnd(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        DialogEndReason endReason = DialogEndReason.fromJson(data);
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((DialogListener) obj).onDialogEnd(endReason);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onDialogWait(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        DMWait waitReason = new DMWait();
        if (!TextUtils.isEmpty(data)) {
            waitReason = DMWait.fromJson(data);
        }
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((DialogListener) obj).onDialogWait(waitReason);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onDialogContinue(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((DialogListener) obj).onDialogContinue();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWakeupResult(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((DialogListener) obj).onWakeupResult();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onVadBegin(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((DialogListener) obj).onVadBegin();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onVadEnd(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((DialogListener) obj).onVadEnd();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onDialogStatusChanged(String event, String status) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((DialogListener) obj).onDialogStatusChanged(status);
            }
        }
    }
}
