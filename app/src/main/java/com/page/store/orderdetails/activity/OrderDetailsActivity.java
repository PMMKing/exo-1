package com.page.store.orderdetails.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.framework.activity.BaseActivity;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.utils.ArrayUtils;
import com.page.pay.PayActivity;
import com.page.pay.PayData;
import com.page.store.orderdetails.model.OrderDetailParam;
import com.page.store.orderdetails.model.OrderDetailResult;
import com.page.store.orderdetails.model.OrderDetailResult.Data;
import com.page.store.orderlist.model.OrderListResult;
import com.page.store.orderlist.model.OrderListResult.Data.OrderList.Products;
import com.page.store.orderlist.view.ProductView;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by shucheng.qu on 2017/8/18.
 */

public class OrderDetailsActivity extends BaseActivity {

    public static final String ID = "id";

    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.tv_logistics_msg)
    TextView tvLogisticsMsg;
    @BindView(R.id.tv_logistics_time)
    TextView tvLogisticsTime;
    @BindView(R.id.ll_logistics)
    LinearLayout llLogistics;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.ll_recipients)
    LinearLayout llRecipients;
    @BindView(R.id.tv_cancle)
    TextView tvCancle;
    @BindView(R.id.tv_pay)
    TextView tvPay;
    @BindView(R.id.ll_products)
    LinearLayout llProducts;
    private String id;
    private OrderDetailResult result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_orderdetail_layout);
        if (myBundle == null) finish();
        ButterKnife.bind(this);
        setTitleBar("订单详情", true);
        id = myBundle.getString(ID);
        startRequest();
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        myBundle.putString(ID, id);
    }

    private void startRequest() {
        OrderDetailParam param = new OrderDetailParam();
        param.id = id;
        Request.startRequest(param, ServiceMap.getOrder, mHandler, Request.RequestFeature.BLOCK);
    }


    private void updataView(Data data) {
        tvStatus.setText(data.status);
        tvAddress.setText(String.format("收货地址：%s", data.address));
        tvName.setText(String.format("收货人：%s", data.receiver));
        llProducts.removeAllViews();
        for (Products product : data.products) {
            ProductView productView = new ProductView(getContext());
            productView.updataView(product);
            llProducts.setBackgroundColor(getResources().getColor(R.color.pub_color_white));
            llProducts.addView(productView);
        }
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.getOrder) {
             result = (OrderDetailResult) param.result;
            if (result != null && result.data != null && !ArrayUtils.isEmpty(result.data.products)) {
                updataView(result.data);
            } else {
                showToast(param.result.bstatus.des);
            }
        }
        return false;
    }

    @OnClick({R.id.ll_logistics, R.id.ll_recipients, R.id.tv_cancle, R.id.tv_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_logistics:
                break;
            case R.id.ll_recipients:
                break;
            case R.id.tv_cancle:
                break;
            case R.id.tv_pay:
                Bundle bundle = new Bundle();
                PayData payData = new PayData();
                payData.id = result.data.id;
                payData.price = result.data.totalprice;
                bundle.putSerializable("order",payData);
                qStartActivity(PayActivity.class, bundle);
                break;
        }
    }
}
