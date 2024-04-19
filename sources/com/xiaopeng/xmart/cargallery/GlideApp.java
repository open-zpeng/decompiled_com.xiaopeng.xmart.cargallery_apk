package com.xiaopeng.xmart.cargallery;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import java.io.File;
/* loaded from: classes21.dex */
public final class GlideApp {
    private GlideApp() {
    }

    public static File getPhotoCacheDir(Context context) {
        return Glide.getPhotoCacheDir(context);
    }

    public static File getPhotoCacheDir(Context context, String string) {
        return Glide.getPhotoCacheDir(context, string);
    }

    public static Glide get(Context context) {
        return Glide.get(context);
    }

    @Deprecated
    public static void init(Glide glide) {
        Glide.init(glide);
    }

    public static void init(Context context, GlideBuilder builder) {
        Glide.init(context, builder);
    }

    public static void tearDown() {
        Glide.tearDown();
    }

    public static GlideRequests with(Context context) {
        return (GlideRequests) Glide.with(context);
    }

    public static GlideRequests with(Activity activity) {
        return (GlideRequests) Glide.with(activity);
    }

    public static GlideRequests with(FragmentActivity activity) {
        return (GlideRequests) Glide.with(activity);
    }

    public static GlideRequests with(Fragment fragment) {
        return (GlideRequests) Glide.with(fragment);
    }

    @Deprecated
    public static GlideRequests with(android.app.Fragment fragment) {
        return (GlideRequests) Glide.with(fragment);
    }

    public static GlideRequests with(View view) {
        return (GlideRequests) Glide.with(view);
    }
}
