package com.xiaopeng.speech.protocol.node.personalCenter;

import com.xiaopeng.speech.annotation.ICommandProcessor;
import com.xiaopeng.speech.protocol.event.PersonalCenterEvent;
/* loaded from: classes.dex */
public class PersonalCenterNode_Processor implements ICommandProcessor {
    private PersonalCenterNode mTarget;

    public PersonalCenterNode_Processor(PersonalCenterNode target) {
        this.mTarget = target;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public void performCommand(String event, String data) {
        char c;
        switch (event.hashCode()) {
            case 911050310:
                if (event.equals(PersonalCenterEvent.CONTROL_PROFILE_HABIT_SELECT_NEXT)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1416411835:
                if (event.equals(PersonalCenterEvent.CONTROL_PROFILE_HABIT_SELECT)) {
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
                this.mTarget.onControlProfileHabitSelect(event, data);
                return;
            case 1:
                this.mTarget.onControlProfileHabitSelectNext(event, data);
                return;
            default:
                return;
        }
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public String[] getSubscribeEvents() {
        return new String[]{PersonalCenterEvent.CONTROL_PROFILE_HABIT_SELECT, PersonalCenterEvent.CONTROL_PROFILE_HABIT_SELECT_NEXT};
    }
}
