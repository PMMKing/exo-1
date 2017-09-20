package com.page.store.orderlist.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.framework.view.pull.SwipRefreshLayout;
import com.page.community.event.activity.EventActivity;
import com.page.pay.PayActivity;
import com.page.pay.PayData;
import com.page.store.orderback.activity.OrderBackActivity;
import com.page.store.orderdetails.activity.OrderDetailsActivity;
import com.page.store.orderlist.holder.OrderListHolder;
import com.page.store.orderlist.inteface.OnOrderStatusCallBack;
import com.page.store.orderlist.model.OrderListParam;
import com.page.store.orderlist.model.OrderListResult;
import com.page.store.orderlist.model.OrderListResult.Data.OrderList;
import com.page.store.orderlist.model.OrderStatusParam;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by shucheng.qu on 2017/8/18.
 */

public class OrderListFragment extends BaseFragment implements OnItemClickListener<OrderList>, SwipRefreshLayout.OnRefreshListener {

    @BindView(R.id.rv_list)
    RecyclerView rvList;
    Unbinder unbinder;
    @BindView(R.id.refreshLayout)
    SwipRefreshLayout refreshLayout;
    private int type;
    private MultiAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pub_fragment_orderlist_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        type = myBundle.getInt("type", 1);
        setListView();
    }

    @Override
    public void onResume() {
        super.onResume();
        startRequest(1);
    }

    private void startRequest(int pager) {
        OrderListParam param = new OrderListParam();
        param.type = type;
        param.pageNo = pager;
        Request.startRequest(param, pager, ServiceMap.getMyOrders, mHandler, Request.RequestFeature.CANCELABLE);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        myBundle.putInt("type", type);
    }

    private void setListView() {
        adapter = new MultiAdapter(getContext()).addTypeView(new ITypeView() {
            @Override
            public boolean isForViewType(Object item, int position) {
                return true;
            }

            @Override
            public BaseViewHolder createViewHolder(Context mContext, ViewGroup parent) {
                return new OrderListHolder(mContext, LayoutInflater.from(mContext).inflate(R.layout.pub_fragment_orderlist_item_layout, parent, false), new OnOrderStatusCallBack() {
                    @Override
                    public void orderCancle(String id) {
                        OrderListFragment.this.orderCancle(id);
                    }

                    @Override
                    public void orderPay(String id, double totalprice,String orderno) {
                        OrderListFragment.this.orderPay(id, totalprice,orderno);
                    }

                    @Override
                    public void orderEvaluate(String id) {
                        OrderListFragment.this.orderEvaluate(id);
                    }

                    @Override
                    public void orderBack(String id) {
                        OrderListFragment.this.orderBack(id);
                    }

                    @Override
                    public void orderConfirm(String id) {
                        OrderListFragment.this.orderConfirm(id);
                    }
                });
            }
        });

        rvList.setHasFixedSize(true);
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvList.addItemDecoration(new LineDecoration(getContext(), LineDecoration.VERTICAL_LIST, R.drawable.pub_gray_line_5));
        rvList.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        refreshLayout.setOnRefreshListener(this);
    }

    private void orderCancle(String id) {
        OrderStatusParam param = new OrderStatusParam();
        param.id = id;
        Request.startRequest(param, ServiceMap.cancelOrder, mHandler, Request.RequestFeature.BLOCK);
    }

    private void orderPay(String id, double totalprice,String orderno) {
        Bundle bundle = new Bundle();
        PayData payData = new PayData();
        payData.id = Integer.parseInt(id);
        payData.price = totalprice;
        payData.orderno = orderno;
        bundle.putSerializable("order", payData);
        qStartActivity(PayActivity.class, bundle);
    }

    private void orderEvaluate(String id) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(EventActivity.ID, id);
        qStartActivity(EventActivity.class, bundle);
    }

    private void orderConfirm(String id) {
        OrderStatusParam param = new OrderStatusParam();
        param.id = id;
        Request.startRequest(param, ServiceMap.updateOrderConfirm, mHandler, Request.RequestFeature.BLOCK);
    }

    private void orderBack(String id) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(OrderBackActivity.ID, id);
        qStartActivity(OrderBackActivity.class, bundle);
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.getMyOrders) {
            OrderListResult result = (OrderListResult) param.result;
            if (result != null && result.data != null && !ArrayUtils.isEmpty(result.data.orderList)) {
                if ((int) param.ext == 1) {
                    adapter.setData(result.data.orderList);
                } else {
                    adapter.addData(result.data.orderList);
                }
            } else {
                if ((int) param.ext == 1) {
//                    showToast("没有数据");
                } else {
                    showToast("没有更多了");
                }
            }
            refreshLayout.setRefreshing(false);
        } else if (param.key == ServiceMap.cancelOrder) {
            if (param.result.bstatus.code == 0) {
                showToast("订单取消成功");
            } else {
                showToast(param.result.bstatus.des);
            }
        } else if (param.key == ServiceMap.updateOrderConfirm) {
            showToast(param.result.bstatus.des);
        }
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onItemClickListener(View view, OrderList data, int position) {
        Bundle bundle = new Bundle();
        bundle.putString(OrderDetailsActivity.ID, data.id);
        qStartActivity(OrderDetailsActivity.class, bundle);
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
