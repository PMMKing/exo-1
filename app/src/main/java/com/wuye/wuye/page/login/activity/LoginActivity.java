package com.wuye.wuye.page.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wuye.wuye.R;
import com.wuye.wuye.base.BaseActivity;
import com.wuye.wuye.framework.view.TitleBarItem;
import com.wuye.wuye.net.link.param.NetworkParam;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by shucheng.qu on 2017/6/1.
 */

public class LoginActivity extends BaseActivity {

    private boolean mIsExit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        animType = CloseActivityAnim.FADE;
        ButterKnife.bind(this);
    }


    @Override
    public void onMsgSearchComplete(NetworkParam param) {
        super.onMsgSearchComplete(param);

    }


    @Override
    public void onBackPressed() {
        exitBy2Click();
    }

    public void exitBy2Click() {
        Timer tExit;

        if (!mIsExit) {
            mIsExit = true;
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    mIsExit = false;
                }
            }, 2000);
        } else {
            finish();
        }
    }

}
