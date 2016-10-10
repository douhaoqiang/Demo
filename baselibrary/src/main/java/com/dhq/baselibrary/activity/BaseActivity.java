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
 * DESC
 * Created by douhaoqiang on 2016/9/1.
 */

public abstract class BaseActivity<V, P extends BasePresenter<V>> extends AppCompatActivity {

    protected P mPresenter;
    private static final int INVALID_VAL = -1;

    private static final int COLOR_DEFAULT = Color.parseColor("#FA7198");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            // 透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//头部状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//底部虚拟键
//        }

        setContentView(getLayoutId());

        setStatusBar(getResources().getColor(R.color.colorAccent));


        mPresenter = createPresenter();
        mPresenter.attachView((V) this);

        initialize();
    }


    private void setStatusBar(int statusColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (statusColor != INVALID_VAL) {
                getWindow().setStatusBarColor(statusColor);
            }
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {

            int color = COLOR_DEFAULT;
            ViewGroup contentView = (ViewGroup) findViewById(android.R.id.content);
//            contentView.setFitsSystemWindows(true);
//            contentView.setClipToPadding(true);
            if (statusColor != INVALID_VAL) {
                color = statusColor;
            }
            View statusBarView = contentView.getChildAt(0);
            // 改变颜色时避免重复添加statusBarView
            if (statusBarView != null && statusBarView.getMeasuredHeight() == getStatusBarHeight(this)) {
                statusBarView.setBackgroundColor(color);
                return;
            }
            statusBarView = new View(this);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(this));
            statusBarView.setBackgroundColor(color);
            contentView.addView(statusBarView, lp);
        }
    }

    private int getStatusBarHeight(Context context) {

        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroy();
    }

    /**
     * 获取Activity对应的布局文件
     *
     * @return 布局文件的layoutid
     */
    protected abstract int getLayoutId();

    /**
     * 初始化方法
     */
    protected abstract void initialize();


    /**
     * 获取对应的Presenter
     *
     * @return
     */
    protected abstract P createPresenter();


    /**
     * 显示Toast消息
     *
     * @param msg 需要显示的消息
     */
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示Toast消息
     *
     * @param resId 需要显示的消息id
     */
    public void showToast(int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
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
