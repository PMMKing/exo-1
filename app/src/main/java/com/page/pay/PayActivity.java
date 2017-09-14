package com.page.pay;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Button;

import com.alipay.sdk.app.PayTask;
import com.framework.activity.BaseActivity;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.page.store.orderdetails.model.OrderDetailResult;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chenxi.cui on 2017/9/11.
 */

public class PayActivity extends BaseActivity {
    protected static final int SDK_PAY_FLAG = 0x35;
    @BindView(R.id.btn_pay)
    Button btnPay;
    private PayResult payResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_pay_activity);
        ButterKnife.bind(this);
        OrderDetailResult.Data order = (OrderDetailResult.Data) myBundle.getSerializable("order");
        PayParam payParam = new PayParam();
        payParam.orderid = order.id;
        payParam.price = order.totalprice;
        Request.startRequest(payParam, ServiceMap.alipayPayProduct, mHandler, Request.RequestFeature.BLOCK);
    }

    @Override
    protected Handler.Callback genCallback() {

        return new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case SDK_PAY_FLAG: {
//                        PayResult payResult = new PayResult((String) msg.obj);
//                        String resultStatus = payResult.getResultStatus();
                        // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
//                        if (TextUtils.equals(resultStatus, "9000") || TextUtils.equals(resultStatus, "8000")) {
//                        }
//                        basePayControl.goOrderDetail(aliPayResult);
                        break;
                    }

                }
                return false;
            }
        };
    }

    /**
     * 支付宝支付
     *
     * @param payInfo
     */
    public void airPay(final String payInfo) {

        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(PayActivity.this);
                String result = alipay.pay(payInfo, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.alipayPayProduct) {

            if (param.result.bstatus.code == 0) {
                 payResult = (PayResult) param.result;

            }
        }

        return super.onMsgSearchComplete(param);
    }

    @OnClick(R.id.btn_pay)
    public void onClick() {
        airPay(payResult.data.params);
    }
}
