package com.dhq.filter.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * DESC 多布局Adapter
 * Created by douhaoqiang on 2017/2/21.
 */
public class FilterAdapter<T> extends BaseAdapter {

    private static final String TAG = "FilterAdapter";

    private List<T> mDatas;
    private Context mContext;
    private AdapterCallBack<T> mCallBack;


    public FilterAdapter(Context context,AdapterCallBack<T> callBack){
        this.mContext =context;
        this.mCallBack=callBack;
    }

    public void setDatas(List<T> datas){
        this.mDatas =datas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

//    //返回 代表某一个样式 的 数值
//    @Override
//    public int getItemViewType(int position) {
//        return super.getItemViewType(position);
//    }
//
//    //有几种item布局样式
//    @Override
//    public int getViewTypeCount() {
//        return 2;
//    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        T data = getItem(position);
        viewHolder = ViewHolder.getViewHolder(mContext,convertView,mCallBack.getItemLayId(data));

        mCallBack.getView(data,viewHolder);

        return convertView;
    }


    public interface AdapterCallBack<T>{
        int getItemLayId(T data);
        void getView(T data,ViewHolder viewHolder);

    }


}
