package com.dhq.net.util;

import com.google.gson.Gson;

import java.security.MessageDigest;
import java.util.Map;

/**
 * DESC
 * Created by douhaoqiang on 2017/3/28.
 */

public class DataUtils {


    /**
     * 将Map转化为Json
     * @param   map
     * @return  String
     */
    public static <T> String mapToJson(Map<String, T> map) {
        return gsonMapToJson(map);
    }

    /**
     * 利用Gson将Map转化为Json
     * @param   map
     * @return  String
     */
    private static String gsonMapToJson(Map map){
        Gson gson = new Gson();
        String jsonStr = gson.toJson(map);
        return jsonStr;
    }


    /**
     * 对数据MD5加密
     * @param inStr
     * @return
     */
    public static String MD5(String inStr) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception var8) {
            var8.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int md5Bytes = 0; md5Bytes < charArray.length; ++md5Bytes) {
            byteArray[md5Bytes] = (byte) charArray[md5Bytes];
        }
        byte[] var9 = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < var9.length; ++i) {
            int val = var9[i] & 255;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

}
