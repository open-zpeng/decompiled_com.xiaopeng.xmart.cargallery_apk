package cn.bingoogolapple.qrcode.core;

import android.content.Context;
import android.graphics.Point;
import android.hardware.Camera;
import android.view.Display;
import android.view.WindowManager;
import com.alibaba.fastjson.asm.Opcodes;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
/* loaded from: classes.dex */
final class CameraConfigurationManager {
    private static final Pattern COMMA_PATTERN = Pattern.compile(",");
    private static final int TEN_DESIRED_ZOOM = 27;
    private Point cameraResolution;
    private final Context mContext;
    private Point mScreenResolution;

    public CameraConfigurationManager(Context context) {
        this.mContext = context;
    }

    public void initFromCameraParameters(Camera camera) {
        Camera.Parameters parameters = camera.getParameters();
        WindowManager manager = (WindowManager) this.mContext.getSystemService("window");
        Display display = manager.getDefaultDisplay();
        this.mScreenResolution = new Point(display.getWidth(), display.getHeight());
        Point screenResolutionForCamera = new Point();
        screenResolutionForCamera.x = this.mScreenResolution.x;
        screenResolutionForCamera.y = this.mScreenResolution.y;
        if (this.mScreenResolution.x < this.mScreenResolution.y) {
            screenResolutionForCamera.x = this.mScreenResolution.y;
            screenResolutionForCamera.y = this.mScreenResolution.x;
        }
        this.cameraResolution = getCameraResolution(parameters, screenResolutionForCamera);
    }

    public Point getCameraResolution() {
        return this.cameraResolution;
    }

    public void setDesiredCameraParameters(Camera camera) {
        Camera.Parameters parameters = camera.getParameters();
        parameters.setPreviewSize(this.cameraResolution.x, this.cameraResolution.y);
        setZoom(parameters);
        camera.setDisplayOrientation(getDisplayOrientation());
        camera.setParameters(parameters);
    }

    public void openFlashlight(Camera camera) {
        doSetTorch(camera, true);
    }

    public void closeFlashlight(Camera camera) {
        doSetTorch(camera, false);
    }

    private void doSetTorch(Camera camera, boolean newSetting) {
        String flashMode;
        Camera.Parameters parameters = camera.getParameters();
        if (newSetting) {
            flashMode = findSettableValue(parameters.getSupportedFlashModes(), "torch", "on");
        } else {
            flashMode = findSettableValue(parameters.getSupportedFlashModes(), "off");
        }
        if (flashMode != null) {
            parameters.setFlashMode(flashMode);
        }
        camera.setParameters(parameters);
    }

    private static String findSettableValue(Collection<String> supportedValues, String... desiredValues) {
        if (supportedValues == null) {
            return null;
        }
        for (String desiredValue : desiredValues) {
            if (supportedValues.contains(desiredValue)) {
                return desiredValue;
            }
        }
        return null;
    }

    public int getDisplayOrientation() {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(0, info);
        WindowManager wm = (WindowManager) this.mContext.getSystemService("window");
        Display display = wm.getDefaultDisplay();
        int rotation = display.getRotation();
        int degrees = 0;
        switch (rotation) {
            case 0:
                degrees = 0;
                break;
            case 1:
                degrees = 90;
                break;
            case 2:
                degrees = Opcodes.GETFIELD;
                break;
            case 3:
                degrees = 270;
                break;
        }
        if (info.facing == 1) {
            int result = (info.orientation + degrees) % 360;
            return (360 - result) % 360;
        }
        int result2 = ((info.orientation - degrees) + 360) % 360;
        return result2;
    }

    private static Point getCameraResolution(Camera.Parameters parameters, Point screenResolution) {
        Point cameraResolution = findBestPreviewSizeValue(parameters.getSupportedPreviewSizes(), screenResolution);
        if (cameraResolution == null) {
            return new Point((screenResolution.x >> 3) << 3, (screenResolution.y >> 3) << 3);
        }
        return cameraResolution;
    }

    private static Point findBestPreviewSizeValue(List<Camera.Size> supportSizeList, Point screenResolution) {
        int bestX = 0;
        int bestY = 0;
        int diff = Integer.MAX_VALUE;
        Iterator<Camera.Size> it = supportSizeList.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Camera.Size previewSize = it.next();
            int newX = previewSize.width;
            int newY = previewSize.height;
            int newDiff = Math.abs(newX - screenResolution.x) + Math.abs(newY - screenResolution.y);
            if (newDiff == 0) {
                bestX = newX;
                bestY = newY;
                break;
            } else if (newDiff < diff) {
                bestX = newX;
                bestY = newY;
                diff = newDiff;
            }
        }
        if (bestX > 0 && bestY > 0) {
            return new Point(bestX, bestY);
        }
        return null;
    }

    private static int findBestMotZoomValue(CharSequence stringValues, int tenDesiredZoom) {
        String[] split;
        int tenBestValue = 0;
        for (String stringValue : COMMA_PATTERN.split(stringValues)) {
            try {
                double value = Double.parseDouble(stringValue.trim());
                int tenValue = (int) (10.0d * value);
                if (Math.abs(tenDesiredZoom - value) < Math.abs(tenDesiredZoom - tenBestValue)) {
                    tenBestValue = tenValue;
                }
            } catch (NumberFormatException e) {
                return tenDesiredZoom;
            }
        }
        return tenBestValue;
    }

    private void setZoom(Camera.Parameters parameters) {
        String zoomSupportedString = parameters.get("zoom-supported");
        if (zoomSupportedString != null && !Boolean.parseBoolean(zoomSupportedString)) {
            return;
        }
        int tenDesiredZoom = 27;
        String maxZoomString = parameters.get("max-zoom");
        if (maxZoomString != null) {
            try {
                int tenMaxZoom = (int) (Double.parseDouble(maxZoomString) * 10.0d);
                if (27 > tenMaxZoom) {
                    tenDesiredZoom = tenMaxZoom;
                }
            } catch (NumberFormatException e) {
            }
        }
        String takingPictureZoomMaxString = parameters.get("taking-picture-zoom-max");
        if (takingPictureZoomMaxString != null) {
            try {
                int tenMaxZoom2 = Integer.parseInt(takingPictureZoomMaxString);
                if (tenDesiredZoom > tenMaxZoom2) {
                    tenDesiredZoom = tenMaxZoom2;
                }
            } catch (NumberFormatException e2) {
            }
        }
        String motZoomValuesString = parameters.get("mot-zoom-values");
        if (motZoomValuesString != null) {
            tenDesiredZoom = findBestMotZoomValue(motZoomValuesString, tenDesiredZoom);
        }
        String motZoomStepString = parameters.get("mot-zoom-step");
        if (motZoomStepString != null) {
            try {
                double motZoomStep = Double.parseDouble(motZoomStepString.trim());
                int tenZoomStep = (int) (motZoomStep * 10.0d);
                if (tenZoomStep > 1) {
                    tenDesiredZoom -= tenDesiredZoom % tenZoomStep;
                }
            } catch (NumberFormatException e3) {
            }
        }
        if (maxZoomString != null || motZoomValuesString != null) {
            parameters.set("zoom", String.valueOf(tenDesiredZoom / 10.0d));
        }
        if (takingPictureZoomMaxString != null) {
            parameters.set("taking-picture-zoom", tenDesiredZoom);
        }
    }
}
