package com.dhq.demo.jock.contract;

import com.dhq.base.presenter.BasePresenter;

/**
 * DESC
 * Created by douhaoqiang on 2016/10/24.
 */

public interface JockContract {


    public interface IJockView {
        void showDialog();
    }

    public abstract class Presenter extends BasePresenter<IJockView>{

        public Presenter(IJockView view) {
            super(view);
        }
    }

    public interface IJockModel {


    }


}