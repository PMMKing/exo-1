package com.page.store.classifylist.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
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
import com.framework.view.GridDecoration;
import com.framework.view.pull.SwipRefreshLayout;
import com.page.store.classifylist.holder.ViewHolder;
import com.page.store.classifylist.model.ClassifyListParam;
import com.page.store.classifylist.model.ClassifyListResult;
import com.page.store.classifylist.model.ClassifyListResult.Data.ProductList;
import com.page.store.prodetails.activity.ProDetailsActivity;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shucheng.qu on 2017/8/15.
 */

public class ClassifyListActivity extends BaseActivity implements OnItemClickListener<ProductList>, SwipRefreshLayout.OnRefreshListener {

    public static final String CATEGORYID = "categoryId";

    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.refreshLayout)
    SwipRefreshLayout refreshLayout;
    private String categoryid;
    private MultiAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_classifylist_layout);
        if (myBundle == null) {
            finish();
        }
        categoryid = myBundle.getString(CATEGORYID);
        ButterKnife.bind(this);
        setTitleBar("分类商品列表", true);
        setListView();
        startRequest(1);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        myBundle.putString(CATEGORYID, categoryid);
    }

    private void startRequest(int pager) {
        ClassifyListParam param = new ClassifyListParam();
        param.categoryId = categoryid;
        param.pageNo = pager;
        Request.startRequest(param, pager, ServiceMap.getProducts, mHandler);
    }

    private void setListView() {
        adapter = new MultiAdapter<ProductList>(getContext()).addTypeView(new ITypeView() {
            @Override
            public boolean isForViewType(Object item, int position) {
                return true;
            }

            @Override
            public BaseViewHolder createViewHolder(Context mContext, ViewGroup parent) {
                return new ViewHolder(mContext, LayoutInflater.from(mContext).inflate(R.layout.pub_activity_classifylist_item_layout, parent, false));
            }
        });
        rvList.setLayoutManager(new GridLayoutManager(getContext(), 3));
        rvList.setHasFixedSize(true);
        rvList.addItemDecoration(new GridDecoration(getContext()));
        rvList.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        refreshLayout.setOnRefreshListener(this);
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.getProducts) {
            ClassifyListResult result = (ClassifyListResult) param.result;
            if (result != null && result.data != null && !ArrayUtils.isEmpty(result.data.productList)) {
                if ((int) param.ext == 1) {
                    adapter.setData(result.data.productList);
                } else {
                    adapter.addData(result.data.productList);
                }
            } else {
                if ((int) param.ext == 1) {
                    showToast("还没有数据");
                } else {
                    showToast("没有更多了");
                }
            }
            refreshLayout.setRefreshing(false);
        }
        return false;
    }

    @Override
    public void onItemClickListener(View view, ProductList data, int position) {
        Bundle bundle = new Bundle();
        bundle.putString(ProDetailsActivity.ID, data.id);
        qStartActivity(ProDetailsActivity.class, bundle);
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
