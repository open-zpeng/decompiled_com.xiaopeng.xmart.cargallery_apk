package com.xiaopeng.xmart.cargallery.unity.transfer;

import android.net.wifi.WifiClient;
import com.xiaopeng.xmart.cargallery.CameraLog;
import com.xiaopeng.xmart.cargallery.CarGalleryConfig;
import com.xiaopeng.xmart.cargallery.bean.BIConfig;
import com.xiaopeng.xmart.cargallery.bean.BaseItem;
import com.xiaopeng.xmart.cargallery.bean.ConnectionBean;
import com.xiaopeng.xmart.cargallery.helper.BIHelper;
import com.xiaopeng.xmart.cargallery.helper.ThreadPoolHelper;
import com.xiaopeng.xmart.cargallery.presenter.transfer.FileTransferPresenter;
import com.xiaopeng.xmart.cargallery.presenter.transfer.service.TransferManagerService;
import com.xiaopeng.xmart.cargallery.unity.app.GalleryAppManager;
import com.xiaopeng.xmart.cargallery.view.transfer.IFileTransferView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/* loaded from: classes8.dex */
public class TransferManager extends GalleryAppManager implements IFileTransferView {
    private static final String TAG = TransferManager.class.getSimpleName();
    private TransferListener mListener;
    private FileTransferPresenter mPresenter;

    /* loaded from: classes8.dex */
    public static class SingleHolder {
        public static TransferManager instance = new TransferManager();
    }

    public static TransferManager get_instance() {
        return SingleHolder.instance;
    }

    @Override // com.xiaopeng.xmart.cargallery.unity.app.GalleryAppManager, com.xiaopeng.xmart.cargallery.unity.UnityBaseManager
    public void enterScene() {
        CameraLog.i(TAG, "enterScene: ", false);
    }

    @Override // com.xiaopeng.xmart.cargallery.unity.app.GalleryAppManager, com.xiaopeng.xmart.cargallery.unity.UnityBaseManager
    public void exitScene() {
        CameraLog.i(TAG, "exitScene: ", false);
        TransferManagerService.TRANSFER_UNDER_WAY = false;
        this.mPresenter.onStop();
    }

    public void setTransferFile(ArrayList<BaseItem> transferFile) {
        this.mPresenter = new FileTransferPresenter(this, transferFile);
        CameraLog.i(TAG, "setTransferFile: " + transferFile.toString(), false);
    }

    public void set_listener(TransferListener listener) {
        this.mListener = listener;
    }

    public String generateRandomPw() {
        String password = this.mPresenter.generateRandomPw();
        CameraLog.i(TAG, "generateRandomPw: " + password, false);
        return password;
    }

    public String generateWifiSSID() {
        String ssid = this.mPresenter.generateWifiSSID();
        CameraLog.i(TAG, "generateWifiSSID: " + ssid, false);
        return ssid;
    }

    public void connectError() {
        CameraLog.i(TAG, "connectError: ", false);
        this.mPresenter.connectError();
    }

    public void replyToClient(ConnectionBean data) {
        CameraLog.i(TAG, "replyToClient: " + data.toString(), false);
        this.mPresenter.replyToClient(data);
    }

    @Override // com.xiaopeng.xmart.cargallery.view.transfer.IFileTransferView
    public void addConnect(final ConnectionBean connectionBean) {
        CameraLog.i(TAG, "addConnect: " + connectionBean.toString(), false);
        ThreadPoolHelper.getInstance().postOnMainThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.unity.transfer.-$$Lambda$TransferManager$VMzVzXYKurQizaR6lBbquEMHgfM
            @Override // java.lang.Runnable
            public final void run() {
                TransferManager.this.lambda$addConnect$0$TransferManager(connectionBean);
            }
        });
    }

    public /* synthetic */ void lambda$addConnect$0$TransferManager(final ConnectionBean connectionBean) {
        TransferListener transferListener = this.mListener;
        if (transferListener != null) {
            transferListener.addConnect(connectionBean);
        }
    }

    @Override // com.xiaopeng.xmart.cargallery.view.transfer.IFileTransferView
    public void clientReject(final ConnectionBean connectionBean) {
        CameraLog.i(TAG, "clientReject: " + connectionBean.toString(), false);
        ThreadPoolHelper.getInstance().postOnMainThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.unity.transfer.-$$Lambda$TransferManager$dqXor_moKDYgB-Qtq8rVCW5aKVM
            @Override // java.lang.Runnable
            public final void run() {
                TransferManager.this.lambda$clientReject$1$TransferManager(connectionBean);
            }
        });
    }

    public /* synthetic */ void lambda$clientReject$1$TransferManager(final ConnectionBean connectionBean) {
        TransferListener transferListener = this.mListener;
        if (transferListener != null) {
            transferListener.clientReject(connectionBean);
        }
    }

    @Override // com.xiaopeng.xmart.cargallery.view.transfer.IFileTransferView
    public void showSingleConnection() {
    }

    @Override // com.xiaopeng.xmart.cargallery.view.transfer.IFileTransferView
    public void replyError(ConnectionBean connection) {
    }

    @Override // com.xiaopeng.xmart.cargallery.view.transfer.IFileTransferView
    public void replySuc(final ConnectionBean connection) {
        CameraLog.i(TAG, "replySuc: " + connection.toString(), false);
        ThreadPoolHelper.getInstance().postOnMainThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.unity.transfer.-$$Lambda$TransferManager$0T3Oo036_L_VqVRcU8pN0EsF4yg
            @Override // java.lang.Runnable
            public final void run() {
                TransferManager.this.lambda$replySuc$2$TransferManager(connection);
            }
        });
    }

    public /* synthetic */ void lambda$replySuc$2$TransferManager(final ConnectionBean connection) {
        TransferListener transferListener = this.mListener;
        if (transferListener != null) {
            transferListener.replySuc(connection);
        }
    }

    @Override // com.xiaopeng.xmart.cargallery.view.transfer.IFileTransferView
    public void clientReceiveOver() {
        CameraLog.i(TAG, "clientReceiveOver: ", false);
        ThreadPoolHelper.getInstance().postOnMainThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.unity.transfer.-$$Lambda$TransferManager$j5KhHpGyOgH0NNY9TfdLEWaNzvg
            @Override // java.lang.Runnable
            public final void run() {
                TransferManager.this.lambda$clientReceiveOver$3$TransferManager();
            }
        });
    }

    public /* synthetic */ void lambda$clientReceiveOver$3$TransferManager() {
        TransferListener transferListener = this.mListener;
        if (transferListener != null) {
            transferListener.clientReceiveOver();
        }
    }

    @Override // com.xiaopeng.xmart.cargallery.view.transfer.IFileTransferView
    public void updateProgress(final long progress, final long total, final String filePath) {
        CameraLog.i(TAG, "updateProgress progress :" + progress + " total:" + total + " filePath: " + filePath, false);
        ThreadPoolHelper.getInstance().postOnMainThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.unity.transfer.-$$Lambda$TransferManager$tJGajz2BeNX5XpVngoR26jS7aXo
            @Override // java.lang.Runnable
            public final void run() {
                TransferManager.this.lambda$updateProgress$4$TransferManager(progress, total, filePath);
            }
        });
        if (progress == total) {
            uploadTransferBI(true, filePath);
        }
    }

    public /* synthetic */ void lambda$updateProgress$4$TransferManager(final long progress, final long total, final String filePath) {
        TransferListener transferListener = this.mListener;
        if (transferListener != null) {
            transferListener.updateProgress(progress, total, filePath);
        }
    }

    @Override // com.xiaopeng.xmart.cargallery.view.transfer.IFileTransferView
    public void updateTransferFailure(final String filePath) {
        CameraLog.i(TAG, "updateTransferFailure: " + filePath, false);
        ThreadPoolHelper.getInstance().postOnMainThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.unity.transfer.-$$Lambda$TransferManager$flPBBYGKZ9AD3fKyICzpzloy-yc
            @Override // java.lang.Runnable
            public final void run() {
                TransferManager.this.lambda$updateTransferFailure$5$TransferManager(filePath);
            }
        });
        uploadTransferBI(false, filePath);
    }

    public /* synthetic */ void lambda$updateTransferFailure$5$TransferManager(final String filePath) {
        TransferListener transferListener = this.mListener;
        if (transferListener != null) {
            transferListener.updateTransferFailure(filePath);
        }
    }

    @Override // com.xiaopeng.xmart.cargallery.view.transfer.IFileTransferView
    public void transferStart(final String acceptIp) {
        CameraLog.i(TAG, "transferStart: " + acceptIp, false);
        ThreadPoolHelper.getInstance().postOnMainThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.unity.transfer.-$$Lambda$TransferManager$AVHLreqphs-_KdLy8wOYdcmk17E
            @Override // java.lang.Runnable
            public final void run() {
                TransferManager.this.lambda$transferStart$6$TransferManager(acceptIp);
            }
        });
    }

    public /* synthetic */ void lambda$transferStart$6$TransferManager(final String acceptIp) {
        TransferListener transferListener = this.mListener;
        if (transferListener != null) {
            transferListener.transferStart(acceptIp);
        }
    }

    @Override // com.xiaopeng.xmart.cargallery.view.transfer.IFileTransferView
    public void updateConnectList(final List<WifiClient> clientList) {
        CameraLog.i(TAG, "updateConnectList: " + clientList.toString(), false);
        ThreadPoolHelper.getInstance().postOnMainThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.unity.transfer.-$$Lambda$TransferManager$fskn8OKjrAwMnyNNCcGow6NC2nA
            @Override // java.lang.Runnable
            public final void run() {
                TransferManager.this.lambda$updateConnectList$7$TransferManager(clientList);
            }
        });
    }

    public /* synthetic */ void lambda$updateConnectList$7$TransferManager(final List clientList) {
        TransferListener transferListener = this.mListener;
        if (transferListener != null) {
            transferListener.updateConnectList(clientList);
        }
    }

    @Override // com.xiaopeng.xmart.cargallery.view.transfer.IFileTransferView
    public void apDisable() {
        CameraLog.i(TAG, "apDisable: ", false);
        ThreadPoolHelper.getInstance().postOnMainThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.unity.transfer.-$$Lambda$TransferManager$-sLVtFR-JW7I0K89fHpTjS6n7Eo
            @Override // java.lang.Runnable
            public final void run() {
                TransferManager.this.lambda$apDisable$8$TransferManager();
            }
        });
    }

    public /* synthetic */ void lambda$apDisable$8$TransferManager() {
        TransferListener transferListener = this.mListener;
        if (transferListener != null) {
            transferListener.apDisabled();
        }
    }

    @Override // com.xiaopeng.xmart.cargallery.view.transfer.IFileTransferView
    public void onToastInfo(final int status) {
        ThreadPoolHelper.getInstance().postOnMainThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.unity.transfer.-$$Lambda$TransferManager$qO_MumEDcKDFtxRhKEOcCErNpgc
            @Override // java.lang.Runnable
            public final void run() {
                TransferManager.this.lambda$onToastInfo$9$TransferManager(status);
            }
        });
    }

    public /* synthetic */ void lambda$onToastInfo$9$TransferManager(final int status) {
        TransferListener transferListener = this.mListener;
        if (transferListener != null) {
            transferListener.onToastInfo(status);
        }
    }

    @Override // com.xiaopeng.xmart.cargallery.view.transfer.IFileTransferView
    public void onToastTransferInfo(final int status, final int sucNum, final int failureNum) {
        ThreadPoolHelper.getInstance().postOnMainThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.unity.transfer.-$$Lambda$TransferManager$8WQ30NxRJwOE7XrxSMYt3Ysczdw
            @Override // java.lang.Runnable
            public final void run() {
                TransferManager.this.lambda$onToastTransferInfo$10$TransferManager(status, sucNum, failureNum);
            }
        });
    }

    public /* synthetic */ void lambda$onToastTransferInfo$10$TransferManager(final int status, final int sucNum, final int failureNum) {
        TransferListener transferListener = this.mListener;
        if (transferListener != null) {
            transferListener.onToastTransferInfo(status, sucNum, failureNum);
        }
    }

    private void uploadTransferBI(boolean success, String filePath) {
        Map<String, Number> params = new HashMap<>();
        params.put(BIConfig.PROPERTY.DATA_TYPE, 0);
        params.put("result", Integer.valueOf(success ? 1 : 0));
        BIHelper.getInstance().uploadGalleryBI(8, 21, params);
        if (filePath != null && filePath.contains(CarGalleryConfig.SENTRY_MODE_COLLISION_SUFFIX)) {
            params.put(BIConfig.PROPERTY.DATA_TYPE, 0);
            params.put("result", Integer.valueOf(success ? 1 : 0));
            BIHelper.getInstance().uploadGalleryBI(8, 24, params);
        }
    }
}
