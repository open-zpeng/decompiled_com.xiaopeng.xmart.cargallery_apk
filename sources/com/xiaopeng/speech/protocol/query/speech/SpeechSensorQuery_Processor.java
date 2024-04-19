package com.xiaopeng.speech.protocol.query.speech;

import com.xiaopeng.speech.annotation.IQueryProcessor;
import com.xiaopeng.speech.protocol.event.query.speech.SpeechQueryEvent;
/* loaded from: classes.dex */
public class SpeechSensorQuery_Processor implements IQueryProcessor {
    private SpeechSensorQuery mTarget;

    public SpeechSensorQuery_Processor(SpeechSensorQuery target) {
        this.mTarget = target;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public Object querySensor(String event, String data) {
        char c;
        switch (event.hashCode()) {
            case -1914865600:
                if (event.equals(SpeechQueryEvent.SOUND_LOCATION)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1584155906:
                if (event.equals(SpeechQueryEvent.GET_SCENE_SWITCH_STATUS)) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case -1563492784:
                if (event.equals(SpeechQueryEvent.IS_USEREXPRESSION_OPENED)) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case -414885244:
                if (event.equals(SpeechQueryEvent.GET_CURRENT_MODE)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -323965442:
                if (event.equals(SpeechQueryEvent.IS_WAKEUP_ENABLE)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 386767985:
                if (event.equals(SpeechQueryEvent.GET_CAR_PLATFORM)) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 907076707:
                if (event.equals(SpeechQueryEvent.CURRENT_TTS_ENGINE)) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case 1205362780:
                if (event.equals(SpeechQueryEvent.IS_ACCOUNT_LOGIN)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1352584367:
                if (event.equals(SpeechQueryEvent.IS_APP_FOREGROUND)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1717410403:
                if (event.equals(SpeechQueryEvent.APP_IS_INSTALLED)) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case 1990811260:
                if (event.equals(SpeechQueryEvent.GET_FIRST_SPEECH_STATUS)) {
                    c = 7;
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
                return Integer.valueOf(this.mTarget.getSoundLocation(event, data));
            case 1:
                return Boolean.valueOf(this.mTarget.isAppForeground(event, data));
            case 2:
                return Boolean.valueOf(this.mTarget.isAccountLogin(event, data));
            case 3:
                return Boolean.valueOf(this.mTarget.isEnableGlobalWakeup(event, data));
            case 4:
                return Integer.valueOf(this.mTarget.getCurrentMode(event, data));
            case 5:
                return this.mTarget.getCarPlatform(event, data);
            case 6:
                return Integer.valueOf(this.mTarget.getVuiSceneSwitchStatus(event, data));
            case 7:
                return Integer.valueOf(this.mTarget.getFirstSpeechState(event, data));
            case '\b':
                return Integer.valueOf(this.mTarget.getCurrentTtsEngine(event, data));
            case '\t':
                return Boolean.valueOf(this.mTarget.appIsInstalled(event, data));
            case '\n':
                return Boolean.valueOf(this.mTarget.isUserExpressionOpened(event, data));
            default:
                return null;
        }
    }

    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public String[] getQueryEvents() {
        return new String[]{SpeechQueryEvent.SOUND_LOCATION, SpeechQueryEvent.IS_APP_FOREGROUND, SpeechQueryEvent.IS_ACCOUNT_LOGIN, SpeechQueryEvent.IS_WAKEUP_ENABLE, SpeechQueryEvent.GET_CURRENT_MODE, SpeechQueryEvent.GET_CAR_PLATFORM, SpeechQueryEvent.GET_SCENE_SWITCH_STATUS, SpeechQueryEvent.GET_FIRST_SPEECH_STATUS, SpeechQueryEvent.CURRENT_TTS_ENGINE, SpeechQueryEvent.APP_IS_INSTALLED, SpeechQueryEvent.IS_USEREXPRESSION_OPENED};
    }
}
