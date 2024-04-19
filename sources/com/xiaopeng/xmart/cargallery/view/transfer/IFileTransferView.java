package com.xiaopeng.xmart.cargallery.view.transfer;

import android.net.wifi.WifiClient;
import com.xiaopeng.xmart.cargallery.bean.ConnectionBean;
import java.util.List;
/* loaded from: classes13.dex */
public interface IFileTransferView {
    void addConnect(ConnectionBean protocol);

    void apDisable();

    void clientReceiveOver();

    void clientReject(ConnectionBean o);

    void onToastInfo(int status);

    void onToastTransferInfo(int status, int sucNum, int failureNum);

    void replyError(ConnectionBean connection);

    void replySuc(ConnectionBean connection);

    void showSingleConnection();

    void transferStart(String acceptIp);

    void updateConnectList(List<WifiClient> clientList);

    void updateProgress(long progress, long total, String filePath);

    void updateTransferFailure(String filePath);
}
