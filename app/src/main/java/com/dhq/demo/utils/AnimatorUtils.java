package com.dhq.demo.utils;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

/**
 * DESC
 * Created by douhaoqiang on 2016/10/26.
 */
public class AnimatorUtils {
    private static final String TAG = "AnimatorUtils";


    private void ScaleAnimation(View view) {

        ObjectAnimator anim = ObjectAnimator.ofFloat(view, "scaleX", 0.0f, 0.2f, 0.4f, 0.6f, 0.8f, 1.0f);
        anim.setDuration(1000);
        anim.setRepeatCount(-1);
        anim.start();

        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.e(TAG, "onAnimationStart");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.e(TAG, "onAnimationEnd");
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Log.e(TAG, "onAnimationCancel");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Log.e(TAG, "onAnimationRepeat");
            }
        });
    }

    private void TranslationAnimation(View view) {

        ObjectAnimator anim = ObjectAnimator.ofFloat(view, "translationX", -200, 0, 300, 0);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(-1);
        anim.setDuration(2000);
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
        anim.setRepeatCount(-1);
        anim.setRepeatMode(ObjectAnimator.REVERSE);
        anim.setDuration(2000);
        anim.start();
    }


    /**
     * 缩放的组合动画
     * @param view
     */
    private void ScaleHolderAnimation(View view){
        /**动画组合**/
        PropertyValuesHolder objectAnimatorScaleX = PropertyValuesHolder.ofFloat("scaleX", 0f, 1f);
        PropertyValuesHolder objectAnimatorScaleY = PropertyValuesHolder.ofFloat("scaleY", 0f, 1f);
        /**同时播放两个动画**/
        ObjectAnimator.ofPropertyValuesHolder(view, objectAnimatorScaleX, objectAnimatorScaleY).setDuration(1000).start();
    }


    private void multAnimation(View view){
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
