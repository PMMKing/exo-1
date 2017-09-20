package com.page.store.orderlist.inteface;

/**
 * Created by shucheng.qu on 2017/9/17.
 */

public interface OnOrderStatusCallBack {

    void orderCancle(String id);//取消

    void orderPay(String id, double totalprice,String orderno);//支付

    void orderEvaluate(String id);//评价

    void orderBack(String id);//退款

    void orderConfirm(String id);//确认收货
}
