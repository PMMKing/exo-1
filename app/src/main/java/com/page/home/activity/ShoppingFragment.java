package com.page.home.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.framework.activity.BaseFragment;
import com.framework.view.tab.TabItem;
import com.page.store.home.fragment.ClassifyFragment;
import com.page.store.home.fragment.ShopHomeFragment;
import com.page.uc.UserCenterFragment;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by chenxi.cui on 2017/8/13.
 */

public class ShoppingFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.rb_home)
    RadioButton rbHome;
    @BindView(R.id.rb_class)
    RadioButton rbClass;
    @BindView(R.id.rg_group)
    RadioGroup rgGroup;
    @BindView(R.id.fl_fragment)
    FrameLayout flFragment;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pub_fragment_shopping_layout, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setTabBar();
    }

    private void setTabBar() {
        rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                switch (checkedId) {
                    case R.id.rb_home:
                        ShopHomeFragment fragment = new ShopHomeFragment();
                        fragmentTransaction.replace(R.id.fl_fragment, fragment);
                        break;
                    case R.id.rb_class:
                        ClassifyFragment fragment3 = new ClassifyFragment();
                        fragmentTransaction.replace(R.id.fl_fragment, fragment3);
                        break;
                }
                fragmentTransaction.commitAllowingStateLoss();
            }
        });
        rbHome.setChecked(true);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        ShopHomeFragment fragment = new ShopHomeFragment();
        fragmentTransaction.replace(R.id.fl_fragment, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
