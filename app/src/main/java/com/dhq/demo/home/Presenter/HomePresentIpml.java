package com.dhq.demo.home.Presenter;

import android.content.Context;

import com.dhq.demo.base.BasePresenter;
import com.dhq.demo.home.model.HomeMode;
import com.dhq.demo.home.view.HomeView;

/**
 * Created by Administrator on 2016/8/18.
 */
public class HomePresentIpml extends BasePresenter<HomeView> {
    private Context mContext;
    private HomeMode mHomeMode=new HomeMode();

    public HomePresentIpml(Context context){
        this.mContext=context.getApplicationContext();
    }



}
