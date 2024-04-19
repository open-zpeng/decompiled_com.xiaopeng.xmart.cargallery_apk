package com.xiaopeng.xmart.cargallery.presenter.transfer.service.callback;
/* loaded from: classes5.dex */
public interface TransferProgressCallBack extends CallBack {
    void onProgressUpdate(long progress, long total, String filePath);

    void onTransferFailure(String file);

    void onTransferStart(String address);

    void onTransferSuccess(String file);
}
