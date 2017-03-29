package com.dhq.mywidget;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dhq.net.BaseObserver;
import com.dhq.net.entity.BaseResponse;
import com.dhq.net.entity.LoginEntity;
import com.dhq.net.http.HttpUtil;
import com.dhq.net.util.DataUtils;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String url = "http://www.hezhongyimeng.com/nmip/bjgoodwill/loginApp_loginAPP_login.action";
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("useraccount", "15555555555");
        hashMap.put("password", DataUtils.MD5("a111111"));
        hashMap.put("menutypecode", "phone");

        //方式1

        BaseObserver<BaseResponse> loginEntityBaseObserver = new BaseObserver<>(new BaseObserver.ResponseCallback<LoginEntity>() {
            @Override
            public void success(LoginEntity result) {
                Log.d("info", result.userid);
            }

            @Override
            public void fail(String msg) {

            }
        });

        HttpUtil.getInstance().postFormHttpRequest(url, hashMap, loginEntityBaseObserver);


    }
}
