package com.xiaopeng.speech.protocol.query.speech.hardware.bean;

import com.xiaopeng.xmart.cargallery.bean.BIConfig;
import org.json.JSONException;
import org.json.JSONObject;
/* loaded from: classes.dex */
public class StreamType {
    public static final String MEDIA = "media";
    public static final String NAVI = "navi";
    public static final String PHONE = "phone";
    public static final String SPEECH = "speech";
    public static final String SYSTEM = "system";
    public String type;

    public static StreamType fromJson(String data) {
        StreamType streamType = new StreamType();
        try {
            JSONObject jsonObject = new JSONObject(data);
            streamType.type = jsonObject.optString(BIConfig.PROPERTY.DATA_TYPE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return streamType;
    }
}
