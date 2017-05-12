package com.dhq.demo.http;

import android.util.Log;

import com.dhq.demo.http.entity.BaseResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * DESC
 * Created by douhaoqiang on 2017/1/13.
 */
public abstract class RespCallBack<T> implements Callback<BaseResponse<T>> {
    private static final String TAG = "RespCallBack";


    @Override
    public void onResponse(Call<BaseResponse<T>> call, Response<BaseResponse<T>> response) {

        BaseResponse<T> body = response.body();
        if("success".equals(body.getResult())){
            T result = body.getBody();
            success(result);
        }else {
            fail(body.getMsg());
        }

    }

    @Override
    public void onFailure(Call<BaseResponse<T>> call, Throwable t) {
        fail("网络请求失败！");
//        success(result);
    }


    public abstract void success(T result);

    public abstract void fail(String msg);


}
