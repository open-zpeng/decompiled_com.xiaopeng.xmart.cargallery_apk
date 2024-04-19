package com.xiaopeng.xmart.cargallery.widget;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.speech.speechwidget.ListWidget;
import com.xiaopeng.xmart.cargallery.App;
import com.xiaopeng.xmart.cargallery.R;
import com.xiaopeng.xmart.cargallery.utils.ToastUtils;
import com.xiaopeng.xmart.cargallery.utils.ViewUtils;
import com.xiaopeng.xui.widget.XLoading;
/* loaded from: classes19.dex */
public class SpeechInputLayout extends FrameLayout {
    private static final String CAR_CONTROL_PACKAGE = "com.xiaopeng.carcontrol";
    private static final String MICRO_SETTING_ACTION = "com.xiaopeng.carcontrol.intent.action.ACTION_SHOW_MICROPHONE_UNMUTE_DIALOG";
    private static final int PREPARE_MODE = 0;
    private static final int RECORD_MODE = 1;
    private static final String TAG = "SpeechInputLayout";
    private static final int TRANSFER_MODE = 2;
    private boolean isNeedInterrupt;
    private AudioManager mAudioManager;
    private int mCurrentMode;
    private ImageView mIvSpeechPrepare;
    private SpeechListener mListener;
    private SpeechInputView mSvSpeechRecord;
    private XLoading mXlSpeechTranslate;

    /* loaded from: classes19.dex */
    public interface SpeechListener {
        void startRecord();

        void stopRecord(boolean byUser);
    }

    public SpeechInputLayout(Context context) {
        super(context);
        initView(context);
    }

    public SpeechInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public SpeechInputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public SpeechInputLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_speech_inflat, (ViewGroup) this, true);
        this.mIvSpeechPrepare = (ImageView) view.findViewById(R.id.iv_speech_prepare);
        this.mSvSpeechRecord = (SpeechInputView) view.findViewById(R.id.siv_input);
        this.mXlSpeechTranslate = (XLoading) view.findViewById(R.id.xl_speech_loading);
        this.mAudioManager = (AudioManager) context.getSystemService(ListWidget.EXTRA_TYPE_AUDIO);
    }

    public void setPrepareMode() {
        this.mCurrentMode = 0;
        ViewUtils.setViewsVisible(this.mIvSpeechPrepare);
        ViewUtils.setViewsGone(this.mSvSpeechRecord, this.mXlSpeechTranslate);
    }

    public void setRecordMode() {
        this.mCurrentMode = 1;
        ViewUtils.setViewsVisible(this.mSvSpeechRecord);
        ViewUtils.setViewsGone(this.mIvSpeechPrepare, this.mXlSpeechTranslate);
    }

    public void setTransferMode() {
        this.mCurrentMode = 2;
        ViewUtils.setViewsVisible(this.mXlSpeechTranslate);
        ViewUtils.setViewsGone(this.mIvSpeechPrepare, this.mSvSpeechRecord);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case 0:
                if (this.isNeedInterrupt) {
                    ToastUtils.showToast(getContext(), getContext().getString(R.string.share_publish_text_limit));
                    break;
                } else if (this.mAudioManager.isMicrophoneMute()) {
                    Intent intent = new Intent(MICRO_SETTING_ACTION);
                    intent.setPackage(CAR_CONTROL_PACKAGE);
                    intent.addFlags(20971552);
                    App.getInstance().sendBroadcast(intent);
                    break;
                } else {
                    int i = this.mCurrentMode;
                    if (i == 0) {
                        LogUtils.d(TAG, "touch event trigger start record!");
                        this.mCurrentMode = 1;
                        SpeechListener speechListener = this.mListener;
                        if (speechListener != null) {
                            speechListener.startRecord();
                        }
                    } else if (i == 1) {
                        LogUtils.d(TAG, "touch event trigger stop record!");
                        this.mCurrentMode = 0;
                        SpeechListener speechListener2 = this.mListener;
                        if (speechListener2 != null) {
                            speechListener2.stopRecord(true);
                        }
                    }
                    invalidate();
                    break;
                }
        }
        return super.onTouchEvent(event);
    }

    public void notifyTextCount(boolean needInterrupt) {
        this.isNeedInterrupt = needInterrupt;
        if (needInterrupt) {
            if (this.mCurrentMode == 1) {
                LogUtils.d(TAG, "exceed the limit stop record!", false);
                this.mCurrentMode = 0;
                SpeechListener speechListener = this.mListener;
                if (speechListener != null) {
                    speechListener.stopRecord(true);
                }
                ToastUtils.showToast(getContext(), getContext().getString(R.string.share_publish_text_limit));
            }
            invalidate();
        }
    }

    public void setListener(SpeechListener mListener) {
        this.mListener = mListener;
    }
}
