package com.wuye.wuye.net.link;


import com.wuye.wuye.WApplication;
import com.wuye.wuye.config.AppConfig;
import com.wuye.wuye.config.DebugUtils;
import com.wuye.wuye.utils.LogUtils;
import com.wuye.wuye.utils.NetworkUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.annotations.NonNull;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by shucheng.qu on 2017/5/26.
 */

public class RetrofitFactor {

    private static long OUTTIME = 20L;
    private static Retrofit.Builder builder;
    private static OkHttpClient client;


    public static <T> T getService(Class<T> clazz){
        Retrofit.Builder builder= createRetrofitService();
        builder.baseUrl(DebugUtils.getBaseUrl());
        return builder.build().create(clazz);
    }

    /**
     * 设置头
     */
    private static Interceptor addHeaderInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(@NonNull Chain chain) {
                Request originalRequest = chain.request();
                Request.Builder requestBuilder = originalRequest.newBuilder()
                        .header("token", "123")
                        .method(originalRequest.method(), originalRequest.body());
                Request request = requestBuilder.build();
                try {
                    return chain.proceed(request);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }

    /**
     * 设置日志log
     *
     * @return
     */
    private static Interceptor httpLoggingInterceptor() {
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                LogUtils.d("logInterceptor=====>" + message);
            }
        });
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logInterceptor;
    }

    private static Retrofit.Builder createRetrofitService() {
        if (builder == null) {
            synchronized (RetrofitFactor.class) {
                if (builder == null) {
                    //设置 请求的缓存的大小跟位置
                    File cacheFile = new File(WApplication.getContext().getCacheDir(), "netcache");
                    Cache cache = new Cache(cacheFile, 1024 * 1024 * 30); //30Mb 缓存的大小
                    client = new OkHttpClient
                            .Builder()
//                            .addInterceptor(addQueryParameterInterceptor())  //参数添加
                            .addInterceptor(addHeaderInterceptor()) // token过滤
                            .addInterceptor(httpLoggingInterceptor()) //日志,所有的请求响应度看到
                            .addInterceptor(addCacheInterceptor())//缓存
                            .cache(cache)  //添加缓存
                            .connectTimeout(OUTTIME, TimeUnit.SECONDS)
                            .readTimeout(OUTTIME, TimeUnit.SECONDS)
                            .writeTimeout(OUTTIME, TimeUnit.SECONDS)
                            .build();
                    // 获取retrofit的实例
                    builder = new Retrofit
                            .Builder()
                            .baseUrl(AppConfig.getBaseUrl())
                            .client(client)
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                            .addConverterFactory(FastJsonConverterFactory.create())
                    ; //这里是用的fastjson的

                }
            }
        }

        return builder;
    }

    /**
     * 设置缓存
     */
    private static Interceptor addCacheInterceptor() {
         return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetworkUtil.isNetworkAvailable(WApplication.getContext())) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (NetworkUtil.isNetworkAvailable(WApplication.getContext())) {
                    int maxAge = 0;
                    // 有网络时 设置缓存超时时间0个小时 ,意思就是不读取缓存数据,只对get有用,post没有缓冲
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Retrofit")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                            .build();
                } else {
                    // 无网络时，设置超时为24h  只对get有用,post没有缓冲
                    int maxStale = 60 * 60 * 24;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" +
                                    maxStale)
                            .removeHeader("nyn")
                            .build();
                }
                return response;
            }
        };
    }

    /**
     * 设置公共参数
     */
    private static Interceptor addQueryParameterInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request request;
                HttpUrl modifiedUrl = originalRequest.url().newBuilder()
                        // Provide your custom parameter here
                        .addQueryParameter("phoneSystem", "")
                        .addQueryParameter("phoneModel", "")
                        .build();
                request = originalRequest.newBuilder().url(modifiedUrl).build();
                return chain.proceed(request);
            }
        };
    }

}
