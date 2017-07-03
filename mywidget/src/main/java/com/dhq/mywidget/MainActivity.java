package com.dhq.mywidget;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.dhq.mywidget.base.UserDetailPostParams;
import com.dhq.mywidget.circleprograss.CircleProgressView;
import com.dhq.mywidget.selectview.SelectView;
import com.dhq.mywidget.selectview.WheelView;
import com.dhq.net.BaseObserver;
import com.dhq.net.entity.BaseResponse;
import com.dhq.net.entity.LoginEntity;
import com.dhq.net.http.HttpUtil;
import com.dhq.net.util.DataUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SelectView selectView = (SelectView) findViewById(R.id.main_selectview);
        WheelView wheelView = (WheelView) findViewById(R.id.main_wheelview);
        CircleProgressView progressView = (CircleProgressView) findViewById(R.id.main_circleprogressView);

        progressView.setProgress(20);

        selectView.setSelectListener(new SelectView.SelectListener<String>() {
            @Override
            public String setShowValue(String item) {
                return item;
            }

            @Override
            public void onSelectItem(String item) {

            }
        });
        List<String> list_year = new ArrayList<>();
        for (int i = 1988; i <= 2056; i++) {
            list_year.add(i + "");
        }
        selectView.setDatas(list_year);

        wheelView.setSelectListener(new WheelView.SelectListener<String>() {
            @Override
            public String setShowValue(String item) {
                return item;
            }

            @Override
            public void onSelectItem(String item) {
                Log.d("wheelView",item);
            }
        });
        List<String> list_year2 = new ArrayList<>();
        for (int i = 1988; i <= 2056; i++) {
            list_year2.add(i + "");
        }
        wheelView.setDatas(list_year2);



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
