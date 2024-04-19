package com.xiaopeng.xmart.cargallery.utils;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
/* loaded from: classes3.dex */
public class IoUtils {
    public static void closeSilently(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
            }
        }
    }

    public static boolean isFileExist(String path) {
        File file = new File(path);
        return file.exists();
    }
}
