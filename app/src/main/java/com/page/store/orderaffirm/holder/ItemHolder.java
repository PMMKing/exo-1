package com.page.store.orderaffirm.holder;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.utils.BusinessUtils;
import com.framework.utils.imageload.ImageLoad;
import com.page.store.orderaffirm.model.CommitOrderParam.Product;
import com.page.store.orderaffirm.view.ProItemView;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by shucheng.qu on 2017/8/17.
 */

public class ItemHolder extends BaseViewHolder<Product> {


    @BindView(R.id.proitemview)
    ProItemView proitemview;

    public ItemHolder(Context context, View itemView) {
        super(context, itemView);
//        R.layout.pub_activity_orderaffirm_item_pro_layout;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, Product data, int position) {
        if (data == null) return;
        proitemview.updataView(data);
    }
}
