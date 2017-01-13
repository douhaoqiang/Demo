package com.dhq.demo.utils;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * DESC
 * Created by douhaoqiang on 2016/11/24.
 */
public class StringUtil {
    private static final String TAG = "StringUtil";

    public static void main(String[] args){
        isRight("123456dddd");
    }
    /**
     * 必须包含数字、中文、字母
     *
     * @param str
     * @return
     */
    private static boolean isRight(String str) {
        Pattern p = Pattern.compile("[0-9]*");
        Matcher m = p.matcher(str);
        if (m.matches()) {
//            Log.d(TAG, "输入是数字！");
            System.out.println("输入是数字！");
        }
        p = Pattern.compile("[a-zA-Z]");
        m = p.matcher(str);
        if (m.matches()) {
//            Log.d(TAG, "输入是字母！");
            System.out.println("输入是字母！");
        }
        p = Pattern.compile("[\u4e00-\u9fa5]");
        m = p.matcher(str);
        if (m.matches()) {
//            Log.d(TAG, "输入是汉字！");
            System.out.println("输入是汉字！");
        }

        return true;
    }


}
