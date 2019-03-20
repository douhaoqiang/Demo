package com.dhq.demo.home.Presenter;

import android.content.Context;

import com.dhq.demo.home.contract.HomeContract;
import com.dhq.demo.home.model.HomeMode;

/**
 * Created by Administrator on 2016/8/18.
 */
public class HomePresenter extends HomeContract.IHomePresenter {
    private Context mContext;
    private HomeMode mHomeMode = new HomeMode();


    public HomePresenter(HomeContract.IHomeView view) {
        super(view);
    }
}
