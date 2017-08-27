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
import com.framework.domain.param.BaseParam;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.rvadapter.adapter.MultiAdapter;
import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.rvadapter.manage.ITypeView;
import com.framework.utils.imageload.ImageLoad;
import com.framework.view.IFView;
import com.framework.view.sivin.Banner;
import com.framework.view.sivin.BannerAdapter;
import com.haolb.client.R;
import com.page.community.eventlist.activity.EventListActivity;
import com.page.community.serve.activity.ServeActivity;
import com.page.home.holder.SMHolder;
import com.page.home.model.HomeModel;
import com.page.home.model.LinksParam;
import com.page.home.model.LinksResult;
import com.page.home.model.LinksResult.Data.Links;
import com.page.home.view.ModeView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.page.community.serve.activity.ServeActivity.SERVICEMAP;
import static com.page.community.serve.activity.ServeActivity.TITLE;

/**
 * Created by chenxi.cui on 2017/8/13.
 * 首页
 */

public class HomeFragment extends BaseFragment {

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
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.tv_event)
    TextView tvEvent;
    private BannerAdapter bannerAdapter;

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

    @Override
    public void onResume() {
        super.onResume();
        getLinks();
        getEvents();
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
        for (int i = 0; i < 20; i++) {
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
        ArrayList<HomeModel> list = new ArrayList<>();
        list.add(new HomeModel("维修"));
        list.add(new HomeModel("送水"));
        list.add(new HomeModel("洗衣"));
        list.add(new HomeModel("家政"));
        list.add(new HomeModel("缴费"));
        list.add(new HomeModel("超市"));
        list.add(new HomeModel("周边"));
        list.add(new HomeModel("电话"));

        for (HomeModel homeModel : list) {
            ModeView itemView = new ModeView(getContext());
            itemView.setData(homeModel);
            itemView.setTag(homeModel.title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    switch ((String) v.getTag()) {
                        case "维修":
                            bundle.putString(TITLE, "维修列表");
                            break;
                        case "送水":
                            bundle.putString(TITLE, "送水商家");
                            bundle.putSerializable(SERVICEMAP, ServiceMap.getWaters);
                            break;
                        case "洗衣":
                            bundle.putString(TITLE, "洗衣店");
                            bundle.putSerializable(SERVICEMAP, ServiceMap.getWashes);
                            break;
                        case "家政":
                            bundle.putString(TITLE, "家政");
                            bundle.putSerializable(SERVICEMAP, ServiceMap.getHouses);
                            break;
                        case "缴费":
                            bundle.putString(TITLE, "缴费");
                            break;
                        case "超市":
                            bundle.putString(TITLE, "超市");
                            break;
                        case "周边":
                            bundle.putString(TITLE, "周边");
                            break;
                        case "电话":
                            bundle.putString(TITLE, "电话");
                            break;
                    }
                    qStartActivity(ServeActivity.class, bundle);
                }
            });
            glMode.addView(itemView);
        }

    }

    private void setBanner() {
        ArrayList<Links> arrayList = new ArrayList<>();
        bannerAdapter = new BannerAdapter<Links>(arrayList) {
            @Override
            protected void bindTips(TextView tv, Links bannerModel) {
//                tv.setText(bannerModel.getTips());
            }

            @Override
            public void bindImage(ImageView imageView, Links bannerModel) {
                ImageLoad.loadPlaceholder(getContext(), bannerModel.link + bannerModel.imgurl, imageView);
            }

        };
        banner.setBannerAdapter(bannerAdapter);
    }

    public void getLinks() {
        LinksParam param = new LinksParam();
        param.type = 1;
        Request.startRequest(new BaseParam(), ServiceMap.getLinks, mHandler);
    }


    public void getEvents() {

    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.getLinks) {
            LinksResult linksResult = (LinksResult) param.result;
            if (linksResult != null && linksResult.data != null && linksResult.data.links != null) {
                updataBanner(linksResult.data.links);
            }

        }


        return false;
    }

    private void updataBanner(List<Links> links) {
        bannerAdapter.setImages(links);
        banner.notifyDataHasChanged();
    }

    @OnClick(R.id.tv_event)
    public void onViewClicked() {
        qStartActivity(EventListActivity.class);
    }

}
