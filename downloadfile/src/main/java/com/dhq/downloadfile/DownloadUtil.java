package com.dhq.downloadfile;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * DESC
 * Created by douhaoqiang on 2017/4/13.
 */

public class DownloadUtil {

    private static final String TAG="DownloadUtil";
    private static final int CONNECT_TIMEOUT = 15;
    private static final int READ_TIMEOUT = 30;

    private static OkHttpClient mOkHttpClient= new OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .build();;


    /**
     * 下载文件
     * @param fileUrl 文件url
     * @param destFileDir 存储目标目录
     */
    public static void downLoadFile(String fileUrl, final String destFileDir, final ReqProgressCallBack callBack) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1, fileUrl.length());
        final File file = new File(destFileDir, fileName);
//        final String fileName = "sf.apk";
//        final File file = new File(destFileDir, fileName);

        final Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 0://失败
                        callBack.failed();
                        break;
                    case 1://下载中
                        long total = msg.getData().getLong("total");
                        long current = msg.getData().getLong("current");
                        callBack.onProgress(total,current);
                        break;
                    case 2://下载完成
                        callBack.success(file);
                        break;
                }
            }
        };


//        if (file.exists()) {
//            Message msg=new Message();
//            msg.what=2;
//            handler.sendMessage(msg);
//            return;
//        }

        Request request = new Request.Builder().url(fileUrl).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, e.toString());
                Message msg=new Message();
                msg.what=0;
                handler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    final long total= response.body().contentLength();
//                    Log.e(TAG, "total------>" + total);
                    long current = 0;
                    is = response.body().byteStream();
                    fos = new FileOutputStream(file);
                    while ((len = is.read(buf)) != -1) {
                        current += len;
                        fos.write(buf, 0, len);
//                        Log.e(TAG, "current------>" + current);
                        Message msg=new Message();
                        msg.what=1;
                        Bundle bundle = new Bundle();//消息数据实体
                        bundle.putLong("total", total);
                        bundle.putLong("current", current);
                        msg.setData(bundle);
                        handler.sendMessage(msg);
                    }
                    fos.flush();
                } catch (IOException e) {
                    Log.e(TAG, e.toString());
                    Message msg=new Message();
                    msg.what=0;
                    handler.sendMessage(msg);
                } finally {
                    try {
                        if (is != null) {
                            is.close();
                        }
                        if (fos != null) {
                            fos.close();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, e.toString());
                    }
                }
            }
        });
    }



    public interface ReqProgressCallBack {
        /**
         * 响应进度更新
         */
        void onProgress(long total, long current);

        /**
         * 下载失败
         */
        void failed();

        /**
         * 下载完成
         */
        void success(File file);

    }

}
