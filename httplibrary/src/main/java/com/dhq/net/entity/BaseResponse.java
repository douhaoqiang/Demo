package com.dhq.net.entity;

import java.io.Serializable;

/**
 * DESC
 * Created by douhaoqiang on 2016/11/9.
 */
public class BaseResponse<T> implements Serializable{

    //  判断标示
    private int code;
    //    提示信息
    private String msg;
    //显示数据（用户需要关心的数据）
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
