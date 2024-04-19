package com.xiaopeng.xmart.cargallery.bean;

import java.util.List;
/* loaded from: classes9.dex */
public class UdpHandshakeProtocol extends ConnectionBean {
    private List<String> fileList;

    public List<String> getFileList() {
        return this.fileList;
    }

    public void setFileList(List<String> fileList) {
        this.fileList = fileList;
    }

    @Override // com.xiaopeng.xmart.cargallery.bean.ConnectionBean
    public String toString() {
        return "UdpHandshakeProtocol{port=" + this.port + ", ip='" + this.ip + "', action=" + this.action + ", deviceName='" + this.deviceName + "', fileList=" + this.fileList + '}';
    }
}
