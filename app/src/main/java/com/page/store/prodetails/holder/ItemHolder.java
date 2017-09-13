package com.page.store.prodetails.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.framework.rvadapter.holder.BaseViewHolder;
import com.page.store.prodetails.model.PEResult;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shucheng.qu on 2017/8/16.
 */

public class ItemHolder extends BaseViewHolder<PEResult.Evaluate> {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_content)
    TextView tvContent;

    public ItemHolder(Context context, View itemView) {
        super(context, itemView);
//        R.layout.pub_activity_prodetails_item_layout;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, PEResult.Evaluate data, int position) {
        if (data == null) return;
        tvName.setText(data.nickname);
        tvContent.setText(data.content);
        tvTime.setText(data.createtime);
    }
}
