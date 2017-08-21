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
import com.framework.rvadapter.adapter.MultiAdapter;
import com.framework.rvadapter.click.OnItemClickListener;
import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.rvadapter.manage.ITypeView;
import com.framework.view.LineDecoration;
import com.framework.view.tab.TabItem;
import com.framework.view.tab.TabView;
import com.haolb.client.R;
import com.page.store.orderaffirm.activity.OrderAffirmActivity;
import com.page.store.prodetails.holder.HeaderHolder;
import com.page.store.prodetails.holder.ItemHolder;
import com.page.store.prodetails.holder.TitleHolder;
import com.page.store.productevaluate.activity.ProEvaluateActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by shucheng.qu on 2017/8/16.
 */

public class ProDetailsActivity extends BaseActivity implements OnItemClickListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_prodetails_layout);
        ButterKnife.bind(this);
        setTabView();
        setListView();
    }

    private void setTabView() {
        tvCollect.initData(new TabItem("收藏", null, null, R.string.icon_font_home));
        tvCar.initData(new TabItem("购物车", null, null, R.string.icon_font_home));
    }

    private void setListView() {

        ArrayList<Integer> list = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            list.add(i);
        }

        MultiAdapter adapter = new MultiAdapter(getContext(), list).addTypeView(new ITypeView() {
            @Override
            public boolean isForViewType(Object item, int position) {
                return position == 0;
            }

            @Override
            public BaseViewHolder createViewHolder(Context mContext, ViewGroup parent) {
                return new HeaderHolder(mContext, LayoutInflater.from(mContext).inflate(R.layout.pub_activity_prodetails_item_header_layout, parent, false));
            }
        }).addTypeView(new ITypeView() {
            @Override
            public boolean isForViewType(Object item, int position) {
                return position < 3 && position > 0;
            }

            @Override
            public BaseViewHolder createViewHolder(Context mContext, ViewGroup parent) {
                return new TitleHolder(mContext, LayoutInflater.from(mContext).inflate(R.layout.pub_activity_prodetails_item_title_layout, parent, false));
            }
        }).addTypeView(new ITypeView() {
            @Override
            public boolean isForViewType(Object item, int position) {
                return position >= 3;
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
