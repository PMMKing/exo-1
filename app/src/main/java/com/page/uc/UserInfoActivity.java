package com.page.uc;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.framework.activity.BaseActivity;
import com.framework.domain.param.BaseParam;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.utils.BitmapHelper;
import com.framework.utils.ShopCarUtils;
import com.framework.utils.cache.ImageLoader;
import com.framework.utils.imageload.ImageLoad;
import com.framework.view.CircleImageView;
import com.igexin.sdk.PushManager;
import com.page.home.activity.MainActivity;
import com.qfant.wuye.R;
import com.page.uc.bean.LoginResult;
import com.page.uc.bean.NickNameResult;
import com.page.uc.bean.UpdateMyPortraitParam;
import com.page.uc.bean.UpdateMyPortraitResult;
import com.page.uc.chooseavatar.OnChoosePictureListener;
import com.page.uc.chooseavatar.UpLoadHeadImageDialog;
import com.page.uc.chooseavatar.YCLTools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.framework.net.Request.RequestFeature.BLOCK;
import static com.framework.net.Request.RequestFeature.CANCELABLE;

/**
 * Created by chenxi.cui on 2017/8/13.
 */

public class UserInfoActivity extends BaseActivity {
    @BindView(R.id.image_head)
    CircleImageView imageHead;
    @BindView(R.id.ll_head)
    LinearLayout llHead;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.ll_nickname)
    LinearLayout llNickname;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.ll_sex)
    LinearLayout llSex;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.ll_phone)
    LinearLayout llPhone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_layout);
        ButterKnife.bind(this);
        setTitleBar("用户信息", true);
        YCLTools.getInstance().setOnChoosePictureListener(new OnChoosePictureListener() {
            @Override
            public void OnChoose(String filePath) {
                Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                saveAvatar(bitmap, filePath);
            }

            @Override
            public void OnCancel() {

            }
        });
        setData();
    }

    private void setData() {
        LoginResult.LoginData instance = UCUtils.getInstance().getUserInfo();
        ImageLoad.loadPlaceholder(this, instance.portrait, imageHead);
        tvPhone.setText(instance.phone);
        tvNickname.setText(instance.nickname);
//        tvSex.setText(instance.nickname);
        tvSex.setVisibility(View.GONE);
    }

    @OnClick({R.id.ll_head, R.id.ll_nickname, R.id.ll_sex, R.id.ll_phone, R.id.btn_logout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_head:
                new UpLoadHeadImageDialog(this).show();
                break;
            case R.id.ll_nickname:
                showNickName();
                break;
            case R.id.ll_sex:
                break;
            case R.id.ll_phone:
                break;
            case R.id.btn_logout:
                PushManager.getInstance().unBindAlias(getContext(), UCUtils.getInstance().getUserInfo().phone, false);
                UCUtils.getInstance().saveUserInfo(null);
                ShopCarUtils.getInstance().clearData();//清空购物车
                Intent intent = new Intent();
                intent.setClass(getContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                qStartActivity(intent);
                finish();
//                Request.startRequest(new BaseParam(), ServiceMap.customerLogout, mHandler);
                break;
        }
    }

    private void showNickName() {
        final EditText et = new EditText(this);
        et.setText(tvNickname.getText().toString());
        new AlertDialog.Builder(this).setTitle("昵称")
                .setView(et)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String input = et.getText().toString();
                        if (input.equals("")) {
                            Toast.makeText(getApplicationContext(), "输入内容不能为空！" + input, Toast.LENGTH_LONG).show();
                        } else {
                            NickNameParam param = new NickNameParam();
                            param.nickname = input;
                            Request.startRequest(param, input, ServiceMap.updateNickname, mHandler, BLOCK);
                        }
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    public static class NickNameParam extends BaseParam {

        public String nickname;
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {

        if (param.key.equals(ServiceMap.updateNickname)) {

            if (param.result.bstatus.code == 0) {
                UCUtils.getInstance().saveUsername((String) param.ext);
                showToast(param.result.bstatus.des);
                setData();
            } else {
                showToast(param.result.bstatus.des);
            }
        } else if (param.key.equals(ServiceMap.UPDATE_MY_PROTRAIT)) {
            UpdateMyPortraitResult portraitResult = (UpdateMyPortraitResult) param.result;
            if (portraitResult.bstatus.code == 0) {
                ImageLoader.getInstance(this).loadImage(portraitResult.data.portrait, imageHead, R.drawable.default_head);
                if (!TextUtils.isEmpty(portraitResult.data.portrait)) {
                    UCUtils.getInstance().savePortrait(portraitResult.data.portrait);
                }
            } else {
                showToast(param.result.bstatus.des);
            }
        }
        return super.onMsgSearchComplete(param);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        YCLTools.getInstance().upLoadImage(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void saveAvatar(final Bitmap bitmap, String filePath) {
        File mCurrentPhotoFile = new File(filePath);
        Bitmap bt = BitmapHelper.compressImage(bitmap);
        imageHead.setImageBitmap(bt);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mCurrentPhotoFile);
            bt.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            UpdateMyPortraitParam param = new UpdateMyPortraitParam();
            param.byteLength = mCurrentPhotoFile.length();
            param.ext = "jpg";
            NetworkParam np = Request.getRequest(param,
                    ServiceMap.UPDATE_MY_PROTRAIT, new Request.RequestFeature[]{
                            BLOCK, CANCELABLE});
            np.progressMessage = "上传中......";
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

}
