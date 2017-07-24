package com.dhq.mywidget.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dhq.mywidget.R;
import com.dhq.mywidget.base.UserDetailPostParams;
import com.dhq.net.BaseObserver;
import com.dhq.net.entity.BaseResponse;
import com.dhq.net.entity.LoginEntity;
import com.dhq.net.http.HttpUtil;
import com.dhq.net.util.DataUtils;

import java.util.HashMap;

/**
 * DESC
 * Created by douhaoqiang on 2017/7/24.
 */

public class HttpTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_http_test);

        String url = "http://www.hezhongyimeng.com/nmip/bjgoodwill/loginApp_loginAPP_login.action";
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("useraccount", "15555555555");
        hashMap.put("password", DataUtils.MD5("a111111"));
        hashMap.put("menutypecode", "phone");

        //方式1

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

        String BASE_URL = "http://112.124.3.197:8011/app/method/app_bound.php";

        HttpUtil.getInstance().postFormHttpRequest(url, hashMap, loginObserver);

        HttpUtil.getInstance().postJsonHttpRequest(url, hashMap, loginObserver);


        UserDetailPostParams params = new UserDetailPostParams();

        HttpUtil.getInstance().postJsonHttpRequest(BASE_URL, params, loginObserver);

    }
}
