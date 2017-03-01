package com.dhq.demo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.dhq.baselibrary.activity.BaseActivity;
import com.dhq.demo.main.ui.MainActivity;
import com.dhq.demo.widget.MotionListener;
import com.dhq.demo.widget.SlideIcon;
import com.dhq.demo.widget.SlideView;

/**
 * DESC
 * Created by douhaoqiang on 2017/3/1.
 */
public class SlideViewActivity extends BaseActivity {

    private static final String TAG = "SlideViewActivity";

    private SlideIcon mSlideView;

    private CheckBox mResetNotFullCb;

    private CheckBox mEnableWhenFullCb;

    private Button mResetBtn;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_slideview_lay;
    }

    @Override
    protected void initialize() {


        mSlideView = (SlideIcon) findViewById(R.id.slideview);


        mSlideView.setListener(new MotionListener() {
            @Override
            public void onActionMove(int distanceX) {
                Log.d(TAG,"distanceX:"+distanceX);
            }

            @Override
            public void onActionUp(int x) {
                Log.d(TAG,"x:"+x);
            }
        });

//        mSlideView.addSlideListener(new SlideView.OnSlideListener() {
//            @Override
//            public void onSlideSuccess() {
//                Toast.makeText(SlideViewActivity.this, "确认成功", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        mResetNotFullCb = (CheckBox) findViewById(R.id.cb_reset_when_not_full);
//        mResetNotFullCb.setChecked(mSlideView.isResetWhenNotFull());
//        mResetNotFullCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                mSlideView.resetWhenNotFull(isChecked);
//            }
//        });
//
//        mEnableWhenFullCb = (CheckBox) findViewById(R.id.cb_enable_when_full);
//        mEnableWhenFullCb.setChecked(mSlideView.isEnableWhenFull());
//        mEnableWhenFullCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                mSlideView.enableWhenFull(isChecked);
//            }
//        });
//
//        mResetBtn = (Button) findViewById(R.id.btn_reset);
//        mResetBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mSlideView.reset();
//            }
//        });

    }



}
