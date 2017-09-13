package com.page.store.shopcar.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.LinearLayout;

import com.framework.activity.BaseActivity;
import com.page.home.activity.ShoppingCartFragment;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shucheng.qu on 2017/9/13.
 */

public class ShopCarActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_shopcar_layout);
        ButterKnife.bind(this);
        setFragment();
    }

    private void setFragment() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        ShoppingCartFragment fragment = new ShoppingCartFragment();
        fragment.setArguments(bundle);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.ll_fragment, fragment);
        transaction.commitAllowingStateLoss();
    }

}
