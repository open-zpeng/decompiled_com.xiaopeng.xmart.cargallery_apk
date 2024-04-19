package com.xiaopeng.xmart.cargallery.view;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.appcompat.app.AppCompatActivity;
import com.xiaopeng.xmart.cargallery.CameraLog;
import com.xiaopeng.xmart.cargallery.carmanager.CarClientWrapper;
import com.xiaopeng.xmart.cargallery.carmanager.listener.IMcuController;
import com.xiaopeng.xmart.cargallery.carmanager.listener.IVcuController;
import com.xiaopeng.xmart.cargallery.helper.CarTypeHelper;
import com.xiaopeng.xmart.cargallery.helper.ThreadPoolHelper;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
/* loaded from: classes17.dex */
public abstract class GalleryBaseActivity extends AppCompatActivity {
    private static final String ACTION_ACTIVITY_CHANGED = "com.xiaopeng.intent.action.ACTIVITY_CHANGED";
    private static final String EXTRA_COMPONENT = "android.intent.extra.COMPONENT";
    public static final String EXTRA_FLAGS = "android.intent.extra.FLAGS";
    public static final String EXTRA_PROGRAM = "android.intent.extra.mini.PROGRAM";
    public static final int FLAG_ACTIVITY_LAUNCH_DORPDOWN = 128;
    public static final int FLAG_ACTIVITY_LAUNCH_MINI_PROGRAM = 512;
    public static final int FLAG_ACTIVITY_LAUNCH_PANEL = 256;
    private static final String TAG = "GalleryBaseActivity";
    private ActivityChangeReceiver mActivityChangeReceiver = new ActivityChangeReceiver();
    private IVcuController mIVcuController;

    /* loaded from: classes17.dex */
    private class ActivityChangeReceiver extends BroadcastReceiver {
        private ActivityChangeReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(GalleryBaseActivity.ACTION_ACTIVITY_CHANGED)) {
                String componentName = intent.getStringExtra(GalleryBaseActivity.EXTRA_COMPONENT);
                if (!TextUtils.isEmpty(componentName)) {
                    ComponentName component = ComponentName.unflattenFromString(componentName);
                    int flags = intent.getIntExtra(GalleryBaseActivity.EXTRA_FLAGS, 0);
                    boolean isPanelActivity = (flags & 256) == 256;
                    boolean isDropdownActivity = (flags & 128) == 128;
                    CameraLog.d(GalleryBaseActivity.TAG, "  isPanelActivity " + isPanelActivity + ",isDropdownActivity:" + isDropdownActivity, false);
                    if (GalleryBaseActivity.this.isUserLeaveSensitive() && !component.getPackageName().equals(GalleryBaseActivity.this.getPackageName()) && !isPanelActivity && !isDropdownActivity) {
                        CameraLog.d(GalleryBaseActivity.TAG, "user leave gallery,finish.", false);
                        GalleryBaseActivity.this.finish();
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerEventBus();
        ThreadPoolHelper.getInstance().execute(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.view.-$$Lambda$GalleryBaseActivity$YLH735JPSPxhjQQQGs71EiIQAq0
            @Override // java.lang.Runnable
            public final void run() {
                GalleryBaseActivity.this.lambda$onCreate$0$GalleryBaseActivity();
            }
        });
        if (isUserLeaveSensitive()) {
            IntentFilter intentFilter = new IntentFilter(ACTION_ACTIVITY_CHANGED);
            registerReceiver(this.mActivityChangeReceiver, intentFilter);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: initController */
    public void lambda$onCreate$0$GalleryBaseActivity() {
        CameraLog.i(TAG, "  initController ", false);
        CarClientWrapper carClientWrapper = CarClientWrapper.getInstance();
        this.mIVcuController = (IVcuController) carClientWrapper.getController(CarClientWrapper.XP_VCU_SERVICE);
    }

    public boolean isGearLevelSensitive() {
        return CarTypeHelper.isXMartQ5();
    }

    public boolean isUserLeaveSensitive() {
        return CarTypeHelper.isXMartQ5();
    }

    public void onGearLevelChanged(int gearLevel) {
        CameraLog.d(TAG, "onGearChanged gearLevel:" + gearLevel, false);
        if (isGearLevelSensitive() && gearLevel != 4) {
            CameraLog.d(TAG, "leave gear level p,finish.", false);
            finish();
        }
    }

    public IVcuController getIVcuController() {
        return this.mIVcuController;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveEventBusGearChanged(IVcuController.CarGearLevelEventMsg msg) {
        if (msg == null) {
            CameraLog.e(TAG, "onReceiveEventBusGearChanged, msg is null", false);
            return;
        }
        CameraLog.d(TAG, "CarService VCU CarGearLevelEventMsg = " + msg.toString(), false);
        if (isGearLevelSensitive()) {
            onGearLevelChanged(msg.getData().intValue());
        }
    }

    public boolean isIGOffSensitive() {
        return CarTypeHelper.isXMartQ5();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveEventIGStatus(IMcuController.IGStatusChangeEvent msg) {
        if (msg == null) {
            CameraLog.e(TAG, "onReceiveEventIGStatus, msg is null", false);
            return;
        }
        int igValue = msg.getData().intValue();
        CameraLog.d(TAG, "igValue IS " + igValue, false);
        onIgStatusChange(igValue);
    }

    public void onIgStatusChange(int igStatus) {
        CameraLog.d(TAG, "onIgStatusChange:" + igStatus, false);
        if (isIGOffSensitive() && igStatus == 0) {
            CameraLog.d(TAG, "receive IG_OFF,call finish", false);
            finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        CameraLog.d(TAG, "onDestroy", false);
        if (isUserLeaveSensitive()) {
            unregisterReceiver(this.mActivityChangeReceiver);
        }
        unRegisterEventBus();
    }

    public void registerEventBus() {
        CameraLog.d(TAG, "registerEventBus " + getLocalClassName(), false);
        if (!EventBus.getDefault().isRegistered(this)) {
            CameraLog.d(TAG, "registerEventBus register " + getLocalClassName(), false);
            EventBus.getDefault().register(this);
        }
    }

    public void unRegisterEventBus() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
