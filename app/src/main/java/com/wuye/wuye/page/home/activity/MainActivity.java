package com.wuye.wuye.page.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;


import com.wuye.wuye.R;
import com.wuye.wuye.ServiceID;
import com.wuye.wuye.base.FragmentOnBackListener;
import com.wuye.wuye.framework.view.tab.TabLayout;
import com.wuye.wuye.net.link.Request;
import com.wuye.wuye.net.link.param.NetworkParam;
import com.wuye.wuye.page.home.fragment.AccountFragment;
import com.wuye.wuye.page.home.fragment.HomeFragment;
import com.wuye.wuye.page.home.model.AccountParam;
import com.wuye.wuye.page.home.model.AccountResult;
import com.wuye.wuye.utils.UCUtils;
import com.wuye.wuye.utils.framwork.ArrayUtils;
import com.wuye.wuye.utils.storage.Store;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by shucheng.qu on 2017/5/27.
 */

public class MainActivity extends MainTabActivity {

    @BindView(R.id.tl_tab)
    TabLayout tlTab;
    private boolean mIsExit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        animType = CloseActivityAnim.FADE;
        setContentView(R.layout.pub_activity_mian_layout);
        ButterKnife.bind(this);
        tabLayout = tlTab;
//        setTitleBar("测试", true);
        addTab("首页", HomeFragment.class, myBundle, R.string.icon_font_home);
        addTab("我的", AccountFragment.class, myBundle, R.string.icon_font_my);
        onPostCreate();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
                    if (fragment.isVisible()) {
                        FragmentOnBackListener backListener = (FragmentOnBackListener) fragment;
                        if (backListener.onBackPressed()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onMsgSearchComplete(NetworkParam param) {
        super.onMsgSearchComplete(param);

    }

    @Override
    public void onNetEnd(NetworkParam param) {
        super.onNetEnd(param);
    }

}
