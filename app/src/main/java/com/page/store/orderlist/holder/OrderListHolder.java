package com.page.store.orderlist.holder;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.utils.ArrayUtils;
import com.page.store.orderlist.model.OrderListResult.Data.OrderList;
import com.page.store.orderlist.view.ProductView;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shucheng.qu on 2017/8/18.
 */

public class OrderListHolder extends BaseViewHolder<OrderList> {

    @BindView(R.id.ll_products)
    LinearLayout llProducts;
    @BindView(R.id.tv_evalate)
    TextView tvEvalate;

    public OrderListHolder(Context context, View itemView) {
        super(context, itemView);
//        R.layout.pub_fragment_orderlist_item_layout;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, OrderList data, int position) {
        llProducts.removeAllViews();
        if (data == null || ArrayUtils.isEmpty(data.products)) return;
        for (OrderList.Products product : data.products) {
            ProductView productView = new ProductView(mContext);
            productView.updataView(product);
            llProducts.addView(productView);
        }
    }
}
