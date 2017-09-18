package com.page.uc.chooseavatar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;

public class UpLoadHeadImageDialog {

    private final FragmentActivity activity;
    private final AlertDialog.Builder builder;

    public UpLoadHeadImageDialog(final FragmentActivity activity) {
        builder = new AlertDialog.Builder(activity);
//        builder.setTitle("设置头像");
        String[] cities = new String[]{"相机", "相册"};
        builder.setItems(cities, new android.content.DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                switch (which) {
                    case 0:
                        YCLTools.getInstance().startChoose(activity, 0);
                        break;
                    case 1:
                        YCLTools.getInstance().startChoose(activity, 1);
                        break;
                    default:
                }

            }
        });
        this.activity = activity;
    }

    public void show() {
        if (builder != null) {
            builder.show();
        }
    }
    public void hide(){
        if (builder != null) {
            builder.create().hide();
        }
    }
}
