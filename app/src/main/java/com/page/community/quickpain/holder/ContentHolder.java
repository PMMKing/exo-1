package com.page.community.quickpain.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.view.IFView;
import com.page.community.quickpain.model.ScommentsReault.Data.Datas;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by shucheng.qu on 2017/8/11.
 */

public class ContentHolder extends BaseViewHolder<Datas> {


    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_zan)
    IFView tvZan;

    public ContentHolder(Context context, View itemView) {
        super(context, itemView);
//        R.layout.pub_activity_quickpain_item_layout
        ButterKnife.bind(this, itemView);
    }


    @Override
    public void onBindViewHolder(BaseViewHolder holder, Datas data, int position) {
        if(data == null)return;
        tvName.setText(data.nickname);
        tvContent.setText(data.content);

    }

    @OnClick(R.id.tv_zan)
    public void onViewClicked() {
    }
}
