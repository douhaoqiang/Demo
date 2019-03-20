package com.dhq.base.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.dhq.base.R;
import com.dhq.base.presenter.BasePresenter;
import com.dhq.base.presenter.PresenterManger;
import com.dhq.base.util.HeaderUtil;

import java.util.ArrayList;

/**
 * DESC MVP模式Activity
 * Created by douhaoqiang on 2016/9/1.
 */

public abstract class BaseActivity extends AppCompatActivity implements PresenterManger{

    private ArrayList<BasePresenter> mPresenters=new ArrayList<>();
    private HeaderUtil headerUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = getLayoutInflater().inflate(R.layout.activity_base_lay, null);
        setContentView(contentView);
        initStatusBar();
        headerUtil = new HeaderUtil(this, contentView);
        addContentView();
        initializes(savedInstanceState);
    }

    /**
     * 设置透明状态栏
     */
    protected void initStatusBar() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置透明状态栏,这样才能让 ContentView 向上
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }


    /**
     * 设置Activity的内容布局，取代setContentView（）方法
     */
    public void addContentView() {
        LinearLayout content_linear = this.findViewById(R.id.content_view);
        content_linear.addView(View.inflate(this, getLayoutId(), null),
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
    }


    public HeaderUtil getHeaderUtil() {
        return headerUtil;
    }

    @Override
    public void addPresenter(BasePresenter presenter){
        mPresenters.add(presenter);
    }

    @Override
    public void detachView(){
        for (BasePresenter presenter : mPresenters) {
            presenter.detachView();
        }
    }

    @Override
    protected void onDestroy() {
        //接触绑定
        detachView();
        super.onDestroy();
    }


    /**
     * 获取Activity对应的布局文件
     *
     * @return 布局文件的layoutid
     */
    protected abstract int getLayoutId();

    /**
     * 入口方法
     */
    protected abstract void initializes(Bundle savedInstanceState);


}
