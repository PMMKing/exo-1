package com.wuye.wuye.framework.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.wuye.wuye.R;
import com.wuye.wuye.framework.rvadapter.adapter.MultiAdapter;
import com.wuye.wuye.framework.rvadapter.click.OnItemClickListener;
import com.wuye.wuye.framework.rvadapter.holder.BaseViewHolder;
import com.wuye.wuye.framework.rvadapter.manage.ITypeView;

import java.util.ArrayList;

/**
 * Created by shucheng.qu on 2017/8/7.
 */

public class ListDialog<T> extends Dialog implements OnItemClickListener<Integer> {
    private final Context mContext;
    RecyclerView rcvListDialog;

    public ListDialog(@NonNull Context context) {
        this(context, 0);

    }

    public ListDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        this.mContext = context;
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
//        getWindow().setWindowAnimations(R.style.atom_PopupAnimation);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.dimAmount = 0.3f;
        getWindow().setAttributes(attributes);
        setContentView(R.layout.pub_dialog_listdialog_layout);
        rcvListDialog = (RecyclerView) findViewById(R.id.rcv_list_dialog);
        rcvListDialog.addItemDecoration(new LineDecoration(mContext));
        setListView();

    }


    private void setListView() {

        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
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
