package com.page.community.quickpain.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;

import com.framework.activity.BaseFragment;
import com.framework.utils.html.ZoomImageView;
import com.framework.utils.imageload.ImageLoad;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by shucheng.qu on 2017/8/11.
 */

public class ImageFragment extends BaseFragment {

    @BindView(R.id.iv_image)
    ImageView ivImage;
    Unbinder unbinder;
    private String imageUrl;

    private PopupWindow popupWindow;
    //需要放大的图片
    private ZoomImageView tecent_chat_image;
    //加载中的进度条
    private ProgressBar image_scale_progress;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pub_fragment_quickpain_header_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imageUrl = myBundle.getString("imageUrl");
        ImageLoad.loadPlaceholder(getContext(), imageUrl, ivImage);
        initPopWindow();
    }

    private void initPopWindow() {
        View popView = LayoutInflater.from(getContext()).inflate(R.layout.pub_zoom_popwindow_layout, null);
        tecent_chat_image = (ZoomImageView) popView.findViewById(R.id.image_scale_image);
        image_scale_progress = (ProgressBar) popView.findViewById(R.id.image_scale_progress);

        popView.findViewById(R.id.image_scale_rll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }
        });
        popupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);// 设置允许在外点击消失
        ColorDrawable dw = new ColorDrawable(0x50000000);
        popupWindow.setBackgroundDrawable(dw);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        myBundle.putString("imageUrl", imageUrl);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.iv_image)
    public void onViewClicked() {
        if (TextUtils.isEmpty(imageUrl)) return;
        image_scale_progress.setVisibility(View.GONE);
        popupWindow.showAtLocation(ivImage, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        ImageLoad.loadPlaceholder(getContext(), imageUrl, tecent_chat_image);
    }

}
