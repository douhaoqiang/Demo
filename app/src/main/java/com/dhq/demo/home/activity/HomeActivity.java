package com.dhq.demo.home.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.dhq.baselibrary.activity.BaseActivity;
import com.dhq.demo.MyApplication;
import com.dhq.demo.R;
import com.dhq.demo.home.Presenter.HomePresenter;
import com.dhq.demo.home.TabContentFragment;
import com.dhq.demo.home.adapter.HomePagerAdapter;
import com.dhq.demo.home.fragment.ItemFragment;
import com.dhq.demo.home.view.HomeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2016/8/18.
 */
public class HomeActivity extends BaseActivity<HomeView, HomePresenter> implements HomeView {


    @BindView(R.id.home_toolbar)
    Toolbar homeToolbar;
    @BindView(R.id.home_tab)
    TabLayout homeTab;
    @BindView(R.id.home_viewpager)
    ViewPager mViewPager;

    private Unbinder bind;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initialize() {

        bind = ButterKnife.bind(this);

        initToolBar();

//        initContent();
        int i=1;
        initTab();

    }

    private void initToolBar() {
        homeToolbar.setTitle("首页");// 标题的文字需在setSupportActionBar之前，不然会无效
        setSupportActionBar(homeToolbar);
    }

    private void initTab() {
//        homeTab.set
        List<String> titles = new ArrayList<>();
        List<Fragment> fragmentLists= new ArrayList<>();
        for(int i=1;i<4;i++){
            titles.add("标题"+i);
            fragmentLists.add(new ItemFragment());
        }
        HomePagerAdapter adapter = new HomePagerAdapter(
                getSupportFragmentManager(), titles, fragmentLists);
        mViewPager.setAdapter(adapter);
        homeTab.setupWithViewPager(mViewPager);
        dynamicSetTabLayoutMode(homeTab);

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


    /**
     * 定义Tab布局的显示模式
     * @param tabLayout
     */
    public static void dynamicSetTabLayoutMode(TabLayout tabLayout) {
        int tabWidth = calculateTabWidth(tabLayout);
        int screenWidth = getScreenWith();

        if (tabWidth <= screenWidth) {
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
        } else {
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }
    }

    /**
     *  计算tab布局的宽度
     * @param tabLayout
     * @return
     */
    private static int calculateTabWidth(TabLayout tabLayout) {
        int tabWidth = 0;
        for (int i = 0; i < tabLayout.getChildCount(); i++) {
            final View view = tabLayout.getChildAt(i);
            view.measure(0, 0); // 通知父view测量，以便于能够保证获取到宽高
            tabWidth += view.getMeasuredWidth();
        }
        return tabWidth;
    }

    /**
     * 获取屏幕的宽度
     * @return
     */
    public static int getScreenWith() {
        return MyApplication.getIntance().getResources().getDisplayMetrics().widthPixels;
    }


}
