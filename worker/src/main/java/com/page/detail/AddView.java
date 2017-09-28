package com.page.detail;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.framework.activity.BaseActivity;
import com.framework.domain.param.UpdateMyPortraitParam;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.utils.BitmapHelper;
import com.framework.utils.Dimen;
import com.framework.utils.cache.ImageLoader;
import com.haolb.client.R;
import com.page.chooseavatar.OnChoosePictureListener;
import com.page.chooseavatar.UpLoadHeadImageDialog;
import com.page.chooseavatar.YCLTools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.framework.net.Request.RequestFeature.BLOCK;
import static com.framework.net.Request.RequestFeature.CANCELABLE;

/**
 * Created by shucheng.qu on 2017/8/11.
 */

public class AddView extends LinearLayout implements View.OnClickListener {

    @BindView(R.id.ll_add)
    LinearLayout llAdd;
    private int id = 0;
    private String[] imageUrls;
    private boolean isClick = true;

    public AddView(Context context) {
        this(context, null);
    }

    public AddView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AddView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LinearLayout.inflate(getContext(), R.layout.pub_activity_addview_layout, this);
        ButterKnife.bind(this);
        setBackgroundColor(Color.WHITE);
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        int widthPixels = displayMetrics.widthPixels;
        int width = (widthPixels - Dimen.dpToPx(70)) / 4;
        int height = width + Dimen.dpToPx(40);
        LayoutParams layoutParams = (LayoutParams) llAdd.getLayoutParams();
        layoutParams.width = LayoutParams.MATCH_PARENT;
        layoutParams.height = height;
        llAdd.setLayoutParams(layoutParams);

//        YCLTools.getInstance().setOnChoosePictureListener(new OnChoosePictureListener() {
//            @Override
//            public void OnChoose(String filePath) {
//                sendImage(filePath);
//            }
//
//            @Override
//            public void OnCancel() {
//
//            }
//        });
    }

    public void setAddNumber(int number) {
        number = Math.min(number, 4);
        imageUrls = new String[number];
        llAdd.removeAllViews();
        for (int i = 0; i < number; i++) {
            ImageView imageView = new ImageView(getContext());
            LayoutParams layoutParams = new LayoutParams(0, LayoutParams.MATCH_PARENT);
            layoutParams.weight = 1;
            int dp7 = Dimen.dpToPx(7);
            layoutParams.setMargins(dp7, dp7, dp7, dp7);
            imageView.setLayoutParams(layoutParams);
            imageView.setClickable(true);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setImageResource(R.drawable.pub_add_picture);
            imageView.setOnClickListener(this);
            imageView.setTag(i);
            llAdd.addView(imageView);
        }
    }

    public void setAddNumber(String[] imageUrls) {
        int number = Math.min(imageUrls.length, 4);
        this.imageUrls = new String[number];
        llAdd.removeAllViews();
        for (int i = 0; i < number; i++) {
            this.imageUrls[i] = imageUrls[i];
            ImageView imageView = new ImageView(getContext());
            LayoutParams layoutParams = new LayoutParams(0, LayoutParams.MATCH_PARENT);
            layoutParams.weight = 1;
            int dp7 = Dimen.dpToPx(7);
            layoutParams.setMargins(dp7, dp7, dp7, dp7);
            imageView.setLayoutParams(layoutParams);
            imageView.setClickable(true);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            if (TextUtils.isEmpty(imageUrls[i])) {
                imageView.setImageResource(R.drawable.pub_add_picture);
            } else {
                ImageLoader.getInstance(getContext()).loadImage(imageUrls[i], imageView);
            }
            imageView.setOnClickListener(this);
            imageView.setTag(i);
            llAdd.addView(imageView);
        }
    }

    public void setClickable(boolean isClick) {
        this.isClick = isClick;
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
            Request.startRequest(np, ((BaseActivity) getContext()).mHandler);
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

    public String[] getImageUrls() {
        return imageUrls;
    }

    @Override
    public void onClick(View v) {
        if (!isClick) return;
        id = (int) v.getTag();
        new UpLoadHeadImageDialog((BaseActivity) getContext()).show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        YCLTools.getInstance().imageUrl(requestCode, resultCode, data, new OnChoosePictureListener() {
            @Override
            public void OnChoose(String filePath) {
//                ImageLoader.getInstance(getContext()).loadImageFile(filePath, (ImageView) llAdd.getChildAt(id));
                sendImage(filePath);
            }

            @Override
            public void OnCancel() {

            }
        });

//        YCLTools.getInstance().upLoadImage(requestCode, resultCode, data);

    }


    public void onMsgSearchComplete(NetworkParam param) {
        if (param.result.bstatus.code == 0) {
            UpdateMyPortraitResult result = (UpdateMyPortraitResult) param.result;
            imageUrls[id] = result.data.url;
            ImageLoader.getInstance(getContext()).loadImageFile((String) param.ext, (ImageView) llAdd.getChildAt(id));
        }
    }
}
