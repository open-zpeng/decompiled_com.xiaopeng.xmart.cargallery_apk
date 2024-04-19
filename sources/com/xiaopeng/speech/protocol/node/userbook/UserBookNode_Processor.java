package com.xiaopeng.speech.protocol.node.userbook;

import com.xiaopeng.speech.annotation.ICommandProcessor;
import com.xiaopeng.speech.protocol.event.CarcontrolEvent;
/* loaded from: classes.dex */
public class UserBookNode_Processor implements ICommandProcessor {
    private UserBookNode mTarget;

    public UserBookNode_Processor(UserBookNode target) {
        this.mTarget = target;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public void performCommand(String event, String data) {
        char c;
        switch (event.hashCode()) {
            case -1001296170:
                if (event.equals(CarcontrolEvent.CLOSE_USER_BOOK)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1414376552:
                if (event.equals(CarcontrolEvent.OPEN_USER_BOOK)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1829348592:
                if (event.equals(CarcontrolEvent.CHECK_USER_BOOK)) {
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
                this.mTarget.onCheckUserBook(event, data);
                return;
            case 1:
                this.mTarget.onOpenUserBook(event, data);
                return;
            case 2:
                this.mTarget.onCloseUserBook(event, data);
                return;
            default:
                return;
        }
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public String[] getSubscribeEvents() {
        return new String[]{CarcontrolEvent.CHECK_USER_BOOK, CarcontrolEvent.OPEN_USER_BOOK, CarcontrolEvent.CLOSE_USER_BOOK};
    }
}
