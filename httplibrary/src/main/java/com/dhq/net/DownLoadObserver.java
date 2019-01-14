package com.dhq.net;

import android.content.Context;
import android.os.Environment;

import com.dhq.baselibrary.util.LogUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * DESC 下载响应处理
 * Created by douhaoqiang on 2017/2/14.
 */
public abstract class DownLoadObserver implements Observer<File> {
    private static final String TAG = "DownLoadObserver";
    private Disposable mDisposable;

    private String destFileDir= Environment.getExternalStorageDirectory() + "/AppUpdate";//目标文件夹
    private String destFileName="test.apk";//目标文件名
    /**
     * 不显示弹框
     */
    public DownLoadObserver() {

    }


    @Override
    public void onSubscribe(Disposable d) {
        this.mDisposable = d;
        showWaitingDialog();
    }

    @Override
    public void onNext(File file) {
        try {
            onDownLoadSuccess(file);
//            saveFile(responseBody);
        } catch (Exception e) {
            e.printStackTrace();
            onDownLoadFail("下载失败");
        }
    }

    @Override
    public void onError(Throwable e) {
        hintWaitingDialog();
        onDownLoadFail("下载失败");
    }

    @Override
    public void onComplete() {
        hintWaitingDialog();
    }


    /**
     * 解除网络请求绑定
     */
    public void cancle() {
        mDisposable.dispose();
    }


    /**
     * 显示网络请求等待框
     */
    private void showWaitingDialog() {
//        if (myDialog != null)
//            myDialog.show();
    }

    /**
     * 取消等待框
     */
    private void hintWaitingDialog() {
//        if (myDialog != null)
//            myDialog.cancel();
    }


    /**
     * 将文件写入本地
     * @param responseBody 请求结果全体
     * @return 写入完成的文件
     * @throws IOException IO异常
     */
    public File saveFile(ResponseBody responseBody) throws IOException {

        InputStream is = null;
        byte[] buf = new byte[2048];
        int len = 0;
        FileOutputStream fos = null;
        try {
            is = responseBody.byteStream();
            final long total = responseBody.contentLength();
            long sum = 0;

            File dir = new File(destFileDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, destFileName);
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                sum += len;
                fos.write(buf, 0, len);
                final long finalSum = sum;
                LogUtil.d(""+(int) (finalSum * 100 / total));
                //这里就是对进度的监听回调
                onDownLoadProgress((int) (finalSum * 100 / total),total);
            }
            fos.flush();

            return file;

        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fos != null) fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    //下载成功的回调
    public abstract void onDownLoadSuccess(File file);

    //下载失败回调
    public abstract void onDownLoadFail(String errorMsg);

    //下载进度监听
    public abstract void onDownLoadProgress(int progress, long total);


}
