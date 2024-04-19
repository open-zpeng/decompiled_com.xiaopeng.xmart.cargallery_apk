package com.xiaopeng.lib.framework.account.info;

import android.os.Parcel;
import android.os.Parcelable;
import com.xiaopeng.lib.framework.account.utils.L;
import com.xiaopeng.lib.framework.moduleinterface.accountmodule.IUserInfo;
/* loaded from: classes.dex */
public class UserInfoImpl implements IUserInfo, Parcelable {
    public static final Parcelable.Creator<UserInfoImpl> CREATOR = new Parcelable.Creator<UserInfoImpl>() { // from class: com.xiaopeng.lib.framework.account.info.UserInfoImpl.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UserInfoImpl createFromParcel(Parcel in) {
            return new UserInfoImpl(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UserInfoImpl[] newArray(int size) {
            return new UserInfoImpl[size];
        }
    };
    public static final int GRADE_DRIVER = 4;
    public static final int GRADE_OWNER = 1;
    public static final int GRADE_TEMP = -1;
    public static final int GRADE_TENANT = 3;
    public static final int GRADE_USER = 2;
    private static final String TAG = "UserInfoImpl";
    private String mAvatar;
    private String mUserName;
    private IUserInfo.UserType mUserType = IUserInfo.UserType.TEMP;
    private IUserInfo.InfoType mInfoType = IUserInfo.InfoType.UPDATE;

    public UserInfoImpl() {
    }

    public UserInfoImpl(Parcel in) {
        readFromParcel(in);
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.accountmodule.IUserInfo
    public IUserInfo setUserName(String name) {
        this.mUserName = name;
        return this;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.accountmodule.IUserInfo
    public String getUserName() {
        return this.mUserName;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.accountmodule.IUserInfo
    public IUserInfo.UserType getUserType() {
        return this.mUserType;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.accountmodule.IUserInfo
    public IUserInfo setUserType(IUserInfo.UserType type) {
        this.mUserType = type;
        return this;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.accountmodule.IUserInfo
    public IUserInfo setUserType(int type) {
        switch (type) {
            case -1:
                this.mUserType = IUserInfo.UserType.TEMP;
                break;
            case 0:
            default:
                this.mUserType = IUserInfo.UserType.TEMP;
                break;
            case 1:
                this.mUserType = IUserInfo.UserType.OWNER;
                break;
            case 2:
                this.mUserType = IUserInfo.UserType.USER;
                break;
            case 3:
                this.mUserType = IUserInfo.UserType.TENANT;
                break;
            case 4:
                this.mUserType = IUserInfo.UserType.DRIVER;
                break;
        }
        return this;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.accountmodule.IUserInfo
    public String getAvatar() {
        return this.mAvatar;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.accountmodule.IUserInfo
    public IUserInfo setAvatar(String url) {
        this.mAvatar = url;
        return this;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.accountmodule.IUserInfo
    public IUserInfo.InfoType getInfoType() {
        return this.mInfoType;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.accountmodule.IUserInfo
    public IUserInfo setInfoType(IUserInfo.InfoType type) {
        this.mInfoType = type;
        return this;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void readFromParcel(Parcel in) {
        this.mUserName = in.readString();
        int userType = in.readInt();
        this.mAvatar = in.readString();
        int infoType = in.readInt();
        L.v(TAG, "UserInfoImpl type=" + userType);
        if (userType >= 0 && userType < IUserInfo.UserType.values().length) {
            this.mUserType = IUserInfo.UserType.values()[userType];
        } else {
            L.v(TAG, "UserInfoImpl mUserType is ArrayIndexOutBoundsException");
        }
        if (infoType >= 0 && infoType < IUserInfo.InfoType.values().length) {
            this.mInfoType = IUserInfo.InfoType.values()[infoType];
        } else {
            L.v(TAG, "UserInfoImpl mUserType is ArrayIndexOutBoundsException");
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        L.v(TAG, "writeToParcel mUserType=" + this.mUserType.ordinal() + ";mInfoType=" + this.mInfoType.ordinal());
        parcel.writeString(this.mUserName);
        parcel.writeInt(this.mUserType.ordinal());
        parcel.writeString(this.mAvatar);
        parcel.writeInt(this.mInfoType.ordinal());
    }

    public String toString() {
        String result = "UserInfoImpl;{ mUserName=" + this.mUserName + " mUserType=" + this.mUserType + " mAvatar=" + this.mAvatar + " mInfoType=" + this.mInfoType + " }";
        return result;
    }
}
