package com.xiaopeng.xmart.cargallery.fastsp;

import android.content.Context;
import com.xiaopeng.xmart.cargallery.CameraLog;
import com.xiaopeng.xmart.cargallery.utils.IoUtils;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
/* loaded from: classes10.dex */
class ReadWriteManager {
    private static final String DEFAULT_DIR_PATH = "fast_sp";
    private static final String TAG = "ReadWriteManager";
    private String mFilePath;
    private String mLockFilePath;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String getFilePath(Context context, String name) {
        return context.getFilesDir().getAbsolutePath() + File.separator + DEFAULT_DIR_PATH + File.separator + name;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ReadWriteManager(Context context, String name) {
        this.mFilePath = getFilePath(context, name);
        this.mLockFilePath = getFilePath(context, name + ".lock");
        prepare();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void write(Object obj) {
        ObjectOutputStream oos = null;
        FileOutputStream fos = null;
        Lock lock = null;
        try {
            try {
                prepare();
                lock = new Lock(this.mLockFilePath).lock();
                CameraLog.d(TAG, "start write file: " + this.mFilePath);
                fos = new FileOutputStream(this.mFilePath);
                oos = new ObjectOutputStream(new BufferedOutputStream(fos));
                oos.writeObject(obj);
                oos.flush();
                CameraLog.d(TAG, "finish write file: " + this.mFilePath);
                IoUtils.closeSilently(oos);
                IoUtils.closeSilently(fos);
                if (lock == null) {
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
                CameraLog.d(TAG, "finish write file: " + this.mFilePath);
                IoUtils.closeSilently(oos);
                IoUtils.closeSilently(fos);
                if (lock == null) {
                    return;
                }
            }
            lock.release();
        } catch (Throwable th) {
            CameraLog.d(TAG, "finish write file: " + this.mFilePath);
            IoUtils.closeSilently(oos);
            IoUtils.closeSilently(fos);
            if (lock != null) {
                lock.release();
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Object read() {
        if (IoUtils.isFileExist(this.mFilePath)) {
            ObjectInputStream ois = null;
            FileInputStream fis = null;
            Lock lock = null;
            try {
                try {
                    lock = new Lock(this.mLockFilePath).lock();
                    fis = new FileInputStream(this.mFilePath);
                    ois = new ObjectInputStream(new BufferedInputStream(fis));
                    Object readObject = ois.readObject();
                    IoUtils.closeSilently(ois);
                    IoUtils.closeSilently(fis);
                    if (lock != null) {
                        lock.release();
                    }
                    return readObject;
                } catch (Exception e) {
                    e.printStackTrace();
                    IoUtils.closeSilently(ois);
                    IoUtils.closeSilently(fis);
                    if (lock != null) {
                        lock.release();
                    }
                    return null;
                }
            } catch (Throwable th) {
                IoUtils.closeSilently(ois);
                IoUtils.closeSilently(fis);
                if (lock != null) {
                    lock.release();
                }
                throw th;
            }
        }
        return null;
    }

    private void prepare() {
        File file = new File(this.mFilePath);
        File parent = file.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }
    }

    /* loaded from: classes10.dex */
    private static class Lock {
        private static final Map<String, ReentrantLock> THREAD_LOCK_MAP = new HashMap();
        private FileChannel channel;
        private FileLock fileLock;
        private FileOutputStream fos;
        private final String lockFilePath;
        private ReentrantLock threadLock;

        private static ReentrantLock getLock(String key) {
            ReentrantLock reentrantLock;
            Map<String, ReentrantLock> map = THREAD_LOCK_MAP;
            synchronized (map) {
                if (!map.containsKey(key)) {
                    map.put(key, new ReentrantLock());
                }
                reentrantLock = map.get(key);
            }
            return reentrantLock;
        }

        Lock(String lockFilePath) {
            this.lockFilePath = lockFilePath;
            this.threadLock = getLock(lockFilePath);
            File file = new File(lockFilePath);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Lock lock() throws IOException {
            this.threadLock.lock();
            FileOutputStream fileOutputStream = new FileOutputStream(this.lockFilePath);
            this.fos = fileOutputStream;
            FileChannel channel = fileOutputStream.getChannel();
            this.channel = channel;
            this.fileLock = channel.lock();
            return this;
        }

        void release() {
            FileLock fileLock = this.fileLock;
            if (fileLock != null) {
                try {
                    fileLock.release();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            IoUtils.closeSilently(this.channel);
            IoUtils.closeSilently(this.fos);
            this.threadLock.unlock();
        }
    }
}
