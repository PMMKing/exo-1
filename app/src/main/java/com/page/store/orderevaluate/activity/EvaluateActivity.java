package com.page.store.orderevaluate.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.framework.activity.BaseActivity;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.page.store.orderevaluate.model.EvaluateParam;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by shucheng.qu on 2017/9/17.
 */

public class EvaluateActivity extends BaseActivity {

    public static final String ID = "id";

    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.seekBar)
    RatingBar seekBar;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_evaluate_layout);
        ButterKnife.bind(this);
        if (myBundle == null) finish();
        id = myBundle.getString(ID);
        setTitleBar("评价", true);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        myBundle.putString(ID, id);
    }

    //1好评 2中评3差评
    @OnClick(R.id.tv_commit)
    public void onViewClicked() {
        String content = etContent.getText().toString().trim();
        float rating = seekBar.getRating();
        if (TextUtils.isEmpty(content)) {
            showToast("还没有写评价");
            return;
        }
        if (rating <= 0) {
            showTipText("还没有评分呢~");
            return;
        }
        EvaluateParam param = new EvaluateParam();
        param.id = id;
        param.comment = content;
        param.evaluate = rating == 3f ? 1 : rating == 2f ? 2 : 3;
        Request.startRequest(param, ServiceMap.orderEvaluate, mHandler, Request.RequestFeature.BLOCK);
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.orderEvaluate) {
            showToast(param.result.bstatus.des);
            if (param.result.bstatus.code == 0) {
                finish();
            }
        }
        return false;
    }
}
