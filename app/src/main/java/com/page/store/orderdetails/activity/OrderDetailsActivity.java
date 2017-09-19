package com.page.store.orderdetails.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.framework.activity.BaseActivity;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.utils.ArrayUtils;
import com.framework.utils.Dimen;
import com.framework.utils.viewutils.ViewUtils;
import com.page.community.event.activity.EventActivity;
import com.page.pay.PayActivity;
import com.page.pay.PayData;
import com.page.store.orderback.activity.OrderBackActivity;
import com.page.store.orderdetails.model.OrderDetailParam;
import com.page.store.orderdetails.model.OrderDetailResult;
import com.page.store.orderdetails.model.OrderDetailResult.Data;
import com.page.store.orderlist.model.OrderListResult.Data.OrderList.Products;
import com.page.store.orderlist.model.OrderStatusParam;
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
    public static final String STATUS = "status";
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
    @BindView(R.id.ll_products)
    LinearLayout llProducts;
    @BindView(R.id.ll_status)
    LinearLayout llStatus;

    private String id;
    private OrderDetailResult result;
    private int status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_orderdetail_layout);
        if (myBundle == null) finish();
        ButterKnife.bind(this);
        setTitleBar("订单详情", true);
        id = myBundle.getString(ID);
        status = myBundle.getInt(STATUS);
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
        myBundle.putInt(STATUS, status);
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
        ViewUtils.setOrGone(tvLogisticsTime, data.orderno);
        ViewUtils.setOrGone(tvLogisticsMsg, data.logistics);
        llProducts.removeAllViews();
        for (Products product : data.products) {
            ProductView productView = new ProductView(getContext());
            productView.updataView(product);
            llProducts.setBackgroundColor(getResources().getColor(R.color.pub_color_white));
            llProducts.addView(productView);
        }
        switch (status) {
            case 1:
                orderCancle();
                orderPay(data.totalprice);
                break;
            case 2:
//                orderCancle(data.id);
                break;
            case 3:
                orderBack();
                orderConfirm();
                break;
            case 4:

                break;
            case 5:
                orderBack();
                break;
            case 6:

                break;
            case 7:

                break;
            case 8:
                orderEvaluate();
                break;
            case 9:

                break;
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
        } else if (param.key == ServiceMap.cancelOrder) {
            if (param.result.bstatus.code == 0) {
                showToast("订单取消成功");
            } else {
                showToast(param.result.bstatus.des);
            }
        } else if (param.key == ServiceMap.updateOrderConfirm) {
            showToast(param.result.bstatus.des);
        }
        return false;
    }

    private TextView getTextView(String text) {
        TextView textView = new TextView(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(Dimen.dpToPx(80), Dimen.dpToPx(33));
        params.leftMargin = Dimen.dpToPx(10);
        textView.setText(text);
        textView.setTextColor(getContext().getResources().getColor(R.color.pub_color_blue));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        textView.setGravity(Gravity.CENTER);
        textView.setClickable(true);
        textView.setBackgroundResource(R.drawable.pub_btn_blue_box_selector);
        textView.setLayoutParams(params);
        return textView;
    }

    private void orderCancle() {
        TextView textView = getTextView("取消");
        llStatus.addView(textView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderStatusParam param = new OrderStatusParam();
                param.id = id;
                Request.startRequest(param, ServiceMap.cancelOrder, mHandler, Request.RequestFeature.BLOCK);
            }
        });
    }

    private void orderPay(final double totalprice) {
        TextView textView = getTextView("支付");
        llStatus.addView(textView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                PayData payData = new PayData();
                payData.id = Integer.parseInt(id);
                payData.price = totalprice;
                bundle.putSerializable("order", payData);
                qStartActivity(PayActivity.class, bundle);
            }
        });
    }

    private void orderEvaluate() {
        TextView textView = getTextView("评价");
        llStatus.addView(textView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(EventActivity.ID, id);
                qStartActivity(EventActivity.class, bundle);
            }
        });
    }

    private void orderBack() {
        TextView textView = getTextView("退款");
        llStatus.addView(textView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(OrderBackActivity.ID, id);
                qStartActivity(OrderBackActivity.class, bundle);
            }
        });
    }

    private void orderConfirm() {
        TextView textView = getTextView("收货");
        llStatus.addView(textView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderStatusParam param = new OrderStatusParam();
                param.id = id;
                Request.startRequest(param, ServiceMap.updateOrderConfirm, mHandler, Request.RequestFeature.BLOCK);
            }
        });
    }

}
