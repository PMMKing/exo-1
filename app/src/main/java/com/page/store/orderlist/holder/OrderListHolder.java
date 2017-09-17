package com.page.store.orderlist.holder;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.utils.ArrayUtils;
import com.framework.utils.Dimen;
import com.page.store.orderlist.inteface.OnOrderStatusCallBack;
import com.page.store.orderlist.model.OrderListResult.Data.OrderList;
import com.page.store.orderlist.view.ProductView;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shucheng.qu on 2017/8/18.
 */

public class OrderListHolder extends BaseViewHolder<OrderList> {


    private final OnOrderStatusCallBack onOrderStatusCallBack;
    @BindView(R.id.ll_products)
    LinearLayout llProducts;
    @BindView(R.id.ll_order_status)
    LinearLayout llOrderStatus;

    public OrderListHolder(Context context, View itemView, OnOrderStatusCallBack onOrderStatusCallBack) {
        super(context, itemView);
//        R.layout.pub_fragment_orderlist_item_layout;
        this.onOrderStatusCallBack = onOrderStatusCallBack;
        ButterKnife.bind(this, itemView);
    }

    //1 等待支付 2订单取消 3 支付成功 4 支付失败 5已发货6申请退款 7退款完成8订单完成9已评价
    @Override
    public void onBindViewHolder(BaseViewHolder holder, OrderList data, int position) {
        llProducts.removeAllViews();
        llOrderStatus.removeAllViews();
        if (data == null || ArrayUtils.isEmpty(data.products)) return;
        for (OrderList.Products product : data.products) {
            ProductView productView = new ProductView(mContext);
            productView.updataView(product);
            llProducts.addView(productView);
        }
        switch (data.status) {
            case 1:
                orderCancle(data.id);
                orderPay(data.id, data.totalprice);
                break;
            case 2:
//                orderCancle(data.id);
                break;
            case 3:
                orderBack(data.id);
                orderConfirm(data.id);
                break;
            case 4:

                break;
            case 5:
                orderBack(data.id);
                break;
            case 6:

                break;
            case 7:

                break;
            case 8:
                orderEvaluate(data.id);
                break;
            case 9:

                break;
        }

    }

    private TextView getTextView(String text) {
        TextView textView = new TextView(mContext);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(Dimen.dpToPx(80), Dimen.dpToPx(33));
        params.leftMargin = Dimen.dpToPx(10);
        textView.setText(text);
        textView.setTextColor(mContext.getResources().getColor(R.color.pub_color_blue));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        textView.setGravity(Gravity.CENTER);
        textView.setClickable(true);
        textView.setBackgroundResource(R.drawable.pub_btn_blue_box_selector);
        textView.setLayoutParams(params);
        return textView;
    }


    private void orderCancle(final String id) {
        TextView textView = getTextView("取消");
        llOrderStatus.addView(textView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onOrderStatusCallBack != null) {
                    onOrderStatusCallBack.orderCancle(id);
                }
            }
        });
    }

    private void orderPay(final String id, final double totalprice) {
        TextView textView = getTextView("支付");
        llOrderStatus.addView(textView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onOrderStatusCallBack != null) {
                    onOrderStatusCallBack.orderPay(id, totalprice);
                }
            }
        });
    }

    private void orderEvaluate(final String id) {
        TextView textView = getTextView("评价");
        llOrderStatus.addView(textView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onOrderStatusCallBack != null) {
                    onOrderStatusCallBack.orderEvaluate(id);
                }
            }
        });
    }

    private void orderBack(final String id) {
        TextView textView = getTextView("退款");
        llOrderStatus.addView(textView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onOrderStatusCallBack != null) {
                    onOrderStatusCallBack.orderBack(id);
                }
            }
        });
    }

    private void orderConfirm(final String id) {
        TextView textView = getTextView("收货");
        llOrderStatus.addView(textView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onOrderStatusCallBack != null) {
                    onOrderStatusCallBack.orderConfirm(id);
                }
            }
        });
    }

}
