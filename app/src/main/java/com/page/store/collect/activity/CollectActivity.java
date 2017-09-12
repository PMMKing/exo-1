package com.page.store.collect.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.framework.activity.BaseActivity;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.rvadapter.adapter.MultiAdapter;
import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.rvadapter.manage.ITypeView;
import com.framework.view.LineDecoration;
import com.framework.view.pull.SwipRefreshLayout;
import com.page.community.quickpain.holder.ContentHolder;
import com.page.store.collect.model.CollectParam;
import com.qfant.wuye.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shucheng.qu on 2017/8/18.
 */

public class CollectActivity extends BaseActivity implements SwipRefreshLayout.OnRefreshListener {

    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.refreshLayout)
    SwipRefreshLayout refreshLayout;
    private MultiAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_collect_layout);
        ButterKnife.bind(this);
        setTitleBar("我的收藏", true);
        setListView();
        startRequest(1);
    }

    private void startRequest(int pager) {
        CollectParam param = new CollectParam();
        param.pageNo = pager;
        param.pageSize = 7;
        Request.startRequest(param, pager, ServiceMap.getFavList, mHandler);
    }

    private void setListView() {
        adapter = new MultiAdapter(getContext()).addTypeView(new ITypeView() {
            @Override
            public boolean isForViewType(Object item, int position) {
                return true;
            }

            @Override
            public BaseViewHolder createViewHolder(Context mContext, ViewGroup parent) {
                return new ContentHolder(mContext, LayoutInflater.from(mContext).inflate(R.layout.pub_activity_collect_item_layout, parent, false));
            }
        });
        rvList.addItemDecoration(new LineDecoration(getContext()));
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvList.setHasFixedSize(true);
        rvList.setAdapter(adapter);
        refreshLayout.setOnRefreshListener(this);
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.getFavList) {

        }
        return false;
    }

    @Override
    public void onRefresh(int index) {
        startRequest(1);
    }

    @Override
    public void onLoad(int index) {
        startRequest(++index);
    }
}
