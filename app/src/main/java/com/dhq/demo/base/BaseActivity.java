package com.dhq.demo.base;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Administrator on 2016/8/1.
 */
public abstract class BaseActivity<V, P extends BasePresenter<V>> extends Activity {

    protected P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mPresenter = createPresenter();
        mPresenter.attachView((V) this);
    }


    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroy();
    }

    /**
     * 获取Activity对应的布局文件
     *
     * @return 布局文件的layoutid
     */
    protected abstract int getLayoutId();

    /**
     * 初始化方法
     */
    protected abstract void initialize();


    /**
     * 获取对应的Presenter
     *
     * @return
     */
    protected abstract P createPresenter();
}
