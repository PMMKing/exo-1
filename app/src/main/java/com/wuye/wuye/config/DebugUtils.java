package com.wuye.wuye.config;


import com.wuye.wuye.utils.storage.Store;

/**
 * Created by shucheng.qu on 2017/5/26.
 */

public class DebugUtils {
    public static boolean isMock() {
        return AppConfig.DEBUG && Store.get("isMock", false);
    }

    public static void setIsMock(boolean isMock) {
        Store.put("isMock", isMock);
    }

    public static boolean isOpenDev() {
        return AppConfig.DEBUG && Store.get("open_dev", true);
    }

    public static void setOpenDev(boolean openDev) {
        Store.put("open_dev", openDev);
    }

    public static void saveBaseUrl(String baseUrl) {
        Store.put("base_url", baseUrl);
    }

    public static String getBaseUrl() {
        return Store.get("base_url", AppConfig.BASE_URL);
    }


    public static String getMockIP() {
        return Store.get("mock_ip", "169.254.46.38");
    }

    public static void saveMockIp(String ip) {
        Store.put("mock_ip", ip);
    }


    public static void resetUrls() {
        saveBaseUrl(AppConfig.BASE_URL);
    }
}
