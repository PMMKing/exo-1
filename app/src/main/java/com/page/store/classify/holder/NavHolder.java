package com.page.store.classify.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.framework.rvadapter.holder.BaseViewHolder;
import com.page.store.classify.model.ClassifyResult;
import com.page.store.classify.model.ClassifyResult.Data.Datas;
import com.qfant.wuye.R;

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
