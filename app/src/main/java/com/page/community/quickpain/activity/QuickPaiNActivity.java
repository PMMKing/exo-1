package com.page.community.quickpain.activity;

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
import com.framework.view.LineDecoration;
import com.haolb.client.R;
import com.page.community.quickpain.holder.ContentHolder;
import com.page.community.quickpain.holder.HeaderHolder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shucheng.qu on 2017/8/10.
 */

public class QuickPaiNActivity extends BaseActivity {

    @BindView(R.id.rv_list)
    RecyclerView rvList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_quickpain_layout);
        ButterKnife.bind(this);
        setListView();
    }

    private void setListView() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i + "");
        }
        rvList.addItemDecoration(new LineDecoration(this));
        rvList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        MultiAdapter adapter = new MultiAdapter(this, list).addTypeView(new ITypeView() {
            @Override
            public boolean isForViewType(Object item, int position) {
                return position == 0;
            }

            @Override
            public BaseViewHolder createViewHolder(Context mContext, ViewGroup parent) {
                return new HeaderHolder(mContext, LayoutInflater.from(mContext).inflate(R.layout.pub_activity_quick_headerview_layout, parent, false));
            }
        }).addTypeView(new ITypeView() {
            @Override
            public boolean isForViewType(Object item, int position) {
                return position > 0;
            }

            @Override
            public BaseViewHolder createViewHolder(Context mContext, ViewGroup parent) {
                return new ContentHolder(mContext, LayoutInflater.from(mContext).inflate(R.layout.pub_activity_quickpain_item_layout, parent, false));
            }
        });
        rvList.setAdapter(adapter);
    }
}
