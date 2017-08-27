package com.page.login.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.framework.activity.BaseActivity;
import com.framework.domain.param.BaseParam;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.haolb.client.R;
import com.page.home.activity.MainActivity;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shucheng.qu on 2017/6/1.
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.tiet_username)
    TextInputEditText tietUsername;
    @BindView(R.id.til_username)
    TextInputLayout tilUsername;
    @BindView(R.id.tiet_password)
    TextInputEditText tietPassword;
    @BindView(R.id.til_password)
    TextInputLayout tilPassword;
    @BindView(R.id.text_send_code)
    TextView textSendCode;

    private boolean mIsExit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_login_layout);
        ButterKnife.bind(this);
        textSendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//             sendCode();
//                checkUpdate();
                getLink();
            }

        });
    }

    public static class LinkParam extends BaseParam {
        public int type = 1;
    }

    public void getLink() {
        Request.startRequest(new LinkParam(), ServiceMap.getLinks, mHandler);
    }

    public void checkUpdate() {

        Request.startRequest(new UpdateParam(), ServiceMap.checkVersion, mHandler);
    }

    private void sendCode() {
        LoginSendCodeParam loginSendCodeParam = new LoginSendCodeParam();
        loginSendCodeParam.phone = "15811508404";
        Request.startRequest(loginSendCodeParam, ServiceMap.getVerificationCode, mHandler);
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.getVerificationCode) {

        }
        return super.onMsgSearchComplete(param);
    }

    @Override
    public void onBackPressed() {

        qStartActivity(MainActivity.class);
    }


}