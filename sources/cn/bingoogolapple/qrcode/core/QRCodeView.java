package cn.bingoogolapple.qrcode.core;

import android.content.Context;
import android.hardware.Camera;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import cn.bingoogolapple.qrcode.core.ProcessDataTask;
/* loaded from: classes.dex */
public abstract class QRCodeView extends RelativeLayout implements Camera.PreviewCallback, ProcessDataTask.Delegate {
    protected Camera mCamera;
    protected Delegate mDelegate;
    protected Handler mHandler;
    private Runnable mOneShotPreviewCallbackTask;
    protected CameraPreview mPreview;
    protected ProcessDataTask mProcessDataTask;
    protected ScanBoxView mScanBoxView;
    protected boolean mSpotAble;

    /* loaded from: classes.dex */
    public interface Delegate {
        void onScanQRCodeOpenCameraError();

        void onScanQRCodeSuccess(String str);
    }

    public QRCodeView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public QRCodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mSpotAble = false;
        this.mOneShotPreviewCallbackTask = new Runnable() { // from class: cn.bingoogolapple.qrcode.core.QRCodeView.2
            @Override // java.lang.Runnable
            public void run() {
                if (QRCodeView.this.mCamera != null && QRCodeView.this.mSpotAble) {
                    try {
                        QRCodeView.this.mCamera.setOneShotPreviewCallback(QRCodeView.this);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        this.mHandler = new Handler();
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        this.mPreview = new CameraPreview(getContext());
        ScanBoxView scanBoxView = new ScanBoxView(getContext());
        this.mScanBoxView = scanBoxView;
        scanBoxView.initCustomAttrs(context, attrs);
        this.mPreview.setId(R.id.bgaqrcode_camera_preview);
        addView(this.mPreview);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(context, attrs);
        layoutParams.addRule(6, this.mPreview.getId());
        layoutParams.addRule(8, this.mPreview.getId());
        addView(this.mScanBoxView, layoutParams);
    }

    public void setDelegate(Delegate delegate) {
        this.mDelegate = delegate;
    }

    public ScanBoxView getScanBoxView() {
        return this.mScanBoxView;
    }

    public void showScanRect() {
        ScanBoxView scanBoxView = this.mScanBoxView;
        if (scanBoxView != null) {
            scanBoxView.setVisibility(0);
        }
    }

    public void hiddenScanRect() {
        ScanBoxView scanBoxView = this.mScanBoxView;
        if (scanBoxView != null) {
            scanBoxView.setVisibility(8);
        }
    }

    public void startCamera() {
        startCamera(0);
    }

    public void startCamera(int cameraFacing) {
        if (this.mCamera != null) {
            return;
        }
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int cameraId = 0; cameraId < Camera.getNumberOfCameras(); cameraId++) {
            Camera.getCameraInfo(cameraId, cameraInfo);
            if (cameraInfo.facing == cameraFacing) {
                startCameraById(cameraId);
                return;
            }
        }
    }

    private void startCameraById(int cameraId) {
        try {
            Camera open = Camera.open(cameraId);
            this.mCamera = open;
            this.mPreview.setCamera(open);
        } catch (Exception e) {
            Delegate delegate = this.mDelegate;
            if (delegate != null) {
                delegate.onScanQRCodeOpenCameraError();
            }
        }
    }

    public void stopCamera() {
        try {
            stopSpotAndHiddenRect();
            if (this.mCamera != null) {
                this.mPreview.stopCameraPreview();
                this.mPreview.setCamera(null);
                this.mCamera.release();
                this.mCamera = null;
            }
        } catch (Exception e) {
        }
    }

    public void startSpot() {
        startSpotDelay(1500);
    }

    public void startSpotDelay(int delay) {
        this.mSpotAble = true;
        startCamera();
        this.mHandler.removeCallbacks(this.mOneShotPreviewCallbackTask);
        this.mHandler.postDelayed(this.mOneShotPreviewCallbackTask, delay);
    }

    public void stopSpot() {
        cancelProcessDataTask();
        this.mSpotAble = false;
        Camera camera = this.mCamera;
        if (camera != null) {
            try {
                camera.setOneShotPreviewCallback(null);
            } catch (Exception e) {
            }
        }
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacks(this.mOneShotPreviewCallbackTask);
        }
    }

    public void stopSpotAndHiddenRect() {
        stopSpot();
        hiddenScanRect();
    }

    public void startSpotAndShowRect() {
        startSpot();
        showScanRect();
    }

    public void openFlashlight() {
        this.mPreview.openFlashlight();
    }

    public void closeFlashlight() {
        this.mPreview.closeFlashlight();
    }

    public void onDestroy() {
        stopCamera();
        this.mHandler = null;
        this.mDelegate = null;
        this.mOneShotPreviewCallbackTask = null;
    }

    protected void cancelProcessDataTask() {
        ProcessDataTask processDataTask = this.mProcessDataTask;
        if (processDataTask != null) {
            processDataTask.cancelTask();
            this.mProcessDataTask = null;
        }
    }

    public void changeToScanBarcodeStyle() {
        if (!this.mScanBoxView.getIsBarcode()) {
            this.mScanBoxView.setIsBarcode(true);
        }
    }

    public void changeToScanQRCodeStyle() {
        if (this.mScanBoxView.getIsBarcode()) {
            this.mScanBoxView.setIsBarcode(false);
        }
    }

    public boolean getIsScanBarcodeStyle() {
        return this.mScanBoxView.getIsBarcode();
    }

    @Override // android.hardware.Camera.PreviewCallback
    public void onPreviewFrame(byte[] data, final Camera camera) {
        if (this.mSpotAble) {
            cancelProcessDataTask();
            this.mProcessDataTask = new ProcessDataTask(camera, data, this) { // from class: cn.bingoogolapple.qrcode.core.QRCodeView.1
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // android.os.AsyncTask
                public void onPostExecute(String result) {
                    if (QRCodeView.this.mSpotAble) {
                        if (QRCodeView.this.mDelegate != null && !TextUtils.isEmpty(result)) {
                            try {
                                QRCodeView.this.mDelegate.onScanQRCodeSuccess(result);
                                return;
                            } catch (Exception e) {
                                return;
                            }
                        }
                        try {
                            camera.setOneShotPreviewCallback(QRCodeView.this);
                        } catch (Exception e2) {
                        }
                    }
                }
            }.perform();
        }
    }
}
