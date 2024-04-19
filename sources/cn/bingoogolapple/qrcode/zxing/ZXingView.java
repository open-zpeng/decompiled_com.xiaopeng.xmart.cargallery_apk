package cn.bingoogolapple.qrcode.zxing;

import android.content.Context;
import android.util.AttributeSet;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import com.google.zxing.MultiFormatReader;
/* loaded from: classes.dex */
public class ZXingView extends QRCodeView {
    private MultiFormatReader mMultiFormatReader;

    public ZXingView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ZXingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initMultiFormatReader();
    }

    private void initMultiFormatReader() {
        MultiFormatReader multiFormatReader = new MultiFormatReader();
        this.mMultiFormatReader = multiFormatReader;
        multiFormatReader.setHints(QRCodeDecoder.HINTS);
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:30:? A[RETURN, SYNTHETIC] */
    @Override // cn.bingoogolapple.qrcode.core.ProcessDataTask.Delegate
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.lang.String processData(byte[] r17, int r18, int r19, boolean r20) {
        /*
            r16 = this;
            r1 = r16
            r2 = 0
            r3 = 0
            r0 = 0
            cn.bingoogolapple.qrcode.core.ScanBoxView r4 = r1.mScanBoxView     // Catch: java.lang.Throwable -> L54 java.lang.Exception -> L58
            r14 = r19
            android.graphics.Rect r4 = r4.getScanBoxAreaRect(r14)     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
            if (r4 == 0) goto L2a
            com.google.zxing.PlanarYUVLuminanceSource r15 = new com.google.zxing.PlanarYUVLuminanceSource     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
            int r9 = r4.left     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
            int r10 = r4.top     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
            int r11 = r4.width()     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
            int r12 = r4.height()     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
            r13 = 0
            r5 = r15
            r6 = r17
            r7 = r18
            r8 = r19
            r5.<init>(r6, r7, r8, r9, r10, r11, r12, r13)     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
            r0 = r15
            goto L3e
        L2a:
            com.google.zxing.PlanarYUVLuminanceSource r15 = new com.google.zxing.PlanarYUVLuminanceSource     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
            r9 = 0
            r10 = 0
            r13 = 0
            r5 = r15
            r6 = r17
            r7 = r18
            r8 = r19
            r11 = r18
            r12 = r19
            r5.<init>(r6, r7, r8, r9, r10, r11, r12, r13)     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
            r0 = r15
        L3e:
            com.google.zxing.MultiFormatReader r5 = r1.mMultiFormatReader     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
            com.google.zxing.BinaryBitmap r6 = new com.google.zxing.BinaryBitmap     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
            com.google.zxing.common.HybridBinarizer r7 = new com.google.zxing.common.HybridBinarizer     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
            r7.<init>(r0)     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
            r6.<init>(r7)     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
            com.google.zxing.Result r5 = r5.decodeWithState(r6)     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
            r3 = r5
            goto L5e
        L50:
            r0 = move-exception
            goto L6b
        L52:
            r0 = move-exception
            goto L5b
        L54:
            r0 = move-exception
            r14 = r19
            goto L6b
        L58:
            r0 = move-exception
            r14 = r19
        L5b:
            r0.printStackTrace()     // Catch: java.lang.Throwable -> L50
        L5e:
            com.google.zxing.MultiFormatReader r0 = r1.mMultiFormatReader
            r0.reset()
            if (r3 == 0) goto L6a
            java.lang.String r2 = r3.getText()
        L6a:
            return r2
        L6b:
            com.google.zxing.MultiFormatReader r4 = r1.mMultiFormatReader
            r4.reset()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.bingoogolapple.qrcode.zxing.ZXingView.processData(byte[], int, int, boolean):java.lang.String");
    }
}
