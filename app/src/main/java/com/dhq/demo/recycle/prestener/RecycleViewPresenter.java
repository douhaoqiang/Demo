package com.dhq.demo.recycle.prestener;

import android.content.Context;

import com.dhq.demo.recycle.bean.MyMessage;
import com.dhq.demo.recycle.contract.RecycleContract;
import com.dhq.demo.recycle.model.RecycleViewModel;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/18.
 */
public class RecycleViewPresenter extends RecycleContract.IRecyclePresenter {

    private Context mContext;
    private RecycleViewModel model = new RecycleViewModel();

    public RecycleViewPresenter(RecycleContract.IRecycleView view) {
        super(view);
    }


    /**
     * 获取列表数据
     */
    @Override
    public void getListData() {
        getView().loadingData();
        ArrayList<MyMessage> listData = model.getListData();
        getView().getDataSuccess(listData);
    }

}
