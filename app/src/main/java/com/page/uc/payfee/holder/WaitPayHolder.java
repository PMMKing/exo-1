package com.page.uc.payfee.holder;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.utils.DateFormatUtils;
import com.page.uc.payfee.model.WaitFeeResult;
import com.page.uc.payfee.model.WaitFeeResult.Data.Datas;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shucheng.qu on 2017/9/14.
 */

public class WaitPayHolder extends BaseViewHolder<Datas> {

    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.cb_select)
    CheckBox cbSelect;

    public WaitPayHolder(Context context, View itemView) {
        super(context, itemView);
//        R.layout.pub_activity_waitpayfee_item_layout;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, Datas data, int position) {
        if (data == null) return;
        tvTime.setText(String.format("%s - %s 物业费", DateFormatUtils.format(data.startdate, "yyyy.MM.dd"), DateFormatUtils.format(data.enddate, "yyyy.MM.dd")));
        tvPrice.setText(data.price + "");
        cbSelect.setChecked(data.isSelect);
    }
}
