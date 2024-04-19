package com.xiaopeng.xmart.cargallery.presenter.transfer.service.callback;
/* loaded from: classes5.dex */
public interface TransferStatusCallBack extends CallBack {
    public static final int STATUS_HAND_SHAKE = 16;
    public static final int STATUS_PREPARE = 0;
    public static final int STATUS_PREPARE_ERROR = 2;
    public static final int STATUS_PREPARE_SUC = 1;
    public static final int STATUS_TRANSFER = 17;
    public static final int STATUS_TRANSFER_FAILURE = 34;
    public static final int STATUS_TRANSFER_PART = 33;
    public static final int STATUS_TRANSFER_SUC = 32;

    void updateTransferResult(int status, int sucNum, int failureNum);

    void updateTransferStatus(int status);
}
