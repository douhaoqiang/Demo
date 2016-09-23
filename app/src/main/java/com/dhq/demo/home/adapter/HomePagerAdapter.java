package com.dhq.demo.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * DESC
 * Created by douhaoqiang on 2016/9/22.
 */

public class HomePagerAdapter extends FragmentPagerAdapter {

    private final List<String> mTitles;
    private List<Fragment> mNewsFragmentList;

    public HomePagerAdapter(FragmentManager fm, List<String> mTitles, List<Fragment> mNewsFragmentList) {
        super(fm);
        this.mTitles = mTitles;
        this.mNewsFragmentList = mNewsFragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mNewsFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mNewsFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
