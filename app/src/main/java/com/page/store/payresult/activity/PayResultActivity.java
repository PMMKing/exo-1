package com.page.store.payresult.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.framework.activity.BaseActivity;
import com.page.home.activity.MainActivity;
import com.qfant.wuye.R;
import com.page.store.orderdetails.activity.OrderDetailsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by shucheng.qu on 2017/8/17.
 */

public class PayResultActivity extends BaseActivity {

    @BindView(R.id.tv_order_detail)
    TextView tvOrderDetail;
    @BindView(R.id.tv_go_shopping)
    TextView tvGoShopping;
    @BindView(R.id.text)
    TextView textView;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_payresult_layout);
        ButterKnife.bind(this);
        id = myBundle.getString("id");
        textView.setText(String.format("订单编号%s支付成功", id));
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null && intent.getExtras() != null) {
            id = intent.getExtras().getString("id");
        }
        textView.setText(String.format("订单编号%s支付成功", id));
    }

    @OnClick({R.id.tv_order_detail, R.id.tv_go_shopping})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_order_detail:
                Bundle bundle = new Bundle();
                bundle.putString("goto", "orderDetail");
                bundle.putString("id", id + "");
                qBackToActivity(MainActivity.class, bundle);
                break;
            case R.id.tv_go_shopping:
                qBackToActivity(MainActivity.class, null);
                break;
        }
    }
}
