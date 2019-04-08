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


    @Override
    public void onSubscribe(Disposable d) {
        this.mDisposable = d;
        showLoadingDialog();
    }

    @Override
    public void onNext(ResponseBody response) {
        try {
            reqSucc(response.string());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onError(Throwable e) {
        hintLoadingDialog();
        String error = MyHttpException.handleException(e).getMessage();
        reqFail(error);
    }

    @Override
    public void onComplete() {
        hintLoadingDialog();
    }


    /**
     * 解除网络请求绑定
     */
    public void cancle() {
        mDisposable.dispose();
    }


    /**
     * 显示请求等待框
     */
    public abstract void showLoadingDialog();

    /**
     * 隐藏请求等待框
     */
    public abstract void hintLoadingDialog();

    /**
     * 请求成功
     *
     * @param response 请求返回数据
     */
    public abstract void reqSucc(String response);

    /**
     * 请求失败
     *
     * @param msg 失败信息
     */
    public abstract void reqFail(String msg);


}
