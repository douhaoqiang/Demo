package com.dhq.demo.recycle.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dhq.demo.R;
import com.dhq.demo.recycle.bean.MyMessage;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * DESC
 * Created by douhaoqiang on 2016/9/6.
 */

public class RecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private ArrayList<MyMessage> datas = new ArrayList<>();

    public void setDatas(ArrayList<MyMessage> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    public void removeData(int position){
        this.datas.remove(position);
        notifyItemRemoved(position);
    }

    public void changeItem(int dragPosition,int targetPosition){
        Collections.swap(datas,dragPosition,targetPosition);
        notifyItemMoved(dragPosition, targetPosition);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_view_item, parent, false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder= (MyHolder) holder;
        myHolder.logoIv.setImageResource(datas.get(holder.getAdapterPosition()).logo);
        myHolder.titleTv.setText(datas.get(holder.getAdapterPosition()).name);
        myHolder.descTv.setText(datas.get(holder.getAdapterPosition()).desc);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    public class MyHolder<MyMessage> extends RecyclerView.ViewHolder {
        @BindView(R.id.recycle_item_logo_iv)
        ImageView logoIv;
        @BindView(R.id.recycle_item_title_tv)
        TextView titleTv;
        @BindView(R.id.recycle_item_desc_tv)
        TextView descTv;
        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }


    }

}
