package com.xiaopeng.xmart.cargallery.presenter.transfer.file;

import com.xiaopeng.xmart.cargallery.CameraLog;
import com.xiaopeng.xmart.cargallery.bean.FileHeadInfo;
import com.xiaopeng.xmart.cargallery.model.transfer.send.BaseTransfer;
import com.xiaopeng.xmart.cargallery.utils.FileUtils;
import com.xiaopeng.xmart.cargallery.utils.GsonUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
/* loaded from: classes9.dex */
public class FileSendUnit extends BaseTransfer implements Runnable {
    private static final int REFRESH_GAP = 500;
    private static final String TAG = FileSendUnit.class.getSimpleName();
    private File file;
    private String filePath;
    private long fileSize;
    private FileInputStream fis;
    private OnSendListener listener;
    private boolean mHasException = false;
    private OutputStream mOutputStream;
    private Socket mSocket;
    private int refreshCount;

    /* loaded from: classes9.dex */
    public interface OnSendListener {
        void onFailure(Throwable t, String filePath);

        void onProgress(long progress, long total, String filePath);

        void onStart(String acceptIp);

        void onSuccess(String filePath);
    }

    public FileSendUnit(Socket socket) {
        this.mSocket = socket;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            init();
            try {
                parseHeader();
                try {
                    parseThumbnail();
                    try {
                        parseBody();
                        finish();
                        OnSendListener onSendListener = this.listener;
                        if (onSendListener != null && !this.mHasException) {
                            onSendListener.onSuccess(this.filePath);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        notifyException(e);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    notifyException(e2);
                }
            } catch (Exception e3) {
                e3.printStackTrace();
                notifyException(e3);
            }
        } catch (Exception e4) {
            e4.printStackTrace();
            notifyException(e4);
        }
    }

    public void notifyException(Exception e) {
        this.mHasException = true;
        if (this.listener != null) {
            CameraLog.d(TAG, "========" + e.getMessage(), false);
            this.listener.onFailure(e, this.filePath);
        }
        finish();
    }

    @Override // com.xiaopeng.xmart.cargallery.model.transfer.send.Transferable
    public void init() throws Exception {
        this.refreshCount = 0;
        this.mSocket.setSoTimeout(3000);
        byte[] request = new byte[1024];
        InputStream inputStream = this.mSocket.getInputStream();
        int headTotal = 0;
        do {
            int readByte = inputStream.read();
            if (readByte == -1) {
                break;
            }
            request[headTotal] = (byte) readByte;
            headTotal++;
        } while (headTotal != request.length);
        String requestStr = FileUtils.byteToStr(request);
        String str = TAG;
        CameraLog.d(str, requestStr, false);
        this.filePath = requestStr;
        this.mOutputStream = this.mSocket.getOutputStream();
        CameraLog.d(str, "==========requestFile: " + this.filePath, false);
    }

    @Override // com.xiaopeng.xmart.cargallery.model.transfer.send.Transferable
    public void parseHeader() throws Exception {
        File file = new File(this.filePath);
        this.file = file;
        this.fileSize = file.length();
        FileHeadInfo headInfo = new FileHeadInfo();
        headInfo.setFileName(this.file.getName());
        headInfo.setFileSize(this.fileSize);
        String jsonStr = GsonUtils.convertVO2String(headInfo);
        byte[] headbytes = new byte[10240];
        byte[] contentbyte = jsonStr.getBytes();
        for (int i = 0; i < Math.min(contentbyte.length, headbytes.length); i++) {
            headbytes[i] = contentbyte[i];
        }
        this.mOutputStream.write(headbytes);
    }

    @Override // com.xiaopeng.xmart.cargallery.model.transfer.send.Transferable
    public void parseThumbnail() throws Exception {
        byte[] thumbnail = new byte[40960];
        CameraLog.d(TAG, "fetch the file thumbnail length:" + thumbnail.length);
        this.mOutputStream.write(thumbnail);
    }

    @Override // com.xiaopeng.xmart.cargallery.model.transfer.send.Transferable
    public void parseBody() throws Exception {
        if (!this.file.exists()) {
            CameraLog.d(TAG, "file not exit!!!", false);
            return;
        }
        this.fis = new FileInputStream(this.file);
        byte[] bytes = new byte[4096];
        long total = 0;
        while (true) {
            int len = this.fis.read(bytes);
            if (len == -1) {
                break;
            }
            this.mOutputStream.write(bytes, 0, len);
            total += len;
            int i = this.refreshCount + 1;
            this.refreshCount = i;
            OnSendListener onSendListener = this.listener;
            if (onSendListener != null && i % 500 == 0) {
                onSendListener.onProgress(total, this.fileSize, this.filePath);
            }
        }
        OnSendListener onSendListener2 = this.listener;
        if (onSendListener2 != null) {
            onSendListener2.onProgress(total, this.fileSize, this.filePath);
        }
        this.mOutputStream.flush();
    }

    @Override // com.xiaopeng.xmart.cargallery.model.transfer.send.Transferable
    public void finish() {
        FileInputStream fileInputStream = this.fis;
        if (fileInputStream != null) {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        OutputStream outputStream = this.mOutputStream;
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        Socket socket = this.mSocket;
        if (socket != null && socket.isConnected()) {
            try {
                this.mSocket.close();
            } catch (IOException e3) {
                e3.printStackTrace();
            }
        }
        CameraLog.i(TAG, "FileSender close mSocket######>>>", false);
    }

    public void setListener(OnSendListener listener) {
        this.listener = listener;
    }
}
