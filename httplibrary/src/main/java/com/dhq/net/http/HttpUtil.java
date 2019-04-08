package com.dhq.net.http;

import com.dhq.net.BaseObserver;
import com.dhq.net.DownLoadObserver;
import com.dhq.net.MyIntercepter;
import com.dhq.net.util.DataUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * DESC
 * Created by douhaoqiang on 2017/3/28.
 */

public class HttpUtil {

    private static String TAG = HttpUtil.class.getSimpleName();

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
    public void getReq(String url, HashMap<String, Object> paramMaps, Observer observer) {

        mApiService.getReq(url, paramMaps).compose(new RxTransformer()).subscribe(observer);
    }

    /**
     * get请求 添加参数
     *
     * @param url
     * @param paramMaps
     * @return
     */
    public void downLoadFileReq(String url, HashMap<String, Object> paramMaps, DownLoadObserver observer) {

        mApiService.downLoadFileReq(url, paramMaps).compose(new RxDownloadTransformer(observer)).subscribe(observer);
    }

    /**
     * 上传文件请求
     *
     * @param url
     * @return
     */
    public void uploadFileReq(String url, List<File> files, BaseObserver observer) {
        uploadFileReq(url, null, files, observer);
    }

    /**
     * 上传文件请求
     *
     * @param url
     * @return
     */
    public void uploadFileReq(String url, Map<String, String> map, List<File> files, BaseObserver observer) {
//        File file = new File(picPath);
        MultipartBody.Builder builder = new MultipartBody.Builder();

        if (files != null) {
            for (File file : files) {
                RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                builder.setType(MultipartBody.FORM)
//                    .addFormDataPart("phone", phone)
//                    .addFormDataPart("password", password)
                        .addFormDataPart("uploadFile", file.getName(), imageBody);
            }
        }

        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String mapKey = entry.getKey();
                String mapValue = entry.getValue();
                builder.addFormDataPart(mapKey, mapValue);
            }
        }

        List<MultipartBody.Part> parts = builder.build().parts();
        if (parts==null || parts.size()==0){

            return;
        }
        mApiService.uploadFileReq(url, parts).compose(new RxTransformer()).subscribe(observer);
    }

    /**
     * post请求 添加表单参数
     *
     * @param url
     * @param paramMaps
     * @param observer
     */
    public void postFormReq(String url, HashMap<String, Object> paramMaps, BaseObserver observer) {
//        String jsonParam = DataUtils.mapToJson(paramMaps);
//        HashMap<String, Object> hashMap = new HashMap<>();
//        hashMap.put("data", jsonParam);
        mApiService.postFormReq(url, paramMaps).compose(new RxTransformer()).subscribe(observer);

    }

    /**
     * post请求 添加表json参数
     *
     * @param url
     * @param paramMaps
     * @return
     */
    public void postFormBodyReq(String url, HashMap<String, String> paramMaps, Observer observer) {

        String jsonParam = DataUtils.mapToJson(paramMaps);
        RequestBody body = RequestBody.create(mediaTypeJson, jsonParam);

        mApiService.postJsonReq(url, body).compose(new RxTransformer<>()).subscribe(observer);

    }

    public class RxTransformer<T> implements ObservableTransformer<T, T> {

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
//                    .unsubscribeOn(Schedulers.io())
                    /*
                    指定subscriber (观察者)的回调在主线程中，
                    observeOn的作用是指定subscriber（观察者）将会在哪个Scheduler观察这个Observable,
                    由于subscriber已经能取到界面所关心的数据了，所以设定指定subscriber的回调在主线程中
                     */
                    .observeOn(AndroidSchedulers.mainThread());
            return tObservable;
        }
    }

    public class RxDownloadTransformer implements ObservableTransformer<ResponseBody, File> {
        private DownLoadObserver mLoadObserver;

        public RxDownloadTransformer(DownLoadObserver loadObserver) {
            this.mLoadObserver = loadObserver;
        }

        @Override
        public ObservableSource apply(Observable<ResponseBody> observable) {
            Observable<File> tObservable = observable
                    /*
                    订阅关系发生在IO线程中
                     */
                    .subscribeOn(Schedulers.io())
                    /*
                    解除订阅关系也发生在IO线程中
                     */
//                    .unsubscribeOn(Schedulers.io())
                    .observeOn(Schedulers.computation())//需要
                    .map(new Function<ResponseBody, File>() {
                        @Override
                        public File apply(ResponseBody responseBody) throws Exception {
                            return mLoadObserver.saveFile(responseBody);
                        }
                    })
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
