package com.dhq.demo.recycle.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.dhq.baselibrary.activity.BaseActivity;
import com.dhq.demo.R;
import com.dhq.demo.recycle.adapter.ItemTouchCallback;
import com.dhq.demo.recycle.adapter.RecycleViewAdapter;
import com.dhq.demo.recycle.bean.MyMessage;
import com.dhq.demo.recycle.prestener.RecycleViewPresenter;
import com.dhq.demo.recycle.view.IRecycleView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * DESC
 * Created by douhaoqiang on 2016/9/6.
 */

public class RecycleViewActivity extends BaseActivity<IRecycleView, RecycleViewPresenter> implements IRecycleView {

    @BindView(R.id.home_first_recycleview)
    RecyclerView recycleview;
    private Unbinder bind;

    private RecycleViewAdapter homeFirstAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recycleview;
    }

    @Override
    protected void initialize() {

        bind = ButterKnife.bind(this);
        initView();
        getData();
    }

    private void initView(){
        recycleview.setLayoutManager(new LinearLayoutManager(this));
        homeFirstAdapter = new RecycleViewAdapter();
        recycleview.setAdapter(homeFirstAdapter);

        ItemTouchCallback itemTouchCallback = new ItemTouchCallback(new ItemTouchCallback.ItemTouchCallbackListener() {
            @Override
            public void dragItem(int dragPosition, int targetPosition) {
                homeFirstAdapter.changeItem(dragPosition,targetPosition);
            }

            @Override
            public void removeItem(int position) {
                homeFirstAdapter.removeData(position);
            }
        });

        ItemTouchHelper touchHelper = new ItemTouchHelper(itemTouchCallback);
        touchHelper.attachToRecyclerView(recycleview);
    }

    private void getData(){
        mPresenter.getListData();
    }

    @Override
    protected RecycleViewPresenter createPresenter() {
        return new RecycleViewPresenter(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

    @Override
    public void loadingData() {
        showToast("正在努力加载数据！");
    }

    @Override
    public void getDataSuccess(ArrayList<MyMessage> myMessages) {
        homeFirstAdapter.setDatas(myMessages);
    }

    @Override
    public void getDataFinal(String msg) {
        showToast("加载数据失败，请刷新！");
    }
}
