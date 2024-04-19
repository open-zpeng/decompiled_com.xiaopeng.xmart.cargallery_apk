package com.xiaopeng.speech.protocol.query.aiassistant;

import com.xiaopeng.speech.SpeechQuery;
import org.json.JSONException;
import org.json.JSONObject;
/* loaded from: classes.dex */
public class AiAssistantQuery extends SpeechQuery<IAiAssistantCaller> {
    /* JADX INFO: Access modifiers changed from: protected */
    public int getAiVideoOpenStatus(String event, String data) {
        if (data == null) {
            return 0;
        }
        try {
            JSONObject jsonData = new JSONObject(data);
            String videoName = jsonData.optString("video_name");
            String videoTag = jsonData.optString("video_tag");
            String videoCategory = jsonData.optString("video_category");
            return ((IAiAssistantCaller) this.mQueryCaller).getAiVideoOpenStatus(videoName, videoTag, videoCategory);
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
