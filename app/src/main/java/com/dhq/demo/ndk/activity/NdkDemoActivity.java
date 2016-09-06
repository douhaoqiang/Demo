package com.dhq.demo.ndk.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.dhq.demo.R;
import com.dhq.demo.utils.JNIUtils;

/**
 * DESC
 * Created by douhaoqiang on 2016/9/6.
 */

public class NdkDemoActivity extends Activity {

    TextView text_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ndkdemo);
        initView();
    }

    private void initView() {
        text_tv = (TextView)findViewById(R.id.ndk_text_tv);
        spanStringClick();
    }

    /**
     * 字符串的分段响应事件设置监听
     */
    private void spanStringClick(){
        String url_0_text = "用户协议及隐私条款——点击另一个";

        SpannableString spStr = new SpannableString(url_0_text);

        spStr.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.WHITE);       //设置文件颜色
                ds.setUnderlineText(true);      //设置下划线

            }

            @Override
            public void onClick(View widget) {
                Log.d("info", "onTextClick........");
                text_tv.append(JNIUtils.getPackname(""));
            }
        }, 0, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        spStr.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.WHITE);       //设置文件颜色
                ds.setUnderlineText(true);      //设置下划线
            }

            @Override
            public void onClick(View widget) {
                Log.d("info", "onTextClick........2222");
            }
        }, 9, url_0_text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        spStr.setSpan(new ForegroundColorSpan(Color.RED), 0, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //设置字体颜色为红色
        text_tv.setHighlightColor(Color.TRANSPARENT); //设置点击后的颜色为透明，否则会一直出现高亮
        text_tv.setText(spStr);
        text_tv.setMovementMethod(LinkMovementMethod.getInstance());//开始响应点击事件
    }

}
