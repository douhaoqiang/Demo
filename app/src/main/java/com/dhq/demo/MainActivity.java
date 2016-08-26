package com.dhq.demo;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.TextView;

import com.dhq.demo.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity<MainView, MainPresenter> implements MainView{


    @BindView(R.id.main_setting_tv)
    TextView settingTv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initialize() {
        settingTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transfer();
            }
        });
    }


    private void transfer() {

//        RotateAnimation animation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        animation.setDuration(2000);
//        animation.setRepeatCount(2);
//        animation.setRepeatMode(Animation.REVERSE);
//        settingTv.startAnimation(animation);



        ObjectAnimator anim = ObjectAnimator//
                .ofFloat(settingTv, "rotationX", 0.0F, 100.0F)//
                .setDuration(500);//
        anim.start();
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation){
                float cVal = (Float) animation.getAnimatedValue();

            }
        });

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
}
