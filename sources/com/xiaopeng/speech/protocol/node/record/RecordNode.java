package com.xiaopeng.speech.protocol.node.record;

import com.xiaopeng.speech.SpeechNode;
import com.xiaopeng.speech.protocol.node.record.bean.AsrResult;
import com.xiaopeng.speech.protocol.node.record.bean.RecordErrReason;
import com.xiaopeng.speech.protocol.node.record.bean.Volume;
import org.json.JSONException;
import org.json.JSONObject;
/* loaded from: classes.dex */
public class RecordNode extends SpeechNode<RecordListener> {
    public void onAsrResult(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        AsrResult asrResult = AsrResult.fromJson(data);
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((RecordListener) obj).onAsrResult(asrResult);
            }
        }
    }

    public void onRecordBegin(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((RecordListener) obj).onRecordBegin();
            }
        }
    }

    public void onRecordEnd(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        boolean isStopRecord = false;
        try {
            JSONObject object = new JSONObject(data);
            isStopRecord = object.optBoolean("isStopRecord");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((RecordListener) obj).onRecordEnd(isStopRecord);
            }
        }
    }

    public void onRecordError(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        RecordErrReason recordError = RecordErrReason.fromJson(data);
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((RecordListener) obj).onRecordError(recordError);
            }
        }
    }

    public void onSpeechBegin(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((RecordListener) obj).onSpeechBegin();
            }
        }
    }

    public void onSpeechEnd(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((RecordListener) obj).onSpeechEnd();
            }
        }
    }

    public void onSpeechVolume(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        Volume volume = Volume.fromJson(data);
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((RecordListener) obj).onSpeechVolume(volume);
            }
        }
    }

    public void onRecordMaxLength(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((RecordListener) obj).onRecordMaxLength();
            }
        }
    }
}
