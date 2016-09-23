package com.dhq.demo.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dhq.baselibrary.adapter.ItemTouchCallback;
import com.dhq.baselibrary.adapter.RecycleViewBaseAdapter;
import com.dhq.baselibrary.adapter.RecycleViewBaseHolder;
import com.dhq.demo.R;
import com.dhq.demo.recycle.bean.MyMessage;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * DESC
 * Created by douhaoqiang on 2016/9/8.
 */

public class ItemFragment extends Fragment {
    @BindView(R.id.item_fragment_recyclerview)
    RecyclerView mRecyclerview;

    private RecycleViewBaseAdapter<MyMessage> adapter;
    private Unbinder bind;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_item, container, false);

        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bind = ButterKnife.bind(this, getView());
        initView();
        setData();
    }

    private void initView() {
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new RecycleViewBaseAdapter<MyMessage>(R.layout.recycle_view_item) {
            @Override
            public void convert(RecycleViewBaseHolder holder, MyMessage message, int position) {

                ImageView logoIv = holder.getView(R.id.recycle_item_logo_iv);
                TextView titleTv = holder.getView(R.id.recycle_item_title_tv);
                TextView descTv = holder.getView(R.id.recycle_item_desc_tv);

                logoIv.setImageResource(message.logo);
                titleTv.setText(message.name);
                descTv.setText(message.desc);
            }
        };
        mRecyclerview.setAdapter(adapter);

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
        touchHelper.attachToRecyclerView(mRecyclerview);
    }

    private void setData(){
        ArrayList<MyMessage> myMessages=new ArrayList<>();
        MyMessage myMessage;
        for(int i=1;i<=10;i++){
            myMessage=new MyMessage(R.mipmap.ic_launcher,i+"-Title",i+"---描述的卡口监控了发动机是两个");
            myMessages.add(myMessage);
        }
        adapter.setDatas(myMessages);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
