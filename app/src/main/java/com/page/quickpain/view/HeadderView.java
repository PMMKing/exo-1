package com.page.quickpain.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.framework.activity.BaseActivity;
import com.haolb.client.R;
import com.page.quickpain.ScaleCircleNavigator;
import com.page.quickpain.fragment.ImageFragment;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.circlenavigator.CircleNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shucheng.qu on 2017/8/10.
 */

public class HeadderView extends LinearLayout {
    @BindView(R.id.vp_image)
    ViewPager vpImage;
    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.tv_msg)
    TextView tvMsg;

    public HeadderView(Context context) {
        this(context, null);
    }

    public HeadderView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeadderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LinearLayout.inflate(getContext(), R.layout.pub_activity_quickpain_header_layout, this);
        ButterKnife.bind(this);
        final ArrayList<Fragment> mTitleDataList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mTitleDataList.add(new ImageFragment());
        }

        ScaleCircleNavigator scaleCircleNavigator = new ScaleCircleNavigator(getContext());
        scaleCircleNavigator.setCircleCount(mTitleDataList.size());
        scaleCircleNavigator.setNormalCircleColor(getContext().getResources().getColor(R.color.pub_color_gray_999));
        scaleCircleNavigator.setSelectedCircleColor(getContext().getResources().getColor(R.color.pub_color_blue));
        scaleCircleNavigator.setCircleClickListener(new ScaleCircleNavigator.OnCircleClickListener() {
            @Override
            public void onClick(int index) {
                vpImage.setCurrentItem(index);
            }
        });
        magicIndicator.setNavigator(scaleCircleNavigator);
        ViewPagerHelper.bind(magicIndicator, vpImage);
        vpImage.setAdapter(new PagerAdapter(((BaseActivity) getContext()).getSupportFragmentManager(), mTitleDataList));
    }

    class PagerAdapter extends FragmentPagerAdapter {

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
