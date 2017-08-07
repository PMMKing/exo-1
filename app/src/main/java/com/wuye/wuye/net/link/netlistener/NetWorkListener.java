package com.wuye.wuye.net.link.netlistener;

import com.wuye.wuye.net.link.param.NetworkParam;

/**
 * Created by shucheng.qu on 2017/5/26.
 */

public interface NetWorkListener {

    void onNetStart(NetworkParam param);

    void onNetEnd(NetworkParam param);

    void onNetError(NetworkParam param);

    void onNetFinish(NetworkParam param);

    void onMsgSearchComplete(NetworkParam param);

    void onNetCancel(NetworkParam param);

    void onCacheHit(NetworkParam param);
}
