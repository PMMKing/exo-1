package com.page.home.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.swipe.util.Attributes;
import com.framework.activity.BaseFragment;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.rvadapter.adapter.MultiAdapter;
import com.framework.rvadapter.click.OnItemClickListener;
import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.rvadapter.manage.ITypeView;
import com.framework.utils.ArrayUtils;
import com.framework.view.LineDecoration;
import com.framework.view.TitleBar;
import com.framework.view.pull.SwipRefreshLayout;
import com.qfant.wuye.R;
import com.page.community.quickpai.activity.AddQPaiActivity;
import com.page.community.quickpai.activity.QuickPaiActivity;
import com.page.community.quickpai.holder.RecyclerViewAdapter;
import com.page.community.quickpain.activity.QuickPaiNActivity;
import com.page.home.holder.QpListHolder;
import com.page.home.model.QpListParam;
import com.page.home.model.QpListResult;
import com.page.home.model.QpListResult.Data.Snapshots;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by shucheng.qu on 2017/8/30.
 */

public class QpListFragment extends BaseFragment implements SwipRefreshLayout.OnRefreshListener, OnItemClickListener<Snapshots> {

    private static final int ADDQP_REQUEST_CODE = 0x01;

    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.spl_refresh)
    SwipRefreshLayout refreshLayout;
    Unbinder unbinder;
    @BindView(R.id.tv_add_qp)
    TextView tvAddQp;
    //    @BindView(R.id.tv_my_qp)
//    TextView tvMyQp;
    private MultiAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pub_fragment_qplist_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        mTitleBar = (TitleBar) view.findViewById(R.id.title_bar);
        tvAddQp.setText(R.string.icon_font_add);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setTitleBar("随手拍", false, "我的", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qStartActivity(QuickPaiActivity.class);
            }
        });
        setListView();
    }

    @Override
    public void onResume() {
        super.onResume();
        startRequest(1);
    }

    private void startRequest(int pager) {
        QpListParam param = new QpListParam();
        param.pageNo = pager;
        param.pageSize = 7;
        Request.startRequest(param, pager, ServiceMap.getSnapshots, mHandler);
    }


    private void setListView() {
        mAdapter = new MultiAdapter<Snapshots>(getContext()).addTypeView(new ITypeView() {
            @Override
            public boolean isForViewType(Object item, int position) {
                return true;
            }

            @Override
            public BaseViewHolder createViewHolder(Context mContext, ViewGroup parent) {
                return new QpListHolder(mContext, LayoutInflater.from(mContext).inflate(R.layout.pub_fragment_qplist_item_layout, parent, false));
            }
        });
        mAdapter.setOnItemClickListener(this);
        rvList.addItemDecoration(new LineDecoration(getContext()));
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvList.setAdapter(mAdapter);
        rvList.setHasFixedSize(true);
        refreshLayout.setOnRefreshListener(this);
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.getSnapshots) {
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
        }
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onRefresh(int index) {
        startRequest(1);
    }

    @Override
    public void onLoad(int index) {
        startRequest(++index);
    }

    @OnClick({R.id.ll_add_qp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_add_qp:
//                qStartActivity(AddQPaiActivity.class);
                qStartActivityForResult(AddQPaiActivity.class, null, ADDQP_REQUEST_CODE);
                break;
//            case R.id.tv_my_qp:
//                qStartActivity(QuickPaiActivity.class);
//                break;
        }
    }

    @Override
    public void onItemClickListener(View view, Snapshots data, int position) {
        Bundle bundle = new Bundle();
        bundle.putString(QuickPaiNActivity.ID, data.id);
        qStartActivity(QuickPaiNActivity.class, bundle);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ADDQP_REQUEST_CODE:
                if (data != null) {
                    if (data.getBooleanExtra("refresh", false)) {
                        startRequest(1);
                    }
                }
                break;
        }
    }
}
