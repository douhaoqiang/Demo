package com.dhq.net.http;

import com.dhq.net.entity.BaseResponse;
import com.dhq.net.entity.LoginEntity;
import com.dhq.net.entity.UserInfo;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.RequestBody;
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

public interface ApiService {


    /**
     * get请求 添加参数
     * @return
     */
    @GET()
    Observable<BaseResponse<LoginEntity>> getHttpRequest(@Url String url, @QueryMap HashMap<String,String> paramMaps);


    /**
     * post请求 添加表单参数
     * @return
     */
    @POST()
    @FormUrlEncoded
    Observable<BaseResponse<UserInfo>> postFormHttpRequest(@Url String url, @FieldMap(encoded=true) HashMap<String,String> paramMaps);


    /**
     * post请求 添加表json参数
     * @param url
     * @param jsonBody
     * @return
     */
    @POST()
    Observable<BaseResponse<LoginEntity>> postJsonHttpRequest(@Url String url, @Body RequestBody jsonBody);


}
