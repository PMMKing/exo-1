package com.page.community.quickpain.holder;

import android.content.Context;
import android.view.View;

import com.framework.rvadapter.holder.BaseViewHolder;
import com.page.community.quickpain.model.ScommentsReault.Data.Datas;
import com.page.community.quickpain.view.HeadderView;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shucheng.qu on 2017/8/11.
 */

public class HeaderHolder extends BaseViewHolder<Datas> {

    @BindView(R.id.headerview)
    HeadderView headerview;

    public HeaderHolder(Context context, View itemView) {
        super(context, itemView);
//        R.layout.pub_activity_quick_headerview_layout;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, Datas data, int position) {
        headerview.updataView(data.hearderData);
    }
}
