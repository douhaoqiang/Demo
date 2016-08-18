package com.dhq.demo.base;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2016/8/1.
 */
public class BasePresenter<V> {

    private WeakReference<V> viewRef;

    /**
     * 绑定对应的view
     *
     * @param view
     */
    public void attachView(V view) {
        viewRef = new WeakReference<>(view);
    }

    /*
     * 获取对应的view
     */
    protected V getView() {
        return viewRef.get();
    }

    /**
     * 解绑对应的view
     */
    public void detachView() {
        if (viewRef != null) {
            viewRef.clear();
            viewRef = null;
        }
    }


}
