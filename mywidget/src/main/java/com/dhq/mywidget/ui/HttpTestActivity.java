package com.dhq.mywidget.ui;

import android.util.Log;
import android.view.View;

import com.dhq.baselibrary.activity.BaseActivity;
import com.dhq.mywidget.R;
import com.dhq.net.BaseObserver;
import com.dhq.net.DownLoadObserver;
import com.dhq.net.http.HttpUtil;

import java.io.File;
import java.util.HashMap;

/**
 * DESC
 * Created by douhaoqiang on 2017/7/24.
 */

public class HttpTestActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_http_test;
    }

    @Override
    protected void initialize() {
        findViewById(R.id.tv_form_request).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                formRequest();
            }
        });

        findViewById(R.id.tv_json_request).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                jsonRequest();
                uploadFile();
            }
        });

    }


    private void formRequest() {

        String url = "http://192.168.12.38/hecsp/elder/queryFoodInfo";
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("page", "1");
        hashMap.put("pagesize", "10");

        BaseObserver loginObserver = new BaseObserver<Object>("foodInfo") {
            @Override
            public void success(Object result) {
                Log.d("info", result.toString());
            }

            @Override
            public void fail(String msg) {
                Log.e("错误信息", msg);
            }

        };


        //表单形式传参
        HttpUtil.getInstance().postFormReq(url, hashMap, loginObserver);
    }

    private void jsonRequest() {

        String url = "http://192.168.12.38/hecsp/elder/queryFoodInfo";
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("page", "1");
        hashMap.put("pagesize", "10");
        BaseObserver loginObserver = new BaseObserver<Object>() {
            @Override
            public void success(Object result) {
                Log.d("info", result.toString());
            }

            @Override
            public void fail(String msg) {
                Log.e("错误信息", msg);
            }

        };

//        json传参
        HttpUtil.getInstance().postFormReq(url, hashMap, loginObserver);

    }


    /**
     * 上传文件
     */
    private void uploadFile(){

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("page", "1");
        hashMap.put("pagesize", "10");
        BaseObserver loginObserver = new BaseObserver<Object>() {
            @Override
            public void success(Object result) {
                Log.d("info", result.toString());
            }

            @Override
            public void fail(String msg) {
                Log.e("错误信息", msg);
            }

        };
        HttpUtil.getInstance().uploadFileReq("http://192.168.12.38/hecsp/elder/queryFoodInfo",null,loginObserver );
    }


    /**
     * 文件下载
     */
    private void downloadFile(){
        HttpUtil.getInstance().downLoadFileReq("", new HashMap<String, Object>(), new DownLoadObserver() {
            @Override
            public void onDownLoadSuccess(File file) {

            }

            @Override
            public void onDownLoadFail(String errorMsg) {

            }

            @Override
            public void onDownLoadProgress(int progress, long total) {

            }
        });
    }


}
