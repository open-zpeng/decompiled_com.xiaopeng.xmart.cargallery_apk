package com.xiaopeng.speech.protocol.node.carac;

import com.xiaopeng.speech.SpeechNode;
import com.xiaopeng.speech.common.util.LogUtils;
import com.xiaopeng.speech.protocol.bean.SpeechParam;
import com.xiaopeng.speech.protocol.node.carac.bean.ChangeValue;
import org.json.JSONObject;
/* loaded from: classes.dex */
public class CaracNode extends SpeechNode<CaracListener> {
    /* JADX INFO: Access modifiers changed from: protected */
    public void onHvacOff(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onHvacOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onHvacOn(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onHvacOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onAcOn(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onAcOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onAcOff(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onAcOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onAutoOn(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onAutoOn();
            }
        }
    }

    protected void onAutoOffSupport(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onAutoOffSupport();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onAutoOff(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onAutoOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onBlowFootOn(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onBlowFootOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onHeadFootOn(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onHeadFootOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onBlowHeadOn(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onBlowHeadOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onDemistFrontOff(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onDemistFrontOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onDemistFrontOn(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onDemistFrontOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onDemistRearOff(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onDemistRearOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onDemistRearOn(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onDemistRearOn();
            }
        }
    }

    protected void onDemistFootOnSupport(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onDemistFootOnSupport();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onDemistFootOn(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onDemistFootOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onInnerOff(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onInnerOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onInnerOn(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onInnerOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindDown(String event, String data) {
        ChangeValue changeValue = ChangeValue.fromJson(data);
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onWindDown(changeValue);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindUp(String event, String data) {
        ChangeValue changeValue = ChangeValue.fromJson(data);
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onWindUp(changeValue);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindSet(String event, String data) {
        ChangeValue changeValue = ChangeValue.fromJson(data);
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onWindSet(changeValue);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onRearHeatOff(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onRearHeatOff();
            }
        }
    }

    protected void onRearHeatOffSupport(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onRearHeatOffSupport();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onRearHeatOn(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onRearHeatOn();
            }
        }
    }

    protected void onRearHeatOnSupport(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onRearHeatOnSupport();
            }
        }
    }

    protected void onTempDownHalfSupport(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onTempDownHalfSupport();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTempDown(String event, String data) {
        ChangeValue changeValue = ChangeValue.fromJson(data);
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onTempDown(changeValue);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTempUp(String event, String data) {
        ChangeValue changeValue = ChangeValue.fromJson(data);
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onTempUp(changeValue);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTempSet(String event, String data) {
        ChangeValue changeValue = ChangeValue.fromJson(data);
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onTempSet(changeValue);
            }
        }
    }

    protected void onTempUpHalfSupport(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onTempUpHalfSupport();
            }
        }
    }

    protected void onPurifierOpenSupport(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onPurifierOpenSupport();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onPurifierOpen(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onPurifierOpen();
            }
        }
    }

    protected void onPurifierCloseSupport(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onPurifierCloseSupport();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onPurifierClose(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onPurifierClose();
            }
        }
    }

    protected void onPurifierPm25(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onPurifierPm25();
            }
        }
    }

    protected void onTempDriverUpSupport(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onTempDriverUpSupport();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTempDriverUp(String event, String data) {
        ChangeValue changeValue = ChangeValue.fromJson(data);
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onTempDriverUp(changeValue);
            }
        }
    }

    protected void onTempDriverDownSupport(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onTempDriverDownSupport();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTempDriverDown(String event, String data) {
        ChangeValue changeValue = ChangeValue.fromJson(data);
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onTempDriverDown(changeValue);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTempDriverSet(String event, String data) {
        ChangeValue changeValue = ChangeValue.fromJson(data);
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onTempDriverSet(changeValue);
            }
        }
    }

    protected void onTempPassengerUpSupport(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onTempPassengerUpSupport();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTempPassengerUp(String event, String data) {
        ChangeValue changeValue = ChangeValue.fromJson(data);
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onTempPassengerUp(changeValue);
            }
        }
    }

    protected void onTempPassengerDownSupport(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onTempPassengerDownSupport();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTempPassengerDown(String event, String data) {
        ChangeValue changeValue = ChangeValue.fromJson(data);
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onTempPassengerDown(changeValue);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTempPassengerSet(String event, String data) {
        ChangeValue changeValue = ChangeValue.fromJson(data);
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onTempPassengerSet(changeValue);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTempDualSyn(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onTempDualSyn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTempDualOff(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onTempDualOff();
            }
        }
    }

    protected void onDataTempTTS(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onDataTempTTS();
            }
        }
    }

    protected void onDataWindTTS(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onDataWindTTS();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindMax(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        SpeechParam param = SpeechParam.fromJson(data);
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onWindMax(param);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindMin(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        SpeechParam param = SpeechParam.fromJson(data);
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onWindMin(param);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTempMin(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        SpeechParam param = SpeechParam.fromJson(data);
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onTempMin(param);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTempMax(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onTempMax();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTempDriveMin(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onTempDriveMin();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTempDriveMax(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onTempDriveMax();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTempPassengerMin(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onTempPassengerMin();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTempPassengerMax(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onTempPassengerMax();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatHeatingOpen(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onSeatHeatingOpen();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatHeatingClose(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onSeatHeatingClose();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatPsnHeatingOpen(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onSeatPsnHeatingOpen();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatPsnHeatingClose(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onSeatPsnHeatingClose();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatHeatingMax(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onSeatHeatingMax();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatHeatingMin(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onSeatHeatingMin();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatHeatingUp(String event, String data) {
        ChangeValue changeValue = ChangeValue.fromJson(data);
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onSeatHeatingUp(changeValue);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatHeatingDown(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onSeatHeatingDown();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatVentilateOn(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        SpeechParam param = SpeechParam.fromJson(data);
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onSeatVentilateOn(param);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatVentilateOff(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onSeatVentilateOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatVentilateMax(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onSeatVentilateMax();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatVentilateMin(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onSeatVentilateMin();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatVentilateDown(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onSeatVentilateDown();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatVentilateUp(String event, String data) {
        ChangeValue changeValue = ChangeValue.fromJson(data);
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onSeatVentilateUp(changeValue);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatVentilateDriverSet(String event, String data) {
        ChangeValue changeValue = ChangeValue.fromJson(data);
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onSeatVentilateDriverSet(changeValue);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatHeatDriverSet(String event, String data) {
        ChangeValue changeValue = ChangeValue.fromJson(data);
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onSeatHeatDriverSet(changeValue);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatHeatPassengerSet(String event, String data) {
        ChangeValue changeValue = ChangeValue.fromJson(data);
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onSeatHeatPassengerSet(changeValue);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatHeatPassengerUp(String event, String data) {
        ChangeValue changeValue = ChangeValue.fromJson(data);
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onSeatHeatPassengerUp(changeValue);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatHeatPassengerDown(String event, String data) {
        ChangeValue changeValue = ChangeValue.fromJson(data);
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onSeatHeatPassengerDown(changeValue);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onOpenFastFridge(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onOpenFastFridge();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onOpenFreshAir(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onOpenFreshAir();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onExitFastFridge(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onExitFastFridge();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onExitFreshAir(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onExitFreshAir();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindUnidirectionSet(String event, String data) {
        int pilotValue = 0;
        int directionValue = 7;
        try {
            JSONObject jsonObject = new JSONObject(data);
            String pilot = jsonObject.optString("pilot");
            String direction = jsonObject.optString("direction");
            pilotValue = Integer.parseInt(pilot);
            directionValue = Integer.parseInt(direction);
        } catch (Throwable th) {
            LogUtils.e("CaracNode", "onWindUnidirectionSet string data error, data = " + data);
        }
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onWindUnidirectionSet(pilotValue, directionValue);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindUnindirection(String event, String data) {
        int pilotValue = 0;
        int directionValue = 7;
        try {
            JSONObject jsonObject = new JSONObject(data);
            String pilot = jsonObject.optString("pilot");
            String direction = jsonObject.optString("direction");
            pilotValue = Integer.parseInt(pilot);
            directionValue = Integer.parseInt(direction);
        } catch (Throwable th) {
            LogUtils.e("CaracNode", "onWindUnindirection string data error, data = " + data);
        }
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onWindUnidirection(pilotValue, directionValue);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindEvad(String event, String data) {
        int pilotValue = 0;
        int directionValue = 7;
        try {
            JSONObject jsonObject = new JSONObject(data);
            String pilot = jsonObject.optString("pilot");
            String direction = jsonObject.optString("direction");
            pilotValue = Integer.parseInt(pilot);
            directionValue = Integer.parseInt(direction);
        } catch (Throwable th) {
            LogUtils.e("CaracNode", "onWindEvad string data error, data = " + data);
        }
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onWindEvad(pilotValue, directionValue);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindFront(String event, String data) {
        int pilotValue = 0;
        int directionValue = 7;
        try {
            JSONObject jsonObject = new JSONObject(data);
            String pilot = jsonObject.optString("pilot");
            String direction = jsonObject.optString("direction");
            pilotValue = Integer.parseInt(pilot);
            directionValue = Integer.parseInt(direction);
        } catch (Throwable th) {
            LogUtils.e("CaracNode", "onWindFront string data error, data = " + data);
        }
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onWindFront(pilotValue, directionValue);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindFree(String event, String data) {
        int pilotValue = 0;
        int directionValue = 7;
        try {
            JSONObject jsonObject = new JSONObject(data);
            String pilot = jsonObject.optString("pilot");
            String direction = jsonObject.optString("direction");
            pilotValue = Integer.parseInt(pilot);
            directionValue = Integer.parseInt(direction);
        } catch (Throwable th) {
            LogUtils.e("CaracNode", "onWindFree string data error, data = " + data);
        }
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onWindFree(pilotValue, directionValue);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindAutoSweepOn(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onWindAutoSweepOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindAutoSweepOff(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onWindAutoSweepOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onAqsOn(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onAqsOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onAqsOff(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onAqsOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onModesEcoOn(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onModesEcoOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onModesEcoOff(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onModesEcoOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onHeatingOn(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onHeatingOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onHeatingOff(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onHeatingOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onNatureOn(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onNatureOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onNatureOff(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onNatureOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindowOn(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onWindowOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onPsnTempSynOn(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onPsnTempSynOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onPsnTempSynOff(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onPsnTempSynOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onHvacPanelOn(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onAcPanelOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void openIntelligentPsn(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).openIntelligentPsn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void closeIntelligentPsn(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).closeIntelligentPsn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onMirrorOn(String event, String data) {
        int pilotValue = 0;
        try {
            JSONObject jsonObject = new JSONObject(data);
            String pilot = jsonObject.optString("pilot");
            pilotValue = Integer.parseInt(pilot);
        } catch (Throwable th) {
            LogUtils.e("CaracNode", "onMirrorOn string data error, data = " + data);
        }
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onMirrorOn(pilotValue);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onMirrorOff(String event, String data) {
        int pilotValue = 0;
        try {
            JSONObject jsonObject = new JSONObject(data);
            String pilot = jsonObject.optString("pilot");
            pilotValue = Integer.parseInt(pilot);
        } catch (Throwable th) {
            LogUtils.e("CaracNode", "onMirrorOff string data error, data = " + data);
        }
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onMirrorOff(pilotValue);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindOutletOn(String event, String data) {
        int pilotValue = 0;
        try {
            JSONObject jsonObject = new JSONObject(data);
            String pilot = jsonObject.optString("pilot");
            pilotValue = Integer.parseInt(pilot);
        } catch (Throwable th) {
            LogUtils.e("CaracNode", "nWindOutletOn string data error, data = " + data);
        }
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onWindOutletOn(pilotValue);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindOutletOff(String event, String data) {
        int pilotValue = 0;
        try {
            JSONObject jsonObject = new JSONObject(data);
            String pilot = jsonObject.optString("pilot");
            pilotValue = Integer.parseInt(pilot);
        } catch (Throwable th) {
            LogUtils.e("CaracNode", "onWindOutletOff string data error, data = " + data);
        }
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onWindOutletOff(pilotValue);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLeftRearSeatHeatingOpen(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onRearSeatHeatingOpenClose(0, 0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onRightRearSeatHeatingOpen(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onRearSeatHeatingOpenClose(0, 1);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLeftRearSeatHeatingClose(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onRearSeatHeatingOpenClose(1, 0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onRightRearSeatHeatingClose(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onRearSeatHeatingOpenClose(1, 1);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLeftRearSeatHeatSet(String event, String data) {
        ChangeValue changeValue = ChangeValue.fromJson(data);
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onRearSeatHeatSet(0, changeValue);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onRightRearSeatHeatSet(String event, String data) {
        ChangeValue changeValue = ChangeValue.fromJson(data);
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onRearSeatHeatSet(1, changeValue);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLeftRearSeatHeatUp(String event, String data) {
        ChangeValue changeValue = ChangeValue.fromJson(data);
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onRearSeatHeatUp(0, changeValue);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onRightRearSeatHeatUp(String event, String data) {
        ChangeValue changeValue = ChangeValue.fromJson(data);
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onRearSeatHeatUp(1, changeValue);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLeftRearSeatHeatDown(String event, String data) {
        ChangeValue changeValue = ChangeValue.fromJson(data);
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onRearSeatHeatDown(0, changeValue);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onRightRearSeatHeatDown(String event, String data) {
        ChangeValue changeValue = ChangeValue.fromJson(data);
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onRearSeatHeatDown(1, changeValue);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onHeadWindowOn(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onHeadWindowOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onHeadWindowFootOn(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onHeadWindowFootOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onHeadWindowOff(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CaracListener) obj).onHeadWindowOff();
            }
        }
    }
}
