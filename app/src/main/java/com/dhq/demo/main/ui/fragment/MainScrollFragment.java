package com.dhq.demo.main.ui.fragment;

import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ScrollView;

import com.dhq.baselibrary.fragment.BaseMvpFragment;
import com.dhq.demo.R;

/**
 * DESC
 * Created by douhaoqiang on 2016/11/23.
 */
public class MainScrollFragment extends BaseMvpFragment {

    private static final String TAG = "MainScrollFragment";
    private ScrollView pullableView;

    @Override
    protected int getLayoutId() {
        return R.layout.main_scroll_lay;
    }

    @Override
    protected void initialize() {
        pullableView = (ScrollView) getView().findViewById(R.id.main_scroll_fragment_sv);
        pullableView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Log.e(TAG, "上拉：" + canChildScrollUp(v));
                Log.e(TAG, "下拉：" + canChildScrollDown(v));

                return false;
            }
        });
    }


    /**
     * 判断自己的内容是否可以上拉
     *
     * @return
     */

    protected boolean canChildScrollUp(View pullableView) {
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
    protected boolean canChildScrollDown(View pullableView) {
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
}
