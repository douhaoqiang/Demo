package com.dhq.demo.utils;

/**
 * DESC
 * Created by douhaoqiang on 2016/9/2.
 */

public class JNIUtils {
    /**
     * 获取应用的签名
     * @param o
     * @return
     */
    public static native String getSignature(Object o);

    /**
     * 获取应用的包名
     * @param o
     * @return
     */
    public static native String getPackname(Object o);

    /**
     * 加载so库或jni库
     */
    static {
        System.loadLibrary("JNI_ANDROID");
    }
}