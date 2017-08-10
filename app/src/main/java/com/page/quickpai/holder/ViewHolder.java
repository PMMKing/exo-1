package com.page.quickpai.holder;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.daimajia.swipe.SwipeLayout;
import com.framework.rvadapter.holder.BaseViewHolder;
import com.haolb.client.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shucheng.qu on 2017/8/10.
 */

public class ViewHolder extends BaseViewHolder implements SwipeLayout.SwipeListener {
    @BindView(R.id.ll_hide)
    LinearLayout llHide;
    @BindView(R.id.ll_show)
    LinearLayout llShow;
    @BindView(R.id.sl_content)
    SwipeLayout slContent;

    public ViewHolder(Context context, View itemView) {
        super(context, itemView);
//        R.layout.pub_activity_quickpai_item_layout;
        ButterKnife.bind(this, itemView);
        slContent.setShowMode(SwipeLayout.ShowMode.PullOut);
        slContent.addDrag(SwipeLayout.DragEdge.Left, llHide);
        slContent.addSwipeListener(this);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, Object data, int position) {

    }

    @Override
    public void onStartOpen(SwipeLayout layout) {

    }

    @Override
    public void onOpen(SwipeLayout layout) {

    }

    @Override
    public void onStartClose(SwipeLayout layout) {

    }

    @Override
    public void onClose(SwipeLayout layout) {

    }

    @Override
    public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

    }

    @Override
    public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

    }
}
