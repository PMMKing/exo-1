package com.page.home.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.framework.activity.BaseFragment;
import com.framework.rvadapter.adapter.MultiAdapter;
import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.rvadapter.manage.ITypeView;
import com.framework.view.GridDecoration;
import com.haolb.client.R;
import com.page.classifylist.holder.ViewHolder;
import com.page.home.holder.SMHolder;
import com.sivin.Banner;
import com.sivin.BannerAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by chenxi.cui on 2017/8/13.
 */

public class ShoppingFragment extends BaseFragment {

    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.tv_recommend1)
    TextView tvRecommend1;
    @BindView(R.id.rv_recommend1)
    RecyclerView rvRecommend1;
    @BindView(R.id.tv_recommend2)
    TextView tvRecommend2;
    @BindView(R.id.rv_recommend2)
    RecyclerView rvRecommend2;
    @BindView(R.id.tv_recommend3)
    TextView tvRecommend3;
    @BindView(R.id.rv_recommend3)
    RecyclerView rvRecommend3;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pub_fragment_shopping_layout, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setBanner();
        setRecommend1();
        setRecommend2();
        setRecommend3();

    }

    private void setBanner() {
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            arrayList.add("" + i);
        }
        BannerAdapter adapter = new BannerAdapter<String>(arrayList) {
            @Override
            protected void bindTips(TextView tv, String bannerModel) {
//                tv.setText(bannerModel.getTips());
            }

            @Override
            public void bindImage(ImageView imageView, String bannerModel) {
                imageView.setImageResource(R.drawable.pub_login_icon);
            }

        };
        banner.setBannerAdapter(adapter);
        banner.notifyDataHasChanged();
    }

    private void setRecommend1() {

    }

    private void setRecommend2() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }
        MultiAdapter adapter = new MultiAdapter<Integer>(getContext(), list).addTypeView(new ITypeView<Integer>() {
            @Override
            public boolean isForViewType(Integer item, int position) {
                return true;
            }

            @Override
            public BaseViewHolder createViewHolder(Context mContext, ViewGroup parent) {
                return new SMHolder(mContext, LayoutInflater.from(mContext).inflate(R.layout.pub_fragment_home_711_item_layout, parent, false));
            }
        });
        rvRecommend2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvRecommend2.setHasFixedSize(true);
        rvRecommend2.setAdapter(adapter);
    }

    private void setRecommend3() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
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
        rvRecommend3.setLayoutManager(new GridLayoutManager(getContext(), 3));
        rvRecommend3.setHasFixedSize(true);
        rvRecommend3.addItemDecoration(new GridDecoration(getContext()));
        rvRecommend3.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
