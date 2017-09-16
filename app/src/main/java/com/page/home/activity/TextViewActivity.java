package com.page.home.activity;

import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.widget.TextView;

import com.framework.activity.BaseActivity;
import com.framework.domain.param.BaseParam;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.page.home.model.ContactResult;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.page.community.serve.activity.ServeActivity.SERVICEMAP;

/**
 * Created by shucheng.qu on 2017/9/13.
 */

public class TextViewActivity extends BaseActivity {

    @BindView(R.id.tv_content)
    TextView tvContent;
    private String content;
    private ServiceMap serviceMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_textview_layout);
        ButterKnife.bind(this);

        if (myBundle == null) finish();
        content = myBundle.getString("content");
        setTitleBar("通知详情", true);
        serviceMap = (ServiceMap) myBundle.getSerializable(SERVICEMAP);
        if (TextUtils.isEmpty(content)) {
            Request.startRequest(new BaseParam(), ServiceMap.contact, mHandler, Request.RequestFeature.BLOCK, Request.RequestFeature.CANCELABLE);
        }else {
            tvContent.setText(Html.fromHtml(content));
        }
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.contact) {
            ContactResult result = (ContactResult) param.result;
            if (param.result.bstatus.code == 0) {
                tvContent.setText(Html.fromHtml(content));
            }
        }
        return super.onMsgSearchComplete(param);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        myBundle.putString("content", content);
    }
}
