package com.page.store.prodetails.model;

import com.framework.domain.response.BaseResult;

import java.io.Serializable;

/**
 * Created by shucheng.qu on 2017/9/6.
 */

public class PDResult extends BaseResult {


    public Data data;

    public static class Data implements Serializable {
        public String id;
        public String name;
        public String pic1;
        public String pic2;
        public String pic3;
        public String intro;
        public double price;
        public double marketprice;
        public String category_id;
        public String categoryName;
        public int storage;
        public int isFav;//0 no 1 have
    }
}
