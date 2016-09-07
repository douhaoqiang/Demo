package com.dhq.demo.recycle.prestener;

import android.content.Context;

import com.dhq.baselibrary.presenter.BasePresenter;
import com.dhq.demo.recycle.bean.MyMessage;
import com.dhq.demo.recycle.model.RecycleViewModel;
import com.dhq.demo.recycle.view.IRecycleView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/18.
 */
public class RecycleViewPresenter extends BasePresenter<IRecycleView> {
    private Context mContext;
    private RecycleViewModel model = new RecycleViewModel();

    public RecycleViewPresenter(Context context) {
        this.mContext = context.getApplicationContext();
    }

    /**
     * 获取列表数据
     */
    public void getListData(){
        getView().loadingData();
        ArrayList<MyMessage> listData = model.getListData();
        getView().getDataSuccess(listData);
    }


}
