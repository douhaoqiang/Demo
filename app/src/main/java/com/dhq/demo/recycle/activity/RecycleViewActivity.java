package com.dhq.demo.recycle.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.ImageView;
import android.widget.TextView;

import com.dhq.base.activity.BaseActivity;
import com.dhq.demo.R;
import com.dhq.base.adapter.ItemTouchCallback;
import com.dhq.base.adapter.BaseRvAdapter;
import com.dhq.base.adapter.BaseRvHolder;
import com.dhq.demo.recycle.bean.MyMessage;
import com.dhq.demo.recycle.contract.RecycleContract;
import com.dhq.demo.recycle.prestener.RecycleViewPresenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * DESC RecyclerView 的简单demo
 * Created by douhaoqiang on 2016/9/6.
 */

public class RecycleViewActivity extends BaseActivity implements RecycleContract.IRecycleView {

    @BindView(R.id.home_first_recycleview)
    RecyclerView recycleview;
    private Unbinder bind;

    private BaseRvAdapter<MyMessage> adapter;

    private RecycleViewPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recycleview;
    }

    @Override
    protected void initializes(Bundle savedInstanceState) {
        
        bind = ButterKnife.bind(this);
        initView();
        mPresenter = new RecycleViewPresenter(this);

        getData();
    }

    private void initView(){
        recycleview.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BaseRvAdapter<MyMessage>(R.layout.recycle_view_item) {
            @Override
            public void convert(BaseRvHolder holder, MyMessage message, int position) {

                ImageView logoIv=holder.getView(R.id.recycle_item_logo_iv);
                TextView titleTv=holder.getView(R.id.recycle_item_title_tv);
                TextView descTv=holder.getView(R.id.recycle_item_desc_tv);

                logoIv.setImageResource(message.logo);
                titleTv.setText(message.name);
                descTv.setText(message.desc);
            }
        };
        recycleview.setAdapter(adapter);

        ItemTouchCallback itemTouchCallback = new ItemTouchCallback(new ItemTouchCallback.ItemTouchCallbackListener() {
            @Override
            public void dragItem(int dragPosition, int targetPosition) {
                adapter.changeItem(dragPosition,targetPosition);
            }

            @Override
            public void removeItem(int position) {
                adapter.removeData(position);
            }
        });

        ItemTouchHelper touchHelper = new ItemTouchHelper(itemTouchCallback);
        touchHelper.attachToRecyclerView(recycleview);
    }



    private void getData(){
        mPresenter.getListData();
    }

    

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

    @Override
    public void loadingData() {
//        showToast("正在努力加载数据！");
    }

    @Override
    public void getDataSuccess(ArrayList<MyMessage> myMessages) {
        adapter.setDatas(myMessages);
    }

    @Override
    public void getDataFinal(String msg) {
//        showToast("加载数据失败，请刷新！");
    }
}
