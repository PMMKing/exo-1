package com.framework.permission;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;


import com.framework.utils.ArrayUtils;
import com.framework.utils.QLog;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ironman.li flight_on 2015/10/29.
 */
public class SinglePermission {

    private SinglePermissionListener singlePermissionListener;
    private int currentRequestCode = -1;
    private static HashMap<String, String> permissionName = new HashMap<String, String>(8);

    static {
        permissionName.put(Manifest.permission.READ_CONTACTS, "读取联系人");
        permissionName.put(Manifest.permission.READ_PHONE_STATE, "读取手机状态");
        permissionName.put(Manifest.permission.CALL_PHONE, "拨打电话");
        permissionName.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, "读写sd卡");
        permissionName.put(Manifest.permission.WRITE_CALENDAR, "访问日历");
    }

    private String[] notHasPermission;

    public void requestPermission(Activity activity, int requestCode, SinglePermissionListener listener, String... permissions) {
        if (ArrayUtils.isEmpty(permissions) || listener == null) {
            return;
        }
        this.currentRequestCode = requestCode;
        this.singlePermissionListener = listener;
        notHasPermission = notHasPermission(activity, permissions);
        if (!ArrayUtils.isEmpty(notHasPermission)) {
            String[] showRequestPermission = showRequestPermission(activity, permissions);
            if (!ArrayUtils.isEmpty(showRequestPermission)) {
                showExplanation(activity, showRequestPermission, null);
            } else {
                ActivityCompat.requestPermissions(activity, notHasPermission, currentRequestCode);
            }
        } else {
            if (singlePermissionListener != null) {
                singlePermissionListener.onPermissionGranted(currentRequestCode, permissions);
            }
        }
    }

    public void requestPermission(Fragment fragment, int requestCode, SinglePermissionListener listener, String... permissions) {
        if (ArrayUtils.isEmpty(permissions) || listener == null || fragment.getActivity() == null) {
            return;
        }
        this.currentRequestCode = requestCode;
        this.singlePermissionListener = listener;
        notHasPermission = notHasPermission(fragment.getActivity(), permissions);
        //check if permission is granted
        if (!ArrayUtils.isEmpty(notHasPermission)) {
            String[] showRequestPermission = showRequestPermission(fragment.getActivity(), permissions);
            if (!ArrayUtils.isEmpty(showRequestPermission)) {
                showExplanation(fragment.getActivity(), permissions, fragment);
            } else {
                fragment.requestPermissions(permissions, currentRequestCode);
            }
        } else {
            if (singlePermissionListener != null) {
                singlePermissionListener.onPermissionGranted(currentRequestCode, permissions);
            }
        }
    }

    /**
     * you can overwrite this method if you want
     *
     * @param activity
     * @param permissions
     * @param fragment
     */
    protected void showExplanation(final Activity activity, final String[] permissions, final Fragment fragment) {
        if (!activity.isFinishing()) {
            StringBuilder stringBuilder = new StringBuilder().append("你所做的操作需要以下权限\n");
            for (String permission : permissions) {
                stringBuilder.append(permissionName.containsKey(permission) ? permissionName.get(permission) : permission).append("\n");
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setMessage(stringBuilder.toString());
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    tryAgain(activity, fragment);
                }
            });
            builder.setNegativeButton("取消", null);
            builder.show();
        }
    }


    public void tryAgain(Activity activity, Fragment fragment) {
        if (fragment != null && !ArrayUtils.isEmpty(notHasPermission)) {
            fragment.requestPermissions(notHasPermission, currentRequestCode);
        } else if (activity != null && !ArrayUtils.isEmpty(notHasPermission)) {
            ActivityCompat.requestPermissions(activity, notHasPermission, currentRequestCode);
        }

    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == currentRequestCode && !ArrayUtils.isEmpty(permissions)) {
            if (!ArrayUtils.isEmpty(grantResults) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (singlePermissionListener != null) {
                    singlePermissionListener.onPermissionGranted(requestCode, notHasPermission);
                }
            } else {
                if (singlePermissionListener != null) {
                    singlePermissionListener.onPermissionDenied(requestCode, notHasPermission);
                }
            }
        }
    }

    private String[] notHasPermission(Context context, String[] permissions) {
        ArrayList<String> notPermissions = new ArrayList<>();
        for (String permission : permissions) {
            try {
                if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    notPermissions.add(permission);
                }
            } catch (Throwable t) {
                QLog.e(String.format("Failure checking permission %s", permission), t);
            }
        }
        return notPermissions.toArray(new String[]{});
    }

    private String[] showRequestPermission(Activity activity, String[] permissions) {

        ArrayList<String> showRequestPermission = new ArrayList<>();
        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                showRequestPermission.add(permission);
            }
        }
        return showRequestPermission.toArray(new String[]{});


    }

}
