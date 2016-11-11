package com.dhq.demo.main.ui;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.dhq.baselibrary.activity.BaseMvpActivity;
import com.dhq.demo.edit.EditActivity;
import com.dhq.demo.main.contract.MainContract;
import com.dhq.demo.main.presenter.MainPresenterImpl;
import com.dhq.demo.R;
import com.dhq.demo.home.activity.HomeActivity;
import com.dhq.demo.ndk.activity.NdkDemoActivity;
import com.dhq.demo.recycle.activity.RecycleViewActivity;
import com.dhq.demo.refresh.PullToRefreshLayout;
import com.dhq.dialoglibrary.MyDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends BaseMvpActivity<MainContract.IMainPresenter> implements MainContract.IMainView {

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

    @BindView(R.id.main_menu_more)
    Button moreBtn;


    private Unbinder bind;

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
        Log.e("infe","onCreate");
        return R.layout.activity_main;
    }

    @Override
    protected void initializes() {
        bind = ButterKnife.bind(this);
        initData();
        initListener();
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
                gotoActivity(HomeActivity.class);
            }
        });

        edtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity(EditActivity.class);
            }
        });

        moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyDialog(MainActivity.this, MyDialog.CUSTOM_IMAGE_TYPE)
                        .setTitleText("Good job!")
                        .setContentText("You clicked the button!")
                        .show();
            }
        });
    }


    private void gotoActivity(Class aClass){
        Intent intent=new Intent(this,aClass);
        startActivity(intent);
    }


    private void transfer() {

//        RotateAnimation animation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        animation.setDuration(2000);
//        animation.setRepeatCount(2);
//        animation.setRepeatMode(Animation.REVERSE);
//        settingTv.startAnimation(animation);


//        ObjectAnimator anim = ObjectAnimator//
//                .ofFloat(settingTv, "rotationX", 0.0F, 100.0F)//
//                .setDuration(500);//
        ValueAnimator anim = ObjectAnimator.ofFloat(0.0F, 100.0F).setDuration(500);//
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float cVal = (Float) animation.getAnimatedValue();
                menuRecycle.setRotationX(cVal);
            }
        });
        anim.start();

    }

    @Override
    protected MainPresenterImpl createPresenter() {
        return new MainPresenterImpl(this);
    }

    @Override
    public void isLoading() {
        //表示正在加载数据应该显示加载框


    }

    @Override
    public void isCompleted() {
        //表示正在加载数据完成，应该隐藏加载框

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }


}
