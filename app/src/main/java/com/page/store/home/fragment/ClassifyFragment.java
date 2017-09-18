package com.page.store.home.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.framework.activity.BaseFragment;
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
import com.page.store.classify.holder.NavHolder;
import com.page.store.classify.holder.ProHolder;
import com.page.store.classify.model.ClassifyResult;
import com.page.store.orderaffirm.model.CommitOrderParam;
import com.page.store.prodetails.activity.ProDetailsActivity;
import com.qfant.wuye.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by shucheng.qu on 2017/9/14.
 */

public class ClassifyFragment extends BaseFragment implements View.OnTouchListener {

    @BindView(R.id.rv_nav_list)
    RecyclerView rvNavList;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    Unbinder unbinder;
    private MultiAdapter multiAdapter;
    private MultiAdapter parentAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = onCreateViewWithTitleBar(inflater, container, R.layout.pub_activity_classify_layout);
        unbinder = ButterKnife.bind(this, view);
        view.setOnTouchListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setTitleBar("商品分类", false);
        setLeftListView();
        setRightListView();
        startRequest();
    }


    private void startRequest() {
        Request.startRequest(new BaseParam(), ServiceMap.getCategorys, mHandler, Request.RequestFeature.BLOCK);
    }


    private void setLeftListView() {
        parentAdapter = new MultiAdapter<ClassifyResult.Data.Datas>(getContext()).addTypeView(new ITypeView() {
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
        parentAdapter.setOnItemClickListener(new OnItemClickListener<ClassifyResult.Data.Datas>() {
            @Override
            public void onItemClickListener(View view, ClassifyResult.Data.Datas data, int position) {
                for (ClassifyResult.Data.Datas item : (ArrayList<ClassifyResult.Data.Datas>) parentAdapter.getData()) {
                    item.isSelect = item == data;
                }
                multiAdapter.setData(data.produts);
                parentAdapter.notifyDataSetChanged();
            }
        });

    }

    private void setRightListView() {

        multiAdapter = new MultiAdapter<CommitOrderParam.Product>(getContext()).addTypeView(new ITypeView() {
            @Override
            public boolean isForViewType(Object item, int position) {
                return true;
            }

            @Override
            public BaseViewHolder createViewHolder(Context mContext, ViewGroup parent) {
                return new ProHolder(mContext, LayoutInflater.from(mContext).inflate(R.layout.pub_activity_classify_right_item_layout, parent, false));
            }
        });
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvList.addItemDecoration(new LineDecoration(getContext()));
        rvList.setAdapter(multiAdapter);
        multiAdapter.setOnItemClickListener(new OnItemClickListener<ClassifyResult.Data.Datas.Produts>() {
            @Override
            public void onItemClickListener(View view, ClassifyResult.Data.Datas.Produts data, int position) {
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
                ClassifyResult.Data.Datas datas = result.data.datas.get(0);
                datas.isSelect = true;
                parentAdapter.setData(result.data.datas);
                multiAdapter.setData(result.data.datas.get(0).produts);
            }
        }
        return false;
    }


    /**
     * @param v
     * @param event
     * @return 防止击穿
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public boolean onBackPressed() {
        FragmentTransaction fragmentTransaction = getContext().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(
                R.anim.back_left_in_show
                , R.anim.back_right_out_dismiss
                , R.anim.back_left_in_show
                , R.anim.back_right_out_dismiss);
        fragmentTransaction.remove(this);
        fragmentTransaction.commitAllowingStateLoss();
        return true;
    }
}
