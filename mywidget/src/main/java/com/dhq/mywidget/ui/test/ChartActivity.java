package com.dhq.mywidget.ui.test;

import android.widget.FrameLayout;

import com.bjgoodwill.familyproduct.R;

import java.util.ArrayList;
import java.util.List;

import bjgoodwill.baselibrary.activity.BaseActivity;

/**
 * DESC
 * Created by douhaoqiang on 2018/5/25.
 */

public class ChartActivity extends BaseActivity {

    private FrameLayout chart;

    @Override
    protected int getLayout() {
        return R.layout.activity_chart_test;
    }

    @Override
    protected void initializeData() {
        chart = findViewById(R.id.chart_container);
//        initBarChart1();
        initBarChart2();
//        initBarChart3();
    }

    @Override
    protected void initializeOnclick() {

    }


    private void initBarChart1() {
        String[] xLabel = {"计算机二级", "熟练操作", "基本操作"};
        String title="计算机水平";
        int yUnit = 5;
        int[] data1 = {11, 5, 4};
        List<Integer> colors = new ArrayList<>();
        colors.add(R.color.AliceBlue);
        chart.addView(new CustomBarChart(this, xLabel, yUnit, data1,title));
    }



    private void initBarChart2() {
        String[] xLabel = {"主硬盘", "移动硬盘", "U盘","光盘"};
        String title="储存介质";
        int yUnit = 3;
        int[] data1 = {4, 8, 5,3};
        List<Integer> colors = new ArrayList<>();
        colors.add(R.color.AliceBlue);
        chart.addView(new CustomBarChart(this, xLabel, yUnit, data1,title));
    }


    private void initBarChart3() {
        String[] xLabel = {"品牌", "价格", "实用性"};
        String title="选择财务依据";
        int yUnit = 4;
        int[] data1 = {7,3,10};
        List<Integer> colors = new ArrayList<>();
        colors.add(R.color.AliceBlue);
        chart.addView(new CustomBarChart(this, xLabel, yUnit, data1,title));
    }


}
