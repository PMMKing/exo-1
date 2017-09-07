package com.page.store.orderlist.model;

import com.framework.domain.param.BaseParam;

/**
 * Created by shucheng.qu on 2017/9/6.
 */

public class OrderListParam extends BaseParam {

    public int type;
    public int pageNo = 1;
    public int pageSize = 20;
}
