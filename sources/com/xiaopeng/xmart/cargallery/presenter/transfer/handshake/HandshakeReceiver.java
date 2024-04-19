package com.xiaopeng.xmart.cargallery.presenter.transfer.handshake;

import android.os.SystemClock;
import com.xiaopeng.xmart.cargallery.CameraLog;
import com.xiaopeng.xmart.cargallery.bean.UdpHandshakeProtocol;
import com.xiaopeng.xmart.cargallery.utils.FileUtils;
import com.xiaopeng.xmart.cargallery.utils.GsonUtils;
import java.io.IOException;
import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Arrays;
/* loaded from: classes4.dex */
public class HandshakeReceiver implements Runnable {
    public static final int CLIENT_RECEIVE_OVER = 4;
    public static final int CLIENT_REJECT_MESSAGE = 3;
    public static final int CREATE_SOCKET = 1;
    public static final int RECEIVE_CLIENT_MESSAGE = 2;
    private static final String TAG = HandshakeReceiver.class.getSimpleName();
    private ApUdpAcceptListener mListener;
    private DatagramSocket mServerSocket;
    private boolean mSocketServerCreateSuc;
    private volatile boolean mRunning = true;
    private int mPort = 19874;

    /* loaded from: classes4.dex */
    public interface ApUdpAcceptListener {
        void handShake(int progress, Object object);
    }

    @Override // java.lang.Runnable
    public void run() {
        ApUdpAcceptListener apUdpAcceptListener;
        String str = TAG;
        CameraLog.d(str, "handshake receive thread start...");
        init(this.mPort);
        byte[] receiveData = new byte[1024];
        byte[] bArr = new byte[1024];
        CameraLog.d(str, "udp server create success!!!", false);
        while (this.mRunning && !Thread.currentThread().isInterrupted()) {
            String str2 = TAG;
            CameraLog.d(str2, "ThreadName :" + Thread.currentThread().getName() + "running: " + this.mRunning);
            Arrays.fill(receiveData, (byte) 0);
            CameraLog.d(str2, "udp waiting data!!!", false);
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            try {
                this.mServerSocket.receive(receivePacket);
            } catch (IOException e) {
                e.printStackTrace();
                CameraLog.d(TAG, "udp receive occurs an exception", false);
                this.mRunning = false;
            }
            String sentence = FileUtils.byteToStr(receiveData);
            if (sentence == null) {
                CameraLog.d(TAG, "udp server accept data null!!", false);
                return;
            }
            String str3 = TAG;
            CameraLog.d(str3, "udp server accept data" + sentence, false);
            UdpHandshakeProtocol udpHandshakeProtocol = (UdpHandshakeProtocol) GsonUtils.convertString2Object(sentence, UdpHandshakeProtocol.class);
            if (udpHandshakeProtocol == null || udpHandshakeProtocol.getPort() < 0 || udpHandshakeProtocol.getPort() > 65535) {
                CameraLog.d(str3, "parse udp message occurs exception!!!");
            } else {
                udpHandshakeProtocol.setIp(receivePacket.getAddress().getHostAddress());
                DatagramPacket sendPacket = new DatagramPacket(receiveData, receiveData.length, receivePacket.getAddress(), udpHandshakeProtocol.getPort());
                try {
                    this.mServerSocket.send(sendPacket);
                } catch (IOException e2) {
                    e2.printStackTrace();
                    String str4 = TAG;
                    CameraLog.d(str4, "send packet occurs exception!" + sentence, false);
                    this.mRunning = false;
                    CameraLog.d(str4, "running2:" + this.mRunning);
                    DatagramSocket datagramSocket = this.mServerSocket;
                    if (datagramSocket != null) {
                        datagramSocket.close();
                    }
                }
                if (udpHandshakeProtocol.getAction() == 0) {
                    ApUdpAcceptListener apUdpAcceptListener2 = this.mListener;
                    if (apUdpAcceptListener2 != null && !this.mSocketServerCreateSuc) {
                        apUdpAcceptListener2.handShake(1, null);
                        this.mSocketServerCreateSuc = true;
                    }
                } else if (udpHandshakeProtocol.getAction() == 2) {
                    ApUdpAcceptListener apUdpAcceptListener3 = this.mListener;
                    if (apUdpAcceptListener3 != null) {
                        apUdpAcceptListener3.handShake(3, udpHandshakeProtocol);
                    }
                } else if (udpHandshakeProtocol.getAction() == 3 && (apUdpAcceptListener = this.mListener) != null) {
                    apUdpAcceptListener.handShake(4, udpHandshakeProtocol);
                }
                if (this.mListener != null) {
                    CameraLog.d(TAG, "receive message from client ,content ====>" + sentence, false);
                    this.mListener.handShake(2, udpHandshakeProtocol);
                }
            }
        }
        releaseSocket();
        CameraLog.d(TAG, "handshake receive thread release suc...");
    }

    private void init(int port) {
        CameraLog.d(TAG, "init port " + port, false);
        SystemClock.sleep(1000L);
        try {
            this.mServerSocket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
            if (e instanceof BindException) {
                init(port - 1);
            }
        }
    }

    private void releaseSocket() {
        DatagramSocket datagramSocket = this.mServerSocket;
        if (datagramSocket != null) {
            datagramSocket.close();
        }
    }

    public void cleanUp() {
        String str = TAG;
        CameraLog.d(str, "handshake clean up ...", false);
        this.mRunning = false;
        this.mListener = null;
        releaseSocket();
        CameraLog.d(str, "handshake clean up end...", false);
    }

    public void setmListener(ApUdpAcceptListener mListener) {
        this.mListener = mListener;
    }
}
