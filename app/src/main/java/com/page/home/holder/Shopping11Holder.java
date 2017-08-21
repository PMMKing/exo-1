package com.page.home.holder;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.utils.Dimen;

/**
 * Created by shucheng.qu on 2017/8/18.
 */

public class Shopping11Holder extends BaseViewHolder {

    private final int widthPixels;

    public Shopping11Holder(Context context, View itemView) {
        super(context, itemView);
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        widthPixels = displayMetrics.widthPixels;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, Object data, int position) {

        ViewGroup.LayoutParams layoutParams = getConvertView().getLayoutParams();
        layoutParams.width = widthPixels - Dimen.dpToPx(30);
        layoutParams.height = Dimen.dpToPx(96);
        getConvertView().setLayoutParams(layoutParams);
    }
}
