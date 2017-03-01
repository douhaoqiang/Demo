package com.dhq.demo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.dhq.demo.R;

/**
 * DESC
 * Created by douhaoqiang on 2017/3/1.
 */
public class SlideIcon extends View {

    // 用来控制触摸事件是否可用
    private boolean mEnable=true;

    // 提示文字的Paint
    private Paint mTextPaint = null;

    // 提示文字的字体测量类
    private Paint.FontMetrics mFontMetrics;

    // 回调
    private MotionListener listener = null;

    // 手指按下时SlideIcon的X坐标
    private float mDownX = 0;

    // SlideIcon在非拖动状态下的X坐标
    private float mX = 0;

    // SliedIcon在拖动状态下X轴的偏移量
    private float mDistanceX = 0;

    // attr: 最小高度
    private int mMinHeight;
    // attr: Icon背景图
    private int mIconResId;

    // attr: Icon上显示的文字
    private String mIconText = "";

    // attr: Icon上文字的颜色
    private int mIconTextColor;

    // attr: Icon上文字的大小
    private float mIconTextSize;

    // attr: Icon的宽度占总长的比例
    private float mIconRatio;


    private float mHeight;
    private float mWidth;


    public SlideIcon(Context context) {
        this(context, null);
    }

    public SlideIcon(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SlideView, 0, 0);
        try {

            mIconResId = a.getResourceId(R.styleable.SlideView_icon_drawable, android.R.drawable.btn_default);
            mMinHeight = a.getDimensionPixelSize(R.styleable.SlideView_min_height, 240);

            mIconText = a.getString(R.styleable.SlideView_icon_text);
            mIconTextColor = a.getColor(R.styleable.SlideView_icon_text_color, Color.WHITE);
            mIconTextSize = a.getDimensionPixelSize(R.styleable.SlideView_icon_text_size, 44);
            mIconRatio = a.getFloat(R.styleable.SlideView_icon_ratio, 0.2f);

        } finally {
            a.recycle();
        }

        init();
    }

    public void setListener(MotionListener listener) {
        this.listener = listener;
    }

    public void setEnable(boolean enable) {
        this.mEnable = enable;
    }

    public boolean getEnable() {
        return mEnable;
    }

    private void init() {
        // 设置文字Paint
        mTextPaint = new Paint();
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setColor(mIconTextColor);
        mTextPaint.setTextSize(mIconTextSize);

        // 获取字体测量类
        mFontMetrics = mTextPaint.getFontMetrics();

        // 设置背景图
        setBackgroundResource(R.color.blue_btn_bg_color);

        // 设置可用
        mEnable = true;
    }

    /**
     * 重置SlideIcon
     */
    public void resetIcon() {
        mDownX = 0;
        mDistanceX = 0;
        mX = 0;
        mEnable = true;
    }

    public void setSlideSuccess(){

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 宽度和宽Mode
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        // 高度和高Mode
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        switch (widthMode) {
            case MeasureSpec.EXACTLY:   // layout_height为"wrap_content"时显示最小高度
                mWidth=widthSize;
                break;
            default:    // layout_height为"match_parent"或指定具体高度时显示默认高度
                mWidth=widthSize=mMinHeight;
                break;
        }
        switch (heightMode) {
            case MeasureSpec.EXACTLY:
                mHeight=heightSize;
                break;
            default:    // layout_height为"match_parent"或指定具体高度时显示默认高度
                mHeight=heightSize=mMinHeight;
                break;
        }

        setMeasuredDimension(widthSize,heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), mIconResId);
        Rect srcRect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());// 截取bmp1中的矩形区域

        int left= (int) mDistanceX;
        int top=0;
        int right=(int)(mDistanceX+bitmap.getWidth());
        int bottom= (int) mHeight;

        Rect dstRect = new Rect(left, top, right, bottom);// bmp1在目标画布中的位置
        canvas.drawBitmap(bitmap, srcRect, dstRect,  mTextPaint);

        // 获取文字baseline的Y坐标
        float baselineY = (getMeasuredHeight() - mFontMetrics.top - mFontMetrics.bottom) / 2;
        // 绘制文字
        canvas.drawText(mIconText == null ? "肯德基" : mIconText, getMeasuredWidth() / 2, baselineY, mTextPaint);





    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(mEnable){
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                // 记录手指按下时SlideIcon的X坐标
                mDownX = event.getRawX();
                return true;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                // 设置手指松开时SlideIcon的X坐标
                mDownX = 0;
                mX = mX + mDistanceX;
                mDistanceX = 0;
                // 触发松开回调并传入当前SlideIcon的X坐标
                if (listener != null) {
                    listener.onActionUp((int) mX);
                }
                invalidate();
                return true;
            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                // 记录SlideIcon在X轴上的拖动距离
                mDistanceX = event.getRawX() - mDownX;
                // 触发拖动回调并传入当前SlideIcon的拖动距离
                if (listener != null) {
                    listener.onActionMove((int) mDistanceX);
                }
                invalidate();
                return true;
            }else {
                return true;
            }
        }
        return true;

    }
}
