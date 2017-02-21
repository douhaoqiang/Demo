package com.dhq.filter.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * DESC
 * Created by douhaoqiang on 2017/2/21.
 */
public class CommAdapter<T> extends BaseAdapter {
    private static final String TAG = "CommAdapter";

    private List<T> mDatas=new ArrayList<>();
    private Context mContext;
    private AdapterCallBack<T> mCallBack;


    public CommAdapter(Context context,AdapterCallBack<T> callBack){
        this.mContext =context;
        this.mCallBack=callBack;
    }

    public void setDatas(List<T> datas){
        this.mDatas =datas;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

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

        return viewHolder.getRootView();
    }


    public interface AdapterCallBack<T>{
        int getItemLayId(T data);
        void getView(T data,ViewHolder viewHolder);
    }

}
