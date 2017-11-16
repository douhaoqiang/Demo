package com.dhq.net;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import com.dhq.dialoglibrary.MyDialog;
import com.dhq.net.entity.BaseResponse;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * DESC
 * Created by douhaoqiang on 2017/2/14.
 */
public class BaseObserver<T> implements Observer<BaseResponse<T>> {
    private static final String TAG = "BaseObserver";
    private Context mContext;
    private ResponseCallback responseCallback;
    private Disposable mDisposable;
    private MyDialog myDialog;

    /**
     * 显示弹框
     *
     * @param context
     * @param responseCallback
     */
    public BaseObserver(Context context, ResponseCallback responseCallback) {
        mContext = context;
        this.responseCallback = responseCallback;
        myDialog = new MyDialog(mContext, MyDialog.WARNING_TYPE)
                .setContentText("加载中。。。");

        myDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                //弹框消失，取消网络请求
                cancle();
            }
        });

    }

    /**
     * 不显示弹框
     *
     * @param responseCallback
     */
    public BaseObserver(ResponseCallback responseCallback) {
        this.responseCallback = responseCallback;
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.mDisposable = d;
        showWaitingDialog();
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
        hintWaitingDialog();
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


    /**
     * 显示网络请求等待框
     */
    private void showWaitingDialog() {
        myDialog.show();
    }

    /**
     * 取消等待框
     */
    private void hintWaitingDialog() {
        myDialog.cancel();
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
