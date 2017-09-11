package com.page.store.classify.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framework.activity.BaseActivity;
import com.framework.domain.param.BaseParam;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.rvadapter.adapter.MultiAdapter;
import com.framework.rvadapter.click.OnItemClickListener;
import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.rvadapter.manage.ITypeView;
import com.framework.utils.ArrayUtils;
import com.framework.view.LineDecoration;
import com.page.store.classify.model.ClassifyResult;
import com.page.store.classify.model.ClassifyResult.Data.Datas;
import com.page.store.classify.model.ClassifyResult.Data.Datas.Produts;
import com.page.store.classifylist.model.ClassifyListResult;
import com.page.store.orderaffirm.model.CommitOrderParam;
import com.page.store.orderaffirm.model.CommitOrderParam.Product;
import com.page.store.prodetails.activity.ProDetailsActivity;
import com.qfant.wuye.R;
import com.page.store.classify.holder.NavHolder;
import com.page.store.classify.holder.ProHolder;
import com.page.store.classifylist.activity.ClassifyListActivity;

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
    private MultiAdapter parentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_classify_layout);
        ButterKnife.bind(this);
        setTitleBar("商品分类", true);
        setLeftListView();
        setRightListView();
        startRequest();
    }

    private void startRequest() {
        Request.startRequest(new BaseParam(), ServiceMap.getCategorys, mHandler, Request.RequestFeature.BLOCK);
    }


    private void setLeftListView() {
        parentAdapter = new MultiAdapter<Datas>(getContext()).addTypeView(new ITypeView() {
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
        rvNavList.setAdapter(parentAdapter);
        parentAdapter.setOnItemClickListener(new OnItemClickListener<Datas>() {
            @Override
            public void onItemClickListener(View view, Datas data, int position) {
                for (Datas item : (ArrayList<Datas>) parentAdapter.getData()) {
                    item.isSelect = item == data;
                }
                multiAdapter.setData(data.produts);
                parentAdapter.notifyDataSetChanged();
            }
        });

    }

    private void setRightListView() {

        multiAdapter = new MultiAdapter<Product>(getContext()).addTypeView(new ITypeView() {
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
        multiAdapter.setOnItemClickListener(new OnItemClickListener<Produts>() {
            @Override
            public void onItemClickListener(View view, Produts data, int position) {
//                Bundle bundle = new Bundle();
//                bundle.putString(ClassifyListActivity.CATEGORYID, data.id);
//                qStartActivity(ClassifyListActivity.class, bundle);

                Bundle bundle = new Bundle();
                bundle.putString(ProDetailsActivity.ID, data.id);
                qStartActivity(ProDetailsActivity.class, bundle);
            }
        });
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.getCategorys) {
            ClassifyResult result = (ClassifyResult) param.result;
            if (result != null && result.data != null && !ArrayUtils.isEmpty(result.data.datas) && !ArrayUtils.isEmpty(result.data.datas.get(0).produts)) {
                Datas datas = result.data.datas.get(0);
                datas.isSelect = true;
                parentAdapter.setData(result.data.datas);
                multiAdapter.setData(result.data.datas.get(0).produts);
            }
        }
        return false;
    }
}
