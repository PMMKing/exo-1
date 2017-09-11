package com.page.store.classify.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.utils.imageload.ImageLoad;
import com.page.store.classify.model.ClassifyResult;
import com.page.store.classify.model.ClassifyResult.Data.Datas.Produts;
import com.page.store.orderaffirm.model.CommitOrderParam;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shucheng.qu on 2017/8/15.
 */

public class ProHolder extends BaseViewHolder<Produts> {

    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.tv_name)
    TextView tvName;

    public ProHolder(Context context, View itemView) {
        super(context, itemView);
//        R.layout.pub_activity_classify_right_item_layout;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, Produts data, int position) {
        if (data == null) return;
        ImageLoad.loadPlaceholder(mContext, data.pic1, ivImage);
        tvName.setText(data.name);
    }
}
