package com.page.community.details.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.framework.activity.BaseActivity;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.utils.TextViewUtils;
import com.framework.view.AddView;
import com.page.community.details.model.RepairDetailParam;
import com.page.community.details.model.RepairDetailResult;
import com.page.community.details.model.RepairDetailResult.Data;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by shucheng.qu on 2017/8/12.
 */

public class DetailsActivity extends BaseActivity {

    public static String ID = "id";
    @BindView(R.id.addView)
    AddView addView;
    @BindView(R.id.tv_repair_mame)
    TextView tvRepairMame;
    @BindView(R.id.ll_repair_name)
    LinearLayout llRepairName;
    @BindView(R.id.tv_repair_address)
    TextView tvRepairAddress;
    @BindView(R.id.ll_repair_address)
    LinearLayout llRepairAddress;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_accendant_name)
    TextView tvAccendantName;
    @BindView(R.id.ll_accendant_name)
    LinearLayout llAccendantName;
    @BindView(R.id.line_accendant_name)
    View lineAccendantName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.ll_phone)
    LinearLayout llPhone;
    @BindView(R.id.line_phone)
    View linePhone;
    @BindView(R.id.cb_evaluate_ok)
    CheckBox cbEvaluateOk;
    @BindView(R.id.cb_evaluate_no)
    CheckBox cbEvaluateNo;
    @BindView(R.id.ll_evaluate)
    LinearLayout llEvaluate;
    @BindView(R.id.et_no_cause)
    EditText etNoCause;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_details_layout);
        ButterKnife.bind(this);
        if (myBundle == null) {
            finish();
        }
        setTitleBar("维修详情", true);
        id = myBundle.getString(ID);
        startRequest();
        addView.setClickable(false);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        myBundle.putString(ID, id);
    }

    private void startRequest() {
        RepairDetailParam param = new RepairDetailParam();
        param.id = id;
        Request.startRequest(param, ServiceMap.getRepair, mHandler, Request.RequestFeature.BLOCK);
    }

    private void updatView(Data data) {
        addView.setAddNumber(new String[]{data.pic});
        tvRepairAddress.setText(data.address);
        tvRepairMame.setText(data.intro);
        tvStatus.setText(getState(data.status));
        if (TextUtils.isEmpty(data.workername)) {
            llAccendantName.setVisibility(View.GONE);
        } else {
            llAccendantName.setVisibility(View.VISIBLE);
            tvAccendantName.setText(data.workername);
        }
        if (TextUtils.isEmpty(data.workerphone)) {
            llPhone.setVisibility(View.GONE);
        } else {
            llPhone.setVisibility(View.VISIBLE);
            tvPhone.setText(data.workerphone);
        }


    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.getRepair) {
            RepairDetailResult result = (RepairDetailResult) param.result;
            if (result != null && result.data != null) {
                updatView(result.data);
            }
        }
        return false;
    }

    @OnClick(R.id.tv_commit)
    public void onViewClicked() {

    }


    /*
    * 1 未处理 2 正在派单 3派单完成 4已接单 5维修中 6已完成 7已评价
    * // 1 未处理 2 正在派单 3派单完成 4已接单 5维修中 6已完成 7已评价
    *
    * */

    private CharSequence getState(int state) {

        String temp = "状态：";
        int color = getContext().getResources().getColor(R.color.pub_color_gray_666);

        switch (state) {
            case 1:
                temp += "未处理";
                color = getContext().getResources().getColor(R.color.pub_color_gray_666);
                break;
            case 2:
                temp += "正在派单";
                color = getContext().getResources().getColor(R.color.pub_color_red);
                break;
            case 3:
                temp += "派单完成";
                color = getContext().getResources().getColor(R.color.pub_color_gray_666);
                break;
            case 4:
                temp += "已接单";
                color = getContext().getResources().getColor(R.color.pub_color_yellow);
                break;
            case 5:
                temp += "维修中";
                color = getContext().getResources().getColor(R.color.pub_color_green_7ed321);
                break;
            case 6:
                temp += "已完成";
                color = getContext().getResources().getColor(R.color.pub_color_blue);
                break;
            case 7:
                temp += "已评价";
                color = getContext().getResources().getColor(R.color.pub_color_gray_666);
                break;
            default:
                temp += "订单异常";
                break;
        }
        return TextViewUtils.genericColorfulText(temp, color, new int[]{3, temp.length() - 1});
    }

}
