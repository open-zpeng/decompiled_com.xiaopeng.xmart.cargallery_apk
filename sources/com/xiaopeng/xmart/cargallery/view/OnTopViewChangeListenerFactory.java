package com.xiaopeng.xmart.cargallery.view;

import android.view.View;
import com.xiaopeng.xmart.cargallery.CameraLog;
import com.xiaopeng.xmart.cargallery.view.DetailActivity;
/* loaded from: classes17.dex */
public class OnTopViewChangeListenerFactory {
    private static final String TAG = "OnTopViewChangeListenerFactory";

    public static DetailActivity.OnTopViewChangeListener createOnTopViewChangeListener(String carType, final View view) {
        CameraLog.d(TAG, "createOnTopViewChangeListener carType:" + carType + ",view:" + view);
        if (view == null) {
            return null;
        }
        char c = 65535;
        switch (carType.hashCode()) {
            case 2560:
                if (carType.equals("Q1")) {
                    c = 4;
                    break;
                }
                break;
            case 2561:
                if (carType.equals("Q2")) {
                    c = 1;
                    break;
                }
                break;
            case 2562:
                if (carType.equals("Q3")) {
                    c = 3;
                    break;
                }
                break;
            case 2564:
                if (carType.equals("Q5")) {
                    c = 0;
                    break;
                }
                break;
            case 2565:
                if (carType.equals("Q6")) {
                    c = 2;
                    break;
                }
                break;
            case 2567:
                if (carType.equals("Q8")) {
                    c = 5;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
            case 1:
            case 2:
            case 3:
                return null;
            case 4:
            case 5:
                return new DetailActivity.OnTopViewChangeListener() { // from class: com.xiaopeng.xmart.cargallery.view.-$$Lambda$OnTopViewChangeListenerFactory$VheZq1SJoa5-eTbULbd7WCKyBhk
                    @Override // com.xiaopeng.xmart.cargallery.view.DetailActivity.OnTopViewChangeListener
                    public final void onTopChange(boolean z, int i) {
                        OnTopViewChangeListenerFactory.lambda$createOnTopViewChangeListener$0(view, z, i);
                    }
                };
            default:
                return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$createOnTopViewChangeListener$0(final View view, boolean force, int visible) {
        CameraLog.d(TAG, "Top view change, force " + force + ", current visibility:" + view.getVisibility());
        if (force) {
            view.setVisibility(visible);
        } else {
            view.setVisibility(view.getVisibility() == 8 ? 0 : 8);
        }
    }
}
