package com.dhq.net;

import android.util.Log;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

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

        try {
            RequestBody requestBody = newRequest.body();
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            Charset charset = Charset.forName("UTF-8");
            String paramsStr = buffer.readString(charset);
            Log.d(TAG,"RequestUrl："+paramsStr);
            Log.d(TAG,"RequestUrl："+newRequest.url());
            Log.d(TAG,"RequestMethod："+newRequest.method());
            Log.d(TAG,"RequestMethod："+newRequest.toString());
            Log.d(TAG,"RequestHeader："+newRequest.headers().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return newRequest;
    }


    /**
     * 修改公用返回信息
     * @param response
     */
    private void responseModify(Response response){

    }

}
