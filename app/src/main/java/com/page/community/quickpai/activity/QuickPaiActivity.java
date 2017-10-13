package com.page.community.quickpai.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.daimajia.swipe.util.Attributes;
import com.framework.activity.BaseActivity;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.utils.ArrayUtils;
import com.framework.view.LineDecoration;
import com.framework.view.pull.SwipRefreshLayout;
import com.page.community.quickpai.holder.RecyclerViewAdapter;
import com.page.home.model.QpListParam;
import com.page.home.model.QpListResult;
import com.page.home.model.QpListResult.Data.Snapshots;
import com.qfant.wuye.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shucheng.qu on 2017/8/9.
 */

public class QuickPaiActivity extends BaseActivity implements SwipRefreshLayout.OnRefreshListener {

    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.spl_refresh)
    SwipRefreshLayout refreshLayout;
    private RecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_quickpai_layout);
        ButterKnife.bind(this);
        setTitleBar("我的随手拍", true);
        setListView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startRequest(1);
    }

    private void startRequest(int pager) {
        QpListParam param = new QpListParam();
        param.pageNo = pager;
        Request.startRequest(param, pager, ServiceMap.getMySnapshots, mHandler);
    }

    private void setListView() {
        ArrayList<Snapshots> list = new ArrayList<>();
        mAdapter = new RecyclerViewAdapter(this, list);
        mAdapter.setMode(Attributes.Mode.Single);
        rvList.addItemDecoration(new LineDecoration(this));
        rvList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvList.setAdapter(mAdapter);
        refreshLayout.setOnRefreshListener(this);
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key.equals(ServiceMap.getMySnapshots)) {
            QpListResult serveResult = (QpListResult) param.result;
            if (serveResult != null && serveResult.data != null && !ArrayUtils.isEmpty(serveResult.data.snapshots)) {
                if ((int) param.ext == 1) {
                    mAdapter.setData(serveResult.data.snapshots);
                } else {
                    mAdapter.addData(serveResult.data.snapshots);
                }
            } else {
                showToast("没有更多了");
            }
            refreshLayout.setRefreshing(false);
        }else if(param.key == ServiceMap.deleteSnapshot){
            mAdapter.onMsgSearchComplete(param);
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
