package com.page.store.orderlist.view;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.framework.utils.BusinessUtils;
import com.framework.utils.imageload.ImageLoad;
import com.page.store.orderlist.model.OrderListResult;
import com.page.store.orderlist.model.OrderListResult.Data.OrderList.Products;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shucheng.qu on 2017/9/12.
 */

public class ProductView extends FrameLayout {
    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_number2)
    TextView tvNumber2;
    private String rmb;

    public ProductView(@NonNull Context context) {
        this(context, null);
    }

    public ProductView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProductView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        rmb = getContext().getString(R.string.rmb);
        FrameLayout.inflate(getContext(), R.layout.pub_fragment_orderlist_item_item_layout, this);
        ButterKnife.bind(this);
    }

    public void updataView(Products product) {
        if (product == null) return;
        ImageLoad.loadPlaceholder(getContext(), product.pic1, ivImage);
        tvName.setText(product.productname);
        tvNumber.setText(String.format("共%d件商品 合计：", product.num));
        tvPrice.setText(String.format("%s %s", rmb, BusinessUtils.formatDouble2String(product.price * product.num)));
        tvNumber2.setText(String.format("X%d", product.num));
    }
}
