package com.dhq.demo.home.activity;

import com.dhq.demo.R;
import com.dhq.demo.base.BaseActivity;
import com.dhq.demo.home.Presenter.HomePresentIpml;
import com.dhq.demo.home.view.HomeView;

/**
 * Created by Administrator on 2016/8/18.
 */
public class HomeActivity extends BaseActivity<HomeView,HomePresentIpml> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected HomePresentIpml createPresenter() {
        return new HomePresentIpml(this);
    }
}
