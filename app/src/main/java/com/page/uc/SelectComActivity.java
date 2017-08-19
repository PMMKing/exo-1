package com.page.uc;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.framework.activity.BaseActivity;
import com.framework.domain.param.BaseParam;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.haolb.client.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chenxi.cui on 2017/8/18.
 */

public class SelectComActivity extends BaseActivity {
    @BindView(R.id.text_0)
    TextView text0;
    @BindView(R.id.ll_0)
    LinearLayout ll0;
    @BindView(R.id.text_1)
    TextView text1;
    @BindView(R.id.ll_1)
    LinearLayout ll1;
    @BindView(R.id.text_2)
    TextView text2;
    @BindView(R.id.ll_2)
    LinearLayout ll2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_select_com_layout);
        ButterKnife.bind(this);
        setTitleBar("选择小区",true);

    }


    @OnClick({R.id.ll_0, R.id.ll_1, R.id.ll_2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_0:
                Request.startRequest(new BaseParam(), ServiceMap.getDistricts, mHandler,Request.RequestFeature.BLOCK);
                break;
            case R.id.ll_1:
                Request.startRequest(new BaseParam(), ServiceMap.getBuildings, mHandler,Request.RequestFeature.BLOCK);
                break;
            case R.id.ll_2:
                Request.startRequest(new BaseParam(), ServiceMap.getUnits, mHandler,Request.RequestFeature.BLOCK);
                break;
        }
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.getDistricts) {

        }else  if (param.key == ServiceMap.getBuildings) {

        }else if (param.key == ServiceMap.getUnits) {

        }
        return false;
    }
}
