package com.dhq.filter.view;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.dhq.filter.R;
import com.dhq.filter.adapter.CommAdapter;
import com.dhq.filter.adapter.GoodsAttrsAdapter;
import com.dhq.filter.adapter.ViewHolder;
import com.dhq.filter.data.JsonData;
import com.dhq.filter.vo.SaleAttributeNameVo;
import com.dhq.filter.vo.SaleAttributeVo;

import java.util.ArrayList;
import java.util.List;


/**
 * 筛选商品属性选择的popupwindow
 */
public class FilterPopupWindow extends PopupWindow {
    private View contentView;
    private Context context;

    private GridView serviceGrid;
    private ListView selectionList;
    private TextView filterReset;
    private TextView filterSure;
    private CommAdapter adapterGoods;
    private GoodsAttrsAdapter serviceAdapter;
    private List<SaleAttributeNameVo> itemData;
    private List<SaleAttributeVo> serviceList;
    private String[] serviceStr = new String[]{"仅看有货", "促销", "手机专享"};

    /**
     * 商品属性选择的popupwindow
     */
    public FilterPopupWindow(final Activity context) {
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.popup_goods_details, null);

        serviceGrid = (GridView) contentView.findViewById(R.id.yuguo_service);

        selectionList = (ListView) contentView.findViewById(R.id.selection_list);

        filterReset = (TextView) contentView.findViewById(R.id.filter_reset);
        filterSure = (TextView) contentView.findViewById(R.id.filter_sure);

        serviceList = new ArrayList<SaleAttributeVo>();
        for (int i = 0; i < serviceStr.length; i++) {
            SaleAttributeVo vo = new SaleAttributeVo();
            vo.setValue(serviceStr[i]);
            serviceList.add(vo);
        }
        serviceAdapter = new GoodsAttrsAdapter(context);
        serviceGrid.setAdapter(serviceAdapter);
        serviceAdapter.notifyDataSetChanged(true, serviceList);
        serviceGrid.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //设置当前选中的位置的状态为非。
                serviceList.get(arg2).setChecked(!serviceList.get(arg2).isChecked());
                for (int i = 0; i < serviceList.size(); i++) {
                    //跳过已设置的选中的位置的状态
                    if (i == arg2) {
                        continue;
                    }
                    serviceList.get(i).setChecked(false);
                }
                serviceAdapter.notifyDataSetChanged(true, serviceList);
            }
        });

        itemData = new ArrayList<SaleAttributeNameVo>();

        adapterGoods = new CommAdapter<SaleAttributeNameVo>(context, new CommAdapter.AdapterCallBack<SaleAttributeNameVo>() {
            @Override
            public int getItemLayId(SaleAttributeNameVo data) {
                return R.layout.filter_grid_item;
            }

            @Override
            public void getView(final SaleAttributeNameVo data, ViewHolder viewHolder) {
                TextView name = (TextView) viewHolder.getView(R.id.filter_grid_item_title_tv);
                final ImageView img = (ImageView) viewHolder.getView(R.id.filter_grid_item_depend_iv);
                GridView grid = (GridView) viewHolder.getView(R.id.filter_grid_item_gridview);
                grid.setSelector(new ColorDrawable(Color.TRANSPARENT));

                name.setText(data.getName());
                final GoodsAttrsAdapter adapter = new GoodsAttrsAdapter(context);
                grid.setAdapter(adapter);
                adapter.notifyDataSetChanged(data.isNameIsChecked(), data.getSaleVo());
                img.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (data.isNameIsChecked()) {
                            ((ImageView) v).setImageResource(R.drawable.sort_common_up);
                        } else {
                            ((ImageView) v).setImageResource(R.drawable.sort_common_down);
                        }
                        adapter.notifyDataSetChanged(data.isNameIsChecked(), data.getSaleVo());
                        data.setNameIsChecked(!data.isNameIsChecked());
                    }
                });
                grid.setOnItemClickListener(new OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                        //设置当前选中的位置的状态为非。
                        data.getSaleVo().get(arg2).setChecked(!data.getSaleVo().get(arg2).isChecked());
                        for (int i = 0; i < data.getSaleVo().size(); i++) {
                            //跳过已设置的选中的位置的状态
                            if (i == arg2) {
                                continue;
                            }
                            data.getSaleVo().get(i).setChecked(false);
                        }
                        if (!data.isNameIsChecked()) {
                            img.setImageResource(R.drawable.sort_common_up);
                        } else {
                            img.setImageResource(R.drawable.sort_common_down);
                        }
                        adapter.notifyDataSetChanged(!data.isNameIsChecked(), data.getSaleVo());
                    }
                });
            }
        });

        selectionList.setAdapter(adapterGoods);

        //解析过滤数据
        itemData = JsonData.getDatas();
        adapterGoods.setDatas(itemData);

        // 重置的点击监听，将所有选项全设为false
        filterReset.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                for (int i = 0; i < itemData.size(); i++) {
                    for (int j = 0; j < itemData.get(i).getSaleVo().size(); j++) {
                        itemData.get(i).getSaleVo().get(j).setChecked(false);
                    }
                }
                adapterGoods.setDatas(itemData);
            }
        });
        // 确定的点击监听，将所有已选中项列出
        filterSure.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String str = "";
                for (int i = 0; i < itemData.size(); i++) {
                    for (int j = 0; j < itemData.get(i).getSaleVo().size(); j++) {
                        if (itemData.get(i).getSaleVo().get(j).isChecked()) {
                            str = str + itemData.get(i).getSaleVo().get(j).getValue();
                        }
                    }
                }
                Toast.makeText(FilterPopupWindow.this.context, str, Toast.LENGTH_SHORT).show();
            }
        });

        this.setContentView(contentView);
        this.setWidth(LayoutParams.MATCH_PARENT);
        this.setHeight(LayoutParams.MATCH_PARENT);
        this.setBackgroundDrawable(new ColorDrawable(00000000));
        this.setFocusable(true);
        this.setOutsideTouchable(false);
        this.update();

    }

    public void showFilterPopup(View parent) {
        if (!this.isShowing()) {
            this.showAsDropDown(parent);
        } else {
            this.dismiss();
        }
    }

}
