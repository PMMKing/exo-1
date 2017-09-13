package com.page.store.collect.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.utils.BusinessUtils;
import com.framework.utils.imageload.ImageLoad;
import com.page.store.collect.model.CollectResult;
import com.page.store.collect.model.CollectResult.Data;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shucheng.qu on 2017/8/18.
 */

public class CollectHolder extends BaseViewHolder<Data> {

    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_price)
    TextView tvPrice;

    public CollectHolder(Context context, View itemView) {
        super(context, itemView);
//        R.layout.pub_activity_collect_item_layout;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, Data data, int position) {
        ImageLoad.loadPlaceholder(mContext, data.pic1, ivImage);
        tvName.setText(data.name);
        tvPrice.setText(String.format("%s %s", mContext.getResources().getString(R.string.rmb), BusinessUtils.formatDouble2String(data.price)));

    }
}
