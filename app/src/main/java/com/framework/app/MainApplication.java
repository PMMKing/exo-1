/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.framework.app;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;

import com.framework.utils.ArrayUtils;
import com.igexin.sdk.PushManager;
import com.igexin.sdk.PushService;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.IOException;

public class MainApplication extends Application {

    public static Context applicationContext;
    private static MainApplication instance;
    private static Typeface iconFont;
    public String versionName;
    public int versionCode;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
        instance = this;
        try {
            String pkName = this.getPackageName();
            PackageInfo packageInfo = this.getPackageManager().getPackageInfo(
                    pkName, 0);
            versionName = packageInfo.versionName;
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);
// 将该app注册到微信
        msgApi.registerApp(AppConstants.APP_ID);
        PushManager.getInstance().initialize(this, com.qfant.wuye.push.PushService.class);
    }


    public static MainApplication getInstance() {
        return instance;
    }


    public static Typeface getIconFont() {

        if (iconFont == null) {
            synchronized (Typeface.class) {
                if (iconFont == null) {
                    iconFont = Typeface.createFromAsset(applicationContext.getAssets(), "iconfont/iconfont.ttf");
                }
            }
        }
        return iconFont;
    }


}
