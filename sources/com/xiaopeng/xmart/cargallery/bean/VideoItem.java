package com.xiaopeng.xmart.cargallery.bean;
/* loaded from: classes9.dex */
public class VideoItem extends BaseItem {
    public VideoItem(String path) {
        super("com.xiaopeng/local", path);
    }

    public VideoItem(String source, String path) {
        super(source, path);
    }

    @Override // com.xiaopeng.xmart.cargallery.bean.BaseItem
    public void setDuration(long duration) {
        this.mDuration = duration;
        if (this.mDuration <= 0) {
            this.mType = 0;
        }
    }

    @Override // com.xiaopeng.xmart.cargallery.bean.BaseItem
    public String toString() {
        return "VideoItem{mSource='" + this.mSource + "', mType=" + this.mType + ", mPath='" + this.mPath + "', mName='" + this.mName + "', mDateTaken=" + this.mDateTaken + ", mDateTakenKey=" + this.mDateTakenKey + ", mIsSelected=" + this.mIsSelected + ", mDuration=" + this.mDuration + ", mBucketId='" + this.mBucketId + "'}";
    }
}
