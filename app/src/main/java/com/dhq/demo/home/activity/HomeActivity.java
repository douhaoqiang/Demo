package com.dhq.demo.home.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.dhq.demo.R;
import com.dhq.demo.base.BaseActivity;
import com.dhq.demo.home.Presenter.HomePresentIpml;
import com.dhq.demo.home.view.HomeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/8/18.
 */
public class HomeActivity extends BaseActivity<HomeView, HomePresentIpml> {


    @BindView(R.id.home_toolbar)
    Toolbar homeToolbar;

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


    @OnClick(R.id.home_toolbar)
    public void onClick() {
        showToast("点击toolbar");
    }
}
