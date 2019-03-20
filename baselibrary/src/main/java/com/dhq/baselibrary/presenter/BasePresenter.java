package com.dhq.baselibrary.presenter;

import java.lang.ref.WeakReference;

/**
 * DESC Presenter父类
 * Created by douhaoqiang on 2016/9/1.
 */

public class BasePresenter<V> {

    private WeakReference<V> viewRef;

    public BasePresenter(V view) {
        attachView(view);
        if (view instanceof PresenterManger) {
            ((PresenterManger) view).addPresenter(this);
        }
    }

    /**
     * 绑定对应的view
     *
     * @param view
     */
    private void attachView(V view) {
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
