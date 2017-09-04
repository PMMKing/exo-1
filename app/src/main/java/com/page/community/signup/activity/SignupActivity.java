package com.page.community.signup.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.framework.activity.BaseActivity;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.rvadapter.adapter.MultiAdapter;
import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.rvadapter.manage.ITypeView;
import com.framework.view.LineDecoration;
import com.page.community.signup.holder.HeaderHolder;
import com.page.community.signup.holder.ViewHolder;
import com.page.community.signup.model.SignUpParam;
import com.qfant.wuye.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shucheng.qu on 2017/8/11.
 */

public class SignupActivity extends BaseActivity {

    @BindView(R.id.rv_list)
    RecyclerView rvList;
    private MultiAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_signup_layout);
        ButterKnife.bind(this);
        setListView();
        startRequest();
    }

    private void startRequest() {
        SignUpParam param = new SignUpParam();
        param.id = "";
        Request.startRequest(param, ServiceMap.getActivityJoinerList, mHandler, Request.RequestFeature.BLOCK);
    }

    private void setListView() {
        adapter = new MultiAdapter(this).addTypeView(new ITypeView() {
            @Override
            public boolean isForViewType(Object item, int position) {
                return position == 0;
            }

            @Override
            public BaseViewHolder createViewHolder(Context mContext, ViewGroup parent) {
                return new HeaderHolder(mContext, LayoutInflater.from(mContext).inflate(R.layout.pub_activity_signup_header_layout, parent, false));
            }
        }).addTypeView(new ITypeView() {
            @Override
            public boolean isForViewType(Object item, int position) {
                return position > 0;
            }

            @Override
            public BaseViewHolder createViewHolder(Context mContext, ViewGroup parent) {
                return new ViewHolder(mContext, LayoutInflater.from(mContext).inflate(R.layout.pub_activity_signup_item_layout, parent, false));
            }
        });
        rvList.addItemDecoration(new LineDecoration(this));
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.setAdapter(adapter);

    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {

        if (param.key == ServiceMap.getActivityJoinerList) {

        }
        return false;
    }
}
