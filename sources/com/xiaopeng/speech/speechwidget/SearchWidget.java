package com.xiaopeng.speech.speechwidget;

import org.json.JSONObject;
/* loaded from: classes.dex */
public class SearchWidget extends SpeechWidget {
    private static final String SEARCH_CHARGING_STATION = "chargingStation";
    private static final String SEARCH_FOOD = "food";
    public static final int SEARCH_TYPE_CHARGING = 1;
    public static final int SEARCH_TYPE_FOOD = 2;
    public static final int SEARCH_TYPE_NONE = 0;
    private int searchType;

    public SearchWidget() {
        super(SpeechWidget.TYPE_SEARCH);
    }

    @Override // com.xiaopeng.speech.speechwidget.SpeechWidget
    public SearchWidget fromJson(String data) {
        super.fromJson(data);
        JSONObject jsonObject = this.mWidget.optJSONObject(SpeechWidget.WIDGET_SEARCH_CONTENT);
        if (jsonObject != null) {
            String type = jsonObject.optString("searchType");
            char c = 65535;
            switch (type.hashCode()) {
                case -604125821:
                    if (type.equals(SEARCH_CHARGING_STATION)) {
                        c = 0;
                        break;
                    }
                    break;
                case 3148894:
                    if (type.equals(SEARCH_FOOD)) {
                        c = 1;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    this.searchType = 1;
                    break;
                case 1:
                    this.searchType = 2;
                    break;
                default:
                    this.searchType = 0;
                    break;
            }
        }
        return this;
    }

    public String getTitle() {
        return getExtra(SpeechWidget.WIDGET_TITLE);
    }

    public int getCurrentPage() {
        return getIntContent("currentPage") - 1;
    }

    public int getPageSize() {
        return getIntContent("itemsPerPage");
    }

    public int getOpenDetailPosition() {
        return getIntContent("curPageOpenSerial") - 1;
    }

    public JSONObject getWidget() {
        return this.mWidget;
    }

    public int getSearchType() {
        return this.searchType;
    }
}
