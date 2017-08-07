package com.wuye.wuye.net.link;

import com.alibaba.fastjson.JSON;
import com.wuye.wuye.ServiceID;
import com.wuye.wuye.net.base.BaseParam;
import com.wuye.wuye.net.link.netlistener.NetWorkListener;
import com.wuye.wuye.net.link.param.NetworkParam;
import com.wuye.wuye.net.service.ApiService;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;


/**
 * Created by shucheng.qu on 2017/5/26.
 */

public class Request {

    public static Disposable startRequest(NetWorkListener netWorkListener, ServiceID serviceID, final BaseParam param) {
        return startRequest(netWorkListener, serviceID, param, null);
    }

    public static Disposable startRequest(NetWorkListener netWorkListener, ServiceID serviceID, final BaseParam param, Serializable ext) {
        NetworkParam networkParam = getNetworkParam(param, serviceID);
        networkParam.ext = ext;
        return startRequest(netWorkListener, networkParam);
    }

    private static Disposable startRequest(final NetWorkListener netWorkListener, final NetworkParam param) {

        final Disposable[] disposable = {null};
        Observable<ResponseBody> observable = RetrofitFactor.getService(ApiService.class).request(param.param.toHashMap());
        observable.subscribeOn(Schedulers.io())
                .throttleFirst(1000, TimeUnit.MILLISECONDS)//抖动
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        netWorkListener.onNetStart(param);
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable[0] = d;
                    }

                    @Override
                    public void onNext(@NonNull ResponseBody result) {
                        try {
                            param.result = JSON.parseObject(result.string(), param.key.getClazz());
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            netWorkListener.onMsgSearchComplete(param);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        netWorkListener.onNetError(param);
                        netWorkListener.onNetEnd(param);
                    }

                    @Override
                    public void onComplete() {
                        netWorkListener.onNetFinish(param);
                        netWorkListener.onNetEnd(param);
                    }
                });

        return disposable[0];
    }

    private static NetworkParam getNetworkParam(BaseParam param, ServiceID serviceID) {
        NetworkParam networkParam = new NetworkParam();
        networkParam.key = serviceID;
        param.service = serviceID.getServiceID();
        networkParam.param = param;
        return networkParam;
    }

    public enum RequestFeature {
        BLOCK,
        CANCELABLE,
        ADD_ONORDER {},
        ADD_INSERT2HEAD {},
        ADD_CANCELSAMET {},
        CACHE_FORCE {},
        CACHE_DEP {},
        CACHE_NEVER {},
        CACHE_DOB {},
        MULTI_TRANSFER,
        PROGRESS;
    }

}
