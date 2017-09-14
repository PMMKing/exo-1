package com.page.home.model;

import com.framework.domain.response.BaseResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shucheng.qu on 2017/9/14.
 */

public class ShopRecResult extends BaseResult {

    public Data data;

    public static class Data implements Serializable {
        public int totalNum;
        public List<ProductList> productList;

        public static class ProductList implements Serializable {
            public String id;
            public String name;
            public String pic1;
            public String pic2;
            public String pic3;
            public Object intro;
            public Object praise;
            public Object storage;
            public int price;
            public int marketprice;
            public int category;
            public int comments;
        }
    }
}
