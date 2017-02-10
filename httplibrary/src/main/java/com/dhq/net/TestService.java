package com.dhq.net;

import com.dhq.net.entity.BaseResponse;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * DESC
 * Created by douhaoqiang on 2016/11/9.
 */

public interface TestService {

    /**
     * get请求 添加参数
     * @param userId
     * @return
     */
    @GET("{userId}/getUsers")
    Call<BaseResponse> getUsers(@Path("userId") String userId);

    /**
     * post请求 将参数以json的形式放到body中
     * @param response
     * @return
     */
    @POST("add")
    Call<BaseResponse> addUser(@Body BaseResponse response);

    /**
     * post请求 以form表单的形式传递参数
     * @param userId
     * @return
     */
    @POST("query/getuser")
    @FormUrlEncoded
    Call<BaseResponse> getUser(@Field("userId") String userId);

    /**
     * post请求 以form表单的形式传递多个参数
     * @param paramsMap
     * @return
     */
    @POST("login")
    @FormUrlEncoded//读参数进行urlEncoded
    Call<BaseResponse> login(@FieldMap HashMap<String, String> paramsMap);

    /**
     * post请求 以form表单的形式Multipart形式参数
     * @param userId
     * @param password
     * @return
     */
    @Multipart
    @POST("login")
    Call<BaseResponse> login(@Part("userId") String userId, @Part("password") String password);


    /**
     * get请求 添加参数
     * @return
     */
    @GET("getUsers")
    Observable<BaseResponse> getUsers();

}
