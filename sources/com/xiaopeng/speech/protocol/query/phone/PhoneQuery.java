package com.xiaopeng.speech.protocol.query.phone;

import com.xiaopeng.speech.SpeechQuery;
/* loaded from: classes.dex */
public class PhoneQuery extends SpeechQuery<IPhoneQueryCaller> {
    /* JADX INFO: Access modifiers changed from: protected */
    public int getGuiPageOpenState(String event, String data) {
        return ((IPhoneQueryCaller) this.mQueryCaller).getGuiPageOpenState(data);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean onQueryBluetooth(String event, String data) {
        return ((IPhoneQueryCaller) this.mQueryCaller).onQueryBluetooth();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int onQueryContactSyncStatus(String event, String data) {
        return ((IPhoneQueryCaller) this.mQueryCaller).onQueryContactSyncStatus();
    }
}
