package com.xiaopeng.lib.framework.account;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.xiaopeng.lib.framework.account.IUserInfoChangedListener;
import com.xiaopeng.lib.framework.account.IXpCallback;
import com.xiaopeng.lib.framework.account.info.UserInfoImpl;
/* loaded from: classes.dex */
public interface IXpAccountService extends IInterface {
    UserInfoImpl getUserInfo(long j) throws RemoteException;

    void init(long j, String str) throws RemoteException;

    void requestAction(long j, int i, Bundle bundle) throws RemoteException;

    void requestOAuth(long j, String str, IXpCallback iXpCallback) throws RemoteException;

    void requestOTP(long j, String str, String str2, IXpCallback iXpCallback) throws RemoteException;

    void subscribe(long j, Bundle bundle, IUserInfoChangedListener iUserInfoChangedListener) throws RemoteException;

    void unsubscribe(long j, Bundle bundle, IUserInfoChangedListener iUserInfoChangedListener) throws RemoteException;

    /* loaded from: classes.dex */
    public static abstract class Stub extends Binder implements IXpAccountService {
        private static final String DESCRIPTOR = "com.xiaopeng.lib.framework.account.IXpAccountService";
        static final int TRANSACTION_getUserInfo = 2;
        static final int TRANSACTION_init = 1;
        static final int TRANSACTION_requestAction = 4;
        static final int TRANSACTION_requestOAuth = 3;
        static final int TRANSACTION_requestOTP = 7;
        static final int TRANSACTION_subscribe = 5;
        static final int TRANSACTION_unsubscribe = 6;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IXpAccountService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IXpAccountService)) {
                return (IXpAccountService) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            Bundle _arg2;
            Bundle _arg1;
            Bundle _arg12;
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    long _arg0 = data.readLong();
                    String _arg13 = data.readString();
                    init(_arg0, _arg13);
                    reply.writeNoException();
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    long _arg02 = data.readLong();
                    UserInfoImpl _result = getUserInfo(_arg02);
                    reply.writeNoException();
                    if (_result != null) {
                        reply.writeInt(1);
                        _result.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    long _arg03 = data.readLong();
                    String _arg14 = data.readString();
                    IXpCallback _arg22 = IXpCallback.Stub.asInterface(data.readStrongBinder());
                    requestOAuth(_arg03, _arg14, _arg22);
                    reply.writeNoException();
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    long _arg04 = data.readLong();
                    int _arg15 = data.readInt();
                    if (data.readInt() != 0) {
                        _arg2 = (Bundle) Bundle.CREATOR.createFromParcel(data);
                    } else {
                        _arg2 = null;
                    }
                    requestAction(_arg04, _arg15, _arg2);
                    reply.writeNoException();
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    long _arg05 = data.readLong();
                    if (data.readInt() != 0) {
                        _arg1 = (Bundle) Bundle.CREATOR.createFromParcel(data);
                    } else {
                        _arg1 = null;
                    }
                    IUserInfoChangedListener _arg23 = IUserInfoChangedListener.Stub.asInterface(data.readStrongBinder());
                    subscribe(_arg05, _arg1, _arg23);
                    reply.writeNoException();
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    long _arg06 = data.readLong();
                    if (data.readInt() != 0) {
                        _arg12 = (Bundle) Bundle.CREATOR.createFromParcel(data);
                    } else {
                        _arg12 = null;
                    }
                    IUserInfoChangedListener _arg24 = IUserInfoChangedListener.Stub.asInterface(data.readStrongBinder());
                    unsubscribe(_arg06, _arg12, _arg24);
                    reply.writeNoException();
                    return true;
                case 7:
                    data.enforceInterface(DESCRIPTOR);
                    long _arg07 = data.readLong();
                    String _arg16 = data.readString();
                    String _arg25 = data.readString();
                    IXpCallback _arg3 = IXpCallback.Stub.asInterface(data.readStrongBinder());
                    requestOTP(_arg07, _arg16, _arg25, _arg3);
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes.dex */
        private static class Proxy implements IXpAccountService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // com.xiaopeng.lib.framework.account.IXpAccountService
            public void init(long timestamp, String appId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(timestamp);
                    _data.writeString(appId);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.xiaopeng.lib.framework.account.IXpAccountService
            public UserInfoImpl getUserInfo(long timestamp) throws RemoteException {
                UserInfoImpl _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(timestamp);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = UserInfoImpl.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.xiaopeng.lib.framework.account.IXpAccountService
            public void requestOAuth(long timestamp, String appId, IXpCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(timestamp);
                    _data.writeString(appId);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.xiaopeng.lib.framework.account.IXpAccountService
            public void requestAction(long timestamp, int pAction, Bundle pBundle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(timestamp);
                    _data.writeInt(pAction);
                    if (pBundle != null) {
                        _data.writeInt(1);
                        pBundle.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.xiaopeng.lib.framework.account.IXpAccountService
            public void subscribe(long timestamp, Bundle pBundle, IUserInfoChangedListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(timestamp);
                    if (pBundle != null) {
                        _data.writeInt(1);
                        pBundle.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.xiaopeng.lib.framework.account.IXpAccountService
            public void unsubscribe(long timestamp, Bundle pBundle, IUserInfoChangedListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(timestamp);
                    if (pBundle != null) {
                        _data.writeInt(1);
                        pBundle.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.xiaopeng.lib.framework.account.IXpAccountService
            public void requestOTP(long timestamp, String appId, String deviceID, IXpCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(timestamp);
                    _data.writeString(appId);
                    _data.writeString(deviceID);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }
    }
}
