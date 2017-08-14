package com.page.home.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;


import com.framework.view.tab.TabItem;
import com.framework.view.tab.TabLayout;
import com.haolb.client.R;
import com.framework.activity.BaseActivity;
import com.framework.activity.BaseFragment;

import java.util.ArrayList;

/**
 * Created by shucheng.qu on 2017/5/31.
 */

public class MainTabActivity extends BaseActivity implements TabLayout.OnTabClickListener {

    protected final ArrayList<TabItem> mTabs = new ArrayList<TabItem>();

//    @BindView(R.id.tl_tab)
    TabLayout tabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tabLayout = (TabLayout) findViewById(R.id.title_bar);
    }

    protected void addTab(String text, Class<? extends BaseFragment> clss, Bundle bundle, int... icon) {
        TabItem tabItem = new TabItem(text, icon, clss, bundle);
        if (!mTabs.contains(tabItem)) {
            mTabs.add(tabItem);
        }
    }

    protected void onPostCreate() {
        tabLayout.initData(mTabs, this);
        tabLayout.setCurrentTab(0);
    }

    @Override
    public void onTabClick(TabItem tabItem) {
        try {
            BaseFragment fragment = tabItem.tagFragmentClz.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, fragment).commitAllowingStateLoss();
            tabLayout.setCurrentTab(mTabs.indexOf(tabItem));
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
