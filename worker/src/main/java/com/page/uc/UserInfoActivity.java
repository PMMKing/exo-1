package com.page.uc;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.framework.activity.BaseActivity;
import com.framework.view.CircleImageView;
import com.framework.view.IFView;
import com.haolb.client.R;
import com.page.login.UCUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chenxi.cui on 2017/8/13.
 */

public class UserInfoActivity extends BaseActivity {
    @BindView(R.id.text_back)
    IFView textBack;
    @BindView(R.id.image_head)
    CircleImageView imageHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;

    @BindView(R.id.btn_logout)
    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_layout);
        ButterKnife.bind(this);
        tvName.setText(UCUtils.getInstance().getUserInfo().nickname);
        tvPhone.setText(UCUtils.getInstance().getUserInfo().phone);
    }

    @OnClick({R.id.text_back, R.id.btn_logout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_back:
                finish();
                break;
            case R.id.btn_logout:
                UCUtils.getInstance().saveUserInfo(null);
                finish();
                break;
        }
    }
}
