package com.wuye.wuye.net.link.param;

import com.wuye.wuye.ServiceID;
import com.wuye.wuye.net.base.BaseParam;
import com.wuye.wuye.net.base.BaseResult;

import java.io.Serializable;

/**
 * Created by shucheng.qu on 2017/5/26.
 */

public class NetworkParam {
    public BaseParam param;
    public BaseResult result;
    public Serializable ext;
    public String progressMessage = "";
    public boolean block = false;
    public boolean cancelAble = false;
    public int errCode;

    public ServiceID key;
}
