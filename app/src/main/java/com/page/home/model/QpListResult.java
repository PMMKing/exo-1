package com.page.home.model;

import com.framework.domain.response.BaseResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shucheng.qu on 2017/8/30.
 */

public class QpListResult extends BaseResult {


    public Data data;

    public static class Data implements Serializable {
        public int totalNum;
        public List<Snapshots> snapshots;

        public static class Snapshots implements Serializable {
            public String id;
            public String pic1;
            public String pic2;
            public String pic3;
            public String intro;
            public String createtime;
        }
    }
}
