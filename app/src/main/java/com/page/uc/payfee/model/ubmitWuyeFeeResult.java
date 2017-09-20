package com.page.uc.payfee.model;

import com.framework.domain.response.BaseResult;

/**
 * Created by chenxi.cui on 2017/9/16.
 */

public class ubmitWuyeFeeResult extends BaseResult {
    public ubmitWuyeFeeData data;

    public static class ubmitWuyeFeeData implements BaseData {
        public double totalprice;
        public String ordername;
        public String orderno;
        public int orderid;
    }
}
