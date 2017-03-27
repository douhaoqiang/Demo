package com.dhq.banner;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity {

    BannerView bannerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        bannerView = (BannerView) findViewById(R.id.main_banner_view);

        List<String> imgUrlList = Arrays.asList(Constant.imgUrls);
//
//        BannerView<String> bannerView = new BannerView(this);

        bannerView.setOnBannerListener(new BannerView.BannerListener<String>() {
            @Override
            public void getView(ImageView imageView, String data, int position) {
                Glide.with(MainActivity.this).load(data).into(imageView);
            }

            @Override
            public void selectitem(String data, int position) {

            }

            @Override
            public void itemClick(String data, int position) {
                Toast.makeText(MainActivity.this, "点击 : " + position, Toast.LENGTH_SHORT).show();
            }


        });

        bannerView.setImgUrlData(imgUrlList);

    }

}
