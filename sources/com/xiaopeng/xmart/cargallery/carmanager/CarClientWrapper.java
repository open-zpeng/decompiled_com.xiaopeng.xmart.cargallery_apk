package com.xiaopeng.xmart.cargallery.carmanager;

import android.car.Car;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.xiaopeng.xmart.cargallery.App;
import com.xiaopeng.xmart.cargallery.CameraLog;
import com.xiaopeng.xmart.cargallery.carmanager.impl.CiuController;
import com.xiaopeng.xmart.cargallery.carmanager.impl.McuController;
import com.xiaopeng.xmart.cargallery.carmanager.impl.VcuController;
import com.xiaopeng.xmart.cargallery.helper.ThreadPoolHelper;
import java.util.Hashtable;
/* loaded from: classes6.dex */
public class CarClientWrapper {
    public static final String XP_AVM_SERVICE = "xp_avm";
    public static final String XP_SCU_SERVICE = "xp_scu";
    public static final String XP_TBOX_SERVICE = "xp_tbox";
    private Car mCarClient;
    private static final String TAG = CarClientWrapper.class.getSimpleName();
    public static final String XP_CIU_SERVICE = "xp_ciu";
    public static final String XP_VCU_SERVICE = "xp_vcu";
    public static final String XP_MCU_SERVICE = "xp_mcu";
    private static final String[] CAR_SVC_ARRAY = {XP_CIU_SERVICE, XP_VCU_SERVICE, XP_MCU_SERVICE};
    private static CarClientWrapper sInstance = null;
    private final Object mCarClientReady = new Object();
    private boolean mIsCarSvcConnected = false;
    private final Object mControllerLock = new Object();
    private final Hashtable<String, BaseCarController<?>> mControllers = new Hashtable<>();
    private final ServiceConnection mCarConnectionCb = new ServiceConnection() { // from class: com.xiaopeng.xmart.cargallery.carmanager.CarClientWrapper.1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName name, IBinder service) {
            CameraLog.d(CarClientWrapper.TAG, "onCarServiceConnected", false);
            CarClientWrapper.this.initCarControllers();
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName name) {
            CameraLog.d(CarClientWrapper.TAG, "onCarServiceDisconnected", false);
            CarClientWrapper.this.mIsCarSvcConnected = false;
            CarClientWrapper.this.reconnectToCar();
        }
    };

    public static CarClientWrapper getInstance() {
        if (sInstance == null) {
            synchronized (CarClientWrapper.class) {
                if (sInstance == null) {
                    sInstance = new CarClientWrapper();
                }
            }
        }
        return sInstance;
    }

    public void connectToCar() {
        if (!this.mIsCarSvcConnected) {
            Car createCar = Car.createCar(App.getInstance(), this.mCarConnectionCb);
            this.mCarClient = createCar;
            createCar.connect();
            CameraLog.d(TAG, "Start to connect Car service", false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reconnectToCar() {
        ThreadPoolHelper.getInstance().postDelayedOnMainThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.carmanager.-$$Lambda$CarClientWrapper$IrbTnwUw6aJcAnZWOAEPDvbFf1w
            @Override // java.lang.Runnable
            public final void run() {
                CarClientWrapper.this.lambda$reconnectToCar$0$CarClientWrapper();
            }
        }, 200L);
    }

    public /* synthetic */ void lambda$reconnectToCar$0$CarClientWrapper() {
        CameraLog.d(TAG, "reconnect to car service", false);
        connectToCar();
    }

    public void disconnect() {
        Car car;
        if (this.mIsCarSvcConnected && (car = this.mCarClient) != null) {
            car.disconnect();
        }
    }

    public boolean isCarServiceConnected() {
        synchronized (this.mCarClientReady) {
            while (!this.mIsCarSvcConnected) {
                try {
                    CameraLog.d(TAG, "Waiting car service connected", false);
                    this.mCarClientReady.wait();
                } catch (InterruptedException e) {
                    CameraLog.e(TAG, e.getMessage());
                }
            }
        }
        return true;
    }

    public BaseCarController getController(String serviceName) {
        BaseCarController controller;
        Car carClient = getCarClient();
        synchronized (this.mControllerLock) {
            controller = this.mControllers.get(serviceName);
            if (controller == null) {
                controller = createCarController(serviceName, carClient);
                this.mControllers.put(serviceName, controller);
            }
        }
        return controller;
    }

    private Car getCarClient() {
        Car car;
        synchronized (this.mCarClientReady) {
            while (!this.mIsCarSvcConnected) {
                try {
                    CameraLog.d(TAG, "Waiting car service connecting", false);
                    this.mCarClientReady.wait();
                } catch (InterruptedException e) {
                    CameraLog.e(TAG, e.getMessage());
                }
            }
            car = this.mCarClient;
        }
        return car;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initCarControllers() {
        ThreadPoolHelper.getInstance().execute(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.carmanager.CarClientWrapper.2
            @Override // java.lang.Runnable
            public void run() {
                String[] strArr;
                CameraLog.d(CarClientWrapper.TAG, "initCarControllers start");
                synchronized (CarClientWrapper.this.mControllerLock) {
                    for (String serviceName : CarClientWrapper.CAR_SVC_ARRAY) {
                        final BaseCarController controller = (BaseCarController) CarClientWrapper.this.mControllers.get(serviceName);
                        if (controller != null) {
                            CameraLog.d(CarClientWrapper.TAG, "re-initCarControllers", false);
                            ThreadPoolHelper.getInstance().execute(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.carmanager.CarClientWrapper.2.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    controller.disconnect();
                                    controller.initCarManager(CarClientWrapper.this.mCarClient);
                                }
                            });
                        } else {
                            CarClientWrapper carClientWrapper = CarClientWrapper.this;
                            CarClientWrapper.this.mControllers.put(serviceName, carClientWrapper.createCarController(serviceName, carClientWrapper.mCarClient));
                        }
                    }
                }
                CameraLog.d(CarClientWrapper.TAG, "initCarControllers end");
                synchronized (CarClientWrapper.this.mCarClientReady) {
                    CarClientWrapper.this.mIsCarSvcConnected = true;
                    CarClientWrapper.this.mCarClientReady.notifyAll();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public BaseCarController createCarController(String serviceName, final Car carClient) {
        char c;
        BaseCarController controller;
        switch (serviceName.hashCode()) {
            case -753106168:
                if (serviceName.equals(XP_CIU_SERVICE)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -753096744:
                if (serviceName.equals(XP_MCU_SERVICE)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -753088095:
                if (serviceName.equals(XP_VCU_SERVICE)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                controller = new CiuController();
                break;
            case 1:
                controller = new VcuController();
                break;
            case 2:
                controller = new McuController();
                break;
            default:
                throw new IllegalArgumentException("Can not create controller for " + serviceName);
        }
        final BaseCarController finalController = controller;
        ThreadPoolHelper.getInstance().execute(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.carmanager.-$$Lambda$CarClientWrapper$4CAFOz03J9YVEJyuOKeS429iuOE
            @Override // java.lang.Runnable
            public final void run() {
                BaseCarController.this.initCarManager(carClient);
            }
        });
        return controller;
    }
}
