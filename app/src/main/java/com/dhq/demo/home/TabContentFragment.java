package com.dhq.demo.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dhq.demo.R;
import com.dhq.demo.recycle.adapter.RecycleViewAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by douhaoqiang on 2016/8/24.
 */

public class TabContentFragment extends Fragment {

    @BindView(R.id.home_first_recycleview)
    RecyclerView recycleview;
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        recycleview.setLayoutManager(new LinearLayoutManager(getContext()));
        recycleview.setAdapter(new RecycleViewAdapter());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }
}
