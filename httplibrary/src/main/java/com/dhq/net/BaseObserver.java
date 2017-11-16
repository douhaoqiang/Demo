package com.dhq.net;

import android.util.Log;

import com.dhq.net.entity.BaseResponse;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * DESC
 * Created by douhaoqiang on 2017/2/14.
 */
public class BaseObserver<T> implements Observer<BaseResponse<T>> {
    private static final String TAG = "BaseObserver";
    private ResponseCallback responseCallback;
    private Disposable mDisposable;


    public BaseObserver(ResponseCallback responseCallback) {
        this.responseCallback = responseCallback;
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.mDisposable = d;
    }

    @Override
    public void onNext(BaseResponse<T> response) {

        if (response == null) {
            responseCallback.fail("请求数据错误");
            return;
        }
        if (!"200".equals(response.getCode())) {
            responseCallback.fail(response.getResult());
            return;
        }

        if (responseCallback != null) {
            responseCallback.success(response.getResultMap());
        }
    }

    @Override
    public void onError(Throwable e) {
        Log.d(TAG, e.toString());
        if (responseCallback != null) {
            responseCallback.fail("网络请求失败！");
        }

    }

    @Override
    public void onComplete() {
        if (responseCallback != null) {
            responseCallback.onComplete();
        }
    }


    /**
     * 解除网络请求绑定
     */
    public void cancle() {
        mDisposable.dispose();
    }


    public interface ResponseCallback<T> {
        /**
         * 请求成功
         *
         * @param result 请求数据
         */
        void success(T result);

        /**
         * 请求失败
         *
         * @param msg 失败信息
         */
        void fail(String msg);

        /**
         * 请求结束
         */
        void onComplete();

    }


}
