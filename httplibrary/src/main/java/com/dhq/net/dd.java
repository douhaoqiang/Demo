package com.dhq.net;

import com.dhq.net.entity.BaseResponse;
import com.dhq.net.entity.User;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * DESC  接口样例
 * Created by douhaoqiang on 2016/11/9.
 */
public class dd {
    private static final String TAG = "dd";

    private void ddd() {
        Call<BaseResponse> baseResponseCall = OlderServiceGen.getInstance().getService().addUser(new BaseResponse());
        baseResponseCall.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                BaseResponse body = response.body();
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

            }
        });

        Observer observer = new BaseObserver<User>() {
            @Override
            public void success(User result) {

            }

            @Override
            public void fail(String msg) {

            }
        };


        OlderServiceGen.getInstance().getService().getUsers()
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }


}
