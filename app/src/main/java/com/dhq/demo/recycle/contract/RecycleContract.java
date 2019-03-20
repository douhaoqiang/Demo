package com.dhq.demo.recycle.contract;

import com.dhq.base.presenter.BasePresenter;
import com.dhq.demo.recycle.bean.MyMessage;

import java.util.ArrayList;

/**
 * DESC
 * Created by douhaoqiang on 2016/11/10.
 */

public interface RecycleContract {

    public interface IRecycleView{
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

    public abstract class IRecyclePresenter extends BasePresenter<IRecycleView> {
        public IRecyclePresenter(IRecycleView view) {
            super(view);
        }

        public abstract void getListData();
    }

    public interface IRecycleModel{

    }
}
