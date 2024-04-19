package com.xiaopeng.speech.protocol.node.controlcard;

import com.xiaopeng.speech.SpeechNode;
import com.xiaopeng.speech.protocol.bean.CardValue;
/* loaded from: classes.dex */
public class ControlCardNode extends SpeechNode<ControlCardListener> {
    /* JADX INFO: Access modifiers changed from: protected */
    public void showAcTempCard(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            CardValue cardValue = CardValue.fromJsonForAcTemp(data);
            for (Object obj : listenerList) {
                ((ControlCardListener) obj).showAcTempCard(cardValue);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void showAcDriverTempCard(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            CardValue cardValue = CardValue.fromJsonForAcTemp(data);
            for (Object obj : listenerList) {
                ((ControlCardListener) obj).showAcDriverTempCard(cardValue);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void showAcPassengerTempCard(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            CardValue cardValue = CardValue.fromJsonForAcTemp(data);
            for (Object obj : listenerList) {
                ((ControlCardListener) obj).showAcPassengerTempCard(cardValue);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void showAcWindCard(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            CardValue cardValue = CardValue.fromJsonForAcWindLv(data);
            for (Object obj : listenerList) {
                ((ControlCardListener) obj).showAcWindCard(cardValue);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void showAtmosphereBrightnessCard(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            CardValue cardValue = CardValue.fromJsonForAtmosphereBrightness(data);
            for (Object obj : listenerList) {
                ((ControlCardListener) obj).showAtmosphereBrightnessCard(cardValue);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void showAtmosphereBrightnessColorCard(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            CardValue cardValue = CardValue.fromJsonForAtmosphereColor(data);
            for (Object obj : listenerList) {
                ((ControlCardListener) obj).showAtmosphereBrightnessColorCard(cardValue);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void showAcSeatHeatingDriverCard(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            CardValue cardValue = CardValue.fromJsonForAcSeatHeating(data);
            for (Object obj : listenerList) {
                ((ControlCardListener) obj).showAcSeatHeatingDriverCard(cardValue);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void showAcSeatHeatingPassengerCard(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            CardValue cardValue = CardValue.fromJsonForAcSeatHeating(data);
            for (Object obj : listenerList) {
                ((ControlCardListener) obj).showAcSeatHeatingPassengerCard(cardValue);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void showAcSeatVentilateDriverCard(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            CardValue cardValue = CardValue.fromJsonForAcSeatVentilate(data);
            for (Object obj : listenerList) {
                ((ControlCardListener) obj).showAcSeatVentilateDriverCard(cardValue);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void showSystemScreenBrightnessCard(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            CardValue cardValue = CardValue.fromJsonForScreenBrightness(data);
            for (Object obj : listenerList) {
                ((ControlCardListener) obj).showSystemScreenBrightnessCard(cardValue);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void showSystemIcmBrightnessCard(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            CardValue cardValue = CardValue.fromJsonForIcmBrightness(data);
            for (Object obj : listenerList) {
                ((ControlCardListener) obj).showSystemIcmBrightnessCard(cardValue);
            }
        }
    }
}
