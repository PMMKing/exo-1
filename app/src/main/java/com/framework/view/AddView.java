package com.framework.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;

import com.haolb.client.R;
import com.haolb.client.utils.Dimen;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shucheng.qu on 2017/8/11.
 */

public class AddView extends LinearLayout {

    @BindView(R.id.ll_add)
    LinearLayout llAdd;

    public AddView(Context context) {
        this(context, null);
    }

    public AddView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AddView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LinearLayout.inflate(getContext(), R.layout.pub_activity_addview_layout, this);
        ButterKnife.bind(this);
        setBackgroundColor(Color.WHITE);
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        int widthPixels = displayMetrics.widthPixels;
        int width = (widthPixels - Dimen.dpToPx(70)) / 4;
        int height = width + Dimen.dpToPx(40);
        LinearLayout.LayoutParams layoutParams = (LayoutParams) llAdd.getLayoutParams();
        layoutParams.width = LayoutParams.MATCH_PARENT;
        layoutParams.height = height;
        llAdd.setLayoutParams(layoutParams);
    }
}
