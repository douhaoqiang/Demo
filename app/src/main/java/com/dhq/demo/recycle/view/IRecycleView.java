package com.dhq.demo.recycle.view;

import com.dhq.demo.recycle.bean.MyMessage;

import java.util.ArrayList;

/**
 * DESC
 * Created by douhaoqiang on 2016/9/6.
 */

public interface IRecycleView {

    /**
     * 正在加载数据
     */
    void loadingData();

    /**
     * 成功获取消息列表
     * @param myMessages 消息列表
     */
    void getDataSuccess(ArrayList<MyMessage> myMessages);

    /**
     * 获取数据失败
     * @param msg 失败信息
     */
    void getDataFinal(String msg);

}
