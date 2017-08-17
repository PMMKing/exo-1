package com.page.classifylist.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.framework.activity.BaseActivity;
import com.framework.rvadapter.adapter.MultiAdapter;
import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.rvadapter.manage.ITypeView;
import com.framework.view.GridDecoration;
import com.framework.view.LineDecoration;
import com.haolb.client.R;
import com.page.classifylist.holder.ViewHolder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shucheng.qu on 2017/8/15.
 */

public class ClassifyListActivity extends BaseActivity {

    @BindView(R.id.rv_list)
    RecyclerView rvList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_classifylist_layout);
        ButterKnife.bind(this);
        setListView();

    }

    private void setListView() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add("" + i);
        }
        MultiAdapter adapter = new MultiAdapter(getContext(), list).addTypeView(new ITypeView() {
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
    }
}
