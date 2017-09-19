package com.page.home.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.framework.activity.BaseFragment;
import com.framework.rvadapter.adapter.MultiAdapter;
import com.framework.rvadapter.click.OnItemClickListener;
import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.rvadapter.manage.ITypeView;
import com.framework.utils.Arith;
import com.framework.utils.ArrayUtils;
import com.framework.utils.BusinessUtils;
import com.framework.utils.ShopCarUtils;
import com.framework.view.LineDecoration;
import com.page.home.holder.ShopCarHolder;
import com.page.home.model.ShopCarData;
import com.page.store.orderaffirm.activity.OrderAffirmActivity;
import com.page.store.orderaffirm.model.CommitOrderParam;
import com.page.store.orderaffirm.model.CommitOrderParam.Product;
import com.page.store.prodetails.activity.ProDetailsActivity;
import com.qfant.wuye.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by chenxi.cui on 2017/8/13.
 */

public class ShoppingCartFragment extends BaseFragment implements OnItemClickListener<Product> {

    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    Unbinder unbinder;
    private double totalPrice;
    private boolean back;
    private MultiAdapter<Product> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = onCreateViewWithTitleBar(inflater, container, R.layout.pub_fragment_classify_layout);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        back = myBundle.getBoolean("back", false);
        setTitleBar("购物车", back, "清空", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShopCarUtils.getInstance().clearData();
                refreshAdapter();
            }
        });
        setListView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        myBundle.putBoolean("back", back);
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshAdapter();
    }

    public void refreshAdapter() {
        adapter.setData(ShopCarUtils.getInstance().getShopCarList());
        refreshPrice();
    }

    private void setListView() {
        adapter = new MultiAdapter<Product>(getContext()).addTypeView(new ITypeView() {
            @Override
            public boolean isForViewType(Object item, int position) {
                return true;
            }

            @Override
            public BaseViewHolder createViewHolder(Context mContext, ViewGroup parent) {
                return new ShopCarHolder(mContext, ShoppingCartFragment.this, LayoutInflater.from(mContext).inflate(R.layout.pub_fragment_shopcar_item_layout, parent, false));
            }
        });
        rvList.setHasFixedSize(true);
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvList.addItemDecoration(new LineDecoration(getContext()));
        rvList.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

    }

    public void refreshPrice() {
        totalPrice = 0;
        for (Product product : adapter.getData()) {
            if (product != null && product.price > 0) {
                totalPrice = Arith.add(totalPrice, Arith.mul(product.num, product.price));
//                totalPrice += product.num * product.price;
            }
        }
        tvMoney.setText(String.format("合计：￥%s", BusinessUtils.formatDouble2String(totalPrice)));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.tv_commit)
    public void onViewClicked() {
        ArrayList<Product> shopCarList = ShopCarUtils.getInstance().getShopCarList();
        if (ArrayUtils.isEmpty(shopCarList)) {
            showToast("先添加商品再下单吧~");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(OrderAffirmActivity.PROLIST, ShopCarUtils.getInstance().getShopCarList());
        qStartActivity(OrderAffirmActivity.class, bundle);
    }

    @Override
    public void onItemClickListener(View view, Product data, int position) {
        Bundle bundle = new Bundle();
        bundle.putString(ProDetailsActivity.ID, data.id);
        qStartActivity(ProDetailsActivity.class, bundle);
    }
}
