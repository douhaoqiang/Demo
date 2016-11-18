package com.dhq.demo.rx;

import android.util.Log;

import com.dhq.baselibrary.activity.BaseActivity;
import com.dhq.demo.R;

import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

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

    private void createObserver() {

        //观察者
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.e("info", "试试:" + s);
            }
        };

        //被观察者
        String[] arry = {"qqqqqqq", "wwwwww"};
        Observable observable = Observable.from(arry);

        //事件订阅
        observable.subscribe(subscriber);

    }


    //过滤操作
    private void rxFilter() {
        //结果（4,7,5）
        Observable.just(4, 2, 1, 7, 5)
                .filter(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer > 3;
                    }
                }).subscribeOn(Schedulers.io())//事件处理在io线程
                .observeOn(AndroidSchedulers.mainThread())//事件回调在主线程
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        Log.i("wxl", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.i("wxl", "onNext=" + integer);
                    }
                });
    }


    private <T> void dddd(){
        new Observer<T>() {
            @Override
            public void onCompleted() {
                Log.i("wxl", "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(T integer) {
                Log.i("wxl", "onNext=" + integer);
            }
        };
    }


}
