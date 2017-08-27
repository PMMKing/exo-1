package com.page.community.eventdetails.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.framework.activity.BaseActivity;
import com.framework.domain.param.BaseParam;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.haolb.client.R;
import com.page.community.eventdetails.adapter.ImagePagerAdapter;
import com.page.community.eventdetails.fragment.ImageFragment;
import com.page.community.quickpain.ScaleCircleNavigator;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shucheng.qu on 2017/8/25.
 */

public class EventDetailActivity extends BaseActivity {


    @BindView(R.id.vp_image)
    ViewPager vpImage;
    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_join_number)
    TextView tvJoinNumber;
    @BindView(R.id.tv_join)
    TextView tvJoin;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_detail)
    TextView tvDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_eventdetail_layout);
        ButterKnife.bind(this);
        startRequest();
        setViewPager();
    }

    private void startRequest() {
        Request.startRequest(new BaseParam(), ServiceMap.getActivity, mHandler);
    }

    private void setViewPager() {
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
        vpImage.setAdapter(new ImagePagerAdapter(((BaseActivity) getContext()).getSupportFragmentManager(), mTitleDataList));
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {

        return false;
    }
}
