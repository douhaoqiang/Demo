package com.dhq.demo.widget;

/**
 * DESC
 * Created by douhaoqiang on 2017/3/1.
 */
public interface MotionListener {
    /**
     * 拖动时的回调
     *
     * @param distanceX SlideIcon的X轴偏移量
     */
    void onActionMove(int distanceX);

    /**
     * 松开时的回调
     *
     * @param x SlideIcon的X坐标
     */
    void onActionUp(int x);
}
