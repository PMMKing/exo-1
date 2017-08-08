package com.framework.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haolb.client.R;
import com.haolb.client.app.MainApplication;
import com.haolb.client.utils.Dimen;


/**
 */
public class TitleBarItem extends LinearLayout {
    private static final int DEFAULT_TEXT_SIZE = 20;
//    private UELog logger;

    public TitleBarItem(Context context) {
        super(context);
//        this.logger = new UELog(context);
    }

    public TitleBarItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setImageTypeItem(int resId, int marginLeft, int marginRight) {
        this.removeAllViews();
        this.setGravity(17);
        ImageView imageView = new ImageView(this.getContext());
        imageView.setLayoutParams(new LayoutParams(-2, -2));
        imageView.setBackgroundResource(resId);
        this.addView(imageView);
        LayoutParams layoutParams = new LayoutParams(-2, -1);
        layoutParams.setMargins(Dimen.dpToPx(marginLeft), 0, Dimen.dpToPx(marginRight), 0);
        this.setLayoutParams(layoutParams);
        this.setClickable(true);
    }

    public void setIconFontTypeItem(int resId) {
        this.removeAllViews();
        this.setIconFontTypeItem(this.getContext().getString(resId));
    }

    public void setIconFontTypeItem(String res) {
        this.removeAllViews();
        this.setGravity(17);
        TextView textView = new TextView(this.getContext());
        textView.setTypeface(MainApplication.getIconFont());
        textView.setTextColor(this.getResources().getColorStateList(R.color.pub_color_blue));
        textView.setTextSize(0, Dimen.dpToPx(22.0F));
        textView.setText(res);
        textView.setPadding(Dimen.dpToPx(8), 0, Dimen.dpToPx(8), 0);
        this.addView(textView, new LayoutParams(-2, -2));
        this.setLayoutParams(new LayoutParams(-2, -2));
    }
}
