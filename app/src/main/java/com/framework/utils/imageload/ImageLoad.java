package com.framework.utils.imageload;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.ImageView;

import com.framework.utils.Dimen;
import com.qfant.wuye.R;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by shucheng.qu on 2017/8/24.
 */

public class ImageLoad {

    public static void load(Context context, String url, ImageView imageView) {
        if (TextUtils.isEmpty(url) || imageView == null) return;
        Picasso.with(context).load(url).into(imageView);
    }

    public static void loadPlaceholder(Context context, String url, ImageView imageView) {
        loadPlaceholder(context, url, imageView, R.drawable.moren, R.drawable.moren);
    }

    public static void loadPlaceholder(Context context, String url, Target target) {
        if (TextUtils.isEmpty(url) || target == null) return;
        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.moren)
                .error(R.drawable.moren)
                .transform(new ImageTransform())
                .into(target);
    }

    public static void loadPlaceholder(Context context, String url, ImageView imageView, int placeholderResId, int errorResId) {
        if (TextUtils.isEmpty(url) || imageView == null) return;
        Picasso.with(context)
                .load(url)
                .placeholder(placeholderResId)
                .error(errorResId)
                .transform(new CompressTransformation())
                .into(imageView);
    }

    public static void loadRound(Context context, String url, ImageView imageView) {
        loadRound(context, url, imageView, 3, 0);
    }

    public static void loadRound(Context context, String url, ImageView imageView, int radius, int margin) {
        if (TextUtils.isEmpty(url) || imageView == null) return;
        Picasso.with(context)
                .load(url)
                .transform(new RoundedTransformation(Dimen.dpToPx(radius), Dimen.dpToPx(margin)))
                .into(imageView);
    }

    public static void loadCircle(Context context, String url, ImageView imageView) {
        if (TextUtils.isEmpty(url) || imageView == null) return;
        Picasso.with(context)
                .load(url)
                .transform(new CircleImageTransformation())
                .into(imageView);
    }

    public static void loadCircle(Context context, int resourceId, ImageView imageView) {
        Picasso.with(context)
                .load(resourceId)
                .transform(new CircleImageTransformation())
                .into(imageView);
    }

}
