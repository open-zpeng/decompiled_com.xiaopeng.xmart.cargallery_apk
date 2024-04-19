package com.xiaopeng.xmart.cargallery.presenter.transfer.service.callback;

import android.net.wifi.WifiClient;
import com.xiaopeng.xmart.cargallery.bean.ConnectionBean;
import java.util.List;
/* loaded from: classes5.dex */
public interface NetWorkCallBack extends CallBack {
    void addConnection(ConnectionBean connectionBean);

    void apDisabled();

    void apEnabled(String ssid, String pwd);

    void clientReceiveOver();

    void rejectByConnection(ConnectionBean connectionBean);

    void updateClientList(List<WifiClient> clientList);
}
