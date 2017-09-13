package com.page.home.activity;

import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.widget.TextView;

import com.framework.activity.BaseActivity;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shucheng.qu on 2017/9/13.
 */

public class TextViewActivity extends BaseActivity {

    @BindView(R.id.tv_content)
    TextView tvContent;
    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_textview_layout);
        ButterKnife.bind(this);
        setTitleBar("通知详情", true);
        if (myBundle == null) finish();
        content = myBundle.getString("content");
        if (TextUtils.isEmpty(content)) {
            finish();
        }
        tvContent.setText(Html.fromHtml(content));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        myBundle.putString("content", content);
    }
}
