package com.dhq.base.adapter;

import java.util.List;

/**
 * DESC RecycleView 的公用adapter
 * Created by douhaoqiang on 2016/9/6.
 */

public abstract class BaseRvAdapter<T> extends BaseRvManyAdapter {


    private int layoutId;

    /**
     * @param itemId 单个item的布局文件id
     */
    public BaseRvAdapter(int itemId) {
        this.layoutId = itemId;
    }

    @Override
    public int getViewResId(Object item) {
        return layoutId;
    }

    @Override
    public List<T> getDatas() {
        return super.getDatas();
    }

    @Override
    public void convert(BaseRvHolder holder, Object item, int viewtype, int position) {
        convert(holder, (T) item, position);
    }


    public abstract void convert(BaseRvHolder holder, T item, int position);

}
