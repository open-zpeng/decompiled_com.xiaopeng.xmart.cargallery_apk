package com.xiaopeng.speech.protocol.query.phone;

import com.xiaopeng.speech.IQueryCaller;
/* loaded from: classes.dex */
public interface IPhoneQueryCaller extends IQueryCaller {
    int getGuiPageOpenState(String str);

    boolean onQueryBluetooth();

    int onQueryContactSyncStatus();
}
