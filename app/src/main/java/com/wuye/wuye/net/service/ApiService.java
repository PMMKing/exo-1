package com.wuye.wuye.net.service;



import com.wuye.wuye.config.AppConfig;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by shucheng.qu on 2017/5/26.
 */

public interface ApiService{

    @GET(AppConfig.CONTENT_URL)
    Observable<ResponseBody> request(@QueryMap HashMap<String, Object> paramMap);

}
