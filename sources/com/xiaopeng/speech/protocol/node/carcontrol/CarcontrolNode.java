package com.xiaopeng.speech.protocol.node.carcontrol;

import android.text.TextUtils;
import com.xiaopeng.speech.SpeechNode;
import com.xiaopeng.speech.protocol.bean.ChangeValue;
import com.xiaopeng.speech.protocol.bean.SpeechParam;
import com.xiaopeng.speech.protocol.bean.base.CommandValue;
import com.xiaopeng.speech.protocol.node.carcontrol.bean.ControlReason;
import com.xiaopeng.speech.protocol.node.carcontrol.bean.SeatValue;
import com.xiaopeng.speech.protocol.node.carcontrol.bean.UserBookValue;
import org.json.JSONException;
import org.json.JSONObject;
/* loaded from: classes.dex */
public class CarcontrolNode extends SpeechNode<CarcontrolListener> {
    private static final String KEY_PERCENT = "percent";

    private int getPercent(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            if (jsonObject.has(KEY_PERCENT)) {
                int percent = jsonObject.optInt(KEY_PERCENT);
                return percent;
            }
            return 100;
        } catch (Exception e) {
            e.printStackTrace();
            return 100;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLightHomeOff(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onLightHomeOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLightHomeOn(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onLightHomeOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLightLowOff(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        ControlReason controlReason = ControlReason.fromJson(data);
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onLightLowOff(controlReason);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLightLowOn(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onLightLowOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLightPositionOn(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onLightPositionOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLightPositionOff(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onLightPositionOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLightAutoOn(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onLightAutoOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLightAutoOff(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onLightAutoOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onMistLightOff(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onMistLightOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onMistLightOn(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onMistLightOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onMirrorRearClose(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onMirrorRearClose();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onMirrorRearOn(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onMirrorRearOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTrunkOpen(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onTrunkOpen();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindowDriverClose(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onWindowsControl(0, 1, getPercent(data));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindowDriverOpen(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onWindowsControl(0, 0, getPercent(data));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindowPassengerClose(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onWindowsControl(1, 1, getPercent(data));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindowPassengerOpen(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onWindowsControl(1, 0, getPercent(data));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindowsClose(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onWindowsControl(6, 1, getPercent(data));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindowsOpen(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onWindowsControl(6, 0, getPercent(data));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTrunkClose(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onTrunkClose();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindowsVentilateOn(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onWindowsVentilateOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindowsVentilateOff(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onWindowsVentilateOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onModesDrivingSport(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onModesDrivingSport();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onModesDrivingConservation(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onModesDrivingConservation();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onModesDrivingNormal(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onModesDrivingNormal();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onModesSteeringSoft(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onModesSteeringSoft();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onModesSteeringNormal(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onModesSteeringNormal();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onModesSteeringSport(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onModesSteeringSport();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onEnergyRecycleHigh(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onEnergyRecycleHigh();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onEnergyRecycleLow(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onEnergyRecycleLow();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onEnergyRecycleMedia(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onEnergyRecycleMedia();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onEnergyRecycleUp(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onEnergyRecycleUp();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onEnergyRecycleDown(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onEnergyRecycleDown();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindowRightRearOpen(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onWindowsControl(3, 0, getPercent(data));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindowRightRearClose(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onWindowsControl(3, 1, getPercent(data));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindowLeftRearOpen(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onWindowsControl(2, 0, getPercent(data));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindowLeftRearClose(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onWindowsControl(2, 1, getPercent(data));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindowFrontOpen(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onWindowsControl(7, 0, getPercent(data));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindowFrontClose(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onWindowsControl(7, 1, getPercent(data));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindowRearOpen(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onWindowsControl(8, 0, getPercent(data));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindowRearClose(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onWindowsControl(8, 1, getPercent(data));
            }
        }
    }

    protected void onModesSportSupport(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onModesSportSupport();
            }
        }
    }

    protected void onModesConservationSupport(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onModesConservationSupport();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatMoveUp(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onSeatMoveUp();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatMoveDown(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onSeatMoveDown();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatMoveHighest(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onSeatMoveHighest();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatMoveLowest(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onSeatMoveLowest();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatMoveBack(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        ChangeValue changeValue = ChangeValue.fromJson(data);
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onSeatMoveBack(changeValue);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatMoveForward(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        ChangeValue changeValue = ChangeValue.fromJson(data);
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onSeatMoveForward(changeValue);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatMoveRear(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        SpeechParam speechParam = SpeechParam.fromJson(data);
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onSeatMoveRear(speechParam);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatMoveForemost(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onSeatMoveForemost();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatBackrestBack(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        SpeechParam speechParam = SpeechParam.fromJson(data);
        ChangeValue changeValue = ChangeValue.fromJson(data);
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onSeatBackrestBack(changeValue, speechParam);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatBackrestForward(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        ChangeValue changeValue = ChangeValue.fromJson(data);
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onSeatBackrestForward(changeValue);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatBackrestRear(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        SpeechParam speechParam = SpeechParam.fromJson(data);
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onSeatBackrestRear(speechParam);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatBackrestForemost(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onSeatBackrestForemost();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatAdjust(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        SeatValue value = SeatValue.fromJson(data);
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onSeatAdjust(value);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onControlLightResume(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onControlLightResume();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onControlSeatResume(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onControlSeatResume();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLowVolumeOn(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onLowVolumeOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLowVolumeOff(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onLowVolumeOff();
            }
        }
    }

    protected void onCheckUserBook(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        UserBookValue userBookValue = UserBookValue.fromJson(data);
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onCheckUserBook(userBookValue);
            }
        }
    }

    protected void onOpenUserBook(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onOpenUserBook();
            }
        }
    }

    protected void onCloseUserBook(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onCloseUserBook();
            }
        }
    }

    protected void onLightTopAutoOn(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onLightTopAutoOn();
            }
        }
    }

    protected void onLightTopAutoOff(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onLightTopAutoOff();
            }
        }
    }

    protected void onLightTopOn(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onLightTopOn();
            }
        }
    }

    protected void onLightTopOff(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onLightTopOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLightAtmosphereOn(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onLightAtmosphereOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLightAtmosphereOff(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onLightAtmosphereOff(0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onMirrorRearSet(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onMirrorRearSet(0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWiperSpeedUp(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onWiperSpeedUp();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWiperSpeedDown(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onWiperSpeedDown();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWiperSlow(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onWiperSlow();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWiperHigh(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onWiperHigh();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWiperMedium(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onWiperMedium();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWiperSuperhigh(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onWiperSuperhigh();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTrunkUnlock(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onTrunkUnlock();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindowsLeftClose(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onWindowsControl(4, 1, getPercent(data));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindowsLeftOpen(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onWindowsControl(4, 0, getPercent(data));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindowsRightClose(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onWindowsControl(5, 1, getPercent(data));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWindowsRightOpen(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onWindowsControl(5, 0, getPercent(data));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLegUp(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onLegUp(0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLegDown(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onLegDown(0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLegHighest(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onLegHighest(0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLegLowest(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onLegLowest(0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatRestore(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onSeatRestore();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onRightChargePortOpen(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onChargePortControl(1, 0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onRightChargePortClose(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onChargePortControl(1, 1);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLeftChargePortClose(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onChargePortControl(0, 1);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLeftChargePortOpen(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onChargePortControl(0, 0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTirePressureShow(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onTirePressureShow();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLightAtmosphereColor(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onLightAtmosphereColor(new CommandValue(data));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLightAtmosphereBrightnessSet(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onLightAtmosphereBrightnessSet(new CommandValue(data));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLightAtmosphereBrightnessUp() {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onLightAtmosphereBrightnessUp();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLightAtmosphereBrightnessDown() {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onLightAtmosphereBrightnessDown();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLightAtmosphereBrightnessMax() {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onLightAtmosphereBrightnessMax();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLightAtmosphereBrightnessMin() {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onLightAtmosphereBrightnessMin();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onPsnSeatMoveUp() {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onPsnSeatMoveUp();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onPsnSeatMoveDown() {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onPsnSeatMoveDown();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onPsnSeatBackrestBack() {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onPsnSeatBackrestBack();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onPsnSeatBackrestForward() {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onPsnSeatBackrestForward();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onPsnSeatMoveBack() {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onPsnSeatMoveBack();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onPsnSeatMoveForward() {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onPsnSeatMoveForward();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onControlXpedalOn() {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onControlXpedalOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onControlXpedalOff() {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onControlXpedalOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onControlScissorLeftDoorOn() {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onControlScissorLeftDoorOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onControlScissorRightDoorOn() {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onControlScissorRightDoorOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onControlScissorLeftDoorOff() {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onControlScissorLeftDoorOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onControlScissorRightDoorOff() {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onControlScissorRightDoorOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onControlScissorLeftDoorPause() {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onControlScissorLeftDoorPause();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onControlScissorRightDoorPause() {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onControlScissorRightDoorPause();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onControlElectricCurtainOpen(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onControlSunShade(0, getPercent(data));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onControlElectricCurtainClose(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onControlSunShade(1, getPercent(data));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onControlComfortableDrivingModeOpen(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onControlComfortableDrivingModeOpen();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onControlComfortableDrivingModeClose(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).onControlComfortableDrivingModeClose();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onControlLightLanguageOn(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).setLluSw(true);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onControlLightLanguageOff(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).setLluSw(false);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setCapsuleUniversal(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CarcontrolListener) obj).setCapsuleUniversal(getModeFromJson(data));
            }
        }
    }

    private String getModeFromJson(String data) {
        if (TextUtils.isEmpty(data)) {
            return "";
        }
        try {
            JSONObject modeJson = new JSONObject(data);
            return modeJson.has("mode") ? modeJson.optString("mode") : "";
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }
}
