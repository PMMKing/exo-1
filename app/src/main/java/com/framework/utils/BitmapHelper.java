package com.framework.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import com.framework.app.MainApplication;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class BitmapHelper {

    public static Bitmap decodeFile(String pathName, float density) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        float f = DisplayMetrics.DENSITY_MEDIUM * density;
        options.inDensity = (int) f;
        Bitmap bm = BitmapFactory.decodeFile(pathName, options);
        if (bm != null) {
            bm.setDensity((int) f);
        }
        return bm;
    }

    public static Bitmap decodeStream(InputStream is) {
        BitmapFactory.Options localOptions = new BitmapFactory.Options();
        localOptions.inDensity = DisplayMetrics.DENSITY_MEDIUM;
        localOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
        return BitmapFactory.decodeStream(is, null, localOptions);
    }

    public static Bitmap decodeStream(InputStream is, float density) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inDensity = (int) (DisplayMetrics.DENSITY_MEDIUM * density);
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        return BitmapFactory.decodeStream(is, null, options);
    }

    public static Bitmap decodeURL(String urlString) {
        try {
            // Log.d(TAG, "get bitmap from url:" + urlString);
            HttpURLConnection conn = (HttpURLConnection) new URL(urlString).openConnection();
            conn.setDoInput(true);
            conn.connect();
            return decodeStream(conn.getInputStream());
        } catch (IOException e) {
            // Log.e(TAG, "get bitmap from url failed", e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 如需二次计算,请用 {@link #dip2pxF}
     */
    public static int dip2px(Context context, float dp) {
        return (int) (convertUnitToPixel(context, TypedValue.COMPLEX_UNIT_DIP, dp) + 0.5f);
    }

    public static Bitmap compressImage(Bitmap image) {
        if (image == null) {
            return null;
        }
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, output);
        float zoom = (float) Math.sqrt(100 * 1024 / (float) output.toByteArray().length); //获取缩放比例
        // 设置矩阵数据
        Matrix matrix = new Matrix();
        matrix.setScale(zoom, zoom);
        // 根据矩阵数据进行新bitmap的创建
        Bitmap resultBitmap = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), matrix, true);
        output.reset();
        resultBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
        if (image != resultBitmap) {
            image.recycle();
        }
        try {
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultBitmap;
    }

    /**
     * dip2px的返回float版
     *
     * @author ran.feng
     * @see #dip2px
     */
    public static float dip2pxF(Context context, float dp) {
        return convertUnitToPixel(context, TypedValue.COMPLEX_UNIT_DIP, dp);
    }

    public static float px2dip(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return px / scale;
    }

    public static int px(float dp) {
        return (int) (dip2px(MainApplication.getInstance(), dp) + 0.5f);
    }

    /**
     * iPhone效果图上标注的px值转当前机器真实px值,可直接用来设置Margin之类的. 如需二次计算,请用 {@link #iPXToPXF}提高精度
     *
     * @param context
     * @param iPX     iPhone效果图上的px值
     * @return 真实px值
     * @author ran.feng
     * @since 2013年9月19日下午2:19:53
     */
    public static int iPXToPX(Context context, float iPX) {
        float px = context.getResources().getDisplayMetrics().widthPixels / 640f * iPX + 0.5f;
        return (int) px;
    }

    /**
     * iPXToPX的返回float版
     *
     * @author ran.feng
     * @see #iPXToPX
     */
    public static float iPXToPXF(Context context, float iPX) {
        return context.getResources().getDisplayMetrics().widthPixels / 640f * iPX;
    }

    private static float convertUnitToPixel(Context context, int unit, float in) {
        return TypedValue.applyDimension(unit, in, context.getResources().getDisplayMetrics());
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        // 取 drawable 的长宽
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();

        // 取 drawable 的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 图片叠加器
     *
     * @param bottomResId
     * @param topResId
     * @return
     */
    public static Bitmap bitmapAdder(int bottomResId, int topResId) {
        return bitmapAdder(bottomResId, topResId, 0);
    }

    public static Bitmap bitmapAdder(int bottomResId, int topResId, float padding) {
        Bitmap bottomBmp = BitmapFactory.decodeResource(MainApplication.getInstance().getResources(), bottomResId).copy(
                Bitmap.Config.ARGB_8888, true);//
        Bitmap topBmp = BitmapFactory.decodeResource(MainApplication.getInstance().getResources(), topResId);
        Canvas canvas = new Canvas(bottomBmp);
        canvas.drawBitmap(topBmp, bottomBmp.getWidth() - topBmp.getWidth() - padding, padding, null);
        return bottomBmp;
    }

    /**
     * 读取资源文件返回可编辑的Bitmap
     *
     * @param res
     * @param id
     * @return
     */
    public static Bitmap decodeResource(Resources res, int id) {
        Bitmap bitmap = null;
        try {
            Bitmap original = BitmapFactory.decodeResource(res, id);
            bitmap = original.copy(Bitmap.Config.ARGB_8888, true);
            original.recycle();
        } catch (OutOfMemoryError e) {
        }
        return bitmap;
    }

    /**
     * 从view 得到图片
     *
     * @param view
     * @return
     */
    public static Bitmap getBitmapFromView(View view) {
        view.destroyDrawingCache();
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.setDrawingCacheEnabled(true);
        return view.getDrawingCache(true);
    }
}
