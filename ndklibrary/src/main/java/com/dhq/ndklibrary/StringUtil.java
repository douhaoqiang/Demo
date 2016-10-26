package com.dhq.ndklibrary;

/**
 * DESC
 * Created by douhaoqiang on 2016/10/26.
 */
public class StringUtil {

    static {
        System.loadLibrary("data_util");
    }

    /**
     * spilt the str
     * @param str
     * @return
     */
    public native String spiltStr(String str);

}
