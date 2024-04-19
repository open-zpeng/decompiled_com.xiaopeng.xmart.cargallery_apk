package com.xiaopeng.speech.protocol.node.skill;

import com.xiaopeng.speech.SpeechNode;
/* loaded from: classes.dex */
public class SkillDialogNode extends SpeechNode<SkillDialogListener> {
    /* JADX INFO: Access modifiers changed from: protected */
    public void onCloseWindow(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((SkillDialogListener) obj).onCloseWindow(data);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onOpenWindow(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((SkillDialogListener) obj).onOpenWindow(data);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onForwardScreenEvent(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((SkillDialogListener) obj).onForwardScreenEvent(data);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onRefreshSkillData(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((SkillDialogListener) obj).onRefreshSkillData(data);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onShowDoubleGuide(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((SkillDialogListener) obj).onShowDoubleGuide(data);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onShowKeyGuide(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((SkillDialogListener) obj).onShowKeyGuide(data);
            }
        }
    }
}
