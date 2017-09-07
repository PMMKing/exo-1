package com.page.community.quickpai.holder;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.framework.activity.BaseActivity;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.utils.DateFormatUtils;
import com.framework.utils.imageload.ImageLoad;
import com.page.community.quickpai.activity.AddQPaiActivity;
import com.page.community.quickpai.model.DelQpParam;
import com.page.home.model.QpListResult;
import com.page.home.model.QpListResult.Data.Snapshots;
import com.qfant.wuye.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerSwipeAdapter<RecyclerViewAdapter.SimpleViewHolder> {


    public static class SimpleViewHolder extends RecyclerView.ViewHolder implements Serializable {
        SwipeLayout swipeLayout;
        LinearLayout llHide;
        LinearLayout llShow;
        private final TextView tvEdit;
        private final TextView tvDel;
        private final TextView tvTitle;
        private final TextView tvTime;
        private final ImageView ivImage;

        public SimpleViewHolder(final View itemView) {
            super(itemView);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.sl_content);
            llHide = (LinearLayout) itemView.findViewById(R.id.ll_hide);
            llShow = (LinearLayout) itemView.findViewById(R.id.ll_show);
            tvEdit = (TextView) itemView.findViewById(R.id.tv_edit);
            tvDel = (TextView) itemView.findViewById(R.id.tv_del);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            ivImage = (ImageView) itemView.findViewById(R.id.iv_image);
        }
    }

    private Context mContext;
    private ArrayList<Snapshots> mDataset;

    public RecyclerViewAdapter(Context context, ArrayList<Snapshots> objects) {
        this.mContext = context;
        this.mDataset = objects;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pub_activity_quickpai_item_layout, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int position) {
        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        final Snapshots snapshots = mDataset.get(position);
        viewHolder.tvTitle.setText(snapshots.intro);
        viewHolder.tvTime.setText("发布时间：" + DateFormatUtils.format(snapshots.createtime, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd"));
        ImageLoad.loadPlaceholder(mContext, snapshots.pic1, viewHolder.ivImage);
        viewHolder.tvDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//删除
                DelQpParam param = new DelQpParam();
                param.id = snapshots.id;
                viewHolder.tvTitle.setTag(position);
                Request.startRequest(param, viewHolder, ServiceMap.deleteSnapshot, ((BaseActivity) mContext).mHandler, Request.RequestFeature.BLOCK);
            }
        });
        viewHolder.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(AddQPaiActivity.SNAPSHOTS, snapshots);
                ((BaseActivity) mContext).qStartActivity(AddQPaiActivity.class, bundle);
                mItemManger.closeAllItems();
            }
        });
        mItemManger.bindView(viewHolder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.sl_content;
    }

    public void setData(List<Snapshots> snapshots) {
        mDataset.clear();
        mDataset.addAll(snapshots);
        notifyDataSetChanged();
    }

    public void addData(List<Snapshots> snapshots) {
        this.mDataset.addAll(snapshots);
        notifyDataSetChanged();
    }


    public void onMsgSearchComplete(NetworkParam param) {
        if (param.result.bstatus.code == 0) {
            SimpleViewHolder viewHolder = (SimpleViewHolder) param.ext;
            int position = (int) viewHolder.tvTitle.getTag();
            mItemManger.removeShownLayouts(viewHolder.swipeLayout);
            mDataset.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, mDataset.size());
            mItemManger.closeAllItems();
        } else {
            ((BaseActivity) mContext).showToast(param.result.bstatus.des);
        }
    }

}