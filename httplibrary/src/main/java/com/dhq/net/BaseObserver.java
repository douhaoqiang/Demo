package com.dhq.net;

import com.dhq.net.exception.MyHttpException;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * DESC 通用请求响应处理类
 * Created by douhaoqiang on 2017/2/14.
 */
public abstract class BaseObserver implements Observer<ResponseBody> {

    private static final String TAG = "BaseObserver";

    private Disposable mDisposable;
    private String result="";


    @Override
    public void onSubscribe(Disposable d) {
        this.mDisposable = d;
        reqStart();
    }

    @Override
    public void onNext(ResponseBody response) {

        try {
            result=response.string();
        } catch (IOException e) {
            e.printStackTrace();
            onError(e);
        }

    }

    @Override
    public void onError(Throwable e) {
        reqEnd();
        MyHttpException httpException = MyHttpException.handleException(e);
        reqFail(httpException);
    }

    @Override
    public void onComplete() {
        reqEnd();
        reqSucc(result);
    }


    /**
     * 解除网络请求绑定
     */
    public void cancle() {
        mDisposable.dispose();
        reqEnd();
    }


    /**
     * 显示请求等待框
     */
    public abstract void reqStart();

    /**
     * 隐藏请求等待框
     */
    public abstract void reqEnd();

    /**
     * 请求成功
     *
     * @param response 请求返回数据
     */
    public abstract void reqSucc(String response);

    /**
     * 请求失败
     *
     * @param e 异常
     */
    public abstract void reqFail(MyHttpException e);


}
