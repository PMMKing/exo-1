package com.wuye.wuye.framework.view;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.wuye.wuye.R;


/**
 * 标题栏
 */
public class TitleBarNew extends LinearLayout {
    private OnClickListener mBackListener;
    private boolean hasBackBtn;
    private TitleBarCenterItem barCenterItem;
    private TitleBarItem[] rightBarItems;
    private TitleBarItem[] leftBarItems;
    private int style;
    public IFView iconBack;
    private LinearLayout llCenterFunctionArea;
    private LinearLayout llRightFunctionArea;
    private LinearLayout llLeftArea;

    public TitleBarNew(Context context) {
        this(context, (AttributeSet) null, 0);
    }

    public TitleBarNew(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBarNew(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.style = 3;
        this.initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.pub_titlebar_layout, this, true);
        this.iconBack = (IFView) this.findViewById(R.id.pub_pat_id_icon_back);
        this.llLeftArea = (LinearLayout) this.findViewById(R.id.titlebar_ll_left);
        this.llCenterFunctionArea = (LinearLayout) this.findViewById(R.id.titlebar_ll_center);
        this.llRightFunctionArea = (LinearLayout) this.findViewById(R.id.titlebar_ll_right);
    }

    public void setTitleBar(String backText, boolean hasBackBtn, TitleBarCenterItem barCenterItem, TitleBarItem... barItems) {
        this.setTitleBar(backText, hasBackBtn, (TitleBarItem[]) null, barCenterItem, barItems);
    }

    public void setTitleBar(boolean hasBackBtn, TitleBarCenterItem barCenterItem, TitleBarItem... barItems) {
        this.setTitleBar("", hasBackBtn, (TitleBarItem[]) null, barCenterItem, barItems);
    }

    public void setTitleBar(String backText, boolean hasBackBtn, TitleBarItem[] leftBarItems, TitleBarCenterItem barCenterItem, TitleBarItem... rightBarItems) {
        this.hasBackBtn = hasBackBtn;
        if (hasBackBtn) {
            if (TextUtils.isEmpty(backText)) {
                iconBack.setText(R.string.icon_font_back);
            } else {
                iconBack.setText(backText);
            }
        }
        this.barCenterItem = barCenterItem;
        this.leftBarItems = leftBarItems;
        this.rightBarItems = rightBarItems;
        this.reLayout();
    }

    public void setBackButtonClickListener(OnClickListener listener) {
        this.mBackListener = listener;
        if (this.mBackListener != null) {
            this.llLeftArea.setOnClickListener(this.mBackListener);
        } else {
            this.llLeftArea.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    try {
                        ((Activity) v.getContext()).onBackPressed();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void setTitleBarStyle(int style) {
        this.style = style;
        this.reLayout();
    }


    public void reLayout() {
//        int background;
//        if (this.style == 1) {
//            background = this.getResources().getColor(R.color.pub_color_blue);
//        } else if (this.style == 2) {
//            background = this.getResources().getColor(R.color.pub_color_gray_999);
//        } else if (this.style == 3) {
//            background = this.getResources().getColor(R.color.transparent);
//        } else {
//            background = this.getResources().getColor(R.color.pub_color_white);
//        }
//
//        this.setBackgroundColor(background);
        if (this.hasBackBtn) {
            this.llLeftArea.setVisibility(VISIBLE);
            this.setBackButtonClickListener(this.mBackListener);
        } else {
            this.llLeftArea.setVisibility(GONE);
        }


        this.llRightFunctionArea.removeAllViews();
        if (this.rightBarItems != null && this.rightBarItems.length > 0) {
            int barItemsLength = this.rightBarItems.length;
            for (int i = 0; i < barItemsLength; ++i) {
                this.llRightFunctionArea.addView(this.rightBarItems[i], i);
            }
            this.llRightFunctionArea.setVisibility(VISIBLE);
        } else {
            this.llRightFunctionArea.setVisibility(INVISIBLE);
        }

        this.llCenterFunctionArea.removeAllViews();
        if (this.barCenterItem != null) {
            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.gravity = Gravity.CENTER;
            this.llCenterFunctionArea.addView(barCenterItem, layoutParams);
            this.llCenterFunctionArea.setVisibility(VISIBLE);
        } else {
            this.llCenterFunctionArea.setVisibility(INVISIBLE);
        }


    }

    public void setTitle(String title) {
        if (this.barCenterItem != null && this.barCenterItem.getMode() == 0) {
            this.barCenterItem.setContent(title);
            this.barCenterItem.requestRelayout();
        }
    }

    public TitleBarCenterItem getCenterBar() {
        return barCenterItem;
    }

    public void setCenterOnClickListener(OnClickListener onClickListener) {

        if (llCenterFunctionArea != null && onClickListener != null) {
            llCenterFunctionArea.setOnClickListener(onClickListener);
        }
    }

    public void setCenterOnLongClickListener(OnLongClickListener onLongClickListener) {

        if (llCenterFunctionArea != null && onLongClickListener != null) {
            llCenterFunctionArea.setOnLongClickListener(onLongClickListener);
        }
    }

    public void setRightOnClickListener(OnClickListener onClickListener) {

        if (llRightFunctionArea != null && onClickListener != null) {
            llRightFunctionArea.setOnClickListener(onClickListener);
        }
    }
}
