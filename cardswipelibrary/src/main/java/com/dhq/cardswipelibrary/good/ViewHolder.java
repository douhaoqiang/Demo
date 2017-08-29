package com.dhq.cardswipelibrary.good;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * DESC
 * Author douhaoqiang
 * Create by 2017/8/23.
 */

public class ViewHolder {

    //存放绑定的控件的容器
    private final SparseArray<View> mViews;
    //item视图
    private View mConvertView;

    private ViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
        this.mViews = new SparseArray<View>();
        //初始化视图
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        //绑定Tag
        mConvertView.setTag(this);
    }

    /**
     * 获取Viewholder的对象  为类的入口
     *
     * @param context     上下文
     * @param convertview item视图
     * @param layoutId    item布局的id
     * @param position    当前item的position
     */
    public static ViewHolder get(Context context, View convertview, ViewGroup parent, int layoutId, int position) {
        if (convertview == null) {
            return new ViewHolder(context, parent, layoutId, position);
        }
        return (ViewHolder) convertview.getTag();
    }

    /**
     * 通过控件的id获取对于控件，如果没有加入view
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertview() {
        return mConvertView;
    }


}
