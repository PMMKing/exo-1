package com.page.home.model;

import com.page.store.orderaffirm.model.CommitOrderParam;
import com.page.store.orderlist.model.OrderListResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shucheng.qu on 2017/9/13.
 */

public class ShopCarData implements Serializable {
    public ArrayList<CommitOrderParam.Product> products = new ArrayList<CommitOrderParam.Product>();
}
