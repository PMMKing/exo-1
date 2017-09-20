package com.framework.utils.html;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.text.Html.ImageGetter;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.ImageView;
import android.widget.TextView;

import com.framework.utils.QLog;
import com.framework.utils.imageload.ImageLoad;
import com.qfant.wuye.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class URLImageParser implements ImageGetter {
    Context c;
    TextView tv_image;
    private Drawable mDefaultDrawable;

    public URLImageParser(TextView t, Context c) {
        this.tv_image = t;
        this.c = c;
        mDefaultDrawable = c.getResources().getDrawable(R.drawable.moren);
    }

    @Override
    public Drawable getDrawable(final String source) {
        final URLDrawable urlDrawable = new URLDrawable();
        urlDrawable.setDrawable(mDefaultDrawable);
        final Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                QLog.d("qushucheng", "onBitmapLoaded");
                Drawable drawable = new BitmapDrawable(bitmap);
                drawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
                urlDrawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
                urlDrawable.setDrawable(drawable);
                tv_image.invalidate();
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
//                urlDrawable.setDrawable(c.getResources().getDrawable(R.drawable.moren));
//                tv_image.invalidate();
                QLog.d("qushucheng", "onBitmapFailed");
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
//                urlDrawable.setDrawable(c.getResources().getDrawable(R.drawable.moren));
//                tv_image.invalidate();
                QLog.d("qushucheng", "onPrepareLoad" + "    :     " + source);
            }
        };
        ImageLoad.loadPlaceholder(c, source, target);
//        Picasso.with(c).load(source).into(target);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                ImageLoad.loadPlaceholder(c, source, target);
////                Picasso.with(c).load(source).into(target);
//            }
//        }, 6000);
        return urlDrawable;
    }

}
