package com.dhq.demo.cloudview;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.dhq.base.activity.BaseActivity;
import com.dhq.base.view.TagCloudView;
import com.dhq.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * DESC
 * Created by douhaoqiang on 2017/2/27.
 */
public class CloudViewActivity extends BaseActivity implements TagCloudView.OnTagListener<String> {
    private static final String TAG = "CloudViewActivity";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cloud_view;
    }

    @Override
    protected void initializes(Bundle savedInstanceState) {

        List<String> tags = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            tags.add("标签" + i);
        }

        TagCloudView tagCloudView0 = (TagCloudView) findViewById(R.id.tag_cloud_view_0);
        tagCloudView0.setTags(tags.subList(0, 4));
        tagCloudView0.setOnTagClickListener(this);

        final TagCloudView tagCloudView1 = (TagCloudView) findViewById(R.id.tag_cloud_view_1);
        tagCloudView1.setTags(tags);
        tagCloudView1.setOnTagClickListener(this);


        TagCloudView tagCloudView2 = (TagCloudView) findViewById(R.id.tag_cloud_view_2);
        tagCloudView2.setTags(tags);
        tagCloudView2.setOnTagClickListener(this);

        TagCloudView tagCloudView3 = (TagCloudView) findViewById(R.id.tag_cloud_view_3);
        tagCloudView3.setTags(tags);
        tagCloudView3.setOnTagClickListener(this);

        TagCloudView tagCloudView4 = (TagCloudView) findViewById(R.id.tag_cloud_view_4);
        tagCloudView4.setTags(tags);
        tagCloudView4.setOnTagClickListener(this);

        TagCloudView tagCloudView5 = (TagCloudView) findViewById(R.id.tag_cloud_view_5);
        tagCloudView5.setTags(tags);
        tagCloudView5.setOnTagClickListener(this);

        TagCloudView tagCloudView6 = (TagCloudView) findViewById(R.id.tag_cloud_view_6);
        tagCloudView6.setTags(tags);
        tagCloudView6.setOnTagClickListener(this);

        TagCloudView tagCloudView7 = (TagCloudView) findViewById(R.id.tag_cloud_view_7);
        tagCloudView7.setTags(tags);
        tagCloudView7.setOnTagClickListener(this);

        TagCloudView tagCloudView8 = (TagCloudView) findViewById(R.id.tag_cloud_view_8);
        tagCloudView8.setTags(tags);
        tagCloudView8.setOnTagClickListener(this);
    }

    @Override
    public void convertView(String data, TextView tagView) {
        tagView.setText(data);
    }

    @Override
    public void onTagClick(String data, int position) {
        Toast.makeText(getApplicationContext(), "点击 position : " + position,
                Toast.LENGTH_SHORT).show();
    }
}
