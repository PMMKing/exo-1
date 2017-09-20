package com.page.uc.payfee.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.framework.activity.BaseFragment;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.rvadapter.adapter.MultiAdapter;
import com.framework.rvadapter.click.OnItemClickListener;
import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.rvadapter.manage.ITypeView;
import com.framework.utils.Arith;
import com.framework.utils.ArrayUtils;
import com.framework.utils.DateFormatUtils;
import com.framework.view.DatePickerDialog;
import com.framework.view.LineDecoration;
import com.page.pay.PayActivity;
import com.page.pay.PayData;
import com.page.uc.payfee.model.ubmitWuyeFeeResult;
import com.page.uc.payfee.holder.WaitPayHolder;
import com.page.uc.payfee.model.WaitFeeParam;
import com.page.uc.payfee.model.WaitFeeQueryParam;
import com.page.uc.payfee.model.WaitFeeResult;
import com.page.uc.payfee.model.WaitFeeResult.Data.Datas;
import com.qfant.wuye.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by shucheng.qu on 2017/9/16.
 */

public class WaitFeeFragment extends BaseFragment implements OnItemClickListener<Datas> {

    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_go_pay)
    TextView tvGoPay;
    Unbinder unbinder;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.tv_query)
    TextView tvQuery;
    private MultiAdapter adapter;
    private double totalPrices;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pub_activity_waitpayfee_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListView();
        startRequest();
    }

    private void startRequest() {
        String startTime = tvStartTime.getText().toString().trim();
        String endTime = tvEndTime.getText().toString().trim();
//        if (TextUtils.isEmpty(startTime)) {
//            showToast("请选择开始时间");
//            return;
//        }
//        if (TextUtils.isEmpty(endTime)) {
//            showToast("请选择结束时间");
//            return;
//        }
        WaitFeeQueryParam param = new WaitFeeQueryParam();
        param.startdate = DateFormatUtils.format(startTime, "yyyy-M-d", "yyyy-MM-dd");
        param.enddate = DateFormatUtils.format(endTime, "yyyy-M-d", "yyyy-MM-dd");
        ;
        Request.startRequest(param, ServiceMap.getMyWuyeFees, mHandler, Request.RequestFeature.BLOCK);
    }

    private void refreshMoney() {
        totalPrices = 0;
        List<Datas> datas = adapter.getData();
        if (!ArrayUtils.isEmpty(datas)) {
            for (Datas temp : datas) {
                if (temp.isSelect) {
                    totalPrices = Arith.add(totalPrices, temp.price);
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
        rvList.setNestedScrollingEnabled(false);
    }

    private void goPay() {
        WaitFeeParam param = new WaitFeeParam();
        List<Datas> datas = adapter.getData();
        if (!ArrayUtils.isEmpty(datas)) {
            for (Datas temp : datas) {
                if (temp.isSelect) {
                    WaitFeeParam.WaitFeeItem item = new WaitFeeParam.WaitFeeItem();
                    item.startdate = temp.startdate;
                    item.enddate = temp.enddate;
                    item.yearmonth = temp.yearmonth;
                    item.price = temp.price;
                    param.params.add(item);
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
            ubmitWuyeFeeResult result = (ubmitWuyeFeeResult) param.result;
            Bundle bundle = new Bundle();
            PayData payData = new PayData();
            payData.id = result.data.orderid;
            payData.price = result.data.totalprice;
            payData.from = -1;
            payData.orderno = result.data.orderno;
            bundle.putSerializable("order", payData);
            qStartActivity(PayActivity.class, bundle);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_start_time, R.id.tv_end_time, R.id.tv_query, R.id.tv_go_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_start_time:
                new DatePickerDialog(getContext()).showDatePickerDialogNo(new DatePickerDialog.DatePickerDialogInterface() {

                    @Override
                    public void sure(int year, int month, int day) {
                        tvStartTime.setText(String.format("%d-%d-%d", year, month, day));
                    }

                    @Override
                    public void cancle() {

                    }
                });
                break;
            case R.id.tv_end_time:
                String startTime = tvStartTime.getText().toString().trim();
                if (TextUtils.isEmpty(startTime)) {
                    showToast("先选择开始时间");
                    return;
                }
                new DatePickerDialog(getContext()).showDatePickerDialog(DateFormatUtils.formatLong(startTime, "yyyy-M-d"), new DatePickerDialog.DatePickerDialogInterface() {
                    @Override
                    public void sure(int year, int month, int day) {
                        tvEndTime.setText(String.format("%d-%d-%d", year, month, day));
                    }

                    @Override
                    public void cancle() {

                    }
                });
                break;
            case R.id.tv_query:
                startRequest();
                break;
            case R.id.tv_go_pay:
                goPay();
                break;
        }
    }
}
