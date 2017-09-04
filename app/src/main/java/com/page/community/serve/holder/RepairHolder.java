package com.page.community.serve.holder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.framework.rvadapter.holder.BaseViewHolder;
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
            tvContent.setText("地址：" + data.address);
            tvState.setText("状态：" + data.status);
        }
    }

    /*
    * 1 未处理 2 正在派单 3派单完成 4已接单 5维修中 6已完成 7已评价
    *
    * */


    


    @OnClick(R.id.btn_call)
    public void onViewClicked() {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + data.phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }
}
