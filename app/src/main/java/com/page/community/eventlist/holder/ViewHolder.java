package com.page.community.eventlist.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.utils.cache.ImageLoader;
import com.framework.utils.imageload.ImageLoad;
import com.page.community.eventlist.model.EventListResult.Data.ActivityList;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static java.security.AccessController.getContext;

/**
 * Created by shucheng.qu on 2017/8/9.
 */

public class ViewHolder extends BaseViewHolder<ActivityList> {

    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_initiator)
    TextView tvInitiator;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.iv_call)
    ImageView ivCall;

    public ViewHolder(Context context, View itemView) {
        super(context, itemView);
//        R.layout.pub_activity_eventlist_item_layout;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, ActivityList data, int position) {
        tvTitle.setText(data.title);
        tvInitiator.setText("发起人：" + data.place);
        tvNumber.setText("参与人数：" + data.persons);
        ImageLoad.load(mContext, data.pic, ivImage);
    }

}
