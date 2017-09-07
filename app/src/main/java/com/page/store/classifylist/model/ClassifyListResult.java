package com.page.store.classifylist.model;

import com.framework.domain.response.BaseResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shucheng.qu on 2017/9/6.
 */

public class ClassifyListResult extends BaseResult {


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
            public String intro;
            public int praise;
            public int status;
            public double price;
            public double marketprice;
            public int category;
            public String categoryName;
            public int comments;
        }
    }
}
