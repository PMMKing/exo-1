package com.page.store.prodetails.holder;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.utils.ArrayUtils;
import com.framework.utils.BusinessUtils;
import com.framework.utils.html.HtmlUtils;
import com.framework.utils.html.ZoomImageView;
import com.framework.utils.imageload.ImageLoad;
import com.framework.utils.viewutils.ViewUtils;
import com.framework.view.sivin.Banner;
import com.framework.view.sivin.BannerAdapter;
import com.page.store.prodetails.model.PDResult.Data;
import com.page.store.prodetails.model.PEResult;
import com.qfant.wuye.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by shucheng.qu on 2017/8/16.
 */

public class HeaderHolder extends BaseViewHolder<PEResult.Evaluate> {

    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_explain)
    LinearLayout llExplain;
    @BindView(R.id.tv_intro)
    TextView tvIntro;
    @BindView(R.id.ll_evaluate)
    LinearLayout llEvaluate;
    @BindView(R.id.line_explain)
    View lineExplain;
    @BindView(R.id.line_intro)
    View lineIntro;

    private PopupWindow popupWindow;
    //需要放大的图片
    private ZoomImageView tecent_chat_image;
    //加载中的进度条
    private ProgressBar image_scale_progress;

    public HeaderHolder(Context context, View itemView) {
        super(context, itemView);
//        R.layout.pub_activity_prodetails_item_header_layout;
        ButterKnife.bind(this, itemView);
        View popView = LayoutInflater.from(mContext).inflate(R.layout.pub_zoom_popwindow_layout, null);
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
    public void onBindViewHolder(BaseViewHolder holder, PEResult.Evaluate data, int position) {
        if (data == null || data.product == null || TextUtils.isEmpty(data.product.name)) return;
        setBanner(data.product);
        tvMoney.setText("¥ " + BusinessUtils.formatDouble2String(data.product.price));
        ViewUtils.setOrGone(tvTitle, data.product.name);
        if (TextUtils.isEmpty(data.product.intro)) {
            llExplain.setVisibility(View.GONE);
            lineExplain.setVisibility(View.GONE);
            tvIntro.setVisibility(View.GONE);
            lineIntro.setVisibility(View.GONE);
        } else {
            llExplain.setVisibility(View.VISIBLE);
            lineExplain.setVisibility(View.VISIBLE);
            tvIntro.setVisibility(View.VISIBLE);
            lineIntro.setVisibility(View.VISIBLE);
            tvIntro.setText(HtmlUtils.getHtml(mContext, tvIntro, data.product.intro));
        }

        ViewUtils.setOrGone(llEvaluate, holder.getCount() > 1);

    }

    private void setBanner(Data data) {
        ArrayList<String> arrayList = new ArrayList<>();
        if (!TextUtils.isEmpty(data.pic1)) {
            arrayList.add(data.pic1);
        }
        if (!TextUtils.isEmpty(data.pic2)) {
            arrayList.add(data.pic2);
        }
        if (!TextUtils.isEmpty(data.pic3)) {
            arrayList.add(data.pic3);
        }
        if (ArrayUtils.isEmpty(arrayList)) {
            banner.setVisibility(View.GONE);
        } else {
            banner.setVisibility(View.VISIBLE);
        }
        final BannerAdapter adapter = new BannerAdapter<String>(arrayList) {
            @Override
            protected void bindTips(TextView tv, String bannerModel) {
//                tv.setText(bannerModel.getTips());
            }

            @Override
            public void bindImage(ImageView imageView, String bannerModel) {
                ImageLoad.loadPlaceholder(mContext, bannerModel, imageView);
            }
        };
        banner.setBannerAdapter(adapter);
        banner.notifyDataHasChanged();
        banner.setOnBannerItemClickListener(new Banner.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String url = (String) adapter.getmDataList().get(position);
                image_scale_progress.setVisibility(View.GONE);
                popupWindow.showAtLocation(getConvertView(), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                ImageLoad.loadPlaceholder(mContext, url, tecent_chat_image);
            }
        });
    }

    @OnClick({R.id.ll_explain, R.id.ll_evaluate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_explain:
                if (tvIntro.getVisibility() == View.GONE) {
                    tvIntro.setVisibility(View.VISIBLE);
                    lineIntro.setVisibility(View.VISIBLE);
                } else {
                    tvIntro.setVisibility(View.GONE);
                    lineIntro.setVisibility(View.GONE);
                }
                break;
            case R.id.ll_evaluate:
                break;
        }
    }
}
