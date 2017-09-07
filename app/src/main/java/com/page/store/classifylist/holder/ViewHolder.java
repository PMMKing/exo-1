package com.page.store.classifylist.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.utils.BusinessUtils;
import com.framework.utils.imageload.ImageLoad;
import com.page.store.classifylist.model.ClassifyListResult.Data.ProductList;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shucheng.qu on 2017/8/15.
 */

public class ViewHolder extends BaseViewHolder<ProductList> {


    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_price)
    TextView tvPrice;

    public ViewHolder(Context context, View itemView) {
        super(context, itemView);
//        R.layout.pub_activity_classifylist_item_layout;
        ButterKnife.bind(this, itemView);

    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, ProductList data, int position) {
        if (data == null) return;
        ImageLoad.loadPlaceholder(mContext, data.pic1, ivImage);
        tvName.setText(data.name);
        tvPrice.setText(BusinessUtils.formatDouble2String(data.marketprice));
    }
}
