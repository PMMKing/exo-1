package com.page.community.event.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.framework.activity.BaseActivity;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.view.AddView;
import com.haolb.client.R;
import com.page.community.event.model.EventParam;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by shucheng.qu on 2017/8/11.
 */

public class EventActivity extends BaseActivity {

    public static String URL = "url";
    public static String ID = "id";

    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.et_event_time)
    EditText etEventTime;
    @BindView(R.id.et_event_address)
    EditText etEventAddress;
    @BindView(R.id.et_event_people)
    EditText etEventPeople;
    @BindView(R.id.cb_limit)
    CheckBox cbLimit;
    @BindView(R.id.et_event_detail)
    EditText etEventDetail;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    @BindView(R.id.addView)
    AddView addView;
    private String url;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_eventlaunch_layout);
        ButterKnife.bind(this);
        url = myBundle.getString(URL);
        id = myBundle.getString(ID);
        addView.setAddNumber(1);
        if (TextUtils.isEmpty(url)) {
            setTitleBar("活动发布", true);
        } else {
            setTitleBar("活动修改", true);
        }
        addView.setAddNumber(new String[]{url});

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        myBundle.putString(URL, url);
        myBundle.putString(ID, id);
    }

    private void startRequest() {

        String title = etTitle.getText().toString();
        String time = etEventTime.getText().toString();
        String address = etEventAddress.getText().toString();
        String persons = etEventPeople.getText().toString().trim();
        String details = etEventDetail.getText().toString().trim();
        String[] imageUrls = addView.getImageUrls();

        EventParam param = new EventParam();
        param.pic = imageUrls[0];
        param.title = title;
        param.time = time;
        param.islimit = 1;
        param.persons = 10;
        param.place = address;
        param.intro = details;
        param.id = id;
        if (TextUtils.isEmpty(url)) {
            Request.startRequest(param, ServiceMap.submitActivity, mHandler, Request.RequestFeature.BLOCK);
        } else {
            Request.startRequest(param, ServiceMap.updateActivity, mHandler, Request.RequestFeature.BLOCK);
        }

    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.submitActivity) {
            if (param.result.bstatus.code == 0) {
                finish();
            } else {
                showToast(param.result.bstatus.des);
            }
        } else if (param.key == ServiceMap.uploadPic) {
            addView.onMsgSearchComplete(param);
        } else if (param.key == ServiceMap.updateActivity) {
            if (param.result.bstatus.code == 0) {
                finish();
            } else {
                showToast(param.result.bstatus.des);
            }
        }

        return false;

    }

    @OnClick(R.id.tv_commit)
    public void onViewClicked() {
        startRequest();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        addView.onActivityResult(requestCode, resultCode, data);
    }
}
