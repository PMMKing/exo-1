package com.page.community.eventlist.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framework.activity.BaseActivity;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.rvadapter.adapter.MultiAdapter;
import com.framework.rvadapter.click.OnItemClickListener;
import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.rvadapter.manage.ITypeView;
import com.framework.utils.ArrayUtils;
import com.framework.view.LineDecoration;
import com.framework.view.pull.SwipRefreshLayout;
import com.haolb.client.R;
import com.page.community.event.activity.EventActivity;
import com.page.community.eventdetails.activity.EventDetailActivity;
import com.page.community.eventlist.holder.ViewHolder;
import com.page.community.eventlist.model.EventListParam;
import com.page.community.eventlist.model.EventListResult;
import com.page.community.eventlist.model.EventListResult.Data.ActivityList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shucheng.qu on 2017/8/9.
 */

public class EventListActivity extends BaseActivity implements OnItemClickListener<ActivityList>, SwipRefreshLayout.OnRefreshListener {


    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.srl_down_refresh)
    SwipRefreshLayout srlDownRefresh;
    private MultiAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_eventlist_layout);
        ButterKnife.bind(this);
        setTitleBar("活动列表", true, "+", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qStartActivity(EventActivity.class);
            }
        });
        setListView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startRequest(1);
    }

    private void setListView() {
        adapter = new MultiAdapter<ActivityList>(getContext()).addTypeView(new ITypeView<ActivityList>() {
            @Override
            public boolean isForViewType(ActivityList item, int position) {
                return true;
            }

            @Override
            public BaseViewHolder createViewHolder(Context mContext, ViewGroup parent) {
                return new ViewHolder(mContext, LayoutInflater.from(mContext).inflate(R.layout.pub_activity_eventlist_item_layout, parent, false));
            }
        });
        rvList.addItemDecoration(new LineDecoration(this));
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvList.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        srlDownRefresh.setOnRefreshListener(this);
    }

    private void startRequest(int page) {
        EventListParam param = new EventListParam();
        param.pageNo = page;
        Request.startRequest(param, page, ServiceMap.getActivityList, mHandler);
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.getActivityList) {
            EventListResult result = (EventListResult) param.result;
            if (result != null && result.data != null && !ArrayUtils.isEmpty(result.data.activityList)) {
                if ((int) param.ext == 1) {
                    adapter.setData(result.data.activityList);
                } else {
                    adapter.addData(result.data.activityList);
                }
            } else {
                showToast("没有更多了");
            }
            srlDownRefresh.setRefreshing(false);
        }
        return false;
    }

    @Override
    public void onItemClickListener(View view, ActivityList data, int position) {

        qStartActivity(EventDetailActivity.class);
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
