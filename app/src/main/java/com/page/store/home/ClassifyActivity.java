package com.page.store.home;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.framework.activity.BaseActivity;
import com.page.store.home.fragment.ClassifyFragment;
import com.qfant.wuye.R;

/**
 * Created by chenxi.cui on 2017/9/16.
 */

public class ClassifyActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_classify);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        ClassifyFragment fragment = new ClassifyFragment();
        fragmentTransaction.replace(R.id.fl_fragment, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }
}
