package com.bjgoodwill.paylibrary;

/**
 * DESC
 * Created by douhaoqiang on 2017/4/5.
 */

public interface PayCallBack {

    void success(String trade_no);

    void failure();

}
