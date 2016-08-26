package com.dhq.demo.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.TextView;

import com.dhq.demo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by douhaoqiang on 2016/8/24.
 */

public class TabContentFragment extends Fragment {

    @BindView(R.id.fragment_tv)
    TextView settingTv;
    private Unbinder bind;

    public static Fragment newInstance(String title) {
        return new TabContentFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        bind = ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }
}