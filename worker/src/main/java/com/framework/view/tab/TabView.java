package com.framework.view.tab;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.framework.view.AlwaysFocusedTextView;
import com.framework.view.IFView;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yx on 16/4/3.
 */

public class TabView extends LinearLayout implements View.OnClickListener {


    @BindView(R.id.icon)
    IFView icon;
    @BindView(R.id.text)
    TextView text;
    private TabItem tabItem;

    public TabView(Context context) {
        super(context);
        initView(context);
    }

    public TabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public TabView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    private void initView(Context context) {
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        setBackgroundResource(R.drawable.pub_tabview_bg_selector);
        LinearLayout.inflate(context, R.layout.pub_tabview_layout, this);
        ButterKnife.bind(this);
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        if (!selected) {
            icon.setText(getResources().getString(tabItem.icon[0]));
        } else {
            icon.setText(tabItem.icon.length >= 2 ? getResources().getString(tabItem.icon[1]) : getResources().getString(tabItem.icon[0]));
        }
    }

    public void initData(TabItem tabItem) {
        this.tabItem = tabItem;
        text.setText(tabItem.text);
        icon.setText(getResources().getString(tabItem.icon[0]));
    }

    @Override
    public void onClick(View v) {

    }

    public void onDataChanged(int badgeCount) {
        //  TODO notify new message, change the badgeView
    }
}
