package com.dhq.animator;

import android.view.View;

/**
 * DESC 动画属性
 * Created by douhaoqiang on 2017/11/10.
 */

public class AnimatorBuilder {

    private PropertyName propertyName;//属性名称
    private int duringTime;//动画时间
    private View targetView;//动画的目标view
    private int repeatCount;//动画重复次数
    private int repeatMode;//动画重复模式
//    private Object[] values;//属性值变化范围
    private Class  values;//属性值变化范围

    public PropertyName getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(PropertyName propertyName) {
        this.propertyName = propertyName;
    }

    public int getDuringTime() {
        return duringTime;
    }

    public void setDuringTime(int duringTime) {
        this.duringTime = duringTime;
    }

    public View getTargetView() {
        return targetView;
    }

    public void setTargetView(View targetView) {
        this.targetView = targetView;
    }

    public Class getValues() {
        return values;
    }

    public void setValues(Class values) {
        this.values = values;
    }

    public int getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
    }

    public int getRepeatMode() {
        return repeatMode;
    }

    public void setRepeatMode(int repeatMode) {
        this.repeatMode = repeatMode;
    }

    public void build(){
        AnimatorUtil animatorUtil = new AnimatorUtil(this);
        animatorUtil.startAnimator();
    }

}
