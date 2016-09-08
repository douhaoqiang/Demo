package com.dhq.demo.home.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

import com.dhq.baselibrary.activity.BaseActivity;
import com.dhq.demo.R;
import com.dhq.demo.home.Presenter.HomePresenter;
import com.dhq.demo.home.TabContentFragment;
import com.dhq.demo.home.view.HomeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2016/8/18.
 */
public class HomeActivity extends BaseActivity<HomeView, HomePresenter> implements HomeView {


    @BindView(R.id.home_toolbar)
    Toolbar homeToolbar;


    private int currentTabIndex;
    private int index;
    private Fragment[] fragments;

    private Unbinder bind;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initialize() {

        bind = ButterKnife.bind(this);

        initToolBar();

        initContent();

    }

    private void initContent() {
        TabContentFragment mHomeFragment = new TabContentFragment();


        fragments = new Fragment[]{
                mHomeFragment
        };

        setShowingFragment();
    }

    private void initToolBar() {
        homeToolbar.setTitle("首页");// 标题的文字需在setSupportActionBar之前，不然会无效
        homeToolbar.setSubtitle("副标题");
        setSupportActionBar(homeToolbar);
    }

    private void setShowingFragment() {
        FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
        trx.replace(R.id.home_container, fragments[0]).commit();
//        FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
//        trx.hide(fragments[currentTabIndex]);
//        if (!fragments[index].isAdded()) {
//            trx.add(R.id.home_container, fragments[index]);
//        }
//        trx.show(fragments[index]).commit();
//        currentTabIndex = index;
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
