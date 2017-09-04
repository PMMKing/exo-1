package com.page.community.serve.model;

import com.framework.domain.response.BaseResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shucheng.qu on 2017/8/24.
 */

public class RepairResult extends BaseResult {


    public Data data;

    public static class Data implements Serializable {
        public int totalNum;
        public List<RepairList> repairList;

        public static class RepairList implements Serializable {
            public String id;
            public String address;
            public String createtime;
            public String status;
            public String phone;
            public String pic;
            public String intro;
        }

    }
}
