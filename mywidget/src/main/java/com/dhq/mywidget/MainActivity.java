package com.dhq.mywidget;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dhq.baselibrary.activity.BaseActivity;
import com.dhq.baselibrary.util.PermissionUtils;
import com.dhq.mywidget.ui.CircleProgressActivity;
import com.dhq.mywidget.ui.HttpTestActivity;
import com.dhq.mywidget.ui.SelectActivity;
import com.dhq.mywidget.ui.StarActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.btn_circle_prograss)
    Button btnCirclePrograss;
    @BindView(R.id.btn_select_view)
    Button btnSelectView;
    @BindView(R.id.btn_http_test)
    Button btnHttpTest;
    @BindView(R.id.btn_star)
    Button btnStar;
    @BindView(R.id.tv_html_text)
    TextView tvHtmlView;
    @BindView(R.id.tv_html_text2)
    TextView tvHtmlView2;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initialize() {
        ButterKnife.bind(this);
        btnCirclePrograss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CircleProgressActivity.class);
                startActivity(intent);
            }
        });

        btnSelectView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SelectActivity.class);
                startActivity(intent);
            }
        });

        btnHttpTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HttpTestActivity.class);
                startActivity(intent);
            }
        });

        btnHttpTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HttpTestActivity.class);
                startActivity(intent);
            }
        });

        btnStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StarActivity.class);
                startActivity(intent);
            }
        });



//        %n$ms：代表输出的是字符串，n代表是第几个参数，设置m的值可以在输出之前放置空格
//        %n$md：代表输出的是整数，n代表是第几个参数，设置m的值可以在输出之前放置空格，也可以设为0m,在输出之前放置m个0
//        %n$mf：代表输出的是浮点数，n代表是第几个参数，设置m的值可以控制小数位数，如m=2.2时，输出格式为00.00

        //显示html文本
        tvHtmlView.setText(Html.fromHtml(getResources().getString(R.string.recharge_desc)));
        //显示带参数的html文本
        tvHtmlView2.setText(Html.fromHtml(String.format(getResources().getString(R.string.recharge_desc2),2,3)));


        PermissionUtils.requestPermissions(this, 2, new String[]{Manifest.permission.CAMERA}, new PermissionUtils.OnPermissionListener() {
            @Override
            public void onPermissionGranted() {
                //通过

            }

            @Override
            public void onPermissionDenied() {
                //拒绝

            }
        });
    }





}
