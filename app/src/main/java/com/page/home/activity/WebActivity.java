package com.page.home.activity;

import android.os.Bundle;
import android.webkit.WebView;

import com.framework.activity.BaseActivity;
import com.framework.app.AppConstants;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenxi.cui on 2017/9/17.
 */

public class WebActivity extends BaseActivity {
    @BindView(R.id.web_view)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);
        webView.loadUrl(AppConstants.COMMON_URL + "/contact.do");
        setTitleBar("电话", true);
    }
}
