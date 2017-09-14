package com.page.store.classify.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.utils.ShopCarUtils;
import com.framework.utils.ToastUtils;
import com.framework.utils.imageload.ImageLoad;
import com.page.store.classify.model.ClassifyResult.Data.Datas.Produts;
import com.page.store.orderaffirm.model.CommitOrderParam.Product;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by shucheng.qu on 2017/8/15.
 */

public class ProHolder extends BaseViewHolder<Produts> {

    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_sales_volume)
    TextView tvSalesVolume;
    @BindView(R.id.tv_car_number)
    TextView tvCarNumber;
    @BindView(R.id.tv_sub)
    TextView tvSub;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    private Produts data;


    public ProHolder(Context context, View itemView) {
        super(context, itemView);
//        R.layout.pub_activity_classify_right_item_layout;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, Produts data, int position) {
        if (data == null) return;
        this.data = data;
        ImageLoad.loadPlaceholder(mContext, data.pic1, ivImage);
        tvName.setText(data.name);
        Product productForId = ShopCarUtils.getInstance().getProductForId(data.id);
        refresh(productForId == null ? 0 : productForId.num);
    }

    private void refresh(int num) {
        if (num > 0) {
            tvCarNumber.setText("X" + num);
            tvSub.setVisibility(View.VISIBLE);
        } else {
            tvCarNumber.setText("");
            tvSub.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.tv_sub, R.id.tv_add})
    public void onViewClicked(View view) {
        Product product = ShopCarUtils.getInstance().getProductForId(data.id);
        if (product == null) {
            product = new Product();
        }
        product.price = data.price;
        product.pic = data.pic1;
        product.id = data.id;
        product.name = data.name;
        product.storage = data.storage;
        switch (view.getId()) {
            case R.id.tv_sub:
                if (product.num > 1) {
                    product.num -= 1;
                    ShopCarUtils.getInstance().saveProduct(product);
                    refresh(product.num);
                } else {
                    refresh(0);
                    ShopCarUtils.getInstance().removeProduct(product);
                }
                break;
            case R.id.tv_add:
                if (product.num < data.storage) {
                    product.num += 1;
                    ShopCarUtils.getInstance().saveProduct(product);
                    refresh(product.num);
                } else {
                    ToastUtils.toastSth(mContext, "库存不足");
                }

                break;
        }
    }

}
