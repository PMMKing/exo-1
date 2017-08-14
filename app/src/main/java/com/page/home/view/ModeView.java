package com.page.home.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayout;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.haolb.client.R;

import butterknife.ButterKnife;

/**
 * Created by shucheng.qu on 2017/8/13.
 */

public class ModeView extends LinearLayout {
    public ModeView(Context context) {
        this(context, null);
    }

    public ModeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ModeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        LinearLayout.inflate(getContext(), R.layout.pub_fragment_home_mode_item_layout, this);
        ButterKnife.bind(this);


        //指定平分的布局样式
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(
                GridLayout.spec(GridLayout.UNDEFINED, 1f),
                GridLayout.spec(GridLayout.UNDEFINED, 1f));
//        layoutParams.height = widthPixels / 3;
//        layoutParams.width = 0;
        //设置一些Margin
        layoutParams.setMargins(8, 8, 8, 8);
        //将布局样式应用到ImageView
        setLayoutParams(layoutParams);
    }
}
