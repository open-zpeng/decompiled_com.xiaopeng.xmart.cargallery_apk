package com.xiaopeng.xmart.cargallery.view.share;
/* loaded from: classes3.dex */
public interface ISharePublishView {
    void fetchOtp(String token, String uid);

    void fetchOtpError();

    void fetchOtpException(String message);

    void shareFileIllegal();

    void shareOnError(int code, String reason);

    void shareOnException(String message);

    void shareOnSuccess(String result);

    void uploadProgress(int progress, int total, String fileName);
}
