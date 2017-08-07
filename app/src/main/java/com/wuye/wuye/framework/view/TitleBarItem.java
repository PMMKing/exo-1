package com.wuye.wuye.framework.view;

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

import com.wuye.wuye.R;
import com.wuye.wuye.WApplication;
import com.wuye.wuye.utils.graphics.Dimen;


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

    public void setTextTypeItem(int resId) {
        this.removeAllViews();
        this.setTextTypeItem(this.getContext().getString(resId));
    }

    public void setIconFontTypeItem(int resId) {
        this.removeAllViews();
        this.setIconFontTypeItem(this.getContext().getString(resId));
    }

    public void setIconFontTypeItem(String res) {
        this.removeAllViews();
        this.setGravity(17);
        TextView textView = new TextView(this.getContext());
        textView.setTypeface(WApplication.getIconFont());
        textView.setTextColor(this.getResources().getColorStateList(R.color.pub_color_blue));
        textView.setTextSize(0, Dimen.dpToPx(22.0F));
        textView.setText(res);
        textView.setPadding(Dimen.dpToPx(8), 0, Dimen.dpToPx(8), 0);
        this.addView(textView, new LayoutParams(-2, -2));
        this.setLayoutParams(new LayoutParams(-2, -2));
    }

    public void setTextTypeItem(String res) {
        this.removeAllViews();
        this.setGravity(17);
        TextView textView = new TextView(this.getContext());
        textView.setTypeface(WApplication.getIconFont());
        textView.setTextColor(this.getResources().getColorStateList(R.color.pub_color_blue));
        textView.setTextSize(0, Dimen.dpToPx(15));
        textView.setText(res);
        textView.setPadding(Dimen.dpToPx(8), 0, Dimen.dpToPx(0), 0);
        this.addView(textView, new LayoutParams(-2, -2));
        this.setLayoutParams(new LayoutParams(-2, -2));
    }

    public void setTextTypeItem(String res, float size) {
        setTextTypeItem(res, size, this.getResources().getColor(R.color.pub_color_white));
    }

    public void setTextTypeItem(String res, float size, int color) {
        this.removeAllViews();
        this.setGravity(Gravity.RIGHT);
        TextView textView = new TextView(this.getContext());
        textView.setTypeface(WApplication.getIconFont());
        textView.setTextColor(color);
        textView.setTextSize(0, Dimen.dpToPx(size));
        textView.setText(res);
        textView.setBackgroundResource(R.color.transparent);
        textView.setPadding(0, 0, Dimen.dpToPx(15), 0);
        textView.setLayoutParams(new LayoutParams(-2, -1));
        this.addView(textView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        this.setLayoutParams(new LayoutParams(-2, -2));
        this.setClickable(false);
    }

    public void setTextTypeItem(String res, int size, int right) {
        this.removeAllViews();
        this.setGravity(Gravity.RIGHT);
        TextView textView = new TextView(this.getContext());
        textView.setTypeface(WApplication.getIconFont());
        textView.setTextColor(this.getResources().getColorStateList(R.color.pub_color_gray_666));
        textView.setTextSize(0, Dimen.dpToPx(size));
        textView.setText(res);
        textView.setBackgroundResource(R.color.transparent);
        textView.setLayoutParams(new LayoutParams(-2, -1));
        textView.setPadding(0, 0, Dimen.dpToPx(right), 0);
        this.addView(textView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        this.setLayoutParams(new LayoutParams(-2, -2));
        this.setClickable(false);
    }

    /**
     * @param res
     * @return 天气
     */
    public View setTextTypeItem(SpannableString res) {
        this.removeAllViews();
        this.setGravity(Gravity.RIGHT);
        TextView textView = new TextView(this.getContext());
        textView.setTypeface(WApplication.getIconFont());
        textView.setTextColor(this.getResources().getColorStateList(R.color.pub_color_blue));
        textView.setText(res);
        textView.setLineSpacing(0, 1.1f);
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundResource(R.color.transparent);
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        textView.setLayoutParams(new LayoutParams(-2, -1));
        this.addView(textView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        this.setLayoutParams(new LayoutParams(-2, -2));
        this.setClickable(false);
        return textView;
    }


    public void setTextTypeItem(String res, int maxLength) {
        this.removeAllViews();
        this.setGravity(17);
        TextView textView = new TextView(this.getContext());
        textView.setTypeface(WApplication.getIconFont());
        textView.setTextColor(this.getResources().getColorStateList(R.color.pub_color_blue));
        textView.setTextSize(0, Dimen.dpToPx((18.0F)));
        textView.setText(res.length() > maxLength ? res.substring(0, 4) : res);
        textView.setSingleLine(true);
        int padding = Dimen.dpToPx(5);
        textView.setPadding(padding, 0, padding, 0);
        this.addView(textView, new LayoutParams(-2, -2));
        if (res.length() < 3) {
            this.setLayoutParams(new LayoutParams(Dimen.dpToPx(50), -1));
        } else {
            this.setLayoutParams(new LayoutParams(-2, -1));
        }

        this.setClickable(true);
    }

    public void setImageTypeItem(int resId) {
        this.setImageTypeItem(resId, 50);
    }

    public void setImageTypeItem(int resId, int width) {
        this.removeAllViews();
        this.setGravity(17);
        ImageView imageView = new ImageView(this.getContext());
        imageView.setBackgroundResource(resId);
        this.addView(imageView, new LayoutParams(-2, -2));
        if (width <= 0) {
            this.setLayoutParams(new LayoutParams(-2, -1));
        } else {
            this.setLayoutParams(new LayoutParams(Dimen.dpToPx(width), -1));
        }

        this.setClickable(true);
    }

    public void setTextImageItem(int textId, int iconId) {
        this.setTextImageItem(this.getResources().getString(textId), iconId);
    }

    public void setTextImageItem(String res, int iconId) {
        this.setTextImageItem(res, iconId, 50);
    }

    public void setTextImageItem(String res, int iconId, int width) {
        this.removeAllViews();
        this.setGravity(17);
        TextView textView = new TextView(this.getContext());
        textView.setTypeface(WApplication.getIconFont());
        textView.setTextColor(this.getResources().getColorStateList(R.color.pub_color_blue));
        textView.setTextSize(0, Dimen.dpToPx(34.0F));
        textView.setText(res);
        textView.setPadding(Dimen.dpToPx(12), Dimen.dpToPx(8), Dimen.dpToPx(9), 0);
        textView.setCompoundDrawablesWithIntrinsicBounds(0, iconId, 0, 0);
        this.addView(textView, new LayoutParams(-2, -2));
        if (width <= 0) {
            this.setLayoutParams(new LayoutParams(-2, -1));
        } else {
            this.setLayoutParams(new LayoutParams(Dimen.dpToPx(width), -1));
        }

    }

    public void setCustomViewTypeItem(View view) {
        this.removeAllViews();
        this.setGravity(17);
        this.addView(view, new LayoutParams(-2, -2));
        this.setLayoutParams(new LayoutParams(-2, -1));
    }

    public void setEnabled(boolean enabled) {
        for (int i = 0; i < this.getChildCount(); ++i) {
            View child = this.getChildAt(i);
            if (child instanceof ImageView) {
                ImageView iv = (ImageView) child;
                Drawable drawable = iv.getBackground();
                drawable.setAlpha(enabled ? 0 : 128);
                iv.setBackgroundDrawable(drawable);
            } else if (child instanceof TextView) {
                ((TextView) child).setEnabled(enabled);
            }
        }

        super.setEnabled(enabled);
    }

    public void setEnabledByFixed(boolean enabled) {
        for (int i = 0; i < this.getChildCount(); ++i) {
            View child = this.getChildAt(i);
            if (child instanceof ImageView) {
                ImageView iv = (ImageView) child;
                Drawable drawable = iv.getBackground();
                drawable.setAlpha(enabled ? 255 : 128);
                iv.setBackgroundDrawable(drawable);
            } else if (child instanceof TextView) {
                ((TextView) child).setEnabled(enabled);
            }
        }

        super.setEnabled(enabled);
    }

    public void setTextTypeItem(int resId, ColorStateList list) {
        this.removeAllViews();
        this.setGravity(17);
        TextView textView = new TextView(this.getContext());
        textView.setTypeface(WApplication.getIconFont());
        textView.setTextColor(list);
        textView.setTextSize(0, Dimen.dpToPx(24.0F));
        textView.setText(this.getContext().getString(resId));
        int paddingValue = Dimen.dpToPx(10);
        textView.setPadding(paddingValue, paddingValue, paddingValue, paddingValue);
        this.addView(textView, new LayoutParams(-2, -2));
        this.setLayoutParams(new LayoutParams(-2, -2));
    }
}

