package com.xiaopeng.xmart.cargallery.bean;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
/* loaded from: classes9.dex */
public class StorageVolumeReflected {
    public static final int STATE_BAD_REMOVAL = 8;
    public static final int STATE_CHECKING = 1;
    public static final int STATE_EJECTING = 5;
    public static final int STATE_FORMATTING = 4;
    public static final int STATE_MOUNTED = 2;
    public static final int STATE_MOUNTED_READ_ONLY = 3;
    public static final int STATE_REMOVED = 7;
    public static final int STATE_UNMOUNTABLE = 6;
    public static final int STATE_UNMOUNTED = 0;
    private String fsLable;
    private String mFsUuid;
    private String mPath;
    private boolean mPrimary;
    private boolean mRemovable;
    private String mState;

    public StorageVolumeReflected(Object reflectItem) {
        try {
            Field fieldFsLabelField = reflectItem.getClass().getDeclaredField("mDescription");
            fieldFsLabelField.setAccessible(true);
            this.fsLable = (String) fieldFsLabelField.get(reflectItem);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Method fmPath = reflectItem.getClass().getDeclaredMethod("getPath", new Class[0]);
            fmPath.setAccessible(true);
            this.mPath = (String) fmPath.invoke(reflectItem, new Object[0]);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            Method getUuid = reflectItem.getClass().getDeclaredMethod("getUuid", new Class[0]);
            getUuid.setAccessible(true);
            this.mFsUuid = (String) getUuid.invoke(reflectItem, new Object[0]);
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        try {
            Method fmPrimary = reflectItem.getClass().getDeclaredMethod("isPrimary", new Class[0]);
            fmPrimary.setAccessible(true);
            this.mPrimary = ((Boolean) fmPrimary.invoke(reflectItem, new Object[0])).booleanValue();
        } catch (Exception e4) {
            e4.printStackTrace();
        }
        try {
            Method fisRemovable = reflectItem.getClass().getDeclaredMethod("isRemovable", new Class[0]);
            fisRemovable.setAccessible(true);
            this.mRemovable = ((Boolean) fisRemovable.invoke(reflectItem, new Object[0])).booleanValue();
        } catch (Exception e5) {
            e5.printStackTrace();
        }
        try {
            Method fState = reflectItem.getClass().getDeclaredMethod("getState", new Class[0]);
            fState.setAccessible(true);
            this.mState = (String) fState.invoke(reflectItem, new Object[0]);
        } catch (Exception e6) {
            e6.printStackTrace();
        }
    }

    public String getFsLable() {
        return this.fsLable;
    }

    public String getPath() {
        return this.mPath;
    }

    public String getFsUuid() {
        return this.mFsUuid;
    }

    public boolean isPrimary() {
        return this.mPrimary;
    }

    public boolean isRemovable() {
        return this.mRemovable;
    }

    public String getState() {
        return this.mState;
    }

    public String toString() {
        return "DVRStorageVolume{\n, fsLable='" + this.fsLable + "'\n, mPath='" + this.mPath + "'\n, mFsUuid='" + this.mFsUuid + "'\n, mPrimary=" + this.mPrimary + "\n, mRemovable=" + this.mRemovable + "\n, mState='" + this.mState + "'}\n";
    }
}
