package com.dhq.net;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.Log;

import com.dhq.dialoglibrary.MyDialog;
import com.dhq.net.entity.BaseResponse;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * DESC 通用请求响应处理类
 * Created by douhaoqiang on 2017/2/14.
 */
public abstract class BaseObserver<T> implements Observer<BaseResponse> {
    private static final String TAG = "BaseObserver";
    private Gson gson = new Gson();
    private Context mContext;
    private Disposable mDisposable;
    private MyDialog myDialog;
    private String mEntityName;
    private BaseResponse mResponse;

    /**
     * 显示弹框
     *
     * @param context
     */
    public BaseObserver(Context context) {
        mContext = context;
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
     */
    public BaseObserver() {

    }

    /**
     * 不显示弹框
     */
    public BaseObserver(String entityName) {
        mEntityName = entityName;
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.mDisposable = d;
        showWaitingDialog();
    }

    @Override
    public void onNext(BaseResponse response) {

        mResponse = response;

    }

    @Override
    public void onError(Throwable e) {
        hintWaitingDialog();
        Log.d(TAG, e.toString());
        fail("网络请求失败！");

    }

    @Override
    public void onComplete() {
        hintWaitingDialog();

        if (mResponse == null) {
            fail("请求数据错误");
            return;
        }
        if (!"success".equals(mResponse.getResult())) {
            fail(mResponse.getResult());
            return;
        }

        try {
            Class<T> entityClass = getEntityClass();
            if (entityClass != null) {
                if (TextUtils.isEmpty(mEntityName)) {
                    T result = gson.fromJson(mResponse.getResult(), getEntityClass());
                    success(result);
                } else {
                    T result = gson.fromJson(mResponse.getResultMap(), getEntityClass());
                    success(result);
                }

            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            fail("解析数据失败！");
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
        if (myDialog != null) {
            myDialog.show();
        }
    }

    /**
     * 取消等待框
     */
    private void hintWaitingDialog() {
        if (myDialog != null) {
            myDialog.cancel();
        }
    }


    /**
     * 获取泛型T的Class
     *
     * @return
     */
    public Class<T> getEntityClass() {
        Type t = getClass().getGenericSuperclass();
        Class<T> entityClass = null;
        if (t instanceof ParameterizedType) {
            Type[] p = ((ParameterizedType) t).getActualTypeArguments();
            entityClass = (Class<T>) p[0];
        }
        return entityClass;
    }


    /**
     * 请求成功
     *
     * @param result 请求数据
     */
    public abstract void success(T result);

    /**
     * 请求失败
     *
     * @param msg 失败信息
     */
    public abstract void fail(String msg);


}
