package com.dhq.mywidget.divider;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dhq.base.activity.BaseActivity;
import com.dhq.base.adapter.BaseRvAdapter;
import com.dhq.base.adapter.BaseRvHolder;
import com.dhq.base.util.LogUtil;
import com.dhq.mywidget.R;

import java.util.ArrayList;

public class DividerActivity extends BaseActivity {

    RecyclerView recyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_divider;
    }

    @Override
    protected void initializes(Bundle savedInstanceState) {

        recyclerView = (RecyclerView) findViewById(R.id.rv_recyclerview);
        findViewById(R.id.btn_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//
//        LinearDivider itemDecoration = DividerFactory.builder(this)
////                .setLeftMargin(R.dimen.divider_left)
////                .setRightMargin(R.dimen.divider_right)
////                .setTopMargin(R.dimen.divider_top)
////                .setBottomMargin(R.dimen.divider_bottom)
//                .setColor(R.color.divider, R.dimen.divider_stroke_width)
//                .setDrawable(R.drawable.divider)
////                .setHideLastDivider(false)
//                .buildLinearDivider();


        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        GridDivider itemDecoration = DividerFactory.builder(this)
                .setSpaceColor(R.color.divider, R.dimen.divider_stroke_width)
//                .setDrawable(R.drawable.divider)
//                .setHideLastDivider(false)
                .buildGridDivider();
        itemDecoration.addTo(recyclerView);


        BaseRvAdapter adapter = new BaseRvAdapter<String>(R.layout.item_recycler_view) {
            @Override
            public void convert(BaseRvHolder holder, String item, final int position) {

                holder.getRootView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LogUtil.d("选中第" + position + "项");
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
        ArrayList<String> arrayList = new ArrayList();
        arrayList.add("uio");
        arrayList.add("uio");
        arrayList.add("uio");
        arrayList.add("uio");
        arrayList.add("uio");
        arrayList.add("uio");
        arrayList.add("uio");
        arrayList.add("uio");
        adapter.setDatas(arrayList);


    }
}
