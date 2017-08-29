package com.dhq.cardswipelibrary.good;

import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xmuSistone on 2017/7/5.
 */

public abstract class CardAdapter<T> {

    private final DataSetObservable mDataSetObservable = new DataSetObservable();

    private List<T> mDatas = new ArrayList<>();

    public void addDatas(List<T> datas) {
        this.mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void setDatas(List<T> datas) {
        this.mDatas.clear();
        this.mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    /**
     * item数量，调用者必须实现
     */
    public int getCount() {
        return mDatas.size();
    }


    /**
     * item数量，调用者必须实现
     */
    public T getItem(int position) {
        return mDatas.get(position);
    }

    public void registerDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.registerObserver(observer);
    }

    public void unregisterDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.unregisterObserver(observer);
    }

    public void notifyDataSetChanged() {
        mDataSetObservable.notifyChanged();
    }


    /**
     * layout文件ID，调用者必须实现
     */
    public abstract int getLayoutId();

    /**
     * 绘制item界面
     */
    public abstract void convertView(T data, ViewGroup convertView,int position);
}
