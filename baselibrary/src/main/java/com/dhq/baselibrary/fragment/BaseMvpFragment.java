package com.dhq.baselibrary.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dhq.baselibrary.presenter.BasePresenter;

/**
 * DESC
 * Created by douhaoqiang on 2016/11/21.
 */
public abstract class BaseMvpFragment<P extends BasePresenter> extends BaseFragment {

    private BasePresenter mPresenter;

    @Override
    protected void initialize() {
        //绑定presenter
        mPresenter = createPresenter();
        mPresenter.attachView(this);
        initializes();
    }


    /**
     * 初始化方法
     */
    protected abstract void initializes();


    /**
     * 获取对应的Presenter
     *
     * @return
     */
    protected abstract P createPresenter();

    @Override
    public void onDestroy() {
        //接触绑定
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroy();
    }

}
