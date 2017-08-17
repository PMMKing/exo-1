package com.page.orderaffirm.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.framework.activity.BaseActivity;
import com.framework.rvadapter.adapter.MultiAdapter;
import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.rvadapter.manage.ITypeView;
import com.haolb.client.R;
import com.page.orderaffirm.holder.HeaderHolder;
import com.page.orderaffirm.holder.ItemHolder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shucheng.qu on 2017/8/17.
 */

public class OrderAffirmActivity extends BaseActivity {

    @BindView(R.id.rv_list)
    RecyclerView rvList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_orderaffirm_layout);
        ButterKnife.bind(this);
        setListView();
    }

    private void setListView() {
        ArrayList<String> list = new ArrayList<String>();

        for (int i = 0; i < 4; i++) {
            list.add("" + i);
        }

        MultiAdapter adapter = new MultiAdapter(getContext(), list).addTypeView(new ITypeView() {
            @Override
            public boolean isForViewType(Object item, int position) {
                return position == 0;
            }

            @Override
            public BaseViewHolder createViewHolder(Context mContext, ViewGroup parent) {
                return new HeaderHolder(mContext, LayoutInflater.from(mContext).inflate(R.layout.pub_activity_orderaffirm_item_header_layout, parent, false));
            }
        }).addTypeView(new ITypeView() {
            @Override
            public boolean isForViewType(Object item, int position) {
                return position > 0;
            }

            @Override
            public BaseViewHolder createViewHolder(Context mContext, ViewGroup parent) {
                return new ItemHolder(mContext, LayoutInflater.from(mContext).inflate(R.layout.pub_activity_orderaffirm_item_layout, parent, false));
            }
        });

        rvList.setHasFixedSize(true);
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvList.setAdapter(adapter);


    }
}
