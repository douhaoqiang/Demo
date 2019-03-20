package com.dhq.mywidget.cardswipe;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dhq.base.activity.BaseActivity;
import com.dhq.cardswipelibrary.good.CardAdapter;
import com.dhq.cardswipelibrary.good.CardSlideLayout;
import com.dhq.mywidget.R;

import java.util.ArrayList;
import java.util.List;

import static com.dhq.cardswipelibrary.good.CardSlideLayout.VANISH_TYPE_LEFT;

/**
 * DESC
 * Author douhaoqiang
 * Create by 2017/8/23.
 */

public class CardSwipeActivity extends BaseActivity {

    private CardSlideLayout.CardSwitchListener cardSwitchListener;

    private String imagePaths[] = {"file:///android_asset/wall01.jpg",
            "file:///android_asset/wall02.jpg", "file:///android_asset/wall03.jpg",
            "file:///android_asset/wall04.jpg", "file:///android_asset/wall05.jpg",
            "file:///android_asset/wall06.jpg", "file:///android_asset/wall07.jpg",
            "file:///android_asset/wall08.jpg", "file:///android_asset/wall09.jpg",
            "file:///android_asset/wall10.jpg", "file:///android_asset/wall11.jpg",
            "file:///android_asset/wall12.jpg"}; // 12个图片资源

    private String names[] = {"郭富城", "刘德华", "张学友", "李连杰", "成龙", "谢霆锋", "李易峰",
            "霍建华", "胡歌", "曾志伟", "吴孟达", "梁朝伟"}; // 12个人名

    private List<CardDataItem> dataList = new ArrayList<>();

    private CardAdapter<CardDataItem> cardAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_card_swipe;
    }

    @Override
    protected void initializes(Bundle savedInstanceState) {

        initView();
    }

    private void initView() {
        final CardSlideLayout slidePanel = (CardSlideLayout) findViewById(R.id.image_slide_panel);

        // 1. 左右滑动监听
        cardSwitchListener = new CardSlideLayout.CardSwitchListener() {

            @Override
            public void onShow(int index) {
                Log.d("Card", "正在显示-" + cardAdapter.getItem(index).userName);
            }

            @Override
            public void onCardVanish(int index, int type) {
                Log.d("Card", "正在消失-" + cardAdapter.getItem(index).userName + " 消失type=" + type);
            }
        };
        slidePanel.setCardSwitchListener(cardSwitchListener);


        // 2. 绑定Adapter
        prepareDataList();

        cardAdapter = new CardAdapter<CardDataItem>() {
            @Override
            public int getLayoutId() {
                return R.layout.card_item;
            }

            @Override
            public void convertView(CardDataItem data, ViewGroup convertView, int position) {
                Object tag = convertView.getTag();
                ViewHolder viewHolder;
                if (null != tag) {
                    viewHolder = (ViewHolder) tag;
                } else {
                    viewHolder = new ViewHolder(convertView);
                    convertView.setTag(viewHolder);
                }

                viewHolder.bindData(data);
            }

        };

        cardAdapter.setDatas(dataList);


        slidePanel.setAdapter(cardAdapter);

        // 添加新的卡片
        findViewById(R.id.remove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidePanel.vanishOnBtnClick(VANISH_TYPE_LEFT);
            }
        });
        // 添加新的卡片
        findViewById(R.id.notify_change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendDataList();
            }
        });
    }

    private void prepareDataList() {
        int num = imagePaths.length;
        for (int i = 0; i < num; i++) {
            CardDataItem dataItem = new CardDataItem();
            dataItem.userName = names[i];
            dataItem.imagePath = imagePaths[i];
            dataItem.likeNum = (int) (Math.random() * 10);
            dataItem.imageNum = (int) (Math.random() * 6);
            dataList.add(dataItem);
        }
    }

    private void appendDataList() {
        List<CardDataItem> list = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            CardDataItem dataItem = new CardDataItem();
            dataItem.userName = "From Append";
            dataItem.imagePath = imagePaths[8];
            dataItem.likeNum = (int) (Math.random() * 10);
            dataItem.imageNum = (int) (Math.random() * 6);
            list.add(dataItem);
        }
        cardAdapter.addDatas(list);
    }

    class ViewHolder {

        ImageView imageView;
        View maskView;
        TextView userNameTv;
        TextView imageNumTv;
        TextView likeNumTv;

        public ViewHolder(View view) {
            imageView = (ImageView) view.findViewById(R.id.card_image_view);
            maskView = view.findViewById(R.id.maskView);
            userNameTv = (TextView) view.findViewById(R.id.card_user_name);
            imageNumTv = (TextView) view.findViewById(R.id.card_pic_num);
            likeNumTv = (TextView) view.findViewById(R.id.card_like);
        }

        public void bindData(CardDataItem itemData) {

            Glide.with(CardSwipeActivity.this).load(itemData.imagePath).into(imageView);
            userNameTv.setText(itemData.userName);
            imageNumTv.setText(itemData.imageNum + "");
            likeNumTv.setText(itemData.likeNum + "");
        }
    }

}
