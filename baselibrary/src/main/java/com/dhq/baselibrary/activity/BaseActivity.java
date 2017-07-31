package com.dhq.baselibrary.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.dhq.baselibrary.R;
import com.dhq.baselibrary.util.PermissionUtils;

/**
 * DESC 基础Activity
 * Created by douhaoqiang on 2016/11/10.
 */
public abstract class BaseActivity extends AppCompatActivity {


    private static final int INVALID_VAL = -1;

    private static final int COLOR_DEFAULT = Color.parseColor("#FA7198");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());

        initWindows();

        initialize();

    }

    private void initWindows() {
        Window window = getWindow();
        int color = getResources().getColor(R.color.colorAccent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏颜色
            window.setStatusBarColor(color);
            //设置导航栏颜色
            window.setNavigationBarColor(color);
            ViewGroup contentView = ((ViewGroup) findViewById(android.R.id.content));
            View childAt = contentView.getChildAt(0);
            if (childAt != null) {
                childAt.setFitsSystemWindows(true);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4
            //透明状态栏
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            //设置contentview为fitsSystemWindows
            ViewGroup contentView = (ViewGroup) findViewById(android.R.id.content);
            View childAt = contentView.getChildAt(0);
            if (childAt != null) {
                childAt.setFitsSystemWindows(true);
            }
            //给statusbar着色
            View view = new View(this);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(this)));
            view.setBackgroundColor(color);
            contentView.addView(view);
        }
    }

    /**
     * 获取状态栏高度
     *
     * @param context context
     * @return 状态栏高度
     */
    private static int getStatusBarHeight(Context context) {
        // 获得状态栏高度
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
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
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(final int requestCode, String[] permissions, int[] grantResults) {
        PermissionUtils.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

}
