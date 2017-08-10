package com.framework.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;


import com.framework.rvadapter.adapter.MultiAdapter;
import com.framework.rvadapter.click.OnItemClickListener;
import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.rvadapter.manage.ITypeView;
import com.haolb.client.R;

import java.util.ArrayList;

/**
 * Created by shucheng.qu on 2017/8/7.
 */

public class ListDialog<T> extends Dialog implements OnItemClickListener<Integer> {
    private final Context mContext;
    RecyclerView rcvListDialog;
    private int widthPixels;
    private int heightPixels;

    public ListDialog(@NonNull Context context) {
        this(context, 0);

    }

    public ListDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        widthPixels = displayMetrics.widthPixels;
        heightPixels = displayMetrics.heightPixels;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.pub_dialog_listdialog_layout);
//        getWindow().setWindowAnimations(R.style.atom_PopupAnimation);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
//        attributes.dimAmount = 0.3f;
        attributes.width = (int) (0.9*widthPixels);
        getWindow().setAttributes(attributes);
        rcvListDialog = (RecyclerView) findViewById(R.id.rcv_list_dialog);
        rcvListDialog.addItemDecoration(new LineDecoration(mContext));
        setListView();

        rcvListDialog.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
//                rcvListDialog.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int measuredHeight = rcvListDialog.getMeasuredHeight();
                if (measuredHeight > heightPixels * 0.8) {
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) rcvListDialog.getLayoutParams();
                    layoutParams.height = (int) (heightPixels * 0.8);
                    layoutParams.width = FrameLayout.LayoutParams.MATCH_PARENT;
                    rcvListDialog.setLayoutParams(layoutParams);
                }
            }
        });

    }


    private void setListView() {

        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }
        MultiAdapter adapter = new MultiAdapter<Integer>(getContext(), list).addTypeView(new ITypeView<Integer>() {
            @Override
            public boolean isForViewType(Integer item, int position) {
                return true;
            }

            @Override
            public BaseViewHolder createViewHolder(Context mContext, ViewGroup parent) {
                return new ViewHolder(mContext, LayoutInflater.from(mContext).inflate(R.layout.pub_dialog_listdialog_item_layout, parent, false));
            }
        });
        rcvListDialog.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rcvListDialog.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClickListener(View view, Integer data, int position) {

    }


    private class ViewHolder extends BaseViewHolder<Integer> {

        public ViewHolder(Context context, View itemView) {
            super(context, itemView);
        }

        @Override
        public void onBindViewHolder(BaseViewHolder holder, Integer data, int position) {

        }
    }

}
