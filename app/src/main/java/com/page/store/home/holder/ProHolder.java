package com.page.store.home.holder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.framework.app.MainApplication;
import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.utils.BusinessUtils;
import com.framework.utils.ShopCarUtils;
import com.framework.utils.ToastUtils;
import com.framework.utils.imageload.ImageLoad;
import com.framework.utils.viewutils.ViewUtils;
import com.page.home.activity.MainActivity;
import com.page.store.home.model.ClassifyResult.Data.Datas.Produts;
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
        tvSalesVolume.setText(String.format("价格 ¥%s", BusinessUtils.formatDouble2String(data.price)));
        refresh();
    }

    private void refresh() {
        Product product = ShopCarUtils.getInstance().getProductForId(data.id);
        tvCarNumber.setText(product == null ? "" : "X" + product.num);
        ViewUtils.setOrGone(tvSub, product != null);
    }

    @OnClick({R.id.tv_sub, R.id.tv_add})
    public void onViewClicked(View view) {
        Product product = new Product();
        product.price = data.price;
        product.pic = data.pic1;
        product.id = data.id;
        product.name = data.name;
        product.storage = data.storage;
        switch (view.getId()) {
            case R.id.tv_sub:
                product.num = -1;
                ShopCarUtils.getInstance().addProduct(product);
                refresh();
                break;
            case R.id.tv_add:
                product.num = 1;
                ShopCarUtils.getInstance().addProduct(product);
                refresh();
                break;
        }
       mContext.sendBroadcast(new Intent(MainActivity.REFRESH_TAB_ACTION));
    }

}
