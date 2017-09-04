package com.page.home.holder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.utils.Dimen;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shucheng.qu on 2017/8/18.
 */

public class Shopping12Holder extends BaseViewHolder {

    private final int widthPixels;
    @BindView(R.id.tv_name)
    TextView tvName;

    public Shopping12Holder(Context context, View itemView) {
        super(context, itemView);
        ButterKnife.bind(this, itemView);
//        R.layout.pub_fragment_shopping_12_layout;
        widthPixels = context.getResources().getDisplayMetrics().widthPixels;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, Object data, int position) {
        ViewGroup.LayoutParams layoutParams = getConvertView().getLayoutParams();
        layoutParams.width = (widthPixels - Dimen.dpToPx(30)) / 2;
        layoutParams.height = Dimen.dpToPx(120);
        getConvertView().setLayoutParams(layoutParams);
    }
}
