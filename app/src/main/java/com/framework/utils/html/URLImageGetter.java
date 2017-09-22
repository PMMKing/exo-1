package com.framework.utils.html;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html.ImageGetter;
import android.widget.TextView;

import com.framework.utils.imageload.ImageLoad;
import com.qfant.wuye.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class URLImageGetter implements ImageGetter {
    Context c;
    TextView tv_image;
    private List<Target> targets = new ArrayList<>();

    public URLImageGetter(TextView t, Context c) {
        this.tv_image = t;
        this.c = c;
        tv_image.setTag(targets);
    }

    @Override
    public Drawable getDrawable(final String source) {
        final URLDrawable urlDrawable = new URLDrawable();
        final Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Drawable drawable = new BitmapDrawable(bitmap);
                drawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
                urlDrawable.setDrawable(drawable);
                urlDrawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
                tv_image.invalidate();
                tv_image.setText(tv_image.getText());
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                Drawable drawable = c.getResources().getDrawable(R.drawable.moren);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                urlDrawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                urlDrawable.setDrawable(drawable);
                tv_image.invalidate();
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                Drawable drawable = c.getResources().getDrawable(R.drawable.moren);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                urlDrawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                urlDrawable.setDrawable(drawable);
                tv_image.invalidate();
            }
        };

        targets.add(target);
        ImageLoad.loadPlaceholder(c,source,target);

//        cachedThreadPool.submit(new Runnable() {
//            @Override
//            public void run() {
//
//                try {
//                    Bitmap bitmap = Picasso.with(c).load(source).get();
//                    QLog.d("qushucheng", "onBitmapLoaded");
//                    Drawable drawable = new BitmapDrawable(bitmap);
//                    drawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
//                    urlDrawable.setDrawable(drawable);
//                    urlDrawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
//                    tv_image.invalidate();
//                    tv_image.setText(tv_image.getText());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
////                ImageLoad.get(c, source, new IFacadeBitmapCallback() {
////                    @Override
////                    public void onBitmapFailed(String url) {
////                        Drawable drawable = c.getResources().getDrawable(R.drawable.moren);
////                        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
////                        urlDrawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
////                        urlDrawable.setDrawable(drawable);
////                        tv_image.invalidate();
////                    }
////
////                    @Override
////                    public void onBitmapLoaded(String url, Bitmap bitmap) {
////
////                        QLog.d("qushucheng", "onBitmapLoaded");
////                        Drawable drawable = new BitmapDrawable(bitmap);
////                        drawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
////                        urlDrawable.setDrawable(drawable);
////                        urlDrawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
////                        tv_image.invalidate();
////                        tv_image.setText(tv_image.getText());
//////
//////
//////
//////                        BitmapDrawable d = new BitmapDrawable(c.getResources(), bitmap);
//////                        d.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
//////                        urlDrawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
//////                        urlDrawable.setDrawable(d);
//////                        tv_image.invalidate();
//////                        tv_image.setText(tv_image.getText()); // 解决图文
////                    }
////
////                    @Override
////                    public void onBitmapStart(String url) {
////                        Drawable drawable = c.getResources().getDrawable(R.drawable.moren);
////                        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
////                        urlDrawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
////                        urlDrawable.setDrawable(drawable);
////                        tv_image.invalidate();
////                    }
////                });
//            }
//        });
        return urlDrawable;
    }
}

