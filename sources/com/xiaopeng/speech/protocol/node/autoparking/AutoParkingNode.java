package com.xiaopeng.speech.protocol.node.autoparking;

import android.os.Binder;
import android.os.IBinder;
import com.xiaopeng.speech.SpeechClient;
import com.xiaopeng.speech.SpeechNode;
import com.xiaopeng.speech.jarvisproto.DMEnd;
import com.xiaopeng.speech.jarvisproto.WakeupResult;
import com.xiaopeng.speech.protocol.node.autoparking.bean.ParkingPositionBean;
import com.xiaopeng.speech.protocol.node.dialog.AbsDialogListener;
import com.xiaopeng.speech.protocol.node.dialog.DialogNode;
import com.xiaopeng.speech.protocol.node.dialog.bean.DialogEndReason;
import com.xiaopeng.speech.proxy.HotwordEngineProxy;
import org.json.JSONException;
import org.json.JSONObject;
/* loaded from: classes.dex */
public class AutoParkingNode extends SpeechNode<AutoParkingListener> {
    private static final String COMMAND_SPLIT = "#";
    private static final String FIND_SKILL_NAME = "沿途找车位";
    private static final String FIND_TASK_PARK = "沿途泊车";
    private static final String INTENT_FIND_POSITION_PARK = "找到车位";
    private static final long SET_EXIT_PARKING_TIMEOUT = 150;
    private static final String SKILL_NAME = "离线自动泊车";
    private static final String TASK_PARK = "自动泊车";
    private IBinder mWakeupBinder = new Binder();
    private volatile boolean isShouldExitParking = false;

    public AutoParkingNode() {
        SpeechClient.instance().getNodeManager().subscribe(DialogNode.class, new AbsDialogListener() { // from class: com.xiaopeng.speech.protocol.node.autoparking.AutoParkingNode.1
            @Override // com.xiaopeng.speech.protocol.node.dialog.AbsDialogListener, com.xiaopeng.speech.protocol.node.dialog.DialogListener
            public void onDialogEnd(DialogEndReason endReason) {
                super.onDialogEnd(endReason);
                if (DMEnd.REASON_VOICE.equals(endReason.reason) || DMEnd.REASON_WHEEL.equals(endReason.reason)) {
                    AutoParkingNode.this.onExit("", "");
                }
            }
        });
    }

    public void findParkingPosition(String tts) {
        findParkingPosition(tts, false);
    }

    public void findParkingPosition(String tts, boolean isFindParking) {
        String skill;
        String task;
        String slots;
        String slots2 = "";
        if (isFindParking) {
            skill = FIND_SKILL_NAME;
            task = FIND_TASK_PARK;
            try {
                slots2 = new JSONObject().put("tts", tts).put("isLocalSkill", true).put(WakeupResult.REASON_COMMAND, "command://autoparking.park.start#native://autoparking.park.carport.count#command://autoparking.find.parking.space.continue#command://autoparking.find.parking.space.exit").toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            slots = slots2;
        } else {
            skill = SKILL_NAME;
            task = TASK_PARK;
            try {
                String slots3 = new JSONObject().put("tts", tts).put("isLocalSkill", true).put(WakeupResult.REASON_COMMAND, "command://autoparking.park.start#native://autoparking.park.carport.count#command://autoparking.exit").toString();
                slots = slots3;
            } catch (JSONException e2) {
                e2.printStackTrace();
                slots = "";
            }
        }
        stopCarSpeech();
        SpeechClient.instance().getAgent().triggerIntentWithBinder(this.mWakeupBinder, skill, task, INTENT_FIND_POSITION_PARK, slots);
    }

    private void removeSetExitParkingRunnable() {
    }

    public boolean isShouldExitParking() {
        return this.isShouldExitParking;
    }

    public void setShouldExitParking(boolean exitParking) {
        removeSetExitParkingRunnable();
        this.isShouldExitParking = exitParking;
    }

    public void setWheelWakeupEnabled(boolean state) {
        SpeechClient.instance().getWakeupEngine().setWheelWakeupEnabled(this.mWakeupBinder, state);
    }

    public void enableWakeup() {
        SpeechClient.instance().getWakeupEngine().enableMainWakeupWord(this.mWakeupBinder);
        SpeechClient.instance().getWakeupEngine().enableFastWakeEnhance(this.mWakeupBinder);
        SpeechClient.instance().getHotwordEngine().removeHotWords(HotwordEngineProxy.BY_AUTO_PARKING);
        SpeechClient.instance().getHotwordEngine().removeHotWords(HotwordEngineProxy.BY_PARKING_ALONG_THE_WAY);
    }

    public void disableWakeup() {
        SpeechClient.instance().getWakeupEngine().disableMainWakeupWord(this.mWakeupBinder);
        SpeechClient.instance().getWakeupEngine().disableFastWakeEnhance(this.mWakeupBinder);
    }

    public void stopCarSpeech() {
        removeSetExitParkingRunnable();
        this.isShouldExitParking = false;
        SpeechClient.instance().getWakeupEngine().stopDialogReason(DMEnd.REASON_AUTO_PARK);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onActivate(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((AutoParkingListener) obj).onActivate();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onExit(String event, String data) {
        removeSetExitParkingRunnable();
        this.isShouldExitParking = false;
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((AutoParkingListener) obj).onExit();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onParkStart(String event, String data) {
        removeSetExitParkingRunnable();
        this.isShouldExitParking = false;
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((AutoParkingListener) obj).onParkStart(data);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onParkCarportCount(String event, String data) {
        ParkingPositionBean positionBean = ParkingPositionBean.fromJson(data);
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((AutoParkingListener) obj).onParkCarportCount(positionBean);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onMemoryParkingStart(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((AutoParkingListener) obj).onMemoryParkingStart();
            }
        }
    }
}
