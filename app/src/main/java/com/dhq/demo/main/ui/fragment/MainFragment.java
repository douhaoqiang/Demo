package com.dhq.demo.main.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dhq.baselibrary.fragment.BaseMvpFragment;
import com.dhq.demo.R;
import com.dhq.demo.edit.EditActivity;
import com.dhq.demo.home.activity.HomeFragment;
import com.dhq.demo.main.contract.MainContract;
import com.dhq.demo.main.presenter.MainPresenterImpl;
import com.dhq.demo.ndk.activity.NdkDemoActivity;
import com.dhq.demo.recycle.activity.RecycleViewActivity;
import com.dhq.demo.refresh.PullToRefreshLayout;
import com.dhq.demo.rx.RxActivity;
import com.dhq.dialoglibrary.MyDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * DESC 主页Fragment
 * Created by douhaoqiang on 2016/11/18.
 */
public class MainFragment extends BaseMvpFragment<MainContract.IMainPresenter> {
    private static final String TAG = "MainFragment";
    private Unbinder bind;

    @BindView(R.id.main_refresh_view)
    PullToRefreshLayout refreshView;

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

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {

        }
    };

    private Runnable task =new Runnable() {
        @Override
        public void run() {
            refreshView.complete();
        }
    };



    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_lay;
    }

    @Override
    protected void initializes() {
        bind = ButterKnife.bind(this,getView());
        initData();
        initListener();
    }

    @Override
    protected MainContract.IMainPresenter createPresenter() {
        return new MainPresenterImpl(getActivity());
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

        refreshView.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                //刷新列表
//                pullToRefreshLayout.complete();
                handler.postDelayed(task, 2000);
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                //加载更多
//                pullToRefreshLayout.complete();
                handler.postDelayed(task, 2000);
            }
        });


        menuRecycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity(RecycleViewActivity.class);
            }
        });

        menuNdk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity(NdkDemoActivity.class);
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
    }


    private void gotoActivity(Class aClass){
        Intent intent=new Intent(getActivity(),aClass);
        startActivity(intent);
    }


}
