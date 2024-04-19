package com.xiaopeng.xmart.cargallery.utils;

import android.app.usage.StorageStatsManager;
import android.content.Context;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.os.storage.VolumeInfo;
import com.xiaopeng.xmart.cargallery.CameraLog;
import com.xiaopeng.xmart.cargallery.CarGalleryConfig;
import com.xiaopeng.xmart.cargallery.bean.BaseItem;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.eclipse.paho.client.mqttv3.MqttTopic;
/* loaded from: classes3.dex */
public class FileUtils {
    public static final String BASE_DATA_PATH;
    public static final String BASE_PATH;
    public static final int BUCKET_ID_360;
    public static final int BUCKET_ID_DVR;
    public static final int BUCKET_ID_FRONT;
    public static final int BUCKET_ID_SHOCK;
    public static final int BUCKET_ID_TOP;
    public static final String DIR_360 = "360Camera";
    public static final String DIR_360_FULL_PATH;
    public static final String DIR_DVR = "camera_dvr";
    public static final String DIR_FRONT = "FrontCamera";
    public static final String DIR_FRONT_FULL_PATH;
    public static final String DIR_SHOCK = "shockCamera";
    public static final String DIR_SHOCK_CACHE = ".cache";
    public static final String DIR_SHOCK_CACHE_FULL_PATH;
    public static final String DIR_SHOCK_FULL_PATH;
    public static final String DIR_TOP = "camera_top";
    public static final String DIR_TOP_FULL_PATH;
    public static final String EXTEND_MP4 = ".mp4";
    public static final String SPEECH_INPUT_DIR = "SpeechInput";
    public static final String SPEECH_INPUT_PATH;
    public static final String TAG = "FileUtils";

    static {
        String path = Environment.getExternalStorageDirectory().getPath();
        BASE_DATA_PATH = path;
        String path2 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath();
        BASE_PATH = path2;
        String str = path2 + File.separator + "360Camera";
        DIR_360_FULL_PATH = str;
        BUCKET_ID_360 = str.toLowerCase().hashCode();
        String str2 = path2 + File.separator + "shockCamera";
        DIR_SHOCK_FULL_PATH = str2;
        BUCKET_ID_SHOCK = str2.toLowerCase().hashCode();
        DIR_SHOCK_CACHE_FULL_PATH = str2 + File.separator + DIR_SHOCK_CACHE;
        String str3 = path2 + File.separator + "FrontCamera";
        DIR_FRONT_FULL_PATH = str3;
        BUCKET_ID_FRONT = str3.toLowerCase().hashCode();
        SPEECH_INPUT_PATH = path + File.separator + SPEECH_INPUT_DIR;
        String str4 = path2 + File.separator + "camera_top";
        DIR_TOP_FULL_PATH = str4;
        BUCKET_ID_TOP = str4.toLowerCase().hashCode();
        BUCKET_ID_DVR = "DVR".toLowerCase().hashCode();
    }

    public static long[] getStorageUsageInfo(Context context) {
        long[] result = new long[2];
        long privateFreeBytes = 0;
        long privateTotalBytes = getTotalBytes();
        StorageManager storageManager = (StorageManager) context.getSystemService("storage");
        StorageStatsManager storageStatsManager = (StorageStatsManager) context.getSystemService("storagestats");
        for (VolumeInfo info : storageManager.getVolumes()) {
            if (info.getType() == 1 && info.isMountedReadable()) {
                try {
                    long freeBytes = getFreeBytes(storageStatsManager, info);
                    CameraLog.d(TAG, "info name:" + info.getPath(), false);
                    CameraLog.d(TAG, "Free:" + freeBytes);
                    privateFreeBytes += freeBytes;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        CameraLog.d(TAG, "TotalFree:" + privateFreeBytes + "   TotalAvailable:" + privateTotalBytes + "     Percent:" + ((int) ((100 * privateFreeBytes) / privateTotalBytes)) + "%", false);
        result[0] = privateTotalBytes;
        result[1] = privateTotalBytes - privateFreeBytes;
        return result;
    }

    private static long getTotalBytes() {
        return Environment.getDataDirectory().getTotalSpace() + Environment.getRootDirectory().getTotalSpace();
    }

    private static long getTotalBytes(StorageStatsManager stats, VolumeInfo volume) throws IOException {
        return stats.getTotalBytes(StorageManager.convert(volume.getFsUuid()));
    }

    private static long getFreeBytes(StorageStatsManager stats, VolumeInfo volume) throws IOException {
        return stats.getFreeBytes(StorageManager.convert(volume.getFsUuid()));
    }

    public static String getFileName(String filePath) {
        if (filePath == null || filePath.equals("")) {
            return "";
        }
        return filePath.substring(filePath.lastIndexOf(MqttTopic.TOPIC_LEVEL_SEPARATOR) + 1);
    }

    public static boolean isMp4File(String filePath) {
        return (filePath == null || filePath.equals("") || filePath.lastIndexOf(EXTEND_MP4) <= 0) ? false : true;
    }

    public static String byteToStr(byte[] buffer) {
        int length = 0;
        int i = 0;
        while (true) {
            try {
                if (i >= buffer.length) {
                    break;
                } else if (buffer[i] != 0) {
                    i++;
                } else {
                    length = i;
                    break;
                }
            } catch (Exception e) {
                return "";
            }
        }
        return new String(buffer, 0, length, StandardCharsets.UTF_8);
    }

    public static String convertFileSize(long size) {
        long mb = 1024 * 1024;
        long gb = 1024 * mb;
        if (size >= gb) {
            return String.format("%.1f GB", Float.valueOf(((float) size) / ((float) gb)));
        }
        if (size >= mb) {
            float f = ((float) size) / ((float) mb);
            return String.format(f > 100.0f ? "%.0f MB" : "%.1f MB", Float.valueOf(f));
        } else if (size >= 1024) {
            float f2 = ((float) size) / ((float) 1024);
            return String.format(f2 > 100.0f ? "%.0f KB" : "%.1f KB", Float.valueOf(f2));
        } else {
            return String.format("%d B", Long.valueOf(size));
        }
    }

    public static String getStoreRecordPath() {
        String dir = SPEECH_INPUT_PATH;
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
        return dir;
    }

    public static boolean isExitShockFile(List<BaseItem> list) {
        if (list == null || list.isEmpty()) {
            return false;
        }
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getPath().contains(CarGalleryConfig.SENTRY_MODE_COLLISION_SUFFIX)) {
                return true;
            }
        }
        return false;
    }

    public static void creatNoMediaFileForDVR(String dir) {
        try {
            File noMedia = new File(dir + File.separator + ".nomedia");
            boolean exists = noMedia.exists();
            if (!exists) {
                noMedia.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
