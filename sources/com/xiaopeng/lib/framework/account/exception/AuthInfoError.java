package com.xiaopeng.lib.framework.account.exception;

import com.xiaopeng.lib.framework.moduleinterface.accountmodule.IError;
/* loaded from: classes.dex */
public class AuthInfoError implements IError {
    public static final int ERROR_CODE_AUTH_DENIED = 10002;
    private static final String ERROR_CODE_AUTH_DENIED_DES = "用户拒绝授权";
    public static final int ERROR_CODE_AUTH_REQUEST_FAIL = 10005;
    private static final String ERROR_CODE_AUTH_REQUEST_FAIL_DES = "网络请求失败";
    public static final int ERROR_CODE_AUTH_TIMEOUT = 10004;
    private static final String ERROR_CODE_AUTH_TIMEOUT_DES = "用户授权超时";
    public static final int ERROR_CODE_CAR_NOT_STOPPED = 20001;
    private static final String ERROR_CODE_CAR_NOT_STOPPED_DES = "车辆行驶中，暂不能进行该操作";
    public static final int ERROR_CODE_REDUNDANT_AUTH_REQUEST = 3;
    private static final String ERROR_CODE_REDUNDANT_AUTH_REQUEST_STR = "授权请求中，请稍候";
    public static final int ERROR_CODE_SERVER_DISCONNECTION = 1;
    private static final String ERROR_CODE_SERVER_DISCONNECTION_STR = "账号服务已断开连接";
    public static final int ERROR_CODE_SERVER_UNBIND = 2;
    private static final String ERROR_CODE_SERVER_UNBIND_STR = "账号服务已取消绑定";
    public static final int ERROR_CODE_SERVER_UNKNOWN = 0;
    private static final String ERROR_CODE_SERVER_UNKNOWN_STR = "账号服务未知错误";
    public static final int ERROR_CODE_USER_CANCEL = 10001;
    private static final String ERROR_CODE_USER_CANCEL_DES = "用户取消授权";
    public static final int ERROR_CODE_USER_CLOSE_QRCODE = 20002;
    private static final String ERROR_CODE_USER_CLOSE_QRCODE_DES = "操作已取消";
    public static final int ERROR_CODE_USER_LOGOUT = 10003;
    private static final String ERROR_CODE_USER_LOGOUT_DES = "用户未登录";
    private int mCode;
    private String mErr;

    public AuthInfoError(int code) {
        this.mCode = code;
    }

    public void setErr(String err) {
        this.mErr = err;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.accountmodule.IError
    public String getMessage() {
        switch (getCode()) {
            case 0:
                return "账号服务未知错误";
            case 1:
                return "账号服务已断开连接";
            case 2:
                return ERROR_CODE_SERVER_UNBIND_STR;
            case 3:
                return ERROR_CODE_REDUNDANT_AUTH_REQUEST_STR;
            case 10001:
                return ERROR_CODE_USER_CANCEL_DES;
            case ERROR_CODE_AUTH_DENIED /* 10002 */:
                return ERROR_CODE_AUTH_DENIED_DES;
            case ERROR_CODE_USER_LOGOUT /* 10003 */:
                return "用户未登录";
            case ERROR_CODE_AUTH_TIMEOUT /* 10004 */:
                return ERROR_CODE_AUTH_TIMEOUT_DES;
            case ERROR_CODE_AUTH_REQUEST_FAIL /* 10005 */:
                return "网络请求失败";
            case 20001:
                return ERROR_CODE_CAR_NOT_STOPPED_DES;
            case 20002:
                return ERROR_CODE_USER_CLOSE_QRCODE_DES;
            default:
                return "账号服务未知错误";
        }
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.accountmodule.IError
    public int getCode() {
        return this.mCode;
    }

    public String toString() {
        String result = "AuthInfoError;{ code=" + getCode() + " message=" + getMessage() + " err=" + this.mErr + " }";
        return result;
    }
}
