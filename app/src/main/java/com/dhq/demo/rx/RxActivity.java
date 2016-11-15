package com.dhq.demo.rx;

import android.util.Log;

import com.dhq.baselibrary.activity.BaseActivity;
import com.dhq.demo.R;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2016/11/15.
 */

public class RxActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_rx_lay;
    }

    @Override
    protected void initialize() {
        init();
    }

    private void init() {

    }

    private void createObserver(){

        //观察者
        Subscriber<String> subscriber=new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.e("info","试试:"+s);
            }
        };

        //被观察者
        String[] arry={"qqqqqqq","wwwwww"};
        Observable observable=Observable.from(arry);

        //事件订阅
        observable.subscribe(subscriber);

    }

}
