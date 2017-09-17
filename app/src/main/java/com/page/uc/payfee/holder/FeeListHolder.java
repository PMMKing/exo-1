package com.page.uc.payfee.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.utils.ArrayUtils;
import com.framework.utils.DateFormatUtils;
import com.page.uc.payfee.model.FeeListResult;
import com.page.uc.payfee.model.FeeListResult.Data.DatasX.Datas;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by shucheng.qu on 2017/9/17.
 */

public class FeeListHolder extends BaseViewHolder<FeeListResult.Data.DatasX> {

    @BindView(R.id.tv_order_no)
    TextView tvOrderNo;
    @BindView(R.id.tv_pay_state)
    TextView tvPayState;
    @BindView(R.id.tv_pay_time)
    TextView tvPayTime;
    @BindView(R.id.ll_item)
    LinearLayout llItem;
    @BindView(R.id.ll_content)
    LinearLayout llContent;

    public FeeListHolder(Context context, View itemView) {
        super(context, itemView);
//        R.layout.pub_fragment_feelist_item_layout;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, FeeListResult.Data.DatasX data, int position) {
        if (data == null) return;
        tvOrderNo.setText(data.orderno);
        tvPayState.setText(data.paystatus);
        tvPayTime.setText(data.paytime);
        llItem.removeAllViews();
        if (!ArrayUtils.isEmpty(data.datas)) {
            for (Datas item : data.datas) {
                View itemView = LayoutInflater.from(mContext).inflate(R.layout.pub_fragment_feelist_item_item_layout, null, false);
                TextView tvTime = (TextView) itemView.findViewById(R.id.tv_time);
                TextView tvPrice = (TextView) itemView.findViewById(R.id.tv_price);
                tvTime.setText(String.format("%s - %s 物业费", DateFormatUtils.format(item.startdate, "yyyy.MM"), DateFormatUtils.format(item.enddate, "yyyy.MM")));
                tvPrice.setText(item.price + "");
                llItem.addView(itemView);
            }
        }
        llContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (llItem.getVisibility() == View.VISIBLE) {
                    llItem.setVisibility(View.GONE);
                } else {
                    llItem.setVisibility(View.VISIBLE);
                }

            }
        });
    }

}
