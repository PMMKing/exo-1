package com.page.community.eventdetails.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.TextView;

import com.framework.activity.BaseActivity;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.utils.DateFormatUtils;
import com.framework.utils.imageload.ImageLoad;
import com.page.community.eventdetails.adapter.ImagePagerAdapter;
import com.page.community.eventdetails.model.EventDetailParam;
import com.page.community.eventdetails.model.EventDetailsResult;
import com.page.community.eventdetails.model.JoinEventParam;
import com.qfant.wuye.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by shucheng.qu on 2017/8/25.
 */

public class EventDetailActivity extends BaseActivity {

    public static String ID = "id";

    //    @BindView(R.id.vp_image)
//    ViewPager vpImage;
//    @BindView(R.id.magic_indicator)
//    MagicIndicator magicIndicator;
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
    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private String id;
    private ImagePagerAdapter imagePagerAdapter;
    private ArrayList<Fragment> mTitleDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_eventdetail_layout);
        ButterKnife.bind(this);
        if (myBundle == null) {
            finish();
        }
        id = myBundle.getString(ID);
        startRequest();
//        setViewPager();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        myBundle.putString(ID, id);
    }

    private void startRequest() {
        EventDetailParam param = new EventDetailParam();
        param.id = id;
        Request.startRequest(param, ServiceMap.getActivity, mHandler, Request.RequestFeature.BLOCK);
    }

//    private void setViewPager() {
//        mTitleDataList = new ArrayList<>();
//        ScaleCircleNavigator scaleCircleNavigator = new ScaleCircleNavigator(getContext());
//        scaleCircleNavigator.setCircleCount(mTitleDataList.size());
//        scaleCircleNavigator.setNormalCircleColor(getContext().getResources().getColor(R.color.pub_color_gray_999));
//        scaleCircleNavigator.setSelectedCircleColor(getContext().getResources().getColor(R.color.pub_color_blue));
//        scaleCircleNavigator.setCircleClickListener(new ScaleCircleNavigator.OnCircleClickListener() {
//            @Override
//            public void onClick(int index) {
//                vpImage.setCurrentItem(index);
//            }
//        });
//        magicIndicator.setNavigator(scaleCircleNavigator);
//        ViewPagerHelper.bind(magicIndicator, vpImage);
//        imagePagerAdapter = new ImagePagerAdapter(((BaseActivity) getContext()).getSupportFragmentManager(), mTitleDataList);
//        vpImage.setAdapter(imagePagerAdapter);
//    }


    private void updataView(EventDetailsResult result) {
        if (result == null || result.data == null) return;
        EventDetailsResult.Data data = result.data;
        tvTitle.setText(data.title);
        tvJoinNumber.setText(data.persons);
        tvTime.setText(DateFormatUtils.format(data.time, "yyyy.MM.dd EE"));
        tvAddress.setText(data.place);
        tvDetail.setText(data.customername);
        ImageLoad.loadPlaceholder(getContext(), data.pic, ivImage);
    }


    private void joinEvent() {
        JoinEventParam param = new JoinEventParam();
        param.id = id;
        Request.startRequest(param, ServiceMap.joinActivity, mHandler, Request.RequestFeature.BLOCK);
    }

    private void cancleJoinEvent() {
        JoinEventParam param = new JoinEventParam();
        param.id = id;
        Request.startRequest(param, ServiceMap.canceljoinActivity, mHandler, Request.RequestFeature.BLOCK);
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.getActivity) {
            EventDetailsResult result = (EventDetailsResult) param.result;
            updataView(result);
        } else if (param.key == ServiceMap.joinActivity) {
            if (param.result.bstatus.code == 0) {
                showToast("成功参与活动");
                startRequest();
            } else {
                showToast(param.result.bstatus.des);
            }
        } else if (param.key == ServiceMap.canceljoinActivity) {
            if (param.result.bstatus.code == 0) {
                showToast("成功取消参与活动");
                startRequest();
            } else {
                showToast(param.result.bstatus.des);
            }
        }
        return false;
    }

    @OnClick(R.id.tv_join)
    public void onViewClicked() {
        //三种
    }
}
