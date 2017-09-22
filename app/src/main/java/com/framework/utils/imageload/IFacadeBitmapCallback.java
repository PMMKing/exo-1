package com.framework.utils.imageload;

import android.graphics.Bitmap;

/**
 * Created by shucheng.qu on 2017/9/21.
 */

public interface IFacadeBitmapCallback {
    void onBitmapFailed(String url);
    void onBitmapLoaded(String url, Bitmap bitmap);
    void onBitmapStart(String url);
}
