package com.xiaopeng.xmart.cargallery.carmanager.listener;
/* loaded from: classes7.dex */
public interface IVcuController {
    public static final int GEAR_LEVEL_D = 1;
    public static final int GEAR_LEVEL_INVALID = 0;
    public static final int GEAR_LEVEL_N = 2;
    public static final int GEAR_LEVEL_P = 4;
    public static final int GEAR_LEVEL_R = 3;

    /* loaded from: classes7.dex */
    public static class CarGearLevelEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes7.dex */
    public static class CarSpeedEventMsg extends AbstractEventMsg<Float> {
    }

    float getCarSpeed() throws Exception;

    int getGearLevel();

    int getStallState();
}
