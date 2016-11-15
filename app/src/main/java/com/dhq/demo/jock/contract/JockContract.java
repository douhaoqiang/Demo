package com.dhq.demo.jock.contract;

import com.dhq.baselibrary.presenter.BasePresenter;

/**
 * DESC
 * Created by douhaoqiang on 2016/10/24.
 */

public interface JockContract {


    public interface IJockView {
        void showDialog();
    }

    public abstract class Presenter extends BasePresenter<IJockView>{

    }

    public interface IJockModel {


    }


}