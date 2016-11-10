package com.dhq.demo.main.contract;

import com.dhq.baselibrary.presenter.BasePresenter;

/**
 * DESC
 * Created by douhaoqiang on 2016/11/10.
 */
public interface MainContract {

    public interface IMainView {
        /**
         * 正在加载数据(显示加载框)
         */
        void isLoading();

        /**
         * 加载数据完成（取消加载框）
         */
        void isCompleted();
    }

    public abstract class IMainPresenter extends BasePresenter<IMainView> {

    }

    public interface IMainModel {


    }

}
