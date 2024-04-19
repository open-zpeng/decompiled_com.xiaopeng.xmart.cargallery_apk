package com.xiaopeng.speech.protocol.node.combo;

import android.text.TextUtils;
import com.xiaopeng.speech.SpeechClient;
import com.xiaopeng.speech.SpeechNode;
import com.xiaopeng.speech.protocol.event.ComboEvent;
import com.xiaopeng.speech.protocol.event.OOBEEvent;
import com.xiaopeng.speech.protocol.event.query.speech.SpeechCarStatusEvent;
import org.json.JSONException;
import org.json.JSONObject;
/* loaded from: classes.dex */
public class ComboNode extends SpeechNode<ComboListener> {
    private static final String CLOSE_MEDITATION_INTENT = "关闭冥想模式";
    private static final String CLOSE_WAIT_INTENT = "关闭等人模式";
    private static final String COMBO_SKILL = "离线系统组合命令词";
    private static final String COMBO_TASK = "组合命令词";
    private static final String OPEN_MEDITATION_INTENT = "冥想模式";
    private static final String OPEN_WAIT_INTENT = "等人模式";

    /* JADX INFO: Access modifiers changed from: protected */
    public void onModeBio(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((ComboListener) obj).onModeBio();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onModeVentilate(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((ComboListener) obj).onModeVentilate();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onModeInvisible(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((ComboListener) obj).onModeInvisible();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onModeInvisibleOff(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((ComboListener) obj).onModeInvisibleOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onModeFridge(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((ComboListener) obj).onModeFridge();
            }
        }
    }

    public void onDataModeInvisibleTts(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((ComboListener) obj).onDataModeInvisibleTts();
            }
        }
    }

    public void onDataModeMeditationTts(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((ComboListener) obj).onDataModeMeditationTts();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onFastCloseModeInvisible(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((ComboListener) obj).onFastCloseModeInvisible();
            }
        }
    }

    public void onDataModeBioTts(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((ComboListener) obj).onDataModeBioTts();
            }
        }
    }

    public void onDataModeVentilateTts(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((ComboListener) obj).onDataModeVentilateTts();
            }
        }
    }

    public void onDataModeFridgeTts(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((ComboListener) obj).onDataModeFridgeTts();
            }
        }
    }

    public void onModeBioOff(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((ComboListener) obj).onModeBioOff();
            }
        }
    }

    public void onModeVentilateOff(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((ComboListener) obj).onModeVentilateOff();
            }
        }
    }

    public void onModeFridgeOff(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((ComboListener) obj).onModeFridgeOff();
            }
        }
    }

    public void onDataModeWaitTts(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((ComboListener) obj).onDataModeWaitTts();
            }
        }
    }

    public void onModeWait(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((ComboListener) obj).onModeWait();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onModeWaitOff(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((ComboListener) obj).onModeWaitOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void enterUserMode(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((ComboListener) obj).enterUserMode(getModeFromJson(data));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void exitUserModel(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((ComboListener) obj).exitUserModel(getModeFromJson(data));
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

    public void openMeditationMode(String tts) {
        SpeechClient.instance().getWakeupEngine().stopDialog();
        String slots = "";
        try {
            slots = new JSONObject().put("tts", tts).put("isAsrModeOffline", true).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        SpeechClient.instance().getAgent().triggerIntent(COMBO_SKILL, COMBO_TASK, OPEN_MEDITATION_INTENT, slots);
    }

    public void closeMeditationMode(String tts) {
        closeMeditationMode(tts, false);
    }

    public void closeMeditationMode(String tts, boolean needTTS) {
        SpeechClient.instance().getWakeupEngine().stopDialog();
        String slots = "";
        try {
            slots = new JSONObject().put("tts", tts).put("isAsrModeOffline", true).put("needTTS", needTTS ? OOBEEvent.STRING_TRUE : OOBEEvent.STRING_FALSE).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        SpeechClient.instance().getAgent().triggerIntent(COMBO_SKILL, COMBO_TASK, CLOSE_MEDITATION_INTENT, slots);
    }

    public void openWaitMode(boolean needTTS) {
        String slots = "";
        try {
            slots = new JSONObject().put("isAsrModeOffline", true).put("needTTS", needTTS ? OOBEEvent.STRING_TRUE : OOBEEvent.STRING_FALSE).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        SpeechClient.instance().getAgent().triggerIntent(COMBO_SKILL, COMBO_TASK, OPEN_WAIT_INTENT, slots);
    }

    public void closeWaitMode(boolean needTTS) {
        SpeechClient.instance().getAgent().sendEvent(ComboEvent.MODE_WAIT_OFF, "");
    }

    public int getCurrentMode() {
        return SpeechClient.instance().getQueryInjector().queryData(SpeechCarStatusEvent.STATUS_CUR_MODE, "").getInteger().intValue();
    }
}
