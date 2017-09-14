package com.page.uc.payfee.model;

import com.framework.domain.response.BaseResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shucheng.qu on 2017/9/14.
 */

public class WaitFeeResult extends BaseResult {

    public Data data;

    public static class Data implements Serializable {
        public List<Datas> datas;

        public static class Datas implements Serializable {
            public int roomid;
            public String startdate;
            public String enddate;
            public String yearmonth;
            public double price;
            public boolean isSelect;
        }
    }
}
