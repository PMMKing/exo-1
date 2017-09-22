package com.framework.utils.imageload;

import android.graphics.Bitmap;

import com.framework.app.MainApplication;
import com.framework.utils.Dimen;
import com.squareup.picasso.Transformation;

public class ImageTransform implements Transformation {

    private String Key = "ImageTransform";

    @Override
    public Bitmap transform(Bitmap source) {
        int targetWidth = MainApplication.applicationContext.getResources().getDisplayMetrics().widthPixels - Dimen.dpToPx(28);
        if (source.getWidth() == 0) {
            return source;
        }

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
    }

    @Override
    public String key() {
        return Key;
    }
}
