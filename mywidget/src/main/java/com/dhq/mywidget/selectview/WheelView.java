package com.dhq.mywidget.selectview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.OverScroller;

import java.util.ArrayList;
import java.util.List;

/**
 * 格尺滑动选择控件
 */
public class WheelView<T> extends View {

    private Paint mPaint;//背景刻度画笔
    private Paint mPaintRed;//中间刻度画笔

    private Paint mPaintText;//文字画笔
    private float mTextSize = 0;
    private float mPointY = 0f;
    private float mPointYoff = 0f;
    private int mPadding = dip2px(1);
    private OverScroller scroller;//控制滑动
    private VelocityTracker velocityTracker;

    private float mUnit = 50;
    private int mMaxValue = 200;
    private int mMinValue = 150;
    private int mMiddleValue = (mMaxValue + mMinValue) / 2;
    private boolean isCanvasLine = true;//是否绘制刻度
    private int bgColor = Color.rgb(228, 228, 228);//背景颜色
    private int textColor = Color.rgb(151, 151, 151);//文字的颜色
    private int textSelectColor = Color.rgb(151, 151, 151);//选中文字的颜色

    private int minvelocity;

    private int mDefaultHeight = 100;//控件的最小高度
    private int mDefaultWidth = 100;//控件的最小宽度

    private int mHeight = 100;//控件的高度
    private int mWidth = 300;//控件的宽度

    private T mSelectItem;

    public WheelView(Context context) {
        this(context, null);
    }

    public WheelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WheelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        scroller = new OverScroller(context);
        minvelocity = ViewConfiguration.get(getContext())
                .getScaledMinimumFlingVelocity();
        init(attrs);
    }


    private void init(AttributeSet attrs) {
        isCanvasLine = true;
        mTextSize = dip2px(12);
        bgColor = Color.rgb(228, 228, 228);
        textColor = Color.rgb(151, 151, 151);
        mUnit = 100.f;
        textSelectColor = Color.rgb(151, 151, 151);
        initPaint();
    }


    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(bgColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(dip2px(1));


        mPaintRed = new Paint();
        mPaintRed.setAntiAlias(true);
        mPaintRed.setColor(Color.RED);
        mPaintRed.setStrokeWidth(dip2px(1) * 3 / 2);
        mPaintRed.setStyle(Paint.Style.STROKE);

        mPaintText = new Paint();
        mPaintText.setAntiAlias(true);
        mPaintText.setColor(textColor);
        mPaintText.setTextSize(mTextSize);
        mPaintText.setStyle(Paint.Style.FILL);


    }


    @Override
    protected void onDraw(final Canvas canvas) {

        super.onDraw(canvas);

        canvasBg(canvas);
        canvasLineAndText(canvas);
        canvasRedLine(canvas);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);//获取宽的模式
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);//获取高的模式
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);//获取宽的尺寸
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);//获取高的尺寸

        //根据宽的模式进行判断并复制
        if (widthMode == MeasureSpec.EXACTLY) {
            //EXACTLY如果match_parent或者具体的值，直接赋值
            mWidth = widthSize;
        } else {
            mWidth = getPaddingLeft() + mDefaultWidth + getPaddingRight();
        }
        //高度跟宽度的处理方式一样
        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        } else {
            mHeight = getPaddingTop() + mDefaultHeight + getPaddingBottom();
        }
        //保存测量宽度和测量高度
        setMeasuredDimension(mWidth, mHeight);

    }

    /**
     * 绘制圆角矩形背景框
     *
     * @param canvas
     */
    private void canvasBg(Canvas canvas) {

        RectF rectF = new RectF();
        rectF.top = mPadding;
        rectF.left = mPadding;
        rectF.bottom = getMeasuredHeight() - mPadding;
        rectF.right = getMeasuredWidth() - mPadding;
        canvas.drawRoundRect(rectF, dip2px(2), dip2px(2), mPaint);

    }

    /**
     * 绘制中间红色刻度线
     *
     * @param canvas
     */
    private void canvasRedLine(Canvas canvas) {

        float y = getMeasuredHeight() / 2;
        canvas.drawLine(0, y - mUnit / 2, getMeasuredWidth(), y - mUnit / 2, mPaintRed);
        canvas.drawLine(0, y + mUnit / 2, getMeasuredWidth(), y + mUnit / 2, mPaintRed);


    }


    /**
     * 绘制刻度和文字
     *
     * @param canvas
     */
    private void canvasLineAndText(Canvas canvas) {

        int current = (int) (Math.rint(mPointY / mUnit));
        for (int i = 1; i <= listValue.size(); i++) {

            //距离中间刻度的间隔数
            int space = mMiddleValue - i;
            //计算刻度的纵坐标位置
            float y = getMeasuredHeight() / 2 - space * mUnit + mPointY;
            //判断该点坐标是否在视图范围内
            if (y > mPadding && y < getMeasuredHeight() - mPadding) {

                float x = getMeasuredWidth() / 2;

                mPaintText.setColor(textColor);

//                //计算绝对值在某一区间内文字显示高亮
//                if (Math.abs(mMiddleValue - current - i) < (mUnitLongLine / 2 + 1)) {
//                    mPaintText.setColor(textSelectColor);
//                }
                String text = "";
                if (mSelectListener != null) {
                    text = mSelectListener.setShowValue(listValue.get(i - 1));
                }

                canvas.drawText(text,
                        x - getFontlength(mPaintText, text) / 2,
                        y + getFontHeight(mPaintText) / 2,
                        mPaintText);

//                canvas.drawLine(x - getFontlength(mPaintText, text) / 2, y - getFontHeight(mPaintText) / 2, getMeasuredWidth(), y - getFontHeight(mPaintText) / 2, mPaintText);
//                canvas.drawLine(x - getFontlength(mPaintText, text) / 2, y + getFontHeight(mPaintText) / 2, getMeasuredWidth(), y + getFontHeight(mPaintText) / 2, mPaintText);

//                if (isCanvasLine) {
//                    //画刻度线
//                    canvas.drawLine(0, y, getMeasuredWidth(), y, mPaintText);
//                }

            }
        }

    }


    private boolean isActionUp = false;
    private float mLastY;
//    private boolean startAnim = true;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();
        float yPosition = event.getY();

        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
        velocityTracker.addMovement(event);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (mScrolleAnim != null) {
                    clearAnimation();
                }
                isActionUp = false;
                scroller.forceFinished(true);

                break;
            case MotionEvent.ACTION_MOVE:
                isActionUp = false;
                //计算滑动距离
                mPointYoff = yPosition - mLastY;
                mPointY = mPointY + mPointYoff;
                postInvalidate();

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isActionUp = true;
                countVelocityTracker(event);//控制快速滑动
                startAnim();
//                startAnim = true;
                return false;
            default:
                break;
        }

        mLastY = yPosition;
        return true;
    }


    @Override
    public void computeScroll() {

        if (scroller.computeScrollOffset()) {
            float mPointYoff = (scroller.getFinalY() - scroller.getCurrY());
            mPointY = mPointY + mPointYoff * functionSpeed();
            float absmPointY = Math.abs(mPointY);
            float mmPointY = (mMaxValue - mMinValue) * mUnit / 2;
            if (absmPointY < mmPointY) {//在视图范围内
                startAnim();
            }

        }
        super.computeScroll();

    }

    /**
     * 控制滑动速度
     *
     * @return
     */
    private float functionSpeed() {
        return 0.5f;
    }

    private void countVelocityTracker(MotionEvent event) {
        velocityTracker.computeCurrentVelocity(800, 800);
        float yVelocity = velocityTracker.getYVelocity();
        if (Math.abs(yVelocity) > minvelocity) {
            scroller.fling(0, 0, 0, (int) yVelocity, Integer.MIN_VALUE,
                    Integer.MAX_VALUE, 0, 0);
        }
    }

    public int dip2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public float getFontlength(Paint paint, String str) {
        return paint.measureText(str);
    }

    public float getFontHeight(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
//        return fm.descent - fm.ascent;
        return fm.leading - fm.ascent;
    }


    private ScrolleAnim mScrolleAnim;

    private class ScrolleAnim extends Animation {

        float fromY = 0f;
        float desY = 0f;

        public ScrolleAnim(float d, float f) {
            fromY = f;
            desY = d;
        }


        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);

            mPointY = fromY + (desY - fromY) * interpolatedTime;//计算动画每贞滑动的距离

            invalidate();


        }
    }


    private void startAnim() {
        float absmPointY = Math.abs(mPointY);
        float mmPointY = (mMaxValue - mMinValue) * mUnit / 2;
        if (absmPointY > mmPointY) {//超出视图范围
            if (mPointY > 0) {//最左
                moveToY(mMiddleValue - mMinValue, 300);
            } else {//最右
                moveToY(mMiddleValue - mMaxValue, 300);
            }


        } else {
            int space = (int) (Math.rint(mPointY / mUnit));//四舍五入计算出往左还是往右滑动
            moveToY(space, 200);

        }
    }


    private void moveToY(int distance, int time) {
        if (mScrolleAnim != null)
            clearAnimation();
        mScrolleAnim = new ScrolleAnim((distance * mUnit), mPointY);
        mScrolleAnim.setDuration(time);
        startAnimation(mScrolleAnim);
        if (mSelectListener != null) {
            int index=mMiddleValue - distance - 1;
            if(mSelectItem==null || mSelectItem!=listValue.get(index)){
                mSelectItem=listValue.get(index);
                mSelectListener.onSelectItem(listValue.get(index));
            }

        }

    }

    private List<T> listValue = new ArrayList<>();

    private SelectListener mSelectListener;


    public void setDatas(List<T> list) {

        listValue.clear();
        listValue.addAll(list);
        mMaxValue = listValue.size();
        mMinValue = 1;
        mMiddleValue = (mMaxValue + mMinValue) / 2;

        invalidate();

    }


    public void setSelectListener(SelectListener selectListener) {
        this.mSelectListener = selectListener;
    }

    public interface SelectListener<T> {
        String setShowValue(T item);

        void onSelectItem(T item);
    }


}
