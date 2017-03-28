package com.dhq.net.entity;

import java.io.Serializable;

/**
 * DESC
 * Created by douhaoqiang on 2016/11/9.
 */
public class BaseResponse<T> implements Serializable{

    //  判断标示
    private String code;
    //    提示信息
    private String result;
    //显示数据（用户需要关心的数据）
    private T resultMap;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public T getResultMap() {
        return resultMap;
    }

    public void setResultMap(T resultMap) {
        this.resultMap = resultMap;
    }
}
