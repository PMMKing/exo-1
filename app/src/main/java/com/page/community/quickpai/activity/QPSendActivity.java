package com.page.community.quickpai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.framework.activity.BaseActivity;
import com.framework.domain.param.BaseParam;
import com.framework.domain.response.BaseResult;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.utils.ArrayUtils;
import com.framework.utils.cache.ImageLoader;
import com.haolb.client.R;
import com.page.community.quickpain.activity.QuickPaiNActivity;
import com.page.uc.bean.UpdateMyPortraitParam;
import com.page.uc.bean.UpdateMyPortraitResult;
import com.page.uc.chooseavatar.OnChoosePictureListener;
import com.page.uc.chooseavatar.UpLoadHeadImageDialog;
import com.page.uc.chooseavatar.YCLTools;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.framework.net.Request.RequestFeature.BLOCK;
import static com.framework.net.Request.RequestFeature.CANCELABLE;

/**
 * Created by chenxi.cui on 2017/8/27.
 */

public class QPSendActivity extends BaseActivity {
    @BindView(R.id.ll_top)
    LinearLayout llTop;
    @BindView(R.id.ed_info)
    EditText edInfo;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.image_pic1)
    ImageView imagePic1;
    @BindView(R.id.image_pic2)
    ImageView imagePic2;
    @BindView(R.id.image_pic3)
    ImageView imagePic3;
    private int on1;
    private String file1;
    private String file2;
    private String file3;
    private int index;
    private int temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_qp_send_layout);
        ButterKnife.bind(this);
        setTitleBar("随手拍", true);
    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        if (ArrayUtils.isEmpty(arr)) {
            showToast("选择图片!");
            return;
        }
        int i = 0;
        temp = 0;
        for (String s : arr) {
            if (!TextUtils.isEmpty(s)) {
                i++;
                temp++;
                sendImage(new File(s), i);
            }
        }
    }

    private void sendInfo() {
        String s = edInfo.getText().toString();
        SnapshotParam param = new SnapshotParam();
        param.intro = s;
        param.pic1 = file1;
        param.pic2 = file2;
        param.pic3 = file3;
        Request.startRequest(param, ServiceMap.submitSnapshot, mHandler, BLOCK);
    }

    public static class SnapshotParam extends BaseParam {
        public String pic1;
        public String pic2;
        public String pic3;
        public String intro;
    }

    @OnClick({R.id.image_pic1, R.id.image_pic2, R.id.image_pic3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_pic1:
                on1 = 1;
                new UpLoadHeadImageDialog(this).show();
                break;
            case R.id.image_pic2:
                on1 = 2;
                new UpLoadHeadImageDialog(this).show();
                break;
            case R.id.image_pic3:
                on1 = 3;
                new UpLoadHeadImageDialog(this).show();
                break;
        }
    }

    String[] arr = new String[3];

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        YCLTools.getInstance().imageUrl(requestCode, resultCode, data, new OnChoosePictureListener() {
            @Override
            public void OnChoose(String filePath) {
                switch (on1) {
                    case 1:
                        arr[0] = filePath;
                        ImageLoader.getInstance(getContext()).loadImageFile(filePath, imagePic1);
                        break;
                    case 2:
                        arr[1] = filePath;
                        ImageLoader.getInstance(getContext()).loadImageFile(filePath, imagePic2);
                        break;
                    case 3:
                        arr[2] = filePath;
                        ImageLoader.getInstance(getContext()).loadImageFile(filePath, imagePic3);
                        break;
                }
            }

            @Override
            public void OnCancel() {

            }
        });
    }

    private void sendImage(File file, int i) {
        index = i;
        try {
            UpdateMyPortraitParam param = new UpdateMyPortraitParam();
            param.byteLength = file.length();
            param.ext = "jpg";
            NetworkParam np = Request.getRequest(param,
                    ServiceMap.uploadPic, new Request.RequestFeature[]{
                            BLOCK, CANCELABLE});
            np.progressMessage = "上传中......";
            np.filePath = file.getAbsolutePath();
            Request.startRequest(np, mHandler);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key.equals(ServiceMap.submitSnapshot)) {
            if (param.result.bstatus.code == 0) {
                showToast(param.result.bstatus.des);
                qStartActivity(QuickPaiNActivity.class);
            }
        } else if (param.key.equals(ServiceMap.uploadPic)) {
            if (param.result.bstatus.code == 0) {
                UpdateMyPortraitResult result = (UpdateMyPortraitResult) param.result;
                if (index == 1) {
                    file1 = result.data.url;
                } else if (index == 2) {
                    file2 = result.data.url;
                } else if (index == 3) {
                    file3 = result.data.url;
                }
            }
            temp--;
            if (temp == 0) {
                sendInfo();
            }
        }
        return super.onMsgSearchComplete(param);
    }
}
