package com.xiaopeng.share.callback;
/* loaded from: classes.dex */
public interface ShareCallback extends ICallBack {
    public static final int BUILD_IMAGE_ERROR_CODE = 1;
    public static final String BUILD_IMAGE_ERROR_MESSAGE = "Error Type!";
    public static final int BUILD_USER_ERROR_CODE = 0;
    public static final String BUILD_USER_ERROR_MESSAGE = "need otp and user id!";
    public static final int BUILD_VIDEO_ERROR_CODE = 2;
    public static final String BUILD_VIDEO_ERROR_MESSAGE = "Error Type!";
    public static final int OSS_UPLOAD_FAILURE_CODE = 49;
    public static final String OSS_UPLOAD_FAILURE_MESSAGE = "Oss upload failure!";
    public static final int PRE_CHECK_DURATION_TOO_LARGE_CODE = 19;
    public static final String PRE_CHECK_DURATION_TOO_LARGE_MESSAGE = "Duration too large!";
    public static final int PRE_CHECK_FILE_NOT_EXIT_CODE = 17;
    public static final String PRE_CHECK_FILE_NOT_EXIT_MESSAGE = "File not exist!";
    public static final int PRE_CHECK_PARSE_FRAME_FAILURE_CODE = 20;
    public static final String PRE_CHECK_PARSE_FRAME_FAILURE_MESSAGE = "parse frame failure!";
    public static final int PRE_CHECK_TYPE_ERROR_CODE = 21;
    public static final String PRE_CHECK_TYPE_ERROR_MESSAGE = "error type!";
    public static final int PRE_CHECK_VIDEO_TOO_LARGE_CODE = 18;
    public static final String PRE_CHECK_VIDEO_TOO_LARGE_MESSAGE = "Video too large!";
    public static final int PUBLISH_FAILURE_CODE = 65;
    public static final String PUBLISH_FAILURE_MESSAGE = "Publish failure!";
    public static final int PUBLISH_RESPONSE_ERROR_CODE = 66;
    public static final String PUBLISH_RESPONSE_ERROR_MESSAGE = "Publish response error!";
    public static final int USER_INFO_ERROR_CODE = 33;
    public static final String USER_INFO_ERROR_MESSAGE = "get user info failure!";

    void onError(int i, String str);

    void onException(Throwable th);

    void onSuccess(String str);
}
