package cn.bingoogolapple.qrcode.core;

import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Build;
/* loaded from: classes.dex */
public class ProcessDataTask extends AsyncTask<Void, Void, String> {
    private Camera mCamera;
    private byte[] mData;
    private Delegate mDelegate;

    /* loaded from: classes.dex */
    public interface Delegate {
        String processData(byte[] bArr, int i, int i2, boolean z);
    }

    public ProcessDataTask(Camera camera, byte[] data, Delegate delegate) {
        this.mCamera = camera;
        this.mData = data;
        this.mDelegate = delegate;
    }

    public ProcessDataTask perform() {
        if (Build.VERSION.SDK_INT >= 11) {
            executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
        } else {
            execute(new Void[0]);
        }
        return this;
    }

    public void cancelTask() {
        if (getStatus() != AsyncTask.Status.FINISHED) {
            cancel(true);
        }
    }

    @Override // android.os.AsyncTask
    protected void onCancelled() {
        super.onCancelled();
        this.mDelegate = null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public String doInBackground(Void... params) {
        Camera.Parameters parameters = this.mCamera.getParameters();
        Camera.Size size = parameters.getPreviewSize();
        int width = size.width;
        int height = size.height;
        byte[] rotatedData = new byte[this.mData.length];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                rotatedData[(((x * height) + height) - y) - 1] = this.mData[(y * width) + x];
            }
        }
        try {
            Delegate delegate = this.mDelegate;
            if (delegate != null) {
                return delegate.processData(rotatedData, height, width, false);
            }
            return null;
        } catch (Exception e) {
            try {
                return this.mDelegate.processData(rotatedData, height, width, true);
            } catch (Exception e2) {
                return null;
            }
        }
    }
}
