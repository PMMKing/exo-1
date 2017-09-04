package com.page.store.payresult.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.framework.activity.BaseActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_payresult_layout);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_order_detail, R.id.tv_go_shopping})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_order_detail:
                qStartActivity(OrderDetailsActivity.class);
                break;
            case R.id.tv_go_shopping:

                break;
        }
    }
}
