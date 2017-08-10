package com.page.fillmsg.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import com.haolb.client.R;
import com.haolb.client.activity.BaseActivity;

/**
 * Created by shucheng.qu on 2017/8/9.
 */

public class FillMsgActivity extends BaseActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.pub_activity_fillmsg_layout);

    }
}
