package com.dhq.net.http2;

import com.dhq.net.entity.BaseResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * DESC
 * Created by douhaoqiang on 2017/3/28.
 */

public interface ApiService2 {


    /**
     * get请求 添加参数
     * @return
     */
    @GET()
    Call<ResponseBody> getHttpRequest(@Url String url, @QueryMap HashMap<String, String> paramMaps);


    /**
     * post请求 添加表单参数
     * @return
     */
    @POST()
    @FormUrlEncoded
    Call<ResponseBody> postFormHttpRequest(@Url String url, @FieldMap HashMap<String, String> paramMaps);


    /**
     * post请求 添加表json参数
     * @param url
     * @param jsonBody
     * @return
     */
    @POST()
    Call<ResponseBody> postJsonHttpRequest(@Url String url, @Body RequestBody jsonBody);


}
