package com.dhq.demo.utils;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

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

}
