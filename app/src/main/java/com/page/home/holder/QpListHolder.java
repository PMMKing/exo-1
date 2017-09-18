package com.page.home.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.utils.DateFormatUtils;
import com.framework.utils.cache.ImageLoader;
import com.framework.utils.imageload.ImageLoad;
import com.qfant.wuye.R;
import com.page.home.model.QpListResult;
import com.page.home.model.QpListResult.Data.Snapshots;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shucheng.qu on 2017/8/30.
 */

public class QpListHolder extends BaseViewHolder<Snapshots> {

    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;

    public QpListHolder(Context context, View itemView) {
        super(context, itemView);
//        R.layout.pub_fragment_qplist_item_layout;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, Snapshots data, int position) {
        tvTitle.setText(data.intro);
        tvTime.setText("发布时间：" + DateFormatUtils.format(data.createtime, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd"));
//        ImageLoad.loadPlaceholder(mContext, data.pic1, ivImage);
        ImageLoader.getInstance(mContext).loadImage(data.pic1, ivImage);
    }
}
