//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wuye.wuye.utils.viewutils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.view.View;

import com.wuye.wuye.utils.viewutils.ViewSetter.Method;

import java.util.concurrent.atomic.AtomicInteger;

public class ViewUtils {
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    public ViewUtils() {
    }

    public static int generateViewId() {
        int result;
        int newValue;
        do {
            result = sNextGeneratedId.get();
            newValue = result + 1;
            if(newValue > 16777215) {
                newValue = 1;
            }
        } while(!sNextGeneratedId.compareAndSet(result, newValue));

        return result;
    }

    public static int fakeGenId() {
        int realId = generateViewId();
        int fakeId = realId | 268435456;
        return fakeId;
    }

    public static int unionGenId(int id) {
        if(id == 0) {
            throw new IllegalArgumentException("Id NOT ALLOW 0 !");
        } else {
            int realId = id & 268435455;
            int fakeId = realId | 536870912;
            return fakeId;
        }
    }

    public static boolean setOrGone(View v, CharSequence cs) {
        return ViewSetter.getSetting(v).setOr(Method.Text, true, 8, new Object[]{cs});
    }

    public static boolean setOrHide(View v, CharSequence cs) {
        return ViewSetter.getSetting(v).setOr(Method.Text, true, 4, new Object[]{cs});
    }

    public static boolean setOrGone(View v, Drawable drawable) {
        return ViewSetter.getSetting(v).setOr(Method.Background, true, 8, new Object[]{drawable});
    }

    public static boolean setOrHide(View v, Drawable drawable) {
        return ViewSetter.getSetting(v).setOr(Method.Background, true, 4, new Object[]{drawable});
    }

    public static boolean setOrGone(View v, CharSequence... cs) {
        return ViewSetter.getSetting(v).setOr(Method.Text, true, 8, (Object[])cs);
    }

    public static boolean setOrHide(View v, CharSequence... cs) {
        return ViewSetter.getSetting(v).setOr(Method.Text, true, 4, (Object[])cs);
    }

    public static boolean setOrGone(View v, boolean condition) {
        return ViewSetter.getSetting(v).setOr(Method.NotCare, condition, 8, new Object[0]);
    }

    public static boolean setOrHide(View v, boolean condition) {
        return ViewSetter.getSetting(v).setOr(Method.NotCare, condition, 4, new Object[0]);
    }

    public static boolean setOrGone(View v, boolean condition, CharSequence cs) {
        return ViewSetter.getSetting(v).setOr(Method.Text, condition, 8, new Object[]{cs});
    }

    public static boolean setOrHide(View v, boolean condition, CharSequence cs) {
        return ViewSetter.getSetting(v).setOr(Method.Text, condition, 4, new Object[]{cs});
    }

    public static boolean setOrGone(View v, Bitmap res) {
        return ViewSetter.getSetting(v).setOr(Method.Src, true, 8, new Object[]{res});
    }

    public static boolean setOrHide(View v, Bitmap res) {
        return ViewSetter.getSetting(v).setOr(Method.Src, true, 4, new Object[]{res});
    }

    public static CharSequence joinAllowedNull(Object... str) {
        if(CheckUtils.isEmpty(str)) {
            return null;
        } else {
            StringBuilder sb = new StringBuilder();
            Object[] var2 = str;
            int var3 = str.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                Object o = var2[var4];
                if(!CheckUtils.isEmpty(o)) {
                    sb.append(o.toString());
                }
            }

            if(CheckUtils.isEmpty(sb)) {
                return null;
            } else {
                return sb.toString();
            }
        }
    }

    public static CharSequence joinNotAllowedNull(Object... str) {
        if(CheckUtils.isContainsEmpty(str)) {
            return null;
        } else {
            StringBuilder sb = new StringBuilder();
            Object[] var2 = str;
            int var3 = str.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                Object o = var2[var4];
                sb.append(o.toString());
            }

            return sb.toString();
        }
    }

    public static StateListDrawable makeColorMask(Context context, int color) {
        return makeColorMask(context, color, 150994944);
    }

    public static StateListDrawable makeColorMask(Context context, int color, int argb) {
        ColorDrawable unstate = new ColorDrawable(color);
        Bitmap pressed = Bitmap.createBitmap(1, 1, Config.ARGB_8888);
        Canvas canvas = new Canvas(pressed);
        canvas.drawColor(color);
        canvas.drawColor(150994944);
        StateListDrawable stateDrawable = new StateListDrawable();
        int statePressed = 16842919;
        stateDrawable.addState(new int[]{-statePressed}, unstate);
        stateDrawable.addState(new int[]{statePressed}, new BitmapDrawable(pressed));
        return stateDrawable;
    }

    public static StateListDrawable makeColorMask(Context context, Bitmap bi) {
        return makeColorMask(context, bi, 150994944);
    }

    public static StateListDrawable makeColorMask(Context context, Bitmap bi, int argb) {
        Bitmap unstate = bi.copy(Config.ARGB_8888, false);
        Bitmap pressed = bi.copy(Config.ARGB_8888, true);
        Canvas canvas = new Canvas(pressed);
        canvas.drawColor(argb);
        StateListDrawable stateDrawable = new StateListDrawable();
        int statePressed = 16842919;
        stateDrawable.addState(new int[]{-statePressed}, new BitmapDrawable(unstate));
        stateDrawable.addState(new int[]{statePressed}, new BitmapDrawable(pressed));
        return stateDrawable;
    }

    public static Drawable scaleByDensity(Context context, Drawable drawable) {
        int width = (int)((float)drawable.getIntrinsicWidth() * context.getResources().getDisplayMetrics().density);
        int height = (int)((float)drawable.getIntrinsicHeight() * context.getResources().getDisplayMetrics().density);
        drawable.setBounds(0, 0, width, height);
        return drawable;
    }

    public static Drawable scaleByDensity(Context context, Bitmap bitmap) {
        return scaleByDensity(context, (Drawable)(new BitmapDrawable(bitmap)));
    }
}
