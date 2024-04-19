package com.xiaopeng.share.task;

import android.text.TextUtils;
import android.util.Log;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.Callback;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.StorageException;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.share.ComponentManager;
import com.xiaopeng.share.callback.OssProgressCallback;
import com.xiaopeng.share.callback.ShareCallback;
import com.xiaopeng.share.share.ShareBuilder;
import com.xiaopeng.share.share.ShareProcedure;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.eclipse.paho.client.mqttv3.MqttTopic;
/* loaded from: classes.dex */
public class OssUploadProcess extends ShareProcess {
    private static final String OSS_DOMAIN = "http://" + ShareProcedure.BUCKET_NAME + ".oss-cn-hangzhou.aliyuncs.com";
    private static final String TAG = "OssUploadProcess";
    private static final String VIDEO_COVER_DOMAIN = "https://s01.xiaopeng.com";
    private static final String VIDEO_DOMAIN = "https://s02.xiaopeng.com";
    private OssProgressCallback progressCallback;
    private ShareBuilder shareBuilder;
    private boolean sucResult = true;
    private List<String> imgs = new ArrayList();

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.xiaopeng.share.task.ShareProcess
    public boolean process(ShareBuilder shareBuilder) {
        this.progressCallback = shareBuilder.getProgressCallBack();
        this.shareBuilder = shareBuilder;
        switch (shareBuilder.getShareType()) {
            case 0:
                return new PublishProcess().process(shareBuilder);
            case 1:
                this.imgs.addAll(shareBuilder.getImagesPath());
                uploadOssImageFile(shareBuilder);
                return true;
            case 2:
                uploadOssVideoCoverFile(shareBuilder);
                return true;
            default:
                return true;
        }
    }

    private boolean uploadOssVideoCoverFile(final ShareBuilder shareBuilder) {
        String videoCoverPath = shareBuilder.getVideoCoverPath();
        File file = new File(videoCoverPath);
        if (file.exists()) {
            String objectKey = System.currentTimeMillis() + parseFileName(videoCoverPath);
            try {
                ComponentManager.getInstance().getRemoteStorage().uploadWithPathAndCallback(ShareProcedure.BUCKET_NAME, objectKey, videoCoverPath, new Callback() { // from class: com.xiaopeng.share.task.OssUploadProcess.1
                    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.Callback
                    public void onStart(String s, String s1) {
                        Log.d(OssUploadProcess.TAG, "oss start... " + s);
                    }

                    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.Callback
                    public void onSuccess(String s, String s1) {
                        shareBuilder.setVideoCoverUrl(s.replace(OssUploadProcess.OSS_DOMAIN, OssUploadProcess.VIDEO_COVER_DOMAIN));
                        OssUploadProcess.this.uploadOssVideoFile(shareBuilder);
                    }

                    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.Callback
                    public void onFailure(String s, String s1, StorageException e) {
                        Log.d(OssUploadProcess.TAG, "oss onFailure... " + s + e.getReasonCode());
                        OssUploadProcess.this.onError(49, ShareCallback.OSS_UPLOAD_FAILURE_MESSAGE, shareBuilder);
                    }
                });
                return false;
            } catch (Exception e) {
                onThrowable(e, shareBuilder);
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void uploadOssVideoFile(final ShareBuilder shareBuilder) {
        String videoPath = shareBuilder.getVideoPath();
        String objectKey = System.currentTimeMillis() + parseFileName(videoPath);
        try {
            ComponentManager.getInstance().getRemoteStorage().uploadWithPathAndCallback(ShareProcedure.BUCKET_NAME, objectKey, videoPath, new Callback() { // from class: com.xiaopeng.share.task.OssUploadProcess.2
                @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.Callback
                public void onStart(String s, String s1) {
                    Log.d(OssUploadProcess.TAG, "oss start... " + s);
                }

                @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.Callback
                public void onSuccess(String s, String s1) {
                    shareBuilder.setVideoUrl(s.replace(OssUploadProcess.OSS_DOMAIN, OssUploadProcess.VIDEO_DOMAIN));
                    new PublishProcess().process(shareBuilder);
                }

                @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.Callback
                public void onFailure(String s, String s1, StorageException e) {
                    Log.d(OssUploadProcess.TAG, "oss onFailure... " + s + e.getReasonCode());
                    OssUploadProcess.this.onError(49, ShareCallback.OSS_UPLOAD_FAILURE_MESSAGE, shareBuilder);
                }
            });
        } catch (Exception e) {
            onThrowable(e, shareBuilder);
            e.printStackTrace();
        }
    }

    private void uploadOssImageFile(ShareBuilder shareBuilder) {
        List<String> imgs = new ArrayList<>();
        imgs.addAll(shareBuilder.getImagesPath());
        LinkedHashMap<String, String> result = new LinkedHashMap<>();
        List<String> imgs2 = shareBuilder.getImagesPath();
        upLoadFileMulSync(imgs2, result);
    }

    void upLoadFileMulSync(final List<String> uploadFilePath, final LinkedHashMap<String, String> result) {
        if (uploadFilePath == null || uploadFilePath.size() == 0) {
            LogUtils.d(TAG, "image file upload over result" + result);
            for (int i = 0; i < this.imgs.size(); i++) {
                if (TextUtils.isEmpty(result.get(this.imgs.get(i)))) {
                    this.sucResult = false;
                }
            }
            if (this.sucResult) {
                this.shareBuilder.setOssMap(result);
            }
            new PublishProcess().process(this.shareBuilder);
            return;
        }
        String currentUpload = uploadFilePath.get(0);
        String objectKey = System.currentTimeMillis() + parseFileName(currentUpload);
        try {
            ComponentManager.getInstance().getRemoteStorage().uploadWithPathAndCallback(ShareProcedure.BUCKET_NAME, objectKey, currentUpload, new Callback() { // from class: com.xiaopeng.share.task.OssUploadProcess.3
                @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.Callback
                public void onStart(String s, String s1) {
                    Log.d(OssUploadProcess.TAG, "oss start... " + s);
                }

                @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.Callback
                public void onSuccess(String remote, String local) {
                    uploadFilePath.remove(0);
                    result.put(local, remote);
                    if (OssUploadProcess.this.progressCallback != null) {
                        OssUploadProcess.this.progressCallback.ossProgress(result.size(), uploadFilePath.size() + result.size(), local);
                    }
                    OssUploadProcess.this.upLoadFileMulSync(uploadFilePath, result);
                }

                @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.Callback
                public void onFailure(String s, String s1, StorageException e) {
                    Log.d(OssUploadProcess.TAG, "oss onFailure... " + s + e.getReasonCode());
                    OssUploadProcess ossUploadProcess = OssUploadProcess.this;
                    ossUploadProcess.onError(49, ShareCallback.OSS_UPLOAD_FAILURE_MESSAGE, ossUploadProcess.shareBuilder);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            onThrowable(e, this.shareBuilder);
        }
    }

    private String parseFileName(String fullPath) {
        if (!fullPath.contains(MqttTopic.TOPIC_LEVEL_SEPARATOR)) {
            return fullPath;
        }
        String[] split = fullPath.split(MqttTopic.TOPIC_LEVEL_SEPARATOR);
        return split[split.length - 1];
    }
}
