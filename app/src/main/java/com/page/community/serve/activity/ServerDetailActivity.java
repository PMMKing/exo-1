package com.page.community.serve.activity;

import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.framework.activity.BaseActivity;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.utils.html.HtmlUtils;
import com.framework.utils.viewutils.ViewUtils;
import com.page.community.serve.model.SerDetailParam;
import com.page.community.serve.model.SerDetailResult;
import com.page.community.serve.model.SerDetailResult.Data;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shucheng.qu on 2017/9/16.
 */

public class ServerDetailActivity extends BaseActivity {

    public static String CLICKMAP = "clickMap";

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.ll_name)
    LinearLayout llName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.ll_phone)
    LinearLayout llPhone;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    private ServiceMap serviceMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_serverdetail_layout);
        ButterKnife.bind(this);
        setTitleBar("详情", true);
        if (myBundle == null) finish();
        serviceMap = (ServiceMap) myBundle.getSerializable(CLICKMAP);
        startRequest();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        myBundle.putSerializable(CLICKMAP, serviceMap);
    }

    private void startRequest() {
        SerDetailParam param = new SerDetailParam();
        param.id = myBundle.getString("id");
        Request.startRequest(param, serviceMap, mHandler, Request.RequestFeature.BLOCK);
    }

    private void updataView(Data data) {
        ViewUtils.setOrGone(llPhone, !TextUtils.isEmpty(data.phone));
        ViewUtils.setOrGone(tvPhone, data.phone);
        ViewUtils.setOrGone(llAddress, !TextUtils.isEmpty(data.address));
        ViewUtils.setOrGone(tvAddress, data.address);
        ViewUtils.setOrGone(llContent, !TextUtils.isEmpty(data.content));
        ViewUtils.setOrGone(tvContent, data.content);
        tvContent.setText(HtmlUtils.getHtml(getContext(),tvContent,data.content));
        ViewUtils.setOrGone(llName, !TextUtils.isEmpty(data.title));
        ViewUtils.setOrGone(tvName, data.title);
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == serviceMap) {
            SerDetailResult result = (SerDetailResult) param.result;
            if (result != null && result.data != null) {
                updataView(result.data);
            } else {
                showToast(result.bstatus.des);
            }
        }
        return false;
    }

}
