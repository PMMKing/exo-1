package com.page.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.framework.utils.ArrayUtils;
import com.framework.view.tab.TabItem;
import com.framework.view.tab.TabLayout;
import com.haolb.client.R;
import com.page.community.quickpai.activity.QPSendActivity;
import com.page.community.quickpai.activity.QuickPaiActivity;
import com.page.community.quickpain.activity.QuickPaiNActivity;
import com.page.uc.UserCenterFragment;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by shucheng.qu on 2017/5/27.
 */

public class MainActivity extends MainTabActivity {

    @BindView(R.id.tl_tab)
    TabLayout tlTab;
    @BindView(R.id.image_pai)
    ImageView imagePai;
    private boolean mIsExit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_mian_layout);
        ButterKnife.bind(this);
        tabLayout = tlTab;
        addTab("主页", HomeFragment.class, myBundle, R.string.icon_font_home);
        addTab("商城", ShoppingFragment.class, myBundle, R.string.icon_font_home);
        addTab("随手拍", null, myBundle, R.string.icon_font_home);
        addTab("购物车", ShoppingCartFragment.class, myBundle, R.string.icon_font_home);
        addTab("我的", UserCenterFragment.class, myBundle, R.string.icon_font_home);
        onPostCreate();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onTabClick(TabItem tabItem) {
        if ("随手拍".equals(tabItem.text)) {
            qStartActivity(QPSendActivity.class);
        } else {
            super.onTabClick(tabItem);
        }
    }

    @OnClick(R.id.image_pai)
    public void onViewClicked() {
        qStartActivity(QPSendActivity.class);
    }

    @Override
    public void onBackPressed() {
        if (!onBackPressedWithFragment()) {
            exitBy2Click();
        }
    }

    public void exitBy2Click() {
        Timer tExit;
        if (!mIsExit) {
            mIsExit = true;
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    mIsExit = false;
                }
            }, 2000);
        } else {
            finish();
        }
    }

    boolean onBackPressedWithFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager != null) {
            List<Fragment> fragments = fragmentManager.getFragments();
            if (!ArrayUtils.isEmpty(fragments)) {
                for (Fragment fragment : fragments) {
                    if (fragment == null) {
                        return false;
                    }
//                    if (fragment.isVisible()) {
//                        FragmentOnBackListener backListener = (FragmentOnBackListener) fragment;
//                        if (backListener.onBackPressed()) {
//                            return true;
//                        }
//                    }
                }
            }
        }
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }


}
