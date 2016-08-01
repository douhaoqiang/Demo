package com.dhq.demo;

import com.dhq.demo.base.BaseView;

/**
 * Created by Administrator on 2016/8/1.
 */
public interface MainView extends BaseView {


    /**
     * 正在加载数据(显示加载框)
     */
    void isLoading();

    /**
     * 加载数据完成（取消加载框）
     */
    void isCompleted();

}
