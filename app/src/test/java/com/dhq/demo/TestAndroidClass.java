package com.dhq.demo;

import android.test.InstrumentationTestCase;

import com.dhq.demo.main.ui.MainActivity;

/**
 * DESC
 * Created by douhaoqiang on 2016/9/14.
 */

public class TestAndroidClass extends InstrumentationTestCase {


    private static final String TAG = "TestAndroidClass";

    public void testRes() throws Exception{
        launchActivity("com.dhq.demo",MainActivity.class,null);
    }
}
