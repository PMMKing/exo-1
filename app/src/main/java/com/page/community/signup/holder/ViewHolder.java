package com.page.community.signup.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.framework.rvadapter.holder.BaseViewHolder;
import com.page.community.signup.model.SignUpResult.Data.Datas;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shucheng.qu on base2017/8/11.
 */

public class ViewHolder extends BaseViewHolder<Datas> {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;

    public ViewHolder(Context context, View itemView) {
        super(context, itemView);
//        R.layout.pub_activity_signup_item_layout;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, Datas data, int position) {
        if (data == null) return;
        tvName.setText(data.nickname);
        tvPhone.setText(data.phone);
    }
}
