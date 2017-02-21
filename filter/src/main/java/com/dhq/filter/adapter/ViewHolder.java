package com.dhq.filter.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * DESC
 * Created by douhaoqiang on 2017/2/21.
 */
public class ViewHolder {
    private static final String TAG = "ViewHolder";

    //存放绑定的控件的容器
    private final SparseArray<View> mViews;
    //item视图
    private View mConvertView;

    private ViewHolder(Context context, int layoutId) {
        this.mViews = new SparseArray<View>();
        //初始化视图
        mConvertView = LayoutInflater.from(context).inflate(layoutId, null);
        //绑定Tag
        mConvertView.setTag(this);
    }

    /**
     * 获取Viewholder的对象  为类的入口
     *
     * @param context     上下文
     * @param convertview item视图
     * @param layoutId    item布局的id
     */
    public static ViewHolder getViewHolder(Context context, View convertview, int layoutId) {
        if (convertview == null) {
            return new ViewHolder(context,layoutId);
        }
        return (ViewHolder) convertview.getTag();
    }


    public View getRootView(){

        return mConvertView;
    }

    public View getView(int id){

        View view = null;
        if(mViews.get(id)==null){
            view = mConvertView.findViewById(id);
            mViews.put(id,view);
        }else{
            view=mViews.get(id);
        }

        return view;
    }

}
