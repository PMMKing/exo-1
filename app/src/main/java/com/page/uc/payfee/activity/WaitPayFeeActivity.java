package com.page.uc.payfee.activity;

import android.content.Context;
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
import com.framework.utils.ArrayUtils;
import com.framework.view.LineDecoration;
import com.page.uc.payfee.holder.WaitPayHolder;
import com.page.uc.payfee.model.WaitFeeParam;
import com.page.uc.payfee.model.WaitFeeResult;
import com.page.uc.payfee.model.WaitFeeResult.Data.Datas;
import com.qfant.wuye.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by shucheng.qu on 2017/9/14.
 */

public class WaitPayFeeActivity extends BaseActivity implements OnItemClickListener<Datas> {

    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_go_pay)
    TextView tvGoPay;
    private MultiAdapter adapter;
    private double totalPrices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_waitpayfee_layout);
        ButterKnife.bind(this);
        setTitleBar("待缴费用", true);
        setListView();
        startRequest();
    }

    private void startRequest() {
        Request.startRequest(new BaseParam(), ServiceMap.getMyWuyeFees, mHandler, Request.RequestFeature.BLOCK);
    }

    private void refreshMoney() {
        totalPrices = 0;
        List<Datas> datas = adapter.getData();
        if (!ArrayUtils.isEmpty(datas)) {
            for (Datas temp : datas) {
                if (temp.isSelect) {
                    totalPrices += temp.price;
                }
            }
        }
        tvMoney.setText(getContext().getResources().getString(R.string.rmb) + totalPrices);

    }

    private void setListView() {
        adapter = new MultiAdapter<Datas>(getContext()).addTypeView(new ITypeView() {
            @Override
            public boolean isForViewType(Object item, int position) {
                return true;
            }

            @Override
            public BaseViewHolder createViewHolder(Context mContext, ViewGroup parent) {
                return new WaitPayHolder(mContext, LayoutInflater.from(mContext).inflate(R.layout.pub_activity_waitpayfee_item_layout, parent, false));
            }
        });
        rvList.addItemDecoration(new LineDecoration(getContext()));
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvList.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

    }

    @OnClick(R.id.tv_go_pay)
    public void onViewClicked() {
        goPay();
    }

    private void goPay() {
        WaitFeeParam param = new WaitFeeParam();
        List<Datas> datas = adapter.getData();
        if (!ArrayUtils.isEmpty(datas)) {
            for (Datas temp : datas) {
                if (temp.isSelect) {
                    param.params.add(temp);
                }
            }
        }
        if (param.params.size() < 1) {
            showToast("没有选中待缴费用项");
            return;
        }
        Request.startRequest(param, ServiceMap.submitWuyeFee, mHandler, Request.RequestFeature.BLOCK);

    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.getMyWuyeFees) {
            WaitFeeResult result = (WaitFeeResult) param.result;
            if (result != null && result.data != null && !ArrayUtils.isEmpty(result.data.datas)) {
                adapter.setData(result.data.datas);
            } else {
                showToast(param.result.bstatus.des);
            }
        } else if (param.key == ServiceMap.submitWuyeFee) {
            showToast(param.result.bstatus.des);
        }
        return false;
    }

    @Override
    public void onItemClickListener(View view, Datas data, int position) {
        boolean isSelect = data.isSelect;
        data.isSelect = !isSelect;
        adapter.notifyDataSetChanged();
        refreshMoney();
    }
}
