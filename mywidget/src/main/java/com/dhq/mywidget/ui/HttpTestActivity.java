package com.dhq.mywidget.ui;

import android.util.Log;
import android.view.View;

import com.dhq.baselibrary.activity.BaseActivity;
import com.dhq.mywidget.R;
import com.dhq.mywidget.base.UserDetailPostParams;
import com.dhq.net.BaseObserver;
import com.dhq.net.entity.LoginEntity;
import com.dhq.net.entity.UserInfo;
import com.dhq.net.http.HttpUtil;

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
                jsonRequest();
            }
        });

    }


    private void formRequest() {

        String url = "http://192.168.12.38/hecsp/elder/queryFoodInfo";
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("page","1");
        hashMap.put("pagesize","10");

        BaseObserver loginObserver = new BaseObserver<UserInfo>("foodInfo") {
            @Override
            public void success(UserInfo result) {
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
        hashMap.put("page","1");
        hashMap.put("pagesize","10");
        BaseObserver loginObserver = new BaseObserver<LoginEntity>() {
            @Override
            public void success(LoginEntity result) {
                Log.d("info", result.userid);
            }

            @Override
            public void fail(String msg) {
                Log.e("错误信息", msg);
            }

        };

//        json传参
        HttpUtil.getInstance().postFormReq(url, hashMap, loginObserver);


        String BASE_URL = "http://112.124.3.197:8011/app/method/app_bound.php";
        UserDetailPostParams params = new UserDetailPostParams();
        //json传参
        HttpUtil.getInstance().postFormReq(BASE_URL, params, loginObserver);
    }


}
