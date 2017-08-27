package com.page.uc;

import android.text.TextUtils;

import com.framework.app.MainApplication;
import com.framework.utils.SPUtils;
import com.page.uc.bean.LoginResult;
import com.page.uc.bean.LoginResult.LoginData;

/**
 * Created by chenxi.cui on 2017/8/13.
 */

public class UCUtils {
    private static UCUtils instance = null;
    private LoginData userInfo;


    private UCUtils() {
    }


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

    public String getPortrait() {
        return getUserInfo().portrait;
    }

    public String getUserid() {
        return getUserInfo().userId;
    }

    public String getToken() {
        return getUserInfo().token;
    }

    public boolean isLogin() {
        return !TextUtils.isEmpty(getToken());
    }

    public static final String userInfokey = "userInfo";

    public void saveUserInfo(LoginData data) {
        userInfo = data;
        SPUtils.put(MainApplication.getInstance(), userInfokey, data);
    }

    public LoginData getUserInfo() {
        if (userInfo != null) {
            return userInfo;
        }
        userInfo = (LoginData) SPUtils.get(MainApplication.getInstance(), userInfokey, new LoginData());
        if (userInfo == null) {
            userInfo = new LoginData();
        }
        return userInfo;
    }

    public void savePortrait(String portrait) {
        LoginData data = getUserInfo();
        data.portrait = portrait;

        saveUserInfo(data);
    }

    public void saveUsername(String username) {
        LoginData data = getUserInfo();
        data.nickname = username;
        saveUserInfo(data);

    }
}
