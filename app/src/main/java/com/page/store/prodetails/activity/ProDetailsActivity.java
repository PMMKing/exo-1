package com.page.store.prodetails.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.framework.activity.BaseActivity;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.rvadapter.adapter.MultiAdapter;
import com.framework.rvadapter.click.OnItemClickListener;
import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.rvadapter.manage.ITypeView;
import com.framework.view.LineDecoration;
import com.framework.view.tab.TabItem;
import com.framework.view.tab.TabView;
import com.page.store.prodetails.model.PDParam;
import com.page.store.prodetails.model.PDResult;
import com.page.store.prodetails.model.PDResult.Data;
import com.qfant.wuye.R;
import com.page.store.orderaffirm.activity.OrderAffirmActivity;
import com.page.store.prodetails.holder.HeaderHolder;
import com.page.store.prodetails.holder.ItemHolder;
import com.page.store.productevaluate.activity.ProEvaluateActivity;
import com.taobao.weex.devtools.common.android.ViewUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by shucheng.qu on 2017/8/16.
 */

public class ProDetailsActivity extends BaseActivity implements OnItemClickListener {

    public static final String ID = "id";

    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.tv_collect)
    TabView tvCollect;
    @BindView(R.id.tv_car)
    TabView tvCar;
    @BindView(R.id.tv_add_car)
    TextView tvAddCar;
    @BindView(R.id.tv_buy)
    TextView tvBuy;
    private String id;
    private final ArrayList<Data> dataList = new ArrayList<Data>();
    private MultiAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_prodetails_layout);
        if (myBundle == null) finish();
        id = myBundle.getString(ID);
        ButterKnife.bind(this);
        setTabView();
        setListView();
        startRequest();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        myBundle.putString(ID, id);
    }

    private void startRequest() {
        PDParam param = new PDParam();
        param.id = id;
        Request.startRequest(param, ServiceMap.getProduct, mHandler, Request.RequestFeature.BLOCK);
    }

    private void setTabView() {
        tvCollect.initData(new TabItem("收藏", null, null, R.string.icon_font_home));
        tvCar.initData(new TabItem("购物车", null, null, R.string.icon_font_home));
    }

    private void setListView() {
        dataList.add(new Data());//占位
        adapter = new MultiAdapter<Data>(getContext(), dataList).addTypeView(new ITypeView<Data>() {
            @Override
            public boolean isForViewType(Data item, int position) {
                return position == 0;
            }

            @Override
            public BaseViewHolder createViewHolder(Context mContext, ViewGroup parent) {
                return new HeaderHolder(mContext, LayoutInflater.from(mContext).inflate(R.layout.pub_activity_prodetails_item_header_layout, parent, false));
            }
        }).addTypeView(new ITypeView<Data>() {
            @Override
            public boolean isForViewType(Data item, int position) {
                return position > 0;
            }

            @Override
            public BaseViewHolder createViewHolder(Context mContext, ViewGroup parent) {
                return new ItemHolder(mContext, LayoutInflater.from(mContext).inflate(R.layout.pub_activity_prodetails_item_layout, parent, false));
            }
        });
        rvList.addItemDecoration(new LineDecoration(getContext()));
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvList.setHasFixedSize(true);
        rvList.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.getProduct) {
            PDResult result = (PDResult) param.result;
            if (result != null && result.data != null) {
                dataList.remove(0);
                dataList.add(0, result.data);
                adapter.notifyItemChanged(0);
            }
        }
        return false;
    }

    @Override
    public void onItemClickListener(View view, Object data, int position) {
        qStartActivity(ProEvaluateActivity.class);
    }

    @OnClick({R.id.tv_collect, R.id.tv_car, R.id.tv_add_car, R.id.tv_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_collect:
                break;
            case R.id.tv_car:
                break;
            case R.id.tv_add_car:
                break;
            case R.id.tv_buy:
                qStartActivity(OrderAffirmActivity.class);
                break;
        }
    }
}
