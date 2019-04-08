package com.dhq.net;

import android.util.Log;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;
import okio.BufferedSource;

import static okhttp3.internal.Util.UTF_8;

/**
 * DESC 请求拦截类
 * Created by douhaoqiang on 2016/11/9.
 */
public class MyIntercepter implements Interceptor {

    private static final String TAG = "MyIntercepter";

    private static final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public Response intercept(Chain chain) {
        Request oriRequest = chain.request();
        Response response = null;
        try {
            response = chain.proceed(getNewRequest(oriRequest));
        } catch (Exception e) {
            e.printStackTrace();
        }
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

        printParams(newRequest.body());

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

//            ResponseBody responseBody = response.peekBody(1024 * 1024);
            ResponseBody responseBody = response.body();
//            Log.d("infe","响应头："+response.code());
//            Log.d("infe","请求地址："+response.request().url());
//            Log.d("infe", "返回数据：" + responseBody.string());

            long contentLength = responseBody.contentLength();
            String bodySize = contentLength != -1 ? contentLength + "-byte" : "unknown-length";

            Log.d(TAG, "<-- "
                    + response.code()
                    + (response.message().isEmpty() ? "" : ' ' + response.message())
                    + ' ' + response.request().url());

            Headers headers = response.headers();
            for (int i = 0, count = headers.size(); i < count; i++) {
                Log.d(TAG, headers.name(i) + ": " + headers.value(i));
            }

            if (!HttpHeaders.hasBody(response)) {

            } else if (bodyEncoded(response.headers())) {
                Log.d(TAG, "<-- END HTTP (encoded body omitted)");
            } else {
                BufferedSource source = responseBody.source();
                source.request(Long.MAX_VALUE); // Buffer the entire body.
                Buffer buffer = source.buffer();

                Charset charset = UTF8;
                MediaType contentType = responseBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }

                if (contentLength != 0) {
                    Log.d(TAG, buffer.clone().readString(charset));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    /**
     * 打印请求参数
     *
     * @param body
     */
    private void printParams(RequestBody body) {

        Buffer buffer = new Buffer();
        try {
            body.writeTo(buffer);
            Charset charset = Charset.forName("UTF-8");
            MediaType contentType = body.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF_8);
            }
            if ("multipart".equals(contentType.type())) {
                //表示是文件上传
                return;
            }
            String params = buffer.readString(charset);
//            LogUtil.d(TAG, "请求参数： | " + params);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private boolean bodyEncoded(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !"identity".equalsIgnoreCase(contentEncoding);
    }

}
