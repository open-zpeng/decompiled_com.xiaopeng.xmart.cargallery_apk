package com.xiaopeng.xmart.cargallery.bean;

import android.text.TextUtils;
/* loaded from: classes9.dex */
public class ConnectionBean {
    protected int action;
    protected boolean connecting;
    protected String deviceName;
    protected String ip;
    protected int platform;
    protected int port;
    protected int version;

    public void setPort(int port) {
        this.port = port;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public int getPort() {
        return this.port;
    }

    public String getIp() {
        return this.ip;
    }

    public int getAction() {
        return this.action;
    }

    public String getDeviceName() {
        return this.deviceName;
    }

    public boolean isConnecting() {
        return this.connecting;
    }

    public void setConnecting(boolean connecting) {
        this.connecting = connecting;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getPlatform() {
        return this.platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public String toString() {
        return "ConnectionBean{port=" + this.port + ", ip='" + this.ip + "', action=" + this.action + ", version=" + this.version + ", platform=" + this.platform + ", deviceName='" + this.deviceName + "'}";
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        ConnectionBean bean = (ConnectionBean) o;
        boolean portMatch = this.port == bean.port;
        boolean ipMatch = TextUtils.equals(this.ip, bean.ip);
        boolean actionMatch = this.action == bean.action;
        boolean versionMatch = this.version == bean.version;
        boolean platformMatch = this.platform == bean.platform;
        boolean deviceNameMatch = TextUtils.equals(this.deviceName, bean.deviceName);
        return portMatch && ipMatch && actionMatch && versionMatch && platformMatch && deviceNameMatch;
    }
}
