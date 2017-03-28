package com.dhq.net;

import android.util.Log;

import com.dhq.net.entity.BaseResponse;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * DESC
 * Created by douhaoqiang on 2017/2/14.
 */
public class BaseObserver<T> implements Observer<T> {
    private static final String TAG = "BaseObserver";
    private ResponseCallback responseCallback;
    private Disposable mDisposable;


    public BaseObserver(ResponseCallback responseCallback){
        this.responseCallback=responseCallback;
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.mDisposable=d;
    }

    @Override
    public void onNext(T response) {
        if(responseCallback!=null){
            responseCallback.success(response);
        }
    }

    @Override
    public void onError(Throwable e) {
        Log.d(TAG,e.toString());
        if(responseCallback!=null){

            responseCallback.fail("网络请求失败！");
        }

    }

    @Override
    public void onComplete() {

    }


    public interface ResponseCallback<T>{
        void success(T result);
        void fail(String msg);
    }



}
