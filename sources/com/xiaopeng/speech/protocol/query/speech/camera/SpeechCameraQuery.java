package com.xiaopeng.speech.protocol.query.speech.camera;

import com.xiaopeng.speech.SpeechQuery;
/* loaded from: classes.dex */
public class SpeechCameraQuery extends SpeechQuery<ISpeechCameraQueryCaller> {
    /* JADX INFO: Access modifiers changed from: protected */
    public int getCameraAngle(String event, String data) {
        return ((ISpeechCameraQueryCaller) this.mQueryCaller).getCameraAngle();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getCameraHeight(String event, String data) {
        return ((ISpeechCameraQueryCaller) this.mQueryCaller).getCameraHeight();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getCameraDisplayMode(String event, String data) {
        return ((ISpeechCameraQueryCaller) this.mQueryCaller).getCameraDisplayMode();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getHasPanoCamera(String event, String data) {
        return ((ISpeechCameraQueryCaller) this.mQueryCaller).getHasPanoCamera();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getHasRoofCamera(String event, String data) {
        return ((ISpeechCameraQueryCaller) this.mQueryCaller).getHasRoofCamera();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getRoofCameraState(String event, String data) {
        return ((ISpeechCameraQueryCaller) this.mQueryCaller).getRoofCameraState();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getRoofCameraPosition(String event, String data) {
        return ((ISpeechCameraQueryCaller) this.mQueryCaller).getRoofCameraPosition();
    }
}
