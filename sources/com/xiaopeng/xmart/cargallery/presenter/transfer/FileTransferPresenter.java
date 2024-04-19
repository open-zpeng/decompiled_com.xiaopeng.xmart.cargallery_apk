package com.xiaopeng.xmart.cargallery.presenter.transfer;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.wifi.WifiClient;
import android.os.IBinder;
import com.xiaopeng.xmart.cargallery.App;
import com.xiaopeng.xmart.cargallery.CameraLog;
import com.xiaopeng.xmart.cargallery.bean.BaseItem;
import com.xiaopeng.xmart.cargallery.bean.ConnectionBean;
import com.xiaopeng.xmart.cargallery.bean.TransferProgressEvent;
import com.xiaopeng.xmart.cargallery.bean.UdpHandshakeProtocol;
import com.xiaopeng.xmart.cargallery.helper.ThreadPoolHelper;
import com.xiaopeng.xmart.cargallery.manager.WifiMgr;
import com.xiaopeng.xmart.cargallery.model.transfer.FileTransferModel;
import com.xiaopeng.xmart.cargallery.presenter.transfer.FileTransferPresenter;
import com.xiaopeng.xmart.cargallery.presenter.transfer.handshake.HandshakeSender;
import com.xiaopeng.xmart.cargallery.presenter.transfer.service.TransferManagerService;
import com.xiaopeng.xmart.cargallery.presenter.transfer.service.callback.NetWorkCallBack;
import com.xiaopeng.xmart.cargallery.presenter.transfer.service.callback.TransferProgressCallBack;
import com.xiaopeng.xmart.cargallery.presenter.transfer.service.callback.TransferStatusCallBack;
import com.xiaopeng.xmart.cargallery.utils.GsonUtils;
import com.xiaopeng.xmart.cargallery.view.transfer.IFileTransferView;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes2.dex */
public class FileTransferPresenter {
    private static final String TAG = FileTransferPresenter.class.getSimpleName();
    private ArrayList<String> filePaths;
    private Intent intent;
    FileTransferModel mModel;
    private TransferManagerService mService;
    IFileTransferView mView;
    private NetWorkCallBack netWorkCallBack;
    private ArrayList<BaseItem> transferFiles;
    private TransferProgressCallBack transferProgressCallBack;
    private TransferStatusCallBack transferStatusCallBack;
    private ServiceConnection connection = new ServiceConnection() { // from class: com.xiaopeng.xmart.cargallery.presenter.transfer.FileTransferPresenter.1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName name, IBinder service) {
            CameraLog.d(FileTransferPresenter.TAG, "onServiceConnected()...", false);
            FileTransferPresenter.this.mService = ((TransferManagerService.TransferManagerBinder) service).getService();
            FileTransferPresenter.this.mService.setCallback(FileTransferPresenter.this.netWorkCallBack, FileTransferPresenter.this.transferProgressCallBack, FileTransferPresenter.this.transferStatusCallBack);
            FileTransferPresenter.this.mService.startTether();
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName name) {
            CameraLog.d(FileTransferPresenter.TAG, "onServiceDisconnected()...", false);
        }
    };
    private Context mContext = App.getInstance();

    public FileTransferPresenter(IFileTransferView view, ArrayList<BaseItem> transferFiles) {
        this.transferFiles = transferFiles;
        this.mView = view;
        initCallback();
        FileTransferModel fileTransferModel = new FileTransferModel(transferFiles);
        this.mModel = fileTransferModel;
        this.filePaths = fileTransferModel.getFilePaths();
        Intent intent = new Intent(this.mContext, TransferManagerService.class);
        this.intent = intent;
        intent.putParcelableArrayListExtra(TransferManagerService.EXTRA_TRANSFER_FILE_LIST, transferFiles);
        this.intent.putStringArrayListExtra(TransferManagerService.EXTRA_TRANSFER_FILE_PATH, this.filePaths);
        this.intent.putExtra(TransferManagerService.EXTRA_TRANSFER_AP_SSID, this.mModel.generateWifiSSID(this.mContext));
        this.intent.putExtra(TransferManagerService.EXTRA_TRANSFER_AP_PWD, this.mModel.generateRandomPw());
        this.intent.setAction("android.intent.action.RESPOND_VIA_MESSAGE");
        this.mContext.startService(this.intent);
        this.mContext.bindService(this.intent, this.connection, 0);
    }

    private void initCallback() {
        CameraLog.d(TAG, "initCallback()...", false);
        this.netWorkCallBack = new NetWorkCallBack() { // from class: com.xiaopeng.xmart.cargallery.presenter.transfer.FileTransferPresenter.2
            @Override // com.xiaopeng.xmart.cargallery.presenter.transfer.service.callback.NetWorkCallBack
            public void apEnabled(String ssid, String pwd) {
            }

            @Override // com.xiaopeng.xmart.cargallery.presenter.transfer.service.callback.NetWorkCallBack
            public void apDisabled() {
                if (FileTransferPresenter.this.mView != null) {
                    FileTransferPresenter.this.mView.apDisable();
                }
            }

            @Override // com.xiaopeng.xmart.cargallery.presenter.transfer.service.callback.NetWorkCallBack
            public void addConnection(ConnectionBean connectionBean) {
                if (FileTransferPresenter.this.mView != null) {
                    FileTransferPresenter.this.mView.addConnect(connectionBean);
                }
            }

            @Override // com.xiaopeng.xmart.cargallery.presenter.transfer.service.callback.NetWorkCallBack
            public void rejectByConnection(ConnectionBean connectionBean) {
                if (FileTransferPresenter.this.mView != null) {
                    FileTransferPresenter.this.mView.clientReject(connectionBean);
                }
            }

            @Override // com.xiaopeng.xmart.cargallery.presenter.transfer.service.callback.NetWorkCallBack
            public void clientReceiveOver() {
                FileTransferPresenter.this.mView.clientReceiveOver();
            }

            @Override // com.xiaopeng.xmart.cargallery.presenter.transfer.service.callback.NetWorkCallBack
            public void updateClientList(List<WifiClient> clientList) {
                FileTransferPresenter.this.mView.updateConnectList(clientList);
            }
        };
        this.transferProgressCallBack = new AnonymousClass3();
        this.transferStatusCallBack = new TransferStatusCallBack() { // from class: com.xiaopeng.xmart.cargallery.presenter.transfer.FileTransferPresenter.4
            @Override // com.xiaopeng.xmart.cargallery.presenter.transfer.service.callback.TransferStatusCallBack
            public void updateTransferStatus(int status) {
                if (FileTransferPresenter.this.mView != null) {
                    if (status == 32 || status == 1 || status == 2 || status == 34) {
                        FileTransferPresenter.this.mView.onToastInfo(status);
                    }
                }
            }

            @Override // com.xiaopeng.xmart.cargallery.presenter.transfer.service.callback.TransferStatusCallBack
            public void updateTransferResult(int status, int sucNum, int failureNum) {
                if (FileTransferPresenter.this.mView != null) {
                    FileTransferPresenter.this.mView.onToastTransferInfo(status, sucNum, failureNum);
                }
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.xmart.cargallery.presenter.transfer.FileTransferPresenter$3  reason: invalid class name */
    /* loaded from: classes2.dex */
    public class AnonymousClass3 implements TransferProgressCallBack {
        private boolean mTransferFailure;

        AnonymousClass3() {
        }

        @Override // com.xiaopeng.xmart.cargallery.presenter.transfer.service.callback.TransferProgressCallBack
        public void onTransferStart(final String address) {
            ThreadPoolHelper.getInstance().postOnMainThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.presenter.transfer.-$$Lambda$FileTransferPresenter$3$fBkIr5zbf2P2sPDQ_ooH5WYbvf0
                @Override // java.lang.Runnable
                public final void run() {
                    FileTransferPresenter.AnonymousClass3.this.lambda$onTransferStart$0$FileTransferPresenter$3(address);
                }
            });
        }

        public /* synthetic */ void lambda$onTransferStart$0$FileTransferPresenter$3(final String address) {
            FileTransferPresenter.this.mView.transferStart(address);
        }

        @Override // com.xiaopeng.xmart.cargallery.presenter.transfer.service.callback.TransferProgressCallBack
        public void onProgressUpdate(final long progress, final long total, final String filePath) {
            CameraLog.d(FileTransferPresenter.TAG, "on progress: add" + progress + "total:" + total + "filePath:" + filePath, false);
            if (this.mTransferFailure) {
                CameraLog.d(FileTransferPresenter.TAG, "transfer failure no need to update progress...", false);
            } else {
                ThreadPoolHelper.getInstance().postOnMainThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.presenter.transfer.-$$Lambda$FileTransferPresenter$3$He-RefBLIcFiSypr4xDT5VGwS9A
                    @Override // java.lang.Runnable
                    public final void run() {
                        FileTransferPresenter.AnonymousClass3.this.lambda$onProgressUpdate$1$FileTransferPresenter$3(progress, total, filePath);
                    }
                });
            }
        }

        public /* synthetic */ void lambda$onProgressUpdate$1$FileTransferPresenter$3(final long progress, final long total, final String filePath) {
            FileTransferPresenter.this.mView.updateProgress(progress, total, filePath);
        }

        @Override // com.xiaopeng.xmart.cargallery.presenter.transfer.service.callback.TransferProgressCallBack
        public void onTransferSuccess(String file) {
        }

        @Override // com.xiaopeng.xmart.cargallery.presenter.transfer.service.callback.TransferProgressCallBack
        public void onTransferFailure(final String file) {
            this.mTransferFailure = true;
            CameraLog.d(FileTransferPresenter.TAG, "transfer failure ... fileName:" + file, false);
            ThreadPoolHelper.getInstance().postOnMainThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.presenter.transfer.-$$Lambda$FileTransferPresenter$3$ILnkOPy6Zakyb8-IuXuYFYllVXU
                @Override // java.lang.Runnable
                public final void run() {
                    FileTransferPresenter.AnonymousClass3.this.lambda$onTransferFailure$2$FileTransferPresenter$3(file);
                }
            });
        }

        public /* synthetic */ void lambda$onTransferFailure$2$FileTransferPresenter$3(final String file) {
            FileTransferPresenter.this.mView.updateTransferFailure(file);
        }
    }

    public long getTotalFileLength() {
        return this.mModel.getFileLength();
    }

    public ArrayList<TransferProgressEvent> getTransferInfoData() {
        return this.mModel.getInfoDatas();
    }

    private String getDeviceName() {
        return this.mModel.getDeviceName();
    }

    public void replyToClient(final ConnectionBean connection) {
        CameraLog.d(TAG, "connection :" + connection.toString() + "filePaths:" + this.filePaths.toString(), false);
        HandshakeSender sender = new HandshakeSender(replyToClientJson(this.filePaths), connection.getIp(), connection.getPort(), new HandshakeSender.ReachableListener() { // from class: com.xiaopeng.xmart.cargallery.presenter.transfer.-$$Lambda$FileTransferPresenter$1jQM4eLFhAOn9klhw6SqRnaWJQg
            @Override // com.xiaopeng.xmart.cargallery.presenter.transfer.handshake.HandshakeSender.ReachableListener
            public final void reachable(boolean z) {
                FileTransferPresenter.this.lambda$replyToClient$0$FileTransferPresenter(connection, z);
            }
        });
        new Thread(sender, "handshake_send").start();
    }

    public /* synthetic */ void lambda$replyToClient$0$FileTransferPresenter(final ConnectionBean connection, boolean reach) {
        this.mView.replySuc(connection);
    }

    private String replyToClientJson(ArrayList filePaths) {
        UdpHandshakeProtocol protocol = new UdpHandshakeProtocol();
        protocol.setAction(1);
        protocol.setDeviceName(getDeviceName());
        protocol.setPort(TransferManagerService.UDP_PORT);
        protocol.setFileList(filePaths);
        protocol.setIp(WifiMgr.getIpAddress());
        String jsonStr = GsonUtils.convertVO2String(protocol);
        CameraLog.d(TAG, "reply to client json str :" + jsonStr, false);
        return jsonStr;
    }

    public void onStop() {
        CameraLog.d(TAG, "onStop() ...", false);
        this.mService.stopHandShake();
        try {
            this.mContext.unbindService(this.connection);
        } catch (Exception e) {
            CameraLog.d(TAG, "unbindService ServiceConnection " + e.getMessage(), false);
        }
    }

    public String generateRandomPw() {
        return this.mModel.generateRandomPw();
    }

    public String generateWifiSSID() {
        return this.mModel.generateWifiSSID(this.mContext);
    }

    public void connectError() {
        this.mService.connectError();
    }

    public void clientReceiveOver() {
        this.mService.clientReceiveOver();
    }
}
