package com.framework.utils.imageload;

import android.content.Context;
import android.widget.ImageView;

import com.framework.utils.Dimen;
import com.haolb.client.R;
import com.squareup.picasso.Picasso;

/**
 * Created by shucheng.qu on 2017/8/24.
 */

public class ImageLoad {

    public static void load(Context context, String url, ImageView imageView) {
        Picasso.with(context).load(url).into(imageView);
    }

    public static void loadPlaceholder(Context context, String url, ImageView imageView) {
        loadPlaceholder(context, url, imageView, R.drawable.default_head, R.drawable.default_head);
    }

    public static void loadPlaceholder(Context context, String url, ImageView imageView, int placeholderResId, int errorResId) {
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

}
