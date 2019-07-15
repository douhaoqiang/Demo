package com.dhq.net.http;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * DESC
 * Created by douhaoqiang on 2017/3/28.
 */

public interface BaseApiService {


    /**
     * get请求 添加参数
     *
     * @return
     */
    @GET()
    Observable<ResponseBody> getReq(@Url String url, @QueryMap HashMap<String, Object> paramMaps);


    /**
     * post请求 添加表单参数
     *
     * @return
     */
    @POST()
    @FormUrlEncoded
    Observable<ResponseBody> postFormReq(@Url String url, @FieldMap(encoded = true) HashMap<String, Object> paramMaps);


    /**
     * post请求 添加表json参数
     *
     * @param url
     * @param jsonBody
     * @return
     */
    @POST()
    Observable<ResponseBody> postJsonReq(@Url String url, @Body RequestBody jsonBody);


    /**
     * post请求上传文件
     *
     * @return
     */
    @Multipart
    @POST()
    Observable<ResponseBody> uploadFileReq(@Url String url, @Part List<MultipartBody.Part> fileList);


    @Streaming
    @GET
    Observable<ResponseBody> downLoadFileReq(@Url String url, @QueryMap HashMap<String, Object> paramMaps);

}
