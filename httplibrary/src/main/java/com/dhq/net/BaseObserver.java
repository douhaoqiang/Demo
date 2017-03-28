package com.dhq.net;

import android.util.Log;

import com.dhq.net.entity.BaseResponse;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * DESC
 * Created by douhaoqiang on 2017/2/14.
 */
public abstract class BaseObserver<T> implements Observer<BaseResponse<T>> {
    private static final String TAG = "BaseObserver";
    private Disposable mDisposable;


    public BaseObserver(){

    }

    @Override
    public void onSubscribe(Disposable d) {
        this.mDisposable=d;
    }

    @Override
    public void onNext(BaseResponse<T> response) {
        if("success".equals(response.getResult())){
            success(response.getResultMap());
        }else{
            fail(response.getResult());
        }
    }

    @Override
    public void onError(Throwable e) {
        Log.d(TAG,e.getMessage());
        fail("网络请求失败！");
    }

    @Override
    public void onComplete() {

    }

    public abstract void success(T result);

    public abstract void fail(String msg);


}
