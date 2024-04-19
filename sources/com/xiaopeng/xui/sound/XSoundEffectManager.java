package com.xiaopeng.xui.sound;

import android.media.AudioManager;
import com.xiaopeng.speech.speechwidget.ListWidget;
import com.xiaopeng.xui.Xui;
import com.xiaopeng.xui.utils.XLogUtils;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/* loaded from: classes.dex */
public class XSoundEffectManager {
    private static final String TAG = "xpui-XSoundManager";
    private ExecutorService mExecutorService;
    private boolean mIsDestroy;
    private ConcurrentHashMap<Integer, XSoundEffect> mPoolHashMap;
    private ConcurrentHashMap<Integer, Boolean> mReleaseMap;

    /* loaded from: classes.dex */
    private static class SingletonHolder {
        private static final XSoundEffectManager INSTANCE = new XSoundEffectManager();

        private SingletonHolder() {
        }
    }

    private XSoundEffectManager() {
        this.mPoolHashMap = new ConcurrentHashMap<>();
        this.mExecutorService = Executors.newSingleThreadExecutor();
        this.mReleaseMap = new ConcurrentHashMap<>();
    }

    public static XSoundEffectManager get() {
        return SingletonHolder.INSTANCE;
    }

    public synchronized void play(int resource) {
        int autoManagerId = useAudioManager(resource);
        if (autoManagerId > 0) {
            XAudioManager.get().playSoundEffect(autoManagerId);
        } else {
            playLocal(resource);
        }
    }

    private void playLocal(final int resource) {
        this.mIsDestroy = false;
        this.mReleaseMap.put(Integer.valueOf(resource), false);
        this.mExecutorService.execute(new Runnable() { // from class: com.xiaopeng.xui.sound.-$$Lambda$XSoundEffectManager$huGJR5ZOzeh1S69x-AbXgHZTsuw
            @Override // java.lang.Runnable
            public final void run() {
                XSoundEffectManager.this.lambda$playLocal$0$XSoundEffectManager(resource);
            }
        });
    }

    public /* synthetic */ void lambda$playLocal$0$XSoundEffectManager(int resource) {
        if (this.mIsDestroy) {
            log(String.format("play-not for destroy resource:%s", Integer.valueOf(resource)));
            return;
        }
        Boolean isRelease = this.mReleaseMap.get(Integer.valueOf(resource));
        if (isRelease != null && isRelease.booleanValue()) {
            log(String.format("play-not for release resource:%s", Integer.valueOf(resource)));
            return;
        }
        System.currentTimeMillis();
        XSoundEffect soundEffect = this.mPoolHashMap.get(Integer.valueOf(resource));
        if (soundEffect == null) {
            soundEffect = SoundEffectFactory.create(resource);
            this.mPoolHashMap.put(Integer.valueOf(resource), soundEffect);
        }
        soundEffect.play();
    }

    private int useAudioManager(int id) {
        switch (id) {
            case 2:
                return 17;
            case 3:
                return 16;
            case 4:
                return 15;
            case 5:
                return 14;
            default:
                return -1;
        }
    }

    public synchronized void release(final int resource) {
        this.mReleaseMap.put(Integer.valueOf(resource), true);
        this.mExecutorService.execute(new Runnable() { // from class: com.xiaopeng.xui.sound.-$$Lambda$XSoundEffectManager$8Wd81vpyuA-X8mGVcrX3xX1SDRo
            @Override // java.lang.Runnable
            public final void run() {
                XSoundEffectManager.this.lambda$release$1$XSoundEffectManager(resource);
            }
        });
    }

    public /* synthetic */ void lambda$release$1$XSoundEffectManager(int resource) {
        XSoundEffect soundEffect = this.mPoolHashMap.get(Integer.valueOf(resource));
        if (soundEffect != null) {
            soundEffect.release();
        }
        log(String.format("release resource:%s", Integer.valueOf(resource)));
    }

    public synchronized void destroy() {
        this.mIsDestroy = true;
        this.mExecutorService.execute(new Runnable() { // from class: com.xiaopeng.xui.sound.-$$Lambda$XSoundEffectManager$jJ-CPGjWuwske6TDKYuJyluIjmE
            @Override // java.lang.Runnable
            public final void run() {
                XSoundEffectManager.this.lambda$destroy$2$XSoundEffectManager();
            }
        });
    }

    public /* synthetic */ void lambda$destroy$2$XSoundEffectManager() {
        long time = System.currentTimeMillis();
        for (XSoundEffect soundEffect : this.mPoolHashMap.values()) {
            if (soundEffect != null) {
                soundEffect.release();
            }
        }
        this.mPoolHashMap.clear();
        this.mReleaseMap.clear();
        log("destroy time : " + (System.currentTimeMillis() - time));
    }

    public synchronized void resetResource(int resource, String path, int location) {
        resetResource(resource, path, location, 5);
    }

    public synchronized void resetResource(final int resource, final String path, final int location, final int streamType) {
        this.mExecutorService.execute(new Runnable() { // from class: com.xiaopeng.xui.sound.-$$Lambda$XSoundEffectManager$zG7Nx8cWSQRLaBCfVwA7fqBp5YA
            @Override // java.lang.Runnable
            public final void run() {
                XSoundEffectManager.this.lambda$resetResource$3$XSoundEffectManager(resource, path, location, streamType);
            }
        });
    }

    public /* synthetic */ void lambda$resetResource$3$XSoundEffectManager(int resource, String path, int location, int streamType) {
        SoundEffectFactory.resetResource(resource, path, location, streamType);
        XSoundEffect soundEffect = this.mPoolHashMap.get(Integer.valueOf(resource));
        if (soundEffect != null) {
            soundEffect.release();
            this.mPoolHashMap.remove(Integer.valueOf(resource));
        }
        log(String.format("resetResource--resource:%s,path:%s,location:%s,streamType:%s", Integer.valueOf(resource), path, Integer.valueOf(location), Integer.valueOf(streamType)));
    }

    public synchronized void restoreResource(final int resource) {
        this.mExecutorService.execute(new Runnable() { // from class: com.xiaopeng.xui.sound.-$$Lambda$XSoundEffectManager$xMqojCGb_wGqPP-iwnFxA7uh2Ko
            @Override // java.lang.Runnable
            public final void run() {
                XSoundEffectManager.this.lambda$restoreResource$4$XSoundEffectManager(resource);
            }
        });
    }

    public /* synthetic */ void lambda$restoreResource$4$XSoundEffectManager(int resource) {
        SoundEffectFactory.restoreResource(resource);
        XSoundEffect soundEffect = this.mPoolHashMap.get(Integer.valueOf(resource));
        if (soundEffect != null) {
            soundEffect.release();
            this.mPoolHashMap.remove(Integer.valueOf(resource));
        }
        log(String.format("restoreResource resource:%s", Integer.valueOf(resource)));
    }

    private void log(String msg) {
        XLogUtils.d(TAG, msg);
    }

    /* loaded from: classes.dex */
    public static class XAudioManager {
        public static final int FX_DEL = 14;
        public static final int FX_SWITCH_OFF = 15;
        public static final int FX_SWITCH_ON = 16;
        public static final int FX_WHEEL_SCROLL = 17;
        private AudioManager mAudioManager;

        private XAudioManager() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes.dex */
        public static class SingletonHolder {
            private static final XAudioManager INSTANCE = new XAudioManager();

            private SingletonHolder() {
            }
        }

        public static XAudioManager get() {
            return SingletonHolder.INSTANCE;
        }

        private AudioManager getAudioManager() {
            if (this.mAudioManager == null) {
                this.mAudioManager = (AudioManager) Xui.getContext().getSystemService(ListWidget.EXTRA_TYPE_AUDIO);
            }
            return this.mAudioManager;
        }

        public void playSoundEffect(int effectType) {
            getAudioManager().playSoundEffect(effectType);
        }
    }
}
