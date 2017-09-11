package com.framework.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

/**
 * Created by shucheng.qu on 2017/9/11.
 */

public class ToastUtils {

    private static Toast toast = null;
    private static Handler handler = new Handler(Looper.getMainLooper());

    public ToastUtils() {
    }

    public static void toastSth(final Context context, final String con) {
        handler.post(new Runnable() {
            public void run() {
                if (ToastUtils.toast != null) {
                    ToastUtils.toast.cancel();
                }

                ToastUtils.toast = Toast.makeText(context, con, Toast.LENGTH_SHORT);
                ToastUtils.toast.show();
            }
        });
    }

}
