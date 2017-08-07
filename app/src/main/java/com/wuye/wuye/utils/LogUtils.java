package com.wuye.wuye.utils;

import android.util.Log;

import com.wuye.wuye.utils.storage.BuildConfig;


/**
 * Created by shucheng.qu on 2017/5/26.
 */

public class LogUtils {
    public static final boolean isDebug = BuildConfig.DEBUG;

    public static final String TAG = "qushucheng";

    public static void d(String msg) {
        d(TAG, msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug) {
            Log.d(tag, msg);
        }
    }

    public static void v(String msg) {
        v(TAG, msg);
    }

    public static void v(String tag, String msg) {
        if (isDebug) {
            Log.v(tag, msg);
        }
    }

    public static void e(String msg) {
        e(TAG, msg);
    }

    public static void e(String tag, String msg) {
        if (isDebug) {
            Log.e(tag, msg);
        }
    }

    public static void e(Class cls, String msg) {
        if (isDebug) {
            Log.e(TAG + cls.getSimpleName(), msg);
        }
    }
}
