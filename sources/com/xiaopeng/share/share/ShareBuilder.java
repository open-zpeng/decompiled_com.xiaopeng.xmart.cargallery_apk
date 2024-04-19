package com.xiaopeng.share.share;

import android.text.TextUtils;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.share.callback.FileLimitStrategy;
import com.xiaopeng.share.callback.OssProgressCallback;
import com.xiaopeng.share.callback.ShareCallback;
import java.util.HashMap;
import java.util.List;
/* loaded from: classes.dex */
public class ShareBuilder {
    public static final int IMG_SHARE_TYPE = 1;
    public static final int TEXT_SHARE_TYPE = 0;
    public static final int VIDEO_SHARE_TYPE = 2;
    private ShareCallback callback;
    private String content;
    private List<String> imagesPath;
    private FileLimitStrategy limitStrategy = new DefaultFileLimit();
    private HashMap ossMap = new HashMap();
    private String otp;
    private OssProgressCallback progressCallback;
    private int shareType;
    private String uid;
    private String videoCoverPath;
    private String videoCoverUrl;
    private int videoHeight;
    private String videoPath;
    private String videoUrl;
    private int videoWidth;

    private ShareBuilder(int shareType, ShareCallback callback, String otp, String uid) {
        this.otp = otp;
        this.uid = uid;
        this.shareType = shareType;
        this.callback = callback;
    }

    public static ShareBuilder create(int shareType, String otp, String uid) {
        return create(shareType, null, otp, uid);
    }

    public static ShareBuilder create(int shareType, ShareCallback callback, String otp, String uid) {
        return new ShareBuilder(shareType, callback, otp, uid);
    }

    public void takeStrategy(FileLimitStrategy strategy) {
        this.limitStrategy = strategy;
    }

    public int getShareType() {
        return this.shareType;
    }

    public String getContent() {
        return this.content;
    }

    public ShareBuilder buildContent(String content) {
        this.content = content;
        return this;
    }

    public String getVideoCoverPath() {
        return this.videoCoverPath;
    }

    public void setVideoCoverPath(String videoCoverPath) {
        this.videoCoverPath = videoCoverPath;
    }

    public ShareCallback getCallback() {
        return this.callback;
    }

    public String getVideoPath() {
        return this.videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public ShareBuilder buildVideoPath(String content) {
        ShareCallback shareCallback;
        if (this.shareType != 2 && (shareCallback = this.callback) != null) {
            shareCallback.onError(2, "Error Type!");
        }
        this.videoPath = content;
        return this;
    }

    public List<String> getImagesPath() {
        return this.imagesPath;
    }

    public ShareBuilder buildImagesPath(List<String> imagesPath) {
        ShareCallback shareCallback;
        if (this.shareType != 1 && (shareCallback = this.callback) != null) {
            shareCallback.onError(1, "Error Type!");
        }
        this.imagesPath = imagesPath;
        return this;
    }

    public FileLimitStrategy getLimitStrategy() {
        return this.limitStrategy;
    }

    public void share() {
        if (TextUtils.isEmpty(this.content)) {
            LogUtils.d("encounter problem:share content should not null!");
        } else {
            ShareProcedure.share(this);
        }
    }

    public String toString() {
        return "ShareBuilder{shareType=" + this.shareType + ", content='" + this.content + "', videoPath='" + this.videoPath + "', videoCoverPath='" + this.videoCoverPath + "', videoUrl='" + this.videoUrl + "', videoCoverUrl='" + this.videoCoverUrl + "', videoWidth=" + this.videoWidth + ", videoHeight=" + this.videoHeight + ", imagesPath=" + this.imagesPath + ", ossMap=" + this.ossMap + '}';
    }

    public HashMap<String, String> getOssMap() {
        return this.ossMap;
    }

    public void setOssMap(HashMap ossMap) {
        this.ossMap = ossMap;
    }

    public String getVideoCoverUrl() {
        return this.videoCoverUrl;
    }

    public void setVideoCoverUrl(String videoCoverUrl) {
        this.videoCoverUrl = videoCoverUrl;
    }

    public String getVideoUrl() {
        return this.videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public int getVideoWidth() {
        return this.videoWidth;
    }

    public void setVideoWidth(int videoWidth) {
        this.videoWidth = videoWidth;
    }

    public int getVideoHeight() {
        return this.videoHeight;
    }

    public void setVideoHeight(int videoHeight) {
        this.videoHeight = videoHeight;
    }

    public ShareBuilder needProgress(OssProgressCallback callback) {
        this.progressCallback = callback;
        return this;
    }

    public OssProgressCallback getProgressCallBack() {
        return this.progressCallback;
    }

    public String getUid() {
        return this.uid;
    }

    public String getOtp() {
        return this.otp;
    }
}
