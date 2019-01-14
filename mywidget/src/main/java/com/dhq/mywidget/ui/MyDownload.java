package com.dhq.mywidget.ui;

import com.azhon.appupdate.base.BaseHttpDownloadManager;
import com.azhon.appupdate.listener.OnDownloadListener;
import com.dhq.net.DownLoadObserver;
import com.dhq.net.http.HttpUtil;

import java.io.File;
import java.util.HashMap;

/**
 * 项目名:    AppUpdate
 * 包名       com.azhon.app
 * 文件名:    MyDownload
 * 创建时间:  2018/1/27 on 19:31
 * 描述:     TODO 自己实现apk的下载过程
 *
 * @author 阿钟
 */


public class MyDownload extends BaseHttpDownloadManager {

    @Override
    public void download(final String apkUrl, final String apkName, final OnDownloadListener listener) {
        listener.start();

        try {

            HttpUtil.getInstance().downLoadFileReq(apkUrl, new HashMap<String, Object>(), new DownLoadObserver() {

                @Override
                public void onDownLoadSuccess(File file) {
                    listener.done(file);
                }

                @Override
                public void onDownLoadFail(String errorMsg) {
                    listener.error(new Exception(errorMsg));
                }

                @Override
                public void onDownLoadProgress(int progress, long total) {
                    listener.downloading((int)total,progress);
                }
            });

        } catch (Exception e) {

            e.printStackTrace();
        }


//        try {
//            URL url = new URL(apkUrl);
//            HttpURLConnection con = (HttpURLConnection) url.openConnection();
//            con.setRequestMethod("GET");
//            con.setReadTimeout(Constant.HTTP_TIME_OUT);
//            con.setConnectTimeout(Constant.HTTP_TIME_OUT);
//            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                InputStream is = con.getInputStream();
//                int length = con.getContentLength();
//                int len;
//                //当前已下载完成的进度
//                int progress = 0;
//                byte[] buffer = new byte[1024 * 4];
//                File file = FileUtil.createFile(Environment.getExternalStorageDirectory() + "/AppUpdate", apkName);
//                FileOutputStream stream = new FileOutputStream(file);
//                while ((len = is.read(buffer)) != -1) {
//                    //将获取到的流写入文件中
//                    stream.write(buffer, 0, len);
//                    progress += len;
//                    listener.downloading(length, progress);
//                }
//                //完成io操作,释放资源
//                stream.flush();
//                stream.close();
//                is.close();
//                listener.done(file);
//            } else {
//                listener.error(new SocketTimeoutException("连接超时！"));
//            }
//            con.disconnect();
//        } catch (Exception e) {
//            listener.error(e);
//            e.printStackTrace();
//        }

    }

    @Override
    public void cancel() {

    }
}
