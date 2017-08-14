package com.framework.view.tab;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by shucheng.qu on 2017/8/14.
 */

public class QuickPaiTabView extends LinearLayout {
    public QuickPaiTabView(Context context) {
        this(context, null);
    }

    public QuickPaiTabView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QuickPaiTabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
