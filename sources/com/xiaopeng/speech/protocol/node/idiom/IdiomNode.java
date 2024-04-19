package com.xiaopeng.speech.protocol.node.idiom;

import android.os.Binder;
import com.xiaopeng.speech.SpeechClient;
import com.xiaopeng.speech.SpeechNode;
/* loaded from: classes.dex */
public class IdiomNode extends SpeechNode<IIdiomListener> {
    private Binder mBinder = new Binder();

    public void setVadTimeout(long milliseconds) {
        SpeechClient.instance().getASREngine().setVadTimeout(milliseconds);
    }

    public void setVadPauseTime(long milliseconds) {
        SpeechClient.instance().getASREngine().setVadPauseTime(milliseconds);
    }

    public void resetVadTime() {
        SpeechClient.instance().getASREngine().setVadPauseTime(500L);
        SpeechClient.instance().getASREngine().setVadTimeout(7000L);
    }

    public void setSingleTTS(boolean state) {
        SpeechClient.instance().getTTSEngine().setSingleTTS(state, this.mBinder);
    }

    public String speakSingleTTS(String text) {
        return SpeechClient.instance().getTTSEngine().speak(text, 3);
    }

    public void shutupTTS(String ttsId) {
        SpeechClient.instance().getTTSEngine().shutup(ttsId);
    }

    public void startListening() {
        SpeechClient.instance().getASREngine().startListen();
    }

    public void cancelListening() {
        SpeechClient.instance().getASREngine().cancelListen();
    }

    public void disableWakeup() {
        SpeechClient.instance().getWakeupEngine().disableWakeupEnhance(this.mBinder);
        SpeechClient.instance().getWakeupEngine().setWheelWakeupEnabled(this.mBinder, false);
    }

    public void enableWakeup() {
        SpeechClient.instance().getWakeupEngine().enableWakeupEnhance(this.mBinder);
        SpeechClient.instance().getWakeupEngine().setWheelWakeupEnabled(this.mBinder, true);
    }
}
