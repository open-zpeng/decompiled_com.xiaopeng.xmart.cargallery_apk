package com.xiaopeng.speech.protocol.query.camera;

import com.xiaopeng.speech.SpeechQuery;
/* loaded from: classes.dex */
public class CameraQuery extends SpeechQuery<ICameraCaller> {
    /* JADX INFO: Access modifiers changed from: protected */
    public int getSupportTopStatus(String event, String data) {
        return ((ICameraCaller) this.mQueryCaller).getSupportTopStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getSupportPanoramicStatus(String event, String data) {
        return ((ICameraCaller) this.mQueryCaller).getSupportPanoramicStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getSupportRearStatus(String event, String data) {
        return ((ICameraCaller) this.mQueryCaller).getSupportRearStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getBusinessCameraStatus(String event, String data) {
        return ((ICameraCaller) this.mQueryCaller).getBusinessCameraStatus(data);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getCameraThreeDSupport(String event, String data) {
        return ((ICameraCaller) this.mQueryCaller).getCameraThreeDSupport();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getCameraTransparentChassisSupport(String event, String data) {
        return ((ICameraCaller) this.mQueryCaller).getCameraTransparentChassisSupport();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isCameraRecording(String event, String data) {
        return ((ICameraCaller) this.mQueryCaller).isCameraRecording();
    }
}
