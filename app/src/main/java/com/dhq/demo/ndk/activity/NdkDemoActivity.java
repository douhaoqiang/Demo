package com.dhq.demo.ndk.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.UnderlineSpan;
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
//                ds.setUnderlineText(false);      //设置下划线
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
//                ds.setUnderlineText(false);      //设置下划线
            }

            @Override
            public void onClick(View widget) {
                Log.d("info", "onTextClick........2222");
            }
        }, 9, url_0_text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        //设置字体大小（绝对值,单位：像素）
        spStr.setSpan(new AbsoluteSizeSpan(20), 0, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //第二个参数boolean dip，如果为true，表示前面的字体大小单位为dip，否则为像素，同上。
        spStr.setSpan(new AbsoluteSizeSpan(20,true), 0, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        //设置字体大小（相对值,单位：像素） 参数表示为默认字体大小的多少倍
        spStr.setSpan(new RelativeSizeSpan(0.5f), 0, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //0.5f表示默认字体大小的一半
        spStr.setSpan(new RelativeSizeSpan(2.0f), 0, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //2.0f表示默认字体大小的两倍


        //设置字体样式正常，粗体，斜体，粗斜体
        spStr.setSpan(new StyleSpan(android.graphics.Typeface.NORMAL), 0, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //正常
        spStr.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //粗体
        spStr.setSpan(new StyleSpan(android.graphics.Typeface.ITALIC), 0, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //斜体
        spStr.setSpan(new StyleSpan(android.graphics.Typeface.BOLD_ITALIC), 0, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //粗斜体

        //设置下划线
//        spStr.setSpan(new UnderlineSpan(), 0, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        //设置删除线
//        spStr.setSpan(new StrikethroughSpan(), 0, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        //设置下标
//        spStr.setSpan(new SubscriptSpan(), 0, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置上标
//        spStr.setSpan(new SuperscriptSpan(), 0, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


//        spStr.setSpan(new ForegroundColorSpan(Color.RED), 0, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //设置字体颜色为红色

        text_tv.setHighlightColor(Color.TRANSPARENT); //设置点击后的颜色为透明，否则会一直出现高亮
        text_tv.setText(spStr);
        text_tv.setMovementMethod(LinkMovementMethod.getInstance());//开始响应点击事件
    }

}
