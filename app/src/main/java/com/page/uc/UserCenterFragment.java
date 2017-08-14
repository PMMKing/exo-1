package com.page.uc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.haolb.client.R;
import com.haolb.client.activity.BaseFragment;
import com.haolb.client.view.CircleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by chenxi.cui on 2017/8/13.
 */

public class UserCenterFragment extends BaseFragment {
    @BindView(R.id.image_head)
    CircleImageView imageHead;
    @BindView(R.id.image_setting)
    ImageView imageSetting;
    @BindView(R.id.ll_order_0)
    LinearLayout llOrder0;
    @BindView(R.id.ll_order_1)
    LinearLayout llOrder1;
    @BindView(R.id.ll_order_2)
    LinearLayout llOrder2;
    @BindView(R.id.ll_order_3)
    LinearLayout llOrder3;
    @BindView(R.id.ll_list_0)
    LinearLayout llList0;
    @BindView(R.id.ll_list_1)
    LinearLayout llList1;
    @BindView(R.id.ll_list_2)
    LinearLayout llList2;
    @BindView(R.id.ll_list_3)
    LinearLayout llList3;
    @BindView(R.id.ll_list_4)
    LinearLayout llList4;
    @BindView(R.id.ll_list_5)
    LinearLayout llList5;
    @BindView(R.id.ll_list_6)
    LinearLayout llList6;
    @BindView(R.id.ll_list_7)
    LinearLayout llList7;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pub_fragment_user_center_layout, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.image_head, R.id.image_setting, R.id.ll_order_0, R.id.ll_order_1, R.id.ll_order_2, R.id.ll_order_3, R.id.ll_list_0, R.id.ll_list_1, R.id.ll_list_2, R.id.ll_list_3, R.id.ll_list_4, R.id.ll_list_5, R.id.ll_list_6, R.id.ll_list_7})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_head:
                showToast("头像");
                qStartActivity(UserInfoActivity.class);
                break;
            case R.id.image_setting:
                break;
            case R.id.ll_order_0:
                break;
            case R.id.ll_order_1:
                break;
            case R.id.ll_order_2:
                break;
            case R.id.ll_order_3:
                break;
            case R.id.ll_list_0:
                break;
            case R.id.ll_list_1:
                break;
            case R.id.ll_list_2:
                //收货地址
                qStartActivity(AddressActivity.class);
                break;
            case R.id.ll_list_3:
                break;
            case R.id.ll_list_4:
                break;
            case R.id.ll_list_5:
                break;
            case R.id.ll_list_6:
                break;
            case R.id.ll_list_7:
                break;
        }
    }
}