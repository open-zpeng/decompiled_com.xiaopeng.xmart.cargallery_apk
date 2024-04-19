package com.xiaopeng.xmart.cargallery.presenter.transfer.service;

import android.app.Service;
import android.content.Intent;
import android.net.wifi.WifiClient;
import android.os.Binder;
import android.os.IBinder;
import android.text.TextUtils;
import com.xiaopeng.xmart.cargallery.App;
import com.xiaopeng.xmart.cargallery.CameraLog;
import com.xiaopeng.xmart.cargallery.bean.BaseItem;
import com.xiaopeng.xmart.cargallery.bean.ConnectionBean;
import com.xiaopeng.xmart.cargallery.helper.ThreadPoolHelper;
import com.xiaopeng.xmart.cargallery.manager.TetherManager;
import com.xiaopeng.xmart.cargallery.presenter.transfer.file.FileSendManager;
import com.xiaopeng.xmart.cargallery.presenter.transfer.file.FileSendUnit;
import com.xiaopeng.xmart.cargallery.presenter.transfer.handshake.HandshakeReceiver;
import com.xiaopeng.xmart.cargallery.presenter.transfer.service.callback.NetWorkCallBack;
import com.xiaopeng.xmart.cargallery.presenter.transfer.service.callback.TransferProgressCallBack;
import com.xiaopeng.xmart.cargallery.presenter.transfer.service.callback.TransferStatusCallBack;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes8.dex */
public class TransferManagerService extends Service {
    private static final int CLOSE_TIME_OUT = 10;
    public static final String EXTRA_TRANSFER_AP_PWD = "extra_transfer_ap_pwd";
    public static final String EXTRA_TRANSFER_AP_SSID = "extra_transfer_ap_ssid";
    public static final String EXTRA_TRANSFER_FILE_LIST = "extra_transfer_file_list";
    public static final String EXTRA_TRANSFER_FILE_PATH = "extra_transfer_file_path";
    private static final String TAG = "TransferManagerService";
    public static boolean TRANSFER_UNDER_WAY = false;
    public static int UDP_PORT = 19222;
    public static final int WIFI_AP_STATE_DISABLED = 11;
    public static final int WIFI_AP_STATE_DISABLING = 10;
    public static final int WIFI_AP_STATE_ENABLED = 13;
    public static final int WIFI_AP_STATE_ENABLING = 12;
    private String apPwd;
    private String apSsid;
    private HandshakeReceiver.ApUdpAcceptListener handshakeListener;
    private int mFailCount;
    private HandshakeReceiver mHandshakeReceiver;
    private FileSendManager mServerRunnable;
    private int mSucCount;
    private int mTotalCount;
    private String mTransferAddress;
    private NetWorkCallBack netWorkCallBack;
    private FileSendUnit.OnSendListener onProgressListener;
    private TetherManager.WifiTetherSoftApCallback softApCallback;
    private TetherManager tetherManager;
    private ArrayList<BaseItem> transferFiles;
    private ArrayList<String> transferPaths;
    private TransferProgressCallBack transferProgressCallBack;
    private TransferStatusCallBack transferStatusCallBack;
    private int currentTransferStatue = 0;
    private boolean mFailureNotify = false;
    private TransferManagerBinder binder = new TransferManagerBinder();
    private boolean mIsComplete = false;

    static /* synthetic */ int access$808(TransferManagerService x0) {
        int i = x0.mSucCount;
        x0.mSucCount = i + 1;
        return i;
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return this.binder;
    }

    @Override // android.app.Service
    public void onCreate() {
        CameraLog.d(TAG, "onCreate()...", false);
        super.onCreate();
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int flags, int startId) {
        CameraLog.d(TAG, "onStartCommand()...", false);
        if (intent != null) {
            this.apPwd = intent.getStringExtra(EXTRA_TRANSFER_AP_PWD);
            this.apSsid = intent.getStringExtra(EXTRA_TRANSFER_AP_SSID);
            this.transferFiles = intent.getParcelableArrayListExtra(EXTRA_TRANSFER_FILE_LIST);
            this.transferPaths = intent.getStringArrayListExtra(EXTRA_TRANSFER_FILE_PATH);
            CameraLog.d(TAG, "onStartCommand()...  parse the date from intent:\nssid:" + this.apSsid + "\npwd:" + this.apPwd + "\ntransferFiles:" + this.transferFiles.toString(), false);
        }
        this.mFailureNotify = false;
        this.mTransferAddress = null;
        this.mIsComplete = false;
        return super.onStartCommand(intent, flags, startId);
    }

    @Override // android.app.Service
    public void onDestroy() {
        CameraLog.d(TAG, "onDestroy()...", false);
        stopTether();
        super.onDestroy();
    }

    public void startTether() {
        this.mTransferAddress = null;
        initProgressListener();
        this.tetherManager = TetherManager.getInstance(App.getInstance(), new TetherManager.WifiTetherSoftApCallback() { // from class: com.xiaopeng.xmart.cargallery.presenter.transfer.service.TransferManagerService.1
            @Override // com.xiaopeng.xmart.cargallery.manager.TetherManager.WifiTetherSoftApCallback
            public void onTetherStartSuccess() {
                CameraLog.d(TransferManagerService.TAG, "startTether thread:" + Thread.currentThread().getName(), false);
                TransferManagerService.this.notifyTransferStatus(1);
                TransferManagerService.this.mHandshakeReceiver = new HandshakeReceiver();
                TransferManagerService.this.mHandshakeReceiver.setmListener(TransferManagerService.this.handshakeListener);
                new Thread(TransferManagerService.this.mHandshakeReceiver).start();
            }

            @Override // com.xiaopeng.xmart.cargallery.manager.TetherManager.WifiTetherSoftApCallback
            public void onTetherStartFailure() {
                TransferManagerService.this.notifyTransferStatus(2);
            }

            @Override // com.xiaopeng.xmart.cargallery.manager.TetherManager.WifiTetherSoftApCallback
            public void onStateChanged(int state, int failureReason) {
                CameraLog.d(TransferManagerService.TAG, "onStateChanged()...  state:" + state + "failureReason" + failureReason, false);
                if (state == 10 && TransferManagerService.this.netWorkCallBack != null) {
                    TransferManagerService.this.netWorkCallBack.apDisabled();
                }
            }

            @Override // com.xiaopeng.xmart.cargallery.manager.TetherManager.WifiTetherSoftApCallback
            public void onNumClientsChanged(int numClients) {
                CameraLog.d(TransferManagerService.TAG, "onNumClientsChanged()...  numClients:" + numClients, false);
                if (numClients == 0) {
                    CameraLog.d(TransferManagerService.TAG, "whether transfer under way? " + TransferManagerService.TRANSFER_UNDER_WAY, false);
                    if (TransferManagerService.TRANSFER_UNDER_WAY) {
                        TransferManagerService.this.stopTether();
                        TransferManagerService.TRANSFER_UNDER_WAY = false;
                    }
                }
            }

            @Override // com.xiaopeng.xmart.cargallery.manager.TetherManager.WifiTetherSoftApCallback
            public void onClientsUpdated(List<WifiClient> clients) {
                StringBuilder sb;
                CameraLog.d(TransferManagerService.TAG, "onClientsUpdated()...  clients:" + clients.toString(), false);
                try {
                    try {
                        if (TransferManagerService.this.netWorkCallBack != null) {
                            TransferManagerService.this.netWorkCallBack.updateClientList(clients);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (TextUtils.isEmpty(TransferManagerService.this.mTransferAddress) || "/null".equals(TransferManagerService.this.mTransferAddress)) {
                            sb = new StringBuilder();
                        } else {
                            boolean isDisconnect = true;
                            int i = 0;
                            while (true) {
                                if (i >= clients.size()) {
                                    break;
                                } else if (TransferManagerService.this.mTransferAddress.equals(clients.get(i).mIpAddr)) {
                                    isDisconnect = false;
                                    break;
                                } else {
                                    i++;
                                }
                            }
                            if (!isDisconnect) {
                                return;
                            }
                            CameraLog.d(TransferManagerService.TAG, "wifi client disconnect...", false);
                            TransferManagerService.TRANSFER_UNDER_WAY = false;
                            if (TransferManagerService.this.mHandshakeReceiver != null) {
                                TransferManagerService.this.mHandshakeReceiver.cleanUp();
                            }
                            TransferManagerService.this.releaseCallback();
                            if (TransferManagerService.this.mServerRunnable != null) {
                                TransferManagerService.this.mServerRunnable.quit();
                                TransferManagerService.this.mServerRunnable = null;
                            }
                        }
                    }
                } catch (Throwable th) {
                    if (!TextUtils.isEmpty(TransferManagerService.this.mTransferAddress) && !"/null".equals(TransferManagerService.this.mTransferAddress)) {
                        boolean isDisconnect2 = true;
                        int i2 = 0;
                        while (true) {
                            if (i2 >= clients.size()) {
                                break;
                            } else if (TransferManagerService.this.mTransferAddress.equals(clients.get(i2).mIpAddr)) {
                                isDisconnect2 = false;
                                break;
                            } else {
                                i2++;
                            }
                        }
                        if (isDisconnect2) {
                            CameraLog.d(TransferManagerService.TAG, "wifi client disconnect...", false);
                            TransferManagerService.TRANSFER_UNDER_WAY = false;
                            if (TransferManagerService.this.mHandshakeReceiver != null) {
                                TransferManagerService.this.mHandshakeReceiver.cleanUp();
                            }
                            TransferManagerService.this.releaseCallback();
                            if (TransferManagerService.this.mServerRunnable != null) {
                                TransferManagerService.this.mServerRunnable.quit();
                                TransferManagerService.this.mServerRunnable = null;
                            }
                            TransferManagerService.this.stopTether();
                        }
                        throw th;
                    }
                    sb = new StringBuilder();
                }
                if (TextUtils.isEmpty(TransferManagerService.this.mTransferAddress) || "/null".equals(TransferManagerService.this.mTransferAddress)) {
                    sb = new StringBuilder();
                    CameraLog.d(TransferManagerService.TAG, sb.append("transfer  not start ...").append(TransferManagerService.this.mTransferAddress).toString(), false);
                    return;
                }
                boolean isDisconnect3 = true;
                int i3 = 0;
                while (true) {
                    if (i3 >= clients.size()) {
                        break;
                    } else if (TransferManagerService.this.mTransferAddress.equals(clients.get(i3).mIpAddr)) {
                        isDisconnect3 = false;
                        break;
                    } else {
                        i3++;
                    }
                }
                if (isDisconnect3) {
                    CameraLog.d(TransferManagerService.TAG, "wifi client disconnect...", false);
                    TransferManagerService.TRANSFER_UNDER_WAY = false;
                    if (TransferManagerService.this.mHandshakeReceiver != null) {
                        TransferManagerService.this.mHandshakeReceiver.cleanUp();
                    }
                    TransferManagerService.this.releaseCallback();
                    if (TransferManagerService.this.mServerRunnable != null) {
                        TransferManagerService.this.mServerRunnable.quit();
                        TransferManagerService.this.mServerRunnable = null;
                    }
                    TransferManagerService.this.stopTether();
                }
            }
        });
        ThreadPoolHelper.getInstance().execute(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.presenter.transfer.service.-$$Lambda$TransferManagerService$M2NwQomQtnwPjuLXmaUIDNuQmKQ
            @Override // java.lang.Runnable
            public final void run() {
                TransferManagerService.this.lambda$startTether$0$TransferManagerService();
            }
        });
    }

    public /* synthetic */ void lambda$startTether$0$TransferManagerService() {
        TetherManager tetherManager = this.tetherManager;
        if (tetherManager != null) {
            tetherManager.startTether(this.apSsid, this.apPwd);
        }
    }

    private void initProgressListener() {
        this.onProgressListener = new FileSendUnit.OnSendListener() { // from class: com.xiaopeng.xmart.cargallery.presenter.transfer.service.TransferManagerService.2
            @Override // com.xiaopeng.xmart.cargallery.presenter.transfer.file.FileSendUnit.OnSendListener
            public void onStart(String address) {
                if (TransferManagerService.this.transferProgressCallBack != null) {
                    TransferManagerService.this.transferProgressCallBack.onTransferStart(address);
                    TransferManagerService.this.notifyTransferStatus(17);
                    TransferManagerService.this.mTransferAddress = address;
                    TransferManagerService.TRANSFER_UNDER_WAY = true;
                }
            }

            @Override // com.xiaopeng.xmart.cargallery.presenter.transfer.file.FileSendUnit.OnSendListener
            public void onProgress(long progress, long total, String filePath) {
                if (TransferManagerService.this.transferProgressCallBack != null) {
                    TransferManagerService.this.transferProgressCallBack.onProgressUpdate(progress, total, filePath);
                }
            }

            @Override // com.xiaopeng.xmart.cargallery.presenter.transfer.file.FileSendUnit.OnSendListener
            public void onSuccess(String filePath) {
                if (TransferManagerService.this.transferProgressCallBack != null) {
                    TransferManagerService.this.transferProgressCallBack.onTransferSuccess(filePath);
                }
                TransferManagerService.access$808(TransferManagerService.this);
                if (TransferManagerService.this.mFailCount == 0 && TransferManagerService.this.mSucCount == TransferManagerService.this.mTotalCount) {
                    TransferManagerService transferManagerService = TransferManagerService.this;
                    transferManagerService.allComplete(true, transferManagerService.mSucCount, TransferManagerService.this.mFailCount);
                }
            }

            @Override // com.xiaopeng.xmart.cargallery.presenter.transfer.file.FileSendUnit.OnSendListener
            public void onFailure(Throwable t, String filePath) {
                if (TransferManagerService.this.transferProgressCallBack != null) {
                    TransferManagerService.this.transferProgressCallBack.onTransferFailure(filePath);
                }
                if (!TransferManagerService.this.mFailureNotify) {
                    TransferManagerService.this.mFailureNotify = true;
                    TransferManagerService transferManagerService = TransferManagerService.this;
                    transferManagerService.mFailCount = transferManagerService.mTotalCount - TransferManagerService.this.mSucCount;
                    TransferManagerService transferManagerService2 = TransferManagerService.this;
                    transferManagerService2.allComplete(false, transferManagerService2.mSucCount, TransferManagerService.this.mFailCount);
                }
            }
        };
        this.handshakeListener = new HandshakeReceiver.ApUdpAcceptListener() { // from class: com.xiaopeng.xmart.cargallery.presenter.transfer.service.-$$Lambda$TransferManagerService$N1enuJOIlRUmv7uk3N5C73vU16Y
            @Override // com.xiaopeng.xmart.cargallery.presenter.transfer.handshake.HandshakeReceiver.ApUdpAcceptListener
            public final void handShake(int i, Object obj) {
                TransferManagerService.this.lambda$initProgressListener$1$TransferManagerService(i, obj);
            }
        };
    }

    public /* synthetic */ void lambda$initProgressListener$1$TransferManagerService(int progress, Object bean) {
        if (this.netWorkCallBack == null) {
            CameraLog.d(TAG, "callback is null", false);
            return;
        }
        CameraLog.d(TAG, "Transfer Progress:" + progress + " bean: " + (bean == null ? "null" : bean.toString()), false);
        switch (progress) {
            case 1:
                createSocketServer(this.transferPaths, this.onProgressListener);
                return;
            case 2:
                this.netWorkCallBack.addConnection((ConnectionBean) bean);
                if (bean != null) {
                    notifyTransferStatus(16);
                    return;
                }
                return;
            case 3:
                this.netWorkCallBack.rejectByConnection((ConnectionBean) bean);
                return;
            case 4:
                this.netWorkCallBack.clientReceiveOver();
                return;
            default:
                return;
        }
    }

    public void stopTether() {
        CameraLog.d(TAG, "stop Tether!", false);
        ThreadPoolHelper.getInstance().execute(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.presenter.transfer.service.-$$Lambda$TransferManagerService$9PRYJdhvPkCN8dQypjxwLv407DA
            @Override // java.lang.Runnable
            public final void run() {
                TransferManagerService.this.lambda$stopTether$2$TransferManagerService();
            }
        });
    }

    public /* synthetic */ void lambda$stopTether$2$TransferManagerService() {
        TetherManager tetherManager = this.tetherManager;
        if (tetherManager != null) {
            tetherManager.stopTether();
        }
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        CameraLog.d(TAG, "onUnbind...", false);
        releaseCallback();
        HandshakeReceiver handshakeReceiver = this.mHandshakeReceiver;
        if (handshakeReceiver != null) {
            handshakeReceiver.cleanUp();
        }
        CameraLog.d(TAG, "transfer under way", false);
        return super.onUnbind(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void releaseCallback() {
        CameraLog.d(TAG, "releaseCallback()...", false);
        this.netWorkCallBack = null;
        this.transferProgressCallBack = null;
        this.transferStatusCallBack = null;
    }

    public void createSocketServer(ArrayList<String> filePaths, FileSendUnit.OnSendListener listener) {
        this.mTotalCount = filePaths.size();
        this.mFailCount = 0;
        this.mSucCount = 0;
        buildSocketServer(listener);
    }

    private void buildSocketServer(FileSendUnit.OnSendListener listener) {
        CameraLog.d(TAG, "====== create server socket ====== ", false);
        this.mServerRunnable = new FileSendManager(listener);
        new Thread(this.mServerRunnable).start();
    }

    public void connectError() {
        CameraLog.d(TAG, "connect error!!!", false);
        TRANSFER_UNDER_WAY = false;
        stopTether();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void allComplete(boolean suc, int sucCount, int failureCount) {
        this.mIsComplete = true;
        CameraLog.d(TAG, "file transfer complete. suc:" + suc + " sucCount:" + sucCount + " failureCount:" + failureCount, false);
        if (suc) {
            TransferStatusCallBack transferStatusCallBack = this.transferStatusCallBack;
            if (transferStatusCallBack != null) {
                transferStatusCallBack.updateTransferStatus(32);
            }
        } else if (sucCount != 0) {
            TransferStatusCallBack transferStatusCallBack2 = this.transferStatusCallBack;
            if (transferStatusCallBack2 != null) {
                int i = this.mSucCount;
                transferStatusCallBack2.updateTransferResult(33, i, this.mTotalCount - i);
            }
        } else {
            TransferStatusCallBack transferStatusCallBack3 = this.transferStatusCallBack;
            if (transferStatusCallBack3 != null) {
                transferStatusCallBack3.updateTransferStatus(34);
            }
        }
        ThreadPoolHelper.getInstance().postDelayedOnMainThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.presenter.transfer.service.-$$Lambda$TransferManagerService$7XmsXOTSCPYIuYQDemJ2azto0Eo
            @Override // java.lang.Runnable
            public final void run() {
                TransferManagerService.this.lambda$allComplete$3$TransferManagerService();
            }
        }, 10000L);
    }

    public /* synthetic */ void lambda$allComplete$3$TransferManagerService() {
        CameraLog.d(TAG, "all complete release the resource by timeout!", false);
        if (!this.mIsComplete) {
            CameraLog.d(TAG, "not complete return ", false);
            return;
        }
        FileSendManager fileSendManager = this.mServerRunnable;
        if (fileSendManager != null) {
            fileSendManager.quit();
            this.mServerRunnable = null;
        }
        HandshakeReceiver handshakeReceiver = this.mHandshakeReceiver;
        if (handshakeReceiver != null) {
            handshakeReceiver.cleanUp();
        }
        stopTether();
        TRANSFER_UNDER_WAY = false;
    }

    public void stopHandShake() {
        CameraLog.d(TAG, "stopHandShake()...", false);
        HandshakeReceiver handshakeReceiver = this.mHandshakeReceiver;
        if (handshakeReceiver != null) {
            handshakeReceiver.cleanUp();
        }
        if (!TRANSFER_UNDER_WAY) {
            stopTether();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyTransferStatus(int status) {
        TransferStatusCallBack transferStatusCallBack = this.transferStatusCallBack;
        if (transferStatusCallBack != null) {
            transferStatusCallBack.updateTransferStatus(status);
        }
        this.currentTransferStatue = status;
    }

    public void setCallback(NetWorkCallBack netWorkCallBack, TransferProgressCallBack transferProgressCallBack, TransferStatusCallBack transferStatusCallBack) {
        CameraLog.d(TAG, "setCallback()..." + netWorkCallBack + ":" + transferProgressCallBack + ":" + transferStatusCallBack, false);
        this.netWorkCallBack = netWorkCallBack;
        this.transferProgressCallBack = transferProgressCallBack;
        this.transferStatusCallBack = transferStatusCallBack;
    }

    public void clientReceiveOver() {
        CameraLog.d(TAG, "client receive over", false);
        FileSendManager fileSendManager = this.mServerRunnable;
        if (fileSendManager != null) {
            fileSendManager.quit();
            this.mServerRunnable = null;
        }
        HandshakeReceiver handshakeReceiver = this.mHandshakeReceiver;
        if (handshakeReceiver != null) {
            handshakeReceiver.cleanUp();
        }
        stopTether();
        TRANSFER_UNDER_WAY = false;
    }

    /* loaded from: classes8.dex */
    public class TransferManagerBinder extends Binder {
        public TransferManagerBinder() {
        }

        public TransferManagerService getService() {
            CameraLog.d(TransferManagerService.TAG, "getService()...", false);
            return TransferManagerService.this;
        }
    }
}
