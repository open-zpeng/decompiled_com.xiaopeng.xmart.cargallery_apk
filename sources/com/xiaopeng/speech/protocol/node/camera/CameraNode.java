package com.xiaopeng.speech.protocol.node.camera;

import com.xiaopeng.speech.SpeechNode;
import com.xiaopeng.speech.protocol.bean.ChangeValue;
import org.json.JSONException;
import org.json.JSONObject;
/* loaded from: classes.dex */
public class CameraNode extends SpeechNode<CameraListener> {
    /* JADX INFO: Access modifiers changed from: protected */
    public void onOverallOn(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        boolean isTTS = true;
        try {
            JSONObject jsonObject = new JSONObject(data);
            isTTS = jsonObject.optBoolean("isTTS", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CameraListener) obj).onOverallOn(isTTS);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onRearTake(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        boolean isTTS = true;
        try {
            JSONObject jsonObject = new JSONObject(data);
            isTTS = jsonObject.optBoolean("isTTS", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CameraListener) obj).onRearTake(isTTS);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onRearRecord(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        boolean isTTS = true;
        try {
            JSONObject jsonObject = new JSONObject(data);
            isTTS = jsonObject.optBoolean("isTTS", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CameraListener) obj).onRearRecord(isTTS);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onFrontTake(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CameraListener) obj).onFrontTake();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onFrontRecord(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CameraListener) obj).onFrontRecord();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLeftTake(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CameraListener) obj).onLeftTake();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLeftRecord(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CameraListener) obj).onLeftRecord();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onRightTake(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CameraListener) obj).onRightTake();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onRightRecord(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CameraListener) obj).onRightRecord();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLeftOn(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CameraListener) obj).onLeftOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onRightOn(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CameraListener) obj).onRightOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onRearOn(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CameraListener) obj).onRearOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onRearOnNew(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CameraListener) obj).onRearOnNew();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onFrontOn(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CameraListener) obj).onFrontOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onRearOff(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CameraListener) obj).onRearOff();
            }
        }
    }

    protected void onCarcorderTake(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CameraListener) obj).onCarcorderTake();
            }
        }
    }

    protected void onCarcorderLock(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CameraListener) obj).onCarcorderLock();
            }
        }
    }

    protected void onCarcorderRecord(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CameraListener) obj).onCarcorderRecord();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTopOff(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        boolean isTTS = true;
        try {
            JSONObject jsonObject = new JSONObject(data);
            isTTS = jsonObject.optBoolean("isTTS", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CameraListener) obj).onTopOff(isTTS);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTopOn(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        boolean isTTS = true;
        try {
            JSONObject jsonObject = new JSONObject(data);
            isTTS = jsonObject.optBoolean("isTTS", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CameraListener) obj).onTopOn(isTTS);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTopTake(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CameraListener) obj).onTopTake();
            }
        }
    }

    protected void onTopRecord(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CameraListener) obj).onTopRecord();
            }
        }
    }

    protected void onTopRecordEnd(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CameraListener) obj).onTopRecordEnd();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTopRotateLeft(String event, String data) {
        ChangeValue changeValue = ChangeValue.fromJson(data);
        Object[] listenerList = this.mListenerList.collectCallbacks();
        boolean isTTS = true;
        try {
            JSONObject jsonObject = new JSONObject(data);
            isTTS = jsonObject.optBoolean("isTTS", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CameraListener) obj).onTopRotateLeft(changeValue, isTTS);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTopRotateRight(String event, String data) {
        ChangeValue changeValue = ChangeValue.fromJson(data);
        Object[] listenerList = this.mListenerList.collectCallbacks();
        boolean isTTS = true;
        try {
            JSONObject jsonObject = new JSONObject(data);
            isTTS = jsonObject.optBoolean("isTTS", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CameraListener) obj).onTopRotateRight(changeValue, isTTS);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTopRotateFront(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        boolean isTTS = true;
        try {
            JSONObject jsonObject = new JSONObject(data);
            isTTS = jsonObject.optBoolean("isTTS", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CameraListener) obj).onTopRotateFront(isTTS);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTopRotateRear(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        boolean isTTS = true;
        try {
            JSONObject jsonObject = new JSONObject(data);
            isTTS = jsonObject.optBoolean("isTTS", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CameraListener) obj).onTopRotateRear(isTTS);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTransparentChassisCMD(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CameraListener) obj).onTransparentChassisCMD(data);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onCameraThreeDOn(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CameraListener) obj).onCameraThreeDOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onCameraThreeDOff(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CameraListener) obj).onCameraThreeDOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onCameraTransparentChassisOn(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CameraListener) obj).onCameraTransparentChassisOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onCameraTransparentChassisOff(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CameraListener) obj).onCameraTransparentChassisOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onCameraPhotoalbumOpen(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((CameraListener) obj).onCameraPhotoalbumOpen();
            }
        }
    }
}
