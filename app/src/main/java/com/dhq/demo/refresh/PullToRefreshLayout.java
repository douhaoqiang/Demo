package com.dhq.demo.refresh;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.LinearLayout;

import com.dhq.demo.R;
import com.dhq.demo.refresh.header.ArrowFooterView;
import com.dhq.demo.refresh.header.ArrowHeaderView;
import com.dhq.demo.refresh.mode.Mode;

/**
 *
 */
public class PullToRefreshLayout extends LinearLayout {
    public static final String TAG = "PullToRefreshLayout";
    // 初始状态
    public static final int INIT = 0;
    // 释放刷新
    public static final int RELEASE_TO_REFRESH = 1;
    // 正在刷新
    public static final int REFRESHING = 2;
    // 释放加载
    public static final int RELEASE_TO_LOAD = 3;
    // 正在加载
    public static final int LOADING = 4;
    // 操作完毕
    public static final int DONE = 5;
    // 当前状态
    private int state = INIT;
    // 刷新回调接口
    private OnRefreshListener mListener;
    // 按下Y坐标，上一个事件点Y坐标
    private float downY;

    private float pullY = 0;

    // 释放刷新的距离
    private float refreshDist = 200;
    // 释放加载的距离
    private float loadmoreDist = 200;

    // 回滚速度
    public float MOVE_SPEED = 8;
    // 第一次执行布局
    private boolean isLayout = false;
    // 手指滑动距离与下拉头的滑动距离比，中间会随正切函数变化
    private float radio = 2;

    // 下拉箭头的转180°动画
    private RotateAnimation rotateAnimation;
    // 均匀旋转动画
    private RotateAnimation refreshingAnimation;

    // 下拉头
    private View refreshView;

    // 上拉头
    private View loadmoreView;

    // 实现了Pullable接口的View
    private View pullableView;
    // 控制上拉下拉记载模式，默认情况为BOTH
    private Mode mode=Mode.BOTH;

    //上拉下拉的转换比例
    private float pullRadio = 0.8f;
    //刷新的样式
    private String refreshtype;

    //getScaledTouchSlop是一个距离，表示滑动的时候，手的移动要大于这个距离才开始移动控件。如果小于这个距离就不触发移动控件
    private int mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();

//    /**
//     * 执行自动回滚的handler
//     */
//    Handler updateHandler = new Handler() {
//
//        @Override
//        public void handleMessage(Message msg) {
//            // 回弹速度随下拉距离moveDeltaY增大而增大
//            MOVE_SPEED = (float) (8 + 5 * Math.tan(Math.PI / 2
//                    / getMeasuredHeight() * (pullDownY + Math.abs(pullUpY))));
//            if (!isTouch) {
//                // 正在刷新，且没有往上推的话则悬停，显示"正在刷新..."
//                if (state == REFRESHING && pullDownY <= refreshDist) {
//                    pullDownY = refreshDist;
//                    timer.cancel();
//                } else if (state == LOADING && -pullUpY <= loadmoreDist) {
//                    pullUpY = -loadmoreDist;
//                    timer.cancel();
//                }
//
//            }
//            if (pullDownY > 0) {
//                pullDownY -= MOVE_SPEED;
//            }else if (pullUpY < 0) {
//                pullUpY += MOVE_SPEED;
//            }
//            if (pullDownY < 0) {
//                // 已完成回弹
//                pullDownY = 0;
//                pullView.clearAnimation();
//                // 隐藏下拉头时有可能还在刷新，只有当前状态不是正在刷新时才改变状态
//                if (state != REFRESHING && state != LOADING)
//                    changeState(INIT);
//                timer.cancel();
//                requestLayout();
//            }
//            if (pullUpY > 0) {
//                // 已完成回弹
//                pullUpY = 0;
//                pullUpView.clearAnimation();
//                // 隐藏上拉头时有可能还在刷新，只有当前状态不是正在刷新时才改变状态
//                if (state != REFRESHING && state != LOADING)
//                    changeState(INIT);
//                timer.cancel();
//                requestLayout();
//            }
//            // 刷新布局,会自动调用onLayout
//            requestLayout();
//            // 没有拖拉或者回弹完成
//            if (pullDownY + Math.abs(pullUpY) == 0)
//                timer.cancel();
//        }
//
//    };


    public PullToRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setOrientation(VERTICAL);
        //加载自定义的属性
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.refresh);
        refreshtype = a.getString(R.styleable.refresh_refreshtype);

//        timer = new MyTimer(updateHandler);
        rotateAnimation = (RotateAnimation) AnimationUtils.loadAnimation(
                context, R.anim.reverse_anim);
        refreshingAnimation = (RotateAnimation) AnimationUtils.loadAnimation(
                context, R.anim.rotating);
        // 添加匀速转动动画
        LinearInterpolator lir = new LinearInterpolator();
        rotateAnimation.setInterpolator(lir);
        refreshingAnimation.setInterpolator(lir);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childNum = getChildCount();
        if (childNum != 1) {
            throw new IllegalStateException("Children num must only one!");
        }
        pullableView = getChildAt(0);
        setRefreshType();
    }

    /**
     * 添加对应的刷新样式
     */
    private void setRefreshType() {

        Log.e(TAG,"setRefreshType");
        if ("11".equals(refreshtype)) {

        }

        //添加头部刷新布局
        refreshView = new ArrowHeaderView(getContext());
        addView(refreshView, 0);

        //获取底部加载更多布局
        loadmoreView = new ArrowFooterView(getContext());
        addView(loadmoreView);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        Log.e(TAG, "onMeasure");
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        measureChild(refreshView, widthMeasureSpec, heightMeasureSpec);
        measureChild(loadmoreView, widthMeasureSpec, heightMeasureSpec);
        measureChild(pullableView, widthMeasureSpec, heightMeasureSpec);
        refreshDist = refreshView.getMeasuredHeight();
        loadmoreDist = loadmoreView.getMeasuredHeight();
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        Log.e(TAG, "onLayout");
        // 改变子控件的布局，这里直接用(pullDownY + pullUpY)作为偏移量，这样就可以不对当前状态作区分
        refreshView.layout(0,
                (int) (pullY) - refreshView.getMeasuredHeight(),
                refreshView.getMeasuredWidth(), (int) (pullY));

        pullableView.layout(0, (int) (pullY),
                pullableView.getMeasuredWidth(),
                (int) (pullY) + pullableView.getMeasuredHeight());

        loadmoreView.layout(0,
                (int) (pullY) + pullableView.getMeasuredHeight(),
                loadmoreView.getMeasuredWidth(),
                (int) (pullY) + pullableView.getMeasuredHeight() + loadmoreView.getMeasuredHeight());
    }


    public void setOnRefreshListener(OnRefreshListener listener) {
        mListener = listener;
    }

    private void hide() {

    }

    /**
     * 完成刷新操作，显示刷新结果。注意：刷新完成后一定要调用这个方法
     */
    public void refreshFinish() {

        changeState(DONE);
        hide();
    }

    /**
     * 加载完毕，显示加载结果。注意：加载完成后一定要调用这个方法
     */
    public void loadmoreFinish() {
        changeState(DONE);
        hide();
    }

    private void changeState(int to) {
        state = to;
        switch (state) {
            case INIT:
                Log.e(TAG, "INIT");
                break;
            case RELEASE_TO_REFRESH:
                Log.e(TAG, "RELEASE_TO_REFRESH");
                ((IPullHeaderView) refreshView).prepareRefresh();
                break;
            case REFRESHING:
                Log.e(TAG, "REFRESHING");
                // 正在刷新状态
                ((IPullHeaderView) refreshView).startRefresh();
                break;
            case RELEASE_TO_LOAD:
                Log.e(TAG, "RELEASE_TO_LOAD");
                // 释放加载状态
                ((IPullFooterView) loadmoreView).prepareLoadmore();
                break;
            case LOADING:
                Log.e(TAG, "LOADING");
                // 正在加载状态
                ((IPullFooterView) loadmoreView).startLoadMore();
                break;
            case DONE:
                Log.e(TAG, "DONE");
                // 刷新或加载完毕，啥都不做
                ((IPullFooterView) loadmoreView).completeLoadmore();
                break;
        }
    }

    public void setMode(Mode mode){
        if(mode!=null){
            this.mode=mode;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                downY = ev.getY();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_MOVE:

                if (mode==Mode.NONE || state == LOADING) {
                    //当正在刷新且不可以上下拉时 不监听移动操作
                    break;
                }

                pullY = (ev.getY() - downY) / radio;//Y轴上移动的距离

                if (pullY > 0 && !canChildScrollDown()) {
                    if (pullY > refreshDist) {
                        pullY = refreshDist;
                    }
                    ((IPullHeaderView) refreshView).pullDownPrecent(pullY / refreshDist);
                    //表示下拉
                    if (pullY < refreshDist * pullRadio) {
                        // 如果下拉距离没达到刷新的距离且当前状态是释放刷新，改变状态为下拉刷新
                        changeState(INIT);
                    } else {
                        // 如果下拉距离达到刷新的距离且当前状态是初始状态刷新，改变状态为释放刷新
                        changeState(RELEASE_TO_REFRESH);
                    }
                    requestLayout();
                } else if (pullY < 0 && !canChildScrollUp()) {
                    if (pullY < -loadmoreDist) {
                        pullY = -loadmoreDist;
                    }

                    ((IPullFooterView) loadmoreView).pullUpPrecent(pullY / loadmoreDist);
                    //表示上拉
                    // 下面是判断上拉加载的，同上，注意pullUpY是负值
                    if (-pullY < loadmoreDist * pullRadio) {
                        changeState(INIT);
                    } else {
                        changeState(RELEASE_TO_LOAD);
                    }
                    requestLayout();
                }

                break;
            case MotionEvent.ACTION_UP:

                if (state == RELEASE_TO_REFRESH) {
                    changeState(REFRESHING);
                    // 刷新操作
                    if (mListener != null) {
                        mListener.onRefresh(this);
                    }
                } else if (state == RELEASE_TO_LOAD) {
                    changeState(LOADING);
                    // 加载操作
                    if (mListener != null) {
                        mListener.onLoadMore(this);
                    }
                }
                hide();
            default:
                break;
        }
        // 事件分发交给父类
        super.dispatchTouchEvent(ev);
        return true;
    }

    /**
     * 判断自己的内容是否可以上拉
     *
     * @return
     */

    protected boolean canChildScrollUp() {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (pullableView instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) pullableView;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return ViewCompat.canScrollVertically(pullableView, -1) || pullableView.getScrollY() > 0;
            }
        } else {
            /**
             * 用来判断view在竖直方向上能不能向上或者向下滑动
             * direction 方向    负数代表向上滑动 ，正数则反之
             */
            return ViewCompat.canScrollVertically(pullableView, -1);
        }
    }


    /**
     * 判断自己的内容是否可以下拉
     *
     * @return
     */
    protected boolean canChildScrollDown() {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (pullableView instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) pullableView;

                return absListView.getChildCount() > 0
                        && (absListView.getLastVisiblePosition() < absListView.getChildCount() - 1
                        || absListView.getChildAt(absListView.getChildCount() - 1).getBottom() > absListView.getPaddingBottom());
            } else {
                return ViewCompat.canScrollVertically(pullableView, 1) || pullableView.getScrollY() < 0;
            }
        } else {
            return ViewCompat.canScrollVertically(pullableView, 1);
        }
    }

    /**
     * 刷新加载回调接口
     */
    public interface OnRefreshListener {
        /**
         * 刷新操作
         */
        void onRefresh(PullToRefreshLayout pullToRefreshLayout);

        /**
         * 加载操作
         */
        void onLoadMore(PullToRefreshLayout pullToRefreshLayout);
    }

}
