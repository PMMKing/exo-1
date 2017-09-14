package com.page.store.prodetails.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.framework.activity.BaseActivity;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.rvadapter.adapter.MultiAdapter;
import com.framework.rvadapter.click.OnItemClickListener;
import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.rvadapter.manage.ITypeView;
import com.framework.utils.ArrayUtils;
import com.framework.utils.BusinessUtils;
import com.framework.utils.ShopCarUtils;
import com.framework.view.LineDecoration;
import com.framework.view.pull.SwipRefreshLayout;
import com.framework.view.tab.TabItem;
import com.framework.view.tab.TabView;
import com.page.community.quickpain.model.ScommentsReault;
import com.page.store.orderaffirm.activity.OrderAffirmActivity;
import com.page.store.orderaffirm.model.CommitOrderParam.Product;
import com.page.store.prodetails.holder.HeaderHolder;
import com.page.store.prodetails.holder.ItemHolder;
import com.page.store.prodetails.model.CollectParam;
import com.page.store.prodetails.model.PDParam;
import com.page.store.prodetails.model.PDResult;
import com.page.store.prodetails.model.PDResult.Data;
import com.page.store.prodetails.model.PEParam;
import com.page.store.prodetails.model.PEResult;
import com.page.store.prodetails.model.PEResult.Evaluate;
import com.page.store.prodetails.utils.CheckRepertoryUtils;
import com.page.store.productevaluate.activity.ProEvaluateActivity;
import com.page.store.shopcar.activity.ShopCarActivity;
import com.qfant.wuye.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by shucheng.qu on 2017/8/16.
 */

public class ProDetailsActivity extends BaseActivity implements OnItemClickListener, SwipRefreshLayout.OnRefreshListener {

    public static final String ID = "id";

    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.tv_collect)
    TabView tvCollect;
    @BindView(R.id.tv_car)
    TabView tvCar;
    @BindView(R.id.tv_add_car)
    TextView tvAddCar;
    @BindView(R.id.tv_buy)
    TextView tvBuy;
    @BindView(R.id.refreshLayout)
    SwipRefreshLayout refreshLayout;
    private String id;
    private final ArrayList<Evaluate> dataList = new ArrayList<Evaluate>();
    private MultiAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_prodetails_layout);
        if (myBundle == null) finish();
        id = myBundle.getString(ID);
        ButterKnife.bind(this);
        setTitleBar("商品详情", true);
        setTabView();
        setListView();
        startRequest();
        evaluate(1);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        myBundle.putString(ID, id);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshTabView();
    }

    private void startRequest() {
        PDParam param = new PDParam();
        param.id = id;
        Request.startRequest(param, ServiceMap.getProduct, mHandler, Request.RequestFeature.BLOCK);
    }

    private void collect() {
        CollectParam param = new CollectParam();
        param.id = id;
        param.type = true ? 1 : 2;
        Request.startRequest(param, ServiceMap.fav, mHandler, Request.RequestFeature.BLOCK);
    }

    private void evaluate(int pager) {
        PEParam param = new PEParam();
        param.pageNo = pager;
        Request.startRequest(param, pager, ServiceMap.pcomments, mHandler);
    }

    private void setTabView() {
        tvCollect.initData(new TabItem("收藏", null, null, R.string.icon_font_home));
        tvCar.initData(new TabItem("购物车", null, null, R.string.icon_font_home));
    }

    private void refreshTabView() {
        tvCar.setNumber(ShopCarUtils.getInstance().getShopCarSize());
    }

    private void setListView() {
        dataList.add(new Evaluate());//占位
        adapter = new MultiAdapter<Evaluate>(getContext(), dataList).addTypeView(new ITypeView<Evaluate>() {
            @Override
            public boolean isForViewType(Evaluate item, int position) {
                return position == 0;
            }

            @Override
            public BaseViewHolder createViewHolder(Context mContext, ViewGroup parent) {
                return new HeaderHolder(mContext, LayoutInflater.from(mContext).inflate(R.layout.pub_activity_prodetails_item_header_layout, parent, false));
            }
        }).addTypeView(new ITypeView<Evaluate>() {
            @Override
            public boolean isForViewType(Evaluate item, int position) {
                return position > 0;
            }

            @Override
            public BaseViewHolder createViewHolder(Context mContext, ViewGroup parent) {
                return new ItemHolder(mContext, LayoutInflater.from(mContext).inflate(R.layout.pub_activity_prodetails_item_layout, parent, false));
            }
        });
        rvList.addItemDecoration(new LineDecoration(getContext()));
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvList.setHasFixedSize(true);
        rvList.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        refreshLayout.setOnRefreshListener(this);

    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.getProduct) {
            PDResult result = (PDResult) param.result;
            if (result != null && result.data != null) {
                Evaluate evaluate = new Evaluate();
                evaluate.product = result.data;
                dataList.remove(0);
                dataList.add(0, evaluate);
                adapter.notifyItemChanged(0);
            }
        } else if (param.key == ServiceMap.fav) {
            showToast(param.result.bstatus.des);
        } else if (param.key == ServiceMap.pcomments) {
            PEResult result = (PEResult) param.result;
            if (result != null && result.data != null && !ArrayUtils.isEmpty(result.data.datas)) {
                if ((int) param.ext == 1) {
                    Evaluate temp = dataList.get(0);
                    dataList.clear();
                    dataList.add(temp);
                    dataList.addAll(1, result.data.datas);
                    adapter.notifyDataSetChanged();

                } else {
                    adapter.addData(result.data.datas);
                }
            } else {
                if ((int) param.ext == 1) {
                    showToast("没有数据");
                } else {
                    showToast("没有更多了");
                }
            }
        }
        return false;
    }

    @Override
    public void onNetEnd(NetworkParam param) {
        super.onNetEnd(param);
        if (param.key == ServiceMap.pcomments) {
            refreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onItemClickListener(View view, Object data, int position) {
        qStartActivity(ProEvaluateActivity.class);
    }

    @OnClick({R.id.tv_collect, R.id.tv_car, R.id.tv_add_car, R.id.tv_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_collect:
                collect();
                break;
            case R.id.tv_car:
                qStartActivity(ShopCarActivity.class);
                break;
            case R.id.tv_add_car:
                addShopCar(dataList.get(0), 1);
                break;
            case R.id.tv_buy:
                addShopCar(dataList.get(0), 2);
                break;
        }
    }


    private void addShopCar(final Evaluate evaluate, int type) {
        if (evaluate == null || evaluate.product == null || evaluate.product.price <= 0) return;
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.show();
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        dialogWindow.setAttributes(lp);
        dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);
        View view = View.inflate(this, R.layout.pub_activity_prodetail_addcar_layout, null);
        dialogWindow.setContentView(view);
        TextView tvPrice = (TextView) view.findViewById(R.id.tv_price);
        TextView tvSub = (TextView) view.findViewById(R.id.tv_sub);
        final TextView tvNumber = (TextView) view.findViewById(R.id.tv_number);
        TextView tvAdd = (TextView) view.findViewById(R.id.tv_add);
        TextView tvOk = (TextView) view.findViewById(R.id.tv_ok);

        tvPrice.setText("单价￥：" + BusinessUtils.formatDouble2String(evaluate.product.price));
        tvSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(tvNumber.getText().toString().trim());
                if (number > 1) {
                    tvNumber.setText(--number + "");
                } else {
                    showToast("不能再少了");
                }
            }
        });

        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(tvNumber.getText().toString().trim());
                CheckRepertoryUtils.getInstance().check(++number, evaluate.product.id, new CheckRepertoryUtils.CheckCallBack() {
                    @Override
                    public void result(int number, int code, String des) {
                        //0库存充足 -1失败 1库存不足
                        switch (code) {
                            case 0:
                                tvNumber.setText(String.format("%d", number));
                                break;
                            default:
                                showToast(des);
                                break;
                        }
                    }
                });
            }
        });

        switch (type) {
            case 1:
                tvOk.setText("加入购物车车");
                tvOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Product product = new Product();
                        int number = Integer.parseInt(tvNumber.getText().toString().trim());
                        product.id = evaluate.product.id;
                        product.price = evaluate.product.price;
                        product.name = evaluate.product.name;
                        product.num = number;
                        product.pic = evaluate.product.pic1;
                        ShopCarUtils.getInstance().saveProduct(product);
                        refreshTabView();
                        dialog.dismiss();
                    }
                });
                break;
            case 2:
                tvOk.setText("立即购买");
                tvOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Product product = new Product();
                        ArrayList<Product> products = new ArrayList<Product>();
                        int number = Integer.parseInt(tvNumber.getText().toString().trim());
                        product.id = evaluate.product.id;
                        product.price = evaluate.product.price;
                        product.name = evaluate.product.name;
                        product.num = number;
                        product.pic = evaluate.product.pic1;
                        Bundle bundle = new Bundle();
                        products.add(product);
                        bundle.putSerializable(OrderAffirmActivity.PROLIST, products);
                        qStartActivity(OrderAffirmActivity.class, bundle);
                        dialog.dismiss();
                    }
                });
                break;
        }

    }

    @Override
    public void onRefresh(int index) {
        evaluate(1);
    }

    @Override
    public void onLoad(int index) {
        evaluate(++index);
    }
}
