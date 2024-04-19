package com.xiaopeng.xmart.cargallery.bean;
/* loaded from: classes9.dex */
public class FileHeadInfo {
    private String duration;
    private String fileName;
    private long fileSize;

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFileSize() {
        return this.fileSize;
    }

    public String getDuration() {
        return this.duration;
    }

    public void setDuration(long duration) {
        this.duration = timeParse(duration);
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    private String timeParse(long duration) {
        int second = (int) (duration / 1000);
        int hour = second / 3600;
        int minute = (second % 3600) / 60;
        int ss = second % 60;
        if (hour != 0) {
            String time = String.format("%02d:%02d:%02d", Integer.valueOf(hour), Integer.valueOf(minute), Integer.valueOf(ss));
            return time;
        }
        String time2 = String.format("%02d:%02d", Integer.valueOf(minute), Integer.valueOf(ss));
        return time2;
    }
}
