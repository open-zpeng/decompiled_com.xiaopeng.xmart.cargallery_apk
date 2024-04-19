package com.xiaopeng.speech.protocol.query.appstore;

import android.text.TextUtils;
import com.xiaopeng.speech.SpeechQuery;
import com.xiaopeng.xmart.cargallery.bean.BIConfig;
import org.json.JSONException;
import org.json.JSONObject;
/* loaded from: classes.dex */
public class AppAndAppletQuery extends SpeechQuery<IAppAndAppletCaller> {
    /* JADX INFO: Access modifiers changed from: protected */
    public int onOpenAppshopPage(String event, String data) {
        return ((IAppAndAppletCaller) this.mQueryCaller).onOpenAppshopPage();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int onCloseAppshopPage(String event, String data) {
        return ((IAppAndAppletCaller) this.mQueryCaller).onCloseAppshopPage();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int onOpenAppStoreMainPage(String event, String data) {
        return ((IAppAndAppletCaller) this.mQueryCaller).onOpenAppStoreMainPage();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int onCloseAppStoreMainPage(String event, String data) {
        return ((IAppAndAppletCaller) this.mQueryCaller).onCloseAppStoreMainPage();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int onOpenAppletMainPage(String event, String data) {
        return ((IAppAndAppletCaller) this.mQueryCaller).onOpenAppletMainPage();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int onCloseAppletMainPage(String event, String data) {
        return ((IAppAndAppletCaller) this.mQueryCaller).onCloseAppletMainPage();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getAppOpenStatus(String event, String data) {
        return ((IAppAndAppletCaller) this.mQueryCaller).getAppOpenStatus(data);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getAppCloseStatus(String event, String data) {
        return ((IAppAndAppletCaller) this.mQueryCaller).getAppCloseStatus(data);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getAppletOpenStatus(String event, String data) {
        return ((IAppAndAppletCaller) this.mQueryCaller).getAppletOpenStatus(data);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getAppletCloseStatus(String event, String data) {
        return ((IAppAndAppletCaller) this.mQueryCaller).getAppletCloseStatus(data);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getCurrentCloseStatus(String event, String data) {
        String type = null;
        try {
            if (!TextUtils.isEmpty(data)) {
                JSONObject json = new JSONObject(data);
                type = json.optString(BIConfig.PROPERTY.DATA_TYPE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ((IAppAndAppletCaller) this.mQueryCaller).getCurrentCloseStatus(type);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean hasDialogCloseByHand(String event, String data) {
        return ((IAppAndAppletCaller) this.mQueryCaller).hasDialogCloseByHand();
    }
}
