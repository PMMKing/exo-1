package com.page.classify.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framework.activity.BaseActivity;
import com.framework.rvadapter.adapter.MultiAdapter;
import com.framework.rvadapter.click.OnItemClickListener;
import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.rvadapter.manage.ITypeView;
import com.framework.view.LineDecoration;
import com.haolb.client.R;
import com.page.classify.holder.NavHolder;
import com.page.classify.holder.ProHolder;
import com.page.classify.model.ClassifyModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shucheng.qu on 2017/8/15.
 */

public class ClassifyActivity extends BaseActivity {

    @BindView(R.id.rv_nav_list)
    RecyclerView rvNavList;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    private MultiAdapter multiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_classify_layout);
        ButterKnife.bind(this);

        setLeftListView();
        setRightListView();

    }


    private void setLeftListView() {

        ArrayList<ClassifyModel> list = new ArrayList<>();
        ArrayList<String> types = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            types.add("纯牛奶");
        }
        for (int i = 0; i < 6; i++) {
            ClassifyModel classifyModel = new ClassifyModel();
            classifyModel.title = "零食";
            classifyModel.types = (List<String>) types.clone();
            list.add(classifyModel);
        }

        MultiAdapter adapter = new MultiAdapter(getContext(), list).addTypeView(new ITypeView() {
            @Override
            public boolean isForViewType(Object item, int position) {
                return true;
            }

            @Override
            public BaseViewHolder createViewHolder(Context mContext, ViewGroup parent) {
                return new NavHolder(mContext, LayoutInflater.from(mContext).inflate(R.layout.pub_activity_classify_left_item_layout, parent, false));
            }
        });

        rvNavList.setHasFixedSize(true);
        rvNavList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvNavList.addItemDecoration(new LineDecoration(getContext(), LineDecoration.VERTICAL_LIST, R.drawable.pub_white_line));
        rvNavList.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener<ClassifyModel>() {
            @Override
            public void onItemClickListener(View view, ClassifyModel data, int position) {
                multiAdapter.setData(data.types);
                multiAdapter.notifyDataSetChanged();
            }
        });

    }

    private void setRightListView() {

        multiAdapter = new MultiAdapter(getContext(), null).addTypeView(new ITypeView() {
            @Override
            public boolean isForViewType(Object item, int position) {
                return true;
            }

            @Override
            public BaseViewHolder createViewHolder(Context mContext, ViewGroup parent) {
                return new ProHolder(mContext, LayoutInflater.from(mContext).inflate(R.layout.pub_activity_classify_right_item_layout, parent, false));
            }
        });
        rvList.setLayoutManager(new GridLayoutManager(getContext(), 3));
        rvList.setAdapter(multiAdapter);
    }

}
