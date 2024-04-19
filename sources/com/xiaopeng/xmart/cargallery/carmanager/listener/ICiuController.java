package com.xiaopeng.xmart.cargallery.carmanager.listener;
/* loaded from: classes7.dex */
public interface ICiuController {

    /* loaded from: classes7.dex */
    public static class CiuDVRModeEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes7.dex */
    public static class CiuDVRStatusEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes7.dex */
    public static class CiuSdStEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes7.dex */
    public static class CiuVideoOutputEventMsg extends AbstractEventMsg<Integer> {
    }

    int getDVRStatus() throws Exception;

    int getDvrMode() throws Exception;

    int getSdStatus() throws Exception;

    void setDvrMode(int mode) throws Exception;

    void setVideoOutputMode(int mode) throws Exception;
}
