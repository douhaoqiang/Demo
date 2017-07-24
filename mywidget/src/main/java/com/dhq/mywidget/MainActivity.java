package com.dhq.mywidget;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.dhq.mywidget.ui.CircleProgressActivity;
import com.dhq.mywidget.ui.HttpTestActivity;
import com.dhq.mywidget.ui.SelectActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_circle_prograss)
    Button btnCirclePrograss;
    @BindView(R.id.btn_select_view)
    Button btnSelectView;
    @BindView(R.id.btn_http_test)
    Button btnHttpTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                Intent intent2 = new Intent(MainActivity.this, SelectActivity.class);
                startActivity(intent2);
            }
        });
        btnHttpTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(MainActivity.this, HttpTestActivity.class);
                startActivity(intent3);
            }
        });

    }


}
