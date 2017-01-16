package com.dhq.demo.http.entity;

/**
 * DESC 网络请求公共类
 * Created by douhaoqiang on 2017/1/13.
 */
public class BaseResponse<T> {
    private String result;
    private String msg;
    private T body;

    public String getResult() {
        return result;
    }

    public String getMsg() {
        return msg;
    }

    public T getBody() {
        return body;
    }
}
