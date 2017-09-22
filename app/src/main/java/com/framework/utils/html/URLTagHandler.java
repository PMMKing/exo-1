package com.framework.utils.html;

import java.io.File;
import java.util.Locale;

import org.xml.sax.XMLReader;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.text.Editable;
import android.text.Html.TagHandler;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.ProgressBar;

import com.framework.utils.QLog;
import com.framework.utils.cache.ImageLoader;
import com.framework.utils.imageload.IFacadeBitmapCallback;
import com.framework.utils.imageload.ImageLoad;
import com.framework.utils.imageload.ImageTransform;
import com.qfant.wuye.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class URLTagHandler implements TagHandler {

    private Context mContext;
    private PopupWindow popupWindow;
    //需要放大的图片
    private ZoomImageView tecent_chat_image;
    //加载中的进度条
    private ProgressBar image_scale_progress;

    public URLTagHandler(Context context) {
        mContext = context.getApplicationContext();
        View popView = LayoutInflater.from(context).inflate(R.layout.pub_zoom_popwindow_layout, null);
        tecent_chat_image = (ZoomImageView) popView.findViewById(R.id.image_scale_image);
        image_scale_progress = (ProgressBar) popView.findViewById(R.id.image_scale_progress);

        popView.findViewById(R.id.image_scale_rll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }
        });
        popupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);// 设置允许在外点击消失
        ColorDrawable dw = new ColorDrawable(0x50000000);
        popupWindow.setBackgroundDrawable(dw);
    }

    @Override
    public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
        // 处理标签<img>
        if (tag.toLowerCase(Locale.getDefault()).equals("img")) {
            // 获取长度
            int len = output.length();
            // 获取图片地址
            ImageSpan[] images = output.getSpans(len - 1, len, ImageSpan.class);
            String imgURL = images[0].getSource();
            // 使图片可点击并监听点击事件
            output.setSpan(new ClickableImage(mContext, imgURL), len - 1, len, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    private class ClickableImage extends ClickableSpan {
        private String url;
        private Context context;

        public ClickableImage(Context context, String url) {
            this.context = context;
            this.url = url;
        }

        @Override
        public void onClick(View widget) {
            // 进行图片点击之后的处理
            image_scale_progress.setVisibility(View.VISIBLE);
            popupWindow.showAtLocation(widget, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            final Target target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    tecent_chat_image.setImageBitmap(bitmap);
                    image_scale_progress.setVisibility(View.GONE);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {
                    tecent_chat_image.setImageDrawable(errorDrawable);
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                    tecent_chat_image.setImageDrawable(placeHolderDrawable);
                }
            };
            tecent_chat_image.setTag(target);
            ImageLoad.loadPlaceholder(context, url, target);
        }
    }


}
