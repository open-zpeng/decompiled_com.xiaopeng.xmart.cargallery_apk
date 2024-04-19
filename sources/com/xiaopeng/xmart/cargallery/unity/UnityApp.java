package com.xiaopeng.xmart.cargallery.unity;

import android.content.Context;
import com.xiaopeng.xmart.cargallery.App;
/* loaded from: classes8.dex */
public class UnityApp extends App {
    @Override // android.content.ContextWrapper
    public void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        sInstance = this;
    }
}
