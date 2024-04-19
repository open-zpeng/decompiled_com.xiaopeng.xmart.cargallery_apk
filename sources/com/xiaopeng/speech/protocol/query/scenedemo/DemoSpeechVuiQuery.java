package com.xiaopeng.speech.protocol.query.scenedemo;

import android.text.TextUtils;
import com.xiaopeng.speech.SpeechQuery;
import com.xiaopeng.speech.protocol.query.speech.vui.ISpeechVuiElementStateQueryCaller;
import org.json.JSONException;
import org.json.JSONObject;
/* loaded from: classes.dex */
public class DemoSpeechVuiQuery extends SpeechQuery<ISpeechVuiElementStateQueryCaller> {
    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isSwitchChecked(String event, String data) {
        try {
            JSONObject json = new JSONObject(data);
            String elementId = json.optString("elementId", "");
            String sceneId = json.optString("sceneId", "");
            String state = ((ISpeechVuiElementStateQueryCaller) this.mQueryCaller).getVuiElementState(sceneId, elementId);
            if (!TextUtils.isEmpty(state)) {
                JSONObject stateJson = new JSONObject(state);
                if (stateJson.has("value")) {
                    boolean isChecked = stateJson.optJSONObject("value").optJSONObject("SetCheck").optBoolean("value", false);
                    return isChecked;
                } else if (!stateJson.has("values")) {
                    return false;
                } else {
                    boolean isChecked2 = stateJson.optJSONObject("values").optJSONObject("SetCheck").optBoolean("value", false);
                    return isChecked2;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isTableLayoutSelected(String event, String data) {
        try {
            JSONObject dataJson = new JSONObject(data);
            int index = dataJson.optInt("index", 0);
            String elementId = dataJson.optString("elementId", "");
            String sceneId = dataJson.optString("sceneId", "");
            String state = ((ISpeechVuiElementStateQueryCaller) this.mQueryCaller).getVuiElementState(sceneId, elementId);
            if (!TextUtils.isEmpty(state)) {
                JSONObject json = new JSONObject(state);
                JSONObject valueObj = null;
                if (json.has("value")) {
                    valueObj = json.optJSONObject("value");
                } else if (json.has("values")) {
                    valueObj = json.optJSONObject("values");
                }
                if (valueObj != null) {
                    int value = 0;
                    if (valueObj.has("SetValue")) {
                        value = valueObj.optJSONObject("SetValue").optInt("value", 0);
                    } else if (valueObj.has("SelectTab")) {
                        value = valueObj.optJSONObject("SelectTab").optInt("value", 0);
                    }
                    return value == index;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getXSliderIndex(String event, String data) {
        try {
            JSONObject dataJson = new JSONObject(data);
            String elementId = dataJson.optString("elementId", "");
            String sceneId = dataJson.optString("sceneId", "");
            String state = ((ISpeechVuiElementStateQueryCaller) this.mQueryCaller).getVuiElementState(sceneId, elementId);
            if (!TextUtils.isEmpty(state)) {
                JSONObject json = new JSONObject(state);
                double value = 0.0d;
                if (json.has("value")) {
                    value = json.optJSONObject("value").optJSONObject("SetValue").optDouble("value", 0.0d);
                } else if (json.has("values")) {
                    value = json.optJSONObject("values").optJSONObject("SetValue").optDouble("value", 0.0d);
                }
                return (int) value;
            }
            return 0;
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isStatefulButtonChecked(String event, String data) {
        try {
            JSONObject dataJson = new JSONObject(data);
            String elementId = dataJson.optString("elementId", "");
            String sceneId = dataJson.optString("sceneId", "");
            String state = ((ISpeechVuiElementStateQueryCaller) this.mQueryCaller).getVuiElementState(sceneId, elementId);
            if (!TextUtils.isEmpty(state)) {
                JSONObject json = new JSONObject(state);
                if (json.has("value")) {
                    return json.optJSONObject("value").optJSONObject("SetCheck").optBoolean("value", false);
                }
                if (json.has("values")) {
                    return json.optJSONObject("values").optJSONObject("SetCheck").optBoolean("value", false);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String getStatefulButtonValue(String event, String data) {
        try {
            JSONObject dataJson = new JSONObject(data);
            String elementId = dataJson.optString("elementId", "");
            String sceneId = dataJson.optString("sceneId", "");
            String state = ((ISpeechVuiElementStateQueryCaller) this.mQueryCaller).getVuiElementState(sceneId, elementId);
            if (!TextUtils.isEmpty(state)) {
                JSONObject json = new JSONObject(state);
                if (json.has("value")) {
                    String value = json.optJSONObject("value").optJSONObject("SetValue").optString("value", null);
                    return value;
                } else if (!json.has("values")) {
                    return null;
                } else {
                    String value2 = json.optJSONObject("values").optJSONObject("SetValue").optString("value", null);
                    return value2;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isElementEnabled(String event, String data) {
        try {
            JSONObject dataJson = new JSONObject(data);
            String elementId = dataJson.optString("elementId", "");
            String sceneId = dataJson.optString("sceneId", "");
            String state = ((ISpeechVuiElementStateQueryCaller) this.mQueryCaller).getVuiElementState(sceneId, elementId);
            if (!TextUtils.isEmpty(state)) {
                JSONObject json = new JSONObject(state);
                boolean value = json.optBoolean("enabled", true);
                return value;
            }
            return false;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public boolean isElementCanScrolled(String event, String data) {
        try {
            JSONObject dataJson = new JSONObject(data);
            String elementId = dataJson.optString("elementId", "");
            String sceneId = dataJson.optString("sceneId", "");
            String direction = dataJson.optString("direction", "");
            String state = ((ISpeechVuiElementStateQueryCaller) this.mQueryCaller).getVuiElementState(sceneId, elementId);
            if (!TextUtils.isEmpty(state)) {
                JSONObject json = new JSONObject(state);
                String props = null;
                char c = 65535;
                switch (direction.hashCode()) {
                    case 3739:
                        if (direction.equals("up")) {
                            c = 1;
                            break;
                        }
                        break;
                    case 3089570:
                        if (direction.equals("down")) {
                            c = 3;
                            break;
                        }
                        break;
                    case 3317767:
                        if (direction.equals("left")) {
                            c = 0;
                            break;
                        }
                        break;
                    case 108511772:
                        if (direction.equals("right")) {
                            c = 2;
                            break;
                        }
                        break;
                }
                switch (c) {
                    case 0:
                        props = "canScrollLeft";
                        break;
                    case 1:
                        props = "canScrollUp";
                        break;
                    case 2:
                        props = "canScrollRight";
                        break;
                    case 3:
                        props = "canScrollDown";
                        break;
                }
                boolean value = json.optBoolean(props, true);
                return value;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isCheckBoxChecked(String event, String data) {
        try {
            JSONObject dataJson = new JSONObject(data);
            String elementId = dataJson.optString("elementId", "");
            String sceneId = dataJson.optString("sceneId", "");
            String state = ((ISpeechVuiElementStateQueryCaller) this.mQueryCaller).getVuiElementState(sceneId, elementId);
            if (!TextUtils.isEmpty(state)) {
                JSONObject json = new JSONObject(state);
                if (json.has("value")) {
                    return json.optJSONObject("value").optJSONObject("SetCheck").optBoolean("value", false);
                }
                if (json.has("values")) {
                    return json.optJSONObject("values").optJSONObject("SetCheck").optBoolean("value", false);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isRadiobuttonChecked(String event, String data) {
        try {
            JSONObject dataJson = new JSONObject(data);
            String elementId = dataJson.optString("elementId", "");
            String sceneId = dataJson.optString("sceneId", "");
            String state = ((ISpeechVuiElementStateQueryCaller) this.mQueryCaller).getVuiElementState(sceneId, elementId);
            if (!TextUtils.isEmpty(state)) {
                JSONObject json = new JSONObject(state);
                if (json.has("value")) {
                    return json.optJSONObject("value").optJSONObject("SetCheck").optBoolean("value", false);
                }
                if (json.has("values")) {
                    return json.optJSONObject("values").optJSONObject("SetCheck").optBoolean("value", false);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
}
