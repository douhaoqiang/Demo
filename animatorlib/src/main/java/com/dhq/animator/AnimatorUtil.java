package com.dhq.animator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * DESC 动画工具类
 * Created by douhaoqiang on 2017/11/10.
 */

public class AnimatorUtil {

    private AnimatorBuilder mAnimatorBuilder;

    public AnimatorUtil(AnimatorBuilder animatorBuilder) {
        this.mAnimatorBuilder = animatorBuilder;
    }

    public static AnimatorBuilder getInstance() {
        return new AnimatorBuilder();
    }

    /**
     * 开始动画
     */
    public void startAnimator() {


    }

    //缩放动画
    private void ScaleAnimation(View view) {

        ObjectAnimator anim = ObjectAnimator.ofFloat(view, "scaleX", 0.0f, 0.2f, 0.4f, 0.6f, 0.8f, 1.0f);
        anim.setDuration(mAnimatorBuilder.getDuringTime());
        anim.setRepeatCount(mAnimatorBuilder.getRepeatCount());
        anim.start();

        //可以监听动画的四个动作
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
//                Log.e(TAG, "onAnimationStart");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
//                Log.e(TAG, "onAnimationEnd");
            }

            @Override
            public void onAnimationCancel(Animator animation) {
//                Log.e(TAG, "onAnimationCancel");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
//                Log.e(TAG, "onAnimationRepeat");
            }
        });

        //监听动画属性的改变 更新属性
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

            }
        });

        //可以自己选择动画监听的哪个动作
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });

    }

    private void TranslationAnimation(View view) {

        ObjectAnimator anim = ObjectAnimator.ofFloat(view, "translationX", -200, 0, 300, 0);
        anim.setInterpolator(new LinearInterpolator());
        anim.setDuration(mAnimatorBuilder.getDuringTime());
        anim.setRepeatCount(mAnimatorBuilder.getRepeatCount());
        anim.start();
    }

    private void RotateAnimation(View view) {
        ObjectAnimator anim = ObjectAnimator.ofFloat(view, "rotation", 0f, 360f);
//        ObjectAnimator objectAnimatorRotateX1 = ObjectAnimator.ofFloat(view, "rotationX", 0f, 360f);
//        ObjectAnimator objectAnimatorRotateX2 = ObjectAnimator.ofFloat(view, "rotationX", 0f, 360f);
        anim.setDuration(1000);
        anim.start();
    }

    private void AlpahAnimation(View view) {
        ObjectAnimator anim = ObjectAnimator.ofFloat(view, "alpha", 1.0f, 0.8f, 0.6f, 0.4f, 0.2f, 0.0f, 0.2f, 0.4f, 0.6f, 0.8f, 1.0f);
        anim.setRepeatMode(ObjectAnimator.REVERSE);
        anim.setDuration(mAnimatorBuilder.getDuringTime());
        anim.setRepeatCount(mAnimatorBuilder.getRepeatCount());
        anim.start();
    }


    /**
     * 缩放的组合动画
     *
     * @param view
     */
    private void ScaleHolderAnimation(View view) {
        /**动画组合**/
        PropertyValuesHolder objectAnimatorScaleX = PropertyValuesHolder.ofFloat("scaleX", 0f, 1f);
        PropertyValuesHolder objectAnimatorScaleY = PropertyValuesHolder.ofFloat("scaleY", 0f, 1f);
        /**同时播放两个动画**/
        ObjectAnimator.ofPropertyValuesHolder(view, objectAnimatorScaleX, objectAnimatorScaleY)
                .setDuration(1000)
                .start();
    }


    private void multAnimation(View view) {
        /**动画组合**/
        AnimatorSet animatorSetGroup1 = new AnimatorSet();
        ObjectAnimator animatorScaleX1 = ObjectAnimator.ofFloat(view, "scaleX", 0f, 1f);
        ObjectAnimator animatorScaleY1 = ObjectAnimator.ofFloat(view, "scaleY", 0f, 1f);
        ObjectAnimator animatorRotateX1 = ObjectAnimator.ofFloat(view, "rotationX", 0f, 360f);
        ObjectAnimator animatorRotateY1 = ObjectAnimator.ofFloat(view, "rotationY", 0f, 360f);
        animatorSetGroup1.setDuration(1000);
        animatorSetGroup1.play(animatorScaleX1).with(animatorScaleY1)
                .before(animatorRotateX1).before(animatorRotateY1);
        animatorSetGroup1.start();
    }
}
