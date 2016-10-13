package com.dhq.demo.refresh.header;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.dhq.demo.R;
import com.dhq.demo.refresh.IPullFooterView;
import com.dhq.demo.refresh.IPullHeaderView;

/**
 * DESC 箭头的headerview
 * Created by douhaoqiang on 2016/9/30.
 */
public class ArrowFooterView extends FrameLayout implements IPullFooterView {

    private static final String TAG = "ArrowHeaderView";

    private Context mContext;
    private ImageView pullArrowView;//下拉箭头
    private ImageView pullCicleView;//旋转图片
    private TextView pullText;//下拉文字提示
    private RotateAnimation refreshAnim;

    public ArrowFooterView(Context context) {
        this(context,null);
    }

    public ArrowFooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        init();
    }


    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.load_more,this);
        pullArrowView = (ImageView) findViewById(R.id.pullup_icon);
        pullCicleView = (ImageView) findViewById(R.id.loading_icon);
        pullText= (TextView) findViewById(R.id.loadstate_tv);
        refreshAnim = (RotateAnimation) AnimationUtils.loadAnimation(mContext, R.anim.rotating);
        // 添加匀速转动动画
        LinearInterpolator lir = new LinearInterpolator();
        refreshAnim.setInterpolator(lir);
    }

    @Override
    public void pullUpPrecent(float precent) {
        pullArrowView.setRotation(180*precent);
    }

    @Override
    public void prepareLoadmore() {
        pullText.setText("松开加载更多！");
    }

    @Override
    public void startLoadMore() {
        pullArrowView.setRotation(0);
        pullArrowView.setVisibility(View.GONE);
        pullCicleView.setVisibility(View.INVISIBLE);
        pullCicleView.setAnimation(refreshAnim);
        pullText.setText("正在加载！");
    }

    @Override
    public void completeLoadmore() {

        //清除旋转动画
        pullCicleView.clearAnimation();
        reset();
    }

    /**
     * 重置状态
     */
    private void reset(){
        pullText.setText("上拉加载！");
        //加载完成重置状态
        pullCicleView.clearAnimation();//清除旋转动画
        pullArrowView.setVisibility(View.VISIBLE);
        pullCicleView.setVisibility(View.GONE);
    }

}
