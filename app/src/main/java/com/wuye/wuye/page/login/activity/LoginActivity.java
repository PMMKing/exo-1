package com.wuye.wuye.page.login.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.wuye.wuye.R;
import com.wuye.wuye.base.BaseActivity;
import com.wuye.wuye.framework.view.ListDialog;
import com.wuye.wuye.framework.view.loading.ProgressDialog;
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

    @BindView(R.id.tiet_username)
    TextInputEditText tietUsername;
    @BindView(R.id.til_username)
    TextInputLayout tilUsername;
    @BindView(R.id.tiet_password)
    TextInputEditText tietPassword;
    @BindView(R.id.til_password)
    TextInputLayout tilPassword;
    private boolean mIsExit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        animType = CloseActivityAnim.FADE;
        setContentView(R.layout.pub_activity_login_layout);
        ButterKnife.bind(this);
    }


    @Override
    public void onMsgSearchComplete(NetworkParam param) {
        super.onMsgSearchComplete(param);

    }


    @Override
    public void onBackPressed() {

        ListDialog dialog = null;
        if (dialog == null) {
            dialog = new ListDialog(getContext(), R.style.loading_dialog);
        }
        dialog.show();
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
//        exitBy2Click();
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
