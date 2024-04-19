package com.xiaopeng.xmart.cargallery.fastsp;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.LruCache;
import com.xiaopeng.xmart.cargallery.App;
import com.xiaopeng.xmart.cargallery.CameraLog;
import com.xiaopeng.xmart.cargallery.fastsp.EnhancedSharedPreferences;
import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
/* loaded from: classes10.dex */
public class FastSharedPreferences implements EnhancedSharedPreferences {
    private static final String TAG = "FastSharedPreferences";
    private static FspCache sFspCache = new FspCache();
    private static ExecutorService sSyncExecutor = Executors.newSingleThreadExecutor(new ThreadFactory() { // from class: com.xiaopeng.xmart.cargallery.fastsp.-$$Lambda$FastSharedPreferences$Z1-EPjFlizAFC_vBOgUlyPnyC48
        @Override // java.util.concurrent.ThreadFactory
        public final Thread newThread(Runnable runnable) {
            return FastSharedPreferences.lambda$static$0(runnable);
        }
    });
    private final Context mContext;
    private final ReadWriteLock mCopyLock;
    private final FspEditor mEditor;
    private final AtomicBoolean mIsSyncing;
    private final Map<String, Object> mKeyValueMap;
    private final String mName;
    private final AtomicBoolean mNeedSync;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ Thread lambda$static$0(Runnable r) {
        Thread thread = new Thread(r, "FastSharedPreferences_Thread");
        thread.setPriority(3);
        return thread;
    }

    public static FastSharedPreferences get(String spName) {
        FastSharedPreferences fastSharedPreferences;
        if (TextUtils.isEmpty(spName)) {
            return null;
        }
        synchronized (FastSharedPreferences.class) {
            FastSharedPreferences fastSp = sFspCache.get(spName);
            if (fastSp == null) {
                FastSharedPreferences fastSp2 = new FastSharedPreferences(spName);
                sFspCache.put(spName, fastSp2);
            }
            fastSharedPreferences = sFspCache.get(spName);
        }
        return fastSharedPreferences;
    }

    private FastSharedPreferences(String name) {
        this.mEditor = new FspEditor();
        this.mNeedSync = new AtomicBoolean(false);
        this.mIsSyncing = new AtomicBoolean(false);
        this.mCopyLock = new ReentrantReadWriteLock();
        this.mContext = App.getInstance().getApplicationContext();
        this.mName = name;
        this.mKeyValueMap = new ConcurrentHashMap();
        reload();
    }

    @Override // com.xiaopeng.xmart.cargallery.fastsp.EnhancedSharedPreferences
    public Serializable getSerializable(String key, Serializable defValue) {
        if (this.mKeyValueMap.containsKey(key)) {
            return (Serializable) this.mKeyValueMap.get(key);
        }
        return defValue;
    }

    @Override // android.content.SharedPreferences
    public Map<String, ?> getAll() {
        return this.mKeyValueMap;
    }

    @Override // android.content.SharedPreferences
    public String getString(String key, String defValue) {
        if (this.mKeyValueMap.containsKey(key)) {
            return (String) this.mKeyValueMap.get(key);
        }
        return defValue;
    }

    @Override // android.content.SharedPreferences
    public Set<String> getStringSet(String key, Set<String> defValues) {
        if (this.mKeyValueMap.containsKey(key)) {
            return (Set) this.mKeyValueMap.get(key);
        }
        return defValues;
    }

    @Override // android.content.SharedPreferences
    public int getInt(String key, int defValue) {
        if (this.mKeyValueMap.containsKey(key)) {
            return ((Integer) this.mKeyValueMap.get(key)).intValue();
        }
        return defValue;
    }

    @Override // android.content.SharedPreferences
    public long getLong(String key, long defValue) {
        if (this.mKeyValueMap.containsKey(key)) {
            return ((Long) this.mKeyValueMap.get(key)).longValue();
        }
        return defValue;
    }

    @Override // android.content.SharedPreferences
    public float getFloat(String key, float defValue) {
        if (this.mKeyValueMap.containsKey(key)) {
            return ((Float) this.mKeyValueMap.get(key)).floatValue();
        }
        return defValue;
    }

    @Override // android.content.SharedPreferences
    public boolean getBoolean(String key, boolean defValue) {
        if (this.mKeyValueMap.containsKey(key)) {
            return ((Boolean) this.mKeyValueMap.get(key)).booleanValue();
        }
        return defValue;
    }

    @Override // android.content.SharedPreferences
    public boolean contains(String key) {
        return this.mKeyValueMap.containsKey(key);
    }

    @Override // android.content.SharedPreferences
    public SharedPreferences.Editor edit() {
        return this.mEditor;
    }

    @Override // android.content.SharedPreferences
    public void registerOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
    }

    @Override // android.content.SharedPreferences
    public void unregisterOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
    }

    private void reload() {
        CameraLog.d(TAG, "Reload data from " + this.mName);
        Object loadedData = new ReadWriteManager(this.mContext, this.mName).read();
        this.mKeyValueMap.clear();
        if (loadedData != null) {
            this.mKeyValueMap.putAll((Map) loadedData);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int sizeOf() {
        File file = new File(ReadWriteManager.getFilePath(this.mContext, this.mName));
        if (!file.exists()) {
            return 0;
        }
        return (int) (file.length() / 1024);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes10.dex */
    public class FspEditor implements EnhancedSharedPreferences.EnhancedEditor {
        private FspEditor() {
        }

        @Override // com.xiaopeng.xmart.cargallery.fastsp.EnhancedSharedPreferences.EnhancedEditor
        public EnhancedSharedPreferences.EnhancedEditor putSerializable(String key, Serializable value) {
            put(key, value);
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor putString(String key, String value) {
            put(key, value);
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor putStringSet(String key, Set<String> values) {
            put(key, values);
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor putInt(String key, int value) {
            put(key, Integer.valueOf(value));
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor putLong(String key, long value) {
            put(key, Long.valueOf(value));
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor putFloat(String key, float value) {
            put(key, Float.valueOf(value));
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor putBoolean(String key, boolean value) {
            put(key, Boolean.valueOf(value));
            return this;
        }

        private void put(String key, Object value) {
            FastSharedPreferences.this.mCopyLock.readLock().lock();
            FastSharedPreferences.this.mKeyValueMap.put(key, value);
            FastSharedPreferences.this.mCopyLock.readLock().unlock();
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor remove(String key) {
            FastSharedPreferences.this.mCopyLock.readLock().lock();
            FastSharedPreferences.this.mKeyValueMap.remove(key);
            FastSharedPreferences.this.mCopyLock.readLock().unlock();
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public EnhancedSharedPreferences.EnhancedEditor clear() {
            FastSharedPreferences.this.mCopyLock.readLock().lock();
            FastSharedPreferences.this.mKeyValueMap.clear();
            FastSharedPreferences.this.mCopyLock.readLock().unlock();
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public boolean commit() {
            sync();
            return true;
        }

        @Override // android.content.SharedPreferences.Editor
        public void apply() {
            sync();
        }

        private void sync() {
            FastSharedPreferences.this.mNeedSync.compareAndSet(false, true);
            postSyncTask();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public synchronized void postSyncTask() {
            if (FastSharedPreferences.this.mIsSyncing.get()) {
                return;
            }
            FastSharedPreferences.sSyncExecutor.execute(new SyncTask());
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes10.dex */
        public class SyncTask implements Runnable {
            private SyncTask() {
            }

            @Override // java.lang.Runnable
            public void run() {
                if (FastSharedPreferences.this.mNeedSync.get()) {
                    FastSharedPreferences.this.mIsSyncing.compareAndSet(false, true);
                    FastSharedPreferences.this.mCopyLock.writeLock().lock();
                    Map<String, Object> storeMap = new HashMap<>(FastSharedPreferences.this.mKeyValueMap);
                    FastSharedPreferences.this.mCopyLock.writeLock().unlock();
                    FastSharedPreferences.this.mNeedSync.compareAndSet(true, false);
                    ReadWriteManager manager = new ReadWriteManager(FastSharedPreferences.this.mContext, FastSharedPreferences.this.mName);
                    manager.write(storeMap);
                    FastSharedPreferences.this.mIsSyncing.compareAndSet(true, false);
                    CameraLog.d(FastSharedPreferences.TAG, "Write sp cache to file complete");
                    if (FastSharedPreferences.this.mNeedSync.get()) {
                        CameraLog.d(FastSharedPreferences.TAG, "Need to sync sp cache again");
                        FspEditor.this.postSyncTask();
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes10.dex */
    public static class FspCache extends LruCache<String, FastSharedPreferences> {
        private static final int DEFAULT_MAX_SIZE = 2097152;

        FspCache() {
            this(2097152);
        }

        FspCache(int maxSize) {
            super(maxSize);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.util.LruCache
        public int sizeOf(String key, FastSharedPreferences value) {
            int size = 0;
            if (value != null) {
                size = value.sizeOf();
            }
            CameraLog.d(FastSharedPreferences.TAG, "FspCache sizeOf " + key + " is: " + size);
            return size;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.util.LruCache
        public FastSharedPreferences create(String key) {
            return new FastSharedPreferences(key);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.util.LruCache
        public void entryRemoved(boolean evicted, String key, FastSharedPreferences oldValue, FastSharedPreferences newValue) {
            CameraLog.d(FastSharedPreferences.TAG, "FspCache entryRemoved: " + key);
        }
    }
}
