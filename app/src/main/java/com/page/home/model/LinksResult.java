package com.page.home.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.framework.domain.response.BaseResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shucheng.qu on 2017/8/22.
 */

public class LinksResult extends BaseResult {


    //    @JSONField(name = "bstatus")
//    public Bstatus bstatusX;
    public Data data;

//    public static class Bstatus {
//        public int code;
//        public String des;
//        public int hasmessage;
//    }

    public static class Data implements Serializable {
        public List<Links> links;

        public static class Links {
            public int id;
            public String title;
            public String imgurl;
            public String link;
        }
    }
}
