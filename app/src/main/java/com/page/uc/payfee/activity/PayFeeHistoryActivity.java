package com.page.uc.payfee.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import com.framework.activity.BaseActivity;
import com.page.uc.payfee.fragment.FeeListFragment;
import com.page.uc.payfee.fragment.FeeMonthFragment;
import com.page.uc.payfee.fragment.WaitFeeFragment;
import com.qfant.wuye.R;

import net.lucode.hackware.magicindicator.FragmentContainerHelper;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by shucheng.qu on 2017/9/14.
 */

public class PayFeeHistoryActivity extends BaseActivity {

    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.vp_order_list)
    ViewPager vpOrderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_payfeehis_layout);
        ButterKnife.bind(this);
        setViewPager();
        setTitleBar("缴费列表", true);
    }

    /*
      * 1全部 2待付款 3待发货4待收货5退单
      * */
    private void setViewPager() {
        final ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new WaitFeeFragment());
        fragments.add(new FeeListFragment());
        fragments.add(new FeeMonthFragment());

        final ArrayList<String> titles = new ArrayList<>();
        titles.add("待缴费");
        titles.add("缴费订单");
        titles.add("已缴费月份");

        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return titles.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setNormalColor(getResources().getColor(R.color.pub_color_black));
                simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.pub_color_blue));
                simplePagerTitleView.setText(titles.get(index));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        vpOrderList.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
                linePagerIndicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                linePagerIndicator.setLineWidth(UIUtil.dip2px(context, 30));
                linePagerIndicator.setColors(getResources().getColor(R.color.pub_color_blue));
                return linePagerIndicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        final FragmentContainerHelper fragmentContainerHelper = new FragmentContainerHelper(magicIndicator);
        fragmentContainerHelper.setInterpolator(new OvershootInterpolator(2.0f));
        fragmentContainerHelper.setDuration(300);
        vpOrderList.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                fragmentContainerHelper.handlePageSelected(position);
            }
        });
        vpOrderList.setAdapter(new PagerAdapter((getContext()).getSupportFragmentManager(), fragments));

    }

    static class PagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> mFragments;

        public PagerAdapter(FragmentManager fm, List<Fragment> mFragments) {
            super(fm);
            this.mFragments = mFragments;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

    }

}
