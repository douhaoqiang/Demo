package com.dhq.baselibrary.fragment;

import android.support.v4.app.Fragment;

import com.dhq.baselibrary.presenter.BasePresenter;
import com.dhq.baselibrary.presenter.PresenterManger;

import java.util.ArrayList;

/**
 * DESC
 * Created by douhaoqiang on 2016/11/21.
 */
public abstract class BaseMvpFragment extends Fragment implements PresenterManger {

    private ArrayList<BasePresenter> mPresenters = new ArrayList<>();

    @Override
    public void addPresenter(BasePresenter presenter) {
        mPresenters.add(presenter);
    }

    @Override
    public void detachView() {
        for (BasePresenter presenter : mPresenters) {
            presenter.detachView();
        }
    }



    @Override
    public void onDestroy() {
        //接触绑定
        detachView();
        super.onDestroy();
    }


    /**
     * 获取Fragment对应的布局文件
     *
     * @return 布局文件的layoutid
     */
    protected abstract int getLayoutId();

    /**
     * 初始化方法
     */
    protected abstract void initialize();


}
