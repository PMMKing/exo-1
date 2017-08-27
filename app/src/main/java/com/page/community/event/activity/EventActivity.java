package com.page.community.event.activity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.framework.activity.BaseActivity;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.haolb.client.R;
import com.page.community.event.model.EventParam;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by shucheng.qu on 2017/8/11.
 */

public class EventActivity extends BaseActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_eventlaunch_layout);
        ButterKnife.bind(this);
        setTitleBar("活动", true);
    }

    private void startRequest() {

        String title = etTitle.getText().toString();
        String time = etEventTime.getText().toString();
        String address = etEventAddress.getText().toString();
        String persons = etEventPeople.getText().toString().trim();
        String details = etEventDetail.getText().toString().trim();

        EventParam param = new EventParam();
        param.title = title;
        param.time = time;
        param.islimit = 1;
        param.persons = 10;
        param.place = address;
        param.intro = details;
        Request.startRequest(param, ServiceMap.submitActivity, mHandler, Request.RequestFeature.BLOCK);

    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.submitActivity) {
            if (param.result.bstatus.code == 0) {
                finish();
            }
        }

        return false;

    }

    @OnClick(R.id.tv_commit)
    public void onViewClicked() {
        startRequest();
    }
}
