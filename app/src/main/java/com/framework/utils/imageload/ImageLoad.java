package com.framework.utils.imageload;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.framework.utils.Dimen;
import com.qfant.wuye.R;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.concurrent.TimeUnit;

/**
 * Created by shucheng.qu on 2017/8/24.
 */

public class ImageLoad {

    public static void load(Context context, String url, ImageView imageView) {
        Picasso.with(context).load(url).into(imageView);
    }

    public static void loadPlaceholder(Context context, String url, ImageView imageView) {
        loadPlaceholder(context, url, imageView, R.drawable.moren, R.drawable.moren);
    }

    public static void loadPlaceholder(Context context, String url, Target target) {

        /**
         * 设置超时时间
         */
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(30, TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(30, TimeUnit.SECONDS);
        okHttpClient.setWriteTimeout(30, TimeUnit.SECONDS);

        Picasso picasso = new Picasso.Builder(context)
                .downloader(new OkHttpDownloader(okHttpClient))
                .build();


        picasso.load(url)
//                .placeholder(R.drawable.moren)
//                .error(R.drawable.moren)
//                .transform(new CompressTransformation())
//                .transform(new ImageTransform())
                .into(target);
    }

    public static void loadPlaceholder(Context context, String url, ImageView imageView, int placeholderResId, int errorResId) {
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
        Picasso.with(context)
                .load(url)
                .transform(new RoundedTransformation(Dimen.dpToPx(radius), Dimen.dpToPx(margin)))
                .into(imageView);
    }

    public static void loadCircle(Context context, String url, ImageView imageView) {
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
