package com.dhq.mywidget.ui;

import android.os.Bundle;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.dhq.base.activity.BaseActivity;
import com.dhq.base.adapter.BaseRvAdapter;
import com.dhq.base.adapter.BaseRvHolder;
import com.dhq.mywidget.R;
import com.dhq.mywidget.layoutmanger.SimpleLayoutManager;

import java.util.ArrayList;

/**
 * DESC
 * Created by douhaoqiang on 2018/7/9.
 */
public class LayoutMangerTestActivity extends BaseActivity {

    private RecyclerView mRv;
    private BaseRvAdapter<String> mAdapter;

    private ArrayList<String> mDatas = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_layoutmanger_test;
    }

    @Override
    protected void initializes(Bundle savedInstanceState) {

        mRv = findViewById(R.id.rv_test);

        for (int i = 0; i < 5; i++) {
            mDatas.add(i + "11");
        }

        mRv.setLayoutManager(new SimpleLayoutManager(this, true));
//        mRv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(mRv);


        mAdapter = new BaseRvAdapter<String>(R.layout.item_recycler_view) {
            @Override
            public void convert(BaseRvHolder holder, String item, int position) {
                TextView tvName = holder.getView(R.id.tv_test);
                tvName.setText("position" + position);
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                int realPosition = position % mDatas.size();
                convert((BaseRvHolder) holder, mDatas.get(realPosition), realPosition);
            }

//            @Override
//            public int getItemCount() {
//                return Integer.MAX_VALUE;
//            }
        };
        mRv.setAdapter(mAdapter);

        mAdapter.setDatas(mDatas);

    }
}
