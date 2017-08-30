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
import com.ucar.weex.UWXInit;
import com.ucar.weex.update.UWXResManager;

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
        UWXInit.init(this);
        /**
         * assets/weex/ucar-weex_3_20170828123442
         */
        UWXResManager.getInstance().addWXResFromAssert(this, getWXPackageFileName("weex"));
    }

    public static String getWXPackageFileName(String weexFileName) {
        try {
            String[] assets = instance.getAssets().list(weexFileName);
            if (!ArrayUtils.isEmpty(assets)) {
                String asset = assets[0];
                int i = asset.indexOf(".");
                String rnName = asset.substring(0, i);
                weexFileName = weexFileName + "/" + rnName;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return weexFileName;
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
