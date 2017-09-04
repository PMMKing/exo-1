package com.page.community.details.activity;

import android.os.Bundle;

import com.framework.activity.BaseActivity;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.page.community.details.model.RepairDetailParam;
import com.qfant.wuye.R;

/**
 * Created by shucheng.qu on 2017/8/12.
 */

public class DetailsActivity extends BaseActivity {

    public static String ID = "id";
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_details_layout);
        if (myBundle == null) {
            finish();
        }
        id = myBundle.getString(ID);
        startRequest();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        myBundle.putString(ID, id);
    }

    private void startRequest() {
        RepairDetailParam param = new RepairDetailParam();
        param.id = id;
        Request.startRequest(param, ServiceMap.getRepair, mHandler, Request.RequestFeature.BLOCK);
    }
}
