package com.xiaopeng.speech.protocol.query.personalCenter;

import com.xiaopeng.speech.annotation.IQueryProcessor;
import com.xiaopeng.speech.protocol.event.query.QueryPersonalCenterEvent;
/* loaded from: classes.dex */
public class PersonalCenterQuery_Processor implements IQueryProcessor {
    private PersonalCenterQuery mTarget;

    public PersonalCenterQuery_Processor(PersonalCenterQuery target) {
        this.mTarget = target;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public Object querySensor(String event, String data) {
        char c;
        switch (event.hashCode()) {
            case -1082449569:
                if (event.equals(QueryPersonalCenterEvent.GET_PAGE_OPEN_STATUS_PERSONAL_CENTER)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 982269335:
                if (event.equals(QueryPersonalCenterEvent.CONTROL_PROFILE_HABIT_NUM_SUPPORT)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1404633087:
                if (event.equals(QueryPersonalCenterEvent.CONTROL_PROFILE_HABIT_SUPPORT)) {
                    c = 0;
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
                return Integer.valueOf(this.mTarget.getControlProfileHabitSupport(event, data));
            case 1:
                return Integer.valueOf(this.mTarget.getControlProfileHabitNumSupport(event, data));
            case 2:
                return Integer.valueOf(this.mTarget.getGuiPageOpenState(event, data));
            default:
                return null;
        }
    }

    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public String[] getQueryEvents() {
        return new String[]{QueryPersonalCenterEvent.CONTROL_PROFILE_HABIT_SUPPORT, QueryPersonalCenterEvent.CONTROL_PROFILE_HABIT_NUM_SUPPORT, QueryPersonalCenterEvent.GET_PAGE_OPEN_STATUS_PERSONAL_CENTER};
    }
}
