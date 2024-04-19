package com.xiaopeng.xmart.cargallery.bean;
/* loaded from: classes9.dex */
public class PhotoItem extends BaseItem {
    public PhotoItem(String path) {
        super("com.xiaopeng/local", path);
    }

    public PhotoItem(String source, String path) {
        super(source, path);
    }

    @Override // com.xiaopeng.xmart.cargallery.bean.BaseItem
    public String toString() {
        return "PhotoItem{mSource='" + this.mSource + "', mType=" + this.mType + ", mPath='" + this.mPath + "', mName='" + this.mName + "', mDateTaken=" + this.mDateTaken + ", mDateTakenKey=" + this.mDateTakenKey + ", mIsSelected=" + this.mIsSelected + ", mBucketId='" + this.mBucketId + "'}";
    }
}
