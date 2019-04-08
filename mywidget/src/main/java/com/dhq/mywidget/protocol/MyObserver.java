package com.dhq.mywidget.protocol;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

import com.dhq.dialoglibrary.MyDialog;
import com.dhq.mywidget.entity.BaseResponse;
import com.dhq.net.BaseObserver;
import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author douhaoqiang on 2019/4/8.
 * <p>
 * desc
 */
public abstract class MyObserver<T> extends BaseObserver {

    private String mEntityName;
    private Context mContext;
    private Gson gson = new Gson();
    private MyDialog myDialog;

    /**
     * 显示弹框
     *
     * @param context
     */
    public MyObserver(Context context) {
        mContext = context;

    }

    /**
     * 不显示弹框
     */
    public MyObserver() {

    }

    /**
     * 不显示弹框
     */
    public MyObserver(String entityName) {
        mEntityName = entityName;
    }


    @Override
    public void showLoadingDialog() {

        if (mContext != null) {
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
    }

    @Override
    public void hintLoadingDialog() {
        if (myDialog != null) {
            myDialog.cancel();
        }
    }

    @Override
    public void reqSucc(String response) {

        if (TextUtils.isEmpty(response)) {
            reqFail("数据错误");
            return;
        }

        BaseResponse baseResponse = gson.fromJson(response, BaseResponse.class);

        if (!"success".equals(baseResponse.getResult())) {
            reqFail(baseResponse.getResult());
            return;
        }

        Class<T> entityClass = getEntityClass();
        if (entityClass != null) {
            if (TextUtils.isEmpty(mEntityName)) {
                T result = gson.fromJson(baseResponse.getResult(), getEntityClass());
                success(result);
            } else {
                T result = gson.fromJson(baseResponse.getResultMap(), getEntityClass());
                success(result);
            }

        }
    }

    @Override
    public void reqFail(String msg) {
        fail(msg);
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


    public abstract void success(T result);

    public abstract void fail(String msg);


}
