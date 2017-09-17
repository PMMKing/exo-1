package com.page.uc.payfee.model;

import com.framework.domain.response.BaseResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shucheng.qu on 2017/9/17.
 */

public class FeeMonthResult extends BaseResult {


    public Data data;

    public static class Data implements Serializable {
        public int totalNum;
        public List<DatasX> datas;

        public static class DatasX implements Serializable {
            public String yearmonth;
            public String startdate;
            public String enddate;
            public String monthfee;
            public String price;
            public int type;
            public String createtime;

        }

    }
}
