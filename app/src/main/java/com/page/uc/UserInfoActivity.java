package com.page.uc;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.framework.activity.BaseActivity;
import com.framework.domain.param.BaseParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.view.CircleImageView;
import com.haolb.client.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chenxi.cui on 2017/8/13.
 */

public class UserInfoActivity extends BaseActivity {
    @BindView(R.id.image_head)
    CircleImageView imageHead;
    @BindView(R.id.ll_head)
    LinearLayout llHead;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.ll_nickname)
    LinearLayout llNickname;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.ll_sex)
    LinearLayout llSex;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.ll_phone)
    LinearLayout llPhone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_layout);
        ButterKnife.bind(this);
        setTitleBar("用户信息", true);
    }

    @OnClick({R.id.ll_head, R.id.ll_nickname, R.id.ll_sex, R.id.ll_phone,R.id.btn_logout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_head:
                showToast("头像");
                break;
            case R.id.ll_nickname:
                break;
            case R.id.ll_sex:
                break;
            case R.id.ll_phone:
                break;
            case R.id.btn_logout:
                UCUtils.getInstance().saveUserInfo(null);
//                Request.startRequest(new BaseParam(), ServiceMap.customerLogout, mHandler);
                break;
        }
    }


}
