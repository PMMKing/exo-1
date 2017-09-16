package com.page.home.activity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.framework.adapter.utils.QSimpleAdapter;
import com.framework.utils.cache.ImageLoader;
import com.haolb.client.R;
import com.page.home.WorkerRepairResult;

/**
 * Created by chenxi.cui on 2017/9/12.
 */

public class HomeAdapter extends QSimpleAdapter<WorkerRepairResult.repair> {
    private int type;

    public HomeAdapter(Context context) {
        super(context);
    }

    @Override
    protected View newView(Context context, ViewGroup parent) {
        return inflate(R.layout.home_item, null, false);
    }

    @Override
    protected void bindView(View view, Context context, WorkerRepairResult.repair item, int position) {
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        TextView tvInfo = (TextView) view.findViewById(R.id.tv_info);
        TextView tvAddress = (TextView) view.findViewById(R.id.tv_address);
        TextView btnDetail = (TextView) view.findViewById(R.id.btn_detail);
        ImageLoader.getInstance(context).loadImage(item.pic, imageView,R.drawable.moren);
        tvAddress.setText(item.address);
        tvInfo.setText(item.intro);
        btnDetail.setText(type == 0 ? "接单" : "详情");
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
