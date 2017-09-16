package com.page.uc;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.framework.utils.imageload.ImageLoad;
import com.page.store.collect.activity.CollectActivity;
import com.page.uc.payfee.activity.WaitPayFeeActivity;
import com.qfant.wuye.R;
import com.framework.activity.BaseFragment;
import com.framework.view.CircleImageView;
import com.page.community.applyfor.activity.ApplyForActivity;
import com.page.store.orderdetails.activity.OrderDetailsActivity;
import com.page.address.activity.AddressActivity;
import com.page.store.orderlist.activity.OrderListActivity;
import com.page.uc.bean.LoginResult;

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
    @BindView(R.id.tv_mycomm)
    TextView tvMyComm;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pub_fragment_user_center_layout, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
//        imageHead.setImageResource(R.drawable.ic_launcher);
        LoginResult.LoginData instance = UCUtils.getInstance().getUserInfo();
        ImageLoad.loadPlaceholder(getContext(), instance.portrait, imageHead);
//        tvMyComm.setText(instance.nickname);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.image_head, R.id.image_setting, R.id.ll_order_0, R.id.ll_order_1, R.id.ll_order_2, R.id.ll_order_3, R.id.ll_list_0, R.id.ll_list_1, R.id.ll_list_2, R.id.ll_list_3, R.id.ll_list_4, R.id.ll_list_5, R.id.ll_list_6, R.id.ll_list_7})
    public void onClick(View view) {

        Bundle bundle = new Bundle();

        switch (view.getId()) {
            case R.id.image_head:
                if (UCUtils.getInstance().isLogin()) {
                    qStartActivity(UserInfoActivity.class);
                } else {
                    qStartActivity(LoginActivity.class);
                }
                break;
            case R.id.image_setting:
                qStartActivity(OrderDetailsActivity.class);
                break;
            case R.id.ll_order_0:
                bundle.putInt("index", 1);
                qStartActivity(OrderListActivity.class, bundle);
                break;
            case R.id.ll_order_1:
                bundle.putInt("index", 2);
                qStartActivity(OrderListActivity.class, bundle);
                break;
            case R.id.ll_order_2:
                bundle.putInt("index", 3);
                qStartActivity(OrderListActivity.class, bundle);
                break;
            case R.id.ll_order_3:
                bundle.putInt("index", 4);
                qStartActivity(OrderListActivity.class, bundle);
                break;
            case R.id.ll_list_0:
                qStartActivity(OrderListActivity.class);
                break;
            case R.id.ll_list_1:
                qStartActivity(CollectActivity.class);
                break;
            case R.id.ll_list_2:
                //收货地址
                bundle.putBoolean("isSelect", false);
                qStartActivity(AddressActivity.class, bundle);
                break;
            case R.id.ll_list_3:
                qStartActivity(SelectComActivity.class);
                break;
            case R.id.ll_list_4:
                qStartActivity(WaitPayFeeActivity.class);
                break;
            case R.id.ll_list_5:
                qStartActivity(ApplyForActivity.class);
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
                qStartActivity(AboutUsActivity.class);
//                UWXPageManger.openPage(getContext(), "aboutUs.js");
                break;
        }
    }
}
