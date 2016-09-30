package com.dhq.demo.refresh;

/**
 * DESC
 * Created by douhaoqiang on 2016/9/23.
 */

public interface IPullHeaderView {

    /**
     * 下拉程度
     * @param precent 上拉的百分比（0-1）
     */
    void pullDownPrecent(float precent);

    /**
     * 准备刷新
     */
    void prepareRefresh();

    /**
     * 开始刷新
     */
    void startRefresh();

    /**
     * 完成刷新
     */
    void completeRefresh();
}
