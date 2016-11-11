package com.dhq.baselibrary.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dhq.baselibrary.R;
import com.dhq.baselibrary.presenter.BasePresenter;
import com.dhq.baselibrary.util.PermissionUtils;

/**
 * DESC MVP模式Activity
 * Created by douhaoqiang on 2016/9/1.
 */

public abstract class BaseMvpActivity<P extends BasePresenter> extends BaseActivity {

    protected P mPresenter;

    @Override
    protected void initialize() {
        //绑定presenter
        mPresenter = createPresenter();
        mPresenter.attachView(this);
        initializes();
    }


    protected abstract void initializes();

    /**
     * 获取对应的Presenter
     *
     * @return
     */
    protected abstract P createPresenter();

    @Override
    protected void onDestroy() {
        //接触绑定
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroy();
    }





    /**
     *
     */
    public void showCamera() {
        PermissionUtils.requestPermission(this, PermissionUtils.CODE_CAMERA, mPermissionGrant);
    }

    public void getAccounts() {
        PermissionUtils.requestPermission(this, PermissionUtils.CODE_GET_ACCOUNTS, mPermissionGrant);
    }

    public void callPhone() {
        PermissionUtils.requestPermission(this, PermissionUtils.CODE_CALL_PHONE, mPermissionGrant);
    }

    public void readPhoneState() {
        PermissionUtils.requestPermission(this, PermissionUtils.CODE_READ_PHONE_STATE, mPermissionGrant);
    }

    public void accessFineLocation() {
        PermissionUtils.requestPermission(this, PermissionUtils.CODE_ACCESS_FINE_LOCATION, mPermissionGrant);
    }

    public void accessCoarseLocation() {
        PermissionUtils.requestPermission(this, PermissionUtils.CODE_ACCESS_COARSE_LOCATION, mPermissionGrant);
    }

    public void readExternalStorage() {
        PermissionUtils.requestPermission(this, PermissionUtils.CODE_READ_EXTERNAL_STORAGE, mPermissionGrant);
    }

    public void writeExternalStorage() {
        PermissionUtils.requestPermission(this, PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE, mPermissionGrant);
    }

    public void recordAudio() {
        PermissionUtils.requestPermission(this, PermissionUtils.CODE_RECORD_AUDIO, mPermissionGrant);
    }


    private PermissionUtils.PermissionGrant mPermissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode) {
                case PermissionUtils.CODE_RECORD_AUDIO:
                    showToast("Result Permission Grant CODE_RECORD_AUDIO");
                    break;
                case PermissionUtils.CODE_GET_ACCOUNTS:
                    showToast("Result Permission Grant CODE_GET_ACCOUNTS");
                    break;
                case PermissionUtils.CODE_READ_PHONE_STATE:
                    showToast("Result Permission Grant CODE_READ_PHONE_STATE");
                    break;
                case PermissionUtils.CODE_CALL_PHONE:
                    showToast("Result Permission Grant CODE_CALL_PHONE");
                    break;
                case PermissionUtils.CODE_CAMERA:
                    showToast("Result Permission Grant CODE_CAMERA");
                    break;
                case PermissionUtils.CODE_ACCESS_FINE_LOCATION:
                    showToast("Result Permission Grant CODE_ACCESS_FINE_LOCATION");
                    break;
                case PermissionUtils.CODE_ACCESS_COARSE_LOCATION:
                    showToast("Result Permission Grant CODE_ACCESS_COARSE_LOCATION");
                    break;
                case PermissionUtils.CODE_READ_EXTERNAL_STORAGE:
                    showToast("Result Permission Grant CODE_READ_EXTERNAL_STORAGE");
                    break;
                case PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE:
                    showToast("Result Permission Grant CODE_WRITE_EXTERNAL_STORAGE");
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(final int requestCode, String[] permissions, int[] grantResults) {
        PermissionUtils.requestPermissionsResult(this, requestCode, permissions, grantResults, mPermissionGrant);
    }


}
