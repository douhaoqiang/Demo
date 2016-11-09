package com.dhq.net;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * DESC 接口构造器
 * Created by douhaoqiang on 2016/11/9.
 */
public abstract class ServiceGenerator<T> {

    private static final int DEFAULT_TIMEOUT = 15;//默认连接超时时间15秒
    public Retrofit retrofit;
    private T service;

    public T getService(){
        if(service==null){
            init();
            return createService();
        }
        return service;
    }

    private void init(){

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addNetworkInterceptor(new MyIntercepter())
                .build();

        Retrofit.Builder builder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create());

        retrofit=builder
                .baseUrl(getBaseUrl())
                .client(httpClient).build();
    }

    public abstract String getBaseUrl();
    public abstract  T createService();

}
