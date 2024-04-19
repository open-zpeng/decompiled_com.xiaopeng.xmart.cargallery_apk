package com.xiaopeng.lib.framework.netchannelmodule.http.tracing;

import com.lzy.okgo.cache.CacheEntity;
import com.xiaopeng.datalog.DataLogModuleEntry;
import com.xiaopeng.lib.framework.module.Module;
import com.xiaopeng.lib.framework.moduleinterface.datalogmodule.IDataLog;
import com.xiaopeng.lib.framework.moduleinterface.datalogmodule.IStatEvent;
import com.xiaopeng.lib.framework.netchannelmodule.common.GlobalConfig;
import com.xiaopeng.lib.utils.LogUtils;
import zipkin2.Span;
import zipkin2.reporter.Reporter;
/* loaded from: classes.dex */
public class TracingReporter implements Reporter<Span> {
    private static final String TAG = "Tracer";

    /* JADX INFO: Access modifiers changed from: package-private */
    public TracingReporter() {
        Module.register(DataLogModuleEntry.class, new DataLogModuleEntry(GlobalConfig.getApplicationContext()));
    }

    @Override // zipkin2.reporter.Reporter
    public void report(Span span) {
        String spanJson = span.toString();
        LogUtils.d(TAG, "report:" + spanJson);
        IDataLog dataLog = (IDataLog) Module.get(DataLogModuleEntry.class).get(IDataLog.class);
        IStatEvent statEvent = dataLog.buildStat().setEventName("data_tracing").setProperty(CacheEntity.DATA, "[" + spanJson + "]").build();
        dataLog.sendStatData(statEvent);
    }
}
