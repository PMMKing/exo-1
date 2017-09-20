package com.qfant.wuye.wxapi;

import android.content.Intent;
import android.os.Bundle;

import com.framework.activity.BaseActivity;
import com.framework.app.AppConstants;
import com.page.pay.PayActivity;
import com.qfant.wuye.R;
import com.taobao.weex.devtools.common.LogUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by chenxi.cui on 2017/9/14.
 */

public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);

        api = WXAPIFactory.createWXAPI(this, AppConstants.APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        LogUtil.d(TAG, "onPayFinish, errCode = " + resp.errCode);
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            Bundle bundle = new Bundle();
            bundle.putInt("resp", resp.errCode);
            qBackToActivity(PayActivity.class ,bundle);
            if (resp.errCode == 0) {//成功

            } else if (resp.errCode == -1) {//错误

            } else if (resp.errCode == -2) {//取消

            }
        }
    }
}

