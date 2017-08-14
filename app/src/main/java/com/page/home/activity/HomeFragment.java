package com.page.home.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haolb.client.R;
import com.framework.activity.BaseFragment;

/**
 * Created by chenxi.cui on 2017/8/13.
 * 首页
 */

public class HomeFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pub_fragment_home_layout, null);
        return view;
    }
}
