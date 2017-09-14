package com.page.store.home.model;

import com.framework.domain.response.BaseResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shucheng.qu on 2017/9/14.
 */

public class FoodRecResult extends BaseResult {


    public Data data;

    public static class Data implements Serializable {
        public int id;
        public String name;
        public String imgurl;
        public List<Products> products;

        public static class Products implements Serializable {
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
            public Object category;
            public int comments;
        }
    }
}
