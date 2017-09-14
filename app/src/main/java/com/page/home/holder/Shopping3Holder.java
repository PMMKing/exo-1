package com.page.home.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.utils.BusinessUtils;
import com.framework.utils.imageload.ImageLoad;
import com.page.home.model.ShopRecResult;
import com.page.home.model.ShopRecResult.Data.ProductList;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shucheng.qu on 2017/8/18.
 */

public class Shopping3Holder extends BaseViewHolder<ProductList> {

    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_price)
    TextView tvPrice;

    public Shopping3Holder(Context context, View itemView) {
        super(context, itemView);
//        R.layout.pub_fragment_shopping_3_item_layout;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, ProductList data, int position) {
        if (data == null) return;
        ImageLoad.loadPlaceholder(mContext, data.pic1, ivImage);
        tvName.setText(data.name);
        tvPrice.setText("¥" + BusinessUtils.formatDouble2String(data.price));
    }
}
