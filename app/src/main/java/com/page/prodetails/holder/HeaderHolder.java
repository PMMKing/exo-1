package com.page.prodetails.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.framework.rvadapter.holder.BaseViewHolder;
import com.haolb.client.R;
import com.sivin.Banner;
import com.sivin.BannerAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shucheng.qu on 2017/8/16.
 */

public class HeaderHolder extends BaseViewHolder {

    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    public HeaderHolder(Context context, View itemView) {
        super(context, itemView);
//        R.layout.pub_activity_prodetails_item_header_layout;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, Object data, int position) {

        setBanner();
    }

    private void setBanner() {
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            arrayList.add("" + i);
        }
        BannerAdapter adapter = new BannerAdapter<String>(arrayList) {
            @Override
            protected void bindTips(TextView tv, String bannerModel) {
//                tv.setText(bannerModel.getTips());
            }

            @Override
            public void bindImage(ImageView imageView, String bannerModel) {
                imageView.setImageResource(R.drawable.pub_login_icon);
            }

        };
        banner.setBannerAdapter(adapter);
        banner.notifyDataHasChanged();
    }

}
