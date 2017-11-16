package com.dhq.mywidget.ui;

import android.util.Log;
import android.view.View;

import com.dhq.baselibrary.activity.BaseActivity;
import com.dhq.mywidget.R;
import com.dhq.mywidget.base.UserDetailPostParams;
import com.dhq.net.BaseObserver;
import com.dhq.net.entity.BaseResponse;
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
                formRequest();
            }
        });

    }


    private void formRequest() {

        String url = "http://192.168.12.42/nullStringTest";
        HashMap<String, String> hashMap = new HashMap<>();


        BaseObserver<BaseResponse> loginObserver = new BaseObserver<>(this,new BaseObserver.ResponseCallback<UserInfo>() {
            @Override
            public void success(UserInfo result) {
                Log.d("info", result.toString());
            }

            @Override
            public void fail(String msg) {
                Log.e("错误信息", msg);
            }

            @Override
            public void onComplete() {

            }
        });


        //表单形式传参
        HttpUtil.getInstance().postFormHttpRequest(url, hashMap, loginObserver);
    }

    private void jsonRequest() {

        BaseObserver<BaseResponse> loginObserver = new BaseObserver<>(new BaseObserver.ResponseCallback<LoginEntity>() {
            @Override
            public void success(LoginEntity result) {
                Log.d("info", result.userid);
            }

            @Override
            public void fail(String msg) {
                Log.e("错误信息", msg);
            }

            @Override
            public void onComplete() {

            }
        });

        //json传参
//        HttpUtil.getInstance().postJsonHttpRequest(url, hashMap, loginObserver);


        String BASE_URL = "http://112.124.3.197:8011/app/method/app_bound.php";
        UserDetailPostParams params = new UserDetailPostParams();
        //json传参
        HttpUtil.getInstance().postJsonHttpRequest(BASE_URL, params, loginObserver);
    }


}
