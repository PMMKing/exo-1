package com.page.store.orderdetails.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.framework.activity.BaseActivity;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.page.store.orderdetails.model.OrderDetailParam;
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
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_orderdetail_layout);
        if (myBundle == null) finish();
        ButterKnife.bind(this);
        id = myBundle.getString(ID);
        startRequest();
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

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {

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
                break;
        }
    }
}
