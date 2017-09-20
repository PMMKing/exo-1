package com.page.store.orderdetails.model;

import com.framework.domain.response.BaseResult;
import com.page.store.orderlist.model.OrderListResult;
import com.page.store.orderlist.model.OrderListResult.Data.OrderList.Products;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shucheng.qu on 2017/9/12.
 */

public class OrderDetailResult extends BaseResult {

    public Data data;

    public static class Data implements Serializable {
        public int id;
        public String orderno;
        public int totalprice;
        public String createtime;
        public int status;
        public String address;
        public String receiver;
        public String phone;
        public String logistics;
        public String statusCN;
        public String reason;
        public List<Products> products;

    }
}
