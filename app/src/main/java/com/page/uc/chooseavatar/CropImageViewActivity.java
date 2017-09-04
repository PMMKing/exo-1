package com.page.uc.chooseavatar;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;


import com.qfant.wuye.R;

import java.io.File;


public class CropImageViewActivity extends Activity {
    public static final String TAG = "YCL_CHOOSE_PICTURE";

    private static String YCL_FOLDER_PATH = Environment.getExternalStorageDirectory()
            .getAbsolutePath() + File.separator + "justravel/";
    private String TEMP_PIC_NAME = "temp_headImg";
    CropImageView cropImageView;
    TextView iv_cancel;
    TextView iv_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crop_imageview);
        init();
        String path = this.getIntent().getStringExtra("photo_path");
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        Bitmap bitmap =ImageTools.rotateBitmap(path, width,height);
        cropImageView.setImageBitmap(bitmap);
        cropImageView.setCropMode(CropImageView.CropMode.RATIO_1_1);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            PermissionHelper permissionHelper = new PermissionHelper();
//            permissionHelper.applyPermission(this, null,Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        }
    }


    private void init() {
        cropImageView= (CropImageView) findViewById(R.id.cropImageView);
        iv_cancel= (TextView) findViewById(R.id.iv_cancel);
        iv_ok= (TextView) findViewById(R.id.iv_ok);
        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( YCLTools.listener==null){
                    Log.e(TAG,"you should use the medthod YCLTools.getInstance.setOnChoosePictureListener() in your activity");
                }else{
                    YCLTools.listener.OnCancel();
                }

                CropImageViewActivity.this.finish();
            }
        });
        iv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap1 = cropImageView.getCroppedBitmap();
                File file = ImageTools.savePhotoToSDCard(bitmap1, YCL_FOLDER_PATH, TEMP_PIC_NAME);
                bitmap1.recycle();
                if( YCLTools.listener==null){
                    Log.e(TAG,"you should use the medthod YCLTools.getInstance.setOnChoosePictureListener() in your activity");
                }else{
                    YCLTools.listener.OnChoose(file.getAbsolutePath());
                }

                Log.e("test",file.getAbsolutePath());
                CropImageViewActivity.this.finish();
            }
        });
    }


}
