package com.dhq.net;

import com.dhq.net.entity.BaseResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * DESC  接口样例
 * Created by douhaoqiang on 2016/11/9.
 */
public class dd {
    private static final String TAG = "dd";

    private void ddd(){
        Call<BaseResponse> baseResponseCall = OlderServiceGen.getInstance().getService().addUser(new BaseResponse());
        baseResponseCall.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

            }
        });
    }

}