package cn.bingoogolapple.qrcode.zxing;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.core.view.ViewCompat;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.util.EnumMap;
import java.util.Map;
/* loaded from: classes.dex */
public class QRCodeEncoder {
    public static final Map<EncodeHintType, Object> HINTS;

    static {
        EnumMap enumMap = new EnumMap(EncodeHintType.class);
        HINTS = enumMap;
        enumMap.put((EnumMap) EncodeHintType.CHARACTER_SET, (EncodeHintType) "utf-8");
        enumMap.put((EnumMap) EncodeHintType.ERROR_CORRECTION, (EncodeHintType) ErrorCorrectionLevel.H);
        enumMap.put((EnumMap) EncodeHintType.MARGIN, (EncodeHintType) 0);
    }

    private QRCodeEncoder() {
    }

    public static Bitmap syncEncodeQRCode(String content, int size) {
        return syncEncodeQRCode(content, size, ViewCompat.MEASURED_STATE_MASK, -1, null);
    }

    public static Bitmap syncEncodeQRCode(String content, int size, int foregroundColor) {
        return syncEncodeQRCode(content, size, foregroundColor, -1, null);
    }

    public static Bitmap syncEncodeQRCode(String content, int size, int foregroundColor, Bitmap logo) {
        return syncEncodeQRCode(content, size, foregroundColor, -1, logo);
    }

    public static Bitmap syncEncodeQRCode(String content, int size, int foregroundColor, int backgroundColor, Bitmap logo) {
        try {
            BitMatrix matrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, size, size, HINTS);
            int[] pixels = new int[size * size];
            for (int y = 0; y < size; y++) {
                for (int x = 0; x < size; x++) {
                    if (matrix.get(x, y)) {
                        pixels[(y * size) + x] = foregroundColor;
                    } else {
                        pixels[(y * size) + x] = backgroundColor;
                    }
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, size, 0, 0, size, size);
            return addLogoToQRCode(bitmap, logo);
        } catch (Exception e) {
            return null;
        }
    }

    private static Bitmap addLogoToQRCode(Bitmap src, Bitmap logo) {
        if (src == null || logo == null) {
            return src;
        }
        int srcWidth = src.getWidth();
        int srcHeight = src.getHeight();
        int logoWidth = logo.getWidth();
        int logoHeight = logo.getHeight();
        float scaleFactor = ((srcWidth * 1.0f) / 5.0f) / logoWidth;
        Bitmap bitmap = Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888);
        try {
            Canvas canvas = new Canvas(bitmap);
            canvas.drawBitmap(src, 0.0f, 0.0f, (Paint) null);
            canvas.scale(scaleFactor, scaleFactor, srcWidth / 2, srcHeight / 2);
            canvas.drawBitmap(logo, (srcWidth - logoWidth) / 2, (srcHeight - logoHeight) / 2, (Paint) null);
            canvas.save(31);
            canvas.restore();
            return bitmap;
        } catch (Exception e) {
            return null;
        }
    }
}
