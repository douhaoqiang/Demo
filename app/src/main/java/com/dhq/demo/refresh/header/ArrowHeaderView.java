package com.dhq.demo.refresh.header;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.dhq.demo.R;
import com.dhq.demo.refresh.IPullHeaderView;

/**
 * DESC 箭头的headerview
 * Created by douhaoqiang on 2016/9/30.
 */
public class ArrowHeaderView extends FrameLayout implements IPullHeaderView {

    private static final String TAG = "ArrowHeaderView";

    private Context mContext;

    private ImageView pullArrowView;//下拉箭头
    private ImageView pullCicleView;//旋转图片
    private TextView pullText;//下拉文字提示
    private RotateAnimation refreshAnim;//旋转动画

    public ArrowHeaderView(Context context) {
        this(context, null);
        Log.e(TAG,"ArrowHeaderView");
    }

    public ArrowHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.e(TAG,"ArrowHeaderView2");
        mContext=context;
        init();
    }


    private void init() {
        Log.e(TAG,"ArrowHeaderView--initializes");
        LayoutInflater.from(getContext()).inflate(R.layout.refresh_head,this);

        pullArrowView = (ImageView) findViewById(R.id.pull_icon);
        pullCicleView = (ImageView) findViewById(R.id.refreshing_icon);
        pullText= (TextView) findViewById(R.id.state_tv);
        Log.e(TAG,"pullArrowView--"+pullArrowView);
        refreshAnim = (RotateAnimation) AnimationUtils.loadAnimation(mContext, R.anim.rotating);
        // 添加匀速转动动画
        LinearInterpolator lir = new LinearInterpolator();
        refreshAnim.setInterpolator(lir);
    }

    @Override
    public void pullDownPrecent(float precent) {
        pullArrowView.setRotation(180*precent);
    }

    @Override
    public void prepareRefresh() {
        //准备刷新修改文字提示
        pullText.setText("松开刷新！");
    }


    @Override
    public void startRefresh() {
        pullArrowView.setRotation(0);
        pullArrowView.setVisibility(View.GONE);
        pullCicleView.setVisibility(View.VISIBLE);
        pullCicleView.startAnimation(refreshAnim);//开始旋转
        pullText.setText("正在刷新！");

    }

    @Override
    public void completeRefresh() {
        //清除旋转动画
        pullCicleView.clearAnimation();
        reset();
    }

    /**
     * 重置状态
     */
    private void reset(){
        pullText.setText("下拉刷新！");
        pullArrowView.setRotation(0);
        pullArrowView.setVisibility(View.VISIBLE);//显示下拉箭头
        pullCicleView.setVisibility(View.GONE);//隐藏加载框
    }

}
