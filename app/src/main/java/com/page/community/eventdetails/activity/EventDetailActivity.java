package com.page.community.eventdetails.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.framework.activity.BaseActivity;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.utils.DateFormatUtils;
import com.framework.utils.imageload.ImageLoad;
import com.framework.utils.viewutils.ViewUtils;
import com.page.community.eventdetails.adapter.ImagePagerAdapter;
import com.page.community.eventdetails.model.EventDetailParam;
import com.page.community.eventdetails.model.EventDetailsResult;
import com.page.community.eventdetails.model.JoinEventParam;
import com.page.community.signup.activity.SignupActivity;
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
    @BindView(R.id.tv_customername)
    TextView tvCustomername;
    @BindView(R.id.ll_customername)
    LinearLayout llCustomername;
    @BindView(R.id.line_customername)
    View lineCustomername;
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

    private void updataView(EventDetailsResult result) {
        if (result == null || result.data == null) return;
        EventDetailsResult.Data data = result.data;
        tvTitle.setText(data.title);
        tvTime.setText(DateFormatUtils.format(data.time, "yyyy.MM.dd EE"));
        tvAddress.setText(data.place);
        tvDetail.setText(data.intro);
        ImageLoad.loadPlaceholder(getContext(), data.pic, ivImage);
        tvJoin.setVisibility(View.VISIBLE);
        refreshJoin(data.ismine == 1 ? 2 : data.isjoin == 0 ? 0 : 1);
        ViewUtils.setOrGone(llCustomername, /*data.ismine == 1 &&*/ !TextUtils.isEmpty(data.customername));
        ViewUtils.setOrGone(lineCustomername, /*data.ismine == 1 && */!TextUtils.isEmpty(data.customername));
        tvCustomername.setText(data.customername);
    }

    private void refreshJoin(int isjoin) {

        switch (isjoin) {
            case 0:
                tvJoin.setText("参与活动");
                tvJoin.setTag(0);
                break;
            case 1:
                tvJoin.setText("取消参与");
                tvJoin.setTag(1);
                break;
            case 2:
                tvJoin.setText("查看参与人员");
                tvJoin.setTag(2);
                break;
        }
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
                refreshJoin(1);
            } else {
                showToast(param.result.bstatus.des);
            }
        } else if (param.key == ServiceMap.canceljoinActivity) {
            if (param.result.bstatus.code == 0) {
                showToast("成功取消参与活动");
                refreshJoin(0);
            } else {
                showToast(param.result.bstatus.des);
            }
        }
        return false;
    }

    @OnClick(R.id.tv_join)
    public void onViewClicked() {
        int tag = (int) tvJoin.getTag();
        switch (tag) {
            case 0:
                joinEvent();
                break;
            case 1:
                cancleJoinEvent();
                break;
            case 2:
                Bundle bundle = new Bundle();
                bundle.putString(SignupActivity.ID, id);
                qStartActivity(SignupActivity.class, bundle);
                break;
        }
    }
}
