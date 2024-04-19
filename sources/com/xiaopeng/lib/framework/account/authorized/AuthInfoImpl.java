package com.xiaopeng.lib.framework.account.authorized;

import android.os.Parcel;
import android.os.Parcelable;
import com.xiaopeng.lib.framework.moduleinterface.accountmodule.IAuthInfo;
import org.json.JSONException;
import org.json.JSONObject;
/* loaded from: classes.dex */
public class AuthInfoImpl implements IAuthInfo, Parcelable {
    public static final Parcelable.Creator<AuthInfoImpl> CREATOR = new Parcelable.Creator<AuthInfoImpl>() { // from class: com.xiaopeng.lib.framework.account.authorized.AuthInfoImpl.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AuthInfoImpl createFromParcel(Parcel in) {
            return new AuthInfoImpl(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AuthInfoImpl[] newArray(int size) {
            return new AuthInfoImpl[size];
        }
    };
    private String authCode;

    public AuthInfoImpl() {
    }

    public AuthInfoImpl(JSONObject jsonObject) throws JSONException {
        this.authCode = jsonObject.getString("code");
    }

    public AuthInfoImpl(Parcel in) {
        readFromParcel(in);
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.accountmodule.IAuthInfo
    public String getAuthCode() {
        return this.authCode;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.accountmodule.IAuthInfo
    public void setAuthCode(String code) {
        this.authCode = code;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void readFromParcel(Parcel in) {
        this.authCode = in.readString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.authCode);
    }

    public String toString() {
        String result = "AuthInfoImpl;{ authCode=" + this.authCode + " }";
        return result;
    }
}
