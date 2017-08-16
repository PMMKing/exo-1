package com.framework.rvadapter.click;

import android.view.View;


/**
 * Created by shucheng.qu on 2016/12/16.
 */

public interface OnItemClickListener<T> {
    void onItemClickListener(View view, T data, int position);
}
