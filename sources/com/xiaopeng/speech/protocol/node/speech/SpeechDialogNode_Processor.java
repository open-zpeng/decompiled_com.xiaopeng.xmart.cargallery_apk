package com.xiaopeng.speech.protocol.node.speech;

import com.xiaopeng.speech.annotation.ICommandProcessor;
import com.xiaopeng.speech.protocol.event.SpeechDialogEvent;
/* loaded from: classes.dex */
public class SpeechDialogNode_Processor implements ICommandProcessor {
    private SpeechDialogNode mTarget;

    public SpeechDialogNode_Processor(SpeechDialogNode target) {
        this.mTarget = target;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public void performCommand(String event, String data) {
        char c;
        switch (event.hashCode()) {
            case -1817558719:
                if (event.equals(SpeechDialogEvent.REFRESH_CARSPEECHSERVICE_UI)) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case -1676326235:
                if (event.equals(SpeechDialogEvent.SWITCH_SPEECH_CONTINUOUS_ENABLE)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -1440570874:
                if (event.equals(SpeechDialogEvent.SWITCH_SPEECH_CONTINUOUS_DISABLED)) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -1181317093:
                if (event.equals(SpeechDialogEvent.SYSTEM_LISTENTIME_ACCOMPANY_CLOSE)) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case -412443633:
                if (event.equals(SpeechDialogEvent.ROUTE_OPEN_SCENE_GUIDE_WINDOW)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -265464658:
                if (event.equals(SpeechDialogEvent.ROUTE_CLOSE_SCENE_GUIDE_WINDOW)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -176140078:
                if (event.equals(SpeechDialogEvent.ROUTE_CLOSE_SPEECH_WINDOW)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 239348679:
                if (event.equals(SpeechDialogEvent.SYSTEM_LISTENTIME_ACCOMPANY_OPEN)) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 1975136465:
                if (event.equals(SpeechDialogEvent.ROUTE_OPEN_SPEECH_WINDOW)) {
                    c = 1;
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
                this.mTarget.onCloseWindow(event, data);
                return;
            case 1:
                this.mTarget.onOpenWindow(event, data);
                return;
            case 2:
                this.mTarget.onOpenSceneGuideWindow(event, data);
                return;
            case 3:
                this.mTarget.onCloseSceneGuideWindow(event, data);
                return;
            case 4:
                this.mTarget.onOpenSpeechSceneSetting(event, data);
                return;
            case 5:
                this.mTarget.onCloseSpeechSceneSetting(event, data);
                return;
            case 6:
                this.mTarget.onOpenSuperDialogue(event, data);
                return;
            case 7:
                this.mTarget.onCloseSuperDialogue(event, data);
                return;
            case '\b':
                this.mTarget.onRefreshUi(event, data);
                return;
            default:
                return;
        }
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public String[] getSubscribeEvents() {
        return new String[]{SpeechDialogEvent.ROUTE_CLOSE_SPEECH_WINDOW, SpeechDialogEvent.ROUTE_OPEN_SPEECH_WINDOW, SpeechDialogEvent.ROUTE_OPEN_SCENE_GUIDE_WINDOW, SpeechDialogEvent.ROUTE_CLOSE_SCENE_GUIDE_WINDOW, SpeechDialogEvent.SWITCH_SPEECH_CONTINUOUS_ENABLE, SpeechDialogEvent.SWITCH_SPEECH_CONTINUOUS_DISABLED, SpeechDialogEvent.SYSTEM_LISTENTIME_ACCOMPANY_OPEN, SpeechDialogEvent.SYSTEM_LISTENTIME_ACCOMPANY_CLOSE, SpeechDialogEvent.REFRESH_CARSPEECHSERVICE_UI};
    }
}
