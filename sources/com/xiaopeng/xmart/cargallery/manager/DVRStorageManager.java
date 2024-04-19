package com.xiaopeng.xmart.cargallery.manager;

import android.os.storage.StorageManager;
import android.text.TextUtils;
import com.xiaopeng.xmart.cargallery.App;
import com.xiaopeng.xmart.cargallery.CameraLog;
import com.xiaopeng.xmart.cargallery.bean.StorageVolumeReflected;
import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes9.dex */
public class DVRStorageManager {
    private static DVRStorageManager INSTANCE = null;
    private static final String TAG = "DVRStorageManager";

    private DVRStorageManager() {
    }

    public static DVRStorageManager getInstance() {
        if (INSTANCE == null) {
            synchronized (DVRStorageManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DVRStorageManager();
                }
            }
        }
        return INSTANCE;
    }

    private List<StorageVolumeReflected> getStorageVolumeInfo() {
        List<StorageVolumeReflected> volumeList = new ArrayList<>();
        StorageManager storageManager = (StorageManager) App.getInstance().getSystemService("storage");
        try {
            Class<?>[] paramClasses = new Class[0];
            Method method = StorageManager.class.getMethod("getVolumeList", paramClasses);
            Object[] params = new Object[0];
            Object[] invokes = (Object[]) method.invoke(storageManager, params);
            if (invokes != null) {
                for (Object object : invokes) {
                    volumeList.add(new StorageVolumeReflected(object));
                }
            }
            for (StorageVolumeReflected volume : volumeList) {
                CameraLog.i(TAG, "volume:" + volume.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return volumeList;
    }

    public String getDVRDirPath() {
        String dvrDir = null;
        List<StorageVolumeReflected> volumes = getStorageVolumeInfo();
        for (StorageVolumeReflected volume : volumes) {
            if (!volume.isPrimary() && volume.isRemovable()) {
                String fsLable = volume.getFsLable();
                CameraLog.d(TAG, "fsLable: " + fsLable, false);
                String rootDirPath = volume.getPath();
                if (!TextUtils.isEmpty(fsLable) && fsLable.equals("XPENG_DVR_A")) {
                    dvrDir = findDVRDir(rootDirPath);
                    CameraLog.d(TAG, "dvrDir: " + dvrDir, false);
                    if (!TextUtils.isEmpty(dvrDir)) {
                        break;
                    }
                } else if (!TextUtils.isEmpty(fsLable) && fsLable.equals("oToBrite U 盘")) {
                    dvrDir = findDVRDir(rootDirPath);
                    CameraLog.d(TAG, "dvrDir: " + dvrDir, false);
                    if (!TextUtils.isEmpty(dvrDir)) {
                        break;
                    }
                } else {
                    dvrDir = findDVRDir(rootDirPath);
                    CameraLog.d(TAG, "dvrDir: " + dvrDir, false);
                    if (!TextUtils.isEmpty(dvrDir)) {
                        break;
                    }
                }
            }
        }
        return dvrDir;
    }

    private String findDVRDir(String dirPath) {
        int countMatch;
        try {
            File dir = new File(dirPath);
            countMatch = 0;
            File[] files = dir.listFiles();
            for (File file : files) {
                CameraLog.d(TAG, "file name: " + files.toString(), false);
                if (file.isDirectory() && file.getName().equals("RECORD")) {
                    countMatch++;
                } else if (file.isDirectory() && file.getName().equals("PROTECT")) {
                    countMatch++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            CameraLog.d(TAG, "findDVRDir exception: " + e.getMessage(), false);
        }
        if (countMatch == 2) {
            return dirPath;
        }
        return null;
    }

    public String getVolumeState(String path) {
        StorageManager mStorageManager = (StorageManager) App.getInstance().getSystemService("storage");
        try {
            Method mMethodGetPathsState = mStorageManager.getClass().getMethod("getVolumeState", String.class);
            String status = (String) mMethodGetPathsState.invoke(mStorageManager, path);
            return status;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
