package com.dhq.demo.jock.ui;

import android.os.Bundle;

import com.dhq.base.activity.BaseActivity;
import com.dhq.demo.jock.contract.JockContract;
import com.dhq.demo.jock.presenter.JockPresenterImpl;

/**
 * DESC
 * Created by douhaoqiang on 2016/10/18.
 */
public class JockActivity extends BaseActivity implements JockContract.IJockView {
    private static final String TAG = "JockActivity";


    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initializes(Bundle savedInstanceState) {
        new JockPresenterImpl(this);

    }



    @Override
    public void showDialog() {

    }
}
