package com.page.home.model;

import com.framework.domain.response.BaseResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shucheng.qu on 2017/9/13.
 */

public class NoticeResult extends BaseResult {

    public Data data;

    public static class Data implements Serializable {
        public List<Datas> datas;

        public static class Datas implements Serializable {
            public int id;
            public String title;
            public String content;
            public String createtime;
        }
    }
}
