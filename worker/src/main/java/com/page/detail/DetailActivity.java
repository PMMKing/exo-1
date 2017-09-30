package com.page.detail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.framework.activity.BaseActivity;
import com.framework.domain.param.UpdateMyPortraitParam;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.utils.BitmapHelper;
import com.framework.utils.cache.ImageLoader;
import com.haolb.client.R;
import com.page.chooseavatar.OnChoosePictureListener;
import com.page.chooseavatar.UpLoadHeadImageDialog;
import com.page.chooseavatar.YCLTools;
import com.page.detail.DetailResult.DetailData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.framework.net.Request.RequestFeature.BLOCK;
import static com.framework.net.Request.RequestFeature.CANCELABLE;

/**
 * Created by chenxi.cui on 2017/9/12.
 */

public class DetailActivity extends BaseActivity {
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.image_com)
    ImageView image_com;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.btn_detail)
    TextView btnDetail;
    @BindView(R.id.tv_intro)
    TextView tvIntro;
    @BindView(R.id.btn_start)
    Button btnStart;
    @BindView(R.id.btn_end)
    Button btnEnd;
    @BindView(R.id.image_big)
    ImageView imageBig;
    @BindView(R.id.ll_big)
    LinearLayout llBig;
    @BindView(R.id.ll_bi2)
    LinearLayout llBig2;
    @BindView(R.id.image_big2)
    ImageView imageBig2;
    private DetailData item;
    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        setTitleBar("详情", true);
        item = (DetailData) myBundle.getSerializable("repair");
        setData();
    }

    private void setData() {
        ImageLoader.getInstance(this).loadImage(item.pic, image, R.drawable.moren);
        ImageLoader.getInstance(this).loadImage(item.pic, imageBig, R.drawable.moren);
        llBig.setVisibility(View.GONE);
        tvAddress.setText(item.address);
        tvTitle.setText(item.phone);
        btnDetail.setText(item.statusCN);
        tvIntro.setText(item.intro);
        btnStart.setVisibility(View.GONE);
        btnEnd.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(item.endpic)) {
            ImageLoader.getInstance(getContext()).loadImage(item.endpic, image_com);
            ImageLoader.getInstance(getContext()).loadImage(item.endpic, imageBig2);
            image_com.setVisibility(View.VISIBLE);
        } else {
            image_com.setVisibility(View.GONE);
        }
        if (item.status == 3) {
            btnStart.setVisibility(View.VISIBLE);
            btnStart.setText("开始接单");
        } else if (item.status == 1 || item.status == 4) {
            btnStart.setVisibility(View.VISIBLE);
            btnStart.setText("开始处理");
        } else if (item.status == 5) {
            image_com.setVisibility(View.VISIBLE);
            btnStart.setVisibility(View.VISIBLE);
            btnStart.setText("处理完成");
        } else {
            btnStart.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.btn_start, R.id.btn_end, R.id.image_com})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                if (item.status == 3) {
                    DetailParam param = new DetailParam();
                    param.id = item.id;
                    Request.startRequest(param, ServiceMap.receiveRepair, mHandler, BLOCK);
                } else if (item.status == 1 || item.status == 4) {
                    DetailParam param = new DetailParam();
                    param.id = item.id;
                    Request.startRequest(param, ServiceMap.startRepair, mHandler, BLOCK);
                } else if (item.status == 5) {
                    if (TextUtils.isEmpty(mUrl)) {
                        showToast("请拍照确认");
                        return;
                    }
                    DetailParam param = new DetailParam();
                    param.id = item.id;
                    param.endpic = mUrl;
                    Request.startRequest(param, ServiceMap.endRepair, mHandler, BLOCK);
                }
                break;
            case R.id.btn_end:
                break;
            case R.id.image_com:
                if (item.status ==3||item.status ==5||item.status ==4||item.status ==1){
                    new UpLoadHeadImageDialog((BaseActivity) getContext()).show();
                }else {
                    if (llBig2.getVisibility() == View.GONE) {
                        llBig2.setVisibility(View.VISIBLE);
                    }
                }
                break;
        }
    }

    public String mFilePath;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        YCLTools.getInstance().imageUrl(requestCode, resultCode, data, new OnChoosePictureListener() {

            @Override
            public void OnChoose(String filePath) {
                ImageLoader.getInstance(getContext()).loadImageFile(filePath, image_com);
                ImageLoader.getInstance(getContext()).loadImageFile(filePath, imageBig2);
                mFilePath = filePath;
                sendImage(filePath);
            }

            @Override
            public void OnCancel() {

            }
        });
    }

    private void sendImage(String filePath) {
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        File mCurrentPhotoFile = new File(filePath);
        Bitmap bt = BitmapHelper.compressImage(bitmap);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mCurrentPhotoFile);
            bt.compress(Bitmap.CompressFormat.JPEG, 50, fos);
            fos.flush();
            fos.close();
            UpdateMyPortraitParam param = new UpdateMyPortraitParam();
            param.byteLength = mCurrentPhotoFile.length();
            param.ext = "jpg";
            NetworkParam np = Request.getRequest(param,
                    ServiceMap.uploadPic, new Request.RequestFeature[]{
                            BLOCK, CANCELABLE});
            np.progressMessage = "上传中......";
            np.ext = filePath;
            np.filePath = mCurrentPhotoFile.getAbsolutePath();
            Request.startRequest(np, mHandler);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                }
            }
        }
    }

    void reloadData() {
        DetailParam param = new DetailParam();
        param.id = item.id;
        Request.startRequest(param, ServiceMap.getRepair, mHandler, Request.RequestFeature.BLOCK);
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.receiveRepair) {
            if (param.result.bstatus.code == 0) {
                reloadData();
            }
            showToast(param.result.bstatus.des);
        } else if (param.key == ServiceMap.startRepair) {
            if (param.result.bstatus.code == 0) {
                reloadData();
            }
            showToast(param.result.bstatus.des);
        } else if (param.key == ServiceMap.endRepair) {
            if (param.result.bstatus.code == 0) {
                reloadData();
            }
            showToast(param.result.bstatus.des);
        } else if (param.key == ServiceMap.getRepair) {
            if (param.result.bstatus.code == 0) {
                DetailResult result = (DetailResult) param.result;
                item = result.data;
                setData();
            }
            showToast(param.result.bstatus.des);
        } else if (param.key == ServiceMap.uploadPic) {
            if (param.result.bstatus.code == 0) {
                UpdateMyPortraitResult result = (UpdateMyPortraitResult) param.result;
                mUrl = result.data.url;
                ImageLoader.getInstance(getContext()).loadImageFile((String) param.ext, image_com);
            }
        }
        return super.onMsgSearchComplete(param);
    }


    @OnClick({R.id.image, R.id.image_big, R.id.ll_big,R.id.image_big2, R.id.ll_bi2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image:
                if (llBig.getVisibility() == View.GONE) {
                    llBig.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.image_big:
            case R.id.ll_big:
                if (llBig.getVisibility() == View.VISIBLE) {
                    llBig.setVisibility(View.GONE);
                }
                break;

            case R.id.image_big2:
            case R.id.ll_bi2:
                if (llBig2.getVisibility() == View.VISIBLE) {
                    llBig2.setVisibility(View.GONE);
                }
                break;
        }
    }
}
