package com.page.community.details.model;

import com.framework.domain.response.BaseResult;

import java.io.Serializable;

/**
 * Created by shucheng.qu on 2017/9/4.
 */

public class RepairDetailResult extends BaseResult {

    public Data data;

    public static class Data implements Serializable {
        public int id;
        public int status;
        public String pic;
        public String intro;
        public String phone;
        public String address;
        public String evaluate;
        public String comment;
        public String workername;
        public String workerphone;
    }
}
