package com.page.classify.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.framework.rvadapter.holder.BaseViewHolder;

/**
 * Created by shucheng.qu on 2017/8/15.
 */

public class NavHolder extends BaseViewHolder {

    public TextView itemView;

    public NavHolder(Context context, View itemView) {
        super(context, itemView);
        this.itemView = (TextView) itemView;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, Object data, int position) {
//        itemView.setSelected(true);
    }
}
