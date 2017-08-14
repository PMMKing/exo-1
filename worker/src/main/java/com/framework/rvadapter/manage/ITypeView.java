package com.framework.rvadapter.manage;


import android.content.Context;
import android.view.ViewGroup;

import com.framework.rvadapter.holder.BaseViewHolder;


/**
 * Created by shucheng.qu on 2016/12/16.
 */

public interface ITypeView<T> {

    boolean isForViewType(T item, int position);

    BaseViewHolder createViewHolder(Context mContext, ViewGroup parent);
}
