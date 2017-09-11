package com.framework.permission;

/**
 * Created by ironman.li flight_on 2015/10/29.
 */
public interface SinglePermissionListener {
    void onPermissionGranted(int requestCode, String[] permissions);
    void onPermissionDenied(int requestCode, String[] permissions);
}
