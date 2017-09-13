package com.page.store.prodetails.utils;

import android.os.Handler;

import com.framework.domain.param.BaseParam;
import com.framework.net.NetworkListener;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.utils.HandlerCallbacks;
import com.page.uc.UCUtils;

/**
 * Created by shucheng.qu on 2017/9/12.
 */

public class CheckRepertoryUtils implements NetworkListener {

    private static CheckRepertoryUtils instance;
    private Handler handler = new Handler(new HandlerCallbacks.ActivityCallback(this, null));
    private CheckCallBack callBack;

    private CheckRepertoryUtils() {
    }

    public static CheckRepertoryUtils getInstance() {
        if (instance == null) {
            synchronized (UCUtils.class) {
                if (instance == null) {
                    instance = new CheckRepertoryUtils();
                }
            }
        }
        return instance;
    }

    public void check(int num, String id, CheckCallBack callBack) {
        this.callBack = callBack;
        CheckParam param = new CheckParam();
        param.num = num;
        param.productId = id;
        Request.startRequest(param, num, ServiceMap.validStorage, handler, Request.RequestFeature.ADD_INSERT2HEAD);
    }

    @Override
    public void onNetStart(NetworkParam param) {

    }

    @Override
    public void onNetEnd(NetworkParam param) {
    }

    @Override
    public void onNetError(NetworkParam param, int errCode) {

    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        callBack.result((int) param.ext, param.result.bstatus.code, param.result.bstatus.des);
        return false;
    }

    @Override
    public void onNetCancel(NetworkParam param) {

    }

    public interface CheckCallBack {
        void result(int number, int code, String des);
    }

    public static class CheckParam extends BaseParam {
        public int num;
        public String productId;
    }
}
