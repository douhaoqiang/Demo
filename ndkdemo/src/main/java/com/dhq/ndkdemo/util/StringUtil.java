package com.dhq.ndkdemo.util;

/**
 * DESC
 * Created by douhaoqiang on 2016/10/27.
 */
public class StringUtil {
    private static final String TAG = "StringUtil";

    public static native String append(String str);


    static {
        System.loadLibrary("data");
    }

}
