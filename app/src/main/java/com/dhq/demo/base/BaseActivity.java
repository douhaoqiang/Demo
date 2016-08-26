package com.dhq.demo.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.dhq.demo.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2016/8/1.
 */
public abstract class BaseActivity<V, P extends BasePresenter<V>> extends AppCompatActivity {

    private Unbinder mBind;
    protected P mPresenter;
    private static final int INVALID_VAL = -1;

    private static final int COLOR_DEFAULT = Color.parseColor("#FA7198");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            // 透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }

        setContentView(getLayoutId());

//        setStatusBar(getResources().getColor(R.color.colorAccent));

        mBind = ButterKnife.bind(this);
        mPresenter = createPresenter();
        mPresenter.attachView((V) this);

        initialize();
    }


    private void setStatusBar(int statusColor){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (statusColor != INVALID_VAL) {
                getWindow().setStatusBarColor(statusColor);
            }
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            int color = COLOR_DEFAULT;
            ViewGroup contentView = (ViewGroup) findViewById(android.R.id.content);
            if (statusColor != INVALID_VAL) {
                color = statusColor;
            }
            View statusBarView = contentView.getChildAt(0);
            // 改变颜色时避免重复添加statusBarView
            if (statusBarView != null && statusBarView.getMeasuredHeight() == getStatusBarHeight(this)) {
                statusBarView.setBackgroundColor(color);
                return;
            }
            statusBarView = new View(this);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(this));
            statusBarView.setBackgroundColor(color);
            contentView.addView(statusBarView, lp);
        }
    }

    private int getStatusBarHeight(Context context) {

        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        mBind.unbind();
        super.onDestroy();
    }

    /**
     * 获取Activity对应的布局文件
     *
     * @return 布局文件的layoutid
     */
    protected abstract int getLayoutId();

    /**
     * 初始化方法
     */
    protected abstract void initialize();


    /**
     * 获取对应的Presenter
     *
     * @return
     */
    protected abstract P createPresenter();


    /**
     * 显示Toast消息
     * @param msg 需要显示的消息
     */
    public void showToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示Toast消息
     * @param resId 需要显示的消息id
     */
    public void showToast(int resId){
        Toast.makeText(this,resId,Toast.LENGTH_SHORT).show();
    }


}
