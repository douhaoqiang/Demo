package com.dhq.net;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * DESC
 * Created by douhaoqiang on 2016/11/9.
 */
public class MyIntercepter implements Interceptor {
    private static final String TAG = "MyIntercepter";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oriRequest = chain.request();
        Response response=chain.proceed(getNewRequest(oriRequest));
        responseModify(response);
        return response;
    }

    /**
     * 在请求中添加公用信息
     * @param oriRequest 原始请求信息
     * @return
     */
    private Request getNewRequest(Request oriRequest) {
        Request newRequest = oriRequest.newBuilder()
                .header("token", "oneself_token")
                .build();
        return newRequest;
    }


    /**
     * 修改公用返回信息
     * @param response
     */
    private void responseModify(Response response){

    }

}
