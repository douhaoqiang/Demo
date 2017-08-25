package com.dhq.cardswipelibrary;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;


public class FlingCardListener implements View.OnTouchListener {

    private final float objectX;
    private final float objectY;
    private final int objectH;
    private final int objectW;
    private final int parentWidth;
    private final FlingListener mFlingListener;
    private final Object dataObject;
    private final float halfWidth;

    private float BASE_ROTATION_DEGREES;

    private float aPosX;
    private float aPosY;
    private float aDownTouchX;
    private float aDownTouchY;
    private static final int INVALID_POINTER_ID = -1;

    // The active pointer is the one currently moving our object.
    private int mActivePointerId = INVALID_POINTER_ID;
    private View frame = null;

    private final int TOUCH_ABOVE = 0;
    private final int TOUCH_BELOW = 1;
    private int touchPosition;
    // private final Object obj = new Object();
    private boolean isAnimationRunning = false;
    private float MAX_COS = (float) Math.cos(Math.toRadians(45));
    // 支持左右滑
    private boolean isNeedSwipe = true;

    private float aTouchUpX;

    private int animDuration = 300;
    private float scale;

    /**
     * every time we touch down,we should stop the {@link #animRun}
     */
    private boolean resetAnimCanceled = false;

    private final int mMinTouchSlop; //最小的滑动距离  （小于此值将不触发滑动事件）

    public FlingCardListener(View frame, Object itemAtPosition, float rotation_degrees, FlingListener flingListener) {
        super();
        this.frame = frame;
        this.objectX = frame.getX();
        this.objectY = frame.getY();
        this.objectW = frame.getWidth();
        this.objectH = frame.getHeight();
        this.halfWidth = objectW / 2f;
        this.dataObject = itemAtPosition;
        this.parentWidth = ((ViewGroup) frame.getParent()).getWidth();
        this.BASE_ROTATION_DEGREES = rotation_degrees;
        this.mFlingListener = flingListener;
        mMinTouchSlop = ViewConfiguration.get(frame.getContext()).getScaledTouchSlop();
    }

    public void setIsNeedSwipe(boolean isNeedSwipe) {
        this.isNeedSwipe = isNeedSwipe;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {

        if (!isNeedSwipe) {
            return false;
        }

        try {

            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:

                    stopCancleAnimator();

                    // 获取手指落下时的坐标
                    mActivePointerId = event.getPointerId(0);
                    aDownTouchX = event.getX(mActivePointerId);
                    aDownTouchY = event.getY(mActivePointerId);

                    // 控件所在位置坐标
                    aPosX = frame.getX();
                    aPosY = frame.getY();

                    if (aDownTouchY < objectH / 2) {
                        touchPosition = TOUCH_ABOVE;
                    } else {
                        touchPosition = TOUCH_BELOW;
                    }
                    break;

                case MotionEvent.ACTION_MOVE:
                    float y = event.getY();
                    float currentX = event.getX(mActivePointerId);
                    float currentY = event.getY(mActivePointerId);

                    aPosX = aPosX + currentX - aDownTouchX;
                    aPosY = aPosY + currentY - aDownTouchY;

                    // calculate the rotation degrees of frame
                    float distObjectX = aPosX - objectX;
                    float rotation = BASE_ROTATION_DEGREES * 2f * distObjectX / parentWidth;
                    if (touchPosition == TOUCH_BELOW) {
                        rotation = -rotation;
                    }

                    // in this area would be code for doing something with the view as the frame moves.
                    frame.setX(aPosX);
                    frame.setY(aPosY);
                    frame.setRotation(rotation);

                    mFlingListener.onScroll(getScrollProgress(), getScrollXProgressPercent());
                    break;

                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:

                    int pointerCount = event.getPointerCount();
                    int activePointerId = Math.min(mActivePointerId, pointerCount - 1);
                    aTouchUpX = event.getX(activePointerId);
                    mActivePointerId = INVALID_POINTER_ID;
                    resetCardViewOnStack(event);
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }


    private float getScrollProgress() {
        float dx = aPosX - objectX;
        float dy = aPosY - objectY;
        float dis = Math.abs(dx) + Math.abs(dy);
        return Math.min(dis, 400f) / 400f;
    }

    private float getScrollXProgressPercent() {
        if (movedBeyondLeftBorder()) {
            return -1f;
        } else if (movedBeyondRightBorder()) {
            return 1f;
        } else {
            float zeroToOneValue = (aPosX + halfWidth - leftBorder()) / (rightBorder() - leftBorder());
            return zeroToOneValue * 2f - 1f;
        }
    }

    private boolean resetCardViewOnStack(MotionEvent event) {
        if (isNeedSwipe) {
            final int duration = 200;
            if (movedBeyondLeftBorder()) {
                // 左滑移除
                leftSwipeRemove(duration);
                mFlingListener.onScroll(1f, -1.0f);
            } else if (movedBeyondRightBorder()) {
                // 右滑移除
                rightSwipeRemove(duration);
                mFlingListener.onScroll(1f, 1.0f);
            } else {
                float absMoveXDistance = Math.abs(aPosX - objectX);
                float absMoveYDistance = Math.abs(aPosY - objectY);
                if (absMoveXDistance < 4 && absMoveYDistance < 4) {
                    mFlingListener.onClick(event, frame, dataObject);
                } else {
                    frame.animate()
                            .setDuration(animDuration)
                            .setInterpolator(new OvershootInterpolator(1.5f))
                            .x(objectX)
                            .y(objectY)
                            .rotation(0)
                            .start();
                    scale = getScrollProgress();
                    this.frame.postDelayed(animRun, 0);
                    resetAnimCanceled = false;
                }
                aPosX = 0;
                aPosY = 0;
                aDownTouchX = 0;
                aDownTouchY = 0;
            }
        } else {
            float distanceX = Math.abs(aTouchUpX - aDownTouchX);
            if (distanceX < 4)
                mFlingListener.onClick(event, frame, dataObject);
        }
        return false;
    }

    private Runnable animRun = new Runnable() {
        @Override
        public void run() {
            mFlingListener.onScroll(scale, 0);
            if (scale > 0 && !resetAnimCanceled) {
                scale = scale - 0.1f;
                if (scale < 0)
                    scale = 0;
                frame.postDelayed(this, animDuration / 20);
            }
        }
    };

    /**
     * 判断移动距离是否超过左边距
     *
     * @return
     */
    private boolean movedBeyondLeftBorder() {
        return aPosX + halfWidth < leftBorder();
    }

    /**
     * 判断移动距离是否超过右边距
     *
     * @return
     */
    private boolean movedBeyondRightBorder() {
        return aPosX + halfWidth > rightBorder();
    }


    /**
     * 是否左滑移除的距离临界位置（默认时控件的1/4位置）
     *
     * @return
     */
    public float leftBorder() {
        return parentWidth / 4f;
    }

    /**
     * 是否左滑移除的距离临界位置（默认时控件的3/4位置）
     *
     * @return
     */
    public float rightBorder() {
        return 3 * parentWidth / 4f;
    }


    /**
     * 左滑移除view
     *
     * @param duration 动画时长
     */
    public void leftSwipeRemove(long duration) {
        isAnimationRunning = true;
        float exitX = -objectW - getRotationWidthOffset();
        float exitY = getExitPoint(-objectW);
        this.frame.animate()
                .setDuration(duration)
                .setInterpolator(new LinearInterpolator())
                .translationX(exitX)
                .translationY(exitY)
                //.rotation(isLeft ? -BASE_ROTATION_DEGREES:BASE_ROTATION_DEGREES)
                .setListener(new AnimatorListenerAdapter() {

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mFlingListener.onCardExited();
                        mFlingListener.leftExit(dataObject);
                        isAnimationRunning = false;
                        stopCancleAnimator();
                    }
                }).start();
    }

    /**
     * 右滑移除view
     *
     * @param duration 动画时长
     */
    public void rightSwipeRemove(long duration) {
        isAnimationRunning = true;
        float exitX = parentWidth + getRotationWidthOffset();
        float exitY = getExitPoint(parentWidth);
        this.frame.animate()
                .setDuration(duration)
                .setInterpolator(new LinearInterpolator())
                .translationX(exitX)
                .translationY(exitY)
                //.rotation(isLeft ? -BASE_ROTATION_DEGREES:BASE_ROTATION_DEGREES)
                .setListener(new AnimatorListenerAdapter() {

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mFlingListener.onCardExited();
                        mFlingListener.rightExit(dataObject);
                        isAnimationRunning = false;
                        stopCancleAnimator();
                    }
                }).start();
    }


    /**
     * 停止并移除动画
     */
    private void stopCancleAnimator() {
        // remove the listener because 'onAnimationEnd' will still be called if we cancel the animation.
        if (!resetAnimCanceled) {
            this.frame.animate().setListener(null);
            this.frame.animate().cancel();
            resetAnimCanceled = true;
        }

    }

    /**
     * Starts a default left exit animation.
     */
    public void selectLeft() {
        if (!isAnimationRunning)
            selectLeft(animDuration);
    }

    /**
     * Starts a default left exit animation.
     */
    public void selectLeft(long duration) {
        if (!isAnimationRunning) {
            leftSwipeRemove(duration);
        }
    }

    /**
     * Starts a default right exit animation.
     */
    public void selectRight() {
        if (!isAnimationRunning)
            selectRight(animDuration);
    }

    /**
     * Starts a default right exit animation.
     */
    public void selectRight(long duration) {
        if (!isAnimationRunning) {
            rightSwipeRemove(duration);
        }
    }

    private float getExitPoint(int exitXPoint) {
        float[] x = new float[2];
        x[0] = objectX;
        x[1] = aPosX;

        float[] y = new float[2];
        y[0] = objectY;
        y[1] = aPosY;

        LinearRegression regression = new LinearRegression(x, y);

        //Your typical y = ax+b linear regression
        return (float) regression.slope() * exitXPoint + (float) regression.intercept();
    }

    private float getExitRotation(boolean isLeft) {
        float rotation = BASE_ROTATION_DEGREES * 2f * (parentWidth - objectX) / parentWidth;
        if (touchPosition == TOUCH_BELOW) {
            rotation = -rotation;
        }
        if (isLeft) {
            rotation = -rotation;
        }
        return rotation;
    }

    /**
     * When the object rotates it's width becomes bigger.
     * The maximum width is at 45 degrees.
     * <p>
     * The below method calculates the width offset of the rotation.
     */
    private float getRotationWidthOffset() {
        return objectW / MAX_COS - objectW;
    }


    public void setRotationDegrees(float degrees) {
        this.BASE_ROTATION_DEGREES = degrees;
    }


    protected interface FlingListener<T> {
        void onCardExited();

        void leftExit(T dataObject);

        void rightExit(T dataObject);

        void onClick(MotionEvent event, View v, T dataObject);

        void onScroll(float progress, float scrollXProgress);
    }


}

