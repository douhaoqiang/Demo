package com.dhq.demo;

import android.os.Bundle;

import com.dhq.demo.base.BaseActivity;

public class MainActivity extends BaseActivity<MainView,MainPresenter> {



    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {

    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }


}
