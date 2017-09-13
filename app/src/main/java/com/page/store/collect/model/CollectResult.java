package com.page.store.collect.model;

import com.framework.domain.response.BaseResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shucheng.qu on 2017/9/12.
 */

public class CollectResult extends BaseResult {

    public List<Data> datas;

    public static class Data implements Serializable {
        public int id;
        public String name;
        public String pic1;
        public double price;
    }

}
