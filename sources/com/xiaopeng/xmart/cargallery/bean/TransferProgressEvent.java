package com.xiaopeng.xmart.cargallery.bean;

import android.graphics.Bitmap;
/* loaded from: classes9.dex */
public class TransferProgressEvent extends FileHeadInfo {
    public static final int TRANSFER_ALL_COMPLETE = 4;
    public static final int TRANSFER_STAGE_END = 2;
    public static final int TRANSFER_STAGE_ERROR = 3;
    public static final int TRANSFER_STAGE_NORMAL = 1;
    public static final int TRANSFER_STAGE_START = 0;
    private Bitmap bitmap;
    private long progress;
    private int stage;

    public TransferProgressEvent(String path) {
        setFileName(path);
    }

    public TransferProgressEvent(int stage) {
        this.stage = stage;
    }

    public TransferProgressEvent() {
    }

    public Bitmap getBitmap() {
        return this.bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getStage() {
        return this.stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public long getProgress() {
        return this.progress;
    }

    public void setProgress(long progress) {
        this.progress = progress;
    }

    public String toString() {
        return "TransferProgressEvent{progress=" + this.progress + "fileName=" + getFileName() + ", fileSize=" + getFileSize() + ", bitmap=" + this.bitmap + '}';
    }
}
