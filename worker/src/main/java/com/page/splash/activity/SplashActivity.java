package com.page.splash.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.framework.activity.BaseActivity;
import com.page.home.activity.MainActivity;


/**
 * Created by shucheng.qu on 2017/5/27.
 */

public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        qStartActivity(LoginActivity.class);
        startMainActivity();
    }


    private void startMainActivity() {
        Intent intent = new Intent();
        intent.setClass(getContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        qStartActivity(intent);
        finish();
    }
}