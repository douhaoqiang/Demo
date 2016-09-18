package com.dhq.demo;


import android.util.Log;

import junit.framework.Assert;

import static junit.framework.Assert.assertEquals;


/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    private static final String TAG = "ExampleUnitTest";


    public void test_isCorrect() throws Exception {
        int i = 0;
        i = 4+4;
        System.out.print(".............. "+i);
        Log.i("TAG","..................."+i);
        // 比较 i 是否 等于 8 ，相等的话通过测试！！！
        Assert.assertEquals(8, i);
    }
}