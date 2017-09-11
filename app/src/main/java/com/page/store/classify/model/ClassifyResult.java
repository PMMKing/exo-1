package com.page.store.classify.model;

import com.framework.domain.response.BaseResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shucheng.qu on 2017/9/6.
 */

public class ClassifyResult extends BaseResult {

    public Data data;

    public static class Data implements Serializable {
        public List<Datas> datas;

        public static class Datas implements Serializable {
            public int id;
            public String name;
            public int sort;
            public Object recommend;
            public String imgurl;
            public List<Produts> produts;

            public boolean isSelect;

            public static class Produts implements Serializable {
                public String id;
                public String name;
                public String pic1;
                public String pic2;
                public String pic3;
                public String intro;
                public int praise;
                public double price;
                public double marketprice;
                public int category;
                public int comments;
            }
        }
    }
}
