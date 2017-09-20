package com.framework.utils.imageload;

import android.graphics.Bitmap;

import com.framework.app.MainApplication;
import com.squareup.picasso.Transformation;

public class ImageTransform implements Transformation {

    private String Key = "ImageTransform";

    @Override
    public Bitmap transform(Bitmap source) {
        int targetWidth = MainApplication.applicationContext.getResources().getDisplayMetrics().widthPixels;
        if (source.getWidth() == 0) {
            return source;
        }
        //如果图片小于设置的宽度，做处理
        if (source.getWidth() < targetWidth) {
            double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
            int targetHeight = (int) (targetWidth * aspectRatio);

            if (targetHeight != 0 && targetWidth != 0) {
                Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
                if (result != source) {
                    source.recycle();
                }
                return result;
            } else {
                return source;
            }
        } else {
            return source;
        }
    }

    @Override
    public String key() {
        return Key;
    }
}
