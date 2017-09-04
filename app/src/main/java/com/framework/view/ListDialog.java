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
import android.widget.TextView;


import com.framework.domain.param.BaseParam;
import com.framework.rvadapter.adapter.MultiAdapter;
import com.framework.rvadapter.click.OnItemClickListener;
import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.rvadapter.manage.ITypeView;
import com.qfant.wuye.R;
import com.page.uc.bean.ComBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shucheng.qu on 2017/8/7.
 */

public class ListDialog<T> extends Dialog implements OnItemClickListener<ComBean> {
    private final Context mContext;
    RecyclerView rcvListDialog;
    private int widthPixels;
    private int heightPixels;
    private OnCellClick onCellClick;
    private int type = -1;

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


    public void setListView(List<ComBean> list) {

        MultiAdapter adapter = new MultiAdapter<ComBean>(getContext(), list).addTypeView(new ITypeView<ComBean>() {
            @Override
            public boolean isForViewType(ComBean item, int position) {
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
    public void onItemClickListener(View view, ComBean data, int position) {
        if (onCellClick != null) {
            dismiss();
            onCellClick.onCellClick(data, position);
        }
    }

    public void setOnCellClick(OnCellClick onCellClick) {
        this.onCellClick = onCellClick;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public interface OnCellClick{
        void onCellClick(ComBean baseParam, int pos);
    }

    private class ViewHolder extends BaseViewHolder<ComBean> {

        public ViewHolder(Context context, View itemView) {
            super(context, itemView);
        }

        @Override
        public void onBindViewHolder(BaseViewHolder holder, ComBean data, int position) {
            TextView textView = (TextView) holder.itemView.findViewById(R.id.text_0);
            textView.setText(data.name);
        }
    }

}
