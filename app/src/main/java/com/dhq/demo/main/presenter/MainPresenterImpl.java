package com.dhq.demo.main.presenter;

import android.content.Context;

import com.dhq.demo.main.contract.MainContract;

/**
 * Created by Administrator on 2016/8/1.
 */
public class MainPresenterImpl extends MainContract.IMainPresenter {

    private Context mContext;

    public MainPresenterImpl(MainContract.IMainView view) {
        super(view);
    }


    public void getData(){

    }


}
