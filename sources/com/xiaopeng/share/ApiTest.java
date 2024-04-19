package com.xiaopeng.share;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import com.xiaopeng.lib.framework.module.Module;
import com.xiaopeng.lib.framework.moduleinterface.accountmodule.AbsException;
import com.xiaopeng.lib.framework.moduleinterface.accountmodule.ICallback;
import com.xiaopeng.lib.framework.moduleinterface.accountmodule.IError;
import com.xiaopeng.lib.framework.moduleinterface.accountmodule.IOTPInfo;
import com.xiaopeng.lib.framework.moduleinterface.accountmodule.IUserInfo;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.Callback;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.IRemoteStorage;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.StorageException;
import com.xiaopeng.lib.framework.netchannelmodule.NetworkChannelsEntry;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.lib.utils.SystemPropertyUtil;
import com.xiaopeng.lib.utils.view.ToastUtils;
import java.util.ArrayList;
import java.util.List;
@Deprecated
/* loaded from: classes.dex */
public class ApiTest {
    public static final String TAG = "ApiTest";

    public static void ossMulAsync(Application application, List<String> uploadMulFile, String bjmarket) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void upLoadFileMulSync(final List<String> uploadFilePath) {
        if (uploadFilePath == null || uploadFilePath.size() == 0) {
            return;
        }
        String currentUpload = uploadFilePath.get(0);
        String objectKey = System.currentTimeMillis() + currentUpload;
        try {
            ComponentManager.getInstance().getRemoteStorage().uploadWithPathAndCallback("bjmarket", objectKey, currentUpload, new Callback() { // from class: com.xiaopeng.share.ApiTest.1
                @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.Callback
                public void onStart(String s, String s1) {
                    Log.d(ApiTest.TAG, "oss start... " + s);
                }

                @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.Callback
                public void onSuccess(String s, String s1) {
                    Log.e(ApiTest.TAG, "url:" + s + "\n local:" + s1);
                    uploadFilePath.remove(0);
                    ApiTest.upLoadFileMulSync(uploadFilePath);
                }

                @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.Callback
                public void onFailure(String s, String s1, StorageException e) {
                    Log.d(ApiTest.TAG, "oss onFailure... " + s + e.getReasonCode());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void ossMulSync(Application application, List<String> uploadFilePath, String bucketName) {
        IRemoteStorage storage = ComponentManager.getInstance().getRemoteStorage();
        try {
            storage.initWithContext(application);
            ArrayList<String> toUpload = new ArrayList<>();
            toUpload.addAll(uploadFilePath);
            upLoadFileMulSync(toUpload);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void ossSingle(Application application, String filePath, String bucketName) {
        IRemoteStorage storage = getRemoteStorage();
        try {
            storage.initWithContext(application);
            Log.e(TAG, "\n\nbucketName:" + bucketName + "\nobjectKey:vehicle/LMVHFEFZ8JA800063/mnt/video/camera/15634359851151563262429304.jpg\nfilePath" + filePath);
            storage.uploadWithPathAndCallback(bucketName, "vehicle/LMVHFEFZ8JA800063/mnt/video/camera/15634359851151563262429304.jpg", filePath, new Callback() { // from class: com.xiaopeng.share.ApiTest.2
                @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.Callback
                public void onStart(String s, String s1) {
                }

                @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.Callback
                public void onSuccess(String s, String s1) {
                }

                @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.Callback
                public void onFailure(String s, String s1, StorageException e) {
                }
            });
        } catch (Exception ex) {
            LogUtils.e(TAG, ex);
        }
    }

    public static void showUser(Context context) {
        try {
            IUserInfo userInfo = ComponentManager.getInstance().getAccount().getUserInfo();
            ToastUtils.showToast(context, userInfo.toString());
        } catch (AbsException e) {
            e.printStackTrace();
            ToastUtils.showToast(context, "获取失败：" + e.getMessage());
        }
    }

    public static void showLogin() {
        try {
            ComponentManager.getInstance().getAccount().login();
        } catch (AbsException e) {
            e.printStackTrace();
        }
    }

    public static IUserInfo getUserInfo() throws AbsException {
        return ComponentManager.getInstance().getAccount().getUserInfo();
    }

    public static void requestOTP(final Context context) {
        ComponentManager.getInstance().getAccount().requestOTP(SystemPropertyUtil.getHardwareId(), new ICallback<IOTPInfo, IError>() { // from class: com.xiaopeng.share.ApiTest.3
            @Override // com.xiaopeng.lib.framework.moduleinterface.accountmodule.ICallback
            public void onSuccess(IOTPInfo iotpInfo) {
                ToastUtils.showToast(context, iotpInfo.toString());
            }

            @Override // com.xiaopeng.lib.framework.moduleinterface.accountmodule.ICallback
            public void onFail(IError iError) {
                ToastUtils.showToast(context, "requestOtp error!");
            }
        });
    }

    public static void uploadFile(Application app, String filePath, String bucketName) {
        IRemoteStorage storage = getRemoteStorage();
        try {
            storage.initWithContext(app);
            Log.e(TAG, "\n\nbucketName:" + bucketName + "\nobjectKey:15634359851151563262429304.jpg\nfilePath" + filePath);
            storage.uploadWithPathAndCallback(bucketName, "15634359851151563262429304.jpg", filePath, new Callback() { // from class: com.xiaopeng.share.ApiTest.4
                @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.Callback
                public void onStart(String s, String s1) {
                }

                @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.Callback
                public void onSuccess(String s, String s1) {
                }

                @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.Callback
                public void onFailure(String s, String s1, StorageException e) {
                }
            });
        } catch (Exception ex) {
            LogUtils.e(TAG, ex);
        }
    }

    public static IRemoteStorage getRemoteStorage() {
        IRemoteStorage storage = (IRemoteStorage) Module.get(NetworkChannelsEntry.class).get(IRemoteStorage.class);
        return storage;
    }
}
