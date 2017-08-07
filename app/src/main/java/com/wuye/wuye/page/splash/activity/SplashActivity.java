package com.wuye.wuye.page.splash.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;

import com.wuye.wuye.base.BaseActivity;
import com.wuye.wuye.page.home.activity.MainActivity;
import com.wuye.wuye.page.login.activity.LoginActivity;
import com.wuye.wuye.utils.UCUtils;

/**
 * Created by shucheng.qu on 2017/5/27.
 */

public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        animType = CloseActivityAnim.DOWN;

        startMainActivity();
    }


    private void startMainActivity() {
        Intent intent = new Intent();
        intent.setClass(getContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        qStartActivity(intent);
    }
}
