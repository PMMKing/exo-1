package com.page.home.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haolb.client.R;
import com.haolb.client.activity.BaseFragment;

/**
 * Created by chenxi.cui on 2017/8/13.
 */

public class MycommunityFragment extends BaseFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.pub_fragment_mycom_layout, null);
    }
}
