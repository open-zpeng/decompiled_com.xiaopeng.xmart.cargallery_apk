package com.xiaopeng.speech.protocol.query.charge;

import com.xiaopeng.speech.SpeechQuery;
import org.json.JSONException;
import org.json.JSONObject;
/* loaded from: classes.dex */
public class ChargeQuery extends SpeechQuery<IChargeCaller> {
    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isSupportOpenPort(String event, String data) {
        return ((IChargeCaller) this.mQueryCaller).isSupportOpenPort();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getStartStatus(String event, String data) {
        return ((IChargeCaller) this.mQueryCaller).getStartStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getStopStatus(String event, String data) {
        return ((IChargeCaller) this.mQueryCaller).getStopStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getRestartStatus(String event, String data) {
        return ((IChargeCaller) this.mQueryCaller).getRestartStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isSupportLimitsAdjust(String event, String data) {
        return ((IChargeCaller) this.mQueryCaller).isSupportLimitsAdjust();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getLimitsAdjustMin(String event, String data) {
        return ((IChargeCaller) this.mQueryCaller).getLimitsAdjustMin();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getLimitsAdjustMax(String event, String data) {
        return ((IChargeCaller) this.mQueryCaller).getLimitsAdjustMax();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isSupportSmartMode(String event, String data) {
        return ((IChargeCaller) this.mQueryCaller).isSupportSmartMode();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isHasChargingOrder(String event, String data) {
        return ((IChargeCaller) this.mQueryCaller).isHasChargingOrder();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isChargeReadyPage(String event, String data) {
        return ((IChargeCaller) this.mQueryCaller).isChargeReadyPage();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getChargeStatus(String event, String data) {
        return ((IChargeCaller) this.mQueryCaller).getChargeStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getPageOpenStatus(String event, String data) {
        return ((IChargeCaller) this.mQueryCaller).getGuiPageOpenState(data);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean hasCarRefrigerator(String event, String data) {
        return ((IChargeCaller) this.mQueryCaller).hasCarRefrigerator();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean hasSunRoof(String event, String data) {
        return ((IChargeCaller) this.mQueryCaller).hasSunRoof();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getTrunkPowerStatus(String event, String data) {
        return ((IChargeCaller) this.mQueryCaller).getTrunkPowerStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getMinDischargeLimit(String event, String data) {
        return ((IChargeCaller) this.mQueryCaller).getMinDischargeLimit();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getMaxDischargeLimit(String event, String data) {
        return ((IChargeCaller) this.mQueryCaller).getMaxDischargeLimit();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getTrunkPowerStatusForOpen(String event, String data) {
        return ((IChargeCaller) this.mQueryCaller).getTrunkPowerStatusForOpen();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isSupportEnduranceMode(String event, String data) {
        int mode = 1;
        try {
            JSONObject jsonObject = new JSONObject(data);
            mode = jsonObject.optInt("mode", 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ((IChargeCaller) this.mQueryCaller).isSupportEnduranceMode(mode);
    }
}
