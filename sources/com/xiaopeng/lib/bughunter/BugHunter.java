package com.xiaopeng.lib.bughunter;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.SystemClock;
import android.util.Log;
import com.lzy.okgo.OkGo;
import com.xiaopeng.datalog.DataLogModuleEntry;
import com.xiaopeng.lib.bughunter.anr.Caton;
import com.xiaopeng.lib.bughunter.utils.BugHunterUtils;
import com.xiaopeng.lib.framework.module.Module;
import com.xiaopeng.lib.framework.moduleinterface.datalogmodule.IDataLog;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.lib.utils.info.BuildInfoUtils;
import java.io.File;
import java.io.RandomAccessFile;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.json.JSONObject;
/* loaded from: classes.dex */
public class BugHunter {
    private static final String CATON_EVENT = "perf_caton";
    private static final String KEY_ANR_FLAG = "anr";
    private static final String KEY_APP_NAME = "appName";
    private static final String KEY_APP_VER = "appVer";
    private static final String KEY_EVENT = "_event";
    private static final String KEY_EVENT_TIME = "_time";
    private static final String KEY_MCU_VER = "_mcuver";
    private static final String KEY_MEM_INFO = "memInfo";
    private static final String KEY_MODULE = "_module";
    private static final String KEY_MODULE_VER = "_module_version";
    private static final String KEY_NETWORK = "_network";
    private static final String KEY_STACK_INFO = "stackInfo";
    private static final String KEY_STACK_MD5 = "md5";
    private static final String KEY_STUCK_TIME = "elapseTime";
    private static final String KEY_SYSTEM_BOOT_TIME = "_st_time";
    private static final String SEPARATOR = "#";
    private static final String TAG = "libBugHunter";
    private static boolean dumpToSdCardFlag;

    public static void init(Context context) {
        init(context, false, true, false);
    }

    public static void init(Context context, boolean strictMode, boolean enableLogcat, boolean enableDumpFile) {
        try {
            initCaton(context.getApplicationContext(), strictMode, enableLogcat, enableDumpFile);
            Module.register(DataLogModuleEntry.class, new DataLogModuleEntry(context));
            Module.get(DataLogModuleEntry.class).get(IDataLog.class);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private static void initCaton(final Context context, boolean strictMode, boolean enableLogcat, boolean enableDumpFile) {
        long interval = 400;
        long threshold = 400;
        if (BuildInfoUtils.isUserVersion()) {
            interval = OkGo.DEFAULT_MILLISECONDS;
            threshold = 1000;
        } else if (strictMode) {
            interval = 100;
            threshold = 100;
        } else {
            File flagFile = new File("/sdcard/Log/catonflag");
            if (flagFile.exists()) {
                interval = 100;
                threshold = 100;
            }
        }
        if (enableDumpFile) {
            dumpToSdCardFlag = true;
        } else {
            File dumpFlagFile = new File("/sdcard/Log/catondumpflag");
            if (dumpFlagFile.exists()) {
                dumpToSdCardFlag = true;
            }
        }
        Caton.Builder builder = new Caton.Builder(context.getApplicationContext()).monitorMode(Caton.MonitorMode.LOOPER).loggingEnabled(enableLogcat).collectInterval(interval).thresholdTime(threshold).callback(new Caton.Callback() { // from class: com.xiaopeng.lib.bughunter.BugHunter.1
            /* JADX WARN: Removed duplicated region for block: B:22:0x008f  */
            /* JADX WARN: Removed duplicated region for block: B:27:0x00f0  */
            /* JADX WARN: Removed duplicated region for block: B:30:0x00fd  */
            /* JADX WARN: Removed duplicated region for block: B:33:0x0123  */
            @Override // com.xiaopeng.lib.bughunter.anr.Caton.Callback
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct add '--show-bad-code' argument
            */
            public void onBlockOccurs(java.lang.String[] r23, boolean r24, long... r25) {
                /*
                    Method dump skipped, instructions count: 294
                    To view this dump add '--comments-level debug' option
                */
                throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.lib.bughunter.BugHunter.AnonymousClass1.onBlockOccurs(java.lang.String[], boolean, long[]):void");
            }
        });
        Caton.initialize(builder);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String calcStackTraceMd5(String stacktraceInfo) {
        String newInfo;
        int pos = stacktraceInfo.indexOf("\n");
        if (pos > 0) {
            newInfo = stacktraceInfo.substring(pos + 1);
        } else {
            newInfo = stacktraceInfo;
        }
        return MD5Utils.getMD5(newInfo);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getJsonStuckLog(Context ctx, String pkgName, String appVer, long elapseTime, String stackMd5, String stackInfo) {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.putOpt("_event", CATON_EVENT);
            jsonObj.putOpt("_module", "perf");
            jsonObj.putOpt("_mcuver", BugHunterUtils.getMCUVer());
            jsonObj.putOpt("_module_version", appVer);
            jsonObj.putOpt("_st_time", Long.valueOf(SystemClock.uptimeMillis() / 1000));
            jsonObj.putOpt("_time", Long.valueOf(System.currentTimeMillis()));
            jsonObj.putOpt("_network", BugHunterUtils.getNetworkType(ctx));
            jsonObj.putOpt(KEY_APP_NAME, pkgName);
            jsonObj.putOpt(KEY_APP_VER, appVer);
            jsonObj.putOpt(KEY_ANR_FLAG, false);
            jsonObj.putOpt(KEY_STUCK_TIME, Long.valueOf(elapseTime));
            jsonObj.putOpt(KEY_STACK_MD5, stackMd5);
            jsonObj.putOpt(KEY_STACK_INFO, stackInfo);
            return jsonObj.toString();
        } catch (Throwable t) {
            Log.e(TAG, "error in function getJsonCatonLog, " + t.getMessage());
            return "";
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: BlockProcessor
        jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:22:0x00d7
        	at jadx.core.dex.visitors.blocks.BlockProcessor.checkForUnreachableBlocks(BlockProcessor.java:81)
        	at jadx.core.dex.visitors.blocks.BlockProcessor.processBlocksTree(BlockProcessor.java:47)
        	at jadx.core.dex.visitors.blocks.BlockProcessor.visit(BlockProcessor.java:39)
        */
    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String printStackTrace(java.lang.String r17, android.content.Context r18, java.lang.String r19, java.lang.String[] r20, long r21, long r23) {
        /*
            r1 = r18
            r2 = r19
            r3 = r20
            org.json.JSONObject r0 = new org.json.JSONObject
            r0.<init>()
            r4 = r0
            r5 = 0
            r7 = 0
            r9 = 0
            java.lang.StringBuffer r0 = new java.lang.StringBuffer
            java.lang.String r11 = ""
            r0.<init>(r11)
            r11 = r0
            if (r1 == 0) goto L58
            java.lang.String r0 = "activity"
            java.lang.Object r0 = r1.getSystemService(r0)
            android.app.ActivityManager r0 = (android.app.ActivityManager) r0
            android.app.ActivityManager$MemoryInfo r12 = new android.app.ActivityManager$MemoryInfo
            r12.<init>()
            r0.getMemoryInfo(r12)
            long r13 = r12.availMem
            r15 = 1048576(0x100000, double:5.180654E-318)
            long r5 = r13 / r15
            long r13 = r12.totalMem
            long r7 = r13 / r15
            long r13 = r12.threshold
            long r9 = r13 / r15
            java.lang.String r13 = "availMem:"
            java.lang.StringBuffer r13 = r11.append(r13)
            r13.append(r5)
            java.lang.String r13 = "totalMem:"
            java.lang.StringBuffer r13 = r11.append(r13)
            r13.append(r7)
            java.lang.String r13 = "threshold:"
            java.lang.StringBuffer r13 = r11.append(r13)
            r13.append(r9)
        L58:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r12 = r0
            java.lang.String r0 = "\n----------------caton log [ "
            r12.append(r0)
            boolean r0 = android.text.TextUtils.isEmpty(r19)
            if (r0 != 0) goto L6c
            r12.append(r2)
        L6c:
            java.lang.String r0 = " ]"
            r12.append(r0)
            int r0 = r3.length
            int r0 = r0 + (-1)
        L74:
            if (r0 < 0) goto L83
            r13 = r3[r0]
            java.lang.String r14 = "\n"
            r12.append(r14)
            r12.append(r13)
            int r0 = r0 + (-1)
            goto L74
        L83:
            java.text.SimpleDateFormat r0 = new java.text.SimpleDateFormat
            java.lang.String r13 = "YYYY/MM/dd HH:mm:ss"
            r0.<init>(r13)
            r13 = r0
            java.lang.String r0 = "md5"
            r14 = r17
            r4.put(r0, r14)     // Catch: org.json.JSONException -> Ld5
            java.lang.String r0 = "pkgName"
            r4.put(r0, r2)     // Catch: org.json.JSONException -> Ld5
            java.lang.String r0 = "time"
            java.util.Calendar r15 = java.util.Calendar.getInstance()     // Catch: org.json.JSONException -> Ld5
            java.util.Date r15 = r15.getTime()     // Catch: org.json.JSONException -> Ld5
            java.lang.String r15 = r13.format(r15)     // Catch: org.json.JSONException -> Ld5
            r4.put(r0, r15)     // Catch: org.json.JSONException -> Ld5
            java.lang.String r0 = "ElapseTime"
            r1 = r21
            r4.put(r0, r1)     // Catch: org.json.JSONException -> Ld5
            java.lang.String r0 = "threadElapseTime"
            r1 = r23
            r4.put(r0, r1)     // Catch: org.json.JSONException -> Ld3
            java.lang.String r0 = "availMem"
            r4.put(r0, r5)     // Catch: org.json.JSONException -> Ld3
            java.lang.String r0 = "totalMem"
            r4.put(r0, r7)     // Catch: org.json.JSONException -> Ld3
            java.lang.String r0 = "threshold"
            r4.put(r0, r9)     // Catch: org.json.JSONException -> Ld3
            java.lang.String r0 = "catonLog"
            java.lang.String r15 = r12.toString()     // Catch: org.json.JSONException -> Ld3
            r4.put(r0, r15)     // Catch: org.json.JSONException -> Ld3
            goto Ldf
        Ld3:
            r0 = move-exception
            goto Ldc
        Ld5:
            r0 = move-exception
            goto Lda
        Ld7:
            r0 = move-exception
            r14 = r17
        Lda:
            r1 = r23
        Ldc:
            r0.printStackTrace()
        Ldf:
            java.lang.String r0 = r4.toString()
            java.lang.String r15 = "libBugHunter"
            com.xiaopeng.lib.bughunter.anr.Config.log(r15, r0)
            java.lang.String r0 = r11.toString()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.lib.bughunter.BugHunter.printStackTrace(java.lang.String, android.content.Context, java.lang.String, java.lang.String[], long, long):java.lang.String");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:12:0x0098 -> B:31:0x00a6). Please submit an issue!!! */
    public static void dumpCatonInfo(String logDir, String packageName, byte[] bytes) {
        File catonDir = new File(logDir);
        if (!catonDir.exists()) {
            boolean dirMade = catonDir.mkdirs();
            if (!dirMade) {
                LogUtils.w(TAG, "make caton LogDir failed");
            } else {
                boolean setReadable = catonDir.setReadable(true, false);
                boolean setWritable = catonDir.setWritable(true, false);
                boolean setExecutable = catonDir.setExecutable(true, false);
                LogUtils.d(TAG, "caton LogDir setReadable: " + setReadable + "; setWritable: " + setWritable + "; setExecutable: " + setExecutable);
            }
        }
        File catonFile = new File(logDir + MqttTopic.TOPIC_LEVEL_SEPARATOR + packageName + ".log");
        RandomAccessFile randomFile = null;
        try {
            try {
                try {
                    randomFile = new RandomAccessFile(catonFile, "rw");
                    long fileLength = randomFile.length();
                    randomFile.seek(fileLength);
                    randomFile.write(bytes);
                    randomFile.writeBytes("\n\n");
                    randomFile.getFD().sync();
                    randomFile.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    if (randomFile != null) {
                        randomFile.close();
                    }
                }
            } catch (Exception e2) {
            }
        } catch (Throwable th) {
            if (randomFile != null) {
                try {
                    randomFile.close();
                } catch (Exception e3) {
                }
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getVersionName(Context context, String packageName) {
        PackageInfo packageInfo = getPackageInfo(context, packageName);
        return packageInfo == null ? "" : packageInfo.versionName;
    }

    private static PackageInfo getPackageInfo(Context context, String packageName) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName != null ? packageName : context.getPackageName(), 16384);
            return pi;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
