package com.dhq.demo.http.api;

import com.dhq.demo.entity.BaseEntity;
import com.dhq.demo.http.entity.BaseResponse;

import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * DESC 数据请求类
 * Created by douhaoqiang on 2017/1/13.
 */
public interface TestApiService {

    public final static String Base_URL = "";

    /**
     * get方法传递参数请求
     *
     * @param dataId
     */
    @GET("service/getdata")
    Call getDate(@Query("id") String dataId);


    /**
     * get动态请求路径 多参数请求
     *
     * @param url
     * @param requestmap
     */
    @GET("{path}")
    <T extends BaseResponse> Call<T> getDates(@Path("path") String url, @QueryMap Map<String, String> requestmap);


    @POST()
    <T extends BaseResponse> Call<T> post(@Url String url, @QueryMap Map<String, String> map);


    /**
     * 很多情况下，我们需要上传json格式的数据。比如当我们注册新用户的时候，因为用户注册时的数据相对较多，
     * 并可能以后会变化，这时候，服务端可能要求我们上传json格式的数据。此时就要@Body注解来实现。
     * 直接传入实体,它会自行转化成Json
     *
     * @param url
     * @param post
     * @return
     */
    @POST("api/{url}/newsList")
    <T extends BaseResponse> Call<T> login(@Path("url") String url, @Body BaseEntity post);


    /**
     * 单张图片上传
     * retrofit 2.0的上传和以前略有不同，需要借助@Multipart注解、@Part和MultipartBody实现。
     *
     * @param url
     * @param file
     * @return
     */
    @Multipart
    @POST("{url}")
    <T extends BaseResponse> Call<T> upload(@Path("url") String url, @Part MultipartBody.Part file);

    /**
     * 多张图片上传
     *
     * @param map
     * @return
     */
    @Multipart
    @POST("upload/upload")
    <T extends BaseResponse> Call<T> upload(@PartMap Map<String, MultipartBody.Part> map);

    /**
     * 图文混传
     *
     * @param post
     * @param map
     * @return
     */
    @Multipart
    @POST("")
    <T extends BaseResponse> Call<T> register(@Body BaseEntity post, @PartMap Map<String, MultipartBody.Part> map);

    /**
     * 文件下载
     *
     * @param fileUrl
     * @return
     */
    @Streaming
    @GET
    <T extends BaseResponse> Call<T> downloadPicture(@Url String fileUrl);

    /**
     * 这里需要注意的是如果下载的文件较大，比如在10m以上，那么强烈建议你使用@Streaming进行注解，否则将会出现IO异常.
     *
     * @param fileUrl
     * @return
     */
    @Streaming
    @GET
    <T extends BaseResponse> Call<T> downloadPicture2(@Url String fileUrl);

    @POST()
    @FormUrlEncoded
    <T extends BaseResponse> Call<T> executePost(@FieldMap Map<String, Object> maps);



}
