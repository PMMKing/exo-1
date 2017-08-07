package com.wuye.wuye.config;


import com.wuye.wuye.utils.storage.BuildConfig;

/**
 * Created by shucheng.qu on 2017/5/26.
 */

public class AppConfig {
    public final static String BASE_URL = "http://120.27.13.1:8081/";
    public final static String CONTENT_URL = "pub";

    public final static String DEV_BASE_URL = "http://120.27.13.1:8081/";
    public final static String DEV_CONTENT_URL = "pub";

    public final static int HB = 160;

    public static final boolean DEBUG = BuildConfig.DEBUG;

    public static String getBaseUrl() {
        if (DEBUG && DebugUtils.isOpenDev()) {
            return DEV_BASE_URL;
        }
        return BASE_URL;
    }

    public static String getContentUrl() {
        if (DEBUG && DebugUtils.isOpenDev()) {
            return CONTENT_URL;
        }
        return DEV_CONTENT_URL;
    }


    public static final String WEIXIN_APP_ID = "";
    public static final String WEIXIN_APPSECRET = "";
    public static final String SINA_APP_ID = "";
    public static final String SINA_APPSECRET = "";
    public static final String QQ_APP_ID = "";
    public static final String QQ_APPSECRET = "";
    public static final String MaxentKey = "";
    public static final String MaxentUrl = "";



    public final static String COMMON_URL = "http://123.56.121.2:8080/bang/customer";

    /**
     *  **/
    public final static boolean KILL_PROGRESS_ON_QUIT = false;

    public static final int DATABASE_VERSION = 1;

    public static final String URL_FOR_UPGRADE = "url_for_upgrade";
    public static final String NEW_VERSION = "new_version";
    public static final String ISUPDATE = "isupdate";

    public static final String WEBVIEW_URL = "url";
    public static final String WEBVIEW_CONTENT = "content";

}
