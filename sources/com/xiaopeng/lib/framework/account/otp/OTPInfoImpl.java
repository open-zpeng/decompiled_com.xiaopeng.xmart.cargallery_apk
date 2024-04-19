package com.xiaopeng.lib.framework.account.otp;

import android.os.Parcel;
import android.os.Parcelable;
import com.xiaopeng.lib.framework.moduleinterface.accountmodule.IOTPInfo;
/* loaded from: classes.dex */
public class OTPInfoImpl implements IOTPInfo, Parcelable {
    public static final Parcelable.Creator<OTPInfoImpl> CREATOR = new Parcelable.Creator<OTPInfoImpl>() { // from class: com.xiaopeng.lib.framework.account.otp.OTPInfoImpl.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public OTPInfoImpl createFromParcel(Parcel in) {
            return new OTPInfoImpl(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public OTPInfoImpl[] newArray(int size) {
            return new OTPInfoImpl[size];
        }
    };
    private long mUid = 0;
    private String mOTP = null;

    public OTPInfoImpl() {
    }

    protected OTPInfoImpl(Parcel in) {
        readFromParcel(in);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.mUid);
        dest.writeString(this.mOTP);
    }

    private void readFromParcel(Parcel in) {
        this.mUid = in.readLong();
        this.mOTP = in.readString();
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.accountmodule.IOTPInfo
    public long getUid() {
        return this.mUid;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.accountmodule.IOTPInfo
    public String getOTP() {
        return this.mOTP;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.accountmodule.IOTPInfo
    public void setUid(long uid) {
        this.mUid = uid;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.accountmodule.IOTPInfo
    public void setOTP(String otp) {
        this.mOTP = otp;
    }

    public String toString() {
        return "OTPInfoImpl { uid=" + this.mUid + "; OTP=" + this.mOTP + "; }";
    }
}
