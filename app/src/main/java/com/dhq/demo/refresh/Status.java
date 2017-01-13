package com.dhq.demo.refresh;

/**
 * DESC
 * Created by douhaoqiang on 2016/11/23.
 */
public class Status {
    private static final String TAG = "Status";

    // 初始状态
    public static final int INIT = 0;
    // 表示正在下拉
    public static final int RELEASE_PULL_TO_REFRESH = -1;
    // 释放刷新
    public static final int RELEASE_TO_REFRESH = 1;
    // 正在刷新
    public static final int REFRESHING = 2;

    // 表示正在上拉
    public static final int RELEASE_PULL_TO_LOAD = -3;
    // 释放加载
    public static final int RELEASE_TO_LOAD = 3;
    // 正在加载
    public static final int LOADING = 4;
    // 操作完毕
    public static final int DONE = 5;

}
