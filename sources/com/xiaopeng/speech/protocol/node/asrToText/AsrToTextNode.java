package com.xiaopeng.speech.protocol.node.asrToText;

import android.text.TextUtils;
import com.xiaopeng.speech.SpeechClient;
import com.xiaopeng.speech.asr.RecognizeListener;
import com.xiaopeng.speech.asr.Recognizer;
import com.xiaopeng.speech.common.SpeechConstant;
import com.xiaopeng.speech.common.util.LogUtils;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;
/* loaded from: classes.dex */
public class AsrToTextNode {
    private AsrToTextListener mListener;
    private Recognizer mRecognizer;
    private String TAG = "AsrToTextNode";
    private String mInitParam = null;
    private boolean isRecording = false;
    private final RecognizeListener recognizeListener = new RecognizeListener() { // from class: com.xiaopeng.speech.protocol.node.asrToText.AsrToTextNode.1
        volatile int mState;
        boolean isEnd = false;
        String resultStr = "";

        @Override // com.xiaopeng.speech.asr.RecognizeListener
        public void onResult(String result, boolean last) {
            LogUtils.i(AsrToTextNode.this.TAG, "on result: " + result + ", last: " + last + ",state:" + this.mState);
            try {
                if (!TextUtils.isEmpty(result)) {
                    JSONObject resultObj = new JSONObject(result);
                    JSONObject asr_result = new JSONObject();
                    String text = resultObj.optString("text");
                    this.resultStr = replaceBlank(text);
                    if (this.mState == 3 && !last && TextUtils.isEmpty(text)) {
                        asr_result.put("text", text);
                        asr_result.put("last", true);
                        asr_result.put("messageCode", "401");
                        AsrToTextNode.this.isRecording = false;
                    } else if (this.mState != 4 || !TextUtils.isEmpty(text)) {
                        asr_result.put("text", replaceBlank(text));
                        asr_result.put("last", last);
                        asr_result.put("messageCode", "200");
                    } else {
                        asr_result.put("text", text);
                        asr_result.put("last", true);
                        asr_result.put("messageCode", "401");
                        AsrToTextNode.this.isRecording = false;
                    }
                    if (last) {
                        this.isEnd = true;
                        this.resultStr = "";
                        AsrToTextNode.this.isRecording = false;
                    }
                    if (AsrToTextNode.this.mListener != null) {
                        AsrToTextNode.this.mListener.onResult(asr_result.toString());
                    }
                }
            } catch (JSONException e) {
                e.fillInStackTrace();
            }
        }

        private String replaceBlank(String str) {
            if (TextUtils.isEmpty(str)) {
                return "";
            }
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            String dest = m.replaceAll("");
            return dest;
        }

        @Override // com.xiaopeng.speech.asr.RecognizeListener
        public void onError(int code, String info) {
            LogUtils.e(AsrToTextNode.this.TAG, "on error: " + code + ", info: " + info);
            try {
                JSONObject asr_result = new JSONObject();
                asr_result.put("messageCode", "" + code);
                if (AsrToTextNode.this.mListener != null) {
                    AsrToTextNode.this.mListener.onResult(asr_result.toString());
                }
                AsrToTextNode.this.isRecording = false;
            } catch (JSONException e) {
                e.fillInStackTrace();
            }
        }

        @Override // com.xiaopeng.speech.asr.RecognizeListener
        public void onState(int state, int code) {
            LogUtils.d(AsrToTextNode.this.TAG, "on onState " + state + ", info: " + code);
            this.mState = state;
            if (state == 6) {
                try {
                    if (!this.isEnd) {
                        try {
                            JSONObject asr_result = new JSONObject();
                            asr_result.put("text", this.resultStr);
                            asr_result.put("last", true);
                            if (TextUtils.isEmpty(this.resultStr)) {
                                asr_result.put("messageCode", "401");
                            } else {
                                asr_result.put("messageCode", "200");
                            }
                            if (AsrToTextNode.this.mListener != null) {
                                AsrToTextNode.this.mListener.onResult(asr_result.toString());
                            }
                            AsrToTextNode.this.isRecording = false;
                        } catch (JSONException e) {
                            e.fillInStackTrace();
                        }
                        return;
                    }
                    this.resultStr = "";
                    this.isEnd = false;
                } finally {
                    this.resultStr = "";
                }
            }
        }

        @Override // com.xiaopeng.speech.asr.RecognizeListener
        public void onExtra(int type, int arg1, int arg2, String info, byte[] buffer) {
        }
    };

    public void onServiceConnect() {
        LogUtils.i(this.TAG, "onConnect:" + this.mListener);
        if (this.mListener != null) {
            getRecognizer();
        }
    }

    public void onServiceDisconnect() {
        LogUtils.i(this.TAG, "onDisconnect:" + this.isRecording + ",mListener:" + this.mListener);
        if (this.isRecording) {
            this.mRecognizer = null;
            this.isRecording = false;
            if (this.mListener != null) {
                JSONObject asr_result = new JSONObject();
                try {
                    asr_result.put("messageCode", "501");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                this.mListener.onResult(asr_result.toString());
            }
        }
    }

    /* loaded from: classes.dex */
    private static class SingleHolder {
        static final AsrToTextNode INSTANCE = new AsrToTextNode();

        private SingleHolder() {
        }
    }

    public static AsrToTextNode getInstance() {
        return SingleHolder.INSTANCE;
    }

    public void initRecord(String param, AsrToTextListener listener) {
        LogUtils.i(this.TAG, "initRecognizer:" + param);
        this.mListener = listener;
        this.mInitParam = param;
        getRecognizer();
    }

    private Recognizer getRecognizer() {
        if (this.mRecognizer == null) {
            this.mRecognizer = SpeechClient.instance().getRecognizer();
            if (TextUtils.isEmpty(this.mInitParam) || this.mInitParam.equals("null")) {
                this.mRecognizer.setString(Recognizer.AUDIO_SAVE_PATH, null);
                this.mRecognizer.setBool(Recognizer.KEEP_AUDIO_RECORD, true);
                this.mRecognizer.setBool(Recognizer.ENABLE_ASR_PUNCT, true);
                this.mRecognizer.setBool(Recognizer.ASR_BUFFER, false);
                this.mRecognizer.setInt(Recognizer.AUDIO_FORMAT, 1);
            } else {
                try {
                    JSONObject paramObj = new JSONObject(this.mInitParam);
                    Iterator<String> iterator = paramObj.keys();
                    while (iterator.hasNext()) {
                        String key = iterator.next();
                        Object value = paramObj.opt(key);
                        if (value instanceof Integer) {
                            this.mRecognizer.setInt(key, ((Integer) value).intValue());
                        } else if (value instanceof Boolean) {
                            this.mRecognizer.setBool(key, ((Boolean) value).booleanValue());
                        } else if (value instanceof String) {
                            this.mRecognizer.setString(key, (String) value);
                        }
                    }
                } catch (JSONException e) {
                    e.fillInStackTrace();
                }
            }
        }
        return this.mRecognizer;
    }

    public void initRecord(AsrToTextListener listener) {
        initRecord(null, listener);
    }

    public void startRecord(String packageName) {
        startRecord(packageName, null);
    }

    public void startRecord(String packageName, String param) {
        try {
            LogUtils.i(this.TAG, "startRecord:" + param + ",packageName:" + packageName);
            String result = SpeechClient.instance().getSpeechState().canPerformASRToText(packageName);
            if (TextUtils.isEmpty(result)) {
                if (this.mListener != null) {
                    JSONObject asr_result = new JSONObject();
                    asr_result.put("messageCode", "501");
                    this.mListener.onResult(asr_result.toString());
                }
            } else {
                String message = new JSONObject(result).optString("messageCode");
                if (TextUtils.isEmpty(message)) {
                    startListen(param);
                } else {
                    AsrToTextListener asrToTextListener = this.mListener;
                    if (asrToTextListener != null) {
                        asrToTextListener.onResult(result);
                    }
                }
            }
        } catch (JSONException e) {
            e.fillInStackTrace();
        }
    }

    private void startListen(String param) {
        try {
            Recognizer recognizer = getRecognizer();
            if (recognizer != null && !recognizer.isListening()) {
                LogUtils.i(this.TAG, "startListen param:" + param);
                if (!TextUtils.isEmpty(param) && !param.equals("null")) {
                    JSONObject paramObj = new JSONObject(param);
                    Iterator<String> iterator = paramObj.keys();
                    while (iterator.hasNext()) {
                        String key = iterator.next();
                        Object value = paramObj.opt(key);
                        if (value instanceof Integer) {
                            recognizer.setInt(key, ((Integer) value).intValue());
                        } else if (value instanceof Boolean) {
                            recognizer.setBool(key, ((Boolean) value).booleanValue());
                        } else if (value instanceof String) {
                            recognizer.setString(key, (String) value);
                        }
                    }
                    recognizer.startListening(this.recognizeListener);
                    this.isRecording = true;
                }
                recognizer.setString(Recognizer.AUDIO_SAVE_PATH, null);
                recognizer.setInt(Recognizer.EOS, 1000);
                recognizer.setInt(Recognizer.BOS, SpeechConstant.VAD_TIMEOUT);
                recognizer.setInt(Recognizer.MAX_ACTIVE_TIME, 60000);
                recognizer.setBool(Recognizer.DISABLE_ASR, false);
                recognizer.startListening(this.recognizeListener);
                this.isRecording = true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void stopRecord() {
        LogUtils.i(this.TAG, "stopRecord");
        Recognizer recognizer = getRecognizer();
        if (recognizer != null && recognizer.isListening()) {
            recognizer.stopListening();
        }
        this.isRecording = false;
    }

    public void destroyRecord() {
        LogUtils.i(this.TAG, "destroyRecord");
        Recognizer recognizer = getRecognizer();
        if (recognizer != null) {
            recognizer.cancel();
        }
        this.mListener = null;
        this.isRecording = false;
    }
}
