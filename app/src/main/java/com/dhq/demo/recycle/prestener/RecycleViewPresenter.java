package com.dhq.demo.recycle.prestener;

import android.content.Context;

import com.dhq.baselibrary.presenter.BasePresenter;
import com.dhq.demo.home.model.HomeMode;
import com.dhq.demo.home.view.HomeView;
import com.dhq.demo.recycle.view.IRecycleView;

/**
 * Created by Administrator on 2016/8/18.
 */
public class RecycleViewPresenter extends BasePresenter<IRecycleView> {
    private Context mContext;
    private HomeMode mHomeMode = new HomeMode();

    public RecycleViewPresenter(Context context) {
        this.mContext = context.getApplicationContext();
    }


}
