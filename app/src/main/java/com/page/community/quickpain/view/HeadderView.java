package com.page.community.quickpain.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.framework.activity.BaseActivity;
import com.page.community.quickpain.ScaleCircleNavigator;
import com.page.community.quickpain.fragment.ImageFragment;
import com.page.community.quickpain.model.QpDetailResult;
import com.qfant.wuye.R;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;

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
    }

    public void updataView(QpDetailResult.Data hearderData) {
        if (hearderData == null) return;
        ArrayList<Fragment> mTitleDataList = new ArrayList<>();
        if (!TextUtils.isEmpty(hearderData.pic1)) {
            ImageFragment imageFragment = new ImageFragment();
            Bundle bundle = new Bundle();
            bundle.putString("imageUrl", hearderData.pic1);
            imageFragment.setArguments(bundle);
            mTitleDataList.add(imageFragment);
        }
        if (!TextUtils.isEmpty(hearderData.pic2)) {
            ImageFragment imageFragment = new ImageFragment();
            Bundle bundle = new Bundle();
            bundle.putString("imageUrl", hearderData.pic2);
            imageFragment.setArguments(bundle);
            mTitleDataList.add(imageFragment);
        }
        if (!TextUtils.isEmpty(hearderData.pic3)) {
            ImageFragment imageFragment = new ImageFragment();
            Bundle bundle = new Bundle();
            bundle.putString("imageUrl", hearderData.pic3);
            imageFragment.setArguments(bundle);
            mTitleDataList.add(imageFragment);
        }
        tvMsg.setText(hearderData.intro);
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
        PagerAdapter pagerAdapter = new PagerAdapter(((BaseActivity) getContext()).getSupportFragmentManager(), mTitleDataList);
        vpImage.setAdapter(pagerAdapter);
    }

    static class PagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> mFragments;

        public PagerAdapter(FragmentManager fm, List<Fragment> mFragments) {
            super(fm);
            this.mFragments = mFragments;
        }

        public void setData(List<Fragment> mFragments) {
            this.mFragments = mFragments;
            notifyDataSetChanged();
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
