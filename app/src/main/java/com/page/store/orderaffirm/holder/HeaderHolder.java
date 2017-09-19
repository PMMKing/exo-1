package com.page.store.orderaffirm.holder;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.framework.activity.BaseActivity;
import com.framework.rvadapter.holder.BaseViewHolder;
import com.page.address.activity.AddressActivity;
import com.page.store.orderaffirm.model.CommitOrderParam.Product;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by shucheng.qu on 2017/8/17.
 */

public class HeaderHolder extends BaseViewHolder<Product> {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_address_tips)
    TextView tvAddressTips;

    public HeaderHolder(Context context, View itemView) {
        super(context, itemView);
//        R.layout.pub_activity_orderaffirm_item_header_layout;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, Product data, int position) {
        tvAddressTips.setVisibility(View.VISIBLE);
        if (data == null || data.address == null || TextUtils.isEmpty(data.address.address)) return;
        tvAddress.setText(data.address.address);
        tvName.setText(data.address.name);
        tvAddressTips.setVisibility(View.GONE);
    }

    @OnClick(R.id.tv_address_tips)
    public void onViewClicked() {
        ((BaseActivity) mContext).qStartActivityForResult(AddressActivity.class, null, 100);
    }
}
