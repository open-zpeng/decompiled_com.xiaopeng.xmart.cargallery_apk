package com.xiaopeng.xmart.cargallery;

import com.xiaopeng.xmart.cargallery.bean.TabConfig;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/* loaded from: classes13.dex */
public class CarGalleryConfig {
    public static final String INTENT_EXTRA_DVR_DIR = "intent_extra_dvr_dir";
    public static final String INTENT_EXTRA_ITEM_PATH = "intent_extra_item_path";
    public static final String INTENT_EXTRA_LIST_INDEX = "intent_extra_list_index";
    public static final String INTENT_EXTRA_SELECTED_TAB = "intent_extra_selected_tab";
    public static final String INTENT_EXTRA_TAB = "intent_extra_tab";
    public static final String INTENT_EXTRA_TAB_LIST = "intent_extra_tab_list";
    public static final String SENTRY_MODE_COLLISION_SUFFIX = "_collision";
    public static final String TAB_CAMERA_360 = "tab_camera_360";
    public static final String TAB_CAMERA_BACK = "tab_camera_back";
    public static final String TAB_CAR_DVR = "tab_camera_dvr";
    public static final String TAB_CAR_FRONT = "tab_camera_front";
    public static final String TAB_CAR_TOP = "tab_camera_top";

    public static List<TabConfig> getTabConfig(ArrayList<String> typeList) {
        List<TabConfig> result = new ArrayList<>();
        if (typeList != null && typeList.size() > 0) {
            Iterator<String> it = typeList.iterator();
            while (it.hasNext()) {
                String type = it.next();
                TabConfig tabConfig = new TabConfig();
                char c = 65535;
                switch (type.hashCode()) {
                    case -264335337:
                        if (type.equals(TAB_CAMERA_BACK)) {
                            c = 0;
                            break;
                        }
                        break;
                    case 399751417:
                        if (type.equals(TAB_CAR_FRONT)) {
                            c = 2;
                            break;
                        }
                        break;
                    case 1931089149:
                        if (type.equals(TAB_CAMERA_360)) {
                            c = 1;
                            break;
                        }
                        break;
                    case 1931138288:
                        if (type.equals(TAB_CAR_DVR)) {
                            c = 4;
                            break;
                        }
                        break;
                    case 1931153445:
                        if (type.equals(TAB_CAR_TOP)) {
                            c = 3;
                            break;
                        }
                        break;
                }
                switch (c) {
                    case 0:
                        tabConfig.tabType = type;
                        tabConfig.tabTitleResId = R.string.tab_camera_back;
                        break;
                    case 1:
                        tabConfig.tabType = type;
                        tabConfig.tabTitleResId = R.string.tab_camera_360;
                        break;
                    case 2:
                        tabConfig.tabType = type;
                        tabConfig.tabTitleResId = R.string.tab_car_record;
                        break;
                    case 3:
                        tabConfig.tabType = type;
                        tabConfig.tabTitleResId = R.string.tab_car_top;
                        break;
                    case 4:
                        tabConfig.tabType = type;
                        tabConfig.tabTitleResId = R.string.tab_car_record;
                        break;
                }
                result.add(tabConfig);
            }
            return result;
        }
        return getDefaultTabList();
    }

    private static List<TabConfig> getDefaultTabList() {
        List<TabConfig> result = new ArrayList<>();
        TabConfig tabConfig = new TabConfig();
        tabConfig.tabType = TAB_CAMERA_360;
        tabConfig.tabTitleResId = R.string.tab_camera_360;
        result.add(tabConfig);
        return result;
    }
}
