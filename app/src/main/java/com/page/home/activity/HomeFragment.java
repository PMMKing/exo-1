package com.page.home.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.framework.activity.BaseFragment;
import com.framework.rvadapter.adapter.MultiAdapter;
import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.rvadapter.manage.ITypeView;
import com.framework.view.IFView;
import com.haolb.client.R;
import com.page.home.holder.SMHolder;
import com.page.home.view.ModeView;
import com.sivin.Banner;
import com.sivin.BannerAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by chenxi.cui on 2017/8/13.
 * 首页
 */

public class HomeFragment extends BaseFragment {

    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.tv_tips)
    TextView tvTips;
    @BindView(R.id.ll_tips)
    LinearLayout llTips;
    @BindView(R.id.gl_mode)
    GridLayout glMode;
    @BindView(R.id.ifv_711_more)
    IFView ifv711More;
    @BindView(R.id.ll_711)
    LinearLayout ll711;
    @BindView(R.id.rv_711_list)
    RecyclerView rv711List;
    @BindView(R.id.ll_event)
    LinearLayout llEvent;
    @BindView(R.id.ll_event_list)
    LinearLayout llEventList;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pub_fragment_home_layout, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setBanner();
        setModel();
        set711();
        setEvent();
    }

    private void setEvent() {
        llEventList.removeAllViews();
        for (int i = 0; i < 4; i++) {
            View itemView = LayoutInflater.from(getContext()).inflate(R.layout.pub_fragment_home_event_item_layout, null, false);
            llEventList.addView(itemView);
        }
    }

    private void set711() {
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
        rv711List.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rv711List.setHasFixedSize(true);
        rv711List.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private void setModel() {

        for (int i = 0; i < 8; i++) {
            ModeView itemView = new ModeView(getContext());
            glMode.addView(itemView);
        }

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

}
