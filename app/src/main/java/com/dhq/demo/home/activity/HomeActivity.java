package com.dhq.demo.home.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.dhq.demo.R;
import com.dhq.demo.base.BaseActivity;
import com.dhq.demo.home.Presenter.HomePresentIpml;
import com.dhq.demo.home.TabContentFragment;
import com.dhq.demo.home.view.HomeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/8/18.
 */
public class HomeActivity extends BaseActivity<HomeView, HomePresentIpml> {


    @BindView(R.id.home_toolbar)
    Toolbar homeToolbar;
    @BindView(R.id.home_tab)
    TabLayout tabLayout;
    @BindView(R.id.home_viewpager)
    ViewPager mViewpager;


    private List<String> tabIndicators;
    private List<Fragment> tabFragments;
    private ContentPagerAdapter contentAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initialize() {

        tabIndicators = new ArrayList<>();
        tabFragments = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            tabIndicators.add("Tab " + i);
            tabFragments.add(TabContentFragment.newInstance("Tab " + i));
        }

        contentAdapter = new ContentPagerAdapter(getSupportFragmentManager());
        mViewpager.setAdapter(contentAdapter);

        tabLayout.setupWithViewPager(mViewpager);
    }

    @Override
    protected HomePresentIpml createPresenter() {
        return new HomePresentIpml(this);
    }


    class ContentPagerAdapter extends FragmentPagerAdapter {

        public ContentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return tabFragments.get(position);
        }

        @Override
        public int getCount() {
            return tabIndicators.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabIndicators.get(position);
        }
    }


}
