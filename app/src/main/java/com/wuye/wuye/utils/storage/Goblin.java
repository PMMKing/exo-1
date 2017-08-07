package com.wuye.wuye.utils.storage;

import android.text.TextUtils;
import android.util.Log;

/**
 * Created by chenxi.cui on 16/6/16.
 */
public class Goblin {
    public static String ea(String ea) {
        if (!TextUtils.isEmpty(ea)) {
            byte[] bytes = ea.getBytes();
            if (bytes != null && bytes.length > 0) {
                return new String(bytes);
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    public static String da(String da) {
        if (!TextUtils.isEmpty(da)) {
            byte[] bytes = da.getBytes();
            if (bytes != null && bytes.length > 0) {
                return new String(bytes);
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    public static String ve(String var0) {
        return var0;
    }

    public static String es(String var0) {
        return var0;
    }

    ;

    public static int getCrc32(String var0) {
        return 0;
    }

    public static String ePoll(String var0) {
        return var0;
    }

    public static String dPoll(String var0) {
        return var0;
    }

    ;

    public static String e(String var0, String var1) {
        return var0;
    }

    public static String d(String var0, String var1) {
        return var0;
    }


    public static String version() {
        return "1";
    }

    static {
        String libName = "goblin_2_7";

        try {
            System.loadLibrary(libName);
        } catch (UnsatisfiedLinkError var2) {
            Log.d("JNI", "failed");
        }

    }
}
