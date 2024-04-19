package com.xiaopeng.lib.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Debug;
import android.os.Process;
import android.text.TextUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
/* loaded from: classes.dex */
public class ProcessUtils {
    private static final String TAG = "ProcUtils";

    public static long getAppMemoryUsed(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService("activity");
        List<ActivityManager.RunningAppProcessInfo> runningAppProcessesList = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcessesList) {
            if (!TextUtils.isEmpty(runningAppProcessInfo.processName) && runningAppProcessInfo.processName.equals(context.getPackageName())) {
                int pid = runningAppProcessInfo.pid;
                int[] pids = {pid};
                Debug.MemoryInfo[] memoryInfo = am.getProcessMemoryInfo(pids);
                int memorySize = memoryInfo[0].dalvikSharedDirty + memoryInfo[0].dalvikPrivateDirty;
                return memorySize * 1024;
            }
        }
        return 0L;
    }

    private void getRunningAppProcessInfo() {
    }

    public static long getAvailableMemory(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService("activity");
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(memoryInfo);
        return memoryInfo.availMem;
    }

    public static long getTotalMemory(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService("activity");
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(memoryInfo);
        return memoryInfo.totalMem;
    }

    public static long getAppCpuTime() {
        String[] cpuInfos = null;
        BufferedReader reader = null;
        InputStreamReader inputStreamReader = null;
        FileInputStream fileInputStream = null;
        try {
            try {
                try {
                    int pid = Process.myPid();
                    fileInputStream = new FileInputStream("/proc/" + pid + "/stat");
                    inputStreamReader = new InputStreamReader(fileInputStream);
                    reader = new BufferedReader(inputStreamReader, 1000);
                    String load = reader.readLine();
                    if (load != null) {
                        cpuInfos = load.split(" ");
                    }
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        inputStreamReader.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                    fileInputStream.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                    if (inputStreamReader != null) {
                        try {
                            inputStreamReader.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                    }
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                }
            } catch (IOException e5) {
                e5.printStackTrace();
            }
            if (cpuInfos == null || cpuInfos.length <= 16) {
                return 0L;
            }
            long appCpuTime = Long.parseLong(cpuInfos[13]) + Long.parseLong(cpuInfos[14]) + Long.parseLong(cpuInfos[15]) + Long.parseLong(cpuInfos[16]);
            return appCpuTime;
        } catch (Throwable th) {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e6) {
                    e6.printStackTrace();
                }
            }
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e7) {
                    e7.printStackTrace();
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e8) {
                    e8.printStackTrace();
                }
            }
            throw th;
        }
    }

    public static long getTotalCpuTime() {
        String[] cpuInfos = null;
        BufferedReader reader = null;
        InputStreamReader inputStreamReader = null;
        FileInputStream fileInputStream = null;
        try {
            try {
                try {
                    fileInputStream = new FileInputStream("/proc/stat");
                    inputStreamReader = new InputStreamReader(fileInputStream);
                    reader = new BufferedReader(inputStreamReader, 1000);
                    String load = reader.readLine();
                    if (load != null) {
                        cpuInfos = load.split(" ");
                    }
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        inputStreamReader.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                    fileInputStream.close();
                } catch (Throwable th) {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                    if (inputStreamReader != null) {
                        try {
                            inputStreamReader.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                    }
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (IOException e5) {
                            e5.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e6) {
                        e6.printStackTrace();
                    }
                }
                if (inputStreamReader != null) {
                    try {
                        inputStreamReader.close();
                    } catch (IOException e7) {
                        e7.printStackTrace();
                    }
                }
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            }
        } catch (IOException e8) {
            e8.printStackTrace();
        }
        if (cpuInfos == null || cpuInfos.length <= 8) {
            return 0L;
        }
        long totalCpu = Long.parseLong(cpuInfos[2]) + Long.parseLong(cpuInfos[3]) + Long.parseLong(cpuInfos[4]) + Long.parseLong(cpuInfos[6]) + Long.parseLong(cpuInfos[5]) + Long.parseLong(cpuInfos[7]) + Long.parseLong(cpuInfos[8]);
        return totalCpu;
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x007f, code lost:
        r2.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0083, code lost:
        r5 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0084, code lost:
        r5.printStackTrace();
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x009a, code lost:
        if (r1 != null) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x009c, code lost:
        r1.destroy();
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00c4, code lost:
        if (r1 == null) goto L20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0037, code lost:
        com.xiaopeng.lib.utils.LogUtils.d("ProcessCpuRate", "Result-->" + r5);
        r5 = r5.split("%");
        r9 = r5[0].split("User");
        r10 = r5[1].split("System");
        r0[0] = java.lang.Integer.parseInt(r9[1].trim());
        r0[1] = java.lang.Integer.parseInt(r10[1].trim());
     */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0092 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static int[] getProcessCpuRate() {
        /*
            Method dump skipped, instructions count: 236
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.lib.utils.ProcessUtils.getProcessCpuRate():int[]");
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x006e, code lost:
        if (r4 == null) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0070, code lost:
        r4.destroy();
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0098, code lost:
        if (r4 == null) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x009b, code lost:
        return r0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.String execProcess(java.lang.String r11, int r12) {
        /*
            r0 = 0
            r1 = 0
            r2 = 0
            r3 = 0
            r4 = 0
            java.lang.Runtime r5 = java.lang.Runtime.getRuntime()     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L76
            java.lang.Process r5 = r5.exec(r11)     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L76
            r4 = r5
            java.io.InputStream r5 = r4.getInputStream()     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L76
            r3 = r5
            java.io.InputStreamReader r5 = new java.io.InputStreamReader     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L76
            r5.<init>(r3)     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L76
            r2 = r5
            java.io.BufferedReader r5 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L76
            r5.<init>(r2)     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L76
            r1 = r5
            java.lang.StringBuffer r5 = new java.lang.StringBuffer     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L76
            r5.<init>()     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L76
            char[] r6 = new char[r12]     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L76
            r7 = 0
        L27:
            int r8 = r1.read(r6)     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L76
            r7 = r8
            r9 = -1
            if (r8 == r9) goto L34
            r8 = 0
            r5.append(r6, r8, r7)     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L76
            goto L27
        L34:
            java.lang.String r8 = "ProcUtils"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L76
            r9.<init>()     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L76
            java.lang.String r10 = "respBuff-->"
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L76
            java.lang.StringBuilder r9 = r9.append(r5)     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L76
            java.lang.String r9 = r9.toString()     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L76
            com.xiaopeng.lib.utils.LogUtils.d(r8, r9)     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L76
            java.lang.String r8 = r5.toString()     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L76
            r0 = r8
            r1.close()     // Catch: java.io.IOException -> L57
            goto L5b
        L57:
            r5 = move-exception
            r5.printStackTrace()
        L5b:
            r2.close()     // Catch: java.io.IOException -> L60
            goto L64
        L60:
            r5 = move-exception
            r5.printStackTrace()
        L64:
            if (r3 == 0) goto L6e
            r3.close()     // Catch: java.io.IOException -> L6a
            goto L6e
        L6a:
            r5 = move-exception
            r5.printStackTrace()
        L6e:
            if (r4 == 0) goto L9b
        L70:
            r4.destroy()
            goto L9b
        L74:
            r5 = move-exception
            goto L9c
        L76:
            r5 = move-exception
            r5.printStackTrace()     // Catch: java.lang.Throwable -> L74
            if (r1 == 0) goto L84
            r1.close()     // Catch: java.io.IOException -> L80
            goto L84
        L80:
            r5 = move-exception
            r5.printStackTrace()
        L84:
            if (r2 == 0) goto L8e
            r2.close()     // Catch: java.io.IOException -> L8a
            goto L8e
        L8a:
            r5 = move-exception
            r5.printStackTrace()
        L8e:
            if (r3 == 0) goto L98
            r3.close()     // Catch: java.io.IOException -> L94
            goto L98
        L94:
            r5 = move-exception
            r5.printStackTrace()
        L98:
            if (r4 == 0) goto L9b
            goto L70
        L9b:
            return r0
        L9c:
            if (r1 == 0) goto La6
            r1.close()     // Catch: java.io.IOException -> La2
            goto La6
        La2:
            r6 = move-exception
            r6.printStackTrace()
        La6:
            if (r2 == 0) goto Lb0
            r2.close()     // Catch: java.io.IOException -> Lac
            goto Lb0
        Lac:
            r6 = move-exception
            r6.printStackTrace()
        Lb0:
            if (r3 == 0) goto Lba
            r3.close()     // Catch: java.io.IOException -> Lb6
            goto Lba
        Lb6:
            r6 = move-exception
            r6.printStackTrace()
        Lba:
            if (r4 == 0) goto Lbf
            r4.destroy()
        Lbf:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.lib.utils.ProcessUtils.execProcess(java.lang.String, int):java.lang.String");
    }

    public static String getProcessName(Context cxt, int pid) {
        ActivityManager am = (ActivityManager) cxt.getSystemService("activity");
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }

    public static String getCurProcessName() {
        FileReader fileReader = null;
        try {
            try {
                File file = new File("/proc/" + Process.myPid() + "/cmdline");
                fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String processName = bufferedReader.readLine().trim();
                bufferedReader.close();
                fileReader.close();
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return processName;
            } catch (Throwable th) {
                try {
                    fileReader.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
                throw th;
            }
        } catch (Exception e3) {
            e3.printStackTrace();
            try {
                fileReader.close();
            } catch (IOException e4) {
                e4.printStackTrace();
            }
            return null;
        }
    }
}
