package com.xiaopeng.speech.protocol.node.charge;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.xiaopeng.speech.SpeechNode;
import org.json.JSONObject;
/* loaded from: classes.dex */
public class ChargeNode extends SpeechNode<ChargeListener> {
    public static final String CHARGE_CLTC_MILEAGE = "CLTC";
    public static final String CHARGE_NEDC_MILEAGE = "NEDC";
    public static final String CHARGE_WLTP_MILEAGE = "WLTP";

    protected void onPortOpenSupport(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((ChargeListener) obj).onPortOpenSupport();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onPortOpen(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((ChargeListener) obj).onPortOpen();
            }
        }
    }

    protected void onStartSupport(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((ChargeListener) obj).onStartSupport();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onStart(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((ChargeListener) obj).onStart();
            }
        }
    }

    protected void onRestartSupport(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((ChargeListener) obj).onRestartSupport();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onRestart(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((ChargeListener) obj).onRestart();
            }
        }
    }

    protected void onStopSupport(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((ChargeListener) obj).onStopSupport();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onStop(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((ChargeListener) obj).onStop();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onUiOpen(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((ChargeListener) obj).onUiOpen();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onUiClose(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((ChargeListener) obj).onUiClose();
            }
        }
    }

    protected void onModePercentSupport(String event, String data) {
        int target = 0;
        try {
            JSONObject jsonObject = new JSONObject(data);
            target = Integer.parseInt(jsonObject.optString(TypedValues.Attributes.S_TARGET));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((ChargeListener) obj).onModePercentSupport(target);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onModePercent(String event, String data) {
        int target = 0;
        try {
            JSONObject jsonObject = new JSONObject(data);
            target = Integer.parseInt(jsonObject.optString(TypedValues.Attributes.S_TARGET));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((ChargeListener) obj).onModePercent(target);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onModeFull(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((ChargeListener) obj).onModeFull();
            }
        }
    }

    protected void onModeSmartOnSupport(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((ChargeListener) obj).onModeSmartOnSupport();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onModeSmartOn(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((ChargeListener) obj).onModeSmartOn();
            }
        }
    }

    protected void onModeSmartCloseSupport(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((ChargeListener) obj).onModeSmartCloseSupport();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onModeSmartClose(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((ChargeListener) obj).onModeSmartClose();
            }
        }
    }

    protected void onModeSmartOffSupport(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((ChargeListener) obj).onModeSmartOffSupport();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onModeSmartOff(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((ChargeListener) obj).onModeSmartOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onChangeWltpMileageMode(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((ChargeListener) obj).onChangeMileageMode(CHARGE_WLTP_MILEAGE);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onChangeNedcMileageMode(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((ChargeListener) obj).onChangeMileageMode(CHARGE_NEDC_MILEAGE);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onChargeTrunkPowerOpen(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((ChargeListener) obj).onChargeTrunkPower(true);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onChargeTrunkPowerClose(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((ChargeListener) obj).onChargeTrunkPower(false);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setDischargeLimit(String event, String data) {
        int target = 0;
        try {
            JSONObject jsonObject = new JSONObject(data);
            target = Integer.parseInt(jsonObject.optString(TypedValues.Attributes.S_TARGET));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((ChargeListener) obj).setDischargeLimit(target);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onChangeCltcMileageMode(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((ChargeListener) obj).onChangeMileageMode(CHARGE_CLTC_MILEAGE);
            }
        }
    }
}
