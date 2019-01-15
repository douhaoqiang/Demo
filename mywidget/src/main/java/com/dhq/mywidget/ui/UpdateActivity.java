package com.dhq.mywidget.ui;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.dhq.appupdate.config.UpdateConfiguration;
import com.dhq.appupdate.listener.OnButtonClickListener;
import com.dhq.appupdate.listener.OnDownloadListener;
import com.dhq.appupdate.manager.DownloadManager;
import com.dhq.baselibrary.activity.BaseActivity;
import com.dhq.mywidget.R;

import java.io.File;

/**
 * DESC
 * Created by douhaoqiang on 2019/1/7.
 */
public class UpdateActivity extends BaseActivity implements OnDownloadListener, OnButtonClickListener {

    private Button btn_update;
    private DownloadManager manager;


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//            progressBar.setMax(msg.arg1);
//            progressBar.setProgress(msg.arg2);
            btn_update.setText(msg.arg2+"");
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_lay;
    }

    @Override
    protected void initialize() {

        btn_update=findViewById(R.id.btn_update);

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startUpdate3();
            }
        });

    }


    private void startUpdate3() {
        /*
         * 整个库允许配置的内容
         * 非必选
         */
        UpdateConfiguration configuration = new UpdateConfiguration()
                //输出错误日志
                .setEnableLog(true)
                //设置自定义的下载
                .setHttpManager(new MyDownload())
                //下载完成自动跳动安装页面
                .setJumpInstallPage(true)
                //设置对话框背景图片 (图片规范参照demo中的示例图)
                //.setDialogImage(R.drawable.ic_dialog)
                //设置按钮的颜色
                //.setDialogButtonColor(Color.parseColor("#E743DA"))
                //设置按钮的文字颜色
                .setDialogButtonTextColor(Color.WHITE)
                //支持断点下载
                .setBreakpointDownload(true)
                //设置是否显示通知栏进度
                .setShowNotification(true)
                //设置强制更新
                .setForcedUpgrade(true)
//                .setHttpManager(new MyDownload())
                //设置对话框按钮的点击监听
                .setButtonClickListener(this)
                //设置下载过程的监听
                .setOnDownloadListener(this);

        manager = DownloadManager.getInstance(this);
        manager.setApkName("appupdate.apk")
//                .setApkUrl("https://raw.githubusercontent.com/azhon/AppUpdate/master/apk/appupdate.apk")
                .setApkUrl("http://download.hscpro.com/cecm/iOS/HSCAssessmentV1.2.0-20181207.apk")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setConfiguration(configuration)
//                .setDownloadPath(Environment.getExternalStorageDirectory() + "/AppUpdate")
                .setApkVersionName("2.1.8")
                .setApkSize("20.4")
                .setAuthorities(getPackageName())
                .setApkDescription("1.支持断点下载\n2.支持Android N\n3.支持Android O\n4.支持自定义下载过程\n5.支持 设备>=Android M 动态权限的申请\n6.支持通知栏进度条展示(或者自定义显示进度)")
                .download();
    }

    @Override
    public void onButtonClick(int id) {

    }

    @Override
    public void start() {

    }

    @Override
    public void downloading(int max, int progress) {
        Message msg = new Message();
        msg.arg1 = max;
        msg.arg2 = progress;
        handler.sendMessage(msg);
    }

    @Override
    public void done(File apk) {

    }

    @Override
    public void cancel() {

    }

    @Override
    public void error(Exception e) {

    }
}
