package com.wuye.wuye.utils.simpleutils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

import com.wuye.wuye.utils.LogUtils;


/**
 * Created by shucheng.qu flight_on 2016/8/24 0024.
 */
public class SpliceBitMapUtils {

    /**
     * @param firstBitmap
     * @param secoedBitmap
     * @return 上下拼接，第一个在下，宽度匹配第二个的宽度
     */
    public static Bitmap upAndDownSplice(Bitmap firstBitmap, Bitmap secoedBitmap) {
        if (firstBitmap == null || secoedBitmap == null) {
            return null;
        }

        int sWidth = secoedBitmap.getWidth();
        int sHeight = secoedBitmap.getHeight();

        int fWidth = firstBitmap.getWidth();
        int fHeight = firstBitmap.getHeight();


        Rect fRect = new Rect(0, 0, fWidth, fHeight);
        LogUtils.d("qushucheng" , fWidth + "  :  " + fHeight );
        double factor = sWidth / (double) fWidth;
        fWidth = (int) (factor * fWidth);
        fHeight = (int) (factor * fHeight);
        LogUtils.d("qushucheng" , " 计算后的  :  " + fWidth + "  :  " + fHeight );
        //create the new blank bitmap 创建一个新的和SRC长度宽度一样的位图
        Bitmap newbmp = Bitmap.createBitmap(sWidth, fHeight + sHeight, Bitmap.Config.ARGB_8888);
        Canvas cv = new Canvas(newbmp);
        //draw bg into
        cv.drawBitmap(firstBitmap, fRect, new RectF(0, sHeight, fWidth, fHeight + sHeight), null);
        //draw fg into
        cv.drawBitmap(secoedBitmap, new Rect(0, 0, sWidth, sHeight), new RectF(0, 0, sWidth, sHeight), null);
        //save all clip
//        //draw bg into
//        cv.drawBitmap(firstBitmap, 0, 0, null);//在 0，0坐标开始画入bg
//        //draw fg into
//        cv.drawBitmap(secoedBitmap, 0, fHeight, null);//在 0，0坐标开始画入fg ，可以从任意位置画入
//        //save all clip
        cv.save(Canvas.ALL_SAVE_FLAG);//保存
        //store
        cv.restore();//存储
        return newbmp;
    }

    /**
     * @param firstBitmap
     * @param secoedBitmap
     * @return 左右拼接，第一个在左边，高度匹配第二个
     */
    public static Bitmap aroundSplice(Bitmap firstBitmap, Bitmap secoedBitmap) {
        if (firstBitmap == null || secoedBitmap == null) {
            return null;
        }

        int sWidth = secoedBitmap.getWidth();
        int sHeight = secoedBitmap.getHeight();

        int fWidth = firstBitmap.getWidth();
        int fHeight = firstBitmap.getHeight();
        Rect fRect = new Rect(0, 0, fWidth, fHeight);
        double factor = sHeight / (double) fHeight;
        fWidth = (int) (factor * fWidth);
        fHeight = (int) (factor * fHeight);

        //create the new blank bitmap 创建一个新的和SRC长度宽度一样的位图
        Bitmap newbmp = Bitmap.createBitmap(sWidth + fWidth, sHeight, Bitmap.Config.ARGB_8888);
        Canvas cv = new Canvas(newbmp);
        //draw bg into
        cv.drawBitmap(firstBitmap, fRect, new RectF(0, 0, fWidth, fHeight), null);
        //draw fg into
        cv.drawBitmap(secoedBitmap, new Rect(0, 0, sWidth, sHeight), new RectF(fWidth, 0, sWidth + fWidth, sHeight), null);//在 0，0坐标开始画入fg ，可以从任意位置画入
        //save all clip
        cv.save(Canvas.ALL_SAVE_FLAG);//保存
        //store
        cv.restore();//存储
        return newbmp;
    }


    /**
     * @param view
     * @return
     * VIEW ZHAUN转bitmap
     */
    public static Bitmap createBitmap(View view){
        view.setDrawingCacheEnabled(true);
        /**
         * 这里要注意，在用View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
         * 来测量view 的时候,（如果你的布局中包含有 RelativeLayout ）API 为17 或者 低于17 会包空指针异常
         * 解决方法:
         * 1 布局中不要包含RelativeLayout
         * 2 用 View.MeasureSpec.makeMeasureSpec(256, View.MeasureSpec.EXACTLY) 好像也可以
         *
         */
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }



}
