package com.framework.domain;

/**
 * Created by chenxi.cui on 2017/8/13.
 */

public class UCUtils {
    private static UCUtils instance = null;


    private UCUtils(){}


    public static UCUtils getInstance() {
        if (instance == null) {
            synchronized (UCUtils.class) {
                if (instance == null) {
                    instance = new UCUtils();
                }
            }
        }
        return instance;
    }

    public String getUserid() {
        return "";
    }

    public String getToken() {
        return "";
    }
}
