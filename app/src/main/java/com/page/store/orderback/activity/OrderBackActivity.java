package com.page.store.orderback.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.framework.activity.BaseActivity;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.page.store.orderback.model.OrderBackParam;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by shucheng.qu on 2017/9/18.
 */

public class OrderBackActivity extends BaseActivity {

    public static final String ID = "id";

    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_orderback_layout);
        ButterKnife.bind(this);
        if (myBundle == null) finish();
        setTitleBar("退款", true);
        id = myBundle.getString(ID);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        myBundle.putString(ID, id);
    }

    @OnClick(R.id.tv_commit)
    public void onViewClicked() {
        String trim = etContent.getText().toString().toString().trim();
        if (TextUtils.isEmpty(trim)) {
            showToast("退款原因还没有写~");
            return;
        }
        OrderBackParam param = new OrderBackParam();
        param.id = id;
        param.reason = trim;
        Request.startRequest(param, ServiceMap.orderRefund, mHandler, Request.RequestFeature.BLOCK);
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.orderRefund) {
            showToast(param.result.bstatus.des);
            if (param.result.bstatus.code == 0) {
                finish();
            }
        }
        return false;
    }
}
