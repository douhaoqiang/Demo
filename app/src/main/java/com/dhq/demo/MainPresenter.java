package com.dhq.demo;

import android.content.Context;

import com.dhq.demo.base.BasePresenter;

/**
 * Created by Administrator on 2016/8/1.
 */
public class MainPresenter extends BasePresenter<MainView> {

    private Context mContext;

    public MainPresenter(Context context){
        this.mContext=context.getApplicationContext();
    }



}
