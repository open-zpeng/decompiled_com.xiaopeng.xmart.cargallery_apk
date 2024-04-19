package com.xiaopeng.xmart.cargallery.carmanager;

import android.car.Car;
import android.car.hardware.CarPropertyValue;
import com.xiaopeng.xmart.cargallery.CameraLog;
import com.xiaopeng.xmart.cargallery.carmanager.listener.IEventMsg;
import com.xiaopeng.xmart.cargallery.helper.ThreadPoolHelper;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.greenrobot.eventbus.EventBus;
/* loaded from: classes6.dex */
public abstract class BaseCarController<C> {
    private static final String TAG = "BaseCarController";
    protected C mCarManager;
    protected HashMap<Integer, Class<? extends IEventMsg>> mPropertyTypeMap = new HashMap<>();
    private final ConcurrentHashMap<Integer, CarPropertyValue<?>> mCarPropertyMap = new ConcurrentHashMap<>();
    protected final List<Integer> mPropertyIds = getRegisterPropertyIds();

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract void disconnect();

    protected abstract List<Integer> getRegisterPropertyIds();

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract void initCarManager(Car carClient);

    protected abstract void initPropertyTypeMap();

    public BaseCarController() {
        initPropertyTypeMap();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void handleCarEventsUpdate(final CarPropertyValue<?> value) {
        ThreadPoolHelper.getInstance().execute(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.carmanager.-$$Lambda$BaseCarController$jgPzratmC6aF__4SWVUirfXoWJA
            @Override // java.lang.Runnable
            public final void run() {
                BaseCarController.this.lambda$handleCarEventsUpdate$0$BaseCarController(value);
            }
        });
    }

    public /* synthetic */ void lambda$handleCarEventsUpdate$0$BaseCarController(final CarPropertyValue value) {
        this.mCarPropertyMap.put(Integer.valueOf(value.getPropertyId()), value);
        postEventBusMsg(value);
    }

    protected final boolean getBooleanProperty(int propertyId) throws Exception {
        return ((Boolean) getValue(getCarProperty(propertyId))).booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final int getIntProperty(int propertyId) throws Exception {
        return ((Integer) getValue(getCarProperty(propertyId))).intValue();
    }

    protected final int[] getIntArrayProperty(int propertyId) throws Exception {
        return getIntArrayProperty(getCarProperty(propertyId));
    }

    protected final int[] getIntArrayProperty(CarPropertyValue<?> value) {
        Object[] values = (Object[]) getValue(value);
        int[] result = null;
        if (values != null) {
            result = new int[values.length];
            for (int i = 0; i < values.length; i++) {
                Object objValue = values[i];
                if (objValue instanceof Integer) {
                    result[i] = ((Integer) objValue).intValue();
                }
            }
        }
        return result;
    }

    protected final float getFloatProperty(int propertyId) throws Exception {
        return ((Float) getValue(getCarProperty(propertyId))).floatValue();
    }

    protected final float[] getFloatArrayProperty(int propertyId) throws Exception {
        return getFloatArrayProperty(getCarProperty(propertyId));
    }

    protected final float[] getFloatArrayProperty(CarPropertyValue<?> value) {
        Object[] values = (Object[]) getValue(value);
        float[] result = null;
        if (values != null) {
            result = new float[values.length];
            for (int i = 0; i < values.length; i++) {
                Object objValue = values[i];
                if (objValue instanceof Float) {
                    result[i] = ((Float) objValue).floatValue();
                }
            }
        }
        return result;
    }

    private CarPropertyValue<?> getCarProperty(int propertyId) throws Exception {
        CarPropertyValue<?> property = this.mCarPropertyMap.get(Integer.valueOf(propertyId));
        if (property != null) {
            return property;
        }
        throw new Exception("Car property not found");
    }

    private final <E> E getValue(CarPropertyValue<?> value) {
        return (E) value.getValue();
    }

    protected void postEventBusMsg(CarPropertyValue carPropertyValue) {
        Class<? extends IEventMsg> eventClass = this.mPropertyTypeMap.get(Integer.valueOf(carPropertyValue.getPropertyId()));
        if (eventClass == null) {
            CameraLog.d(TAG, "handle unknown event: " + carPropertyValue);
            return;
        }
        try {
            synchronized (eventClass) {
                IEventMsg eventMsg = eventClass.newInstance();
                eventMsg.setData(carPropertyValue.getValue());
                EventBus.getDefault().postSticky(eventMsg);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e2) {
            e2.printStackTrace();
        }
    }
}
