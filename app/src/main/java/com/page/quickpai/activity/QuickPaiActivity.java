package com.page.quickpai.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.daimajia.swipe.util.Attributes;
import com.framework.view.LineDecoration;
import com.haolb.client.R;
import com.framework.activity.BaseActivity;
import com.page.quickpai.holder.RecyclerViewAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shucheng.qu on 2017/8/9.
 */

public class QuickPaiActivity extends BaseActivity {

    @BindView(R.id.rv_list)
    RecyclerView rvList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_quickpai_layout);
        ButterKnife.bind(this);
        setListView();
    }

    private void setListView() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(i+"");
        }
        rvList.addItemDecoration(new LineDecoration(this));
        rvList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        RecyclerViewAdapter mAdapter = new RecyclerViewAdapter(this, list);
        mAdapter.setMode(Attributes.Mode.Single);
        rvList.setAdapter(mAdapter);
    }

}
