package com.page.community.applyfor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.framework.activity.BaseActivity;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.utils.ArrayUtils;
import com.framework.view.AddView;
import com.qfant.wuye.R;
import com.page.community.applyfor.model.ApplyForParam;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by shucheng.qu on 2017/8/11.
 */

public class ApplyForActivity extends BaseActivity {

    @BindView(R.id.addView)
    AddView addView;
    @BindView(R.id.et_intro)
    EditText etIntro;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.tv_commit)
    TextView tvCommit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_applyfor_layout);
        ButterKnife.bind(this);
        setTitleBar("申请维修", true);
        addView.setAddNumber(1);
    }

    private void startRequest() {

        String[] imageUrls = addView.getImageUrls();
        String intro = etIntro.getText().toString();
        String phone = etPhone.getText().toString().trim();
        String address = etAddress.getText().toString().trim();

        if (ArrayUtils.isEmpty(imageUrls)) {
            showToast("还没有上传图片");
            return;
        }
        if (TextUtils.isEmpty(intro)) {
            showToast("问题描述不能为空");
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            showToast("联系电话不能为空");
            return;
        }
        if (TextUtils.isEmpty(address)) {
            showToast("详细地址不能为空");
            return;
        }

        ApplyForParam param = new ApplyForParam();
        param.pic = imageUrls[0];
        param.intro = intro;
        param.phone = phone;
        param.address = address;
        Request.startRequest(param, ServiceMap.submitRepair, mHandler, Request.RequestFeature.BLOCK);
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.uploadPic) {
            addView.onMsgSearchComplete(param);
        } else if (param.key == ServiceMap.submitRepair) {
            if (param.result.bstatus.code == 0) {
                finish();
            } else {
                showToast(param.result.bstatus.des);
            }
        } else if (param.key == ServiceMap.updateSnapshot) {
            if (param.result.bstatus.code == 0) {
                finish();
            } else {
                showToast(param.result.bstatus.des);
            }
        }
        return false;
    }

    @OnClick(R.id.tv_commit)
    public void onViewClicked() {
        startRequest();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        addView.onActivityResult(requestCode, resultCode, data);
    }
}
