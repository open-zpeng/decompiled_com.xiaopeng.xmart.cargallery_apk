package cn.bingoogolapple.qrcode.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Build;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;
/* loaded from: classes.dex */
public class BGAQRCodeUtil {
    public static Point getScreenResolution(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService("window");
        Display display = wm.getDefaultDisplay();
        Point screenResolution = new Point();
        if (Build.VERSION.SDK_INT >= 13) {
            display.getSize(screenResolution);
        } else {
            screenResolution.set(display.getWidth(), display.getHeight());
        }
        return screenResolution;
    }

    public static int dp2px(Context context, float dpValue) {
        return (int) TypedValue.applyDimension(1, dpValue, context.getResources().getDisplayMetrics());
    }

    public static int sp2px(Context context, float spValue) {
        return (int) TypedValue.applyDimension(2, spValue, context.getResources().getDisplayMetrics());
    }

    public static Bitmap adjustPhotoRotation(Bitmap inputBitmap, int orientationDegree) {
        float outputX;
        float outputY;
        if (inputBitmap == null) {
            return null;
        }
        Matrix matrix = new Matrix();
        matrix.setRotate(orientationDegree, inputBitmap.getWidth() / 2.0f, inputBitmap.getHeight() / 2.0f);
        if (orientationDegree == 90) {
            outputX = inputBitmap.getHeight();
            outputY = 0.0f;
        } else {
            outputX = inputBitmap.getHeight();
            outputY = inputBitmap.getWidth();
        }
        float[] values = new float[9];
        matrix.getValues(values);
        float x1 = values[2];
        float y1 = values[5];
        matrix.postTranslate(outputX - x1, outputY - y1);
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap.getHeight(), inputBitmap.getWidth(), Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        Canvas canvas = new Canvas(outputBitmap);
        canvas.drawBitmap(inputBitmap, matrix, paint);
        return outputBitmap;
    }

    public static Bitmap makeTintBitmap(Bitmap inputBitmap, int tintColor) {
        if (inputBitmap == null) {
            return null;
        }
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap.getWidth(), inputBitmap.getHeight(), inputBitmap.getConfig());
        Canvas canvas = new Canvas(outputBitmap);
        Paint paint = new Paint();
        paint.setColorFilter(new PorterDuffColorFilter(tintColor, PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(inputBitmap, 0.0f, 0.0f, paint);
        return outputBitmap;
    }
}
