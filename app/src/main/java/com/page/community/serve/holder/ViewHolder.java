package com.page.community.serve.holder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.utils.cache.ImageLoader;
import com.framework.utils.imageload.ImageLoad;
import com.page.community.serve.model.ServeResult;
import com.page.community.serve.model.ServeResult.Data.WaterList;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by shucheng.qu on 2017/8/23.
 */

public class ViewHolder extends BaseViewHolder<WaterList> {

    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.btn_call)
    ImageView btnCall;
    private WaterList data;

    public ViewHolder(Context context, View itemView) {
        super(context, itemView);
//        R.layout.pub_activity_gowater_item_layout;
        ButterKnife.bind(this, itemView);
    }


    @Override
    public void onBindViewHolder(BaseViewHolder holder, WaterList data, int position) {
        this.data = data;
        if (data != null) {
            title.setText(data.title);
            tvContent.setText("联系电话：" + data.phone);
            tvState.setText("联系地址：" + data.address);
            ImageLoader.getInstance(mContext).loadImage(data.pic, ivImage, R.drawable.moren);
        }
    }

    @OnClick(R.id.btn_call)
    public void onViewClicked() {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + data.phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }
}
