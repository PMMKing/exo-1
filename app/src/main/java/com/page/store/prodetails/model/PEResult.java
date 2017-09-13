package com.page.store.prodetails.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.framework.domain.response.BaseResult;

import java.util.List;

/**
 * Created by shucheng.qu on 2017/9/12.
 */

public class PEResult extends BaseResult {

    public Data data;

    public static class Data {
        public int totalNum;
        public List<Evaluate> datas;
    }

    public static class Evaluate {

        public String id;
        public String content;
        public String createtime;
        public String nickname;
        public String headimg;
        public String userid;

        public PDResult.Data product;

    }
}
