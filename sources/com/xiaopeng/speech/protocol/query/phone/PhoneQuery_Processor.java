package com.xiaopeng.speech.protocol.query.phone;

import com.xiaopeng.speech.annotation.IQueryProcessor;
import com.xiaopeng.speech.protocol.event.query.QueryPhoneEvent;
/* loaded from: classes.dex */
public class PhoneQuery_Processor implements IQueryProcessor {
    private PhoneQuery mTarget;

    public PhoneQuery_Processor(PhoneQuery target) {
        this.mTarget = target;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public Object querySensor(String event, String data) {
        char c;
        switch (event.hashCode()) {
            case -991919164:
                if (event.equals(QueryPhoneEvent.GET_PAGE_OPEN_STATUS)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 579161930:
                if (event.equals(QueryPhoneEvent.GET_BLUETOOTH_STATUS)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1113652927:
                if (event.equals(QueryPhoneEvent.GET_CONTACT_SYNC_STATUS)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return Integer.valueOf(this.mTarget.getGuiPageOpenState(event, data));
            case 1:
                return Boolean.valueOf(this.mTarget.onQueryBluetooth(event, data));
            case 2:
                return Integer.valueOf(this.mTarget.onQueryContactSyncStatus(event, data));
            default:
                return null;
        }
    }

    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public String[] getQueryEvents() {
        return new String[]{QueryPhoneEvent.GET_PAGE_OPEN_STATUS, QueryPhoneEvent.GET_BLUETOOTH_STATUS, QueryPhoneEvent.GET_CONTACT_SYNC_STATUS};
    }
}
