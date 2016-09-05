package com.dhq.demo;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.dhq.baselibrary.activity.BaseActivity;
import com.dhq.demo.utils.JNIUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends BaseActivity<MainView, MainPresenter> implements MainView {

    private Unbinder bind;

    @BindView(R.id.main_setting_tv)
    TextView settingTv;

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

        spanStringClick();

    }


    /**
     * 字符串的分段响应事件设置监听
     */
    private void spanStringClick(){
        String url_0_text = "用户协议及隐私条款——点击另一个";

        SpannableString spStr = new SpannableString(url_0_text);

        spStr.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.WHITE);       //设置文件颜色
                ds.setUnderlineText(true);      //设置下划线

            }

            @Override
            public void onClick(View widget) {
                Log.d("info", "onTextClick........");
                settingTv.append(JNIUtils.getPackname(""));
            }
        }, 0, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        spStr.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.WHITE);       //设置文件颜色
                ds.setUnderlineText(true);      //设置下划线
            }

            @Override
            public void onClick(View widget) {
                Log.d("info", "onTextClick........2222");
            }
        }, 9, url_0_text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        spStr.setSpan(new ForegroundColorSpan(Color.RED), 0, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //设置字体颜色为红色
        settingTv.setHighlightColor(Color.TRANSPARENT); //设置点击后的颜色为透明，否则会一直出现高亮
        settingTv.setText(spStr);
        settingTv.setMovementMethod(LinkMovementMethod.getInstance());//开始响应点击事件
    }


    /**
     * 事件监听器初始化
     */
    private void initListener() {
//        settingTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                transfer();
//            }
//        });
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
                settingTv.setRotationX(cVal);
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
