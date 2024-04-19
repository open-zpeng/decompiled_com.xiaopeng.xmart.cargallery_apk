package com.xiaopeng.xmart.cargallery.model.transfer;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.xiaopeng.xmart.cargallery.CameraLog;
import com.xiaopeng.xmart.cargallery.bean.BaseItem;
import com.xiaopeng.xmart.cargallery.bean.TransferProgressEvent;
import com.xiaopeng.xmart.cargallery.utils.SpUtils;
import java.io.File;
import java.util.ArrayList;
/* loaded from: classes8.dex */
public class FileTransferModel {
    private static final String TAG = "FileTransferModel";
    private static final String WIFI_DEFAULT_PASSWORD = "xp123456";
    private static final String WIFI_PREFIX = "Xiaopeng_";
    private long mFileTotalLength;
    private ArrayList<BaseItem> mFiles;
    private ArrayList<String> mFilePaths = new ArrayList<>();
    private ArrayList<TransferProgressEvent> mInfoDatas = new ArrayList<>();

    public FileTransferModel(ArrayList<BaseItem> transferFiles) {
        this.mFiles = transferFiles;
        parseFiles();
    }

    public long getFileLength() {
        return this.mFileTotalLength;
    }

    public ArrayList<String> getFilePaths() {
        return this.mFilePaths;
    }

    public ArrayList<TransferProgressEvent> getInfoDatas() {
        return this.mInfoDatas;
    }

    public String generateWifiSSID(Context context) {
        String ssid = (String) SpUtils.get(context, SpUtils.SP_KEY_WIFI_SSID, "");
        if (TextUtils.isEmpty(ssid)) {
            String ssid2 = WIFI_PREFIX + String.format("%04d", Integer.valueOf((int) (Math.random() * 10000.0d)));
            SpUtils.put(context, SpUtils.SP_KEY_WIFI_SSID, ssid2);
            return ssid2;
        }
        return ssid;
    }

    public String generateRandomPw() {
        return WIFI_DEFAULT_PASSWORD;
    }

    public String getDeviceName() {
        return Build.DEVICE;
    }

    public void parseFiles() {
        for (int i = 0; i < this.mFiles.size(); i++) {
            File file = new File(this.mFiles.get(i).getPath());
            long fileLength = 0;
            if (file.exists()) {
                fileLength = file.length();
                this.mFileTotalLength += fileLength;
            }
            this.mFilePaths.add(this.mFiles.get(i).getPath());
            TransferProgressEvent event = new TransferProgressEvent(this.mFiles.get(i).getPath());
            event.setFileSize(fileLength);
            event.setDuration(this.mFiles.get(i).getDuration());
            this.mInfoDatas.add(event);
            CameraLog.d(TAG, i + ":" + this.mFiles.get(i).getPath(), false);
        }
    }
}
