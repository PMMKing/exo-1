package com.page.store.orderaffirm.model;

import com.framework.domain.param.BaseParam;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by shucheng.qu on 2017/9/5.
 */

public class CommitOrderParam extends BaseParam {

    public String address;
    public String phone;
    public String receiver;
    public ArrayList<Product> products;
    public double totalprice;

    public static class Product implements Serializable {
        public String id;
        public int num;
        public double price;
        public String name;
        public String pic;
        public int storage;

        public DefaultAddressResult.Address address;
    }
}