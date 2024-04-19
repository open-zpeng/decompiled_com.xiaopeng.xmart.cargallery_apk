package com.xiaopeng.speech.proxy;

import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import com.xiaopeng.speech.ConnectManager;
import com.xiaopeng.speech.ISpeechEngine;
import com.xiaopeng.speech.common.bean.DisableInfoBean;
import com.xiaopeng.speech.common.util.IPCRunner;
import com.xiaopeng.speech.common.util.LogUtils;
import com.xiaopeng.speech.common.util.WorkerHandler;
import com.xiaopeng.speech.coreapi.IWakeupEngine;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/* loaded from: classes.dex */
public class WakeupEngineProxy extends IWakeupEngine.Stub implements ConnectManager.OnConnectCallback {
    private static final String TAG = "WakeupEngineProxy";
    private IPCRunner<IWakeupEngine> mIpcRunner = new IPCRunner<>(TAG);
    private Map<String, DisableInfoBean> disableInfoCache = new ConcurrentHashMap();

    @Override // com.xiaopeng.speech.ConnectManager.OnConnectCallback
    public void onConnect(ISpeechEngine speechEngine) {
        try {
            this.mIpcRunner.setProxy(speechEngine.getWakeupEngine());
            this.mIpcRunner.fetchAll();
            LogUtils.i(TAG, "reset:   onConnect");
            resumeCarSpeechStatus();
        } catch (RemoteException e) {
            LogUtils.e(this, "onConnect exception ", e);
        }
    }

    @Override // com.xiaopeng.speech.ConnectManager.OnConnectCallback
    public void onDisconnect() {
        this.mIpcRunner.setProxy(null);
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void startDialog() {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.1
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine proxy) throws RemoteException {
                proxy.startDialog();
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void stopDialog() {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.2
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine proxy) throws RemoteException {
                proxy.stopDialog();
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void avatarClick(final String greeting) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.3
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine proxy) throws RemoteException {
                proxy.avatarClick(greeting);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void avatarPress() {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.4
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine proxy) throws RemoteException {
                proxy.avatarPress();
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void avatarRelease() {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.5
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine proxy) throws RemoteException {
                proxy.avatarRelease();
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void enableWakeup() {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.6
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine proxy) throws RemoteException {
                proxy.enableWakeup();
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void disableWakeup() {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.7
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine proxy) throws RemoteException {
                proxy.disableWakeup();
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public String[] getWakeupWords() {
        return (String[]) this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, String[]>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.8
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public String[] run(IWakeupEngine proxy) throws RemoteException {
                return proxy.getWakeupWords();
            }
        }, new String[0]);
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void updateMinorWakeupWord(final String word, final String pinyin, final String threshold, final String[] greetings) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.9
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine proxy) throws RemoteException {
                proxy.updateMinorWakeupWord(word, pinyin, threshold, greetings);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public String getMinorWakeupWord() {
        return (String) this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, String>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.10
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public String run(IWakeupEngine proxy) throws RemoteException {
                return proxy.getMinorWakeupWord();
            }
        }, null);
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void updateCommandWakeupWord(final String[] actions, final String[] words, final String[] pinyin, final String[] threshold, final String[] greetings) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.11
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine proxy) throws RemoteException {
                proxy.updateCommandWakeupWord(actions, words, pinyin, threshold, greetings);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void clearCommandWakeupWord() {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.12
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine proxy) throws RemoteException {
                proxy.clearCommandWakeupWord();
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void addCommandWakeupWord(final String[] actions, final String[] words, final String[] pinyin, final String[] threshold, final String[] greetings) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.13
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine proxy) throws RemoteException {
                proxy.addCommandWakeupWord(actions, words, pinyin, threshold, greetings);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void removeCommandWakeupWord(final String[] words) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.14
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine proxy) throws RemoteException {
                proxy.removeCommandWakeupWord(words);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void updateShortcutWakeupWord(final String[] words, final String[] pinyin, final String[] threshold) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.15
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine proxy) throws RemoteException {
                proxy.updateShortcutWakeupWord(words, pinyin, threshold);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void clearShortCutWakeupWord() {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.16
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine proxy) throws RemoteException {
                proxy.clearShortCutWakeupWord();
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void addShortcutWakeupWord(final String[] words, final String[] pinyin, final String[] threshold) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.17
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine proxy) throws RemoteException {
                proxy.addShortcutWakeupWord(words, pinyin, threshold);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void removeShortcutWakeupWord(final String[] words) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.18
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine proxy) throws RemoteException {
                proxy.removeShortcutWakeupWord(words);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void pauseDialog() {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.19
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine proxy) throws RemoteException {
                proxy.pauseDialog();
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void resumeDialog() {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.20
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine proxy) throws RemoteException {
                proxy.resumeDialog();
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public boolean isEnableWakeup() {
        return ((Boolean) this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Boolean>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.21
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Boolean run(IWakeupEngine proxy) throws RemoteException {
                return Boolean.valueOf(proxy.isEnableWakeup());
            }
        }, false)).booleanValue();
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public boolean isDefaultEnableWakeup() {
        return ((Boolean) this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Boolean>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.22
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Boolean run(IWakeupEngine proxy) throws RemoteException {
                return Boolean.valueOf(proxy.isDefaultEnableWakeup());
            }
        }, true)).booleanValue();
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void setDefaultWakeupEnabled(final boolean enable) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.23
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine proxy) throws RemoteException {
                proxy.setDefaultWakeupEnabled(enable);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void enableWakeupEnhance(final IBinder binder) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.24
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine proxy) throws RemoteException {
                IBinder iBinder = binder;
                if (iBinder == null) {
                    proxy.enableWakeupEnhance(proxy.asBinder());
                    return null;
                }
                proxy.enableWakeupEnhance(iBinder);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void disableWakeupEnhance(final IBinder binder) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.25
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine proxy) throws RemoteException {
                IBinder iBinder = binder;
                if (iBinder == null) {
                    proxy.disableWakeupEnhance(proxy.asBinder());
                    return null;
                }
                proxy.disableWakeupEnhance(iBinder);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public boolean isWheelWakeupEnabled() {
        return ((Boolean) this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Boolean>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.26
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Boolean run(IWakeupEngine proxy) throws RemoteException {
                return Boolean.valueOf(proxy.isWheelWakeupEnabled());
            }
        }, true)).booleanValue();
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void setWheelWakeupEnabled(final IBinder binder, final boolean state) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.27
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine proxy) throws RemoteException {
                IBinder iBinder = binder;
                if (iBinder == null) {
                    proxy.setWheelWakeupEnabled(proxy.asBinder(), state);
                    return null;
                }
                proxy.setWheelWakeupEnabled(iBinder, state);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public boolean isDefaultEnableOneshot() {
        return ((Boolean) this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Boolean>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.28
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Boolean run(IWakeupEngine proxy) throws RemoteException {
                return Boolean.valueOf(proxy.isDefaultEnableOneshot());
            }
        }, false)).booleanValue();
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void setDefaultOneshotEnabled(final boolean enable) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.29
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine proxy) throws RemoteException {
                proxy.setDefaultOneshotEnabled(enable);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void enableOneshot() {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.30
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine proxy) throws RemoteException {
                proxy.enableOneshot();
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void disableOneshot() {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.31
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine proxy) throws RemoteException {
                proxy.disableOneshot();
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public boolean isDefaultEnableFastWake() {
        return ((Boolean) this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Boolean>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.32
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Boolean run(IWakeupEngine proxy) throws RemoteException {
                return Boolean.valueOf(proxy.isDefaultEnableFastWake());
            }
        }, false)).booleanValue();
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void setDefaultFastWakeEnabled(final boolean enable) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.33
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine proxy) throws RemoteException {
                proxy.setDefaultFastWakeEnabled(enable);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void enableFastWake() {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.34
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine proxy) throws RemoteException {
                proxy.enableFastWake();
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void disableFastWake() {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.35
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine proxy) throws RemoteException {
                proxy.disableFastWake();
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void stopDialogMessage() {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.36
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine proxy) throws RemoteException {
                proxy.stopDialogMessage();
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void stopDialogReason(final String reason) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.37
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine proxy) throws RemoteException {
                proxy.stopDialogReason(reason);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void enableMainWakeupWord(final IBinder binder) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.38
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine proxy) throws RemoteException {
                IBinder iBinder = binder;
                if (iBinder == null) {
                    proxy.enableMainWakeupWord(proxy.asBinder());
                    return null;
                }
                proxy.enableMainWakeupWord(iBinder);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void disableMainWakeupWord(final IBinder binder) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.39
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine proxy) throws RemoteException {
                IBinder iBinder = binder;
                if (iBinder == null) {
                    proxy.disableMainWakeupWord(proxy.asBinder());
                    return null;
                }
                proxy.disableMainWakeupWord(iBinder);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void enableFastWakeEnhance(final IBinder binder) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.40
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine proxy) throws RemoteException {
                IBinder iBinder = binder;
                if (iBinder == null) {
                    proxy.enableFastWakeEnhance(proxy.asBinder());
                    return null;
                }
                proxy.enableFastWakeEnhance(iBinder);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void disableFastWakeEnhance(final IBinder binder) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.41
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine proxy) throws RemoteException {
                IBinder iBinder = binder;
                if (iBinder == null) {
                    proxy.disableFastWakeEnhance(proxy.asBinder());
                    return null;
                }
                proxy.disableFastWakeEnhance(iBinder);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void enableInterruptWake(final IBinder binder) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.42
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine proxy) throws RemoteException {
                IBinder iBinder = binder;
                if (iBinder == null) {
                    proxy.enableInterruptWake(proxy.asBinder());
                    return null;
                }
                proxy.enableInterruptWake(iBinder);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void disableInterruptWake(final IBinder binder) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.43
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine proxy) throws RemoteException {
                IBinder iBinder = binder;
                if (iBinder == null) {
                    proxy.disableInterruptWake(proxy.asBinder());
                    return null;
                }
                proxy.disableInterruptWake(iBinder);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void startDialogFrom(final String reason) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.44
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine proxy) throws RemoteException {
                proxy.startDialogFrom(reason);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void disableWakeupWithInfo(final IBinder binder, final int type, final String byWho, final String info, final int notifyType) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.45
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine proxy) throws RemoteException {
                IBinder iBinder = binder;
                if (iBinder == null) {
                    proxy.disableWakeupWithInfo(proxy.asBinder(), type, byWho, info, notifyType);
                } else {
                    proxy.disableWakeupWithInfo(iBinder, type, byWho, info, notifyType);
                }
                WakeupEngineProxy wakeupEngineProxy = WakeupEngineProxy.this;
                IBinder iBinder2 = binder;
                if (iBinder2 == null) {
                    iBinder2 = proxy.asBinder();
                }
                wakeupEngineProxy.setDisableInfoCache(iBinder2, type, byWho, info, notifyType);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void enableWakeupWithInfo(final IBinder binder, final int type, final String byWho, final int notifyType) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.46
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine proxy) throws RemoteException {
                IBinder iBinder = binder;
                if (iBinder == null) {
                    proxy.enableWakeupWithInfo(proxy.asBinder(), type, byWho, notifyType);
                } else {
                    proxy.enableWakeupWithInfo(iBinder, type, byWho, notifyType);
                }
                WakeupEngineProxy wakeupEngineProxy = WakeupEngineProxy.this;
                IBinder iBinder2 = binder;
                if (iBinder2 == null) {
                    iBinder2 = proxy.asBinder();
                }
                wakeupEngineProxy.removeDisableInfoCache(iBinder2, type, byWho);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void disableWheelWakeupWithInfo(final IBinder binder, final String byWho, final String info, final int notifyType) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.47
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine proxy) throws RemoteException {
                IBinder iBinder = binder;
                if (iBinder == null) {
                    proxy.disableWheelWakeupWithInfo(proxy.asBinder(), byWho, info, notifyType);
                } else {
                    proxy.disableWheelWakeupWithInfo(iBinder, byWho, info, notifyType);
                }
                WakeupEngineProxy wakeupEngineProxy = WakeupEngineProxy.this;
                IBinder iBinder2 = binder;
                if (iBinder2 == null) {
                    iBinder2 = proxy.asBinder();
                }
                wakeupEngineProxy.setDisableInfoCache(iBinder2, -1, byWho, info, notifyType);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void enableWheelWakeupWithInfo(final IBinder binder, final String byWho, final int notifyType) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.48
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine proxy) throws RemoteException {
                IBinder iBinder = binder;
                if (iBinder == null) {
                    proxy.enableWheelWakeupWithInfo(proxy.asBinder(), byWho, notifyType);
                } else {
                    proxy.enableWheelWakeupWithInfo(iBinder, byWho, notifyType);
                }
                WakeupEngineProxy wakeupEngineProxy = WakeupEngineProxy.this;
                IBinder iBinder2 = binder;
                if (iBinder2 == null) {
                    iBinder2 = proxy.asBinder();
                }
                wakeupEngineProxy.removeDisableInfoCache(iBinder2, -1, byWho);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void suspendDialogWithReason(final IBinder binder, final String byWho, final String reason) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.49
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine proxy) throws RemoteException {
                IBinder iBinder = binder;
                if (iBinder == null) {
                    proxy.suspendDialogWithReason(proxy.asBinder(), byWho, reason);
                    return null;
                }
                proxy.suspendDialogWithReason(iBinder, byWho, reason);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void resumeDialogWithReason(final IBinder binder, final String byWho, final String reason) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.50
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine proxy) throws RemoteException {
                IBinder iBinder = binder;
                if (iBinder == null) {
                    proxy.resumeDialogWithReason(proxy.asBinder(), byWho, reason);
                    return null;
                }
                proxy.resumeDialogWithReason(iBinder, byWho, reason);
                return null;
            }
        });
    }

    public void setHandler(WorkerHandler workerHandler) {
        this.mIpcRunner.setWorkerHandler(workerHandler);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDisableInfoCache(IBinder binder, int type, String byWho, String info, int notifyType) {
        String key = generateKey(binder, type, byWho);
        LogUtils.i(TAG, "setDisableInfoCache :  " + key);
        if (!TextUtils.isEmpty(key)) {
            DisableInfoBean mInfoBean = new DisableInfoBean(binder, type, byWho, info, notifyType);
            if (!this.disableInfoCache.containsKey(key)) {
                LogUtils.i(TAG, "put data  :  " + key + ": " + mInfoBean.toString());
                this.disableInfoCache.put(key, mInfoBean);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeDisableInfoCache(IBinder binder, int type, String byWho) {
        String key = generateKey(binder, type, byWho);
        LogUtils.i(TAG, "removeDisableInfoCache :  " + key);
        if (!TextUtils.isEmpty(key) && this.disableInfoCache.containsKey(key)) {
            LogUtils.i(TAG, "remove  :  " + key + ": " + this.disableInfoCache.size());
            this.disableInfoCache.remove(key);
            LogUtils.i(TAG, "remove after :  " + key + ": " + this.disableInfoCache.size());
        }
    }

    private String generateKey(IBinder binder, int type, String byWho) {
        if (binder != null) {
            String key = binder.toString();
            if (type == -1) {
                return key + "_" + byWho;
            }
            return key + "_" + type + "_" + byWho;
        }
        return null;
    }

    private void resumeCarSpeechStatus() {
        LogUtils.i(TAG, "resumeCarSpeechStatus  disableInfoCache size " + this.disableInfoCache.size());
        if (this.disableInfoCache.size() > 0) {
            for (Map.Entry<String, DisableInfoBean> entry : this.disableInfoCache.entrySet()) {
                DisableInfoBean disableInfoBean = entry.getValue();
                if (disableInfoBean != null) {
                    LogUtils.i(TAG, "disable from cache:    = ====  " + disableInfoBean.toString());
                    if (disableInfoBean.getType() == -1) {
                        disableWheelWakeupWithInfo(disableInfoBean.getBinder(), disableInfoBean.getByWho(), disableInfoBean.getInfo(), disableInfoBean.getNotifyType());
                    } else {
                        disableWakeupWithInfo(disableInfoBean.getBinder(), disableInfoBean.getType(), disableInfoBean.getByWho(), disableInfoBean.getInfo(), disableInfoBean.getNotifyType());
                    }
                }
            }
        }
    }
}
