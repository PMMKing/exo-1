package com.wuye.wuye.nets;

import android.os.Handler;

/**
 * 
 * @author zexu
 *
 */
public class NetworkTask {

    public boolean cancel = false;
    public final NetworkParam param;
    public final Handler handler;

    public NetworkTask(NetworkParam p, Handler handler) {
        this.param = p;
        this.handler = handler;
    }

}
