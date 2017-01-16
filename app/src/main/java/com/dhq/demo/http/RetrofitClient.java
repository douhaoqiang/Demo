package com.dhq.demo.http;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.dhq.demo.entity.BaseEntity;
import com.dhq.demo.http.api.TestApiService;
import com.dhq.demo.http.entity.BaseResponse;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * DESC
 * Created by douhaoqiang on 2017/1/13.
 */
public class RetrofitClient {

    private static final int DEFAULT_TIMEOUT = 5;

    private TestApiService apiService;

    private OkHttpClient okHttpClient;

    public static String baseUrl = TestApiService.Base_URL;

    private static Context mContext;

    private static RetrofitClient sNewInstance;

    private static class SingletonHolder {
        private static RetrofitClient INSTANCE = new RetrofitClient(
                mContext);
    }

    public static RetrofitClient getInstance(Context context) {
        if (context != null) {
            mContext = context;
        }
        return SingletonHolder.INSTANCE;
    }


    public static RetrofitClient getInstance(Context context, String url) {
        if (context != null) {
            mContext = context;
        }
        sNewInstance = new RetrofitClient(context, url);
        return sNewInstance;
    }

    private RetrofitClient(Context context) {

        this(context, null);
    }

    private RetrofitClient(Context context, String url) {

        if (TextUtils.isEmpty(url)) {
            url = baseUrl;
        }
        okHttpClient = new OkHttpClient.Builder()
//                .addNetworkInterceptor(
//                        new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
//                .cookieJar(new NovateCookieManger(context))
//                .addInterceptor(new BaseInterceptor(mContext))
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(url)
                .build();
        apiService = retrofit.create(TestApiService.class);
    }


    public void getData(Subscriber<ResponseBody> subscriber, String ip) {
        apiService.getDate("").enqueue(new RespCallBack<BaseEntity>() {
            @Override
            public void success(BaseEntity result) {

            }

            @Override
            public void fail(String msg) {

            }
        });
//        apiService.getData(ip)
//                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(subscriber);
//    }
//
//    public void get(String url, Map headers, Map parameters, Subscriber<ResponseBody> subscriber) {
//        apiService.executeGet(url, headers, parameters)
//                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(subscriber);
//    }
//
//    public void post(String url, Map headers, Map parameters, Subscriber<ResponseBody> subscriber) {
//        apiService.executePost(url, headers, parameters)
//                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(subscriber);
//    }

    }
}