package com.dhq.mywidget.ui.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.View;

import com.dhq.mywidget.R;


/**
 * DESC
 * Created by douhaoqiang on 2018/5/25.
 */

public class CustomBarChart extends View {

    // 坐标单位
    private String[] xLabel;
    private String[] yLabel = new String[5];
    // 曲线数据
    private int[] dataList;
    // 距离左边偏移量
    private int marginX = 100;
    // 原点坐标
    private int xPoint;
    private int yPoint;
    // X,Y轴的单位长度
    private int xScale;
    private int yScale;

    private int yUnit;

    // 画笔
    private Paint paintTitle;
    private Paint paintAxes;
    private Paint paintCoordinate;
    private Paint paintRectF;
    private Paint paintValue;
    private float bubbleHeight;
    private String title;

    public CustomBarChart(Context context, String[] xLabel, int yUnit,
                          int[] dataList,String title) {
        super(context);
        this.xLabel = xLabel;
        this.yUnit = yUnit;
        this.title = title;
        this.dataList = dataList;
        for (int i = 0; i < 5; i++) {
            yLabel[i] = String.valueOf(i * yUnit);
        }
    }

    public CustomBarChart(Context context) {
        super(context);
    }

    /**
     * 初始化数据值和画笔
     */
    public void init() {
        xPoint =  marginX;
        yPoint = this.getHeight() - marginX;
        xScale = (this.getWidth() - marginX) / (xLabel.length + 1);
        yScale = (this.getHeight() ) / 5;

        paintTitle = new Paint();
        paintTitle.setStyle(Paint.Style.STROKE);
        paintTitle.setAntiAlias(true);
        paintTitle.setDither(true);
        paintTitle.setColor(ContextCompat.getColor(getContext(), android.R.color.black));
        paintTitle.setTextSize(80);

        paintAxes = new Paint();
        paintAxes.setStyle(Paint.Style.STROKE);
        paintAxes.setAntiAlias(true);
        paintAxes.setDither(true);
        paintAxes.setColor(ContextCompat.getColor(getContext(), android.R.color.black));
        paintAxes.setStrokeWidth(4);

        paintCoordinate = new Paint();
        paintCoordinate.setStyle(Paint.Style.STROKE);
        paintCoordinate.setDither(true);
        paintCoordinate.setAntiAlias(true);
        paintCoordinate.setColor(ContextCompat.getColor(getContext(), android.R.color.black));
        paintCoordinate.setTextSize(40);

        paintRectF = new Paint();
        paintRectF.setStyle(Paint.Style.FILL);
        paintRectF.setDither(true);
        paintRectF.setAntiAlias(true);
        paintRectF.setStrokeWidth(1);

        paintValue = new Paint();
        paintValue.setStyle(Paint.Style.STROKE);
        paintValue.setAntiAlias(true);
        paintValue.setDither(true);
        paintValue.setTextAlign(Paint.Align.CENTER);
        paintValue.setTextSize(10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(ContextCompat.getColor(getContext(), R.color.white));
        init();
        drawAxesLine(canvas, paintAxes);
        drawCoordinate(canvas, paintCoordinate);
        drawBar(canvas, paintRectF, dataList, R.color.blue_btn_bg_color);

        float bubbleLeft = xPoint;
        float bubbleRight = this.getWidth();
        float fontlength = getFontlength(paintTitle, title);
        float fontHeight = getFontHeight(paintTitle);
        bubbleHeight = fontHeight + 2 * 20;
        //绘制描述文字
        canvas.drawText(title, (bubbleRight + bubbleLeft) / 2 - fontlength / 2, marginX/3 + getFontBaseLineHeight(paintTitle), paintTitle);

    }

    /**
     * 绘制坐标轴
     */
    private void drawAxesLine(Canvas canvas, Paint paint) {
        // Y
        canvas.drawLine(xPoint, yPoint, xPoint, 0, paint);

        // 横线
        for (int i = 0; i < 6; i++) {

            float y= yPoint-i*yScale;

            canvas.drawLine(0, y, this.getWidth(), y, paint);
        }

    }

    /**
     * 绘制刻度
     */
    private void drawCoordinate(Canvas canvas, Paint paint) {

        // X轴坐标
        for (int i = 0; i < xLabel.length; i++) {
            paint.setTextAlign(Paint.Align.CENTER);
            int startX = xPoint + (i + 1) * xScale;
            canvas.drawText(xLabel[i], startX, this.getHeight()-marginX/2, paint);
        }

        // Y轴坐标
        for (int i = 0; i < 5; i++) {
            paint.setTextAlign(Paint.Align.LEFT);
            int startY = yPoint - i * yScale;
            canvas.drawText(yLabel[i], marginX/3, startY - 10, paint);
        }
    }

    /**
     * 绘制单柱形
     */
    private void drawBar(Canvas canvas, Paint paint, int data[], int color) {
        for (int i = 1; i <= xLabel.length; i++) {
            int startX = xPoint + i * xScale;
            RectF rect = new RectF(
                    startX - 70,
                    toY(data[i - 1]),
                    startX + 70,
                    yPoint);
            paint.setColor(ContextCompat.getColor(getContext(), color));
            canvas.drawRect(rect, paint);
        }
    }

    /**
     * 数据按比例转坐标
     */
    private float toY(int num) {

        float y;
        try {
            y = yPoint-num * 1.0f * yScale / yUnit;
        } catch (Exception e) {
            return 0;
        }
        return y;
    }

    /*
     * 获取文字的长度
     *
             * @param paint
     * @param str
     * @return
             */
    private float getFontlength(Paint paint, String str) {
        return paint.measureText(str);
    }

    /**
     * 获取文字的高度
     *
     * @param paint
     * @return
     */
    private float getFontHeight(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return fm.descent - fm.ascent;
    }

    /**
     * 计算文字基础线的高度
     *
     * @param paint
     * @return
     */
    public float getFontBaseLineHeight(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (bubbleHeight - fm.descent - fm.ascent) / 2;
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    /**
     * convert sp to its equivalent px
     */
    private int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }

}
