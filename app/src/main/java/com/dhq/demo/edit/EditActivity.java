package com.dhq.demo.edit;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.dhq.baselibrary.activity.BaseMvpActivity;
import com.dhq.demo.R;

/**
 * DESC
 * Created by douhaoqiang on 2016/11/11.
 */
public class EditActivity extends BaseMvpActivity {
    private static final String TAG = "EditActivity";

    private EditText edtext1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_lay;
    }

    @Override
    protected void initializes(Bundle savedInstanceState) {


        init();
//        initListener();
    }

    private void init() {
//        edtext1=(EditText)findViewById(R.id.edit_name);
    }

    private void initListener() {

        edtext1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
//                showToast("是否聚焦"+hasFocus);
            }
        });

        edtext1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    //表示按下的是回车键
//                    showToast("点击回车键！");
//                    edtext.isFocused();
                    return true;
                }
                return false;
            }
        });
    }

}
