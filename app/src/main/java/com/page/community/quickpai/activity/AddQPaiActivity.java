package com.page.community.quickpai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.framework.activity.BaseActivity;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.view.AddView;
import com.page.community.quickpai.model.AddQpParam;
import com.page.home.model.QpListResult;
import com.page.home.model.QpListResult.Data.Snapshots;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by shucheng.qu on 2017/8/13.
 */

public class AddQPaiActivity extends BaseActivity {


    public static String SNAPSHOTS = "snapshots";

    @BindView(R.id.addView)
    AddView addView;
    @BindView(R.id.et_custom)
    EditText etCustom;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    private Snapshots snapshots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_addqpai_layout);
        ButterKnife.bind(this);
        snapshots = (Snapshots) myBundle.getSerializable(SNAPSHOTS);
        if (snapshots == null) {
            setTitleBar("发布随手拍", true);
            addView.setAddNumber(3);
        } else {
            setTitleBar("修改随手拍", true);
            addView.setAddNumber(new String[]{snapshots.pic1, snapshots.pic2, snapshots.pic3});
            etCustom.setText(snapshots.intro);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        myBundle.putSerializable(SNAPSHOTS, snapshots);
    }

    private void startRequest() {
        String[] imageUrls = addView.getImageUrls();
        String intro = etCustom.getText().toString();
        AddQpParam param = new AddQpParam();
        param.id = snapshots == null ? "" : snapshots.id;
        param.pic1 = imageUrls[0];
        param.pic2 = imageUrls[1];
        param.pic3 = imageUrls[2];
        param.intro = intro;
        if (snapshots == null) {
            Request.startRequest(param, ServiceMap.submitSnapshot, mHandler, Request.RequestFeature.BLOCK);
        } else {
            Request.startRequest(param, ServiceMap.updateSnapshot, mHandler, Request.RequestFeature.BLOCK);
        }

    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.uploadPic) {
            addView.onMsgSearchComplete(param);
        } else if (param.key == ServiceMap.submitSnapshot) {
            if (param.result.bstatus.code == 0) {
                finish();
            } else {
                showToast(param.result.bstatus.des);
            }
        } else if (param.key == ServiceMap.updateSnapshot) {
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
