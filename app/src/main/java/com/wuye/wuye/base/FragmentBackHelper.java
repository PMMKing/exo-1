package com.wuye.wuye.base;



import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;


import com.wuye.wuye.utils.framwork.ArrayUtils;

import java.util.List;

/**
 * Created by shucheng.qu on 2017/2/24.
 */

public class FragmentBackHelper {

    public static boolean onBackPressed(FragmentManager fragmentManager) {

        List<Fragment> fragments = fragmentManager.getFragments();
        if (ArrayUtils.isEmpty(fragments)) {
            return false;
        }
        for (Fragment fragment : fragments) {
            if (fragmentOnBackPressed(fragment)) {
                return true;
            }
        }
        return false;
    }

    public static boolean onBackPressed(Fragment fragment) {
        return onBackPressed(fragment.getChildFragmentManager());
    }

    public static boolean onBackPressed(FragmentActivity fragmentActivity) {
        return onBackPressed(fragmentActivity.getSupportFragmentManager());
    }

    private static boolean fragmentOnBackPressed(Fragment fragment) {
        return fragment != null && fragment.isVisible() && fragment.getUserVisibleHint() && fragment instanceof FragmentOnBackListener && ((FragmentOnBackListener) fragment).onBackPressed();
    }

}
