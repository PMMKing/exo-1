package com.wuye.wuye.utils;

import android.content.Context;
import android.text.TextUtils;

import com.wuye.wuye.page.login.model.LoginResult;
import com.wuye.wuye.utils.storage.Store;

public class UCUtils {

    public final static String TAG = UCUtils.class.getSimpleName();
    public static String COOKIES = "cookies";
    private static UCUtils singleInstance;

    public static UCUtils getInstance() {
        if (singleInstance == null) {
            synchronized (UCUtils.class) {
                singleInstance = new UCUtils();
            }
        }
        return singleInstance;
    }

    private UCUtils() {
    }

    public String getUsername() {
        LoginResult.LoginData userInfo = getUserInfo();
        return userInfo != null ? userInfo.userPhone : "";
    }

    public String getUserPhone() {
        LoginResult.LoginData userInfo = getUserInfo();
        return userInfo != null ? userInfo.userPhone : "";
    }

    public String getUserPWD() {
        LoginResult.LoginData userInfo = getUserInfo();
        return userInfo != null ? userInfo.userPasswd : "";
    }

    public String getUserId() {
        LoginResult.LoginData userInfo = getUserInfo();
        return userInfo != null ? userInfo.id : "";
    }

    public LoginResult.LoginData getUserInfo() {
        Store.refreshStore();
        return Store.getSerializable(COOKIES, LoginResult.LoginData.class, null);
    }

    public boolean userValidate() {
        return !TextUtils.isEmpty(getUserId());
    }

    public void removeCookie() {
        Store.putSerializable(COOKIES, new LoginResult.LoginData());
    }

    public void saveUserInfo(LoginResult.LoginData user) {
        Store.putSerializable(COOKIES, user);
    }

}