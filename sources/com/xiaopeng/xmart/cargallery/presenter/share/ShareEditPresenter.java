package com.xiaopeng.xmart.cargallery.presenter.share;

import android.text.TextUtils;
import com.xiaopeng.speech.SpeechClient;
import com.xiaopeng.speech.asr.RecognizeListener;
import com.xiaopeng.speech.asr.Recognizer;
import com.xiaopeng.xmart.cargallery.CameraLog;
import com.xiaopeng.xmart.cargallery.model.share.ShareEditModel;
import com.xiaopeng.xmart.cargallery.view.share.edit.IShareEditView;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONObject;
/* loaded from: classes8.dex */
public class ShareEditPresenter {
    private static final String TAG = "ShareEditPresenter";
    private volatile Recognizer recognizer;
    private IShareEditView view;
    Pattern pattern = Pattern.compile("(?<=[\\x{4e00}-\\x{9fa5}])\\s(?=[\\x{4e00}-\\x{9fa5}])");
    private RecognizeListener listener = new RecognizeListener() { // from class: com.xiaopeng.xmart.cargallery.presenter.share.ShareEditPresenter.1
        @Override // com.xiaopeng.speech.asr.RecognizeListener
        public void onResult(String jsonResult, boolean last) {
            CameraLog.d(ShareEditPresenter.TAG, "on result, last: " + last + ", result: " + jsonResult, false);
            try {
                JSONObject jsResult = new JSONObject(jsonResult);
                String text = jsResult.getString("text");
                boolean end = jsResult.getBoolean("eof");
                if (end) {
                    if (!TextUtils.isEmpty(text)) {
                        Matcher m = ShareEditPresenter.this.pattern.matcher(text);
                        text = m.replaceAll("");
                    }
                    ShareEditPresenter.this.view.parseTextResult(text);
                }
            } catch (Throwable th) {
                CameraLog.e(ShareEditPresenter.TAG, "parse result error: ", false);
            }
        }

        @Override // com.xiaopeng.speech.asr.RecognizeListener
        public void onError(final int code, final String info) {
            CameraLog.e(ShareEditPresenter.TAG, "on error, code: " + code + ", info: " + info, false);
            ShareEditPresenter.this.view.onRecordEnd(true);
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // com.xiaopeng.speech.asr.RecognizeListener
        public void onState(final int state, final int extra) {
            CameraLog.d(ShareEditPresenter.TAG, "on state, state: " + state + ", extra: " + extra, false);
            switch (state) {
                case 0:
                    CameraLog.d(ShareEditPresenter.TAG, "onStarted of asr", false);
                    break;
                case 1:
                    break;
                case 2:
                    CameraLog.d(ShareEditPresenter.TAG, "within eos and speak end", false);
                    return;
                case 3:
                    CameraLog.d(ShareEditPresenter.TAG, "bos timeout", false);
                    return;
                case 4:
                    CameraLog.d(ShareEditPresenter.TAG, "record end", false);
                    ShareEditPresenter.this.view.onRecordEnd(false);
                    return;
                case 5:
                    CameraLog.d(ShareEditPresenter.TAG, "asr end", false);
                    return;
                case 6:
                    CameraLog.d(ShareEditPresenter.TAG, "speak end", false);
                    ShareEditPresenter.this.view.onSpeakEnd();
                    return;
                default:
                    return;
            }
            CameraLog.d(ShareEditPresenter.TAG, "within bos and start speak", false);
        }

        @Override // com.xiaopeng.speech.asr.RecognizeListener
        public void onExtra(int type, int arg1, int arg2, String info, byte[] buffer) {
            CameraLog.d(ShareEditPresenter.TAG, "onExtra type=" + type + " arg1=" + arg1 + " arg2=" + arg2 + " info:" + info);
        }
    };
    private ShareEditModel mModel = new ShareEditModel();

    public ShareEditPresenter(IShareEditView view) {
        this.view = view;
    }

    public void initRecord() {
        this.recognizer = SpeechClient.instance().getRecognizer();
        if (this.recognizer != null) {
            this.recognizer.setBool(Recognizer.KEEP_AUDIO_RECORD, true);
            this.recognizer.setBool(Recognizer.ENABLE_ASR_PUNCT, true);
            this.recognizer.setInt(Recognizer.AUDIO_FORMAT, 1);
            this.recognizer.setBool(Recognizer.ASR_BUFFER, false);
        }
    }

    public void releaseRecord() {
        CameraLog.d(TAG, "releaseRecord ", false);
        if (this.recognizer != null) {
            if (this.recognizer.isListening()) {
                this.recognizer.stopListening();
            }
            this.recognizer.cancel();
            this.recognizer = null;
        }
    }

    public void stopRecordByUser() {
        CameraLog.d(TAG, "stop record", false);
        this.recognizer.stopListening();
    }

    public void startRecord() {
        if (this.recognizer == null) {
            CameraLog.d(TAG, "startRecord failure recognizer is null...", false);
            return;
        }
        if (SpeechClient.instance().getSpeechState().isDMStarted()) {
            CameraLog.d(TAG, "Break the dialog first.", false);
            SpeechClient.instance().getWakeupEngine().stopDialog();
        }
        CameraLog.d(TAG, "startRecord...", false);
        this.recognizer.setString(Recognizer.AUDIO_SAVE_PATH, null);
        this.recognizer.setInt(Recognizer.EOS, 2000);
        this.recognizer.setInt(Recognizer.MAX_ACTIVE_TIME, 60000);
        this.recognizer.setBool(Recognizer.DISABLE_ASR, false);
        this.recognizer.startListening(this.listener);
    }
}
