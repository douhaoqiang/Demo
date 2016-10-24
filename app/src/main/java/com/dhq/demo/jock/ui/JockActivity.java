package com.dhq.demo.jock.ui;

import com.dhq.baselibrary.activity.BaseActivity;
import com.dhq.baselibrary.activity.BaseActivity2;
import com.dhq.demo.jock.contract.JockContract;
import com.dhq.demo.jock.presenter.JockPresenterImpl;
import com.dhq.demo.jock.view.JockViewImpl;

/**
 * DESC
 * Created by douhaoqiang on 2016/10/18.
 */
public class JockActivity extends BaseActivity2<JockContract.Presenter> implements JockViewImpl {
    private static final String TAG = "JockActivity";


    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected JockContract.Presenter createPresenter() {
        return new JockPresenterImpl(this);
    }


    @Override
    public void showDialog() {

    }
}
