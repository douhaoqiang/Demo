package com.dhq.base.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * DESC RecycleView 的公用adapter
 * Created by douhaoqiang on 2016/9/6.
 */

public abstract class BaseRvManyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List datas = new ArrayList<>();

    /**
     * 设置列表数据
     *
     * @param datas 列表数据
     */
    public void setDatas(List datas) {
        this.datas.clear();
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    /**
     * 添加单个数据
     *
     * @param data 列表数据
     */
    public void addData(Object data) {
        this.datas.add(data);
        notifyDataSetChanged();
    }

    /**
     * 添加单个数据到一个位置
     *
     * @param data 列表数据
     */
    public void addData(Object data, int position) {
        this.datas.add(position, data);
        notifyItemInserted(position);
    }

    /**
     * 添加多个数据
     *
     * @param datas 要添加的多个数据
     */
    public void addDatas(ArrayList datas) {
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    /**
     * 移除单个item
     *
     * @param position 要移除的item下标
     */
    public void removeData(int position) {
        this.datas.remove(position);
        notifyItemRemoved(position);
    }

    public List getDatas() {
        return this.datas;
    }

    @Override
    public int getItemViewType(int position) {
        return getViewResId(this.datas.get(position));
    }


    /**
     * 拖拽交换数据
     *
     * @param dragPosition   拖拽的item下标
     * @param targetPosition 要交换的item的下标
     */
    public void changeItem(int dragPosition, int targetPosition) {
        Collections.swap(datas, dragPosition, targetPosition);
        notifyItemMoved(dragPosition, targetPosition);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);

        return new BaseRvHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        convert((BaseRvHolder) holder, datas.get(position), getItemViewType(position), position);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    /**
     * 根据类型获取布局id
     *
     * @param item item
     * @return
     */
    public abstract int getViewResId(Object item);

    /**
     * 绘制item界面
     * @param holder   holder
     * @param item     item
     * @param viewtype  type
     * @param position  position
     */
    public abstract void convert(BaseRvHolder holder, Object item, int viewtype, int position);

}
