package com.xiaopeng.xmart.cargallery.fastsp;

import android.content.SharedPreferences;
import java.io.Serializable;
/* loaded from: classes10.dex */
public interface EnhancedSharedPreferences extends SharedPreferences {

    /* loaded from: classes10.dex */
    public interface EnhancedEditor extends SharedPreferences.Editor {
        EnhancedEditor putSerializable(String key, Serializable value);
    }

    Serializable getSerializable(String key, Serializable defValue);
}
