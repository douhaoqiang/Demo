package com.dhq.net;

import android.util.Log;

import com.dhq.net.entity.BaseResponse;
import com.dhq.net.entity.LoginEntity;
import com.dhq.net.entity.User;
import com.dhq.net.http.HttpUtil;
import com.dhq.net.util.DataUtils;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * DESC  接口样例
 * Created by douhaoqiang on 2016/11/9.
 */
public class dd {
    private static final String TAG = "dd";


    public static void main(String args[]) {
        ddd();
    }

    private static void ddd() {
//        Call<BaseResponse> baseResponseCall = OlderServiceGen.getInstance().getService().addUser(new BaseResponse());
//        baseResponseCall.enqueue(new Callback<BaseResponse>() {
//            @Override
//            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
//                BaseResponse body = response.body();
//            }
//
//            @Override
//            public void onFailure(Call<BaseResponse> call, Throwable t) {
//
//            }
//        });

        Observer observer = new BaseObserver<User>() {
            @Override
            public void success(User result) {

            }

            @Override
            public void fail(String msg) {

            }
        };


//        OlderServiceGen.getInstance().getService().getUsers()
//                .observeOn(Schedulers.io())
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .subscribe(observer);
//        OlderServiceGen.getInstance().getService().get("http://www.baidu.com",new HashMap<String, String>())
//                .observeOn(Schedulers.io())
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .subscribe(observer);

        String url="http://www.hezhongyimeng.com/nmip/bjgoodwill/loginApp_loginAPP_login.action";
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("useraccount", "15555555555");
        hashMap.put("password", DataUtils.MD5("a111111"));
        hashMap.put("menutypecode", "phone");
        HttpUtil.getInstance().postJsonHttpRequest(url, hashMap, new BaseObserver<LoginEntity>() {
            @Override
            public void success(LoginEntity result) {
//                Log.d(TAG,"登录成功："+result.userid);
                System.out.println("登录成功："+result.userid);
            }

            @Override
            public void fail(String msg) {
//                Log.d(TAG,"登录失败："+msg);
                System.out.println("登录失败："+msg);
            }
        });


    }


}
