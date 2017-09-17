package com.page.community.serve.holder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.utils.TextViewUtils;
import com.framework.utils.imageload.ImageLoad;
import com.page.community.serve.model.RepairResult;
import com.page.community.serve.model.RepairResult.Data.RepairList;
import com.page.community.serve.model.ServeResult.Data.WaterList;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by shucheng.qu on 2017/8/23.
 */

public class RepairHolder extends BaseViewHolder<RepairList> {

    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.btn_call)
    ImageView btnCall;
    private RepairList data;

    public RepairHolder(Context context, View itemView) {
        super(context, itemView);
//        R.layout.pub_activity_gowater_item_layout;
        ButterKnife.bind(this, itemView);
    }


    @Override
    public void onBindViewHolder(BaseViewHolder holder, RepairList data, int position) {
        this.data = data;
        if (data != null) {
            title.setText(data.intro);
            tvContent.setText(data.address);
            tvState.setText(getState(data.status));
            ImageLoad.loadPlaceholder(mContext, data.pic, ivImage);
        }
    }

    /*
    * 1 未处理 2 正在派单 3派单完成 4已接单 5维修中 6已完成 7已评价
    *
    * */

    private CharSequence getState(int state) {

        String temp = "状态：";
        int color = mContext.getResources().getColor(R.color.pub_color_gray_666);

        switch (state) {
            case 1:
                temp += "未处理";
                color = mContext.getResources().getColor(R.color.pub_color_gray_666);
                break;
            case 2:
                temp += "正在派单";
                color = mContext.getResources().getColor(R.color.pub_color_red);
                break;
            case 3:
                temp += "派单完成";
                color = mContext.getResources().getColor(R.color.pub_color_gray_666);
                break;
            case 4:
                temp += "已接单";
                color = mContext.getResources().getColor(R.color.pub_color_yellow);
                break;
            case 5:
                temp += "维修中";
                color = mContext.getResources().getColor(R.color.pub_color_green_7ed321);
                break;
            case 6:
                temp += "已完成";
                color = mContext.getResources().getColor(R.color.pub_color_blue);
                break;
            case 7:
                temp += "已评价";
                color = mContext.getResources().getColor(R.color.pub_color_gray_666);
                break;
            default:
                temp += "订单异常";
                break;
        }
        return TextViewUtils.genericColorfulText(temp, color, new int[]{3, temp.length()});
    }


    @OnClick(R.id.btn_call)
    public void onViewClicked() {
//        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + data.phone));
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        mContext.startActivity(intent);
    }
}
