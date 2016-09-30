package com.dhq.demo.refresh;

/**
 * DESC
 * Created by douhaoqiang on 2016/9/23.
 */

public interface IPullFooterView {


    /**
     * 上拉程度
     * @param precent 下拉的百分比（0-1）
     */
    void pullUpPrecent(float precent);

    /**
     * 准备加载更多
     */
    void prepareLoadmore();

    /**
     * 开始加载更多
     */
    void startLoadMore();

    /**
     * 完成加载更多
     */
    void completeLoadmore();


}
