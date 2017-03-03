package com.dhq.demo.home.fragment;

import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.dhq.baselibrary.activity.BaseMvpActivity;
import com.dhq.baselibrary.fragment.BaseMvpFragment;
import com.dhq.demo.R;
import com.dhq.demo.home.Presenter.HomePresenter;
import com.dhq.demo.home.adapter.HomePagerAdapter;
import com.dhq.demo.home.contract.HomeContract;
import com.dhq.demo.home.fragment.ItemFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2016/8/18.
 */
public class HomeFragment extends BaseMvpFragment<HomeContract.IHomePresenter> implements HomeContract.IHomeView {


    @BindView(R.id.home_drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.home_navigationview_la)
    NavigationView mNavigationview;

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
    protected void initializes() {

        bind = ButterKnife.bind(this,getView());
        initToolBar();
        initTab();
        initListener();

    }

    private void initToolBar() {
        homeToolbar.setTitle("首页");// 标题的文字需在setSupportActionBar之前，不然会无效
//        setSupportActionBar(homeToolbar);
    }

    private void initTab() {
//        homeTab.set
        List<String> titles = new ArrayList<>();
        List<Fragment> fragmentLists = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            titles.add("标题" + i);
            fragmentLists.add(new ItemFragment());
        }
        HomePagerAdapter adapter = new HomePagerAdapter(
                getChildFragmentManager(), titles, fragmentLists);
        mViewPager.setAdapter(adapter);
        homeTab.setupWithViewPager(mViewPager);
        dynamicSetTabLayoutMode(homeTab);

    }

    private void initListener() {

        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }


    @Override
    protected HomeContract.IHomePresenter createPresenter() {
        return new HomePresenter(getContext());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }


    /**
     * 定义Tab布局的显示模式
     *
     * @param tabLayout
     */
    public void dynamicSetTabLayoutMode(TabLayout tabLayout) {
        int tabWidth = calculateTabWidth(tabLayout);
        int screenWidth = getScreenWith();

        if (tabWidth <= screenWidth) {
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
        } else {
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }
    }

    /**
     * 计算tab布局的宽度
     *
     * @param tabLayout
     * @return
     */
    private int calculateTabWidth(TabLayout tabLayout) {
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
     *
     * @return
     */
    public int getScreenWith() {
        int widthPixels = getContext().getResources().getDisplayMetrics().widthPixels;
        return widthPixels;
    }

}
