package com.page.pay;

import android.app.Service;

import java.io.Serializable;

/**
 * Created by chenxi.cui on 2017/9/16.
 */

public class PayData implements Serializable {

    public int id;
    public double price;
    public int from;
    public String orderno;

}
