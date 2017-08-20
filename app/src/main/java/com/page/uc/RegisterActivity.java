package com.page.uc;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.framework.activity.BaseActivity;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.utils.BusinessUtils;
import com.haolb.client.R;
import com.page.uc.bean.ComBean;
import com.page.uc.bean.LoginParam;
import com.page.uc.bean.LoginSendCodeParam;
import com.page.uc.bean.RegiserParam;
import com.page.uc.bean.RegiserResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by shucheng.qu on 2017/8/9.
 */

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.ed_name)
    EditText edName;
    @BindView(R.id.ed_phone)
    EditText edPhone;
    @BindView(R.id.ed_code)
    EditText edCode;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    private ComBean comBean4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_fillmsg_layout);
        ButterKnife.bind(this);
        ComBean comBean1 = (ComBean) myBundle.getSerializable("key1");
        ComBean comBean2 = (ComBean) myBundle.getSerializable("key2");
        ComBean comBean3 = (ComBean) myBundle.getSerializable("key3");
        comBean4 = (ComBean) myBundle.getSerializable("key4");

        StringBuilder sb = new StringBuilder();
        if (comBean1 != null) {
            sb.append(comBean1.name);
        }
        if (comBean2 != null) {
            sb.append(comBean2.name);
        }
        if (comBean3 != null) {
            sb.append(comBean3.name);
        }
        if (comBean4 != null) {
            sb.append(comBean4.name);
        }
        tvAddress.setText("所选小区：" + sb.toString());
        setTitleBar("填写信息", true);
    }

    @OnClick({R.id.tv_address, R.id.tv_code, R.id.tv_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_address:
                break;
            case R.id.tv_code:
                String phone1 = edPhone.getText().toString();
                if (!BusinessUtils.checkPhoneNumber(phone1)) {
                    showToast("请填写正确手机号");
                    return;
                }
                LoginSendCodeParam loginSendCodeParam = new LoginSendCodeParam();
                loginSendCodeParam.phone = phone1;
                Request.startRequest(loginSendCodeParam, ServiceMap.getVerificationCode, mHandler, Request.RequestFeature.BLOCK);
                break;
            case R.id.tv_register:
                String code = edCode.getText().toString();
                String phone = edPhone.getText().toString();
                String name = edName.getText().toString();
                if (!BusinessUtils.checkPhoneNumber(phone)) {
                    showToast("请填写正确手机号");
                    return;
                }
                if (TextUtils.isEmpty(name)) {
                    showToast("请填写姓名");
                    return;
                }
                if (TextUtils.isEmpty(code) || code.length() != 6) {
                    showToast("验证码有误");
                    return;
                }

                RegiserParam regiserParam = new RegiserParam();
                regiserParam.name = name;
                regiserParam.room_id = comBean4 == null ? 0 : comBean4.id;
                regiserParam.phone = phone;
                regiserParam.verificationCode = Integer.parseInt(code);

                Request.startRequest(regiserParam, ServiceMap.quickRegister, mHandler, Request.RequestFeature.BLOCK);
                break;
        }
    }
    public void buildSendCodeText() {
        tvCode.setEnabled(false);

        new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvCode.setText(millisUntilFinished / 1000 + "S");
            }

            @Override
            public void onFinish() {
                tvCode.setText("重新发送");
                tvCode.setEnabled(true);
            }
        }.start();
    }


    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.getVerificationCode) {
            if (param.result.bstatus.code == 0) {
                buildSendCodeText();
            }
        } else if (param.key == ServiceMap.quickRegister) {
            RegiserResult regiserResult = (RegiserResult) param.result;
            if (regiserResult.bstatus.code == 0) {
                qBackToActivity(LoginActivity.class, null);
            }
        }
        return super.onMsgSearchComplete(param);
    }

}
