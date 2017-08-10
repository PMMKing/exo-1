package com.framework.view;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haolb.client.R;
import com.haolb.client.app.MainApplication;


/**
 */
public class TitleBarCenterItem extends LinearLayout {
    private float titleMaxTextSize;
    private float titleMinTextSize;
    private int textColor;
    private String content;
    private View customView;
    public static final int MODE_TEXT = 0;
    public static final int MODE_CUSTOM_VIEW = 1;
    private int mode;
    private int innerGravity;

    public TitleBarCenterItem(Context context) {
        this(context, 0);
    }

    public TitleBarCenterItem(Context context, int mode) {
        this(context, mode, (AttributeSet) null);
    }

    public TitleBarCenterItem(Context context, int mode, AttributeSet attrs) {
        super(context, attrs);
        this.titleMaxTextSize = -1.0F;
        this.titleMinTextSize = -1.0F;
        this.textColor = Color.WHITE;
        this.mode = 0;
        this.innerGravity = 0;
        this.mode = mode;
    }

    public void setInnerGravity(int innerGravity) {
        this.innerGravity = innerGravity;
    }

    public void setTitleMaxTextSize(float titleMaxTextSize) {
        this.titleMaxTextSize = titleMaxTextSize;
    }

    public void setTitleMinTextSize(float titleMinTextSize) {
        this.titleMinTextSize = titleMinTextSize;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
        requestRelayout();
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getMode() {
        return this.mode;
    }

    public void requestRelayout() {
        this.requestRelayout(-2, -2);
    }

    public synchronized void requestRelayout(int widthLayoutParam, int heightLayoutParam) {
        this.removeAllViews();
        this.setGravity(17);
        if (this.mode == 1) {
            if (this.customView != null) {
                try {
                    this.removeView(this.customView);
                } catch (Exception var5) {
                }

                this.addView(this.customView, new LayoutParams(widthLayoutParam, heightLayoutParam));
                this.setClickable(true);
            }
        } else {
            View inflateView = inflate(this.getContext(), R.layout.pub_titlebar_center_content_layout, (ViewGroup) null);
            if (inflateView != null && inflateView instanceof TextView) {
                TextView autoScaleTextView = (TextView) inflateView;
                autoScaleTextView.setTypeface(MainApplication.getIconFont());
                if (this.titleMaxTextSize != -1.0F) {
                    autoScaleTextView.setTextSize(this.titleMaxTextSize);
                }

                if (this.titleMinTextSize != -1.0F) {
                    autoScaleTextView.setTextSize(this.titleMinTextSize);
                }

                if (TextUtils.isEmpty(this.content)) {
                    return;
                }

                if (this.textColor != 0) {
                    autoScaleTextView.setTextColor(this.textColor);
                } else {
                    autoScaleTextView.setTextColor(getResources().getColorStateList(R.color.pub_titlebar_center_color_selector));
                }

                if (this.innerGravity != 0) {
                    autoScaleTextView.setGravity(this.innerGravity);
                }

                autoScaleTextView.setText(this.content);
                this.addView(autoScaleTextView, new LayoutParams(-1, -1));
            }
        }

    }

    public void setCustomView(View customView) {
        this.customView = customView;
    }


}

