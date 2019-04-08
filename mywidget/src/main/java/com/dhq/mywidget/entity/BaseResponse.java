package com.dhq.mywidget.entity;

import com.google.gson.JsonElement;

import java.io.Serializable;

/**
 * DESC
 * Created by douhaoqiang on 2016/11/9.
 */
public class BaseResponse implements Serializable{

    //  判断标示
    private String code;
    //    提示信息
    private String result;
    //显示数据（用户需要关心的数据）
    private JsonElement resultMap;

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

    public JsonElement getResultMap() {
        return resultMap;
    }

    public void setResultMap(JsonElement resultMap) {
        this.resultMap = resultMap;
    }
}
