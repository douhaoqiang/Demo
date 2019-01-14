package com.dhq.net;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * DESC 请求拦截类
 * Created by douhaoqiang on 2016/11/9.
 */
public class MyIntercepter implements Interceptor {
    private static final String TAG = "MyIntercepter";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oriRequest = chain.request();
        Response response = chain.proceed(getNewRequest(oriRequest));
        responseModify(response);
        return response;
    }

    /**
     * 在请求中添加公用信息
     *
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
     *
     * @param response
     */
    private void responseModify(Response response) {

    //这里不能直接使用response.body().string()的方式输出日志
    // 因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
    // 个新的response给应用层处理

        try {
            ResponseBody responseBody = response.peekBody(1024 * 1024);

//            Log.d("infe","响应头："+response.code());
//            Log.d("infe","请求地址："+response.request().url());
            Log.d("infe","返回数据："+responseBody.string());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
