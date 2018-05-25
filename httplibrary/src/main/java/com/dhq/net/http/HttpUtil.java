package com.dhq.net.http;

import com.dhq.net.BaseObserver;
import com.dhq.net.MyIntercepter;
import com.dhq.net.entity.BaseResponse;
import com.dhq.net.util.DataUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.bind.CollectionTypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.xml.transform.Transformer;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
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
    private static final MediaType mediaTypeForm = MediaType.parse("application/x-www-form-urlencoded;charset=utf-8");


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

        GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create(new GsonBuilder().registerTypeAdapterFactory(new TypeAdapterFactory() {
            @Override
            public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
                Class<T> rawType = (Class<T>) type.getRawType();
                if (rawType != String.class) {
                    return null;
                }
                return (TypeAdapter<T>) new StringNullAdapter();
            }
        }).create());

        retrofit = new Retrofit.Builder()
                .client(httpClient)
                .addConverterFactory(gsonConverterFactory)
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

//        Observable<BaseResponse> observable = mApiService.getHttpRequest(url, paramMaps).map(new HttpResultFunc());
//        toSubscribe(observable, observer);

        mApiService.getHttpRequest(url, paramMaps).compose(new RxTransformer()).subscribe(observer);
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
        hashMap.put("data", jsonParam);
        mApiService.postFormHttpRequest(url, hashMap).compose(new RxTransformer()).subscribe(observer);

    }


    private class HttpResultFunc implements Function<BaseResponse, JsonObject> {

        @Override
        public JsonObject apply(BaseResponse response) throws Exception {
            return response.getResultMap();
        }
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

//        Observable<BaseResponse> observable = mApiService.postJsonHttpRequest(url, body).map(new HttpResultFunc());
//        toSubscribe(observable, observer);

        mApiService.postJsonHttpRequest(url, body).compose(new RxTransformer<>()).subscribe(observer);

    }

    /**
     * post请求 添加表json参数
     *
     * @param url
     * @param paramMaps
     * @return
     */
    public void postJsonHttpRequest(String url, Object paramMaps, Observer observer) {

//        String jsonParam = DataUtils.gsonObjectToJson(paramMaps);
//        RequestBody body = RequestBody.create(mediaTypeJson, jsonParam);
//
//        Observable<BaseResponse> observable = mApiService.postJsonHttpRequest(url, body).map(new HttpResultFunc());
//        toSubscribe(observable, observer);
    }


    public <T> void toSubscribe(Observable<T> observable, Observer<T> subscriber) {
        observable
                /*
                订阅关系发生在IO线程中
                 */
                .subscribeOn(Schedulers.io())
                /*
                解除订阅关系也发生在IO线程中
                 */
                .unsubscribeOn(Schedulers.io())
                /*
                指定subscriber (观察者)的回调在主线程中，
                observeOn的作用是指定subscriber（观察者）将会在哪个Scheduler观察这个Observable,
                由于subscriber已经能取到界面所关心的数据了，所以设定指定subscriber的回调在主线程中
                 */
                .observeOn(AndroidSchedulers.mainThread())
                /*
                订阅观察者，subscribe就相当于setOnclickListener()
                 */
                .subscribe(subscriber);
        //subscribeOn影响的是它调用之前的代码（也就是observable），observeOn影响的是它调用之后的代码（也就是subscribe()）
    }

    public class RxTransformer<T> implements ObservableTransformer<T,T>{

        @Override
        public ObservableSource apply(Observable<T> observable) {
            Observable<T> tObservable = observable
                /*
                订阅关系发生在IO线程中
                 */
                    .subscribeOn(Schedulers.io())
                /*
                解除订阅关系也发生在IO线程中
                 */
                    .unsubscribeOn(Schedulers.io())
                /*
                指定subscriber (观察者)的回调在主线程中，
                observeOn的作用是指定subscriber（观察者）将会在哪个Scheduler观察这个Observable,
                由于subscriber已经能取到界面所关心的数据了，所以设定指定subscriber的回调在主线程中
                 */
                    .observeOn(AndroidSchedulers.mainThread());
            return tObservable;
        }
    }

}
