package com.dhq.net.http2;

import android.text.TextUtils;
import android.util.Log;

import com.dhq.net.BaseObserver;
import com.dhq.net.MyIntercepter;
import com.dhq.net.entity.BaseResponse;
import com.dhq.net.entity.LoginEntity;
import com.dhq.net.util.DataUtils;
import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * DESC
 * Created by douhaoqiang on 2017/3/28.
 */

public class HttpUtil2 {


    private static final String TAG = HttpUtil2.class.getSimpleName();

    private static HttpUtil2 mHttpUtil;

    private static final MediaType mediaTypeJson = MediaType.parse("application/json; charset=utf-8");


    private static final int CONNECT_TIMEOUT = 15;
    private static final int READ_TIMEOUT = 30;
    private static final String baseUrl = "http://www.baidu.com/";

    private Retrofit retrofit;
    private ApiService2 mApiService;

    private Gson gson = new Gson();

    private HttpUtil2() {
        retrofitBuild();
    }

    public static HttpUtil2 getInstance() {
        if (mHttpUtil == null) {
            synchronized (HttpUtil2.class) {
                if (mHttpUtil == null) {
                    mHttpUtil = new HttpUtil2();
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
        mApiService = retrofit.create(ApiService2.class);
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
    }

    /**
     * post请求 添加表单参数
     *
     * @param url
     * @param paramMaps
     */
    public <T> void postFormHttpRequest(String url, HashMap<String, String> paramMaps, HttpCallback<T> callback) {
        String jsonParam = DataUtils.mapToJson(paramMaps);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("data", jsonParam);
        Call<ResponseBody> request = mApiService.postFormHttpRequest(url, hashMap);
        postRequest(request, callback);
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
    }

    private void postRequest(final Call<ResponseBody> request, final HttpCallback callback) {

        Observable.create(new ObservableOnSubscribe<BaseResponse>() {
            @Override
            public void subscribe(final ObservableEmitter<BaseResponse> e) throws Exception {
                Response<ResponseBody> response = request.execute();
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    Log.d(TAG,"返回数据："+responseData);
                    BaseResponse<LoginEntity> result = gson.fromJson(responseData, BaseResponse.class);
                    e.onNext(result);
                } else {
                    Log.d(TAG,"请求失败状态码："+response.code()+"---"+response.errorBody());
                    e.onError(new Throwable());
                }

            }

        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse response) {
                        if ("success".equals(response.getResult())) {
                            callback.success(response.getResultMap());
                        } else {
                            callback.fail(response.getResult());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.fail("数据加载失败!");
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }


    public interface HttpCallback<T> {
        void success(T result);

        void fail(String msg);
    }


}
