package com.page.store.prodetails.holder;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.utils.ArrayUtils;
import com.framework.utils.BusinessUtils;
import com.framework.utils.imageload.ImageLoad;
import com.framework.utils.viewutils.ViewUtils;
import com.framework.view.sivin.Banner;
import com.framework.view.sivin.BannerAdapter;
import com.page.store.prodetails.model.PDResult;
import com.page.store.prodetails.model.PDResult.Data;
import com.qfant.wuye.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by shucheng.qu on 2017/8/16.
 */

public class HeaderHolder extends BaseViewHolder<Data> {

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

    public HeaderHolder(Context context, View itemView) {
        super(context, itemView);
//        R.layout.pub_activity_prodetails_item_header_layout;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, Data data, int position) {
        if (data == null || TextUtils.isEmpty(data.name)) return;
        setBanner(data);
        tvMoney.setText("¥ " + BusinessUtils.formatDouble2String(data.price));
        ViewUtils.setOrGone(tvTitle, data.name);
        if (TextUtils.isEmpty(data.intro)) {
            llExplain.setVisibility(View.GONE);
            tvIntro.setVisibility(View.GONE);
        } else {
            llExplain.setVisibility(View.VISIBLE);
            tvIntro.setVisibility(View.VISIBLE);
            tvIntro.setText(data.intro);
        }
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
        BannerAdapter adapter = new BannerAdapter<String>(arrayList) {
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
    }

    @OnClick({R.id.ll_explain, R.id.ll_evaluate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_explain:
                if (tvTitle.getVisibility() == View.GONE) {
                    tvTitle.setVisibility(View.VISIBLE);
                } else {
                    tvTitle.setVisibility(View.GONE);
                }
                break;
            case R.id.ll_evaluate:
                break;
        }
    }
}
