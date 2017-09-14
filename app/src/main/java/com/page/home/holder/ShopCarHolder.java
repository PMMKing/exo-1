package com.page.home.holder;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.utils.BusinessUtils;
import com.framework.utils.ShopCarUtils;
import com.framework.utils.ToastUtils;
import com.framework.utils.imageload.ImageLoad;
import com.page.home.activity.ShoppingCartFragment;
import com.page.store.orderaffirm.model.CommitOrderParam;
import com.page.store.orderaffirm.model.CommitOrderParam.Product;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by shucheng.qu on 2017/9/13.
 */

public class ShopCarHolder extends BaseViewHolder<Product> {

    private final ShoppingCartFragment fragment;
    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_sub)
    TextView tvSub;
    @BindView(R.id.tv_number2)
    TextView tvNumber2;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    private Product data;

    public ShopCarHolder(Context context, ShoppingCartFragment fragment, View itemView) {
        super(context, itemView);
//        R.layout.pub_fragment_shopcar_item_layout;
        ButterKnife.bind(this, itemView);
        this.fragment = fragment;

    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, Product data, int position) {
        this.data = data;
        if (data == null) return;
        ImageLoad.loadPlaceholder(mContext, data.pic, ivImage);
        tvName.setText(data.name);
        tvPrice.setText(String.format("%s %s", mContext.getResources().getString(R.string.rmb), BusinessUtils.formatDouble2String(data.price)));
        tvNumber2.setText(String.format("%d", data.num));
    }

    @OnClick({R.id.tv_sub, R.id.tv_add})
    public void onViewClicked(View view) {
        int number = Integer.parseInt(tvNumber2.getText().toString().trim());
        if (data == null) return;
        switch (view.getId()) {
            case R.id.tv_sub:
                if (number > 1) {
                    tvNumber2.setText(--number + "");
                    data.num = number;
                    fragment.refreshPrice();
                } else {
                    ShopCarUtils.getInstance().removeProduct(data);
                    fragment.refreshAdapter();
                }
                break;
            case R.id.tv_add:
                if (number > 100) {
                    ToastUtils.toastSth(mContext, "库存不足");
                } else {
                    tvNumber2.setText(++number + "");
                    data.num = number;
                    fragment.refreshPrice();
                }

                break;
        }
    }
}
