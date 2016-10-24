package com.dhq.demo;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.dhq.baselibrary.activity.BaseActivity;
import com.dhq.demo.home.activity.HomeActivity;
import com.dhq.demo.ndk.activity.NdkDemoActivity;
import com.dhq.demo.recycle.activity.RecycleViewActivity;
import com.dhq.demo.refresh.PullToRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends BaseActivity<MainView, MainPresenter> implements MainView {

    @BindView(R.id.main_refresh_view)
    PullToRefreshLayout refreshView;
    @BindView(R.id.main_menu_recycle)
    Button menuRecycle;
    @BindView(R.id.main_menu_ndk)
    Button menuNdk;
    @BindView(R.id.main_menu_tablayout)
    Button tablayout;

    @BindView(R.id.main_menu_edittext)
    EditText edtext;

    @BindView(R.id.main_menu_adtest)
    Button adtest;
    private Unbinder bind;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {

        }
    };

    private Runnable task =new Runnable() {
        @Override
        public void run() {
            refreshView.complete();
        }
    };

    @Override
    protected int getLayoutId() {
        Log.e("infe","onCreate");
        return R.layout.activity_main;
    }

    @Override
    protected void initialize() {
        bind = ButterKnife.bind(this);
        initData();
        initListener();
    }

    /**
     * 初始化列表数据
     */
    private void initData() {


    }


    /**
     * 事件监听器初始化
     */
    private void initListener() {

        refreshView.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                //刷新列表
//                pullToRefreshLayout.complete();
                handler.postDelayed(task, 2000);
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                //加载更多
//                pullToRefreshLayout.complete();
                handler.postDelayed(task, 2000);
            }
        });


        menuRecycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity(RecycleViewActivity.class);
            }
        });

        menuNdk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity(NdkDemoActivity.class);
            }
        });

        tablayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity(HomeActivity.class);
            }
        });
        adtest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        edtext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                showToast("是否聚焦"+hasFocus);
            }
        });

        edtext.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction()==KeyEvent.ACTION_DOWN) {
                    //表示按下的是回车键
                    showToast("点击回车键！");
//                    edtext.isFocused();
                    return true;
                }
                return false;
            }
        });

//        settingTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                transfer();
//            }
//        });
    }


    private void gotoActivity(Class aClass){
        Intent intent=new Intent(this,aClass);
        startActivity(intent);
    }


    private void transfer() {

//        RotateAnimation animation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        animation.setDuration(2000);
//        animation.setRepeatCount(2);
//        animation.setRepeatMode(Animation.REVERSE);
//        settingTv.startAnimation(animation);


//        ObjectAnimator anim = ObjectAnimator//
//                .ofFloat(settingTv, "rotationX", 0.0F, 100.0F)//
//                .setDuration(500);//
        ValueAnimator anim = ObjectAnimator.ofFloat(0.0F, 100.0F).setDuration(500);//
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float cVal = (Float) animation.getAnimatedValue();
                menuRecycle.setRotationX(cVal);
            }
        });
        anim.start();

    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
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
