package com.page.community.quickpain.model;

import com.framework.domain.response.BaseResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shucheng.qu on 2017/8/31.
 */

public class QpDetailResult extends BaseResult {
    public Data data;

    public static class Data implements Serializable {
        public String pic1;
        public String pic2;
        public String pic3;
        public String intro;
    }
}
