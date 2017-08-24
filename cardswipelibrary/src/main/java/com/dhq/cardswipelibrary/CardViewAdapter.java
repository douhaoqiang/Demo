package com.dhq.cardswipelibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Collection;

/**
 * DESC
 * Author douhaoqiang
 * Create by 2017/8/23.
 */

public class CardViewAdapter<T> extends BaseAdapter {

    private Context mContext;

    private CardViewListener mListener;

    private ArrayList<T> objs;

    public CardViewAdapter(Context context, CardViewListener listener) {
        this.mContext = context;
        this.mListener = listener;
        objs = new ArrayList<>();


    }

    public void setData(Collection<T> collection) {
        objs.clear();
        objs.addAll(collection);
        notifyDataSetChanged();
    }

    public void addAllData(Collection<T> collection) {
        if (isEmpty()) {
            objs.addAll(collection);
            notifyDataSetChanged();
        } else {
            objs.addAll(collection);
        }
    }

    public void clear() {
        objs.clear();
        notifyDataSetChanged();
    }

    public boolean isEmpty() {
        return objs.isEmpty();
    }

    public void remove(int index) {
        if (index >= 0 && index < objs.size()) {
            objs.remove(index);
            notifyDataSetChanged();
        }
    }


    @Override
    public int getCount() {
        return objs.size();
    }

    @Override
    public T getItem(int position) {
        if (objs == null || objs.size() == 0) return null;
        return objs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (mListener != null) {
            T data = getItem(position);
            viewHolder = ViewHolder.get(mContext, convertView, parent, mListener.itemLayoutId(), position);
            mListener.drawView(data, position, viewHolder);
        }

        return viewHolder.getConvertview();
    }


    public interface CardViewListener<T> {

        /**
         * 获取子界面id
         *
         * @return
         */
        int itemLayoutId();

        /**
         * 绘制界面
         *
         * @param data
         * @param position
         * @param viewHolder
         *
         */
        void drawView(T data, int position, ViewHolder viewHolder);
    }


}