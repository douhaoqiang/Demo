package com.dhq.mywidget;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dhq.baselibrary.activity.BaseMvpActivity;
import com.dhq.baselibrary.util.PermissionUtil.PermissionListener;
import com.dhq.baselibrary.util.PermissionUtil.PermissionUtil;
import com.dhq.mywidget.cardswipe.CardSwipeActivity;
import com.dhq.mywidget.divider.DividerActivity;
import com.dhq.mywidget.ui.CompressActivity;
import com.dhq.mywidget.ui.HttpTestActivity;
import com.dhq.mywidget.ui.LayoutMangerTestActivity;
import com.dhq.mywidget.ui.UpdateActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseMvpActivity {

    @BindView(R.id.btn_http_test)
    Button btnHttpTest;
    @BindView(R.id.tv_html_text)
    TextView tvHtmlView;
    @BindView(R.id.tv_html_text2)
    TextView tvHtmlView2;
    @BindView(R.id.btn_divider)
    Button btnDivider;


    @BindView(R.id.btn_iamge_compress)
    Button btn_compress;

    @BindView(R.id.btn_card_swipe)
    Button btnCardSwipe;

    @BindView(R.id.btn_layoutmanger_test)
    Button btnLayoutManger;

    @BindView(R.id.btn_update)
    Button btnUpdate;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initializes(Bundle savedInstanceState) {

        ButterKnife.bind(this);

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

        btn_compress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CompressActivity.class);
                startActivity(intent);
            }
        });

        btnCardSwipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CardSwipeActivity.class);
                startActivity(intent);
            }
        });

        btnLayoutManger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LayoutMangerTestActivity.class);
                startActivity(intent);
            }
        });

        btnDivider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DividerActivity.class);
                startActivity(intent);
            }
        });


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
                startActivity(intent);
            }
        });


//        %n$ms：代表输出的是字符串，n代表是第几个参数，设置m的值可以在输出之前放置空格
//        %n$md：代表输出的是整数，n代表是第几个参数，设置m的值可以在输出之前放置空格，也可以设为0m,在输出之前放置m个0
//        %n$mf：代表输出的是浮点数，n代表是第几个参数，设置m的值可以控制小数位数，如m=2.2时，输出格式为00.00

        //显示html文本
//        tvHtmlView.setText(Html.fromHtml(getResources().getString(R.string.recharge_desc)));
        tvHtmlView.setText(Html.fromHtml(getResources().getString(R.string.recharge_desc3)));
//        tvHtmlView.setText(Html.fromHtml(getResources().getString(R.string.recharge_desc3), null, new MxgsaTagHandler(this)));

        //显示带参数的html文本
        tvHtmlView2.setText(Html.fromHtml(String.format(getResources().getString(R.string.recharge_desc2), 2, 3)));


        PermissionUtil permissionUtil = new PermissionUtil(this);
        permissionUtil.requestPermissions(new String[]{Manifest.permission.CAMERA}, new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                //通过

            }

            @Override
            public void onPermissionDenied() {
                //拒绝

            }
        });

//        PermissionUtils.requestPermissions(this, 2, new String[]{Manifest.permission.CAMERA}, new PermissionUtils.OnPermissionListener() {
//            @Override
//            public void onPermissionGranted() {
//                //通过
//
//            }
//
//            @Override
//            public void onPermissionDenied() {
//                //拒绝
//
//            }
//        });

    }


}
