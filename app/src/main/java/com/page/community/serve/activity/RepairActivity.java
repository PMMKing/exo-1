package com.page.community.serve.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.page.community.applyfor.activity.ApplyForActivity;
import com.page.community.details.activity.DetailsActivity;
import com.page.community.serve.holder.RepairHolder;
import com.page.community.serve.holder.ViewHolder;
import com.page.community.serve.model.RepairResult;
import com.page.community.serve.model.RepairResult.Data.RepairList;
import com.page.community.serve.model.ServeParam;
import com.page.community.serve.model.ServeResult;
import com.page.community.serve.model.ServeResult.Data.WaterList;
import com.qfant.wuye.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shucheng.qu on 2017/8/24.
 */

public class RepairActivity extends BaseActivity implements OnItemClickListener<RepairList>, SwipRefreshLayout.OnRefreshListener {

    public static String TITLE = "title";
    public static String SERVICEMAP = "serviceMap";
    private static final int REQUEST_CODE_REPAIR = 0x01;

    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.refreshLayout)
    SwipRefreshLayout refreshLayout;
    private MultiAdapter adapter;
    private String title;
    private ServiceMap serviceMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_serve_layout);
        ButterKnife.bind(this);
        if (myBundle == null) {
            finish();
        }
        title = myBundle.getString("title");
        serviceMap = (ServiceMap) myBundle.getSerializable(SERVICEMAP);
        setTitleBar(title, true, getResources().getString(R.string.icon_font_add), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qStartActivityForResult(ApplyForActivity.class, null, REQUEST_CODE_REPAIR);
            }
        });
        setListView();
        startRequest(1);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        myBundle.putSerializable(SERVICEMAP, serviceMap);
        myBundle.putString(TITLE, title);
        super.onSaveInstanceState(outState);
    }

    private void setListView() {
        ArrayList<RepairList> list = new ArrayList<>();
        adapter = new MultiAdapter<RepairList>(getContext(), list).addTypeView(new ITypeView<RepairList>() {
            @Override
            public boolean isForViewType(RepairList item, int position) {
                return true;
            }

            @Override
            public BaseViewHolder createViewHolder(Context mContext, ViewGroup parent) {
                return new RepairHolder(mContext, LayoutInflater.from(mContext).inflate(R.layout.pub_activity_serve_item_layout, parent, false));
            }
        });
        rvList.addItemDecoration(new LineDecoration(this));
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvList.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        refreshLayout.setOnRefreshListener(this);
    }

    private void startRequest(int pager) {
        if (serviceMap == null) return;
        ServeParam param = new ServeParam();
        param.pageNo = pager;
        Request.startRequest(param, pager, serviceMap, mHandler);
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == serviceMap) {
            RepairResult serveResult = (RepairResult) param.result;
            if (serveResult != null && serveResult.data != null && !ArrayUtils.isEmpty(serveResult.data.repairList)) {
                if ((int) param.ext == 1) {
                    adapter.setData(serveResult.data.repairList);
                } else {
                    adapter.addData(serveResult.data.repairList);
                }
            } else {
                if ((int) param.ext == 1) {
                    showToast("没有数据");
                } else {
                    showToast("没有更多了");
                }
            }
            refreshLayout.setRefreshing(false);
        }
        return false;
    }

    @Override
    public void onNetEnd(NetworkParam param) {
        super.onNetEnd(param);
        if (param.key == serviceMap) {
            refreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onItemClickListener(View view, RepairList data, int position) {
        Bundle bundle = new Bundle();
        bundle.putString("id", data.id);
        qStartActivity(DetailsActivity.class, bundle);
    }

    @Override
    public void onRefresh(int index) {
        startRequest(1);
    }

    @Override
    public void onLoad(int index) {
        startRequest(++index);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            boolean refresh = data.getBooleanExtra("refresh", false);
            if (refresh) startRequest(1);
        }
    }
}
