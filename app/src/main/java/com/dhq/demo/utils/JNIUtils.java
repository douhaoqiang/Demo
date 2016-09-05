package com.dhq.demo.utils;

/**
 * DESC
 * Created by douhaoqiang on 2016/9/2.
 */

public class JNIUtils {
    /**
     * get app sign
     * @param o
     * @return
     */
    public static native String getSignature(Object o);

    /**
     * get app Packname
     * @param o
     * @return
     */
    public static native String getPackname(Object o);

    /**
     * load so or jni Library
     */
    static {
        System.loadLibrary("apputil");
    }
}