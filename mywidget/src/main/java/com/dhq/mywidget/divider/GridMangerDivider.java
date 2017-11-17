package com.dhq.mywidget.divider;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * DESC RecyclerViewDivider工具类
 * Created by douhaoqiang on 2017/11/17.
 */

public class GridMangerDivider extends DividerManger {

    private final Rect mBounds = new Rect();
    private final Drawable mDivider;
    private final int mStrokeWidth;
    private final int mLeftMargin;
    private final int mRightMargin;
    private final int mTopMargin;
    private final int mBottomMargin;
    private final boolean mHideRoundDivider;//是否显示四周分割线
    private int mSpanCount = 1;

    public GridMangerDivider(Builder builder) {
        this.mDivider = builder.getDrawable();
        this.mStrokeWidth = builder.getStrokeWidth();
        this.mLeftMargin = builder.getLeftMargin();
        this.mRightMargin = builder.getRightMargin();
        this.mTopMargin = builder.getTopMargin();
        this.mBottomMargin = builder.getBottomMargin();
        this.mHideRoundDivider = builder.isHideLastDivider();
    }

    @Override
    public void drawDivider(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (parent.getLayoutManager() == null || mDivider == null) {
            return;
        }
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();

        //判断是否是LinearLayoutManager
        if (layoutManager instanceof GridLayoutManager) {
            //获取列数
            mSpanCount = ((GridLayoutManager) layoutManager).getSpanCount();
            drawHorizontal(c, parent);
            drawVertical(c, parent);
        }
    }


    @Override
    public void calculateItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
        mSpanCount = layoutManager.getSpanCount();
        final int position = getAdapterPosition(view);
        if (mDivider == null && null == getDivider()) {
            outRect.set(0, 0, 0, 0);
            return;
        }

        if (position % mSpanCount == 0) {
            //第一列数据
            if (position < mSpanCount) {
                outRect.set(0, 0, getIntrinsicWidth(position), 0);
            } else {
                outRect.set(0, getIntrinsicWidth(position), getIntrinsicWidth(position), 0);
            }
        } else if (position % mSpanCount == mSpanCount - 1) {
            //最后一列数据
            if (position < mSpanCount) {
                outRect.set(0, 0, 0, 0);
            } else {
                outRect.set(0, getIntrinsicWidth(position), 0, 0);
            }
        } else {
            //其他数据
            if (position < mSpanCount) {
                outRect.set(0, 0, getIntrinsicWidth(position), 0);
            } else {
                outRect.set(0, getIntrinsicWidth(position), getIntrinsicWidth(position), 0);
            }
        }
    }


    private void drawVertical(Canvas canvas, RecyclerView parent) {

//        canvas.save();
//        int left;
//        int right;
//        GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
//        int spanCount = layoutManager.getSpanCount();
//        final int childCount = parent.getChildCount();
//        for (int i = 0; i < childCount; i += spanCount) {
//            final View child = parent.getChildAt(i);
//            final int position = getAdapterPosition(child);
//
//            final int leftMargin = getLeftMargin();
//            final int rightMargin = getRightMargin();
//
//            left = parent.getPaddingLeft() + leftMargin;
//            right = parent.getWidth() - parent.getPaddingRight() - rightMargin;
//
//            final int top = child.getBottom();
//            final int bottom = top + getIntrinsicHeight(position);
//
//            Drawable drawable = getDivider();
//            drawable.setBounds(left, top, right, bottom);
//            drawable.draw(canvas);
//        }
//        canvas.restore();
    }

    private void drawHorizontal(Canvas canvas, RecyclerView parent) {

//        canvas.save();
//        int top;
//        int bottom;
//
//        final int childCount = mHideLastDivider ? parent.getChildCount() - 1 : parent.getChildCount();
//        for (int i = 0; i < childCount; i++) {
//            final View child = parent.getChildAt(i);
//            final int position = getAdapterPosition(child);
//
//            //noinspection AndroidLintNewApi - NewApi lint fails to handle overrides.
//            final int topMargin = getTopMargin();
//            final int bottomMargin = getBottomMargin();
//
//            top = parent.getPaddingTop();
//            bottom = parent.getHeight() - parent.getPaddingBottom() - bottomMargin;
//            canvas.clipRect(parent.getPaddingLeft(), top, parent.getWidth() - parent.getPaddingRight(),
//                    bottom);
//            top = top + topMargin;
//
//            parent.getLayoutManager().getDecoratedBoundsWithMargins(child, mBounds);
//            final int right = mBounds.right + Math.round(child.getTranslationX());
//            final int left = right - getIntrinsicWidth(position);
//            Drawable drawable = getDivider();
//            drawable.setBounds(left, top, right, bottom);
//            drawable.draw(canvas);
//        }
//        canvas.restore();
    }


    private int getAdapterPosition(View view) {
        return ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewAdapterPosition();
    }

    private Drawable getDivider() {
        return mDivider;

    }

    private int getIntrinsicHeight(int position) {

        if (mDivider instanceof ColorDrawable) {
            return mStrokeWidth;
        }
        return mDivider.getIntrinsicHeight();
    }

    private int getIntrinsicWidth(int position) {

        if (mDivider instanceof ColorDrawable) {
            return mStrokeWidth;
        }
        return mDivider.getIntrinsicWidth();
    }

    private int getLeftMargin() {
        return mLeftMargin;
    }

    private int getRightMargin() {
        return mRightMargin;
    }

    private int getTopMargin() {
        return mTopMargin;
    }

    private int getBottomMargin() {
        return mBottomMargin;
    }


}