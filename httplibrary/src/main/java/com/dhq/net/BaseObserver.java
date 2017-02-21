package com.dhq.net;

import com.dhq.net.entity.BaseResponse;

import io.reactivex.Observable;
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
        if(200==response.getCode()){
            success(response.getData());
        }else{
            fail(response.getMsg());
        }
    }

    @Override
    public void onError(Throwable e) {
        fail("网络请求失败！");
    }

    @Override
    public void onComplete() {

    }

    public abstract void success(T result);

    public abstract void fail(String msg);


}
