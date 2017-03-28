package com.dhq.net.http;

import com.dhq.net.BaseObserver;
import com.dhq.net.MyIntercepter;
import com.dhq.net.util.DataUtils;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * DESC
 * Created by douhaoqiang on 2017/3/28.
 */

public class HttpUtil {


    private static HttpUtil mHttpUtil;

    private static final MediaType mediaTypeJson = MediaType.parse("application/json; charset=utf-8");


    private static final int CONNECT_TIMEOUT = 15;
    private static final int READ_TIMEOUT = 30;
    private static final String baseUrl = "http://www.baidu.com/";

    private Retrofit retrofit;
    private ApiService mApiService;

    private HttpUtil() {
        retrofitBuild();
    }

    public static HttpUtil getInstance() {
        if (mHttpUtil == null) {
            synchronized (HttpUtil.class) {
                if (mHttpUtil == null) {
                    mHttpUtil = new HttpUtil();
                }
            }
        }
        return mHttpUtil;
    }


    /**
     * 初始化网络请求
     */
    private void retrofitBuild() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .addNetworkInterceptor(new MyIntercepter())
                .build();

        retrofit = new Retrofit.Builder()
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();
        mApiService = retrofit.create(ApiService.class);
    }


    /**
     * get请求 添加参数
     *
     * @param url
     * @param paramMaps
     * @return
     */
    public void getHttpRequest(String url, HashMap<String, String> paramMaps, Observer observer) {
        mApiService.getHttpRequest(url, paramMaps)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * post请求 添加表单参数
     *
     * @param url
     * @param paramMaps
     * @param observer
     */
    public void postFormHttpRequest(String url, HashMap<String, String> paramMaps, BaseObserver observer) {
        String jsonParam = DataUtils.mapToJson(paramMaps);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("data",jsonParam);
        mApiService.postFormHttpRequest(url, hashMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


    /**
     * post请求 添加表json参数
     *
     * @param url
     * @param paramMaps
     * @return
     */
    public void postJsonHttpRequest(String url, HashMap<String, String> paramMaps, Observer observer) {

        String jsonParam = DataUtils.mapToJson(paramMaps);
        RequestBody body = RequestBody.create(mediaTypeJson, jsonParam);

        mApiService.postJsonHttpRequest(url, body)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


}
