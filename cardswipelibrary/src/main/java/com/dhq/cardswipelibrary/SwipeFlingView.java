package com.dhq.cardswipelibrary;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.FrameLayout;

import java.util.ArrayList;


public class SwipeFlingView extends ViewGroup {

    private ArrayList<View> cacheItems = new ArrayList<>();

    //缩放层叠效果
    private int yOffsetStep; // view叠加垂直偏移量的步长
    private static final float SCALE_STEP = 0.08f; // view叠加缩放的步长
    //缩放层叠效果

    private int MAX_VISIBLE = 4; // 最大显示数， 值建议最小为4
    private int MIN_ADAPTER_STACK = 6;
    private float ROTATION_DEGREES = 2f;
    private int LAST_OBJECT_IN_STACK = 0;

    private Adapter mAdapter;
    private onFlingListener mFlingListener;
    private AdapterDataSetObserver mDataSetObserver;
    private boolean mInLayout = false;//表示是否正在加载界面
    private View mActiveCard = null;
    private OnItemClickListener mOnItemClickListener;
    private FlingCardListener flingCardListener;

    // 支持左右滑
    public boolean isNeedSwipe = true;

    private int initTop;
    private int initLeft;
    private int widthMeasureSpec;
    private int heightMeasureSpec;

    public SwipeFlingView(Context context) {
        this(context, null);
    }

    public SwipeFlingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeFlingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        init(attrs);

    }

    /**
     * 获取自定义参数变量
     *
     * @param attrs
     */
    private void init(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.SwipeFlingView);
        MAX_VISIBLE = a.getInt(R.styleable.SwipeFlingView_max_visible, MAX_VISIBLE);
        MIN_ADAPTER_STACK = a.getInt(R.styleable.SwipeFlingView_min_adapter_stack, MIN_ADAPTER_STACK);
        ROTATION_DEGREES = a.getFloat(R.styleable.SwipeFlingView_rotation_degrees, ROTATION_DEGREES);
        yOffsetStep = a.getDimensionPixelOffset(R.styleable.SwipeFlingView_y_offset_step, 0);
        a.recycle();
    }

    /**
     * 设置是否支持左右滑动（默认支持左右滑动）
     *
     * @param isNeedSwipe
     */
    public void setIsNeedSwipe(boolean isNeedSwipe) {
        this.isNeedSwipe = isNeedSwipe;
    }

    public View getSelectedView() {
        return mActiveCard;
    }


    @Override
    public void requestLayout() {
        if (!mInLayout) {
            //如果正在加载布局 则不做出去
            super.requestLayout();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.widthMeasureSpec = widthMeasureSpec;
        this.heightMeasureSpec = heightMeasureSpec;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

        if (mAdapter == null) {
            return;
        }

        mInLayout = true;
        final int adapterCount = mAdapter.getCount();
        if (adapterCount == 0) {
            removeAndAddToCache(0);
        } else {
            View topCard = getChildAt(LAST_OBJECT_IN_STACK);
            if (mActiveCard != null && topCard != null && topCard == mActiveCard) {
                removeAndAddToCache(1);
                layoutChildren(1, adapterCount);
            } else {
                removeAndAddToCache(0);
                layoutChildren(0, adapterCount);
                setTopView();
            }
        }
        mInLayout = false;

        if (initTop == 0 && initLeft == 0 && mActiveCard != null) {
            initTop = mActiveCard.getTop();
            initLeft = mActiveCard.getLeft();
        }

        if (adapterCount < MIN_ADAPTER_STACK) {
            if (mFlingListener != null) {
                mFlingListener.onAdapterAboutToEmpty(adapterCount);
            }
        }
    }


    /**
     * 移除或者添加进缓存
     *
     * @param remain
     */
    private void removeAndAddToCache(int remain) {
        View view;
        for (int i = 0; i < getChildCount() - remain; ) {
            view = getChildAt(i);
            removeViewInLayout(view);
            cacheItems.add(view);
        }
    }

    /**
     * 加载需要显示的卡片
     *
     * @param startingIndex 显示的开始下标
     * @param adapterCount  需要显示所有数量
     */
    private void layoutChildren(int startingIndex, int adapterCount) {
        while (startingIndex < Math.min(adapterCount, MAX_VISIBLE)) {
            View item = null;
            if (cacheItems.size() > 0) {
                item = cacheItems.get(0);
                cacheItems.remove(item);
            }
            View newUnderChild = mAdapter.getView(startingIndex, item, this);
            makeAndAddView(newUnderChild, startingIndex);
            LAST_OBJECT_IN_STACK = startingIndex;
            startingIndex++;
        }
    }


    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void makeAndAddView(View child, int index) {

        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) child.getLayoutParams();
        //将view插入到第一个view的位置(true表示不会重新加载测量child参数)
        addViewInLayout(child, 0, lp, true);

        boolean needToMeasure = child.isLayoutRequested();
        if (needToMeasure) {
            int childWidthSpec = getChildMeasureSpec(widthMeasureSpec,
                    getPaddingLeft() + getPaddingRight() + lp.leftMargin + lp.rightMargin,
                    lp.width);
            int childHeightSpec = getChildMeasureSpec(heightMeasureSpec,
                    getPaddingTop() + getPaddingBottom() + lp.topMargin + lp.bottomMargin,
                    lp.height);
            child.measure(childWidthSpec, childHeightSpec);
        } else {
            cleanupLayoutState(child);
        }

        int width = child.getMeasuredWidth();
        int height = child.getMeasuredHeight();


        int childLeft = (getWidth() + getPaddingLeft() - getPaddingRight() - width) / 2 +
                lp.leftMargin - lp.rightMargin;

        int childTop = getPaddingTop() + lp.topMargin;

        child.layout(childLeft, childTop, childLeft + width, childTop + height);
        // 缩放层叠效果
        scaleChildView(child, index);
    }

    /**
     * 缩放卡片的大小
     * @param child
     * @param index
     */
    private void scaleChildView(View child, int index) {
        //最多显示3张卡片的叠加效果
        if (index >= 0 && index < MAX_VISIBLE) {
            int multiple;
            if (index > 2) {
                multiple = 2;
            } else {
                multiple = index;
            }
            child.offsetTopAndBottom(yOffsetStep * multiple);
            child.setScaleX(1 - SCALE_STEP * multiple);
            child.setScaleY(1 - SCALE_STEP * multiple);
        }
    }

    private void adjustChildrenOfUnderTopView(float scrollRate) {
        int count = getChildCount();
        if (count > 1) {
            int i;
            int multiple;
            if (count == 2) {
                i = LAST_OBJECT_IN_STACK - 1;
                multiple = 1;
            } else {
                i = LAST_OBJECT_IN_STACK - 2;
                multiple = 2;
            }
            float rate = Math.abs(scrollRate);
            for (; i < LAST_OBJECT_IN_STACK; i++, multiple--) {
                View underTopView = getChildAt(i);
                int offset = (int) (yOffsetStep * (multiple - rate));
                underTopView.offsetTopAndBottom(offset - underTopView.getTop() + initTop);
                underTopView.setScaleX(1 - SCALE_STEP * multiple + SCALE_STEP * rate);
                underTopView.setScaleY(1 - SCALE_STEP * multiple + SCALE_STEP * rate);
            }
        }
    }

    /**
     * Set the top view and add the fling listener
     * 设置头部view 和 增加滑动监听
     */
    private void setTopView() {
        if (getChildCount() > 0) {
            mActiveCard = getChildAt(LAST_OBJECT_IN_STACK);
            if (mActiveCard != null && mFlingListener != null) {

                flingCardListener = new FlingCardListener(mActiveCard, mAdapter.getItem(0),
                        ROTATION_DEGREES, new FlingCardListener.FlingListener() {

                    @Override
                    public void onCardExited() {
                        removeViewInLayout(mActiveCard);
                        mActiveCard = null;
                        mFlingListener.removeFirstObjectInAdapter();
                    }

                    @Override
                    public void leftExit(Object dataObject) {
                        mFlingListener.onLeftCardExit(dataObject);
                    }

                    @Override
                    public void rightExit(Object dataObject) {
                        mFlingListener.onRightCardExit(dataObject);
                    }

                    @Override
                    public void onClick(MotionEvent event, View v, Object dataObject) {
                        if (mOnItemClickListener != null)
                            mOnItemClickListener.onItemClicked(event, v, dataObject);
                    }

                    @Override
                    public void onScroll(float progress, float scrollXProgress) {
//                                Log.e("Log", "onScroll " + progress);
                        adjustChildrenOfUnderTopView(progress);
                        mFlingListener.onScroll(progress, scrollXProgress);
                    }
                });
                // 设置是否支持左右滑
                flingCardListener.setIsNeedSwipe(isNeedSwipe);

                //将view的touch事件交给FlingCardListener处理
                mActiveCard.setOnTouchListener(flingCardListener);
            }
        }
    }

    public FlingCardListener getTopCardListener() throws NullPointerException {
        if (flingCardListener == null) {
            throw new NullPointerException("flingCardListener is null");
        }
        return flingCardListener;
    }

    public void setMaxVisible(int MAX_VISIBLE) {
        this.MAX_VISIBLE = MAX_VISIBLE;
    }

    public void setMinStackInAdapter(int MIN_ADAPTER_STACK) {
        this.MIN_ADAPTER_STACK = MIN_ADAPTER_STACK;
    }

    /**
     * 左滑移除卡片
     */
    public void swipeLeft() {
        getTopCardListener().selectLeft();
    }

    public void swipeLeft(int duration) {
        getTopCardListener().selectLeft(duration);
    }

    /**
     * 右滑移除卡片
     */
    public void swipeRight() {
        getTopCardListener().selectRight();
    }

    public void swipeRight(int duration) {
        getTopCardListener().selectRight(duration);
    }


    public void setAdapter(Adapter adapter) {
        if (mAdapter != null && mDataSetObserver != null) {
            mAdapter.unregisterDataSetObserver(mDataSetObserver);
            mDataSetObserver = null;
        }

        mAdapter = adapter;

        if (mAdapter != null && mDataSetObserver == null) {
            //添加数据监听
            mDataSetObserver = new AdapterDataSetObserver();
            mAdapter.registerDataSetObserver(mDataSetObserver);
        }
    }

    public void setFlingListener(onFlingListener onFlingListener) {
        this.mFlingListener = onFlingListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }


    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new FrameLayout.LayoutParams(getContext(), attrs);
    }


    private class AdapterDataSetObserver extends DataSetObserver {
        @Override
        public void onChanged() {
            requestLayout();
        }

        @Override
        public void onInvalidated() {
            requestLayout();
        }

    }


    public interface OnItemClickListener {
        void onItemClicked(MotionEvent event, View v, Object dataObject);
    }

    /**
     * 滑动事件监听接口
     */
    public interface onFlingListener {
        void removeFirstObjectInAdapter();

        void onLeftCardExit(Object dataObject);

        void onRightCardExit(Object dataObject);

        void onAdapterAboutToEmpty(int itemsInAdapter);

        void onScroll(float progress, float scrollXProgress);
    }


}
