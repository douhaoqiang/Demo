package com.dhq.popuplist;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Fussen on 16/8/31.
 */
public class DropDownMenu extends LinearLayout {

    //顶部菜单布局
    private LinearLayout tabMenuView;
    //现在选中的tab位置
    private int current_tab_position = -1;

    //分割线颜色
    private int dividerColor = 0xffcccccc;
    //tab选中颜色
    private int textSelectedColor = 0xff890c85;
    //tab未选中颜色
    private int textUnselectedColor = 0xff111111;
    //遮罩颜色
    private int maskColor = 0x88888888;
    //tab字体大小
    private int menuTextSize = 14;

    //tab选中图标
    private int menuSelectedIcon;
    //tab未选中图标
    private int menuUnselectedIcon;

    private LayoutInflater mInflater;

    private ListView mListView;

    private MenuListAdapter mAadapter;

    private PopupWindow mPopupWindow;

    private List<String[]> mDatas;

    public DropDownMenu(Context context) {
        super(context, null);
    }

    public DropDownMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DropDownMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setOrientation(VERTICAL);

        //为DropDownMenu添加自定义属性
        int menuBackgroundColor = 0xffffffff;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DropDownMenu);
        dividerColor = a.getColor(R.styleable.DropDownMenu_dividerColor, dividerColor);
        textSelectedColor = a.getColor(R.styleable.DropDownMenu_textSelectedColor, textSelectedColor);
        textUnselectedColor = a.getColor(R.styleable.DropDownMenu_textUnselectedColor, textUnselectedColor);
        menuBackgroundColor = a.getColor(R.styleable.DropDownMenu_menuBackgroundColor, menuBackgroundColor);
        maskColor = a.getColor(R.styleable.DropDownMenu_maskColor, maskColor);
        menuTextSize = a.getDimensionPixelSize(R.styleable.DropDownMenu_menuTextSize, menuTextSize);
        menuSelectedIcon = a.getResourceId(R.styleable.DropDownMenu_menuSelectedIcon, menuSelectedIcon);
        menuUnselectedIcon = a.getResourceId(R.styleable.DropDownMenu_menuUnselectedIcon, menuUnselectedIcon);
        a.recycle();

        mInflater = LayoutInflater.from(getContext());

        //初始化tabMenuView并添加到tabMenuView
        tabMenuView = new LinearLayout(context);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tabMenuView.setOrientation(HORIZONTAL);
        tabMenuView.setBackgroundColor(menuBackgroundColor);
        tabMenuView.setLayoutParams(params);
        addView(tabMenuView);

        popupInit();

    }


    /**
     * 初始化弹出框
     */
    private void popupInit(){
        //初始化下拉菜单
        View contentView = mInflater.inflate(R.layout.popup_content_lay, null);
        mListView= (ListView) contentView.findViewById(R.id.popup_content_list);
        mAadapter=new MenuListAdapter(getContext());
        mListView.setAdapter(mAadapter);
        listClickListener();


        mPopupWindow = new PopupWindow(contentView,LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);

        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(false);
        mPopupWindow.setFocusable(false);
//        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0x88ffffff));
        //指定弹出和消失的动画
//        mPopupWindow.setAnimationStyle(R.style.popwindow_anim_style);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                closeMenu();
            }
        });

    }

    /**
     * 下拉菜单的点击事件
     */
    private void listClickListener(){
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                setTabText(mDatas.get(current_tab_position)[position]);
                mPopupWindow.dismiss();
            }
        });
    }


    public void show() {
        mPopupWindow.showAsDropDown(this,0,0);
    }

    /**
     * 初始化DropDownMenu
     *
     * @param tabTexts tab标题数据
     * @param datas 列表数据
     */
    public void setDropDownMenu(@NonNull List<String> tabTexts, @NonNull List<String[]> datas) {

        for (int i = 0; i < tabTexts.size(); i++) {
            addTab(tabTexts, i);
        }

        this.mDatas=datas;

    }

    /**
     * 添加tab标签
     *
     * @param tabTexts tab文字
     * @param position tab位置
     */
    private void addTab(@NonNull List<String> tabTexts, final int position) {
        final View tabView = mInflater.inflate(R.layout.popup_tab_lay, null);
        tabView.setLayoutParams(new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));

        final TextView tabName = (TextView) tabView.findViewById(R.id.popup_tab_name_tv);
        final ImageView tabIv = (ImageView) tabView.findViewById(R.id.popup_tab_iv);

        tabName.setSingleLine();
        tabName.setEllipsize(TextUtils.TruncateAt.END);
        tabName.setGravity(Gravity.CENTER);
        tabName.setTextSize(TypedValue.COMPLEX_UNIT_PX, menuTextSize);
        tabName.setTextColor(textUnselectedColor);
        tabName.setText(tabTexts.get(position));
        tabIv.setImageResource(menuUnselectedIcon);


        tabView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                if(current_tab_position == -1){
                    tabName.setTextColor(textSelectedColor);
                    tabIv.setImageResource(menuSelectedIcon);
                    current_tab_position=position;
                    mAadapter.setData(Arrays.asList(mDatas.get(position)));
                    show();
                }else{

                    if(current_tab_position==position){
                        mPopupWindow.dismiss();
                    }else{
                        //设置之前的状态为未选中状态
                        TextView tabNameNow = (TextView) (tabMenuView.getChildAt(current_tab_position*2).findViewById(R.id.popup_tab_name_tv));
                        ImageView tabIvNow = (ImageView) (tabMenuView.getChildAt(current_tab_position*2).findViewById(R.id.popup_tab_iv));
                        tabNameNow.setTextColor(textUnselectedColor);
                        tabIvNow.setImageResource(menuUnselectedIcon);
                        //设置现在点击的状态为选中状态
                        tabName.setTextColor(textSelectedColor);
                        tabIv.setImageResource(menuSelectedIcon);
                        current_tab_position = position;
                        mAadapter.setData(Arrays.asList(mDatas.get(position)));
//                        show();
                    }
                }
            }
        });
        tabMenuView.addView(tabView);
        //添加分割线
        if (position < tabTexts.size() - 1) {
            View view = new View(getContext());
            LayoutParams layoutParams = new LayoutParams(dpTpPx(0.5f), ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.gravity = Gravity.CENTER_VERTICAL;
            view.setLayoutParams(layoutParams);
            view.setBackgroundColor(dividerColor);
            tabMenuView.addView(view);
        }
    }

    /**
     * 改变tab文字
     *
     * @param text
     */
    public void setTabText(String text) {

        if (current_tab_position != -1) {
            TextView tabName = (TextView) (tabMenuView.getChildAt(current_tab_position*2).findViewById(R.id.popup_tab_name_tv));
            tabName.setText(text);
        }
    }


    /**
     * 关闭菜单
     */
    public void closeMenu() {
        if (current_tab_position != -1) {
            final TextView tabName = (TextView) (tabMenuView.getChildAt(current_tab_position*2).findViewById(R.id.popup_tab_name_tv));
            final ImageView tabIv = (ImageView) (tabMenuView.getChildAt(current_tab_position*2).findViewById(R.id.popup_tab_iv));
            tabName.setTextColor(textUnselectedColor);
            tabIv.setImageResource(menuUnselectedIcon);
            current_tab_position = -1;
        }

    }



    public int dpTpPx(float value) {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, dm) + 0.5);
    }

}
