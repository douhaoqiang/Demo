package com.dhq.downloadfile;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;

import static android.R.attr.path;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadUtil.downLoadFile("http://download.hscpro.com/cecm/AP/HSCFamilyDefaultV1.2.0.apk", getFilesDir().getPath(), new DownloadUtil.ReqProgressCallBack() {
            @Override
            public void onProgress(long total, long current) {
                Log.d("onProgress",total+" -- "+current);
            }

            @Override
            public void failed() {
                Log.d("failed","failed");
            }

            @Override
            public void success(File file) {
                Log.d("success",file.getPath());
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(Uri.parse("file://" + file.getPath()),"application/vnd.android.package-archive");
                startActivity(intent);
            }

        });

    }
}
