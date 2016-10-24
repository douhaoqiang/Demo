package com.dhq.demo.jock.ui;

import com.dhq.baselibrary.activity.BaseActivity;
import com.dhq.demo.jock.iview.IJockView;
import com.dhq.demo.jock.presenter.JockPresenter;

/**
 * DESC
 * Created by douhaoqiang on 2016/10/18.
 */
public class JockActivity extends BaseActivity<IJockView,JockPresenter> implements IJockView{
    private static final String TAG = "JockActivity";


    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected JockPresenter createPresenter() {
        return new JockPresenter();
    }


    @Override
    public void showDialog() {

    }
}
