package com.page.uc;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.haolb.client.R;
import com.framework.activity.BaseFragment;
import com.framework.view.CircleImageView;
import com.page.address.activity.AddressActivity;
import com.page.orderdetails.activity.OrderDetailsActivity;

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
                if (UCUtils.getInstance().isLogin()) {
                    qStartActivity(UserInfoActivity.class);
                }else {
                    qStartActivity(LoginActivity.class);
                }
                break;
            case R.id.image_setting:
                qStartActivity(OrderDetailsActivity.class);
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
                qStartActivity(SelectComActivity.class);
                break;
            case R.id.ll_list_4:
                break;
            case R.id.ll_list_5:
                break;
            case R.id.ll_list_6:
                final String phone = "10086";
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setMessage("联系客服：" + phone).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        processAgentPhoneCall(phone);
                        dialog.dismiss();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();

                break;
            case R.id.ll_list_7:
                break;
        }
    }
}
