package com.dhq.mywidget.protocol;

import android.content.Context;
import android.text.TextUtils;

import com.dhq.dialoglibrary.MyDialog;
import com.dhq.mywidget.entity.BaseResponse;
import com.dhq.net.BaseObserver;
import com.dhq.net.exception.MyHttpException;
import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author douhaoqiang on 2019/4/8.
 * <p>
 * desc
 */
public abstract class MyObserver<T> extends BaseObserver {


    private Context mContext;
    private Gson gson = new Gson();
    private MyDialog myDialog;
    private boolean mIsShowLoading;
    private boolean mIsShowError;



    /**
     * 不显示弹框
     */
    public MyObserver() {

    }

    /**
     * 显示弹框
     *
     * @param context
     */
    public MyObserver(Context context) {
        this(context,true,true);
    }



    /**
     * 显示弹框
     *
     * @param context
     */
    public MyObserver(Context context,boolean isShowLoading,boolean isShowError) {
        mContext = context;
        mIsShowLoading = isShowLoading;
        mIsShowError = isShowError;
    }


    @Override
    public void reqStart() {

        if (mContext != null) {
            myDialog = new MyDialog(mContext, MyDialog.WARNING_TYPE)
                    .setContentText("加载中。。。");

//            myDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                @Override
//                public void onDismiss(DialogInterface dialog) {
//                    //弹框消失，取消网络请求
//                    cancle();
//                }
//            });
        }
    }

    @Override
    public void reqEnd() {
        if (myDialog != null) {
            myDialog.cancel();
        }
    }

    @Override
    public void reqSucc(String response) {

        if (TextUtils.isEmpty(response)) {
            reqFail(new MyHttpException("数据错误", MyHttpException.ERROR.UNKNOWN));
            return;
        }

        BaseResponse baseResponse = gson.fromJson(response, BaseResponse.class);

        if (!"success".equals(baseResponse.getResult())) {
            reqFail(new MyHttpException(baseResponse.getResult(), MyHttpException.ERROR.UNKNOWN));
            return;
        }

        Class<T> entityClass = getEntityClass();
        if (entityClass != null) {
            success(gson.fromJson(baseResponse.getResultMap(), entityClass));
        }else {
            fail("请求失败");
        }
    }

    @Override
    public void reqFail(MyHttpException e) {
        fail(e.getMessage());
    }

    public abstract void success(T result);

    public abstract void fail(String msg);


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

}
