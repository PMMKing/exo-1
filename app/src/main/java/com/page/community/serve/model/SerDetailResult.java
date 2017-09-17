package com.page.community.serve.model;

import com.framework.domain.response.BaseResult;

import java.io.Serializable;

/**
 * Created by shucheng.qu on 2017/9/16.
 */

public class SerDetailResult extends BaseResult {


    public Data data;

    public static class Data implements Serializable {
        public int id;
        public String title;
        public String phone;
        public String address;
        public String content;
    }
}
