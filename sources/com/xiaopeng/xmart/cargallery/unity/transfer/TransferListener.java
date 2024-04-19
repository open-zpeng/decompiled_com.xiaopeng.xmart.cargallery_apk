package com.xiaopeng.xmart.cargallery.unity.transfer;

import android.net.wifi.WifiClient;
import com.xiaopeng.xmart.cargallery.bean.ConnectionBean;
import java.util.List;
/* loaded from: classes8.dex */
public interface TransferListener {
    void addConnect(ConnectionBean connectionBean);

    void apDisabled();

    void clientReceiveOver();

    void clientReject(ConnectionBean connectionBean);

    void onToastInfo(int status);

    void onToastTransferInfo(int status, int sucNum, int failureNum);

    void replySuc(ConnectionBean connection);

    void transferStart(String ip);

    void updateConnectList(List<WifiClient> clientList);

    void updateProgress(long progress, long total, String filePath);

    void updateTransferFailure(String filePath);
}
