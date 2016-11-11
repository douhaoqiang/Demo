package com.dhq.demo.jock.ui;

import com.dhq.baselibrary.activity.BaseMvpActivity;
import com.dhq.demo.jock.contract.JockContract;
import com.dhq.demo.jock.presenter.JockPresenterImpl;

/**
 * DESC
 * Created by douhaoqiang on 2016/10/18.
 */
public class JockActivity extends BaseMvpActivity<JockContract.Presenter> implements JockContract.IJockView {
    private static final String TAG = "JockActivity";


    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initializes() {

    }

    @Override
    protected JockContract.Presenter createPresenter() {
        return new JockPresenterImpl(this);
    }


    @Override
    public void showDialog() {

    }
}
