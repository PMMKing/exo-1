package com.page.store.orderaffirm.model;

import com.framework.domain.param.BaseParam;

/**
 * Created by shucheng.qu on 2017/9/5.
 */

public class CommitOrderParam extends BaseParam {

    public String address;
    public String phone;
    public String receiver;
    public String products;
    public double totalprice;

    public static class Product {
        public String id;
        public int num;
    }
}