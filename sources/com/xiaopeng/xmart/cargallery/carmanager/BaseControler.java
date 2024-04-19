package com.xiaopeng.xmart.cargallery.carmanager;

import android.car.Car;
import android.car.CarManagerBase;
import android.car.CarNotConnectedException;
/* loaded from: classes6.dex */
public abstract class BaseControler {
    public CarManagerBase mCarManagerBase;

    public abstract void initCarManager(Car car, String serviceName) throws CarNotConnectedException;

    public CarManagerBase getCarManagerBase() {
        return this.mCarManagerBase;
    }
}
