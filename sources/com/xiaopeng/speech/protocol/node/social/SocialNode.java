package com.xiaopeng.speech.protocol.node.social;

import com.xiaopeng.speech.SpeechClient;
import com.xiaopeng.speech.SpeechNode;
import org.json.JSONException;
import org.json.JSONObject;
/* loaded from: classes.dex */
public class SocialNode extends SpeechNode<SocialListener> {
    public static final String GROUP_MESSAGE_INTENT = "播放群内容";
    public static final String JOIN_GROUP_INTENT = "加入鹏窝";
    private static final String LBS_SOCAIL_TASK = "LBS社交";
    private static final String OFFLINE_SKILL = "命令词";
    public static final String QUERY_SEND_MESSAGE = "发送消息";
    public static final String QUERY_SET_VOICE_BUTTON = "设置方向盘按钮";

    public void onSocialMotorcadeOpen(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((SocialListener) obj).onSocialMotorcadeOpen();
            }
        }
    }

    public void onSocialMotorcadeClose(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((SocialListener) obj).onSocialMotorcadeClose();
            }
        }
    }

    public void onSocialGrabMic(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((SocialListener) obj).onSocialGrabMic();
            }
        }
    }

    public void onSocialGrabMicCancel(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((SocialListener) obj).onSocialGrabMicCancel();
            }
        }
    }

    public void onSocialCreateTopic(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((SocialListener) obj).onSocialCreateTopic();
            }
        }
    }

    public void onSocialReplyTopic(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((SocialListener) obj).onSocialReplyTopic();
            }
        }
    }

    public void onSocialQuitChat(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((SocialListener) obj).onSocialQuitChat();
            }
        }
    }

    public void onSocialConfirm(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        String intent = "";
        try {
            JSONObject obj = new JSONObject(data);
            intent = obj.optString("intent");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (listenerList != null) {
            for (Object obj2 : listenerList) {
                ((SocialListener) obj2).onSocialConfirm(intent);
            }
        }
    }

    public void onSocialCancel(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        String intent = "";
        try {
            JSONObject obj = new JSONObject(data);
            intent = obj.optString("intent");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (listenerList != null) {
            for (Object obj2 : listenerList) {
                ((SocialListener) obj2).onSocialCancel(intent);
            }
        }
    }

    public void onVoiceButtonClick(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((SocialListener) obj).onVoiceButtonClick();
            }
        }
    }

    public void onBackButtonClick(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((SocialListener) obj).onBackButtonClick();
            }
        }
    }

    public void broadcastGroupMessage(String tts) {
        String slots = "";
        try {
            slots = new JSONObject().put("tts", tts).put("intent", GROUP_MESSAGE_INTENT).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        SpeechClient.instance().getAgent().triggerIntent(OFFLINE_SKILL, LBS_SOCAIL_TASK, GROUP_MESSAGE_INTENT, slots);
    }

    public void joinGroup(String tts) {
        String slots = "";
        try {
            slots = new JSONObject().put("tts", tts).put("intent", JOIN_GROUP_INTENT).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        SpeechClient.instance().getAgent().triggerIntent(OFFLINE_SKILL, LBS_SOCAIL_TASK, JOIN_GROUP_INTENT, slots);
    }

    public void querySendMessage(String tts) {
        String slots = "";
        try {
            slots = new JSONObject().put("tts", tts).put("intent", QUERY_SEND_MESSAGE).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        SpeechClient.instance().getAgent().triggerIntent(OFFLINE_SKILL, LBS_SOCAIL_TASK, QUERY_SEND_MESSAGE, slots);
    }

    public void querySetVoiceButton(String tts) {
        String slots = "";
        try {
            slots = new JSONObject().put("tts", tts).put("intent", QUERY_SET_VOICE_BUTTON).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        SpeechClient.instance().getAgent().triggerIntent(OFFLINE_SKILL, LBS_SOCAIL_TASK, QUERY_SET_VOICE_BUTTON, slots);
    }

    public void stopDialog() {
        SpeechClient.instance().getWakeupEngine().stopDialog();
    }
}
