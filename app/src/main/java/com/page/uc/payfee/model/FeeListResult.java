package com.page.uc.payfee.model;

import com.framework.domain.response.BaseResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shucheng.qu on 2017/9/17.
 */

public class FeeListResult extends BaseResult {


    public Data data;

    public static class Data implements Serializable {
        public int totalNum;
        public List<DatasX> datas;

        public static class DatasX implements Serializable {
            public String orderno;
            public String ordername;
            public int totalprice;
            public int status;
            public String statusCN;
            public String paystatus;
            public String createtime;
            public String paytime;
            public List<Datas> datas;

            public static class Datas implements Serializable {
                public String yearmonth;
                public String startdate;
                public String enddate;
                public int price;
            }
        }
    }
}
