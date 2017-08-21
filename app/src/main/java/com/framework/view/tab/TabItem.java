package com.framework.view.tab;

import android.os.Bundle;

import com.framework.activity.BaseFragment;

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

    public TabItem(String text, Class<? extends BaseFragment> tagFragmentClz, Bundle bundle, int... icon) {
        this.icon = icon;
        this.text = text;
        this.tagFragmentClz = tagFragmentClz;
        this.bundle = bundle;
    }
}



