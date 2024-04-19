package com.xiaopeng.speech.protocol.query.context;

import com.xiaopeng.speech.SpeechQuery;
/* loaded from: classes.dex */
public class ContextQuery extends SpeechQuery<IContextCaller> {
    /* JADX INFO: Access modifiers changed from: protected */
    public int getWidgetListSize(String event, String data) {
        return ((IContextCaller) this.mQueryCaller).getWidgetListSize();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getWidgetPageSize(String event, String data) {
        return ((IContextCaller) this.mQueryCaller).getWidgetPageSize();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getWidgetCurrLocation(String event, String data) {
        return ((IContextCaller) this.mQueryCaller).getWidgetCurrLocation();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String getWidgetId(String event, String data) {
        return ((IContextCaller) this.mQueryCaller).getWidgetId();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String getWidgetType(String event, String data) {
        return ((IContextCaller) this.mQueryCaller).getWidgetType();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String getWidgetInfo(String event, String data) {
        return ((IContextCaller) this.mQueryCaller).getWidgetInfo();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getInfoFlowScrollToTop(String event, String data) {
        return ((IContextCaller) this.mQueryCaller).getInfoFlowScrollToTop();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getInfoFlowScrollToBottom(String event, String data) {
        return ((IContextCaller) this.mQueryCaller).getInfoFlowScrollToBottom();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getInfoFlowOnePage(String event, String data) {
        return ((IContextCaller) this.mQueryCaller).getInfoFlowOnePage();
    }
}
