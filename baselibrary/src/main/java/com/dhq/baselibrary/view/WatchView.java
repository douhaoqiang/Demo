package com.dhq.baselibrary.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * DESC
 * Created by douhaoqiang on 2016/9/5.
 */

public class WatchView extends View {

    private Paint paintArc;
    private Paint paintText;

    public WatchView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    private void init(){
        paintArc=new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawArc(canvas);
        drawText(canvas);
    }


    /**
     * 绘制表盘
     * @param canvas
     */
    private void drawArc(Canvas canvas){

    }

    /**
     * 绘制文字
     * @param canvas
     */
    private void drawText(Canvas canvas){
        RectF rectF = new RectF(100,100,800,400);
        canvas.drawRoundRect(rectF,30,30,paintText);
    }


}
