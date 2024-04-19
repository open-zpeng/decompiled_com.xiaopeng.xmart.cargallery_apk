package com.xiaopeng.xmart.cargallery.carmanager.listener;
/* loaded from: classes7.dex */
public interface IMcuController {

    /* loaded from: classes7.dex */
    public static class CiuStatusChangeEvent extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes7.dex */
    public static class IGStatusChangeEvent extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes7.dex */
    public static class OcuStatusChangeEvent extends AbstractEventMsg<Integer> {
    }

    int getCiuState() throws Exception;

    String getHardwareCarStage() throws Exception;

    String getHardwareCarType() throws Exception;

    int getHardwareVersion();

    int getOcuState() throws Exception;

    String getXpCduType() throws Exception;

    void setFlash(boolean on) throws Exception;
}
