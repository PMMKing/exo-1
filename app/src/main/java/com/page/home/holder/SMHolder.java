package com.page.home.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.utils.BusinessUtils;
import com.framework.utils.imageload.ImageLoad;
import com.framework.view.sivin.Banner;
import com.page.home.model.ShopRecResult;
import com.page.home.model.ShopRecResult.Data.ProductList;
import com.page.store.home.model.FoodRecResult;
import com.page.store.home.model.FoodRecResult.Data.Products;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shucheng.qu on 2017/8/13.
 */

public class SMHolder extends BaseViewHolder<Products> {

    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_price)
    TextView tvPrice;

    public SMHolder(Context context, View itemView) {
        super(context, itemView);
//        R.layout.pub_fragment_home_711_item_layout;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, Products data, int position) {
        if (data == null) return;
        ImageLoad.loadPlaceholder(mContext, data.pic1, ivImage);
        tvName.setText(data.name);
        tvPrice.setText("¥" + BusinessUtils.formatDouble2String(data.price));
    }
}
