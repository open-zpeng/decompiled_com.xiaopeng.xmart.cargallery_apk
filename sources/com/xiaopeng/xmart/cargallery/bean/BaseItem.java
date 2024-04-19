package com.xiaopeng.xmart.cargallery.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.xiaopeng.xmart.cargallery.App;
import com.xiaopeng.xmart.cargallery.CameraLog;
import com.xiaopeng.xmart.cargallery.CarGalleryConfig;
import com.xiaopeng.xmart.cargallery.R;
import com.xiaopeng.xmart.cargallery.utils.FileUtils;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.eclipse.paho.client.mqttv3.MqttTopic;
/* loaded from: classes9.dex */
public class BaseItem implements Parcelable {
    static final String SOURCE_CATEGORY = "com.xiaopeng/category";
    private static final String SOURCE_DVR = "com.xiaopeng/dvr";
    static final String SOURCE_FOOTER = "com.xiaopeng/footer";
    static final String SOURCE_LOCAL = "com.xiaopeng/local";
    public static final int TYPE_CATEGORY = 3;
    public static final int TYPE_FOOTER = 4;
    static final int TYPE_INVALID = 0;
    public static final int TYPE_PHOTO = 1;
    public static final int TYPE_VIDEO = 2;
    private static SimpleDateFormat sSimpleDateFormat;
    int mBucketId;
    private boolean mCollision;
    long mDateTaken;
    String mDateTakenKey;
    long mDuration;
    boolean mIsSelected;
    private String mKey;
    private double mLatitude;
    private double mLongitude;
    private int mMediaId;
    String mName;
    String mPath;
    private boolean mRead;
    private long mSize;
    String mSource;
    private String mTakenPoi;
    int mType;
    private static final String TAG = BaseItem.class.getSimpleName();
    public static final Parcelable.Creator<BaseItem> CREATOR = new Parcelable.Creator<BaseItem>() { // from class: com.xiaopeng.xmart.cargallery.bean.BaseItem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BaseItem createFromParcel(Parcel source) {
            return new BaseItem(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BaseItem[] newArray(int size) {
            return new BaseItem[size];
        }
    };

    public BaseItem(String source, String path) {
        this.mDateTaken = 0L;
        this.mDateTakenKey = "";
        this.mDuration = 0L;
        this.mCollision = false;
        this.mPath = path;
        this.mSource = source;
        if (path == null || !path.contains(".")) {
            return;
        }
        this.mName = path.substring(path.lastIndexOf(MqttTopic.TOPIC_LEVEL_SEPARATOR) + 1);
        String postfix = path.substring(path.lastIndexOf("."));
        if (postfix.equalsIgnoreCase(".jpg")) {
            this.mType = 1;
        } else if (postfix.equalsIgnoreCase(".3gp") || postfix.equalsIgnoreCase(FileUtils.EXTEND_MP4) || postfix.equalsIgnoreCase(".mkv")) {
            this.mType = 2;
        }
        initDateTakenFromName();
    }

    public int getMediaId() {
        return this.mMediaId;
    }

    public void setMediaId(int mediaId) {
        this.mMediaId = mediaId;
    }

    public int getType() {
        return this.mType;
    }

    public void setType(int type) {
        this.mType = type;
    }

    public String getPath() {
        return this.mPath;
    }

    public void setPath(String path) {
        this.mPath = path;
    }

    public String getName() {
        return this.mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getSource() {
        return this.mSource;
    }

    public long getSize() {
        return this.mSize;
    }

    public void setSize(long size) {
        this.mSize = size;
    }

    public double getLongitude() {
        return this.mLongitude;
    }

    public void setLongitude(double longitude) {
        this.mLongitude = longitude;
    }

    public double getLatitude() {
        return this.mLatitude;
    }

    public void setLatitude(double latitude) {
        this.mLatitude = latitude;
    }

    public int getBucketId() {
        return this.mBucketId;
    }

    public void setBucketId(int bucketId) {
        this.mBucketId = bucketId;
    }

    public String getTakenPoi() {
        return this.mTakenPoi;
    }

    public void setTakenPoi(String takenPoi) {
        this.mTakenPoi = takenPoi;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setDateTaken(long dateTaken) {
        if (dateTaken <= 0) {
            initDateTakenFromName();
            return;
        }
        this.mDateTaken = dateTaken;
        this.mDateTakenKey = convertEpochToDate(dateTaken);
    }

    public long getDateTaken() {
        return this.mDateTaken;
    }

    public String getDateTakenKey() {
        return this.mDateTakenKey;
    }

    public long getDuration() {
        return this.mDuration;
    }

    public void setDuration(long duration) {
    }

    public void setKey(String key) {
        this.mKey = key;
    }

    public String getKey() {
        return this.mKey;
    }

    public boolean isSelected() {
        return this.mIsSelected;
    }

    public void setSelected(boolean isSelected) {
        if (isValid()) {
            this.mIsSelected = isSelected;
        }
    }

    public boolean isCollision() {
        return this.mCollision;
    }

    public void setIsCollision(boolean isCollision) {
        this.mCollision = isCollision;
    }

    public boolean isRead() {
        return this.mRead;
    }

    public void setRead(boolean read) {
        this.mRead = read;
    }

    public boolean isValid() {
        int i = this.mType;
        return i == 2 || i == 1;
    }

    public boolean isDvrItem() {
        return SOURCE_DVR.equals(this.mSource);
    }

    private void initDateTakenFromName() {
        long dateTaken;
        String timeStr;
        long dateTaken2;
        if (SOURCE_LOCAL.equals(this.mSource)) {
            try {
                if (this.mCollision) {
                    String str = this.mPath;
                    timeStr = str.substring(str.lastIndexOf(MqttTopic.TOPIC_LEVEL_SEPARATOR) + 1, this.mPath.lastIndexOf(".")).replace(CarGalleryConfig.SENTRY_MODE_COLLISION_SUFFIX, "");
                } else {
                    String timeStr2 = this.mPath;
                    timeStr = timeStr2.substring(timeStr2.lastIndexOf(MqttTopic.TOPIC_LEVEL_SEPARATOR) + 1, this.mPath.lastIndexOf("."));
                }
                dateTaken = Long.parseLong(timeStr);
            } catch (Exception e) {
                File file = new File(this.mPath);
                dateTaken = file.lastModified();
            }
            this.mDateTaken = dateTaken;
            this.mDateTakenKey = convertEpochToDate(dateTaken);
        } else if (SOURCE_DVR.equals(this.mSource)) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
                String str2 = this.mPath;
                dateTaken2 = sdf.parse(str2.substring(str2.lastIndexOf(MqttTopic.TOPIC_LEVEL_SEPARATOR) + 1, this.mPath.lastIndexOf("."))).getTime();
            } catch (Exception e2) {
                File file2 = new File(this.mPath);
                dateTaken2 = file2.lastModified();
            }
            this.mDateTaken = dateTaken2;
            this.mDateTakenKey = convertEpochToDate(dateTaken2);
        }
    }

    private static String convertEpochToDate(long dateTaken) {
        if (sSimpleDateFormat == null) {
            sSimpleDateFormat = new SimpleDateFormat(App.getInstance().getString(R.string.simple_date_format), Locale.getDefault());
        }
        return sSimpleDateFormat.format(new Date(dateTaken));
    }

    public static BaseItem createItem(String itemPath) {
        BaseItem item;
        CameraLog.d(TAG, "createItem itemPath=" + itemPath);
        if (itemPath.endsWith(FileUtils.EXTEND_MP4)) {
            item = new VideoItem(itemPath);
        } else {
            item = new PhotoItem(itemPath);
        }
        File file = new File(itemPath);
        item.setSize(file.length());
        if (itemPath.contains("360Camera")) {
            item.setKey("360Camera");
            item.setBucketId(FileUtils.BUCKET_ID_360);
        } else if (itemPath.contains("shockCamera")) {
            item.setKey("shockCamera");
            item.setBucketId(FileUtils.BUCKET_ID_SHOCK);
        } else if (itemPath.contains("FrontCamera")) {
            item.setKey("FrontCamera");
            item.setBucketId(FileUtils.BUCKET_ID_FRONT);
        } else if (itemPath.contains("camera_top")) {
            item.setKey("camera_top");
            item.setBucketId(FileUtils.BUCKET_ID_TOP);
        }
        return item;
    }

    public static BaseItem createDVRItem(String itemPath) {
        BaseItem item;
        CameraLog.d(TAG, "createItem itemPath=" + itemPath);
        if (itemPath.endsWith(FileUtils.EXTEND_MP4)) {
            item = new VideoItem(SOURCE_DVR, itemPath);
        } else {
            item = new PhotoItem(SOURCE_DVR, itemPath);
        }
        File file = new File(itemPath);
        item.setSize(file.length());
        item.setKey("camera_dvr");
        item.setBucketId(FileUtils.BUCKET_ID_DVR);
        return item;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        BaseItem baseItem = (BaseItem) o;
        return TextUtils.equals(this.mPath, baseItem.mPath);
    }

    public String toString() {
        return "BaseItem{mMediaId=" + this.mMediaId + ", mSource='" + this.mSource + "', mType=" + this.mType + ", mPath='" + this.mPath + "', mName='" + this.mName + "', mKey='" + this.mKey + "', mSize=" + this.mSize + ", mLongitude=" + this.mLongitude + ", mLatitude=" + this.mLatitude + ", mTakenPoi='" + this.mTakenPoi + "', mDateTaken=" + this.mDateTaken + ", mDateTakenKey='" + this.mDateTakenKey + "', mDuration=" + this.mDuration + ", mIsSelected=" + this.mIsSelected + ", mCollision=" + this.mCollision + '}';
    }

    public BaseItem() {
        this.mDateTaken = 0L;
        this.mDateTakenKey = "";
        this.mDuration = 0L;
        this.mCollision = false;
    }

    protected BaseItem(Parcel in) {
        this.mDateTaken = 0L;
        this.mDateTakenKey = "";
        this.mDuration = 0L;
        this.mCollision = false;
        this.mMediaId = in.readInt();
        this.mSource = in.readString();
        this.mType = in.readInt();
        this.mPath = in.readString();
        this.mName = in.readString();
        this.mKey = in.readString();
        this.mSize = in.readLong();
        this.mLongitude = in.readDouble();
        this.mLatitude = in.readDouble();
        this.mTakenPoi = in.readString();
        this.mDateTaken = in.readLong();
        this.mDateTakenKey = in.readString();
        this.mDuration = in.readLong();
        this.mIsSelected = in.readByte() != 0;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mMediaId);
        dest.writeString(this.mSource);
        dest.writeInt(this.mType);
        dest.writeString(this.mPath);
        dest.writeString(this.mName);
        dest.writeString(this.mKey);
        dest.writeLong(this.mSize);
        dest.writeDouble(this.mLongitude);
        dest.writeDouble(this.mLatitude);
        dest.writeString(this.mTakenPoi);
        dest.writeLong(this.mDateTaken);
        dest.writeString(this.mDateTakenKey);
        dest.writeLong(this.mDuration);
        dest.writeByte(this.mIsSelected ? (byte) 1 : (byte) 0);
    }
}
