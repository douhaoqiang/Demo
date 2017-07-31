package com.dhq.baselibrary.activity;

import com.dhq.baselibrary.presenter.BasePresenter;
import com.dhq.baselibrary.util.PermissionUtils;

/**
 * DESC MVP模式Activity
 * Created by douhaoqiang on 2016/9/1.
 */

public abstract class BaseMvpActivity<P extends BasePresenter> extends BaseActivity {

    protected P mPresenter;

    @Override
    protected void initialize() {
        //绑定presenter
        mPresenter = createPresenter();
        mPresenter.attachView(this);
        initializes();
    }


    protected abstract void initializes();

    /**
     * 获取对应的Presenter
     *
     * @return
     */
    protected abstract P createPresenter();

    @Override
    protected void onDestroy() {
        //接触绑定
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroy();
    }





}
