package com.dhq.demo;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dhq.baselibrary.activity.BaseActivity;
import com.dhq.demo.ndk.activity.NdkDemoActivity;
import com.dhq.demo.recycle.activity.RecycleViewActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends BaseActivity<MainView, MainPresenter> implements MainView {

    @BindView(R.id.main_menu_recycle)
    Button menuRecycle;
    @BindView(R.id.main_menu_ndk)
    Button menuNdk;
    private Unbinder bind;


    @Override
    protected int getLayoutId() {
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
