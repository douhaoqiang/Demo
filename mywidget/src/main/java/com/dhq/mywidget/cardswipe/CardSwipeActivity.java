package com.dhq.mywidget.cardswipe;

import android.os.AsyncTask;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dhq.baselibrary.activity.BaseActivity;
import com.dhq.cardswipelibrary.CardViewAdapter;
import com.dhq.cardswipelibrary.SwipeFlingView;
import com.dhq.cardswipelibrary.ViewHolder;
import com.dhq.mywidget.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * DESC
 * Author douhaoqiang
 * Create by 2017/8/23.
 */

public class CardSwipeActivity extends BaseActivity implements SwipeFlingView.OnItemClickListener, View.OnClickListener{

    int [] headerIcons = {
            R.mipmap.i1,
            R.mipmap.i2,
            R.mipmap.i3,
            R.mipmap.i4,
            R.mipmap.i5,
            R.mipmap.i6
    };

    String [] names = {"张三","李四","王五","小明","小红","小花"};

    String [] citys = {"北京", "上海", "广州", "深圳"};

    String [] edus = {"大专", "本科", "硕士", "博士"};

    String [] years = {"1年", "2年", "3年", "4年", "5年"};

    Random ran = new Random();


    private SwipeFlingView swipeView;
    private CardViewAdapter adapter;



    @Override
    protected int getLayoutId() {
        return R.layout.activity_card_swipe;
    }

    @Override
    protected void initialize() {
        initView();
        loadData();
    }
    private void initView() {


        swipeView = (SwipeFlingView) findViewById(R.id.swipe_view);
        if (swipeView != null) {

            swipeView.setIsNeedSwipe(true);
            swipeView.setOnItemClickListener(this);

            adapter = new CardViewAdapter<Talent>(this, new CardViewAdapter.CardViewListener<Talent>() {
                @Override
                public int itemLayoutId() {
                    return R.layout.card_new_item;
                }

                @Override
                public void drawView(final Talent talent, int position, ViewHolder holder) {

                    ImageView portraitView = holder.getView(R.id.portrait);
                    TextView nameView = holder.getView(R.id.name);

                    TextView cityView =  holder.getView(R.id.city);
                    TextView eduView = holder.getView(R.id.education);
                    TextView workView = holder.getView(R.id.work_year);

                    portraitView.setImageResource(talent.headerIcon);

                    nameView.setText(String.format("%s", talent.nickname));
                    //holder.jobView.setText(talent.jobName);

                    final CharSequence no = "暂无";

                    cityView.setHint(no);
                    cityView.setText(talent.cityName);
                    cityView.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.home01_icon_location, 0, 0);

                    eduView.setHint(no);
                    eduView.setText(talent.educationName);
                    eduView.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.home01_icon_edu, 0, 0);

                    workView.setHint(no);
                    workView.setText(talent.workYearName);
                    workView.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.home01_icon_work_year, 0, 0);

                    portraitView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showToast("我选择了"+talent.nickname);
                        }
                    });
                }
            });
            swipeView.setAdapter(adapter);
        }

        findViewById(R.id.swipeLeft).setOnClickListener(this);

        findViewById(R.id.swipeRight).setOnClickListener(this);


    }


    @Override
    public void onItemClicked(MotionEvent event, View v, Object dataObject) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.swipeLeft:
                swipeView.swipeLeft();
                //swipeView.swipeLeft(250);
                break;
            case R.id.swipeRight:
                swipeView.swipeRight();
                //swipeView.swipeRight(250);
        }
    }

    private void loadData() {
        new AsyncTask<Void, Void, List<Talent>>() {
            @Override
            protected List<Talent> doInBackground(Void... params) {
                ArrayList<Talent> list = new ArrayList<>(10);
                Talent talent;
                for (int i = 0; i < 10; i++) {
                    talent = new Talent();
                    talent.headerIcon = headerIcons[i % headerIcons.length];
                    talent.nickname = names[ran.nextInt(names.length-1)];
                    talent.cityName = citys[ran.nextInt(citys.length-1)];
                    talent.educationName = edus[ran.nextInt(edus.length-1)];
                    talent.workYearName = years[ran.nextInt(years.length-1)];
                    list.add(talent);
                }
                return list;
            }

            @Override
            protected void onPostExecute(List<Talent> list) {
                super.onPostExecute(list);
                adapter.addAllData(list);
            }
        }.execute();
    }


    public static class Talent {
        public int headerIcon;
        public String nickname;
        public String cityName;
        public String educationName;
        public String workYearName;
    }

}
