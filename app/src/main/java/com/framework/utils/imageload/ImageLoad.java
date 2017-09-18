package com.framework.utils.imageload;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.framework.utils.Dimen;
import com.qfant.wuye.R;
import com.squareup.picasso.Picasso;

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

    public static void loadPlaceholder(Context context, String url, ImageView imageView, int placeholderResId, int errorResId) {
        Picasso.with(context)
                .load(url)
                .placeholder(placeholderResId)
                .error(errorResId)
                .transform(new CompressTransformation())
//                .resize(360, 480)
                .into(imageView);
    }

    public static void loadPlaceholderSize(Context context, String url, ImageView imageView) {
        loadPlaceholderSize(context, url, imageView, R.drawable.moren, R.drawable.moren);
    }

    public static void loadPlaceholderSize(Context context, String url, ImageView imageView, int placeholderResId, int errorResId) {
        Picasso.with(context)
                .load(url)
                .placeholder(placeholderResId)
                .error(errorResId)

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
