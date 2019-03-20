package com.dhq.demo.home.contract;

import com.dhq.baselibrary.presenter.BasePresenter;

/**
 * DESC
 * Created by douhaoqiang on 2016/11/10.
 */

public interface HomeContract {

    public interface IHomeView{

    }
    public abstract class IHomePresenter extends BasePresenter<IHomeView>{

        public IHomePresenter(IHomeView view) {
            super(view);
        }
    }
    public interface IHomeModel{

    }

}
