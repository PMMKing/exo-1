package com.wuye.wuye;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by shucheng.qu on 2017/5/26.
 */

public class WApplication extends Application {

    private static Context mContext;
    private static Typeface iconFont;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Typeface getIconFont() {

        if (iconFont == null) {
            synchronized (Typeface.class) {
                if (iconFont == null) {
                    iconFont = Typeface.createFromAsset(getContext().getAssets(), "iconfont/iconfont.ttf");
                }
            }
        }
        return iconFont;
    }

    public static Context getContext() {
        return mContext;
    }
}
