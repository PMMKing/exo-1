package com.page.store.orderlist.model;

import com.framework.domain.response.BaseResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shucheng.qu on 2017/9/10.
 */

public class OrderListResult extends BaseResult {


    public Data data;

    public static class Data implements Serializable {
        public int totalNum;
        public List<OrderList> orderList;

        public static class OrderList implements Serializable {
            public String id;
            public String orderno;
            public int totalprice;
            public String createtime;
            public int status;
            public Object paystatus;
            public List<Products> products;

            public static class Products implements Serializable {
                public int productid;
                public String productname;
                public String pic1;
                public int price;
                public int num;
            }
        }
    }
}
