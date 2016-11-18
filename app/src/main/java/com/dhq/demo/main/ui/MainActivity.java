package com.dhq.demo.main.ui;

import android.app.Fragment;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.LinearLayout;

import com.dhq.baselibrary.activity.BaseMvpActivity;
import com.dhq.demo.R;
import com.dhq.demo.main.contract.MainContract;
import com.dhq.demo.main.presenter.MainPresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends BaseMvpActivity<MainContract.IMainPresenter> implements MainContract.IMainView {


    @BindView(R.id.main_viewpage)
    ViewPager mainViewpage;

    @BindView(R.id.main_bottom_lay)
    LinearLayout mainBottomLay;

    private Unbinder bind;


    @Override
    protected int getLayoutId() {
        Log.e("infe", "onCreate");
        return R.layout.activity_main;
    }

    @Override
    protected void initializes() {
        bind = ButterKnife.bind(this);
        initData();
        initListener();
    }

    /**
     * 初始化列表数据
     */
    private void initData() {

//        mainViewpage.setAdapter();
    }


    /**
     * 事件监听器初始化
     */
    private void initListener() {
        mainViewpage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    private void gotoActivity(Class aClass) {
        Intent intent = new Intent(this, aClass);
        startActivity(intent);
    }


    @Override
    protected MainPresenterImpl createPresenter() {
        return new MainPresenterImpl(this);
    }

    @Override
    public void isLoading() {
        //表示正在加载数据应该显示加载框


    }

    @Override
    public void isCompleted() {
        //表示正在加载数据完成，应该隐藏加载框

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

}
