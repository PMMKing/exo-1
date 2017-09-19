package com.page.store.orderaffirm.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.framework.activity.BaseActivity;
import com.framework.domain.param.BaseParam;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.rvadapter.adapter.MultiAdapter;
import com.framework.rvadapter.click.OnItemClickListener;
import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.rvadapter.manage.ITypeView;
import com.framework.utils.Arith;
import com.framework.utils.BusinessUtils;
import com.framework.utils.ShopCarUtils;
import com.page.address.Address;
import com.page.address.activity.AddressActivity;
import com.page.store.orderaffirm.holder.HeaderHolder;
import com.page.store.orderaffirm.holder.ItemHolder;
import com.page.store.orderaffirm.model.CommitOrderParam;
import com.page.store.orderaffirm.model.CommitOrderParam.Product;
import com.page.store.orderaffirm.model.DefaultAddressResult;
import com.page.store.orderdetails.activity.OrderDetailsActivity;
import com.page.store.payresult.activity.PayResultActivity;
import com.qfant.wuye.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by shucheng.qu on 2017/8/17.
 */

public class OrderAffirmActivity extends BaseActivity implements OnItemClickListener<Product> {

    public static final String PROLIST = "prolist";

    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    private ArrayList<Product> products;
    private MultiAdapter adapter;
    private double totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_orderaffirm_layout);
        if (myBundle == null) finish();
        ButterKnife.bind(this);
        setTitleBar("订单填写", true);
        products = (ArrayList<Product>) myBundle.getSerializable(PROLIST);
        setListView();
        refreshPrice();
        defAddress();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        myBundle.putSerializable(PROLIST, products);
    }

    private void setListView() {
        products.add(0, new Product());
        adapter = new MultiAdapter<Product>(getContext(), products).addTypeView(new ITypeView<Product>() {
            @Override
            public boolean isForViewType(Product item, int position) {
                return position == 0;
            }

            @Override
            public BaseViewHolder createViewHolder(Context mContext, ViewGroup parent) {
                return new HeaderHolder(mContext, LayoutInflater.from(mContext).inflate(R.layout.pub_activity_orderaffirm_item_header_layout, parent, false));
            }
        }).addTypeView(new ITypeView() {
            @Override
            public boolean isForViewType(Object item, int position) {
                return position > 0;
            }

            @Override
            public BaseViewHolder createViewHolder(Context mContext, ViewGroup parent) {
                return new ItemHolder(mContext, LayoutInflater.from(mContext).inflate(R.layout.pub_activity_orderaffirm_item_pro_layout, parent, false));
            }
        });

        rvList.setHasFixedSize(true);
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvList.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

    }

    public void refreshPrice() {

        totalPrice = 0;
        for (Product product : products) {
            if (product != null && product.price > 0) {
                totalPrice = Arith.add(totalPrice, Arith.mul(product.num, product.price));
//                totalPrice += product.num * product.price;
            }
        }
        tvMoney.setText("合计：￥" + BusinessUtils.formatDouble2String(totalPrice));

    }

    private void defAddress() {
        Request.startRequest(new BaseParam(), ServiceMap.getDefaultAddress, mHandler);
    }

    private void startRequest() {
        CommitOrderParam param = new CommitOrderParam();
        Product product = products.get(0);
        if (product == null || product.address == null) {
            showToast("先填写收货信息");
            return;
        }

        param.address = product.address.address;
        param.phone = product.address.phone;
        param.receiver = product.address.name;
        param.totalprice = totalPrice;
        ArrayList<Product> temp = (ArrayList<Product>) products.clone();
        temp.remove(0);
        param.products = temp;
        Request.startRequest(param, ServiceMap.submitOrder, mHandler, Request.RequestFeature.BLOCK);
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.submitOrder) {
            if (param.result.bstatus.code == 0) {
                SubmitResult result = (SubmitResult) param.result;
                if (products.size() > 2) {
                    ShopCarUtils.getInstance().clearData();//清空购物车
                }
                Bundle bundle = new Bundle();
                bundle.putString(OrderDetailsActivity.ID, "" + result.data.id);
                bundle.putInt(OrderDetailsActivity.STATUS, 1);
                Intent intent = new Intent();
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setClass(this, OrderDetailsActivity.class);
                startActivity(intent);
                finish();
            }
        } else if (param.key == ServiceMap.getDefaultAddress) {
            DefaultAddressResult result = (DefaultAddressResult) param.result;
            if (result != null && result.data != null) {
                products.remove(0);
                Product product = new Product();
                product.address = result.data;
                products.add(0, product);
                adapter.notifyItemChanged(0);
            }
        }
        return false;
    }

    @Override
    public void onItemClickListener(View view, Product data, int position) {

        if (position == 0) {
            qStartActivityForResult(AddressActivity.class, null, 100);
        }
    }

    @OnClick(R.id.tv_commit)
    public void onViewClicked() {
        startRequest();
//        qStartActivity(PayResultActivity.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (data == null) return;
            Address address = (Address) data.getExtras().getSerializable("address");
            products.remove(0);
            Product product = new Product();
            product.address = new DefaultAddressResult.Address();
            product.address.address = address.detail;
            product.address.phone = address.tel;
            product.address.name = address.name;
            products.add(0, product);
            adapter.notifyItemChanged(0);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
