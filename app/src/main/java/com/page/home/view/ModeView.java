package com.page.home.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayout;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qfant.wuye.R;
import com.page.home.model.HomeModel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shucheng.qu on 2017/8/13.
 */

public class ModeView extends LinearLayout {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_image)
    ImageView ivImage;

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
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(
                GridLayout.spec(GridLayout.UNDEFINED, 1f),
                GridLayout.spec(GridLayout.UNDEFINED, 1f));
        layoutParams.setMargins(8, 8, 8, 8);
        setLayoutParams(layoutParams);
    }

    public void setData(HomeModel homeModel) {
        tvTitle.setText(homeModel.title);
    }
}
