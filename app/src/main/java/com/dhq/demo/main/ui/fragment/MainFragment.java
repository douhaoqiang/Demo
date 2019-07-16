package com.dhq.demo.main.ui.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.dhq.base.fragment.BaseFragment;
import com.dhq.demo.R;
import com.dhq.demo.SlideViewActivity;
import com.dhq.demo.cloudview.CloudViewActivity;
import com.dhq.demo.edit.EditActivity;
import com.dhq.demo.home.fragment.HomeFragment;
import com.dhq.demo.main.contract.MainContract;
import com.dhq.demo.main.presenter.MainPresenterImpl;
import com.dhq.demo.recycle.activity.RecycleViewActivity;
import com.dhq.demo.rx.RxActivity;
import com.dhq.dialoglibrary.MyDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * DESC 主页Fragment
 * Created by douhaoqiang on 2016/11/18.
 */
public class MainFragment extends BaseFragment implements MainContract.IMainView {
    private static final String TAG = "MainFragment";
    private Unbinder bind;

    @BindView(R.id.main_menu_recycle)
    Button menuRecycle;

    @BindView(R.id.main_menu_ndk)
    Button menuNdk;

    @BindView(R.id.main_menu_tablayout)
    Button tablayout;

    @BindView(R.id.main_menu_edittest)
    Button edtext;

    @BindView(R.id.main_menu_rx)
    Button rxBtn;

    @BindView(R.id.main_menu_more)
    Button moreBtn;

    @BindView(R.id.main_menu_cloud_view)
    Button cloudView;

    @BindView(R.id.main_menu_slide_view)
    Button slideView;

    @BindView(R.id.btn_wheel)
    Button btnWheel;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_lay;
    }

    @Override
    protected void initialize() {

        bind = ButterKnife.bind(this, getView());
        initData();
        initListener();

        new MainPresenterImpl(this);
    }


    /**
     * 初始化列表数据
     */
    private void initData() {


    }


    /**
     * 事件监听器初始化
     */
    private void initListener() {

        menuRecycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity(RecycleViewActivity.class);
            }
        });

        menuNdk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        tablayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity(HomeFragment.class);
            }
        });

        edtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity(EditActivity.class);
            }
        });
        rxBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity(RxActivity.class);
            }
        });

        moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyDialog(getActivity(), MyDialog.CUSTOM_IMAGE_TYPE)
                        .setTitleText("Good job!")
                        .setContentText("You clicked the button!")
                        .show();
            }
        });

        cloudView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity(CloudViewActivity.class);
            }
        });

        slideView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity(SlideViewActivity.class);
            }
        });

        btnWheel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                gotoActivity(WheelActivity.class);
            }
        });

    }


    private void gotoActivity(Class aClass) {
        Intent intent = new Intent(getActivity(), aClass);
        startActivity(intent);
    }


    @Override
    public void isLoading() {

    }

    @Override
    public void isCompleted() {

    }
}
