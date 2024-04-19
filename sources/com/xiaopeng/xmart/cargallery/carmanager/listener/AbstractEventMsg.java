package com.xiaopeng.xmart.cargallery.carmanager.listener;
/* loaded from: classes7.dex */
public abstract class AbstractEventMsg<T> implements IEventMsg<T> {
    private T mData;

    @Override // com.xiaopeng.xmart.cargallery.carmanager.listener.IEventMsg
    public void setData(T data) {
        this.mData = data;
    }

    @Override // com.xiaopeng.xmart.cargallery.carmanager.listener.IEventMsg
    public T getData() {
        return this.mData;
    }

    public String toString() {
        StringBuilder append;
        String str;
        if (this.mData != null) {
            append = new StringBuilder().append(getClass().getSimpleName()).append(" = ");
            str = this.mData.toString();
        } else {
            append = new StringBuilder().append(getClass().getSimpleName());
            str = " = empty content";
        }
        return append.append(str).toString();
    }
}
