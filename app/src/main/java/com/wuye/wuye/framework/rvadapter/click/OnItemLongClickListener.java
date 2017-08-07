package com.wuye.wuye.framework.rvadapter.click;

import android.view.View;


/**
 * Created by shucheng.qu on 2016/12/16.
 */

public interface OnItemLongClickListener<T> {

    boolean onItemLongClickListener(View view, T data, int position);

}
