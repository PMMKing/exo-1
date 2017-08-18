package com.page.uc;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.framework.activity.BaseActivity;
import com.haolb.client.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chenxi.cui on 2017/8/18.
 */

public class SelectComActivity extends BaseActivity {
    @BindView(R.id.text_0)
    TextView text0;
    @BindView(R.id.ll_0)
    LinearLayout ll0;
    @BindView(R.id.text_1)
    TextView text1;
    @BindView(R.id.ll_1)
    LinearLayout ll1;
    @BindView(R.id.text_2)
    TextView text2;
    @BindView(R.id.ll_2)
    LinearLayout ll2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_select_com_layout);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ll_0, R.id.ll_1, R.id.ll_2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_0:

                break;
            case R.id.ll_1:

                break;
            case R.id.ll_2:

                break;
        }
    }
}
