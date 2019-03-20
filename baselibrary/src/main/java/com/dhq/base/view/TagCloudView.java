package com.dhq.base.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dhq.base.R;

import java.util.List;

/**
 * Created by NeXT on 15-7-29.
 */
public class TagCloudView<T> extends ViewGroup {

    private static final String TAG = TagCloudView.class.getSimpleName();
    private static final int TYPE_TEXT_NORMAL = 1;
    private List<T> tags;

    private LayoutInflater mInflater;
    private OnTagListener onTagClickListener;

    private int sizeWidth;
    private int sizeHeight;

    private float mTagSize;    //文字大小
    private int mTagColor;     //文字颜色
    private int mBackground;   //背景
    private int mTagBorderHor; //水平间距
    private int mTagBorderVer; //垂直间距

    private boolean mCanTagClick;


    private static final int DEFAULT_TEXT_COLOR = Color.BLACK;
    private static final int DEFAULT_TEXT_BACKGROUND = R.drawable.tag_background;

    private static final boolean DEFAULT_CAN_TAG_CLICK = true;

    public TagCloudView(Context context) {
        this(context, null);
    }

    public TagCloudView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagCloudView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mInflater = LayoutInflater.from(context);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.TagCloudView,
                defStyleAttr,
                defStyleAttr
        );

        mTagSize = a.getDimensionPixelSize(R.styleable.TagCloudView_tagTextSize, getDefaultValue(R.dimen.tag_cloud_textsize));
        mTagColor = a.getColor(R.styleable.TagCloudView_tagTextColor, DEFAULT_TEXT_COLOR);
        mBackground = a.getResourceId(R.styleable.TagCloudView_tagBackground, DEFAULT_TEXT_BACKGROUND);
        mTagBorderHor = a.getDimensionPixelSize(
                R.styleable.TagCloudView_tagHorizontalMargen, getDefaultValue(R.dimen.tag_cloud_hor_margen));
        mTagBorderVer = a.getDimensionPixelSize(
                R.styleable.TagCloudView_tagVerticalMargen, getDefaultValue(R.dimen.tag_cloud_ver_margen));

        mCanTagClick = a.getBoolean(R.styleable.TagCloudView_tagCanClick, DEFAULT_CAN_TAG_CLICK);


        a.recycle();

    }

    private int getDefaultValue(int res){

        return getContext().getResources().getDimensionPixelOffset(R.dimen.tag_cloud_textsize);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return (!mCanTagClick) || super.onInterceptTouchEvent(ev);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
    }

    /**
     * 计算 ChildView 宽高
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @SuppressLint("DrawAllocation")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /**
         * 计算 ViewGroup 上级容器为其推荐的宽高
         */
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        //计算 childView 宽高
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int totalWidth = 0;
        int totalHeight = mTagBorderVer;

        totalHeight = getTotalHeight(totalWidth, totalHeight);

        /**
         * 高度根据设置改变
         * 如果为 MATCH_PARENT 则充满父窗体，否则根据内容自定义高度
         */
        setMeasuredDimension(sizeWidth, (heightMode == MeasureSpec.EXACTLY ? sizeHeight : totalHeight));

    }

    /**
     * 计算视图高度
     *
     * @param totalWidth
     * @param totalHeight
     * @return
     */
    private int getTotalHeight(int totalWidth, int totalHeight) {
        int childWidth;
        int childHeight;
        //开始位置
        int leftLocation = getPaddingLeft() + ((MarginLayoutParams) getLayoutParams()).leftMargin;
        int rightLocation = getPaddingRight() + ((MarginLayoutParams) getLayoutParams()).rightMargin;
        int topLocation = getPaddingTop() + ((MarginLayoutParams) getLayoutParams()).topMargin;
        int bottomLocation = getPaddingBottom() + ((MarginLayoutParams) getLayoutParams()).bottomMargin;

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            childWidth = child.getMeasuredWidth();
            childHeight = child.getMeasuredHeight();

//            totalWidth += childWidth + mViewBorder;

            if (i == 0) {
                totalWidth = leftLocation + childWidth;
                totalHeight = childHeight + topLocation;
            } else {
                totalWidth += childWidth + mTagBorderHor;
            }

            // + marginLeft 保证最右侧与 ViewGroup 右边距有边界
            if (totalWidth + rightLocation > sizeWidth) {
                totalWidth = leftLocation + childWidth;
                totalHeight += childHeight + mTagBorderVer;

            }
            child.layout(
                    totalWidth - childWidth,
                    totalHeight - childHeight,
                    totalWidth ,
                    totalHeight);
        }
        return totalHeight + bottomLocation;
    }


    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return super.generateLayoutParams(attrs);
    }

    /**
     * 这是标签列表
     *
     * @param tagList 标签列表内容
     */
    public void setTags(List<T> tagList) {
        this.tags = tagList;
        this.removeAllViews();
        if (tags != null && tags.size() > 0) {
            for (int i = 0; i < tags.size(); i++) {
                final T data = tags.get(i);
                TextView tagView = (TextView) mInflater.inflate(R.layout.item_tag, null);
                tagView.setBackgroundResource(mBackground);
                tagView.setTextSize(TypedValue.COMPLEX_UNIT_PX,mTagSize);
                tagView.setTextColor(mTagColor);
                LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                tagView.setLayoutParams(layoutParams);
                if (onTagClickListener != null) {
                    onTagClickListener.convertView(data,tagView);
                }

                tagView.setTag(TYPE_TEXT_NORMAL);
                final int finalI = i;
                tagView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onTagClickListener != null) {
                            onTagClickListener.onTagClick(data,finalI);
                        }
                    }
                });
                addView(tagView);
            }
        }
        postInvalidate();
    }

    public void setOnTagClickListener(OnTagListener onTagClickListener) {
        this.onTagClickListener = onTagClickListener;
    }

    public interface OnTagListener<T> {

        void convertView(T data,TextView tagView);

        void onTagClick( T data,int position);
    }

}
