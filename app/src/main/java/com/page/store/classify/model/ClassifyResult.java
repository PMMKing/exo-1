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
            public int recommend;
            public String imgurl;
        }
    }
}
