package com.page.store.home.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.framework.rvadapter.holder.BaseViewHolder;
import com.page.store.home.model.ClassifyResult.Data.Datas;

/**
 * Created by shucheng.qu on 2017/8/15.
 */

public class NavHolder extends BaseViewHolder<Datas> {

    public TextView itemView;

    public NavHolder(Context context, View itemView) {
        super(context, itemView);
        this.itemView = (TextView) itemView;
//        R.layout.pub_activity_classify_left_item_layout;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, Datas data, int position) {
        if (data == null) return;
        itemView.setSelected(data.isSelect);
        itemView.setText(data.name);
    }
}
