package com.xiaopeng.xmart.cargallery.presenter.transfer.handshake;

import com.xiaopeng.xmart.cargallery.CameraLog;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
/* loaded from: classes4.dex */
public class HandshakeSender implements Runnable {
    private static final int REPLY_TIME = 5;
    private static final String TAG = HandshakeSender.class.getSimpleName();
    private DatagramSocket mDatagramSocket;
    ReachableListener mListener;
    private String mReplyClientIp;
    private int mReplyClientPort;
    private String mReplyContent;

    /* loaded from: classes4.dex */
    public interface ReachableListener {
        void reachable(boolean reach);
    }

    public HandshakeSender(String replyContent, String replyClientIp, int replyClientPort, ReachableListener listener) {
        this.mReplyContent = replyContent;
        this.mReplyClientIp = replyClientIp;
        this.mReplyClientPort = replyClientPort;
        this.mListener = listener;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            boolean isReachable = InetAddress.getByName(this.mReplyClientIp).isReachable(500);
            CameraLog.d(TAG, "connection :" + this.mReplyClientIp + "isReachable:" + isReachable, false);
            ReachableListener reachableListener = this.mListener;
            if (reachableListener != null) {
                reachableListener.reachable(isReachable);
            }
            if (!isReachable) {
                return;
            }
        } catch (IOException e) {
            CameraLog.d(TAG, "check the reachable occurs exception...", false);
            handlerException(e);
        }
        try {
            this.mDatagramSocket = new DatagramSocket();
        } catch (SocketException e2) {
            CameraLog.d(TAG, "create the udp sever occurs exception...", false);
            handlerException(e2);
        }
        byte[] bArr = new byte[1024];
        byte[] sendData = this.mReplyContent.getBytes();
        for (int i = 0; i < 5; i++) {
            try {
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName(this.mReplyClientIp), this.mReplyClientPort);
                this.mDatagramSocket.send(sendPacket);
                CameraLog.d(TAG, "udp server send data" + this.mReplyContent, false);
            } catch (IOException e3) {
                CameraLog.d(TAG, "send the udp message occurs exception...", false);
                handlerException(e3);
            }
        }
    }

    private void handlerException(Exception e) {
        e.printStackTrace();
        DatagramSocket datagramSocket = this.mDatagramSocket;
        if (datagramSocket != null) {
            datagramSocket.close();
            this.mDatagramSocket = null;
        }
    }
}
