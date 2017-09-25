package com.page.store.home.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.framework.utils.imageload.ImageLoad;
import com.framework.view.GridDecoration;
import com.framework.view.sivin.Banner;
import com.framework.view.sivin.BannerAdapter;
import com.page.home.activity.WebActivity;
import com.page.home.holder.Shopping2Holder;
import com.page.home.holder.Shopping3Holder;
import com.page.home.model.LinksParam;
import com.page.home.model.LinksResult;
import com.page.home.model.LinksResult.Data.Links;
import com.page.home.model.ShopRecResult;
import com.page.home.model.ShopRecResult.Data.ProductList;
import com.page.store.home.ClassifyActivity;
import com.page.store.home.model.FoodRecResult;
import com.page.store.home.model.FoodRecResult.Data.Products;
import com.page.store.prodetails.activity.ProDetailsActivity;
import com.qfant.wuye.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.page.community.serve.activity.ServeActivity.SERVICEMAP;
import static com.page.community.serve.activity.ServeActivity.TITLE;

/**
 * Created by shucheng.qu on 2017/9/14.
 */

public class ShopHomeFragment extends BaseFragment {

    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.tv_recommend1)
    TextView tvRecommend1;
    @BindView(R.id.tv_recommend2)
    TextView tvRecommend2;
    @BindView(R.id.rv_recommend2)
    RecyclerView rvRecommend2;
    @BindView(R.id.tv_recommend3)
    TextView tvRecommend3;
    @BindView(R.id.rv_recommend3)
    RecyclerView rvRecommend3;
    Unbinder unbinder;
    @BindView(R.id.tv_arr)
    TextView tvArr;
    private BannerAdapter bannerAdapter;
    private MultiAdapter rec2Adapter;
    private MultiAdapter rec3Adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pub_fragment_shop_home_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        setTitleBar("商城", false);
        tvArr.setText("更多分类 " + getContext().getString(R.string.icon_font_right_arrows));
        setBanner();
//        setRecommend1();
        setRecommend2();
        setRecommend3();

    }

    @Override
    public void onResume() {
        super.onResume();
        getLinks();
        getHome();
        getRecommend();


    }


    private void getLinks() {
        LinksParam param = new LinksParam();
        param.type = 2;
        Request.startRequest(param, ServiceMap.getLinks, mHandler, Request.RequestFeature.BLOCK);
    }

    private void getHome() {
        Request.startRequest(new BaseParam(), ServiceMap.getRecommendCategorys, mHandler, Request.RequestFeature.BLOCK);
    }

    private void getRecommend() {
        Request.startRequest(new BaseParam(), ServiceMap.getParticularProducts, mHandler, Request.RequestFeature.BLOCK);
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
                ImageLoad.loadPlaceholder(getContext(), bannerModel.imgurl, imageView);
            }

        };
        banner.setBannerAdapter(bannerAdapter);
        banner.setOnBannerItemClickListener(new Banner.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                try {
                    Links links = (Links) bannerAdapter.getmDataList().get(position);
                    Bundle bundle = new Bundle();
                    bundle.putString(TITLE, links.title);
                    bundle.putString(WebActivity.URL, links.link);
                    qStartActivity(WebActivity.class, bundle);
                } catch (Exception e) {

                }
            }
        });
    }

    private void setRecommend2() {
        rec2Adapter = new MultiAdapter<Products>(getContext()).addTypeView(new ITypeView() {
            @Override
            public boolean isForViewType(Object item, int position) {
                return true;
            }

            @Override
            public BaseViewHolder createViewHolder(Context mContext, ViewGroup parent) {
                return new Shopping2Holder(mContext, LayoutInflater.from(mContext).inflate(R.layout.pub_fragment_shopping_2_item_layout, parent, false));
            }
        });
        rvRecommend2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvRecommend2.setHasFixedSize(true);
        rvRecommend2.setAdapter(rec2Adapter);
        rec2Adapter.setOnItemClickListener(new OnItemClickListener<Products>() {
            @Override
            public void onItemClickListener(View view, Products data, int position) {
                Bundle bundle = new Bundle();
                bundle.putString(ProDetailsActivity.ID, data.id);
                qStartActivity(ProDetailsActivity.class, bundle);
            }
        });
    }

    private void setRecommend3() {
        rec3Adapter = new MultiAdapter<ProductList>(getContext()).addTypeView(new ITypeView() {
            @Override
            public boolean isForViewType(Object item, int position) {
                return true;
            }

            @Override
            public BaseViewHolder createViewHolder(Context mContext, ViewGroup parent) {
                return new Shopping3Holder(mContext, LayoutInflater.from(mContext).inflate(R.layout.pub_fragment_shopping_3_item_layout, parent, false));
            }
        });
        rvRecommend3.setLayoutManager(new GridLayoutManager(getContext(), 3));
        rvRecommend3.setHasFixedSize(true);
        rvRecommend3.addItemDecoration(new GridDecoration(getContext()));
        rvRecommend3.setAdapter(rec3Adapter);
        rec3Adapter.setOnItemClickListener(new OnItemClickListener<ProductList>() {
            @Override
            public void onItemClickListener(View view, ProductList data, int position) {
                Bundle bundle = new Bundle();
                bundle.putString(ProDetailsActivity.ID, data.id);
                qStartActivity(ProDetailsActivity.class, bundle);
            }
        });
        rvRecommend3.setNestedScrollingEnabled(false);
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.getLinks) {
            LinksResult linksResult = (LinksResult) param.result;
            if (linksResult != null && linksResult.data != null && linksResult.data.links != null) {
                updataBanner(linksResult.data.links);
            }

        } else if (param.key == ServiceMap.getRecommendCategorys) {
            FoodRecResult result = (FoodRecResult) param.result;
            updataFoodRec(result);
        } else if (param.key == ServiceMap.getParticularProducts) {
            ShopRecResult result = (ShopRecResult) param.result;
            updataList(result);
        }
        return false;
    }

    private void updataBanner(List<Links> links) {
        bannerAdapter.setImages(links);
        banner.notifyDataHasChanged();
    }

    private void updataFoodRec(FoodRecResult result) {
        if (result != null && result.data != null && !ArrayUtils.isEmpty(result.data.products)) {
            rvRecommend2.setVisibility(View.VISIBLE);
            rec2Adapter.setData(result.data.products);
        } else {
            rvRecommend2.setVisibility(View.GONE);
        }
    }

    private void updataList(ShopRecResult result) {
        if (result != null && result.data != null && !ArrayUtils.isEmpty(result.data.productList)) {
            rvRecommend3.setVisibility(View.VISIBLE);
            rec3Adapter.setData(result.data.productList);
        } else {
            rvRecommend3.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.tv_arr)
    public void onViewClicked() {
        FragmentTransaction fragmentTransaction = getContext().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(
                R.anim.back_left_in_show
                , R.anim.back_right_out_dismiss
                , R.anim.back_left_in_show
                , R.anim.back_right_out_dismiss);
        ClassifyFragment fragment = new ClassifyFragment();
        fragmentTransaction.replace(R.id.fl_fragment, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public boolean onBackPressed() {
        return super.onBackPressed();
    }
}
