package com.framework.permission;

import android.app.Activity;
import android.support.v4.app.Fragment;

/**
 * Created by shucheng.qu on 2017/3/10.
 */

public class PermissionHelper {

    private SinglePermission singlePermission;

    public PermissionHelper() {
        singlePermission = new SinglePermission();
    }

    public interface IPermission {
        void onConfirm();

        void onCancel();
    }


    public void applyPermission(final Activity activity, int requestCode, final IPermission iPermission, String... permissions) {
        singlePermission.requestPermission(activity, requestCode, new SinglePermissionListener() {
            @Override
            public void onPermissionGranted(int requestCode, String[] permissions) {
                if (iPermission != null) {
                    iPermission.onConfirm();
                }
            }

            @Override
            public void onPermissionDenied(int requestCode, String[] permissions) {
//                StringBuilder sb = new StringBuilder();
//                if (!ArrayUtils.isEmpty(permissions)) {
//                    for (String permission : permissions) {
//                        sb.append(permission).append("\n");
//                    }
//                    sb.append("权限获取失败！");
//                }
                PermissionToastUtils.showToast(activity, "权限获取失败！");
                if (iPermission != null) {
                    iPermission.onCancel();
                }
            }
        }, permissions);
    }

    public void applyPermission(final Fragment fragment, int requestCode, final IPermission iPermission, String... permissions) {
        singlePermission.requestPermission(fragment, requestCode, new SinglePermissionListener() {
            @Override
            public void onPermissionGranted(int requestCode, String[] permissions) {
                if (iPermission != null) {
                    iPermission.onConfirm();
                }
            }

            @Override
            public void onPermissionDenied(int requestCode, String[] permissions) {
//                StringBuilder sb = new StringBuilder();
//                if (!ArrayUtils.isEmpty(permissions)) {
//                    for (String permission : permissions) {
//                        sb.append(permission).append("\n");
//                    }
//                    sb.append("权限获取失败！");
//                }
                PermissionToastUtils.showToast(fragment.getContext(), "权限获取失败！");
                if (iPermission != null) {
                    iPermission.onCancel();
                }
            }
        }, permissions);
    }


    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        singlePermission.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
