package com.page.community.quickpain.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.framework.domain.response.BaseResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shucheng.qu on 2017/8/30.
 */

public class ScommentsReault extends BaseResult {

    public Data data;

    public static class Data implements Serializable {
        public int totalNum;
        public List<Datas> datas;

        public static class Datas implements Serializable {
            public String id;
            public String content;
            public String customerid;
            public String refid;
            public String refuserid;
            public Object refnickname;
            public String headimg;
            public String nickname;
            public String refcontent;
            public String createtime;


            public QpDetailResult.Data hearderData;
        }
    }
}
