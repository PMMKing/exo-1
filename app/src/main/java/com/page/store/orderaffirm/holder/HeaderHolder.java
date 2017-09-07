package com.page.store.orderaffirm.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.framework.rvadapter.holder.BaseViewHolder;
import com.page.store.orderaffirm.model.CommitOrderParam;
import com.page.store.orderaffirm.model.CommitOrderParam.Product;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shucheng.qu on 2017/8/17.
 */

public class HeaderHolder extends BaseViewHolder<Product> {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_address)
    TextView tvAddress;

    public HeaderHolder(Context context, View itemView) {
        super(context, itemView);
//        R.layout.pub_activity_orderaffirm_item_header_layout;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, Product data, int position) {
        if (data == null || data.address == null) return;
        tvAddress.setText(data.address.address);
        tvName.setText(data.address.name);
    }
}
