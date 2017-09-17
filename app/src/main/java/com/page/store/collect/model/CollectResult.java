package com.page.store.collect.model;

import com.framework.domain.response.BaseResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shucheng.qu on 2017/9/12.
 */

public class CollectResult extends BaseResult {


    public Data data;

    public static class Data implements Serializable {
        public int totalNum;
        public List<PraiseList> praiseList;

        public static class PraiseList implements Serializable {
            public int id;
            public String name;
            public String pic1;
            public double price;
        }
    }
}
