package com.xiaopeng.xmart.cargallery.unity.share;
/* loaded from: classes2.dex */
interface IShareListener {
    void fetchOtp(String token, String uid);

    void fetchOtpError();

    void fetchOtpException(String message);

    void onRecordEnd(boolean error);

    void onSpeakEnd();

    void parseTextResult(String text);

    void shareFileIllegal();

    void shareOnError(int code, String reason);

    void shareOnException(String message);

    void shareOnSuccess(String result);

    void uploadProgress(int progress, int total, String fileName);
}
