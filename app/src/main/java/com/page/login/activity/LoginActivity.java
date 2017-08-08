package com.page.login.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;


import com.framework.view.ListDialog;
import com.haolb.client.R;
import com.haolb.client.activity.BaseActivity;

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
        setContentView(R.layout.pub_activity_login_layout);
        ButterKnife.bind(this);
    }


    @Override
    public void onBackPressed() {

        ListDialog dialog = null;
        if (dialog == null) {
            dialog = new ListDialog(getContext(), R.style.list_dialog_style);
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
