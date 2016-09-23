package com.dhq.demo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

/**
 * DESC
 * Created by douhaoqiang on 2016/9/21.
 */

public class TestView extends View {

    public TestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
//        LayoutInflater.from(getContext()).inflate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(widthSize,heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


    private int getMeasure(int measureSpec) {
        int widthMode = MeasureSpec.getMode(measureSpec);
        int widthSize = MeasureSpec.getSize(measureSpec);
        int size = 0;//默认尺寸大小
        if (widthMode == MeasureSpec.EXACTLY) {
            //精确制定控件的大小
            size=widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //父布局能提供的最大尺寸
            size = Math.min(widthSize,size);
        } else {
            size = Math.min(widthSize,size);
        }
        return size;
    }
}
