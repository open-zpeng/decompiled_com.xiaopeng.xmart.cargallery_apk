package com.xiaopeng.speech.protocol.node.userbook;

import com.xiaopeng.speech.SpeechNode;
import com.xiaopeng.speech.protocol.node.carcontrol.CarcontrolListener;
import com.xiaopeng.speech.protocol.node.carcontrol.bean.UserBookValue;
/* loaded from: classes.dex */
public class UserBookNode extends SpeechNode<UserBookListener> {
    /* JADX INFO: Access modifiers changed from: protected */
    public void onCheckUserBook(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        UserBookValue userBookValue = UserBookValue.fromJson(data);
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onCheckUserBook(userBookValue);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onOpenUserBook(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onOpenUserBook();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onCloseUserBook(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onCloseUserBook();
            }
        }
    }
}
