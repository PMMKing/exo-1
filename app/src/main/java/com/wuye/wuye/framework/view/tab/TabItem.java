package com.wuye.wuye.framework.view.tab;

import android.os.Bundle;

import com.wuye.wuye.base.BaseFragment;

/**
 */
public class TabItem {

    private final Bundle bundle;
    /**
     * icon
     */
    public int[] icon;
    /**
     * 文本
     */
    public String text;


    public Class<? extends BaseFragment> tagFragmentClz;

    public TabItem(String text, int[] icon, Class<? extends BaseFragment> tagFragmentClz, Bundle bundle) {
        this.icon = icon;
        this.text = text;
        this.tagFragmentClz = tagFragmentClz;
        this.bundle = bundle;
    }
}



