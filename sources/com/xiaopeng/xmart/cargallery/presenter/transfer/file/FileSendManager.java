package com.xiaopeng.xmart.cargallery.presenter.transfer.file;

import android.os.SystemClock;
import com.xiaopeng.xmart.cargallery.CameraLog;
import com.xiaopeng.xmart.cargallery.presenter.transfer.file.FileSendUnit;
import com.xiaopeng.xmart.cargallery.presenter.transfer.service.TransferManagerService;
import java.io.IOException;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/* loaded from: classes9.dex */
public class FileSendManager implements Runnable {
    private static final int PORT_INCREASE = 100;
    private static final String TAG = "FileSendManager";
    private ExecutorService executorService;
    private FileSendUnit.OnSendListener listener;
    private ServerSocket serverSocket;
    private int mPort = 19222;
    private boolean running = true;
    private ArrayList<FileSendUnit> runnableList = new ArrayList<>();

    public FileSendManager(FileSendUnit.OnSendListener listener) {
        this.listener = listener;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            CameraLog.d(TAG, "Create ServerSocket at port" + this.mPort, false);
            this.executorService = Executors.newSingleThreadExecutor();
            ServerSocket serverSocket = new ServerSocket();
            this.serverSocket = serverSocket;
            serverSocket.setReuseAddress(true);
            bindPort(this.mPort);
            CameraLog.d(TAG, "Create ServerSocket success onPort:" + this.mPort, false);
        } catch (Exception e) {
            CameraLog.d(TAG, "Create server socket occur exception!", false);
            e.printStackTrace();
        }
        while (this.running && !Thread.currentThread().isInterrupted()) {
            try {
                Socket socket = this.serverSocket.accept();
                String acceptIP = socket.getInetAddress().toString();
                FileSendUnit runnable = new FileSendUnit(socket);
                FileSendUnit.OnSendListener onSendListener = this.listener;
                if (onSendListener != null) {
                    onSendListener.onStart(acceptIP);
                }
                runnable.setListener(this.listener);
                this.executorService.execute(runnable);
                this.runnableList.add(runnable);
            } catch (IOException e2) {
                e2.printStackTrace();
                CameraLog.d(TAG, "=|=|=the server socket occurs an exception", false);
                this.running = false;
            }
        }
        CameraLog.d(TAG, "=|=|=the thread is over", false);
    }

    public void quit() {
        CameraLog.d(TAG, "=|=|=close the server socket", false);
        for (int i = 0; i < this.runnableList.size(); i++) {
            this.runnableList.get(i).finish();
        }
        ExecutorService executorService = this.executorService;
        if (executorService != null) {
            executorService.shutdown();
        }
        this.running = false;
        ServerSocket serverSocket = this.serverSocket;
        if (serverSocket != null) {
            try {
                serverSocket.close();
                this.serverSocket = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void bindPort(int port) {
        CameraLog.d(TAG, "udp bind port " + port, false);
        if (this.serverSocket == null) {
            CameraLog.d(TAG, "serverSocket is null", false);
            return;
        }
        SystemClock.sleep(1000L);
        try {
            this.serverSocket.bind(new InetSocketAddress(this.mPort));
            TransferManagerService.UDP_PORT = this.mPort;
        } catch (IOException e) {
            e.printStackTrace();
            if (e instanceof BindException) {
                int i = this.mPort + 1;
                this.mPort = i;
                bindPort(i);
            }
        }
    }
}
