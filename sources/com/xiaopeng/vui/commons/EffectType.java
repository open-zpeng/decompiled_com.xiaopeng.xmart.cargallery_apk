package com.xiaopeng.vui.commons;
/* loaded from: classes.dex */
public enum EffectType {
    WATERRIPPLE("waterRipple");
    
    String effect;

    EffectType(String effect) {
        this.effect = effect;
    }

    public String getEffectType() {
        return this.effect;
    }
}
