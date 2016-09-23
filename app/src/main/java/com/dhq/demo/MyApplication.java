package com.dhq.demo;

import android.app.Application;

/**
 * DESC
 * Created by douhaoqiang on 2016/9/19.
 */

public class MyApplication extends Application {

    private static Application application;

    @Override
    public void onCreate() {
        super.onCreate();
        application=this;
    }

    public static Application getIntance(){
        return application;
    }

}
